package com.example.luizaabraamyan.studentmarkssystem;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewAllStudentsActivity extends Activity {
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        final List<Student> students = new ArrayList<>();

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        context = this;

        Cursor cursor = db.rawQuery("SELECT * FROM students;", null);

        final int cursorSize = cursor.getCount();

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Student student = new Student();
                    student.setId(cursor.getInt(cursor.getColumnIndex("studentId")));
                    student.setName(cursor.getString(cursor.getColumnIndex("studentName")));
                    student.setFacNum(cursor.getString(cursor.getColumnIndex("studentFacNum")));
                    student.setMark(cursor.getInt(cursor.getColumnIndex("studentMark")));
                    students.add(student);
                }while(cursor.moveToNext());
            }
        }else{
            Toast.makeText(this,
                    "No students registered!", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_view_students);
        adapter = new StudentAdapter(this, students);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }


}
