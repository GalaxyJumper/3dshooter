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
    Cuboid[] staticCuboids; 
    Camera camera;
    public GameManager() throws AWTException{
        camera = new Camera();
        input = new InputManager();
        now = System.currentTimeMillis();
        lastSecond = System.currentTimeMillis();
        gui = new Gui(1280, 720, input);
        Cuboid[] temp = {
            new Cuboid(-50, -50, -50, 100, 100, 100, gui), // bruh
            new Cuboid(-160, -50, -50, 100, 100, 100, gui), // what
            new Cuboid(-500, 500, -500, 1000, 20, 1000, gui) // floor
        };
        Cuboid[] staticTemp = {
            new Cuboid(210, 150, -200, 50, 50, 300, gui),
            new Cuboid(200, 150, 0, 70, 100, 100, gui),
            new Cuboid(200, 175, 20, 70, 90, 120, gui).rotated(0, 1.2466)
        };
        staticCuboids = staticTemp;
        cuboids = temp;
        gui.background(255, 255, 255);
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
        camera.rotateX(rotateX / 800);
        camera.rotateY(rotateY / 800);
        //Player movement        
        playerVel.moveTo(
            playerVel.x() * 0.98,
            playerVel.y() * 0.98,
            playerVel.z() * 0.98
        );
        playerPos.move(playerVel.x(), playerVel.y(), playerVel.z());
        for(int i = 0; i < staticCuboids.length; i++){
            gui.drawCuboid(staticCuboids[i]);
        }
        for(int i = 0; i < cuboids.length; i++){
            cuboids[i].move(playerVel.x(), playerVel.y(), playerVel.z());
            // Reset the y axis so that it points up.
            cuboids[i].rotateAboutX(new Point3d(0, 0, -Gui.FOCAL_LENGTH), -cameraAngle[1]);
            // Rotate cubes.
            cuboids[i].rotateAboutX(new Point3d(0, 0, -Gui.FOCAL_LENGTH), rotateY / 800);
            cuboids[i].rotateAboutY(new Point3d(0, 0, -Gui.FOCAL_LENGTH), -rotateX / 800);
            // Return the y axis to its former position.
            cuboids[i].rotateAboutX(new Point3d(0, 0, -Gui.FOCAL_LENGTH), cameraAngle[1]);
            gui.drawCuboid(cuboids[i]);
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
