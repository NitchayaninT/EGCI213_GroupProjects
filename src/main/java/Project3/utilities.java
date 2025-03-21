package Project3;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.sound.sampled.*;     // for sounds

interface MyConstants{
    static final String PATH       = "src/main/java/Project3/Resource/";
    static final int FRAME_WIDTH   = 1200;
    static final int FRAME_HEIGHT  = 800;
    static final String FILE_BG    = PATH + "background2.jpg";
    static final String FILE_FOREST    = PATH + "forest.JPG";

    //for weapon pictures
    static final String FILE_WEAPON0    = PATH + "weapon0.png";
    static final String FILE_WEAPON1    = PATH + "weapon1.png";
    static final String FILE_WEAPON2    = PATH + "weapon2.png";
    static final String FILE_WEAPON3    = PATH + "weapon3.png";
    static final String FILE_WEAPON4    = PATH + "weapon4.png";
    //for character pictures
    static final String FILE_YOURCHARBOX    = PATH + "yourcharacter.JPG";
    static final String FILE_CHAR0    = PATH + "MyCharacters/hope.png";
    static final String FILE_CHAR1    = PATH + "MyCharacters/phil.png";
    static final String FILE_CHAR2    = PATH + "MyCharacters/ninny.PNG";
    static final String FILE_CHAR3    = PATH + "MyCharacters/p.png";
    static final String FILE_CHAR4    = PATH + "MyCharacters/tony.png";


    //for MapFrame pictures
    static final String FILE_MAP0   = PATH + "Maps/"+ "map1.JPG";
    static final String FILE_MAP1   = PATH + "Maps/"+ "map2.JPG";
    static final String FILE_MAP2   = PATH + "Maps/"+ "map3.JPG";
    static final String FILE_MAP3   = PATH + "Maps/"+ "map4.JPG";
    static final String FILE_MAP4   = PATH + "Maps/"+ "map5.JPG";
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

    //for music
    static final String FILE_THEME        = PATH + "Music/theme.wav";
    static final String FILE_THEME0       = PATH + "Music/theme0.wav";
    static final String FILE_THEME1       = PATH + "Music/theme1.wav";
    static final String FILE_THEME2       = PATH + "Music/theme2.wav";
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