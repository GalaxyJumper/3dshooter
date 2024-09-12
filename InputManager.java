import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.event.*;
import java.awt.Robot;
public class InputManager implements MouseListener, KeyListener, MouseMotionListener{
    double lastX, lastY, mouseX, mouseY;
    Robot robot;
    boolean automatedMove;
    double movedX, movedY;
    public InputManager() throws AWTException{
       lastX = MouseInfo.getPointerInfo().getLocation().getX();
       lastY = MouseInfo.getPointerInfo().getLocation().getY();
       mouseX = MouseInfo.getPointerInfo().getLocation().getX();
       mouseY = MouseInfo.getPointerInfo().getLocation().getY();
       robot = new Robot();
       automatedMove = false;
       movedX = 0;
       movedY = 0;
    }
    public double mouseX(){
        return mouseX;
    }
    public double mouseY(){
        return mouseY;
    }
    public void updateMouse(){
        if(!automatedMove){
            lastX = mouseX;
            lastY = mouseY;
            mouseX = MouseInfo.getPointerInfo().getLocation().getX();
            mouseY = MouseInfo.getPointerInfo().getLocation().getY();
            System.out.println("hello bois");
            if(mouseX < 200 || mouseX > 1080 || mouseY < 200 || mouseY > 520){
                robot.mouseMove(640, 360);
                automatedMove = true;
            }
        } else if(automatedMove){
            mouseX = MouseInfo.getPointerInfo().getLocation().getX();
            mouseY = MouseInfo.getPointerInfo().getLocation().getY();
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
        // Empty
    }
    @Override
    public void keyPressed(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void mouseDragged(MouseEvent e){

    }
    
}
