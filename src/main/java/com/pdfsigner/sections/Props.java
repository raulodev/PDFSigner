package com.pdfsigner.sections;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Props {

    private static Properties properties = new Properties();
    private static String pathFile;
    private static TextField reasonTextField;
    private static TextField locationTextField;
    private static ChoiceBox<String> digestalgorithmChoiceBox;

    public static void loadProperties(String path) throws IOException {

        FileInputStream file = new FileInputStream(path);
        properties.load(file);
        pathFile = path;
    }

    private static ImageView InfoIcon() {
        Image icon = new Image(Props.class.getResource("/com/pdfsigner/info.png").toExternalForm());
        ImageView imageView = new ImageView(icon);
        return imageView;
    }

    private static VBox reasonBox() {

        String REASON = properties.getProperty("WORKERGENID1.REASON");

        Label label = new Label("Reason");

        reasonTextField = new TextField(REASON);

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(label, reasonTextField);

        return vbox;
    }

    private static VBox digestalgorithmBox() {

        String DIGESTALGORITHM = properties.getProperty("WORKERGENID1.DIGESTALGORITHM");

        Tooltip tooltip = new Tooltip(
                "Digest algorithm used for the message \ndigest and signature (this is optional \nand defaults to SHA256) the algorithm\ndetermines the minimum PDF version \nof the resulting document and is \ndocumented in the manual. for DSA keys, \nonly SHA1 is supported");
        tooltip.setShowDuration(new Duration(90000));

        Label label = new Label("Digest Algorithm");
        label.setGraphic(InfoIcon());

        label.setTooltip(tooltip);

        digestalgorithmChoiceBox = new ChoiceBox<>();
        digestalgorithmChoiceBox.getItems().addAll("SHA256", "SHA1");
        digestalgorithmChoiceBox.setValue(DIGESTALGORITHM);

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(label, digestalgorithmChoiceBox);

        return vbox;
    }

    private static VBox locationBox() {

        String LOCATION = properties.getProperty("WORKERGENID1.LOCATION");

        Tooltip tooltip = new Tooltip(
                "Specify location. it will be displayed \nin signature properties when \nviewed default is \"SignServer\"");

        tooltip.setShowDuration(new Duration(20000));

        Label label = new Label("Location");

        label.setTooltip(tooltip);

        locationTextField = new TextField(LOCATION);

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(label, locationTextField);

        return vbox;

    }

    private static Button acceptButton() {

        Button button = new Button("Accept");
        button.setOnAction(event -> {

            properties.setProperty("WORKERGENID1.REASON", reasonTextField.getText());
            properties.setProperty("WORKERGENID1.DIGESTALGORITHM", digestalgorithmChoiceBox.getValue().toString());
            properties.setProperty("WORKERGENID1.LOCATION", locationTextField.getText());

            FileOutputStream out;
            try {
                out = new FileOutputStream(pathFile);

                try {
                    properties.store(out, "DEFAULTKEY");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        });

        return button;

    }

    private static Button cancelButton() {

        Button button = new Button("Cancel");
        button.setOnAction(e -> {
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        });

        return button;

    }

    public static HBox controlBox() {

        HBox control = new HBox(10);
        control.relocate(337, 375);
        control.getChildren().addAll(acceptButton(), cancelButton());

        return control;
    }

    public static HBox containerBox() {

        VBox containerbox1 = new VBox(5);
        containerbox1.getChildren().addAll(reasonBox(), digestalgorithmBox());

        VBox containerbox2 = new VBox(5);
        containerbox2.getChildren().addAll(locationBox());

        HBox container = new HBox(60);
        container.relocate(20, 20);
        container.getChildren().addAll(containerbox1, containerbox2);

        return container;

    }
}
