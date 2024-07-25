import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
public class Gui extends JPanel implements ActionListener{
    int width;
    int height;
    ArrayList<GraphicsRunnable> drawQueue;
    Timer frameTimer;
    double focalLength;
    public Gui(int width, int height, JFrame frame){
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        frame.setSize(width, height);
        frame.setVisible(true);
        this.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        drawQueue = new ArrayList<GraphicsRunnable>();
        frameTimer = new Timer(17, this);
        frameTimer.start();
        this.repaint();
        focalLength = 200;
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
    public void drawPoint3d(Point3d p, int num){
        Point3d point = p.toPersp(focalLength);
        drawQueue.add(new GraphicsRunnable(){
            public void draw(Graphics2D g2d){
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(4f));
                g2d.drawLine((int)point.x() + 400, (int)point.y() + 400, (int)point.x() + 400, (int)point.y() + 400);
                g2d.drawString("" + num, (int)point.x() + 400, (int)point.y() + 400);
                
            }
        });
    }
    public void drawPoint3d(Point3d p, int num, Color color){
        Point3d point = p.toPersp(focalLength).flip();
        drawQueue.add(new GraphicsRunnable(){
            public void draw(Graphics2D g2d){
                g2d.setColor(color);
                g2d.setStroke(new BasicStroke(9f));
                g2d.drawLine((int)point.x() + 400, (int)point.y() + 400, (int)point.x() + 400, (int)point.y() + 400);
                g2d.drawString("" + num, (int)point.x() + 400, (int)point.y() + 400);
                
            }
        });
    }
    public void drawCuboidFace(Cuboid c, int index, Color col){
        drawQueue.add(new GraphicsRunnable() {
            public void draw(Graphics2D g2d){
                for(int i = 0; i < 6; i++){
                    c.drawFace(g2d, index, col, focalLength);
                }
            }
            
        });
    }
    public void drawCuboid(Cuboid c){
        Color[] colors = {
            new Color(255, 255, 0, 150),
            new Color(255, 175, 175, 150),
            new Color(255, 175, 0, 150),
            new Color(0, 255, 255, 150),
            new Color(255, 0, 255, 150),
            new Color(255, 0, 0, 150)
        };
        

        for(int i = 0; i < 6; i++){
            drawCuboidFace(c, i, colors[i]);
        }
        for(int i = 0; i < 8; i++){
            drawPoint3d(c.getPoint(i).flip(), i);
        }
        
        c.cull();

    }
    public double getFocalLength(){
        return focalLength;
    }
    public void actionPerformed(ActionEvent e){
        this.repaint();
    }
}