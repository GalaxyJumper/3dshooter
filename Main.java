
/* TODO LIST:
 * 
 *  Create face list + sorting [1]
 *  Add lighting [1]
 *  Fix weird perspective distortion [1]
 *  Setup input nonsense [1]
 *  Color the cuboids [0]
 *  Support animation [0]
 *  Write RotateAbsolute [0]
 *  Fix weird overlap stuff [0]
 *  Comment your code lmfao [0]
 * 
 *  Long term (to ponder for now)
 *  Sorted in order of possibility:
 *  Actual lighting
 *  Texture mapping
 *  Occlusion culling
 *  BSP
 *  Ray tracing (??)
 *  
 */

import java.awt.AWTException;

class Main{
    public static void main(String[] args) throws AWTException{
        new GameManager();
        Point3d bruh = new Point3d();
        Point3d g = new Point3d(bruh.x(), bruh.y(), bruh.z());
        g.move(900, 0, 0);
        System.out.println(bruh);
    }
}