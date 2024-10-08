public class Camera {
    Point3d pos;
    Vector3d vel;
    double[] cameraAngle;
    public Camera(){
        pos = new Point3d();
        vel = new Vector3d(0, 0, 0);
        cameraAngle = new double[] {0, 0};
    }
    public void moveTo(double x, double y, double z){
        pos.moveTo(x, y, z);
    }
    public void move(double x, double y, double z){
        pos.move(x, y, z);
    }
    public void move(Vector3d v){
        pos.move(v.x(), v.y(), v.z());
    }
    public void setYaw(double angleRadians){
        cameraAngle[0] = angleRadians;
    }
    public void setPitch(double angleRadians){
        cameraAngle[1] = angleRadians;
    }
    public void rotateYaw(double angleRadians){
        cameraAngle[0] += angleRadians;
    }
    public void rotatePitch(double angleRadians){
        cameraAngle[1] += angleRadians;
    }
    
    public double angleYaw(){
        return cameraAngle[0];
    }
    public double anglePitch(){
        return cameraAngle[1];
    }
    public void scaleVel(double factor){
        vel.scale(factor);
    }
    
    public void scaleVel(double xFactor, double yFactor, double zFactor){
        vel.scale(xFactor, yFactor, zFactor);
    }
    public void addVel(double x, double y, double z){
        vel.addXComp(x);
        vel.addYComp(y);
        vel.addZComp(z);
    }
    public Vector3d vel(){
        return this.vel;
    }
    public double xVel(){
        return vel.x();
    }
    
    public double yVel(){
        return vel.y();
    }
    
    public double zVel(){
        return vel.z();
    }
    public Point3d pos(){
        return this.pos;
    }
}
