import java.awt.Color;
public class Face3d {
    Point3d[] verts;
    boolean isCulled = false;
    Color color;
    boolean useInvertedCullingLogic;
    public Face3d(Point3d[] verts, Color color, boolean inverted){
        this.verts = verts;
        this.color = color;
        this.useInvertedCullingLogic = inverted;
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
        Point3d farthestVert = verts[0];
        for(int i = 0; i < verts.length; i++){
            verts[i].move(x, y, z);
        }
    }
    public boolean contains(Point3d p){
        for(Point3d point : verts){
            if(point.equals(p)){
                return true;
            }
        }
        return false;
    }
    public Point3d getFarthestVert(){
        Point3d farthestVert = verts[0];
        Point3d camera = new Point3d(0, 0, Gui.FOCAL_LENGTH);
        for(int i = 1; i < verts.length; i++){
            if(Point3d.dist3d(verts[i], camera) > Point3d.dist3d(farthestVert, camera))
                farthestVert = verts[i];
        }
        return farthestVert;
    }
    public Point3d getSurfaceNormal(Point3d relativeCenter, Gui gui){
            double avgX = 0;
            double avgY = 0;
            double avgZ = 0;
            Point3d p;
            for(int i = 0; i < verts.length; i++){
                p = verts[i].toPersp(Gui.FOCAL_LENGTH);
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
    public Point3d getVisualCenter(){
        double avgX = 0;
        double avgY = 0;
        double avgZ = 0;
        Point3d p;
        for(int i = 0; i < verts.length; i++){
            p = verts[i].toPersp(Gui.FOCAL_LENGTH);
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
    public Color color(){
        return this.color;
    }
    public boolean usesInvertedCullingLogic(){
        return useInvertedCullingLogic;
    }
    
}
