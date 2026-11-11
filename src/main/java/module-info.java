module com.mycompany.campust_lf {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.campust_lf to javafx.fxml;
    exports com.mycompany.campust_lf;
}
