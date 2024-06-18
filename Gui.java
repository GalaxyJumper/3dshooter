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
        this.repaint();
    }
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(new Color(200, 200, 255));
        g2d.fillRect(0, 0, width, height);
        for(int i = 0; i < drawQueue.size(); i++){
            drawQueue.get(i).draw(g2d);
        }
    }
    public void drawPoint3d(Point3d p){
        Gui gui = this;
        Point3d point = p;
        drawQueue.add(new GraphicsRunnable(){
            public void draw(Graphics2D g2d){
                g2d.setStroke(new BasicStroke(4f));
                g2d.drawLine((int)point.x() + 400, (int)point.y() + 400, (int)point.x() + 400, (int)point.y() + 400);
                
            }
        });
    }
    public void actionPerformed(ActionEvent e){

    }
}