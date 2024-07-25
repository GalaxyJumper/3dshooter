public class Face3d {
    Point3d[] verts;
    boolean isCulled = false;
    public Face3d(Point3d[] verts){
        this.verts = verts;
    }
    public Point3d[] getVerts() {
        return this.verts;
    }
    public Point3d getVert(int index){
        return verts[index];
    }
    public void setCulled(boolean c){
        isCulled = c;
    }
    public boolean isCulled(){
        return isCulled;
    }
    public int numVerts(){
        return verts.length;
    }
    public void move(double x, double y, double z){
        for(int i = 0; i < verts.length; i++){
            verts[i].move(x, y, z);
        }
    }
    public Point3d getSurfaceNormal(Point3d relativeCenter, double focalLength, Gui gui){
            double avgX = 0;
            double avgY = 0;
            double avgZ = 0;
            Point3d p;
            for(int i = 0; i < verts.length; i++){
                p = verts[i].toPersp(focalLength);
                gui.drawPoint3d(p, 999);     
                avgX += p.x();
                avgY += p.y();
                avgZ += p.z();
            }
            avgX /= verts.length;
            avgY /= verts.length;
            avgZ /= verts.length;
            Point3d faceCenter = new Point3d(avgX, avgY, avgZ);  
            double magnitude = Point3d.dist3d(relativeCenter, faceCenter);
            return new Point3d(
                (faceCenter.x() - relativeCenter.x()) / magnitude,
                (faceCenter.y() - relativeCenter.y()) / magnitude,
                (faceCenter.z() - relativeCenter.z()) / magnitude
            );
    }
    public Point3d getVisualCenter(double focalLength, Gui gui){
        double avgX = 0;
        double avgY = 0;
        double avgZ = 0;
        Point3d p;
        for(int i = 0; i < verts.length; i++){
            p = verts[i].toPersp(focalLength);
            gui.drawPoint3d(p, 999);     
            avgX += p.x();
            avgY += p.y();
            avgZ += p.z();
        }
        avgX /= verts.length;
        avgY /= verts.length;
        avgZ /= verts.length;
        Point3d faceCenter = new Point3d(avgX, avgY, avgZ);  
        
        return faceCenter;
}
}
