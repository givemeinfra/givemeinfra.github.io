package prefuse.demosRadial.applets;

import prefuse.util.ui.JPrefuseApplet;


public class TreeMap extends JPrefuseApplet {

    public void init() {
        this.setContentPane(
            prefuse.demosRadial.TreeMap.demo("/chi-ontology.xml.gz", "name"));
    }
    
} // end of class TreeMap
