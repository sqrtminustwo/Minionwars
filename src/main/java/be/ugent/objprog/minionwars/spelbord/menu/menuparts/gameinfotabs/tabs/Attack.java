package be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabhandler.attacktabhandler.HealHandler;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabhandler.TabHandler;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabhandler.attacktabhandler.AttackHandler;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabhandler.attacktabhandler.SpecialAttackHandler;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabparent.GameInfoTab;
import be.ugent.objprog.minionwars.spelbord.menu.infopanel.minions.minionparent.Minion;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class Attack extends GameInfoTab {

    private final VBox attackButtons;
    private final ToggleGroup attackToggleGroup;
    private final ToggleButton basisAanval;
    private final ToggleButton specialeAanval;
    private final Button heal;
    private final List<TabHandler> handlers;

    public Attack(MenuModel model) {
        super("Aanvallen", model);

        setSpacingAndAlignment();
        Label label = new Label("Selecteer een aanval uit de lijst en klik\nop een vijandelijke minion");
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);


        attackToggleGroup = new ToggleGroup();
        attackButtons = new VBox(10);
        attackButtons.setAlignment(Pos.CENTER);
        basisAanval = new ToggleButton("Basis aanval");
        specialeAanval = new ToggleButton("Speciale aanval");
        basisAanval.setToggleGroup(attackToggleGroup);
        specialeAanval.setToggleGroup(attackToggleGroup);

        VBox healButtons = new VBox(10);
        healButtons.setAlignment(Pos.CENTER);
        heal = new Button("Genees minion (+2hp)");
        healButtons.getChildren().addAll(new Label("of"), heal);

        pane.getStyleClass().add("GameInfoTab");
        tabActions.getChildren().addAll(label, attackButtons, healButtons);
        pane.getChildren().add(tabActions);

        basisAanval.setOnAction(this::handleBaseAttack);
        specialeAanval.setOnAction(this::handleSpecialAttack);
        heal.setOnAction(this::handleHeal);

        handlers = List.of(
                new AttackHandler(),
                new SpecialAttackHandler(),
                new HealHandler()
        );
    }

    private void handleBaseAttack(ActionEvent event) { handleAttack(basisAanval, false);}
    private void handleSpecialAttack(ActionEvent event) { handleAttack(specialeAanval, true); }
    private void handleAttack(ToggleButton button, boolean specialAttack) {
        for (Toggle toggle:  attackToggleGroup.getToggles()) ((Node) toggle).getStyleClass().removeAll("SelectedButton");

        if (button.isSelected()) {
            onSelected(button, specialAttack);
        } else if (attackToggleGroup.getSelectedToggle() == null) {
            onDeselected(button);
        }
    }
    public void onSelected(ToggleButton button, boolean specialAttack) {
        button.getStyleClass().add("SelectedButton");
        if (specialAttack) {
            model.setSpecialAttacking();
        } else {
            model.setAttacking();
        }
    }
    public void onDeselected(ToggleButton button) {
        button.getStyleClass().removeAll("SelectedButton");
        model.setAttackSelecting();
    }

    private void handleHeal(ActionEvent event) {
        Minion selected = model.getSelectedSpelveldMinion();
        if (selected.canHeal()) {
            model.restoreMinion(selected, 2);
            selected.incrementHealed();
            model.setAttacked();
        }
    }

    public void change() {
        attackButtons.getChildren().clear();
        List<ToggleButton> toadd = new ArrayList<>(List.of(basisAanval));
        if (model.getSelectedSpelveldMinion().getGameEffect() != null) toadd.add(specialeAanval);
        for (ToggleButton button: toadd) button.setSelected(false);
        attackButtons.getChildren().addAll(toadd);
        if (model.getSelectedSpelveldMinion().isNotAttacked()) model.setAttackSelecting();
    }

    public void skip() {
        Minion selectedMinion = model.getSelectedSpelveldMinion();
        if (selectedMinion.isNotAttacked()) {
            boolean canBaseAttack = model.canAttack();
            boolean canHeal = selectedMinion.canHeal();
            if (!canBaseAttack && !canHeal && selectedMinion.isMoved()) {
                model.setAttacked();
            }
        }
    }

    public ToggleButton getBaseAttack() { return basisAanval; }
    public ToggleButton getSpecialeAanval() { return specialeAanval; }
    public Button getHeal() { return heal; }

    @Override
    public void invalidated(Observable ignore) {
        if (model.getSelectedSpelveldMinion() != null) {
            for (TabHandler handler : handlers) {
                handler.handleUIUpdate(model);
            }
        }
    }
}