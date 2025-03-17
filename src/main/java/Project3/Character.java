package Project3;

import javax.swing.*;

abstract class Character extends JLabel {
    protected String   name;
    protected int       hp;
    protected int       speed;
    protected int       width;

    public void setHp(int h)        {this.hp=h;};
    public void setSpeed(int s)     {this.speed=s;}
    public void setWidth(int w)     {this.width = w;}

    // intersect

}

class Monster extends Character{

}

class Boss extends Monster{

}

class Player extends Character{
    //MyImageIcon
    Weapon w;


}

