package com.example.gymapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymapp.R;
import com.example.gymapp.model.Visitor;

import java.util.List;

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.VisitorViewHolder> {

    private List<Visitor> visitorList;
    private final OnVisitorActionListener listener;

    public interface OnVisitorActionListener {

        void onDeleteClick(Visitor visitor);

        void onManageClick(Visitor visitor);
    }

    public VisitorAdapter(List<Visitor> visitorList,
                          OnVisitorActionListener listener) {

        this.visitorList = visitorList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visitor_item, parent, false);

        return new VisitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder,
                                 int position) {

        Visitor visitor = visitorList.get(position);

        holder.textName.setText(
                visitor.getFirstName() + " " + visitor.getLastName()
        );

        holder.textId.setText("ID: " + visitor.getId());

        holder.buttonDelete.setOnClickListener(v -> {
            listener.onDeleteClick(visitor);
        });

        holder.buttonManage.setOnClickListener(v -> {
            listener.onManageClick(visitor);
        });
    }

    @Override
    public int getItemCount() {
        return visitorList.size();
    }

    public void setVisitors(List<Visitor> newList) {
        this.visitorList = newList;
        notifyDataSetChanged();
    }

    static class VisitorViewHolder extends RecyclerView.ViewHolder {

        TextView textName, textId;

        Button buttonDelete;
        Button buttonManage;

        public VisitorViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textVisitorName);
            textId = itemView.findViewById(R.id.textVisitorId);

            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonManage = itemView.findViewById(R.id.buttonManage);
        }
    }
}