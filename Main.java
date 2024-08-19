import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.Timer;
class Main{
    public static void main(String[] args) {
        JFrame frame = new JFrame("CUBE~!!!!!!");

        Gui gui = new Gui(800, 800, frame);
       
        Cuboid bruh = new Cuboid(-50, -50, -100, 200, 100, 100, gui);
        //Cuboid hat = new Cuboid(-50, -250, -50, 100, 500, 100, gui);
        gui.background(255, 255, 255);
        
        Timer t = new Timer(11, new ActionPerformer(){
            double now, lastFrame;
            public void actionPerformed(ActionEvent e){
                now = System.currentTimeMillis();
                gui.background(220, 220, 220);
                gui.displayFPS((int)(1000 / (now - lastFrame)));
                //bruh.rotateAboutY(new Point3d(0, 0, 400), 0.01);
                bruh.rotateZ(0.01);
                bruh.rotateY(0.02);
                //bruh.move(0, 0, -1);
                bruh.rotateX(0.05);
                gui.drawCuboid(bruh);
                lastFrame = now;
                System.out.println(Point3d.dist3d(bruh.center, bruh.getVert(0)));
            }
        });
        t.start();
    }
    //klmfaoo
}