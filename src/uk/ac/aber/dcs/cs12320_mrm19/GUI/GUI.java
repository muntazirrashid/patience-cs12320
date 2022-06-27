package uk.ac.aber.dcs.cs12320_mrm19.GUI;


import uk.ac.aber.dcs.cs12320_mrm19.App;

public abstract class GUI {
    App app;

    /**
     * @param choice user inputs
     */
    public abstract void input(String choice);

    /**
     * Command Options
     */
    public abstract void menuList();

    /**
     * GUI Screen - Updates
     */
    public abstract void screenUpdate();



}
