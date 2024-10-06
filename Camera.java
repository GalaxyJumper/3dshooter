public class Camera {
    Point3d pos;
    Point3d vel;
    double[] cameraAngle;
    public Camera(){
        pos = new Point3d();
        vel = new Point3d();
        cameraAngle = new double[] {0, 0};
    }
    public void moveTo(double x, double y, double z){
        pos.moveTo(x, y, z);
    }
    public void move(double x, double y, double z){
        pos.move(x, y, z);
    }
    public void setAngleX(double angleRadians){
        cameraAngle[0] = angleRadians;
    }
    public void setAngleY(double angleRadians){
        cameraAngle[1] = angleRadians;
    }
    public void rotateX(double angleRadians){
        cameraAngle[0] += angleRadians;
    }
    public void rotateY(double angleRadians){
        cameraAngle[1] += angleRadians;
    }
}
