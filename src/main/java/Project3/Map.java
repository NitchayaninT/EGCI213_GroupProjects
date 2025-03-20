package Project3;

import javax.swing.*;

//class for each Map
class Map {
    private MyImageIcon mapIcon;
    private String mapName;
    Map(String name, MyImageIcon icon){
        this.mapName = name;
        this.mapIcon = icon;
    }

    //setter getter
    public String getMapName(){return mapName;}
    public MyImageIcon getMapIcon(){return mapIcon;}
}
//class for MapPanel
class MapPanel extends JPanel {
   private Map map;
}