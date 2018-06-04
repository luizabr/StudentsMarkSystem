package com.example.luizaabraamyan.studentmarkssystem;

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

import java.util.ArrayList;
import java.util.List;

public class ViewAllScholarsActivity extends Activity {

    private RecyclerView recyclerView;
    private ViewAllScholarsAdapter adapter;
    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

    Button home;
    int groupId;

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
        groupId = bundle.getInt("groupId");

//        Cursor cursor = db.rawQuery("SELECT * FROM students WHERE students.studentMark > 4;", null);
//        Cursor cursor = db.rawQuery("SELECT * FROM students JOIN students_marks_table " +
//                "ON(students_marks_table.studentId = students.studentId) " +
//                "JOIN marks ON(students_marks_table.markId = marks.markId) " +
//                "JOIN groups ON (students.groupId = groups.groupId) " +
//                "JOIN teachers_subjects_groups ON (teachers_subjets_groups.groupId = groups.groupId) " +
//                "JOIN teachers ON (teachers_subjets_groups.teacherId = teachers.teacherId) " +
//                "WHERE AVG(marks.markNumber) > 4;", null);

//        Cursor cursor = db.rawQuery("SELECT * FROM students JOIN students_marks_table " +
//                "ON(students_marks_table.studentId = students.studentId) " +
//                "JOIN marks ON(students_marks_table.markId = marks.markId) " +
//                "WHERE marks.markNumber > 4;", null);

        //TODO: Fix this request
        Cursor cursor = db.rawQuery("SELECT AVG(marks.markNumber) AS avg, students.studentId, students.studentName, students.studentFacNum FROM students JOIN students_marks_table " +
                "ON(students_marks_table.studentId = students.studentId) " +
                "JOIN marks ON(students_marks_table.markId = marks.markId);", null);

        int cursorSize = cursor.getCount();
        if(cursorSize != 0){
            if(cursor.moveToFirst()){
                do{
                    Student student = new Student();
                    student.setId(cursor.getInt(cursor.getColumnIndex("studentId")));
                    student.setName(cursor.getString(cursor.getColumnIndex("studentName")));
                    student.setFacNum(cursor.getString(cursor.getColumnIndex("studentFacNum")));
//                    student.setMark(cursor.getInt(cursor.getColumnIndex("studentMark")));
                    students.add(student);
                }while(cursor.moveToNext());
            }
        }else{
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
                intent.putExtra("groupId", groupId);
//                intent.putExtra("bundle", bundle);
                context.startActivity(intent);
            }
        });
    }




}
