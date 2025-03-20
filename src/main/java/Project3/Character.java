package Project3;

import javax.swing.*;
import java.awt.*;

// I just create this class as a place holder for the real one
class LevelUpMenu extends JPanel{
    protected MyCharacter myCharacter;

    // Constructor
    LevelUpMenu(MyCharacter mc){
        myCharacter = mc;
    }
}

abstract class BaseLabel extends JLabel{
    protected String name;
    protected MyImageIcon icon;
    protected int x , y, width, height;
    protected int speed;
    protected MapPanel mapPanel; // for putting the map Panel as reference for characters

    // Constructors
    public BaseLabel() {super();} // default constructor

    public BaseLabel(String n, int s, String file, int w, int h){
        name = n;
        speed = s;
        width = w;
        height = h;
        icon = new MyImageIcon(file).resize(width, height);
        setIcon(icon);
        setHorizontalAlignment(JLabel.CENTER);
    }

    // getter setter
    public void setName(String n) { name = n;}

    public String getName() { return name;}

    public void setMap(MapPanel m){
        mapPanel = m;
    }

    // Methods
    //      might need to modify these last part for character that always
    //      at the center of the map
    public void updateLocation() {
        // ...
        mapPanel.validate();
        mapPanel.repaint();
    }

    public void moveUp() { this.y -= speed;}

    public void moveDown() { this.y += speed;}

    public void moveLeft() { this.x += speed;}

    public void moveRight() { this.y -= speed;}
}

abstract class Character extends BaseLabel{
    // Variable
    protected int       hp;

    // Constructor
    Character(String n, int hp, int s, String file, int w, int h) {
        super(n, s, file, w, h);
        this.hp = hp;
    }

    // setter getter
    void setHp(int hp) { this.hp = hp; }
    int getHp(){return this.hp;}

    // Methods
    void takeDamage(int damage){
        // override this for player and boss
        this.hp -= damage;
        if (hp <= 0) {this.death() ;}
    }

    protected void death(){
        mapPanel.remove(this);
        mapPanel.revalidate();
        mapPanel.repaint();
    }
}

class MyCharacter extends Character {
    // Variable
    protected double        dmgMultiplier;
    protected int           exp, maxExp;
    protected int           maxHp; // Hp get from Character class
    protected int           level;
    protected Weapon        weapon;
    protected MyCharacterPanel panel;
    // Constructors
    MyCharacter(String n, int hp, int s, Weapon wp, String file, int w, int h){
        super(n, hp, s, file, w, h);
        this.maxHp = hp;
        this.hp = maxHp;
        this.weapon = wp;
        dmgMultiplier = 1;
        exp = 0;
        maxExp = 10;
        panel = new MyCharacterPanel(this);

    }
    public Weapon getWeapon(){return this.weapon;}

    void levelUp(){
        level += 1;
        LevelUpMenu lm = new LevelUpMenu(this);//
    }

    @Override
    void takeDamage( int damage){
        this.hp -= damage;
        if (hp <= 0) {this.death() ;}
        else{
            panel.healthBar.setValue(this.hp);
            revalidate();
            repaint();
        }
    }
    @Override
    public String toString(){
        return this.name;
    }
}


class MyCharacterPanel extends JPanel{
    // members
    protected MyCharacter       player;
    protected JProgressBar      healthBar;

    //

    // Constructor
    MyCharacterPanel(MyCharacter p){
        this.player = p;
        this.setSize(player.width,player.height);
        // Creating Health Bar
        healthBar = new JProgressBar(SwingConstants.HORIZONTAL, player.maxHp);
        healthBar.setValue(player.maxHp);
        healthBar.setStringPainted(true);
        healthBar.setForeground(Color.RED);
        healthBar.setBackground(Color.GRAY);

        this.add(player, BorderLayout.CENTER);
        this.add(healthBar, BorderLayout.SOUTH);
    }
}

class Monster extends Character{

    // Constructor
    Monster(String n, int hp, int s, int w, int h, String file){
        super(n, hp, s,file, w, h);
    }
}

class Boss extends Monster{
    Boss(String n, int hp, int s, int w, int h, String file){
        super(n, hp, s, w, h, file);
    }

    @Override
    protected void death(){
        //win();
    }
}