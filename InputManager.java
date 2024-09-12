import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.event.*;
import java.awt.Robot;
public class InputManager implements MouseListener, KeyListener, MouseMotionListener{
    double lastX, lastY, mouseX, mouseY;
    Mouse grabber;
    public InputManager() throws AWTException{
       lastX = MouseInfo.getPointerInfo().getLocation().getX();
       lastY = MouseInfo.getPointerInfo().getLocation().getY();
       mouseX = MouseInfo.getPointerInfo().getLocation().getX();
       mouseY = MouseInfo.getPointerInfo().getLocation().getY();
       grabber = new Mouse();
       grabber.setGrabbed();
    }
    public double mouseX(){
        return mouseX;
    }
    public double mouseY(){
        return mouseY;
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
