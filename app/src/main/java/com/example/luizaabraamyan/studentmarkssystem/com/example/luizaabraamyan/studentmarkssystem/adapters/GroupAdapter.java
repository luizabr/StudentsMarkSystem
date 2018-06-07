package com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan
        .studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.objects.Group;
import com.example.luizaabraamyan.studentmarkssystem.R;
import com.example.luizaabraamyan.studentmarkssystem.com
        .example.luizaabraamyan.studentmarkssystem.activities.MenuActivity;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private Context context;
    private static List<Group> data;

    String idUniversityNum;
    int subjectId;

    public GroupAdapter(Context context, List<Group> objects, int subjectId, String idUniversityNum) {
        this.context = context;
        data = objects;
        this.subjectId = subjectId;
        this.idUniversityNum = idUniversityNum;
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
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("groupId", group.getId());
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("universityId", idUniversityNum);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
