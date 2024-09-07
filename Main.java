import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.util.ArrayList;
class Main{
    public static void main(String[] args) {
        Gui gui = new Gui(1280, 720);
       
        Cuboid bruh = new Cuboid(-2500, 1000, -2100, 5000, 10, 5000, gui);
        //Cuboid hat = new Cuboid(-50, -250, -50, 100, 500, 100, gui);
        gui.background(255, 255, 255);
        
        Timer t = new Timer(11, new ActionPerformer(){
            double now = System.currentTimeMillis();
            double lastSecond = System.currentTimeMillis();
            double frameRate, framesLastSecond;
            public void actionPerformed(ActionEvent e){
                now = System.currentTimeMillis();
                gui.background(220, 220, 220);
                gui.displayFPS((int)(frameRate));
                //bruh.rotateAboutY(new Point3d(0, 0, 400), 0.01);
                //bruh.rotateZ(0.01);
                bruh.rotateY(0.02);
                bruh.move(0, -1, 0);
                //bruh.rotateX(0.05);
                gui.drawCuboid(bruh);
                if(now - lastSecond > 1000){
                    lastSecond = now;
                    frameRate = framesLastSecond;
                    framesLastSecond = 0;
                } else {
                    framesLastSecond ++;
                }
                now = System.currentTimeMillis();
                gui.repaint();
            }
        });
        t.start();
    }
}