package com.pdfsigner.sections;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Timestamp {
    private static Properties properties = new Properties();
    private static String pathFile;
    private static TextField TsaWorkerTextField;
    private static TextField TsaUrlTextField;
    private static TextField TsaDigestalgorithmTextField;
    private static TextField TsaUsernameTextField;
    private static TextField TsaPasswordTextField;

    public static void loadProperties(String path) throws IOException {

        FileInputStream file = new FileInputStream(path);
        properties.load(file);
        pathFile = path;
    }

    private static ImageView InfoIcon() {
        Image icon = new Image(Timestamp.class.getResource("/com/pdfsigner/info.png").toExternalForm());
        ImageView imageView = new ImageView(icon);
        return imageView;
    }

    private static VBox tsaWorkerBox() {

        String TSA_WORKER = properties.getProperty("WORKERGENID1.TSA_WORKER");

        Tooltip tooltip = new Tooltip(
                "If we want to timestamp the document\n" +
                        "signature , specify the timestamp authority URL,\n" +
                        "if necessary, specify the tsa username and password\n" +
                        "with appropriate values\n\n" +
                        "Note: If we do not want to stamp the date and time \n" +
                        "of the document signing, do not set the property.");

        tooltip.setShowDuration(new Duration(20000));

        Label label = new Label("Tsa worker");
        label.setGraphic(InfoIcon());
        label.setTooltip(tooltip);

        TsaWorkerTextField = new TextField(TSA_WORKER);

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(label, TsaWorkerTextField);

        return vbox;

    }

    private static VBox tsaUrlBox() {

        String TSA_URL = properties.getProperty("WORKERGENID1.TSA_URL");

        Tooltip tooltip = new Tooltip(
                "URL of external timestamp authority\n\n" +
                        "Note : if path contains characters \"\\\" or \"=\" ,\n" +
                        "these characters should be escaped (thus \"\\\" = \"\\\", \"=\" =>\"\\=\" )" +
                        "\ndefault is not set (no timestamping)");

        tooltip.setShowDuration(new Duration(20000));

        Label label = new Label("Tsa url");
        label.setGraphic(InfoIcon());
        label.setTooltip(tooltip);

        TsaUrlTextField = new TextField(TSA_URL);

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(label, TsaUrlTextField);

        return vbox;

    }

    private static VBox tsaDigestalgorithmBox() {

        String TSA_DIGESTALGORITHM = properties.getProperty("WORKERGENID1.TSA_DIGESTALGORITHM");
        Label label = new Label("Digest algorithm");
        TsaDigestalgorithmTextField = new TextField(TSA_DIGESTALGORITHM);

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(label, TsaDigestalgorithmTextField);

        return vbox;

    }

    private static VBox tsaUsernameBox() {

        String TSA_USERNAME = properties.getProperty("WORKERGENID1.TSA_USERNAME");

        Label label = new Label("Username");

        TsaUsernameTextField = new TextField(TSA_USERNAME);

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(label, TsaUsernameTextField);

        return vbox;

    }

    private static VBox tsaPasswordBox() {

        String TSA_PASSWORD = properties.getProperty("WORKERGENID1.TSA_PASSWORD");

        Label label = new Label("Password");
        TsaPasswordTextField = new TextField(TSA_PASSWORD);

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(label, TsaPasswordTextField);

        return vbox;

    }

    private static Button cancelButton() {

        Button button = new Button("Cancel");
        button.setOnAction(e -> {
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        });

        return button;

    }

    private static Button acceptButton() {

        Button button = new Button("Accept");
        button.setOnAction(event -> {

            properties.setProperty("WORKERGENID1.TSA_WORKER", TsaWorkerTextField.getText());

            properties.setProperty("WORKERGENID1.TSA_URL", TsaUrlTextField.getText());

            properties.setProperty("WORKERGENID1.TSA_DIGESTALGORITHM", TsaDigestalgorithmTextField.getText());

            properties.setProperty("WORKERGENID1.TSA_USERNAME", TsaUsernameTextField.getText());

            properties.setProperty("WORKERGENID1.TSA_PASSWORD", TsaPasswordTextField.getText());

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

    public static HBox controlBox() {
        HBox control = new HBox(10);
        control.relocate(337, 375);
        control.getChildren().addAll(acceptButton(), cancelButton());

        return control;
    }

    public static HBox containerBox() {

        Tooltip tooltip = new Tooltip(
                "If Tsa requires authentication for\n" +
                        "timestamping , specify username and password.\n" +
                        "If Tsa does not require authentication,\n" +
                        "do not set these properties");

        tooltip.setShowDuration(new Duration(20000));

        Label label = new Label("Authentication");
        label.setGraphic(InfoIcon());
        label.setTooltip(tooltip);
        label.setId("primary-label");

        VBox containerbox1 = new VBox(5);
        containerbox1.getChildren().addAll(tsaWorkerBox(), tsaUrlBox(), tsaDigestalgorithmBox());
        VBox containerbox2 = new VBox(5);
        containerbox2.getChildren().addAll(label, tsaUsernameBox(), tsaPasswordBox());

        HBox container = new HBox(60);
        container.getChildren().addAll(containerbox1, containerbox2);
        container.relocate(20, 20);

        return container;
    }

}