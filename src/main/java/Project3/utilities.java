package Project3;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.sound.sampled.*;     // for sounds

interface MyConstants{
    static final String PATH       = "src/main/java/Project3/Resource/";
    static final int FRAME_WIDTH   = 1200;
    static final int FRAME_HEIGHT  = 800;
    static final String FILE_BG    = PATH + "background.jpg";
    static final String MAP_BG     = PATH + "map.png";
    //for weapon pictures
    static final String FILE_WEAPON0    = PATH + "weapon0.png";
    //for character pictures
    static final String FILE_CHAR0    = PATH + "phil.png";
    static final int BG_WIDTH = 4272;
    static final int BG_HEIGHT = 2800;
    
    static final int MON1_HP = 100;

    //for monster pictures
    static final String FILE_AJ0        = PATH + "Tanasanee.png";
    static final String FILE_AJ1        = PATH + "Mingmanas.png";
    static final String FILE_AJ2        = PATH + "Thanadol.png";
    static final String FILE_AJ3        = PATH + "Sumeth.png";
    static final String FILE_AJ4        = PATH + "Konglit.png";
    static final String FILE_AJ5        = PATH + "Lalita.png";
    static final String FILE_AJ6        = PATH + "Suratos.png";
    static final String FILE_AJ7        = PATH + "Paisarn.png";
    static final String FILE_AJ8        = PATH + "Narit.png";
    static final String FILE_AJ9        = PATH + "Vasin.png";
    static final String FILE_AJ10       = PATH + "Konlakorn.png";
    static final String FILE_AJ11       = PATH + "Noppadol.png";
    static final String FILE_AJ12       = PATH + "Karin.png";
    static final String FILE_AJ13       = PATH + "Rangsipan.png";

    static final int MON1_WIDTH         = 15;
    static final int MON1_HEIGHT        = 15;
    static final int MON2_WIDTH         = 20;
    static final int MON2_HEIGHT        = 20;
    static final int BOSS_WIDTH         = 100;
    static final int BOSS_HEIGHT        = 100;

    //will change this
    static final int CH_WIDTH      = 30;
    static final int CH_HEIGHT     = 30;
    static final int IT_WIDTH      = 60;
    static final int IT_HEIGHT     = 50;
    
    static final int PL_WIDTH = 50;
    static final int PL_HEIGHT = 50;

    //for music
    static final String FILE_THEME1        = PATH + "theme.wav";
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
    public void playLoop()             {
            if (clip != null) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Infinite loop
                clip.start();
            }
            else {
                System.err.println("Cannot find clip");
            }
        //clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    public void stop()                 { clip.stop(); }
    public void setVolume(float gain)
    {
        if (gain < 0.0f)  gain = 0.0f;
        if (gain > 1.0f)  gain = 1.0f;
        float dB = (float)(Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
}