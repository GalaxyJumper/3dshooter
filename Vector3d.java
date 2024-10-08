public class Vector3d {
    double x, y, z;
    public Vector3d(Point3d point1, Point3d point2){
        this.x = point2.x() - point1.x();
        this.y = point2.y() - point1.y();
        this.z = point2.z() - point1.z();
    }
    public Vector3d(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
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
    public void add(Vector3d v){
        this.x += v.x();
        this.y += v.y();
        this.z += v.z();
    }
    public void scale(double xFactor, double yFactor, double zFactor){
        this.x *= xFactor;
        this.y *= yFactor;
        this.z *= zFactor;
    }
    public void scale(double factor){
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
    }
    public Vector3d scaled(double factor){
        return new Vector3d(
            x * factor,
            y * factor,
            z * factor
        );
    }
    public Vector3d flip2d(){
        return new Vector3d(-x, -y, z);
    }
    public Vector3d flip3d(){
        return new Vector3d(-x, -y, -z);
    }
    public void setXComp(double x){

    }
    public void setYComp(double y){

    }
    public void setZComp(double z){
        
    }
    public void addXComp(double val){
        x += val;
    }
    
    public void addYComp(double val){
        y += val;
    }
    
    public void addZComp(double val){
        z += val;
    }
    public Point3d toPoint3d(){
        return new Point3d(x, y, z);
    }
    public static Vector3d add(Vector3d v1, Vector3d v2){
        return new Vector3d(v1.x() + v2.x(),
                            v1.y() + v2.y(),
                            v1.z() + v2.z());
    }
}