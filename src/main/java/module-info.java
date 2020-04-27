module htl.tinf.lab {
    requires javafx.controls;
    requires javafx.fxml;

    opens htl.tinf.lab to javafx.fxml;
    exports htl.tinf.lab;
}