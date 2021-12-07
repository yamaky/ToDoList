package com.yamaky.todolist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.yamaky.todolist.R;
import com.yamaky.todolist.objects.Task;
import com.yamaky.todolist.viewmodel.AppViewModel;

public final class EditTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        AppViewModel appViewModel = AppViewModel.GetInstance(getApplication());

        Intent intent = getIntent();
        if (intent != null) {
            Task task = null;

            if (!intent.getExtras().getBoolean("Add")) {
                task = intent.getParcelableExtra("Task");

                ((EditText) findViewById(R.id.task_name_box)).setText(task.TaskName);
                ((EditText) findViewById(R.id.task_description_box)).setText(task.TaskDescription);
            }


            Task finalTask = task == null ? new Task() : task;
            findViewById(R.id.save_button).setOnClickListener(v -> {
                String taskName = ((EditText) findViewById(R.id.task_name_box))
                        .getText()
                        .toString();
                String taskDescription = ((EditText) findViewById(R.id.task_description_box))
                        .getText()
                        .toString();

                if (!taskName.isEmpty() && !taskDescription.isEmpty()) {
                    finalTask.TaskName = taskName;
                    finalTask.TaskDescription = taskDescription;

                    if (!intent.getExtras().getBoolean("Add")) {
                        appViewModel.UpdateTask(finalTask, result -> {
                            if (result) {
                                Intent toMainScreen = new Intent(this, TaskListActivity.class);
                                runOnUiThread(() -> startActivity(toMainScreen));
                            } else {
                                runOnUiThread(() -> Toast.makeText(this,
                                        "Что-то пошло не так",
                                        Toast.LENGTH_LONG).show());
                            }
                        });
                    } else {
                        appViewModel.CreateTask(finalTask, result -> {
                            if (result) {
                                Intent toMainScreen = new Intent(this, TaskListActivity.class);
                                runOnUiThread(() -> {
                                    Toast.makeText(this,
                                            "Задача сохранена",
                                            Toast.LENGTH_LONG).show();

                                    startActivity(toMainScreen);
                                });
                            } else {
                                runOnUiThread(() -> Toast.makeText(this,
                                        "Что-то пошло не так",
                                        Toast.LENGTH_LONG).show());

                            }
                        });
                    }
                } else {
                    Toast.makeText(this,
                            "Все поля должны быть заполнены",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}