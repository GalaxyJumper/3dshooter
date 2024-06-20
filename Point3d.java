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
        this.x = (x * Math.cos(theta) - y * Math.sin(theta)) + point.x();
        this.y = (y * Math.cos(theta) + x * Math.sin(theta)) + point.y();
    }
    public void rotateAboutX(Point3d point, double theta){
        this.z = -(z * Math.cos(theta) - y * Math.sin(theta)) + point.z();
        this.y =  (y * Math.cos(theta) + z * Math.sin(theta)) + point.y();
    }
    public void rotateAboutY(Point3d point, double theta){
        this.x = (x * Math.cos(theta) - y * Math.sin(theta)) + point.x();
        this.z = -(z * Math.cos(theta) - y * Math.sin(theta)) + point.z();
    } 
}
