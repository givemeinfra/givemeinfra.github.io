package prefuse.demosRadial.applets;

import prefuse.util.ui.JPrefuseApplet;


public class RadialGraphView extends JPrefuseApplet {

    public void init() {
        this.setContentPane(
            prefuse.demosRadial.RadialGraphView.demo("/socialnet.xml", "name"));
    }
    
} // end of class RadialGraphView
