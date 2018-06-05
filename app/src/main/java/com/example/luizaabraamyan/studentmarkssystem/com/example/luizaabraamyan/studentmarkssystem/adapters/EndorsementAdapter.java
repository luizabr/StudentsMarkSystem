package com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.luizaabraamyan.studentmarkssystem.R;
import com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan
        .studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.objects.Student;

import java.util.List;

public class EndorsementAdapter extends RecyclerView.Adapter<EndorsementAdapter.StudentEndorsementViewHolder> {

    private Context context;
    private static List<Student> data;

    public EndorsementAdapter(Context context, List<Student> objects) {
        this.context = context;
        data = objects;
    }

    @Override
    public StudentEndorsementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_endorsement, parent, false);
        return new StudentEndorsementViewHolder(itemView);
    }

    public class StudentEndorsementViewHolder extends RecyclerView.ViewHolder {

        public TextView studentName;
        public TextView facNum;
        public CheckBox isStudentEndorsed;
        public EditText studentNote;

        public StudentEndorsementViewHolder(View view) {
            super(view);
            studentName = view.findViewById(R.id.studentName);
            facNum = view.findViewById(R.id.facNum);
            isStudentEndorsed = view.findViewById(R.id.isEndorsedBox);
            studentNote = view.findViewById(R.id.note);
        }
    }

    @Override
    public void onBindViewHolder(final StudentEndorsementViewHolder holder, final int position) {
        final Student student = data.get(position);
        holder.studentName.setText(student.getName());
        holder.facNum.setText(student.getFacNum());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
