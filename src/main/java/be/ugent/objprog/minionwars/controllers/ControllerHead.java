package be.ugent.objprog.minionwars.controllers;

import be.ugent.objprog.minionwars.MinionWars;
import javafx.scene.Parent;
import org.jdom2.Element;

public abstract class ControllerHead {

    protected MinionWars application;

    public abstract Parent start();
    public void nextFase() {}

    public void setApplication(MinionWars application) {
        this.application = application;
    }
    public void setConfig(Element root) {}

}