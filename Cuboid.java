import javax.swing.*;
import java.awt.Graphics2D;
import java.nio.file.FileAlreadyExistsException;
import java.awt.Color;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;



public class Cuboid {
    private Point3d[] verts;
    private Face3d[] faces;
    double x, y, z, width, height, depth;
    Point3d center;
    double focalLength = 200;
    Point3d cameraPos = new Point3d(0, 0, focalLength);
    Gui gui;
    Point3d lastFarthestVert = new Point3d(0, 0, 0);
    public Cuboid(int x, int y, int z, int width, int height, int depth, Gui gui){
        this.x = x;
        this.y = y;
        this.z = z; 
        this.width = width;
        this.height = height;
        this.depth = depth; //.
        this.center = new Point3d(x + (width / 2), y + (height / 2), z + (depth / 2));
        this.gui = gui;
        this.focalLength = gui.getFocalLength();
        Point3d[] vertices = {
            new Point3d(x, y, z),
            new Point3d(x + width, y, z),
            new Point3d(x + width, y + height, z),
            new Point3d(x, y + height, z),
            
            new Point3d(x, y, z + depth),
            new Point3d(x + width, y, z + depth),
            new Point3d(x + width, y + height, z + depth),
            new Point3d(x, y + height, z + depth)
        };
        verts = vertices;
    
        /*
            // Numbers in the corners represent vertices.
            // Numbers anywhere else represent sides.
            // Imagine this chart as two layers of a cube; 
            the second is father forward than the first. 
               x ->
            y  
            |  0  3  1             
            v  2  0  1              
               3  4  2               
                
               second layer ( in front, supposedly )
                               ________
               4  3  5        |\   3    \  0 is in the back
               2  5  1        | \ _______\
               7  4  6        | 2|       | <-- 1
                               \ |   5   |
                                \|_______|      
                                 

        */
        Face3d[] sides = new Face3d[] {
            new Face3d(new Point3d[] {verts[0], verts[1], verts[2], verts[3]}, focalLength, new Color(255, 255, 0)),
            new Face3d(new Point3d[] {verts[1], verts[2], verts[6], verts[5]}, focalLength, new Color(255, 175, 175)),
            new Face3d(new Point3d[] {verts[0], verts[3], verts[7], verts[4]}, focalLength, new Color(255, 175, 0)),
            new Face3d(new Point3d[] {verts[0], verts[1], verts[5], verts[4]}, focalLength, new Color(0, 255, 255)),
            new Face3d(new Point3d[] {verts[3], verts[2], verts[6], verts[7]}, focalLength, new Color(255, 0, 255)),
            new Face3d(new Point3d[] {verts[4], verts[5], verts[6], verts[7]}, focalLength, new Color(255, 0, 0))
        };
        faces = sides;
    }
    public void move(double dx, double dy, double dz){
        this.x += dx;
        this.y += dy;
        this.z += dz;
        for(int i = 0; i < 8; i++) verts[i].move(dx, dy, dz);
        for(int i = 0; i < 6; i++) faces[i].move(dx, dy, dz);
        center.move(dx, dy, dz);
    }
    public void rotateZ(double theta){
        for(int i = 0; i < verts.length; i++){
            verts[i].rotateAboutZ(center, theta);
        }
    }
    public void rotateX(double theta){
        for(int i = 0; i < verts.length; i++){
            verts[i].rotateAboutX(center, theta);
        }
    }
    public void rotateY(double theta){
        for(int i = 0; i < verts.length; i++){
            verts[i].rotateAboutY(center, theta);
        }
    }
    public void rotateCZ(double theta){
        for(int i = 0; i < verts.length; i++){
            verts[i].rotateAboutZ(center, theta);
        }
    }
    public void rotateCX(double theta){
        for(int i = 0; i < verts.length; i++){
            verts[i].rotateAboutX(center, theta);
        }
    }
    public void rotateCY(double theta){
        for(int i = 0; i < verts.length; i++){
            verts[i].rotateAboutY(center, theta);
        }
    }
    public Point3d getPoint(int index){
        return verts[index];
    }
    public void drawFace(Graphics2D g, int index, double focalLength){
        if(faces[index].isCulled()) return;
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];
        double pointX;
        double pointY;
        Point3d point;
        
        for(int i = 0; i < faces[index].numVerts(); i++){
            point = faces[index].getVert(i).toPersp(focalLength).flip();
            xPoints[i] = (int)(point.x() + center.x() + 400);
            yPoints[i] = (int)(point.y() + center.y() + 400);
        }
        g.setColor(faces[index].color());
        g.fillPolygon(xPoints, yPoints, 4);

        
    }
    public void cull(){
        Point3d farthestVert = verts[0];
        Point3d camera = new Point3d(0, 0, -focalLength);
        Face3d[] tempFaces = new Face3d[6];
        ArrayList<Face3d> temp = new ArrayList<Face3d>();
        // Find the farthest vertex.
        for(int i = 1; i < verts.length; i++){
            if(Point3d.dist3d(verts[i], camera) > Point3d.dist3d(farthestVert, camera)){
                farthestVert = verts[i];
            }
        }
        // If the farthest vertex hasn't changed then nothing else has.
        if(farthestVert.equals(lastFarthestVert)) return;
        // Cull/uncull faces based om whether they contain farthestVert.
        for(int i = 0; i < faces.length; i++){
            faces[i].setCulled(false);
            if(faces[i].contains(farthestVert)){
                faces[i].setCulled(true);
                temp.add(faces[i]);
            } else {
                temp.add(0, faces[i]);
            }
        }
        // Fast sort of 3 objects - sort the 3 unculled faces by distance to the camera.
        if(temp.get(0).getCameraDist() > temp.get(1).getCameraDist()){
            Collections.swap(temp, 0, 1);
        }
        
        if(temp.get(0).getCameraDist() > temp.get(2).getCameraDist()){
            Collections.swap(temp, 0, 2);
        }
        if(temp.get(1).getCameraDist() > temp.get(2).getCameraDist()){
            Collections.swap(temp, 1, 2);
        }
        faces = temp.toArray(tempFaces);
        //last line
        lastFarthestVert = farthestVert;
    }
    public double x(){
        return x;
    }
    public double y(){
        return y;
    }
    public double z(){
        return z;
    }
    public double dist2d(Point3d point1, Point3d point2){
        return Math.sqrt(Math.pow(point2.x() - point1.x(), 2) + Math.pow(point2.x() - point1.x(), 2));
    }
}
