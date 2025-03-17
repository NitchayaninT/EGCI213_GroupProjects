package Project3;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.sound.sampled.*;     // for sounds

interface MyConstants{
    static final String PATH       = "src/main/java/Project3/Resource/";
    static final int FRAME_WIDTH   = 1200;
    static final int FRAME_HEIGHT  = 800;

    static final String FILE_BG    = PATH + "background.jpg";
    static final int CH_WIDTH      = 60;
    static final int CH_HEIGHT     = 50;
    static final int IT_WIDTH      = 60;
    static final int IT_HEIGHT     = 50;

    //for mp3
    static final String FILE_THEME1        = PATH + "theme1.wav";
    static final String FILE_THEME2        = PATH + "theme2.wav";
    static final String FILE_THEME3        = PATH + "theme3.wav";
    static final String FILE_THEME4        = PATH + "theme4.wav";
    static final String FILE_THEME5        = PATH + "theme5.wav";
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

// Auxiliary class to play sound effect (support .wav or .mid file)
class MySoundEffect
{
    private Clip         clip;
    private FloatControl gainControl;

    public MySoundEffect(String filename)
    {
        try
        {
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        catch (Exception e) { e.printStackTrace(); }
    }
    public void playOnce()             { clip.setMicrosecondPosition(0); clip.start(); }
    public void playLoop()             { clip.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop()                 { clip.stop(); }
    public void setVolume(float gain)
    {
        if (gain < 0.0f)  gain = 0.0f;
        if (gain > 1.0f)  gain = 1.0f;
        float dB = (float)(Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
}