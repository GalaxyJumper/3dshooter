import javax.swing.Timer;
import java.awt.event.*;
import java.awt.AWTException;
public class GameManager implements ActionListener{
    Gui gui;
    InputManager input;
    double now, lastSecond, frameRate, framesLastSecond;
    Timer gameLoop;
    double rotateX, rotateY;
    Cuboid[] cuboids;
    public GameManager() throws AWTException{
        input = new InputManager();

        now = System.currentTimeMillis();
        lastSecond = System.currentTimeMillis();
        gui = new Gui(1280, 720, input);
        Cuboid[] temp = {
            new Cuboid(-50, -50, -50, 100, 100, 100, gui), // bruh
            new Cuboid(-160, -50, -50, 100, 100, 100, gui), // what
            new Cuboid(-500, 500, -500, 1000, 20, 1000, gui) // floor
        };
        cuboids = temp;
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
        input.updateMouse(); 
        rotateX = input.dMouseX();
        rotateY = input.dMouseY();
        for(int i = 0; i < cuboids.length; i++){
            cuboids[i].rotateAboutX(new Point3d(0, 0, -Gui.FOCAL_LENGTH), rotateY / 1000);
            cuboids[i].rotateAboutY(new Point3d(0, 0, -Gui.FOCAL_LENGTH), -rotateX / 1000);
            gui.drawCuboid(cuboids[i]);
            gui.rotateLightAboutX(new Point3d(0, 0, -Gui.FOCAL_LENGTH), rotateY / 1000);
            gui.rotateLightAboutY(new Point3d(0, 0, -Gui.FOCAL_LENGTH), -rotateX / 1000);
        }
        gui.background(220, 220, 220);
        gui.displayFPS((int)(frameRate));
        //bruh.rotateAboutY(new Point3d(0, 0, 400), 0.01);
        //bruh.move(1, 0, 0); 
        gui.drawPoint3d(gui.getLightSource(), 999);

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
    
}
