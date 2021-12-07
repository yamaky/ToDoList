package com.yamaky.todolist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yamaky.todolist.R;
import com.yamaky.todolist.objects.Task;

public final class ShowTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);

        Intent intent = getIntent();

        if (intent != null) {
            Task task = intent.getParcelableExtra("Task");

            ((TextView) findViewById(R.id.show_task_name_box)).setText(task.TaskName);
            ((TextView) findViewById(R.id.show_task_description_box)).setText(task.TaskDescription);
            ((TextView) findViewById(R.id.show_task_description_box))
                    .setMovementMethod(new ScrollingMovementMethod());
        }
    }


}