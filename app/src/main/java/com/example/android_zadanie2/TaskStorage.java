package com.example.android_zadanie2;

import java.util.ArrayList;
import java.util.List;

public class TaskStorage {
    private static final TaskStorage taskStorage = new TaskStorage();
    private final List<Task> tasks;
    public static TaskStorage getInstance() {
        return taskStorage;
    }

    private TaskStorage() {
        tasks = new ArrayList<>();
        for(int i=1; i<=150; i++){
            Task task = new Task();
            task.setName("Pilne zadanie numer " + i);
            task.setDone(i%3 == 0);
            tasks.add(task);
        }
    }
}
