class Main{
    public static void main(String[] args) {
        Gui gui = new Gui(900, 900);
        Point3d test = new Point3d(100, 100, 0);
        int count = 0;
        while(count < 5000){
            test.rotateAboutZ(new Point3d(0, 0, 0), 0.2);
            gui.drawPoint3d(test);
        }
        //TEST COMMENT!@!!!@!@!@@#W%$#!^%$&
    }
}