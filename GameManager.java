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
    Point3d playerVel;
    public GameManager() throws AWTException{
        input = new InputManager();
        playerVel = new Point3d();
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
            cuboids[i].move(playerVel.x(), playerVel.y(), playerVel.z());
            gui.moveLightSource(playerVel.x(), playerVel.y(), playerVel.z());
            playerVel.moveTo(
                playerVel.x() * 0.98,
                playerVel.y() * 0.98,
                playerVel.z() * 0.98
            );
            cuboids[i].rotateAboutX(new Point3d(0, 0, -Gui.FOCAL_LENGTH), rotateY / 600);
            cuboids[i].rotateAboutY(new Point3d(0, 0, -Gui.FOCAL_LENGTH), -rotateX / 1000);
            gui.drawCuboid(cuboids[i]);
            gui.rotateLightAboutX(new Point3d(0, 0, -Gui.FOCAL_LENGTH), rotateY / 1000);
            gui.rotateLightAboutY(new Point3d(0, 0, -Gui.FOCAL_LENGTH), -rotateX / 1000);
            if(input.getKey(87)){
                playerVel.move(0, 0, 0.05);
            } else if(input.getKey(83)){
                playerVel.move(0, 0, -0.05);
            }
            if(input.getKey(65)){
                playerVel.move(0.05, 0, 0);
            } else if(input.getKey(68)){
                playerVel.move(-0.05, 0, 0);
            }
            if(input.getKey(32)){
                playerVel.move(0, 0.05, 0);
            } else if(input.getKey(17)){
                playerVel.move(0, -0.05, 0);
            }
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
