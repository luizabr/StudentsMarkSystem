package com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.luizaabraamyan.studentmarkssystem.R;
import com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan
        .studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.objects.Subject;
import com.example.luizaabraamyan.studentmarkssystem.com.example
        .luizaabraamyan.studentmarkssystem.activities.GroupsActivity;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private Context context;
    private static List<Subject> data;
    String idUniversityNum;


    public SubjectAdapter(Context context, List<Subject> objects, String idUniversityNum) {
        this.context = context;
        data = objects;
        this.idUniversityNum = idUniversityNum;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(itemView);
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {

        public Button name;

        public SubjectViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.subjectName);
        }
    }

    @Override
    public void onBindViewHolder(final SubjectViewHolder holder, final int position) {
        final Subject subject = data.get(position);
        holder.name.setText(subject.getName());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GroupsActivity.class);
                intent.putExtra("universityId", idUniversityNum);
                intent.putExtra("subjectId", subject.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
