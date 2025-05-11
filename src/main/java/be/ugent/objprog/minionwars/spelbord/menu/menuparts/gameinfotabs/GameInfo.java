package be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.buildgameinfo.BuildFieldInfo;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.buildgameinfo.BuildGameInfo;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.buildgameinfo.BuildMinionInfo;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.buildgameinfo.BuildOwnMinionInfo;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.tabparent.GameInfoTab;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.Attack;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.Bonus;
import be.ugent.objprog.minionwars.spelbord.menu.menuparts.gameinfotabs.tabs.Move;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class GameInfo extends VBox implements InvalidationListener {

    private final MenuModel model;
    private final TabPane acties;
    private final Tabs tabs;
    private final List<BuildGameInfo> builders;

    public record Tabs(Move move, Attack attack, Bonus bonus) {
        public List<GameInfoTab> getMainTabs() { return List.of(move, attack); }
    }

    public GameInfo(MenuModel model, int width, int margin) {
        this.model = model;
        model.addListener(this);
        width -= 2 * margin;
        setPrefWidth(width);
        setMinWidth(width);

        acties = new TabPane();
        acties.setPrefWidth(width);
        acties.setMinWidth(width);
        tabs = new Tabs(new Move(model), new Attack(model), new Bonus(model));
        acties.getTabs().addAll(tabs.move, tabs.attack, tabs.bonus);
        builders = List.of(
                new BuildMinionInfo(),
                new BuildFieldInfo(),
                new BuildOwnMinionInfo()
        );

        VBox.setVgrow(acties, Priority.ALWAYS);
    }

    @Override
    public void invalidated(Observable ignore) {
        skipTabs();
        if (model.isChangingSelection() || model.isChangingPlayer()) {
            getChildren().clear();
            for (BuildGameInfo builder : builders) {
                builder.build(model, this);
            }
        }
    }

    public void skipTabs() {
        if (model.getSelectedSpelveldMinion() != null) {
            for (GameInfoTab tab : tabs.getMainTabs()) tab.skip();
        }
    }

    public TabPane getActies() { return acties; }
    public Tabs getTabs() { return tabs; }
}