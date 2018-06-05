package com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.luizaabraamyan.studentmarkssystem.R;
import com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan
        .studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.objects.Student;

import java.util.List;

public class LabStudentAdapter extends RecyclerView.Adapter<LabStudentAdapter.StudentViewHolder> {

    private Context context;
    private static List<Student> data;

    public LabStudentAdapter(Context context, List<Student> objects) {
        this.context = context;
        data = objects;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_lab, parent, false);
        return new StudentViewHolder(itemView);
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        public TextView studentName;
        public TextView facNum;
        public CheckBox isPresent;


        public StudentViewHolder(View view) {
            super(view);
            studentName = view.findViewById(R.id.studentName);
            facNum = view.findViewById(R.id.facNum);
            isPresent = view.findViewById(R.id.isPresentBox);
        }
    }

    @Override
    public void onBindViewHolder(final LabStudentAdapter.StudentViewHolder holder, final int position) {
        final Student student = data.get(position);
        holder.studentName.setText(student.getName());
        holder.facNum.setText(student.getFacNum());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
