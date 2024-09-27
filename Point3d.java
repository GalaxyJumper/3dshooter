public class Point3d{
    private double x;
    private double y;
    private double z;
    private boolean isCulled = false;
    public Point3d(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    } // bruder
    public Point3d(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    } // bruder
    public double x(){
        return this.x;
    }
    public double y(){
        return this.y;
    }
    public double z(){
        return this.z;
    }
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setZ(double z){
        this.z = z;
    }
    public void move(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
    }
    public void moveTo(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Point3d toPersp(double focalLength){
        Point3d point = this;
                
        if(point.z() > -Gui.FOCAL_LENGTH){
            point = point.flip();
            point = new Point3d(
                (point.x() * focalLength) * (point.z() + focalLength),
                (point.y() * focalLength) * (point.z() + focalLength),
                point.z()
            ); 
        } else {
            point = new Point3d(
                (point.x() * focalLength) / (point.z() + focalLength),
                (point.y() * focalLength) / (point.z() + focalLength),
                point.z()
            ); 
        }


        

        return point;
    }
    public double getCameraDist(Point3d camera){
        return Point3d.dist3d(this, camera);
    }
    
    // Rotate this about the X axis of th 
    public void rotateAboutZ(Point3d point, double theta){
        double dx = this.x - point.x();
        double dy = this.y - point.y();
        this.x = (dx * Math.cos(theta) - dy * Math.sin(theta)) + point.x();
        this.y = (dy * Math.cos(theta) + dx * Math.sin(theta)) + point.y();
    }
    public void rotateAboutX(Point3d point, double theta){
        double dz = this.z - point.z();
        double dy = this.y - point.y();
        this.z = (dz * Math.cos(theta) - dy * Math.sin(theta)) + point.z();
        this.y = (dy * Math.cos(theta) + dz * Math.sin(theta)) + point.y();
    }
    public void rotateAboutY(Point3d point, double theta){
        double dx = this.x - point.x();
        double dz = this.z - point.z();
        this.x = (dx * Math.cos(theta) - dz * Math.sin(theta)) + point.x();
        this.z = (dz * Math.cos(theta) + dx * Math.sin(theta)) + point.z();
    }
    public void setCulled(boolean b){
        isCulled = b;
    }
    public boolean isCulled(){
        return isCulled;
    }
    public Point3d flip(){
        return new Point3d(-x, -y, z);
    }
    
    public static double dist3d(double x1, double y1, double z1, double x2, double y2, double z2){
        return Math.sqrt(((x2-x1)*(x2-x1)) + ((y2-y1)*(y2-y1)) + ((z2-z1)*(z2-z1)));
    }
    public static double dist3d(Point3d p1, Point3d p2){

        return Math.sqrt(((p2.x()-p1.x())*(p2.x()-p1.x())) + ((p2.y()-p1.y())*(p2.y()-p1.y())) + ((p2.z()-p1.z())*(p2.z()-p1.z())));
    }
    public static double dist2d(Point3d p1, Point3d p2){
        return Math.sqrt(((p2.x()-p1.x())*(p2.x()-p1.x())) + ((p2.y()-p1.y())*(p2.y()-p1.y())));
    }
    @Override
    public String toString(){
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}   
