package org.example.todoprojekt;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Task {
    private String name;
    private final BooleanProperty done = new SimpleBooleanProperty(false);

    public Task(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done.get();
    }

    public void setDone(boolean done) {
        this.done.set(done);
    }

    public BooleanProperty doneProperty() {
        return done;
    }
}