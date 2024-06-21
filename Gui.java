import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
public class Gui extends JFrame implements ActionListener{
    int width;
    int height;
    ArrayList<GraphicsRunnable> drawQueue;
    Timer frameTimer;
    public Gui(int width, int height){
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawQueue = new ArrayList<GraphicsRunnable>();
        frameTimer = new Timer(17, this);
        frameTimer.start();
        this.repaint();
    }
    public void paint(Graphics g){
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
        });;
    }
    public void drawPoint3d(Point3d p){
        Point3d point = p;
        drawQueue.add(new GraphicsRunnable(){
            public void draw(Graphics2D g2d){
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(4f));
                g2d.drawLine((int)point.x() + 400, (int)point.y() + 400, (int)point.x() + 400, (int)point.y() + 400);
                
            }
        });
    }
    public void drawCuboid(Cuboid c){
        for(int i = 0; i < 8; i++){
            drawPoint3d(c.getPoint(i));
        }
    }
    public void actionPerformed(ActionEvent e){
        this.repaint();
    }
}