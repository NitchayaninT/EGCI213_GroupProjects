/*Akkhrawin Nair 6580013
Pakin Panawattanakul 6580043
Nitchayanin Thamkunanon 6580081
Pibhu Chitburanachart 6580195
Panupong Sangaphunchai 6580587*/
package Project3_6580081;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.sound.sampled.*;     // for sounds

interface MyConstants{
    static final String PATH       = "src/main/java/Project3_6580081/Resource/";
    static final int FRAME_WIDTH   = 1200;
    static final int FRAME_HEIGHT  = 768;
    static final String MAP_BG1     = PATH + "Maps/"+ "mapFrame/"+ "map1.png";
    static final String MAP_BG2     = PATH + "Maps/"+ "mapFrame/"+ "map2.png";
    static final String MAP_BG3     = PATH + "Maps/"+ "mapFrame/"+ "map3.png";
    static final String MAP_BG4     = PATH + "Maps/"+ "mapFrame/"+ "map4.png";
    static final String MAP_BG5     = PATH + "Maps/"+ "mapFrame/"+ "map5.png";
    static final String FILE_BG    = PATH + "background2.jpg";
    static final String FILE_FOREST    = PATH + "forest2.JPG";

    //mapicon

    //for weapon pictures
    static final String FILE_WEAPON0    = PATH + "Weapon/weapon0.png";
    static final String FILE_WEAPON1    = PATH + "Weapon/weapon1.png";
    static final String FILE_WEAPON2    = PATH + "Weapon/weapon2.png";
    static final String FILE_WEAPON3    = PATH + "Weapon/weapon3.png";
    static final String FILE_WEAPON4    = PATH + "Weapon/weapon4.png";
    //for character pictures
    static final int BG_WIDTH = 4272;
    static final int BG_HEIGHT = 2800;

    static final int MON_HP = 1;
    static final String FILE_YOURCHARBOX    = PATH + "yourcharacter.JPG";
    static final String FILE_CHAR0    = PATH + "MyCharacters/hope.png";
    static final String FILE_CHAR1    = PATH + "MyCharacters/phil.png";
    static final String FILE_CHAR2    = PATH + "MyCharacters/ninny.PNG";
    static final String FILE_CHAR3    = PATH + "MyCharacters/p.png";
    static final String FILE_CHAR4    = PATH + "MyCharacters/tony.png";


    //for mapIcon
    static final String FILE_MAP0   = PATH + "Maps/"+ "mapIcon/"+ "map01.jpg";
    static final String FILE_MAP1   = PATH + "Maps/"+ "mapIcon/"+ "map02.jpg";
    static final String FILE_MAP2   = PATH + "Maps/"+ "mapIcon/"+ "map03.jpg";
    static final String FILE_MAP3   = PATH + "Maps/"+ "mapIcon/"+ "map04.jpg";
    static final String FILE_MAP4   = PATH + "Maps/"+ "mapIcon/"+ "map05.jpg";


    //for monster pictures
    static final String FILE_AJ0        = PATH + "Monsters/"+ "Tanasanee.png";
    static final String FILE_AJ1        = PATH + "Monsters/"+ "Mingmanas.png";
    static final String FILE_AJ2        = PATH + "Monsters/"+ "Thanadol.png";
    static final String FILE_AJ3        = PATH + "Monsters/"+ "Sumeth.png";
    static final String FILE_AJ4        = PATH + "Monsters/"+ "Konglit.png";
    static final String FILE_AJ5        = PATH + "Monsters/"+ "Lalita.png";
    static final String FILE_AJ6        = PATH + "Monsters/"+ "Suratos.png";
    static final String FILE_AJ7        = PATH + "Monsters/"+ "Paisarn.png";
    static final String FILE_AJ8        = PATH + "Monsters/"+ "Narit.png";
    static final String FILE_AJ9        = PATH + "Monsters/"+ "Vasin.png";
    static final String FILE_AJ10       = PATH + "Monsters/"+ "Konlakorn.png";
    static final String FILE_AJ11       = PATH + "Monsters/"+ "Noppadol.png";
    static final String FILE_AJ12       = PATH + "Monsters/"+ "Karin.png";
    static final String FILE_AJ13       = PATH + "Monsters/"+ "Rangsipan.png";


    static final int MON_WIDTH         = 70;
    static final int MON_HEIGHT        = 70;
    static final int BOSS_WIDTH         = 200;
    static final int BOSS_HEIGHT        = 200;

    //will change this
    static final int IT_WIDTH      = 60;
    static final int IT_HEIGHT     = 50;

    static final int CH_WIDTH = 50;
    static final int CH_HEIGHT = 50;

    //for music
    static final String FILE_THEME        = PATH + "Music/theme.wav";
    static final String FILE_THEME0       = PATH + "Music/theme0.wav";
    static final String FILE_THEME1       = PATH + "Music/theme1.wav";
    static final String FILE_THEME2       = PATH + "Music/theme2.wav";
    static final String FILE_THEME4       = PATH + "Music/theme4.wav";

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