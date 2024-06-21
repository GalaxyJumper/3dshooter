import java.awt.event.ActionEvent;

import javax.swing.Timer;
class Main{
    public static void main(String[] args) {
        Gui gui = new Gui(900, 900);
        Point3d test = new Point3d(-100, 100, 0);
        int count = 0;
        Point3d origin = new Point3d(-200, 0, 0);
        Cuboid bruh = new Cuboid(-50, -50, -50, 100, 100, 100);
        gui.background(255, 255, 255);
        gui.drawPoint3d(test);
        gui.drawPoint3d(origin);
        bruh.rotateCY(Math.PI / 4);
        bruh.rotateCX(Math.PI / 4);
        Timer t = new Timer(17, new ActionPerformer(){
            public void actionPerformed(ActionEvent e){
                gui.background(220, 220, 220);
                test.rotateAboutZ(origin, 0.2);
                test.move(1, 0, 0);
                origin.move(1, 0, 0);
                gui.drawPoint3d(test);
                gui.drawPoint3d(origin);
                gui.drawCuboid(bruh);
                bruh.rotateCY(0.2);
            }
        });
        t.start();
    }
    //klmfaoo
}