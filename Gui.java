import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.awt.event.*;
public class Gui extends JPanel{
    public static final double FOCAL_LENGTH = -400; 
    public static final Point3d CAMERA = new Point3d(0, 0, -FOCAL_LENGTH);
    int width;
    int height;
    
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    ArrayList<GraphicsRunnable> drawQueue;
    JFrame frame = new JFrame("3dshooter");
    ArrayList<Face3d> polys = new ArrayList<Face3d> (); // All of the polygons to be drawn in the scene
    Point3d lightSource = new Point3d(0, 0, 0);
    Camera camera;
    public Gui(int width, int height, InputManager input, Camera camera){
        this.width = WIDTH;
        this.height = HEIGHT;
        this.camera = camera;

        this.setSize(width, height);
        frame.setSize(width, height);

        frame.setVisible(true);
        this.setVisible(true);

        frame.addMouseMotionListener(input);
        frame.addKeyListener(input);
        frame.setFocusable(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);

        drawQueue = new ArrayList<GraphicsRunnable>();
    }
    // Paint renamed
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(int i = 0; i < drawQueue.size(); i++){
            drawQueue.get(i).draw(g2d);
        }
        drawQueue.clear();
    }
    public void addToQueue(GraphicsRunnable g){
        drawQueue.add(g);
    }
    public void background(int r, int g, int b){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.setColor(new Color(r, g, b));
                g2d.fillRect(0, 0, width, height);
            }
        });
    }
    public void displayFPS(int n){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                g2d.setColor(new Color(0, 0, 0));
                g2d.drawString(String.valueOf(n), 40, 60);
            }
        });
    }
    public void drawPoint3d(Point3d p, int num){
        Point3d point = p.toPersp(FOCAL_LENGTH);
        drawQueue.add(new GraphicsRunnable(){
            public void draw(Graphics2D g2d){
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(4f));
                g2d.drawLine((int)point.x() + 640, (int)point.y() + 360, (int)point.x() + 640, (int)point.y() + 360);
                g2d.drawString("" + num, (int)point.x() + 640, (int)point.y() + 360);
                
            }
        });
    }
    public void drawPoint3d(Point3d p, int num, Color color){
        Point3d point = p;//.toPersp(FOCAL_LENGTH);
        drawQueue.add(new GraphicsRunnable(){
            public void draw(Graphics2D g2d){
                g2d.setColor(color);
                g2d.setStroke(new BasicStroke(9f));
                g2d.drawLine((int)point.x() + 640, (int)point.y() + 360, (int)point.x() + 640, (int)point.y() + 360);
                g2d.drawString("" + num, (int)point.x() + 640, (int)point.y() + 360);
                
            }
        });
    }

    public void drawFace3d(Face3d face){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                if(face.isCulled()) return;
                int[] xPoints = new int[4];
                int[] yPoints = new int[4];
                Point3d point;    
                for(int i = 0; i < face.numVerts(); i++){
                    point = face.getVert(i).toPersp(Gui.FOCAL_LENGTH);
                    xPoints[i] = (int)(point.x() + 640);
                    yPoints[i] = (int)(point.y() + 360);
                }
                g2d.setColor(face.calcLighting(lightSource, 0.6));
                g2d.fillPolygon(xPoints, yPoints, 4);
            }
        });
    }
    public void drawPolys(){
        polys.sort(new Comparator<Face3d>(){
          public int compare(Face3d f, Face3d f2){
            f = (Face3d)f;
            return ((Double)(f2.getFarthestVert().toPersp(Gui.FOCAL_LENGTH).getCameraDist(new Point3d(0, 0, -Gui.FOCAL_LENGTH))))
                    .compareTo((Double)f.getFarthestVert().toPersp(Gui.FOCAL_LENGTH).getCameraDist(new Point3d(0, 0, -Gui.FOCAL_LENGTH)));
        }
    });
    
        
        for(int i = 0; i < polys.size(); i++){
            drawFace3d(polys.get(i));
        }
        polys.clear();
        
    }
    public void drawCuboid(Cuboid c){
        Cuboid temp = new Cuboid(c);
        temp.cull(camera);
        if(!c.isStatic()){
            temp.move(0, camera.pos().y(), 0);
            temp.move(camera.pos().x(), 0, camera.pos().z());
            temp.rotateAboutY(new Point3d(0, 0, -Gui.FOCAL_LENGTH), -camera.angleYaw());
            temp.rotateAboutX(new Point3d(0, 0, 0 - Gui.FOCAL_LENGTH), camera.anglePitch());

            
        }
        for(int i = 0; i < 6; i++){
            if(!temp.getFace(i).isCulled()){
                polys.add(temp.getFace(i));
            }
        }
        /* Might be useful later.
        for(int i = 0; i < 6; i++){
            drawFace3d(temp.getFace(i));
        }
        */
        //for(int i = 0; i < 8; i++){
        //    drawPoint3d(temp.getVert(i), i);
        //}
        /*
        drawPoint3d(temp.center, 987);
        drawPoint3d(temp.getVisualCenter(), 255);
        temp.cull();
        */
    }
    public double width() { return width; }
    public double height() { return height; }
    public double getFocalLength(){
        return FOCAL_LENGTH;
    }
    public Point3d getLightSource(){
        return lightSource;
    }
    public void moveLightSource(double x, double y, double z){
        lightSource.move(x, y, z);
    }
    public void rotateLightAboutX(Point3d about, double angle){
        lightSource.rotateAboutX(about, angle);
    }
    public void rotateLightAboutY(Point3d about, double angle){
        lightSource.rotateAboutY(about, angle);
    }
    public void rotateLightAboutZ(Point3d about, double angle){
        lightSource.rotateAboutZ(about, angle);
    }
}