public class Cuboid {
    private Point3d[] verts;
    double x, y, z, width, height, depth;
    public Cuboid(int x, int y, int z, int width, int height, int depth, boolean mode){
        this.x = x;
        this.y = y;
        this.z = z; 
        this.width = width;
        this.height = height;
        this.depth = depth;
        if(mode){
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
        }
        /*
               x ->
            y  
            |  0    1
            v 
               3    2

               second layer

               4    5

               7    6
        */
    }

}
