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
    public Vector3d add(Vector3d v){
        return new Vector3d(this.toPoint3d(), null);
    }
    public void setXComp(double x){

    }
    public Point3d toPoint3d(){
        return new Point3d(x, y, z);
    }
    public static Vector3d add(Vector3d v1, Vector3d v2){
        return new Vector3d
    }
}
