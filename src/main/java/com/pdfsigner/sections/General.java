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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class General {

    private static Properties properties = new Properties();
    private static String pathFile;
    private static TextField typeTextField;
    private static TextField implementationTextField;
    private static TextField nameTextField;
    private static TextField authtypeTextField;
    private static TextField defaultkeyTextField;
    private static ChoiceBox<String> cryptotokenChoiceBox;

    public static void loadProperties(String path) throws IOException {

        FileInputStream file = new FileInputStream(path);
        properties.load(file);
        pathFile = path;
    }

    private static VBox typeBox() {

        String TYPE = properties.getProperty("WORKERGENID1.TYPE");
        Label label = new Label("Type");
        typeTextField = new TextField(TYPE);

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(label, typeTextField);

        return vbox;

    }

    private static VBox implementationBox() {

        String IMPLEMENTATION_CLASS = properties.getProperty("WORKERGENID1.IMPLEMENTATION_CLASS");
        Label label = new Label("Implementation Class");
        implementationTextField = new TextField(IMPLEMENTATION_CLASS);

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(label, implementationTextField);

        return vbox;
    }

    private static VBox nameBox() {

        String NAME = properties.getProperty("WORKERGENID1.NAME");
        Label label = new Label("Name");
        nameTextField = new TextField(NAME);

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(label, nameTextField);

        return vbox;

    }

    private static VBox authtypeBox() {

        String AUTHTYPE = properties.getProperty("WORKERGENID1.AUTHTYPE");
        Label label = new Label("Auth Type");
        authtypeTextField = new TextField(AUTHTYPE);

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(label, authtypeTextField);

        return vbox;

    }

    private static VBox cryptoTokenBox() {

        String CRYPTOTOKEN = properties.getProperty("WORKERGENID1.CRYPTOTOKEN");

        Label label = new Label("Crypto Token");

        cryptotokenChoiceBox = new ChoiceBox<>();

        cryptotokenChoiceBox.getItems().addAll(
                "CryptoTokenP12",
                "CryptoTokenP11",
                "CryptoTokenP11NG1",
                "CryptoTokenP11NG1KeyWrapping");

        cryptotokenChoiceBox.setValue(CRYPTOTOKEN);

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(label, cryptotokenChoiceBox);

        return vbox;
    }

    private static VBox defaultkeyBox() {

        String DEFAULTKEY = properties.getProperty("WORKERGENID1.DEFAULTKEY");
        Label label = new Label("Default Key");
        defaultkeyTextField = new TextField(DEFAULTKEY);

        VBox vbox = new VBox(2);
        vbox.getChildren().addAll(label, defaultkeyTextField);

        return vbox;
    }

    private static Button acceptButton() {

        Button button = new Button("Accept");
        button.setOnAction(event -> {

            properties.setProperty("WORKERGENID1.TYPE", typeTextField.getText());
            properties.setProperty("WORKERGENID1.IMPLEMENTATION_CLASS",
                    implementationTextField.getText());
            properties.setProperty("WORKERGENID1.NAME", nameTextField.getText());
            properties.setProperty("WORKERGENID1.AUTHTYPE", authtypeTextField.getText());
            properties.setProperty("WORKERGENID1.CRYPTOTOKEN",
                    cryptotokenChoiceBox.getValue().toString());
            properties.setProperty("WORKERGENID1.DEFAULTKEY", defaultkeyTextField.getText());

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
        containerbox1.getChildren().addAll(typeBox(), implementationBox(), nameBox(), authtypeBox());

        VBox containerbox2 = new VBox(5);
        containerbox2.getChildren().addAll(defaultkeyBox(), cryptoTokenBox(), authtypeBox());

        HBox container = new HBox(60);
        container.relocate(20, 20);
        container.getChildren().addAll(containerbox1, containerbox2);

        return container;

    }
}
