package prefuse.demosTree.applets;

import prefuse.util.ui.JPrefuseApplet;


public class TreeView extends JPrefuseApplet {

    public void init() {
        this.setContentPane(
            prefuse.demosTree.TreeView.demo("C:/Analisador/TreeViewRastreabilidadeComponente.xml", "name"));
    }
    
} // end of class TreeView
