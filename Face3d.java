import java.awt.Color;
public class Face3d{
    Point3d[] verts;
    boolean isCulled = false;
    Color color;
    boolean useInvertedCullingLogic;
    Point3d shapeCenter;
    public Face3d(Point3d[] verts, Color color, boolean inverted, Point3d shapeCenter){
        this.verts = verts;
        this.color = color;
        this.useInvertedCullingLogic = inverted;
        this.shapeCenter = shapeCenter;
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
    public Point3d getSurfaceNormal(Point3d relativeCenter){
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
    public Color calcLighting(Point3d lightSource, double intensity){
        Point3d center = getVisualCenter();
        Point3d vLightSource = new Point3d(
            (lightSource.x() - center.x()) / Point3d.dist3d(lightSource, center),
            (lightSource.y() - center.y()) / Point3d.dist3d(lightSource, center),
            (lightSource.z() - center.z()) / Point3d.dist3d(lightSource, center)
        );
        Point3d vSurfNorm = getSurfaceNormal(shapeCenter);
        double dotProduct = ((vSurfNorm.x() * vLightSource.x()) + 
                            (vSurfNorm.y() * vLightSource.y()) +
                            (vSurfNorm.z() * vLightSource.z())) * intensity;
        Color trueColor;
        if(dotProduct > 0){
            trueColor = new Color(
                color.getRed() + (int)((255 - (color.getRed())) * dotProduct),
                color.getGreen() + (int)((255 - (color.getGreen())) * dotProduct),
                color.getBlue() + (int)((255 - (color.getBlue())) * dotProduct)
            ); 
        } else {
            trueColor = new Color(
                color.getRed() + (int)(((color.getRed())) * dotProduct),
                color.getGreen() + (int)(((color.getGreen())) * dotProduct),
                color.getBlue() + (int)(((color.getBlue())) * dotProduct)
            ); 
        }
        return trueColor;
    }   
    public Color color(){
        return this.color;
    }
    public void setShapeCetner(Point3d newCenter){
        this.shapeCenter = newCenter;
    }
    public boolean usesInvertedCullingLogic(){
        return useInvertedCullingLogic;
    }
    
}
