package com.yamaky.todolist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.yamaky.todolist.objects.Task;
import com.yamaky.todolist.sql.AppDatabase;
import com.yamaky.todolist.sql.TaskDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public final class AppViewModel {
    @NonNull
    private final TaskDao taskDao;

    @NonNull
    private final MutableLiveData<List<Task>> tasksLiveData;

    private static AppViewModel appViewModel;

    public static AppViewModel GetInstance(@NonNull Application application) {
        if (appViewModel == null)
            appViewModel = new AppViewModel(Room
                    .databaseBuilder(application.getApplicationContext(),
                            AppDatabase.class,
                            "TaskDb")
                    .build());

        return appViewModel;
    }

    private AppViewModel(@NonNull AppDatabase appDatabase) {
        taskDao = appDatabase.GetTaskDao();
        tasksLiveData = new MutableLiveData<>();
    }

    public void CreateTask(@NonNull Task task, @NonNull Consumer<Boolean> callback) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            boolean result = false;

            try {
                taskDao.InsertTask(task);
                result = true;

                List<Task> tasks = tasksLiveData.getValue() != null ? tasksLiveData.getValue() : new ArrayList<>();
                tasks.add(task);

                tasksLiveData.postValue(tasks);

            } catch (Exception ignored) {

            }
            service.shutdown();

            callback.accept(result);
        });
    }

    public void DeleteTask(@NonNull Task task) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            if (tasksLiveData.getValue() != null) {
                taskDao.DeleteTask(task);

                List<Task> tasks = tasksLiveData.getValue();

                int taskId = -1;
                int index = 0;
                while (taskId == -1 && index < tasks.size()) {
                    if (tasks.get(index).TaskId == task.TaskId) {
                        taskId = index;
                    } else {
                        ++index;
                    }
                }

                if (taskId != -1) {
                    tasks.remove(taskId);
                }

                tasksLiveData.postValue(tasks);

                service.shutdown();
            }
        });
    }

    public void UpdateTask(@NonNull Task task, @NonNull Consumer<Boolean> callback) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            boolean result = false;

            try {
                taskDao.EditTask(task);
                result = true;
            } catch (Exception ignored) {

            }
            service.shutdown();

            callback.accept(result);
        });
    }

    public void GetTasks() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            List<Task> tasks = taskDao.GetTasks();
            tasksLiveData.postValue(tasks);
        });
    }

    public LiveData<List<Task>> GetTasksLiveData() {
        return tasksLiveData;
    }
}
