public class Camera {
    Point3d pos;
    Point3d vel;
    double[] cameraAngle;
    public Camera(){
        pos = new Point3d();
        vel = new Point3d();
        cameraAngle = new double[] {0, 0};
    }
}
