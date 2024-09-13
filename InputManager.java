import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.event.*;
import java.awt.Robot;
public class InputManager implements MouseListener, KeyListener, MouseMotionListener{
    double lastX, lastY, mouseX, mouseY;
    Robot robot;
    boolean automatedMove;
    double movedX, movedY;
    boolean mouseLocked;
    public InputManager() throws AWTException{
       lastX = MouseInfo.getPointerInfo().getLocation().getX();
       lastY = MouseInfo.getPointerInfo().getLocation().getY();
       mouseX = MouseInfo.getPointerInfo().getLocation().getX();
       mouseY = MouseInfo.getPointerInfo().getLocation().getY();
       robot = new Robot();
       automatedMove = false;
       movedX = 0;
       movedY = 0;
       mouseLocked = true;
    }
    public double mouseX(){
        return mouseX;
    }
    public double mouseY(){
        return mouseY;
    }
    public void updateMouse(){
        // If the user moves the mouse...
        if(!automatedMove){
            // Update last pos
            lastX = mouseX;
            lastY = mouseY;
            // Get current pos
            mouseX = MouseInfo.getPointerInfo().getLocation().getX();
            mouseY = MouseInfo.getPointerInfo().getLocation().getY();
            // Make sure the mouse is farther than 200 px away from the borders of the screen if the mouse is locked.
            if(mouseLocked && (mouseX < 200 || mouseX > 1080 || mouseY < 200 || mouseY > 520)){
                // If so...
                // Calculate x and y dist from the center
                movedX = 640 - mouseX;
                movedY = 360 - mouseY;
                // Move the mouse to the center.
                robot.mouseMove(640, 360);
                // Notify the program that this was a robot moving the mouse.
                automatedMove = true;
            }
        } else if(automatedMove){
            // Adjust for the shift to the center, so that the camera doesn't "jump" when the mouse gets moved to center.
            lastX = mouseX + movedX;
            lastY = mouseY + movedY;
            // Get new mouseX and Y.
            mouseX = MouseInfo.getPointerInfo().getLocation().getX();
            mouseY = MouseInfo.getPointerInfo().getLocation().getY();
            // Reset automatedMove.
            automatedMove = false;
        }
    }
    public double dMouseX(){
        return mouseX - lastX;
    }
    public double dMouseY(){
        return mouseY - lastY;
    }
    @Override
    public void mouseMoved(MouseEvent e){

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Empty
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Empty
    }
    @Override
    public void keyTyped(KeyEvent e) {
            mouseLocked = !mouseLocked;
            System.out.println("Locked/unlocked moues");
    }
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("broder");
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void mouseDragged(MouseEvent e){

    }
    
}
