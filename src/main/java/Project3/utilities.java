package Project3;

import javax.swing.ImageIcon;
import java.awt.*;

interface MyConstants{
    static final int FRAME_WIDTH   = 1500;
    static final int FRAME_HEIGHT  = 800;

    static final int CH_WIDTH      = 60;
    static final int CH_HEIGHT     = 50;
    static final int IT_WIDTH      = 60;
    static final int IT_HEIGHT     = 50;
}

class MyImageIcon extends ImageIcon{
    public MyImageIcon(String fname)  { super(fname); }
    public MyImageIcon(Image image)   { super(image); }

    public MyImageIcon resize(int width, int height)
    {
        Image oldimg = this.getImage();
        Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newimg);
    }
}