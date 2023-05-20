package com.pdfsigner.sections;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Visibility {

    private static Properties properties = new Properties();
    private static String pathFile;
    private static boolean isDisable;
    private static CheckBox firstPageCheckBox;
    private static CheckBox lastPageCheckBox;
    private static CheckBox resizeImageCheckBox;
    private static CheckBox avsCheckBox;
    private static Spinner<Integer> vspSpinner;
    private static Spinner<Integer> llxSpinner;
    private static Spinner<Integer> llySpinner;
    private static Spinner<Integer> urxSpinner;
    private static Spinner<Integer> urySpinner;
    private static VBox customImageVb;
    private static VBox certificationLevelVb;
    private static HBox vspHb; // VISIBLE_SIGNATURE_PAGE
    private static VBox vsrVb; // VISIBLE_SIGNATURE_RECTANGLE
    private static ChoiceBox<String> certificationLevelChoiceBox;
    private static TextField imagePathTextField;
    private static TextField imageBase64TextField;

    public static void loadProperties(String path) throws IOException {

        FileInputStream file = new FileInputStream(path);
        properties.load(file);
        pathFile = path;
    }

    private static ImageView InfoIcon() {
        Image icon = new Image(Visibility.class.getResource("/com/pdfsigner/info.png").toExternalForm());
        ImageView imageView = new ImageView(icon);
        return imageView;
    }

    private static CheckBox avsBox() {

        String ADD_VISIBLE_SIGNATURE = properties.getProperty("WORKERGENID1.ADD_VISIBLE_SIGNATURE");
        boolean isSelect = Boolean.parseBoolean(ADD_VISIBLE_SIGNATURE);
        isDisable = isSelect ? false : true;

        Tooltip tooltip = new Tooltip(
                "If we want the signature \nto be drawn on the set of document pages");

        avsCheckBox = new CheckBox("Add visible signature");
        avsCheckBox.setTooltip(tooltip);
        avsCheckBox.setGraphic(InfoIcon());
        avsCheckBox.setIndeterminate(false);
        avsCheckBox.setSelected(isSelect);

        avsCheckBox.setOnAction(e -> {
            if (avsCheckBox.isSelected()) {
                firstPageCheckBox.setDisable(false);
                lastPageCheckBox.setDisable(false);
                vspHb.setDisable(false);
                vsrVb.setDisable(false);
                resizeImageCheckBox.setDisable(false);
                customImageVb.setDisable(false);
                certificationLevelVb.setDisable(false);
            } else {
                firstPageCheckBox.setDisable(true);
                lastPageCheckBox.setDisable(true);
                vspHb.setDisable(true);
                vsrVb.setDisable(true);
                resizeImageCheckBox.setDisable(true);
                customImageVb.setDisable(true);
                certificationLevelVb.setDisable(true);

            }

            if (firstPageCheckBox.isSelected() || lastPageCheckBox.isSelected()) {
                vspHb.setDisable(true);
            }
        });

        return avsCheckBox;

    }

    private static VBox vspBox() {

        String VISIBLE_SIGNATURE_PAGE = properties.getProperty("WORKERGENID1.VISIBLE_SIGNATURE_PAGE");

        Integer setPageNumber = Integer.parseInt(VISIBLE_SIGNATURE_PAGE);

        Label labelSpinner = new Label("Page number");

        vspSpinner = new Spinner<>(1, 99999999, setPageNumber);

        vspHb = new HBox(5);
        vspHb.getChildren().addAll(vspSpinner, labelSpinner);
        vspHb.setDisable(isDisable);

        firstPageCheckBox = new CheckBox("First page");
        firstPageCheckBox.setDisable(isDisable);
        lastPageCheckBox = new CheckBox("Last page");
        lastPageCheckBox.setDisable(isDisable);

        firstPageCheckBox.setOnAction(e -> {
            if (firstPageCheckBox.isSelected()) {
                lastPageCheckBox.setSelected(false);
                vspHb.setDisable(true);

            } else if (firstPageCheckBox.isSelected() == false && lastPageCheckBox.isSelected() == false) {
                vspHb.setDisable(false);

            }
        });

        lastPageCheckBox.setOnAction(e -> {
            if (lastPageCheckBox.isSelected()) {
                firstPageCheckBox.setSelected(false);
                vspHb.setDisable(true);

            } else if (firstPageCheckBox.isSelected() == false && lastPageCheckBox.isSelected() == false) {
                vspHb.setDisable(false);

            }
        });

        VBox container = new VBox(15);
        container.getChildren().addAll(firstPageCheckBox, lastPageCheckBox, vspHb);
        container.setId("containerbox1");
        VBox.setMargin(firstPageCheckBox, new Insets(15, 0, 0, 15));
        VBox.setMargin(lastPageCheckBox, new Insets(0, 0, 0, 15));
        VBox.setMargin(vspHb, new Insets(0, 0, 0, 15));

        return container;

    }

    private static VBox vsrBox() {

        Label label = new Label("Rectangle signature");
        label.setId("primary-label");

        String llx, lly, urx, ury;

        llx = properties.getProperty("WORKERGENID1.VISIBLE_SIGNATURE_RECTANGLE")
                .split(",")[0];
        lly = properties.getProperty("WORKERGENID1.VISIBLE_SIGNATURE_RECTANGLE")
                .split(",")[1];
        urx = properties.getProperty("WORKERGENID1.VISIBLE_SIGNATURE_RECTANGLE")
                .split(",")[2];
        ury = properties.getProperty("WORKERGENID1.VISIBLE_SIGNATURE_RECTANGLE")
                .split(",")[3];

        llxSpinner = new Spinner<>(-99999, 999999, Integer.parseInt(llx));
        llySpinner = new Spinner<>(-99999, 999999, Integer.parseInt(lly));
        urxSpinner = new Spinner<>(-99999, 999999, Integer.parseInt(urx));
        urySpinner = new Spinner<>(-99999, 999999, Integer.parseInt(ury));

        Label positionLabel = new Label("Position coordinate");

        vsrVb = new VBox(3);
        VBox.setMargin(label, new Insets(-5, 0, 0, 0));
        VBox.setMargin(positionLabel, new Insets(-10, 0, 0, 0));
        vsrVb.getChildren().addAll(
                label,
                positionLabel,
                new HBox(10,
                        new HBox(5, llxSpinner, new Label("X")),
                        new HBox(5, llySpinner, new Label("Y"))),

                new Label("Size width & heigth"),
                new HBox(10,
                        new HBox(5, urxSpinner, new Label("W")),
                        new HBox(5, urySpinner, new Label("H")))

        );

        vsrVb.setDisable(isDisable);

        return vsrVb;

    }

    private static VBox customImageBox(Stage primaryStage) {

        String VISIBLE_SIGNATURE_CUSTOM_IMAGE_PATH = properties
                .getProperty("WORKERGENID1.VISIBLE_SIGNATURE_CUSTOM_IMAGE_PATH");

        imagePathTextField = new TextField(VISIBLE_SIGNATURE_CUSTOM_IMAGE_PATH);

        String VISIBLE_SIGNATURE_CUSTOM_IMAGE_BASE64 = properties
                .getProperty("WORKERGENID1.VISIBLE_SIGNATURE_CUSTOM_IMAGE_BASE64");

        imageBase64TextField = new TextField(VISIBLE_SIGNATURE_CUSTOM_IMAGE_BASE64);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        ExtensionFilter extFilter = new ExtensionFilter("*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        Button openButton = new Button("Select Image");
        openButton.setId("button-select-file");
        openButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            imagePathTextField.setText(selectedFile.getAbsolutePath());
        });

        imageBase64TextField.setOnKeyTyped(e -> {

            if (imageBase64TextField.getText().length() > 0) {

                imagePathTextField.setDisable(true);
                openButton.setDisable(true);

            } else {
                imagePathTextField.setDisable(false);
                openButton.setDisable(false);

            }

        });

        Label customImageLabel = new Label("Custom image");
        customImageLabel.setGraphic(InfoIcon());
        customImageLabel.setTooltip(new Tooltip(
                "If we want the visible signature \n" +
                        "to contain custom image , specify image \n" +
                        "as base64 encoded byte array alternatively \n" +
                        "custom image can be specified by giving a path \n" +
                        "to image on file system"));
        customImageLabel.setId("primary-label");

        VBox containerImagePath = new VBox(0, new Label("Image"), imagePathTextField);
        VBox containerBase64 = new VBox(0, new Label("Image base64"), imageBase64TextField);

        customImageVb = new VBox(5);
        VBox.setMargin(containerImagePath, new Insets(-10, 0, 0, 0));
        customImageVb.getChildren().addAll(
                customImageLabel,
                containerImagePath,
                openButton,
                containerBase64);

        return customImageVb;
    }

    private static CheckBox scaleRectangleBox() {

        String VISIBLE_SIGNATURE_CUSTOM_IMAGE_SCALE_TO_RECTANGLE = properties
                .getProperty("WORKERGENID1.VISIBLE_SIGNATURE_CUSTOM_IMAGE_SCALE_TO_RECTANGLE");

        boolean isSelect = Boolean.parseBoolean(VISIBLE_SIGNATURE_CUSTOM_IMAGE_SCALE_TO_RECTANGLE);

        resizeImageCheckBox = new CheckBox("Resize image");
        resizeImageCheckBox.setIndeterminate(false);
        resizeImageCheckBox.setSelected(isSelect);
        resizeImageCheckBox.setGraphic(InfoIcon());
        resizeImageCheckBox
                .setTooltip(new Tooltip("If we want our custom image \nto be resized to specified rectangle "));

        return resizeImageCheckBox;

    }

    private static VBox certificationLevelBox() {

        String CERTIFICATION_LEVEL = properties
                .getProperty("WORKERGENID1.CERTIFICATION_LEVEL");

        Label label = new Label("Certification level");
        label.setGraphic(InfoIcon());
        label.setTooltip(new Tooltip("Create a certifying signature \nthat certifies the document."));

        certificationLevelChoiceBox = new ChoiceBox<>();
        certificationLevelChoiceBox.getItems().addAll(
                "NOT_CERTIFIED",
                "FORM_FILLING",
                "FORM_FILLING_AND_ANNOTATIONS",
                "NO_CHANGES_ALLOWED");

        certificationLevelChoiceBox.setValue(CERTIFICATION_LEVEL);

        certificationLevelVb = new VBox(2);
        certificationLevelVb.getChildren().addAll(label, certificationLevelChoiceBox);

        return certificationLevelVb;

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

            properties.setProperty("WORKERGENID1.ADD_VISIBLE_SIGNATURE", Boolean.toString(avsCheckBox.isSelected()));

            if (firstPageCheckBox.isSelected()) {
                properties.setProperty("WORKERGENID1.VISIBLE_SIGNATURE_PAGE", "First");
            } else if (lastPageCheckBox.isSelected()) {
                properties.setProperty("WORKERGENID1.VISIBLE_SIGNATURE_PAGE", "Last");
            } else {
                properties.setProperty("WORKERGENID1.VISIBLE_SIGNATURE_PAGE", vspSpinner.getValue().toString());
            }

            String spinnersValueVsr = llxSpinner.getValue().toString() + ","
                    + llySpinner.getValue().toString() + ","
                    + urxSpinner.getValue().toString() + ","
                    + urySpinner.getValue().toString();

            properties.setProperty(
                    "WORKERGENID1.VISIBLE_SIGNATURE_RECTANGLE",
                    spinnersValueVsr);

            properties.setProperty("WORKERGENID1.VISIBLE_SIGNATURE_CUSTOM_IMAGE_SCALE_TO_RECTANGLE",
                    Boolean.toString(resizeImageCheckBox.isSelected()));

            properties.setProperty("WORKERGENID1.VISIBLE_SIGNATURE_CUSTOM_IMAGE_BASE64",
                    imageBase64TextField.getText());
            properties.setProperty("WORKERGENID1.VISIBLE_SIGNATURE_CUSTOM_IMAGE_PATH",
                    imagePathTextField.getText());

            properties.setProperty("WORKERGENID1.NOT_CERTIFIED",
                    certificationLevelChoiceBox.getValue().toString());

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

    public static HBox containerBox(Stage primaryStage) {

        VBox containerCustomImage = customImageBox(primaryStage);
        CheckBox checkBoxScaleRectangle = scaleRectangleBox();

        VBox containerBox1 = new VBox(10);
        VBox.setMargin(containerCustomImage, new Insets(-5, 0, 0, 0));
        containerBox1.getChildren().addAll(avsBox(), vspBox(), containerCustomImage);

        VBox containerbox2 = new VBox(15);
        VBox.setMargin(checkBoxScaleRectangle, new Insets(15, 0, 0, 0));
        containerbox2.getChildren().addAll(vsrBox(), checkBoxScaleRectangle, certificationLevelBox());

        HBox container = new HBox(60);
        container.getChildren().addAll(containerBox1, containerbox2);
        container.relocate(20, 20);

        return container;
    }
}

