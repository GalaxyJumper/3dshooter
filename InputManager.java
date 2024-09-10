import java.awt.MouseInfo;
import java.awt.event.*;
public class InputManager implements MouseListener, KeyListener{
    double lastX, lastY, mouseX, mouseY;
    public InputManager(){
       lastX = MouseInfo.getPointerInfo().getLocation().getX();
       lastY = MouseInfo.getPointerInfo().getLocation().getY();
       mouseX = MouseInfo.getPointerInfo().getLocation().getX();;
       mouseY = MouseInfo.getPointerInfo().getLocation().getY();
    }
    public double mouseX(){
        lastX = mouseX;
        mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        return mouseX;
    }
    public double mouseY(){
        lastY = mouseY;
        mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        return mouseY;
    }
    public double dMouseX(){
        return mouseX - lastX;
    }
    public double dMouseY(){
        return mouseY - lastY;
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
    
}
