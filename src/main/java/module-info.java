module org.example.todoprojekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.todoprojekt to javafx.fxml;
    exports org.example.todoprojekt;
    exports org.example.todoapp;
    opens org.example.todoapp to javafx.fxml;
}