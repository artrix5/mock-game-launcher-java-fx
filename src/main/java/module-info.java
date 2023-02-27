module com.example.java.freegamestracker {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.java.freegamestracker to javafx.fxml;
    exports com.example.java.freegamestracker;
}