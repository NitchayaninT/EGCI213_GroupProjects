package Project3;

import javax.swing.*;

//class for each MapFrame
class MapFrame extends JFrame {
    private MyImageIcon mapIcon;
    private String mapName;
    MapFrame(String name, MyImageIcon icon){
        this.mapName = name;
        this.mapIcon = icon;
    }

    //setter getter
    public String getMapName(){return mapName;}
    public MyImageIcon getMapIcon(){return mapIcon;}
}
//class for MapPanel
class MapPanel extends JPanel {
   private MapFrame mapFrame;
}