import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.Timer;
class Main{
    public static void main(String[] args) {
        JFrame frame = new JFrame("CUBE~!!!!!!");
        Gui gui = new Gui(800, 800, frame);
        Point3d test = new Point3d(-100, 100, 0);
        int count = 0;
        Point3d origin = new Point3d(-200, 0, 0);
        Cuboid bruh = new Cuboid(-50, -50, -200, 100, 100, 100);
        gui.background(255, 255, 255);
        gui.drawPoint3d(test);
        gui.drawPoint3d(origin);
        bruh.rotateCY(Math.PI / 4);
        bruh.rotateCX(Math.PI / 4);
        Timer t = new Timer(17, new ActionPerformer(){
            public void actionPerformed(ActionEvent e){
                gui.background(220, 220, 220);
                //gui.drawPoint3d(test);
                //gui.drawPoint3d(origin);
                gui.drawCuboid(bruh);
                bruh.rotateCY(0.02);
                bruh.move(0, 0, -1);
                
 
            }
        });
        t.start();
    }
    //klmfaoo
}