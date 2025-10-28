module org.example.btl {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires javafx.graphics;
    requires annotations;
    requires javafx.media;

    opens org.example.btl to javafx.fxml;
    exports org.example.btl;
    exports org.example.btl.controllers;
    opens org.example.btl.controllers to javafx.fxml;
}