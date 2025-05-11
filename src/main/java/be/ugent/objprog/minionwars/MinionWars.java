package be.ugent.objprog.minionwars;

import be.ugent.objprog.minionwars.controllers.ControllerHead;
import be.ugent.objprog.minionwars.controllers.EindSchermController;
import be.ugent.objprog.minionwars.controllers.SpelbordController;
import be.ugent.objprog.minionwars.controllers.StartSchermController;
import be.ugent.objprog.minionwars.startscherm.input.Speler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MinionWars extends Application {

    private Stage stage;
    private String configPath;

    @Override
    public void start(Stage stage) {
        configPath = getParameters().getRaw().getFirst();
        this.stage = stage;
        startScherm();
    }

    public void startScherm() {
        ControllerHead controller = new StartSchermController();
        initializeFase(controller, "startscherm");
        setupStage(600, 600, 400, 400, false);
        showStage();
    }

    public void spelBord(List<Speler> spelers) throws IOException, JDOMException {
        ControllerHead spelbord = new SpelbordController(spelers);
        Document document = loadConfigDocument();
        spelbord.setConfig(document.getRootElement());
        initializeFase(spelbord, "spelbord");
        setupStage(1300, 700, 600, 700, true);
        showStage();
    }

    public void eindScherm(Speler winner) {
        ControllerHead controller = new EindSchermController(winner);
        initializeFase(controller, "eindscherm");
        setupStage(600, 600, 400, 400, false);
        showStage();
    }

    private void initializeFase(ControllerHead spelbord, String css) {
        spelbord.setApplication(this);
        Parent root = spelbord.start();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                Objects.requireNonNull(MinionWars.class.getResource("styles/" + css + ".css")).toExternalForm()
        );
        stage.setScene(scene);
        stage.setTitle("Minion Wars");
    }

    private Document loadConfigDocument() throws JDOMException, IOException {
        File configFile = new File(configPath).getAbsoluteFile();
        if (!configFile.exists()) {
            throw new FileNotFoundException("Config bestand niet gevonden: " + configFile.getAbsolutePath());
        }
        return new SAXBuilder().build(configFile);
    }

    private void setupStage(int width, int height, int minWidth, int minHeight, boolean resizable) {
        setResolution(width, height);
        setMinResolution(minWidth, minHeight);
        stage.setResizable(resizable);
    }

    public void setResolution(int width, int height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }

    public void setMinResolution(int minwidth, int minheight) {
        stage.setMinWidth(minwidth);
        stage.setMinHeight(minheight);
    }

    public void showStage() {
        stage.show();
        stage.getScene().getWindow().centerOnScreen();
    }

    public void endGame() { Platform.exit(); }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Gebruik: MinionWars <config bestand>");
            Platform.exit();
            return;
        }

        try {
            File configFile = new File(args[0]).getAbsoluteFile();
            if (!configFile.exists()) {
                throw new FileNotFoundException("Config bestand niet gevonden: " + configFile.getAbsolutePath());
            }
            launch(args);
        } catch (Exception e) {
            System.err.println("Fout bij laden van " + args[0] + ":");
            System.err.println(e.getMessage());
            Platform.exit();
        }
    }
}