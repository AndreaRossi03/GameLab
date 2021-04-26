package dev.gamelab;

import dev.gamelab.profili.GestoreProfili;
import dev.gamelab.paginainiziale.PaginaInizialeController;
import dev.gamelab.profili.Profilo;
import dev.gamelab.util.Utilita;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class GameLab extends Application {

    /**
     * Instanza unica della classe per essere chiamata globalmente
     */
    private static GameLab instance;

    private Stage mainStage;
    private GestoreProfili gestoreProfili;

    /**
     * Il profilo della sessione corrente
     */
    private Profilo profiloSessione;

    /**
     * Costruttore della classe
     * permette di creare solamente una instanza di questa classe
     */
    public GameLab() {
        super();
        synchronized (GameLab.class) {
            if (instance != null) {
                throw new UnsupportedOperationException("Questa classe non può essere chiamata più di una volta!");
            }

            instance = this;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.gestoreProfili = new GestoreProfili();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/PaginaIniziale.fxml"));
        Parent root = loader.load();

        Utilita.creaStageMobile(
                primaryStage,
                ((PaginaInizialeController) loader.getController()).getBarra()
        );

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/scene/Stile.css").toExternalForm());
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        mainStage = primaryStage;
    }

    /**
     * Salva la configurazione in config.json
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        gestoreProfili.salvaConfigurazione();
    }

    public void cambiaScena(String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        getMainStage().getScene().setRoot(root);
    }

    public void creaSessione(Profilo profilo) {
        this.profiloSessione = profilo;
    }

    public Profilo getProfiloSessione() {
        return profiloSessione;
    }

    public static GameLab getInstance() {
        return instance;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public GestoreProfili getGestoreProfili() {
        return gestoreProfili;
    }
}
