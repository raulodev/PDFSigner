package com.pdfsigner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.pdfsigner.sections.Extra;
import com.pdfsigner.sections.General;
import com.pdfsigner.sections.Props;
import com.pdfsigner.sections.Timestamp;
import com.pdfsigner.sections.Visibility;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private Tab tabGeneral(String path) throws IOException {

        Tab section = new Tab("General");
        section.setClosable(false);

        General.loadProperties(path);

        HBox controlBox = General.controlBox();
        HBox containerBox = General.containerBox();

        Pane panel = new Pane();
        panel.setPrefSize(600, 370);
        panel.getChildren().addAll(containerBox, controlBox);

        section.setContent(panel);

        return section;
    };

    private Tab tabProperties(String path) throws IOException {

        Tab section = new Tab("Properties");
        section.setClosable(false);

        Props.loadProperties(path);

        HBox controlBox = Props.controlBox();
        HBox containerBox = Props.containerBox();

        Pane panel = new Pane();
        panel.setPrefSize(600, 370);
        panel.getChildren().addAll(containerBox, controlBox);

        section.setContent(panel);

        return section;

    }

    private Tab tabVisibility(String path, Stage primaryStage) throws IOException {

        Tab section = new Tab("Visibility");
        section.setClosable(false);

        Visibility.loadProperties(path);

        HBox containerBox = Visibility.containerBox(primaryStage);
        HBox controlBox = Visibility.controlBox();

        Pane panel = new Pane();
        panel.setPrefSize(530, 450);
        panel.getChildren().addAll(containerBox, controlBox);

        section.setContent(panel);

        return section;

    }

    private Tab tabTimestamping(String path) throws IOException {

        Tab section = new Tab("Timestamping");
        section.setClosable(false);

        Timestamp.loadProperties(path);

        HBox controlBox = Timestamp.controlBox();
        HBox containerBox = Timestamp.containerBox();

        Pane panel = new Pane();
        panel.setPrefSize(600, 370);
        panel.getChildren().addAll(containerBox, controlBox);

        section.setContent(panel);

        return section;

    }

    private Tab tabExtra(String path) throws IOException {

        Tab section = new Tab("Extra");
        section.setClosable(false);

        Extra.loadProperties(path);

        HBox controlBox = Extra.controlBox();
        HBox containerBox = Extra.containerBox();

        Pane panel = new Pane();
        panel.setPrefSize(600, 370);
        panel.getChildren().addAll(containerBox, controlBox);

        section.setContent(panel);

        return section;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        String currentDir = System.getProperty("user.dir");

        Path rutaArchivo = Paths.get(currentDir, "pdfsigner.properties");

        String path = rutaArchivo.toAbsolutePath().toString();

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
                tabGeneral(path),
                tabProperties(path),
                tabVisibility(path, primaryStage),
                tabTimestamping(path),
                tabExtra(path));

        VBox contenedor = new VBox(tabPane);

        Scene scene = new Scene(contenedor, 530, 450);
        scene.getStylesheets().add(App.class.getResource("/com/pdfsigner/style.css").toExternalForm());

        Image icon = new Image(getClass().getResourceAsStream("icon.png"));

        primaryStage.setTitle("Configuration");
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    
    
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }
    }