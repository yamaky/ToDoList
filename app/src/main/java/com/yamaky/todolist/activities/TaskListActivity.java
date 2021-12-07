package com.yamaky.todolist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yamaky.todolist.R;
import com.yamaky.todolist.adapters.TaskAdapter;
import com.yamaky.todolist.objects.Task;
import com.yamaky.todolist.viewmodel.AppViewModel;

public final class TaskListActivity extends AppCompatActivity {

    private AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        appViewModel = AppViewModel.GetInstance(getApplication());

        appViewModel.GetTasksLiveData().observe(this, tasks -> {
            if (tasks.size() != 0) {

                if (findViewById(R.id.tasks_list).getVisibility() == View.GONE) {
                    findViewById(R.id.no_tasks).setVisibility(View.GONE);
                    findViewById(R.id.tasks_list).setVisibility(View.VISIBLE);
                }

                TaskAdapter taskAdapter = (TaskAdapter) ((RecyclerView) findViewById(R.id.tasks_list))
                        .getAdapter();

                if (taskAdapter == null) {
                    taskAdapter = new TaskAdapter(tasks,
                            this::EditTask,
                            this::DeleteTask,
                            this::ShowTask);

                    ((RecyclerView) findViewById(R.id.tasks_list)).setAdapter(taskAdapter);
                } else {
                    taskAdapter.setTasks(tasks);
                }

            } else {
                findViewById(R.id.no_tasks).setVisibility(View.VISIBLE);
                findViewById(R.id.tasks_list).setVisibility(View.GONE);
            }
        });

        appViewModel.GetTasks();

        findViewById(R.id.add_task_button).setOnClickListener(v -> AddTask());
    }

    private void AddTask() {
        Intent intent = new Intent(this, EditTaskActivity.class);
        intent.putExtra("Show", false);
        intent.putExtra("Add", true);
        runOnUiThread(() -> startActivity(intent));
    }

    private void EditTask(@NonNull Task task) {
        Intent intent = new Intent(this, EditTaskActivity.class);
        intent.putExtra("Show", false);
        intent.putExtra("Add", false);
        intent.putExtra("Task", task);

        runOnUiThread(() -> startActivity(intent));
    }

    private void ShowTask(@NonNull Task task) {
        Intent intent = new Intent(this, ShowTaskActivity.class);
        intent.putExtra("Task", task);

        runOnUiThread(() -> startActivity(intent));
    }

    private void DeleteTask(@NonNull Task task) {
        appViewModel.DeleteTask(task);
    }
}