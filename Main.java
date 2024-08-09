import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.Timer;
class Main{
    public static void main(String[] args) {
        JFrame frame = new JFrame("CUBE~!!!!!!");

        Gui gui = new Gui(800, 800, frame);

        Cuboid bruh = new Cuboid(-50, -50, -400, 100, 100, 100, gui);
        gui.background(255, 255, 255);
        bruh.rotateCY(Math.PI / 4);
        bruh.rotateCX(Math.PI / 4);

        Timer t = new Timer(17, new ActionPerformer(){
            public void actionPerformed(ActionEvent e){
                gui.background(220, 220, 220);
                bruh.rotateCY(0.005);
                bruh.rotateCZ(0.002);
                
                gui.drawCuboid(bruh);
            }
        });
        t.start();
    }
    //klmfaoo
}