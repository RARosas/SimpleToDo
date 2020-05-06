package com.rar.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private ArrayList<Task> tareas;
    private onButtonsClicks interf;
    RecyclerAdapter (ArrayList<Task> tareas, Context context)  {
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
        holder.taskDescription.setText(tareas.get(position).getDescription());
        if (tareas.get(position).getDone()==1)  {
            holder.taskCard.setChecked(true);
        } else  {
            holder.taskCard.setChecked(false);
        }
        holder.taskCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (tareas.get(position).getDone()==1)  {
                    holder.taskCard.setChecked(false);
                    interf.changeTaskStatus(position);
                    return true;
                }
                else    {
                    holder.taskCard.setChecked(true);
                    interf.changeTaskStatus(position);
                    return true;
                }
            }
        });
        holder.taskDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interf.deleteButtonClick(position);
            }
        });
        holder.taskEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interf.editButtonClick(position);
            }
        });
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
        void editButtonClick(int position);
        void changeTaskStatus (int position);
    }
}
