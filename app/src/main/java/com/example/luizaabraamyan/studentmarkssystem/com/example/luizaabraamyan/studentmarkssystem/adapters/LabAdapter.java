package com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.luizaabraamyan.studentmarkssystem.com.example
        .luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.objects.Lab;
import com.example.luizaabraamyan.studentmarkssystem
        .com.example.luizaabraamyan.studentmarkssystem.activities.LabStudentActivity;
import com.example.luizaabraamyan.studentmarkssystem.R;

import java.util.List;

public class LabAdapter extends RecyclerView.Adapter<LabAdapter.LabViewHolder> {

    private Context context;
    private static List<Lab> data;

    String idUniversityNum;
    int subjectId;
    int groupId;

    public LabAdapter(Context context, List<Lab> objects, String idUniversityNum, int subjectId, int groupId) {
        this.context = context;
        data = objects;
        this.idUniversityNum = idUniversityNum;
        this.subjectId = subjectId;
        this.groupId = groupId;
    }

    @Override
    public LabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lab, parent, false);
        return new LabViewHolder(itemView);
    }

    public class LabViewHolder extends RecyclerView.ViewHolder {

        public Button labPrettyName;

        public LabViewHolder(View view) {
            super(view);
            labPrettyName = view.findViewById(R.id.labPrettyNameBtn);
        }
    }

    @Override
    public void onBindViewHolder(final LabViewHolder holder, final int position) {
        final Lab lab = data.get(position);
        holder.labPrettyName.setText(lab.getPrettyName());

        holder.labPrettyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LabStudentActivity.class);
                intent.putExtra("labId", lab.getLabId());
                intent.putExtra("universityId", idUniversityNum);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
