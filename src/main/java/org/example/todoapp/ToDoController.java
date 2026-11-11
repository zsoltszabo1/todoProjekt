package org.example.todoapp;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.example.todoprojekt.Task;

public class ToDoController {

    @FXML
    private TextField taskInputField;

    @FXML
    private TableView<Task> tableView;

    @FXML
    private TableColumn<Task, Boolean> doneColumn;

    @FXML
    private TableColumn<Task, Task> taskColumn;

    @FXML
    private Label counterLabel;

    // Az "extractor" miatt a lista jelez, ha egy feladat "done" értéke megváltozik
    private final ObservableList<Task> taskList =
            FXCollections.observableArrayList(task -> new Observable[]{task.doneProperty()});

    @FXML
    public void initialize() {
        tableView.setItems(taskList);

        Label placeholder = new Label("Még nincs feladat. Adj hozzá egyet!");
        placeholder.getStyleClass().add("placeholder-text");
        tableView.setPlaceholder(placeholder);

        // 1. oszlop: pipálható négyzet (kész / nem kész)
        doneColumn.setCellValueFactory(cellData -> cellData.getValue().doneProperty());
        doneColumn.setCellFactory(CheckBoxTableCell.forTableColumn(doneColumn));

        // 2. oszlop: a feladat szövege, késznél áthúzva és halványabban
        taskColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
        taskColumn.setCellFactory(column -> new TableCell<Task, Task>() {
            private final Text text = new Text();

            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setGraphic(null);
                } else {
                    text.setText(task.getName());
                    text.setStrikethrough(task.isDone());
                    text.setFill(task.isDone() ? Color.web("#B9AEB2") : Color.WHITE);
                    setGraphic(text);
                }
            }
        });

        // Bármilyen változásnál (hozzáadás, törlés, pipálás) frissítjük a táblát és a számlálót
        taskList.addListener((ListChangeListener<Task>) change -> {
            tableView.refresh();
            updateCounter();
        });
        updateCounter();
    }

    @FXML
    void onAddButtonClick() {
        String text = taskInputField.getText().trim();
        if (!text.isEmpty()) {
            taskList.add(new Task(text));
            taskInputField.clear();
        }
        taskInputField.requestFocus();
    }

    @FXML
    void onDeleteButtonClick() {
        Task selectedTask = tableView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            taskList.remove(selectedTask);
        }
    }

    @FXML
    void onClearDoneButtonClick() {
        taskList.removeIf(Task::isDone);
    }

    @FXML
    void onClearAllButtonClick() {
        taskList.clear();
    }

    private void updateCounter() {
        long remaining = taskList.stream().filter(task -> !task.isDone()).count();
        counterLabel.setText(remaining + " feladat van hátra");
    }
}