package be.ugent.objprog.minionwars.startscherm.input;

import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.powers.powerparent.Power;
import be.ugent.objprog.minionwars.startscherm.input.inputparent.Input;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class Speler extends Input {

    private String naam;
    private int munten;
    private final Integer nummer;
    private final List<Minion> minions;
    private final List<Power> usedPowers;
    private boolean placed;

    public Speler(int nummer) {
        super("Speler", new Label("Speler " + nummer), "De naam mag niet leeg zijn");
        this.nummer = nummer-1;
        placed = false;
        minions = new ArrayList<>();
        usedPowers = new ArrayList<>();
    }

    public boolean isAfgehandeld() { return getAantalAfgehandeld() >= getAantalMinions(); }
    public boolean isPlaced() { return placed; }
    public boolean isOwnerOf(Minion minion) { return minions.contains(minion); }
    public boolean canUsePower(Power power) { return usedPowers.size() < 2 && !usedPowers.contains(power); }

    public String getNaam() { return naam; }
    public int getMunten() { return munten; }
    public int getNummer() { return nummer; }

    public void setPlaced(boolean placed) { this.placed = placed; }
    public void setMunten(int munten) { this.munten = munten; }
    public void usedPower(Power power) { usedPowers.add(power); }
    public void addMinion(Minion minion) { minions.add(minion); }
    public void removeMinion(Minion minion) { minions.remove(minion); }
    public List<Minion> getMinions() { return minions; }
    public int getAantalMinions() { return minions.size(); }
    public int getAantalAfgehandeld() { return Math.toIntExact(minions.stream().filter(Minion::isAfgehandeld).count()); }

    public boolean notValid() {
        naam = getInput().getText();
        return naam == null || naam.isEmpty();
    }
}