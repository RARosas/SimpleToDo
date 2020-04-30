package com.rar.simpletodo;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView taskTitle;
    CheckBox checkTaskDone;
    Button taskDeleteButton;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        taskTitle = itemView.findViewById(R.id.taskTitleView);
        checkTaskDone = itemView.findViewById(R.id.checkDoneView);
        taskDeleteButton = itemView.findViewById(R.id.taskDeleteButton);
    }
}
