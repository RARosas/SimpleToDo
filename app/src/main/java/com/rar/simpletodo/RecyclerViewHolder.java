package com.rar.simpletodo;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

class RecyclerViewHolder extends RecyclerView.ViewHolder {

    MaterialCardView taskCard;
    TextView taskTitle, taskDescription;
    Button taskDeleteButton, taskEditButton;

    RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        taskCard = itemView.findViewById(R.id.taskCard);
        taskDescription = itemView.findViewById(R.id.taskDescription);
        taskTitle = itemView.findViewById(R.id.taskTitleView);
        taskDeleteButton = itemView.findViewById(R.id.taskDeleteButton);
        taskEditButton = itemView.findViewById(R.id.taskEditBtn);
    }
}
