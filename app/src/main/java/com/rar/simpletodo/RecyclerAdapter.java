package com.rar.simpletodo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private ArrayList<Task> tareas;
    private onButtonsClicks interf;
    public RecyclerAdapter (ArrayList tareas, Context context)  {
        this.tareas = tareas;
        this.interf = (onButtonsClicks) context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskview, parent,false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
        holder.taskTitle.setText(tareas.get(position).getTitle());
        holder.checkTaskDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    holder.taskTitle.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    interf.changeTaskStatus(position);
                    holder.taskDescription.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                } else  {
                    holder.taskTitle.setPaintFlags(holder.taskTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    interf.changeTaskStatus(position);
                    holder.taskDescription.setPaintFlags(holder.taskDescription.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
        holder.taskDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interf.deleteButtonClick((position));
            }
        });
        holder.taskDescription.setText(tareas.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        if (tareas==null)   {
            return 0;
        } else  {
            return tareas.size();
        }
    }

    public interface onButtonsClicks  {
        void deleteButtonClick(int position);
        void changeTaskStatus (int position);
    }
}
