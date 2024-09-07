import java.awt.Color;
public class Cuboid {
    private Point3d[] verts = new Point3d[8];
    private Face3d[] faces = new Face3d[6];
    double x, y, z, width, height, depth;
    public Point3d center;
    Point3d cameraPos = new Point3d(0, 0, Gui.FOCAL_LENGTH);
    Gui gui;
    Point3d lastFarthestVert = new Point3d(0, 0, 0);
    public Cuboid(int x, int y, int z, int width, int height, int depth, Gui gui){
        // Basic cuboid attributes
        this.x = x;
        this.y = y;
        this.z = z; 
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.center = new Point3d(x + (width / 2), y + (height / 2), z + (depth / 2));
        // Draw space things, mostly for culling
        this.gui = gui;
        // Define vertices, "front" layer
        verts[0] = new Point3d(x, y, z);
        verts[1] = new Point3d(x + width, y, z);
        verts[2] = new Point3d(x + width, y + height, z);
        verts[3] = new Point3d(x, y + height, z);
        // "back" layer
        verts[4] = new Point3d(x, y, z + depth);
        verts[5] = new Point3d(x + width, y, z + depth);
        verts[6] = new Point3d(x + width, y + height, z + depth);
        verts[7] = new Point3d(x, y + height, z + depth);
        // Define faces       {vertices of this face...............................}  color of this face            does it face backward at definition (for culling)
        faces[0] = new Face3d(new Point3d[] {verts[0], verts[1], verts[2], verts[3]}, new Color(255, 255, 0  ), false);
        faces[1] = new Face3d(new Point3d[] {verts[1], verts[2], verts[6], verts[5]}, new Color(255, 175, 175), true);
        faces[2] = new Face3d(new Point3d[] {verts[0], verts[3], verts[7], verts[4]}, new Color(255, 175, 0  ), false);
        faces[3] = new Face3d(new Point3d[] {verts[0], verts[1], verts[5], verts[4]}, new Color(0, 255, 255  ), true);
        faces[4] = new Face3d(new Point3d[] {verts[3], verts[2], verts[6], verts[7]}, new Color(255, 0, 255  ), false);
        faces[5] = new Face3d(new Point3d[] {verts[4], verts[5], verts[6], verts[7]}, new Color(255, 0, 0    ), true);
    }
    ////////////////////////////////////////////
    // MOVEMENT & ROTATION
    //////////////////////////////////////////
    public void move(double dx, double dy, double dz){
        this.x += dx;
        this.y += dy;
        this.z += dz;
        // Moving faces
        for(int i = 0; i < 6; i++) faces[i].move(dx, dy, dz);
        // Ugly hack - in some cases the center can drift. Correct each time the cuboid moves.
        center = getVisualCenter();
    }
    // See Point3d for documentation.
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
    // Loops through each face and determines whether it is facing forward
    // or backward using winding order - loop through the vertices of said face
    // in the order you are given them and check whether they wind clockwise 
    // or counterclockwise.
    public void cull(){

        double sum = 0;
        Point3d vert1, vert2;
        boolean isVisible;

        for(int i = 0; i < faces.length; i++){
            sum = 0; // Reset sum for each iteration
            isVisible = false; // assume that the face is offscreen until we find an onscreen point
            faces[i].setCulled(false); // assume that this face is facing forward until proven otherwise
            for(int k = 0; k < 4; k++){
                // Get the vert at k and the vert after it (vert 0 is after vert 3)
                vert1 = faces[i].getVert(k).toPersp(Gui.FOCAL_LENGTH);
                
                vert2 = faces[i].getVert((k + 1) % 4).toPersp(Gui.FOCAL_LENGTH);

                // Is the vert at k onscreen? If so this face is onscreen.
                if(vert1.x() + 640 >= 0 && vert1.x() + 640 <= gui.width() &&
                   vert1.y() + 360 >= 0 && vert1.y() + 360 <= gui.height() && vert1.z() <= -Gui.FOCAL_LENGTH) {
                    isVisible = true;
                }
                // Determine winding order between this vert and the vert after it.
                // Positive = clockwise afaik negative = counterclockwise
                // By summing up the orders we can determine the winding order of a face.
                sum += (vert1.x() - vert2.x()) * (vert1.y() + vert2.y());
            }
            
            if(!isVisible){
                faces[i].setCulled(true);
            }
            // Some faces face backwards at first and require inverted logic.
            if(((faces[i].usesInvertedCullingLogic())? (sum < 0) : (sum > 0))){
                faces[i].setCulled(true);
            }
        }
        
    }
    // Gets the center of this Cuboid by averaging all the points.
    // TODO: Optimize.
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
    public Point3d getVert(int index){
        return verts[index];
    }
    public Face3d getFace(int index){
        return faces[index];
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
