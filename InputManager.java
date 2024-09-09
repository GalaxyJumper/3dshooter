import java.awt.MouseInfo;
import java.awt.event.*;
public class InputManager implements MouseListener, KeyListener{
    MouseEvent lastEvent;
    public InputManager(){
        this.lastEvent = new MouseEvent(null, 0, 0, 0, 0, 0, 0, false);
        
    }
    public double mouseX(){
        return MouseInfo.getPointerInfo().getLocation().getX();
    }
    public double mouseY(){
        return MouseInfo.getPointerInfo().getLocation().getY();
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
