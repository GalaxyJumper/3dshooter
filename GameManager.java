import javax.swing.Timer;
import java.awt.event.*;
import java.awt.AWTException;
import java.awt.Robot;
public class GameManager implements ActionListener{
    Gui gui;
    InputManager input;
    Cuboid bruh, what;
    double now, lastSecond, frameRate, framesLastSecond;
    Timer gameLoop;
    double rotateX, rotateY;
    public GameManager() throws AWTException{
        gui = new Gui(1280, 720);
        input = new InputManager();
        bruh = new Cuboid(-50, -50, -50, 100, 100, 100, gui); 

        now = System.currentTimeMillis();
        lastSecond = System.currentTimeMillis();
        what = new Cuboid(-160, -50, -50, 100, 100, 100, gui);
        //what.rotateY(Math.PI/2);
        //Cuboid hat = new Cuboid(-50, -250, -50, 100, 500, 100, gui);
        gui.background(255, 255, 255);
    //    
        gameLoop = new Timer(11, this);
        gameLoop.start();
    }
    // MAIN LOOP
    @Override
    public void actionPerformed(ActionEvent e){
        now = System.currentTimeMillis();
        input.mouseX();
        input.mouseY();
        rotateX = input.dMouseX();
        rotateY = input.dMouseY();

        bruh.rotateAboutX(new Point3d(0, 0, -Gui.FOCAL_LENGTH), rotateY / 1000);
        bruh.rotateAboutY(new Point3d(0, 0, -Gui.FOCAL_LENGTH), -rotateX / 1000);
        
        what.rotateAboutX(new Point3d(0, 0, -Gui.FOCAL_LENGTH), rotateY / 1000);
        what.rotateAboutY(new Point3d(0, 0, -Gui.FOCAL_LENGTH), -rotateX / 1000);
        gui.background(220, 220, 220);
        gui.displayFPS((int)(frameRate));
        //bruh.rotateAboutY(new Point3d(0, 0, 400), 0.01);
        //bruh.move(1, 0, 0);
        gui.rotateLightAboutY(new Point3d(), 0.04);
        gui.drawCuboid(bruh);
        gui.drawPoint3d(gui.getLightSource(), 999);
        gui.drawCuboid(what);
        if(now - lastSecond > 1000){
            lastSecond = now;
            frameRate = framesLastSecond;
            framesLastSecond = 0;
        } else {
            framesLastSecond ++;
        }
        now = System.currentTimeMillis();
        gui.drawPolys();
    }
    
}
