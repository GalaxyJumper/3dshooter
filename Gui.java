import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
public class Gui extends JPanel{
    public static final double FOCAL_LENGTH = -400; 
    int width;
    int height;
    ArrayList<GraphicsRunnable> drawQueue;
    JFrame frame = new JFrame("3dshooter");
    public Gui(int width, int height){
        this.width = width;
        this.height = height;

        this.setSize(width, height);
        frame.setSize(width, height);

        frame.setVisible(true);
        this.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);

        drawQueue = new ArrayList<GraphicsRunnable>();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(int i = 0; i < drawQueue.size(); i++){
            drawQueue.get(i).draw(g2d);
        }
        drawQueue.clear();
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
                double pointX;
                double pointY;
                Point3d point;    
                String debug;
                for(int i = 0; i < face.numVerts(); i++){
                    point = face.getVert(i).toPersp(Gui.FOCAL_LENGTH);
                    xPoints[i] = (int)(point.x() + 640);
                    yPoints[i] = (int)(point.y() + 360);
                }
                g2d.setColor(face.color());
                g2d.fillPolygon(xPoints, yPoints, 4);
            }
        });
    }
    public void drawCuboid(Cuboid c){

        

        for(int i = 0; i < 6; i++){
            drawFace3d(c.getFace(i));
        }
        for(int i = 0; i < 8; i++){
            drawPoint3d(c.getVert(i), i);
        }
        drawPoint3d(c.center, 987);
        drawPoint3d(c.getVisualCenter(), 255);
        c.cull();

    }
    public double width() { return width; }
    public double height() { return height; }
    public double getFocalLength(){
        return FOCAL_LENGTH;
    }
}