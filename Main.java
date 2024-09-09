import java.awt.event.ActionEvent;
import javax.swing.Timer;
/* TODO LIST:
 * 
 *  Create face list + sorting [1]
 *  Add lighting [1]
 *  Fix weird perspective distortion
 *  Setup input nonsense
 */
class Main{
    public static void main(String[] args) {
        Gui gui = new Gui(1280, 720);
       
        Cuboid bruh = new Cuboid(-50, -50, -50, 100, 100, 100, gui);
        bruh.rotateX(Math.PI / 4);
        bruh.rotateY(Math.PI / 4);
        //Cuboid what = new Cuboid(-160, -50, -100, 100, 100, 100, gui);
        //what.rotateY(Math.PI/2);
        //Cuboid hat = new Cuboid(-50, -250, -50, 100, 500, 100, gui);
        gui.background(255, 255, 255);
    //    
        Timer t = new Timer(11, new ActionPerformer(){
            double now = System.currentTimeMillis();
            double lastSecond = System.currentTimeMillis();
            double frameRate, framesLastSecond;
            public void actionPerformed(ActionEvent e){
                now = System.currentTimeMillis();
                gui.background(220, 220, 220);
                gui.displayFPS((int)(frameRate));
                //bruh.rotateAboutY(new Point3d(0, 0, 400), 0.01);
                bruh.rotateZ(0.01);
                bruh.rotateY(0.02);
                //what.rotateAboutY(bruh.center, 0.02);
                //bruh.move(1, 0, 0);
                gui.rotateLightAboutY(new Point3d(), 0.02);
                System.out.println(bruh.center.toString() + " ,");
                System.out.println();
                gui.drawCuboid(bruh);
                gui.drawPoint3d(gui.getLightSource(), 999);
                //gui.drawCuboid(what);
                if(now - lastSecond > 1000){
                    lastSecond = now;
                    frameRate = framesLastSecond;
                    framesLastSecond = 0;
                } else {
                    framesLastSecond ++;
                }
                now = System.currentTimeMillis();
                gui.drawPolys();
                gui.repaint();
            }
        });
        t.start();
    }
}