import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Color;
public class Cuboid {
    private Point3d[] verts;
    private Point3d[][] faces;
    double x, y, z, width, height, depth;
    Point3d center;
    double focalLength = 300;
    public Cuboid(int x, int y, int z, int width, int height, int depth){
        this.x = x;
        this.y = y;
        this.z = z; 
        this.width = width;
        this.height = height;
        this.depth = depth; //.
        this.center = new Point3d(x + (width / 2), y + (height / 2), z + (depth / 2));

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
        Point3d[][] sides = new Point3d[][] {
            {verts[0], verts[1], verts[2], verts[3]},
            {verts[1], verts[2], verts[6], verts[5]},
            {verts[0], verts[3], verts[7], verts[4]},
            {verts[0], verts[1], verts[5], verts[4]},
            {verts[3], verts[2], verts[6], verts[7]},
            {verts[4], verts[5], verts[6], verts[7]}
        };
        faces = sides;
    }
    public void move(double dx, double dy, double dz){
        this.x += dx;
        this.y += dy;
        this.z += dz;
        for(int i = 0; i < 8; i++) verts[i].move(dx, dy, dz);
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
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];
        double pointX;
        double pointY;
        this.focalLength = focalLength;
        for(int i = 0; i < faces[index].length; i++){
            pointX = (faces[index][i].x() * focalLength) / (faces[index][i].z() + focalLength + this.z);
            pointY = (faces[index][i].y() * focalLength) / (faces[index][i].z() + focalLength + this.z);
            xPoints[i] = (int)(pointX + 400 + center.x());
            yPoints[i] = (int)(pointY + 400 + center.y());
            if(faces[index][i].isCulled()){
                return;
            }
        }
        g.setColor(color);
        g.fillPolygon(xPoints, yPoints, 4);
        
    }
    public void cull(){
        double lowestZ = verts[0].z();
        int lowestIndex = 0;
        for(int i = 1; i < verts.length; i++){
            verts[i].setCulled(false);
            if(verts[i].z() < lowestZ){
                lowestZ = verts[i].z();
                lowestIndex = i;
            }
        }
        verts[lowestIndex].setCulled(true);

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
}
