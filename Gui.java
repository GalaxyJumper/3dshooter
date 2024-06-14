import javax.swing.*;
import java.awt.*;
public class Gui extends JFrame{
    int width;
    int height;
    public Gui(int width, int height){
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.repaint();
    }
    public void paint(Graphics g){
        g = (Graphics2D)g;
        // Things that are ALWAYS DRAWN sould go here!!!
        g.drawLine(400, 400, 400, 400);
    }
    public void drawPoint3d(Point3d p){
        Gui gui = this;
        Point3d point = p;
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                Graphics2D g = (Graphics2D)gui.getGraphics();
                g.setStroke(new BasicStroke(4f));
                g.drawLine((int)point.x() + 400, (int)point.y() + 400, (int)point.x() + 400, (int)point.y() + 400);
                gui.update(g);
                
            }
        });
    }

}