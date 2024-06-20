import java.awt.event.ActionEvent;

import javax.swing.Timer;
class Main{
    public static void main(String[] args) {
        Gui gui = new Gui(900, 900);
        Point3d test = new Point3d(-100, 100, 0);
        int count = 0;
        Point3d origin = new Point3d(-200, 0, 0);
        gui.background(255, 255, 255);
        gui.drawPoint3d(test);
        gui.drawPoint3d(origin);
        Timer t = new Timer(17, new ActionPerformer(){
            public void actionPerformed(ActionEvent e){
                test.rotateAboutZ(origin, 0.2);
                gui.drawPoint3d(test);
                gui.drawPoint3d(origin);
            }
        });
        t.start();
    }
    //klmfaoo
}