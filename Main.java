class Main{
    public static void main(String[] args) {
        Gui gui = new Gui(900, 900);
        Point3d test = new Point3d(100, 100, 0);
        int count = 0;
        Point3d origin = new Point3d(1, 0, 0);
        
        while(count < 5000){
            test.rotateAboutZ(origin, 0.02);
            origin.move(0.01, 0, 0);
            
            gui.drawPoint3d(test);
            gui.drawPoint3d(origin);
        }
        //TEST COMMENT!@!!!@!@!@@#W%$#!^%$&
    }
    //klmfao
}