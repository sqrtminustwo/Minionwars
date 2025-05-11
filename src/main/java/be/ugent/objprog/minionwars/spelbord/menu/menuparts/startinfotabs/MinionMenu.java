package be.ugent.objprog.minionwars.spelbord.menu.menuparts.startinfotabs;

import be.ugent.objprog.minionwars.spelbord.menu.MenuModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MinionMenu extends ScrollPane {
    public MinionMenu(MenuModel model, int width, int margin) {
        width = width - 2 * margin;
        setMinWidth(width);
        setPrefWidth(width);
        VBox holder = new VBox();
        holder.setMinWidth(width);
        holder.setPrefWidth(width);
        holder.getChildren().addAll(model.getMinions());
        setContent(holder);
    }
}