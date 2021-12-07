package com.yamaky.todolist.objects;

import static androidx.room.ColumnInfo.INTEGER;
import static androidx.room.ColumnInfo.TEXT;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tasks")
public final class Task implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(typeAffinity = INTEGER)
    public int TaskId;

    @NonNull
    @ColumnInfo(typeAffinity = TEXT)
    public String TaskName;

    @NonNull
    @ColumnInfo(typeAffinity = TEXT)
    public String TaskDescription;

    public Task() {
        TaskName = "";
        TaskDescription = "";
    }

    protected Task(Parcel in) {
        TaskId = in.readInt();
        TaskName = in.readString();
        TaskDescription = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(TaskId);
        dest.writeString(TaskName);
        dest.writeString(TaskDescription);
    }
}
