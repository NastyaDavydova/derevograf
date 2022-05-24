module com.example.derevograf {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires JavaFXSmartGraph;

    opens com.example.derevograf to javafx.fxml;
    exports com.example.derevograf;
    exports example;
    opens example to javafx.graphics;

    exports derevo;
    opens derevo to test;

}