package com.yamaky.todolist.sql;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.yamaky.todolist.objects.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao GetTaskDao();
}
