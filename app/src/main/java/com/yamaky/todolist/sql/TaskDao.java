package com.yamaky.todolist.sql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.yamaky.todolist.objects.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void InsertTask(Task task);

    @Delete
    void DeleteTask(Task task);

    @Update
    void EditTask(Task task);

    @Query("SELECT * FROM Tasks")
    List<Task> GetTasks();
}
