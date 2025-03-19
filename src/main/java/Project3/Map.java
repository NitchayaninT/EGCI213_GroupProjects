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
//class for MapFrame
class MapFrame extends JPanel {
   private Map map;
}