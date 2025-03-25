/*Akkhrawin Nair 6580013
Pakin Panawattanakul 6580043
Nitchayanin Thamkunanon 6580081
Pibhu Chitburanachart 6580195
Panupong Sangaphunchai 6580587*/
package Project3_6580081;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class MapPanel extends JPanel {
    //size of panel that we are going to map to frame
    private static final int DIM_W = MyConstants.FRAME_WIDTH;
    private static final int DIM_H = MyConstants.FRAME_HEIGHT;
    //where background will be drawn on the panel, basically where you want to image to be drawn
    private int dx1,dx2,dy1,dy2;
    //the part of the bg image to be drawn
    private int srcx1,srcx2,srcy1,srcy2;
    //actual size of map
    private int IMAGE_WIDTH = MyConstants.BG_WIDTH;
    private int IMAGE_HEIGHT = MyConstants.BG_HEIGHT;
    //background image and myCharacter obj
    private BufferedImage backgroundImg;
    private MyCharacter MyCharacter;
    private MapFrame mf;
    public int getsrcx1(){return srcx1;}
    public int getsrcx2(){return srcx2;}
    public int getsrcy1(){return srcy1;}
    public int getsrcy2(){return srcy2;}
    public MapPanel(MyCharacter MyCharacter,MapFrame map)
    {
        //import background image
        mf = map;
        String mapName = mf.getMapName();
        String background = "";
        switch(mapName)
        {
            case "Desert":
                background = MyConstants.MAP_BG1;
                break;
            case "Galaxy":
                background = MyConstants.MAP_BG2;
                break;
            case "Sky":
                background = MyConstants.MAP_BG3;
                break;
            case "Hell":
                background = MyConstants.MAP_BG4;
                break;
            case "Grass":
                background = MyConstants.MAP_BG5;
                break;
        }
        try{
            backgroundImg = ImageIO.read(new File(background));
            repaint();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        initImagePoints();
        this.MyCharacter = MyCharacter;
    }
    private void initImagePoints()
    {
        dx1 =0;
        dy1 =0;
        dx2 = DIM_W;
        dy2 = DIM_H;
        srcx1 = (IMAGE_WIDTH/2)-(DIM_W/2);
        srcy1 = (IMAGE_HEIGHT/2)-(DIM_H/2);
        srcx2 = (IMAGE_WIDTH/2)+(DIM_W/2);
        srcy2 = (IMAGE_HEIGHT/2)+(DIM_H/2);
    }
    public void moveRight()
    {
        if (srcx2 + MyCharacter.getSpeedX() <= IMAGE_WIDTH)
        {
            srcx1 += MyCharacter.getSpeedX();
            srcx2 += MyCharacter.getSpeedX();
            for (Monster monster : mf.monsterArrayList) {
                monster.setX(monster.getX() - MyCharacter.getSpeedX());
                monster.setY(monster.getY());
            }
        }
    }
    public void moveLeft()
    {
        if (srcx1 - MyCharacter.getSpeedX() >= 0)
        {
            srcx1 -= MyCharacter.getSpeedX();
            srcx2 -= MyCharacter.getSpeedX();
            for (Monster monster : mf.monsterArrayList) {
                monster.setX(monster.getX() + MyCharacter.getSpeedX());
                monster.setY(monster.getY());
            }
        }
    }

    public void moveUp()
    {
        if (srcy1 - MyCharacter.getSpeedY() >= 0)
        {
            srcy1 -= MyCharacter.getSpeedY();
            srcy2 -= MyCharacter.getSpeedY();
            for (Monster monster : mf.monsterArrayList) {
                monster.setX(monster.getX());
                monster.setY(monster.getY()+MyCharacter.getSpeedY());
            }
        }
    }

    public void moveDown()
    {
        if (srcy2 + MyCharacter.getSpeedY() <= IMAGE_HEIGHT)
        {
            srcy1 += MyCharacter.getSpeedY();
            srcy2 += MyCharacter.getSpeedY();
            for (Monster monster : mf.monsterArrayList) {
                monster.setX(monster.getX());
                monster.setY(monster.getY()-MyCharacter.getSpeedY());
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        //clear background by calling default implementation
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        //draw a filled white rectangle on the panel
        g.fillRect(0,0,getWidth(),getHeight());
        //draw the image
        g.drawImage(backgroundImg, dx1, dy1, dx2, dy2, srcx1, srcy1, srcx2, srcy2, this);
    }

}