module com.example.instruments {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.media;
    requires java.logging;
    requires miglayout;
    requires java.desktop;

    opens assignment2 to javafx.fxml;
    exports assignment2;
    exports assignment2.instruments;
    opens assignment2.instruments to javafx.fxml;
}