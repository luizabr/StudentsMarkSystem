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

public class ViewAllStudentsActivity extends Activity {

    private RecyclerView recyclerView;
    private ViewAllStudentsAdapter adapter;
    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

    Button home;
    int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_all);

        final List<Student> students = new ArrayList<>();

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        context = this;

        Intent intent = this.getIntent();
        final Bundle bundle = intent.getExtras();
        groupId = bundle.getInt("groupId");

        home = findViewById(R.id.homeBtn);

        Cursor cursor = db.rawQuery("SELECT * FROM students;", null);

        if(cursor != null){
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
                    "No students registered!", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_view_students);
        adapter = new ViewAllStudentsAdapter(this, students);

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
