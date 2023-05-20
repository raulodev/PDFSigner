package com.pdfsigner.sections;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Extra {
    private static Properties properties = new Properties();
    private static String pathFile;
    private static CheckBox embedCrlCheckBox;
    private static CheckBox embedOcspResponseCheckBox;
    private static CheckBox disableKeyUsagecounterCheckBox;

    public static void loadProperties(String path) throws IOException {

        FileInputStream file = new FileInputStream(path);
        properties.load(file);
        pathFile = path;
    }

    private static ImageView InfoIcon() {
        Image icon = new Image( Extra.class.getResource("/com/pdfsigner/info.png").toExternalForm());
        ImageView imageView = new ImageView(icon);
        return imageView;
    }

    private static CheckBox embedCrlBox() {

        String EMBED_CRL = properties.getProperty("WORKERGENID1.EMBED_CRL");
        boolean isSelect = Boolean.parseBoolean(EMBED_CRL);

        embedCrlCheckBox = new CheckBox("Embed the crl");
        embedCrlCheckBox.setIndeterminate(false);
        embedCrlCheckBox.setSelected(isSelect);
        embedCrlCheckBox.setGraphic(InfoIcon());
        embedCrlCheckBox.setTooltip(new Tooltip(
                "If we want to embed the crl \nfor the signer's certificate inside \nthe set of signature packages"));

        return embedCrlCheckBox;

    }

    private static CheckBox embedOcspResponseBox() {

        String EMBED_OCSP_RESPONSE = properties.getProperty("WORKERGENID1.EMBED_OCSP_RESPONSE");
        boolean isSelect = Boolean.parseBoolean(EMBED_OCSP_RESPONSE);

        embedOcspResponseCheckBox = new CheckBox("Embed the ocsp response");
        embedOcspResponseCheckBox.setIndeterminate(false);
        embedOcspResponseCheckBox.setSelected(isSelect);
        embedOcspResponseCheckBox.setGraphic(InfoIcon());
        embedOcspResponseCheckBox.setTooltip(new Tooltip(
                "If we want to embed the ocsp response \n" +
                        "for the signer's certificate inside the \nsignature package.\n\n" +
                        "Note: The issuer certificate \n(of the signing certificate) must be in the \ncertificate chain."));

        return embedOcspResponseCheckBox;

    }

    private static CheckBox disableKeyUsageCounterBox() {

        String DISABLEKEYUSAGECOUNTER = properties.getProperty("WORKERGENID1.DISABLEKEYUSAGECOUNTER");
        boolean isSelect = Boolean.parseBoolean(DISABLEKEYUSAGECOUNTER);

        disableKeyUsagecounterCheckBox = new CheckBox("Key usage counter");
        disableKeyUsagecounterCheckBox.setIndeterminate(false);
        disableKeyUsagecounterCheckBox.setSelected(isSelect);

        return disableKeyUsagecounterCheckBox;

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

            properties.setProperty("WORKERGENID1.EMBED_CRL", Boolean.toString(embedCrlCheckBox.isSelected()));

            properties.setProperty("WORKERGENID1.EMBED_OCSP_RESPONSE",
                    Boolean.toString(embedOcspResponseCheckBox.isSelected()));

            properties.setProperty("WORKERGENID1.DISABLEKEYUSAGECOUNTER",
                    Boolean.toString(disableKeyUsagecounterCheckBox.isSelected()));

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

        VBox containerbox1 = new VBox(15);
        containerbox1.getChildren().addAll(embedCrlBox(), embedOcspResponseBox(), disableKeyUsageCounterBox());

        HBox container = new HBox(60);
        container.getChildren().addAll(containerbox1);
        container.relocate(20, 20);

        return container;
    }
}

