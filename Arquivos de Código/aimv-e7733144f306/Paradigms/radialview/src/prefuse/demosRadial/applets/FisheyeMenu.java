package prefuse.demosRadial.applets;

import prefuse.util.ui.JPrefuseApplet;


public class FisheyeMenu extends JPrefuseApplet {
    
    public void init() {
        prefuse.demosRadial.FisheyeMenu fm = prefuse.demosRadial.FisheyeMenu.demo();
        this.getContentPane().add(fm);
        fm.getVisualization().run("init");
    }
    
} // end of class FisheyeMenu
