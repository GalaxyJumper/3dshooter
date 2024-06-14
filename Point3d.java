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
    public void rotateAboutZ(Point3d point, double theta){
        double dx = this.x - point.x();
        double dy = this.y - point.y();
        double angle = Math.atan2(dy, dx);
        System.out.println(angle * (180 / Math.PI));
        double dist = Math.sqrt((dx*dx) + (dy*dy));
        angle += theta;
        System.out.println(angle * (180 / Math.PI));
        this.x = (dist * Math.cos(angle));
        this.y = (dist * Math.sin(angle));
        System.out.println(this.x);
        System.out.println(this.y);
        
    }
}
