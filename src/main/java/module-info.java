module com.pdfsigner {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.pdfsigner to javafx.fxml;
    exports com.pdfsigner;
}
