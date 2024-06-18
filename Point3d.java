public class Point3d {
    private double x;
    private double y;
    private double z;
    public Point3d(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
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
    // Rotate this about the X axis of th 
    public void rotateAboutZ(Point3d point, double theta){
        double dx = this.x - point.x();
        double dy = this.y - point.y();
        
        double angle = Math.atan2(dy, dx);

        double dist = Math.sqrt((dx*dx) + (dy*dy));

        angle += theta;

        this.x = (dist * Math.cos(angle)) + point.x();
        this.y = (dist * Math.sin(angle)) + point.y();
    }
    public void rotateAboutX(Point3d point, double theta){
        double dy = this.y - point.y();
        double dz = this.z - point.z();
        
        double angle = Math.atan2(dy, dz);

        double dist = Math.sqrt((dz*dz) + (dy*dy));

        angle += theta;
        
        this.z = (dist * Math.cos(angle)) + point.z();
        this.y = (dist * Math.sin(angle)) + point.y();
    }
}
