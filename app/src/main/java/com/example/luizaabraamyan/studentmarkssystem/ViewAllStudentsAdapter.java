package com.example.luizaabraamyan.studentmarkssystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class ViewAllStudentsAdapter extends RecyclerView.Adapter<ViewAllStudentsAdapter.StudentViewHolder> {

    private Context context;
    private static List<Student> data;

    public ViewAllStudentsAdapter(Context context, List<Student> objects) {
        this.context = context;
        data = objects;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_all, parent, false);
        return new StudentViewHolder(itemView);
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        public TextView studentName;
        public TextView facNum;
//        public EditText studentMark;

        public StudentViewHolder(View view) {
            super(view);
            studentName = view.findViewById(R.id.studentName);
            facNum = view.findViewById(R.id.facNum);
//            studentMark = view.findViewById(R.id.studentMark);
        }
    }

    @Override
    public void onBindViewHolder(final ViewAllStudentsAdapter.StudentViewHolder holder, final int position) {
        final Student student = data.get(position);
        holder.studentName.setText(student.getName());
        holder.facNum.setText(student.getFacNum());
//        holder.studentMark.setText(String.valueOf(student.getMark()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
