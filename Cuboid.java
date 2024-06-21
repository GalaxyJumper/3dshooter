import javax.swing.*;
import java.awt.Graphics2D;
public class Cuboid {
    private Point3d[] verts;
    private Point3d[][] faces;
    double x, y, z, width, height, depth;
    Point3d center;
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

               4  3  5
               2  5  1
               7  4  6
        */
        Point3d[][] sides = new Point3d[][] {
            {verts[0], verts[1], verts[2], verts[3]},
            {verts[1], verts[2], verts[6], verts[5]},
            {verts[0], verts[3], verts[7], verts[4]},
            {verts[0], verts[1], verts[5], verts[4]},
            {verts[3], verts[2], verts[6], verts[7]},
            {verts[4], verts[5], verts[6], verts[7]}
        };
    }

    public void rotateZ(double theta){
        for(int i = 0; i < verts.length; i++){
            verts[i].rotateAboutZ(center, theta);
        }
    }
    public void rotateX(){

    }
    public void rotateY(){

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
}
