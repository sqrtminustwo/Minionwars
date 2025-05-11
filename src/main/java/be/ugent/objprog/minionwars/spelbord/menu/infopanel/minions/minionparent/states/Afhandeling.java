package be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.states;

public class Afhandeling {

    private boolean moved = false;
    private boolean attacked = false;

    public boolean canRest() { return !moved && !attacked; }
    public boolean isAfgehandeld() { return moved && attacked; }
    public boolean isMoved() { return moved; }
    public boolean isAttacked() { return attacked; }

    public void reset() { moved = false; attacked = false; }
    public void setMoved() { moved = true; }
    public void setAttacked() { attacked = true; }
}