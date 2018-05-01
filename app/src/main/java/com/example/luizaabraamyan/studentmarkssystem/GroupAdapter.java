package com.example.luizaabraamyan.studentmarkssystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder>  {

    private Context context;
    private static List<Group> data;

    public GroupAdapter(Context context, List<Group> objects) {
        this.context = context;
        data = objects;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group, parent, false);
        return new GroupAdapter.GroupViewHolder(itemView);
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {

        public Button number;

        public GroupViewHolder(View view) {
            super(view);
            number = view.findViewById(R.id.groupNumber);
        }
    }

    @Override
    public void onBindViewHolder(final GroupAdapter.GroupViewHolder holder, final int position) {
        final Group group = data.get(position);
        holder.number.setText(String.valueOf(group.getGroupNumber()));

        holder.number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StudentsActivity.class);
                intent.putExtra("groupId", group.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
