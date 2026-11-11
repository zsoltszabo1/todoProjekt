package org.example.todoapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.todoprojekt.Task;

public class ToDoController {

    @FXML
    private TextField taskInputField;

    @FXML
    private TableView<Task> tableView;

    @FXML
    private TableColumn<Task, String> taskColumn;

    private ObservableList<Task> taskList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.setItems(taskList);

        tableView.setRowFactory(tv -> new TableRow<Task>() {
            @Override
            protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item.isDone()) {
                    setStyle("-fx-background-color: lightgreen;");
                } else {
                    setStyle("");
                }
            }
        });
    }

    @FXML
    void onAddButtonClick() {
        String text = taskInputField.getText().trim();
        if (!text.isEmpty()) {
            taskList.add(new Task(text));
            taskInputField.clear();
        }
    }

    @FXML
    void onDoneButtonClick() {
        Task selectedTask = tableView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            selectedTask.setDone(true);
            tableView.refresh();
        }
    }
}
