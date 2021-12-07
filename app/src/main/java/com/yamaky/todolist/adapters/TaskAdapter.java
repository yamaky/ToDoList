package com.yamaky.todolist.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yamaky.todolist.R;
import com.yamaky.todolist.databinding.TaskItemBinding;
import com.yamaky.todolist.objects.Task;

import java.util.List;
import java.util.function.Consumer;

public final class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    @NonNull
    private List<Task> tasks;

    @NonNull
    private final Consumer<Task> editTask;

    @NonNull
    private final Consumer<Task> deleteTask;

    @NonNull
    private final Consumer<Task> showTask;

    public TaskAdapter(@NonNull List<Task> tasks,
                       @NonNull Consumer<Task> editTask,
                       @NonNull Consumer<Task> deleteTask,
                       @NonNull Consumer<Task> showTask) {
        this.editTask = editTask;
        this.deleteTask = deleteTask;
        this.showTask = showTask;

        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(TaskItemBinding
                .inflate(LayoutInflater
                        .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get((tasks.size() - 1) - position);
        holder.Bind(task);
        holder.itemView.findViewById(R.id.edit_button)
                .setOnClickListener(v -> editTask.accept(task));
        holder.itemView.findViewById(R.id.delete_button)
                .setOnClickListener(v -> deleteTask.accept(task));
        holder.itemView.findViewById(R.id.task_card)
                .setOnClickListener(v -> showTask.accept(task));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(@NonNull List<Task> tasks) {
        notifyItemRangeRemoved(0, this.tasks.size());
        this.tasks = tasks;
    }

    protected static final class TaskViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        private final TaskItemBinding taskItemBinding;

        public TaskViewHolder(@NonNull TaskItemBinding taskItemBinding) {
            super(taskItemBinding.getRoot());
            this.taskItemBinding = taskItemBinding;
        }

        public void Bind(@NonNull Task task) {
            taskItemBinding.setTask(task);
            taskItemBinding.executePendingBindings();
        }
    }
}
