public class Point3d {
    private double x;
    private double y;
    private double z;
    private boolean isCulled = false;
    public Point3d(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
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
    public static double dist3d(double x1, double y1, double z1, double x2, double y2, double z2){
        return Math.sqrt(((x2-x1)*(x2-x1)) + ((y2-y1)*(y2-y1)) + ((z2-z1)*(z2-z1)));
    }
}   
