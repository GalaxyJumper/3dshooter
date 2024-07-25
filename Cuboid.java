import javax.swing.*;
import java.awt.Graphics2D;
import java.nio.file.FileAlreadyExistsException;
import java.awt.Color;
import java.awt.*;
public class Cuboid {
    private Point3d[] verts;
    private Face3d[] faces;
    double x, y, z, width, height, depth;
    Point3d center;
    double focalLength = 200;
    Point3d cameraPos = new Point3d(0, 0, focalLength);
    Gui gui;
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
            new Face3d(new Point3d[] {verts[0], verts[1], verts[2], verts[3]}),
            new Face3d(new Point3d[] {verts[1], verts[2], verts[6], verts[5]}),
            new Face3d(new Point3d[] {verts[0], verts[3], verts[7], verts[4]}),
            new Face3d(new Point3d[] {verts[0], verts[1], verts[5], verts[4]}),
            new Face3d(new Point3d[] {verts[3], verts[2], verts[6], verts[7]}),
            new Face3d(new Point3d[] {verts[4], verts[5], verts[6], verts[7]})
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
    public void drawFace(Graphics2D g, int index, Color color, double focalLength){
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
        g.setColor(color);
        g.fillPolygon(xPoints, yPoints, 4);

        
    }
    public void cull(){
        // Take two opposite points on a face in 3d space and move them to 2d space.
        // If the one with higher Z (closer to the camera) is farther from the camera than the other point,
        // that face is facing away from the camera and should be culled.
        Point3d vSurfNorm;
        Point3d vCamera;

        Point3d centerPersp = center.toPersp(focalLength);
        Point3d camera = new Point3d(0, 0, focalLength);

        for(int i = 0; i < faces.length; i++){
            faces[i].setCulled(false);
            Point3d faceCenter = faces[i].getVisualCenter(focalLength, gui);
            vSurfNorm = faces[i].getSurfaceNormal(center, focalLength, gui);
            vCamera = new Point3d(
                faceCenter.x() - camera.x(),
                faceCenter.y() - camera.y(),
                faceCenter.z() - camera.z()
            );
            
            double dotProduct = 
                (vCamera.x() * vSurfNorm.x()) +
                (vCamera.y() * vSurfNorm.y()) +
                (vCamera.z() * vSurfNorm.z()) ;
            if(dotProduct > 0){
                faces[i].setCulled(true);
            }
        }

        
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
