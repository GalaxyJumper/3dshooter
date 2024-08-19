import javax.swing.*;
import java.awt.Graphics2D;
import java.nio.file.FileAlreadyExistsException;
import java.awt.Color;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;



public class Cuboid {
    private Point3d[] verts = new Point3d[8];
    private Face3d[] faces = new Face3d[6];
    double x, y, z, width, height, depth;
    public Point3d center;
    double focalLength = -400;
    Point3d cameraPos = new Point3d(0, 0, focalLength);
    Gui gui;
    Point3d lastFarthestVert = new Point3d(0, 0, 0);
    public Cuboid(int x, int y, int z, int width, int height, int depth, Gui gui){
        // Basic cuboid nonsense
        this.x = x;
        this.y = y;
        this.z = z; 
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.center = new Point3d(x + (width / 2), y + (height / 2), z + (depth / 2));

        this.gui = gui;
        this.focalLength = gui.getFocalLength();

        verts[0] = new Point3d(x, y, z);
        verts[1] = new Point3d(x + width, y, z);
        verts[2] = new Point3d(x + width, y + height, z);
        verts[3] = new Point3d(x, y + height, z);
            
        verts[4] = new Point3d(x, y, z + depth);
        verts[5] = new Point3d(x + width, y, z + depth);
        verts[6] = new Point3d(x + width, y + height, z + depth);
        verts[7] = new Point3d(x, y + height, z + depth);
    
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
            new Face3d(new Point3d[] {verts[0], verts[1], verts[2], verts[3]}, focalLength, new Color(255, 255, 0  ), false),
            new Face3d(new Point3d[] {verts[1], verts[2], verts[6], verts[5]}, focalLength, new Color(255, 175, 175), true),
            new Face3d(new Point3d[] {verts[0], verts[3], verts[7], verts[4]}, focalLength, new Color(255, 175, 0  ), false),
            new Face3d(new Point3d[] {verts[0], verts[1], verts[5], verts[4]}, focalLength, new Color(0, 255, 255  ), true),
            new Face3d(new Point3d[] {verts[3], verts[2], verts[6], verts[7]}, focalLength, new Color(255, 0, 255  ), false),
            new Face3d(new Point3d[] {verts[4], verts[5], verts[6], verts[7]}, focalLength, new Color(255, 0, 0    ), true)
        };
        faces = sides;
    }
    public void move(double dx, double dy, double dz){
        this.x += dx;
        this.y += dy;
        this.z += dz;
        for(int i = 0; i < 6; i++) faces[i].move(dx, dy, dz);
        center = getVisualCenter();
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
    public Point3d getVert(int index){
        return verts[index];
    }
    public void rotateAboutX(Point3d p, double theta){
        for(int i = 0; i < verts.length; i++){
            verts[i].rotateAboutX(p, theta);
        }
    }
    public void rotateAboutY(Point3d p, double theta){
        for(int i = 0; i < verts.length; i++){
            verts[i].rotateAboutY(p, theta);
        }
        center.rotateAboutY(p, theta);
    }
    public void rotateAboutZ(Point3d p, double theta){
        for(int i = 0; i < verts.length; i++){
            verts[i].rotateAboutZ(p, theta);
        }
        center.rotateAboutZ(p, theta);
    }
    public void drawFace(Graphics2D g, int index, double focalLength){
        if(faces[index].isCulled()) return;
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];
        double pointX;
        double pointY;
        Point3d point;    

        for(int i = 0; i < faces[index].numVerts(); i++){
            point = faces[index].getVert(i).toPersp(focalLength);
            xPoints[i] = (int)(point.x() + center.x() + 400);
            yPoints[i] = (int)(point.y() + center.y() + 400);
        }
        g.setColor(faces[index].color());
        g.fillPolygon(xPoints, yPoints, 4);

        
    }
    public void cull(){
        double sum = 0;
        Point3d vert1;
        Point3d vert2;
        boolean isVisible;
        for(int i = 0; i < faces.length; i++){
            sum = 0;
            isVisible = false; // assume that the face is offscreen until we find an onscreen point
            faces[i].setCulled(false); // assume that this face is facing forward until proven otherwise
            for(int k = 0; k < 4; k++){

                vert1 = faces[i].getVert(k).toPersp(focalLength);
                vert2 = faces[i].getVert((k + 1) % 4).toPersp(focalLength);

                
                if(vert1.x() + 400 >= 0 && vert1.x() + 400 <= gui.width() &&
                   vert1.y() + 400 >= 0 && vert1.y() + 400 <= gui.height() && vert1.z() <= focalLength){
                    isVisible = true;
                }

                sum += (vert1.x() - vert2.x()) * (vert1.y() + vert2.y());
            }
            
            if(!isVisible){
                //faces[i].setCulled(true);
            }
            if(((faces[i].usesInvertedCullingLogic())? (sum < 0) : (sum > 0))){
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
    public Point3d getVisualCenter(){
        Point3d center = new Point3d();
        for(int i = 0; i < verts.length; i++){
            center.move(verts[i].x(), verts[i].y(), verts[i].z());
        }
        center.moveTo(
            center.x() / verts.length, 
            center.y() / verts.length, 
            center.z() / verts.length 
        );
        return center;
    }
}
