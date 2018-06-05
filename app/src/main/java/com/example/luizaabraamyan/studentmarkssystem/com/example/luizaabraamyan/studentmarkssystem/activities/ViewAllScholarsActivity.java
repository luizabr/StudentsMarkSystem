package com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.luizaabraamyan.studentmarkssystem.DBHelper;
import com.example.luizaabraamyan.studentmarkssystem.R;
import com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan
        .studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.objects.Student;
import com.example.luizaabraamyan
        .studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.adapters.ViewAllScholarsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewAllScholarsActivity extends Activity {

    private RecyclerView recyclerView;
    private ViewAllScholarsAdapter adapter;
    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

    Button home;

    String idUniversityNum;
    int groupId;
    int subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholar);

        final List<Student> students = new ArrayList<>();

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        context = this;

        home = findViewById(R.id.homeBtn);

        Intent intent = this.getIntent();
        final Bundle bundle = intent.getExtras();
        idUniversityNum = bundle.getString("universityId");
        subjectId = bundle.getInt("subjectId");
        groupId = bundle.getInt("groupId");


//        Cursor cursor = db.rawQuery("SELECT * FROM students JOIN students_marks " +
//                "ON(students_marks.studentId = students.studentId) " +
//                "JOIN marks ON(students_marks.markId = marks.markId) " +
//                "JOIN groups ON (students.groupId = groups.groupId) " +
//                "JOIN teachers_subjects_groups ON (teachers_subjets_groups.groupId = groups.groupId) " +
//                "JOIN teachers ON (teachers_subjets_groups.teacherId = teachers.teacherId) " +
//                "WHERE AVG(marks.markNumber) > 4;", null);

        Cursor cursor = db.rawQuery("SELECT * FROM students " +
                "WHERE students.studentTempMark > 4;", null);

        int cursorSize = cursor.getCount();
        if (cursorSize != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Student student = new Student();
                    student.setId(cursor.getInt(cursor.getColumnIndex("studentId")));
                    student.setName(cursor.getString(cursor.getColumnIndex("studentName")));
                    student.setFacNum(cursor.getString(cursor.getColumnIndex("studentFacNum")));
                    students.add(student);
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(this,
                    "Няма регистрирани стипендианти!", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_view_scholars);
        adapter = new ViewAllScholarsAdapter(this, students);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("universityId", idUniversityNum);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });
    }


}
