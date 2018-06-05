package com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.luizaabraamyan.studentmarkssystem.DBHelper;
import com.example.luizaabraamyan.studentmarkssystem.R;

public class MenuActivity extends Activity {

    Button addMark;
    Button addEndorsement;
    Button addAttendance;
    Button editMark;
    Button viewAllStudents;
    Button viewAllScholars;
    Button logout;

    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

    String idUniversityNum;
    int subjectId;
    int groupId;
    int mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        addMark = findViewById(R.id.addMarkBtn);
        addEndorsement = findViewById(R.id.addEndorsementBtn);
        addAttendance = findViewById(R.id.addAttendanceBtn);
        editMark = findViewById(R.id.editMarkBtn);
        viewAllStudents = findViewById(R.id.viewStudentsBtn);
        viewAllScholars = findViewById(R.id.viewScholarsBtn);
        logout = findViewById(R.id.logoutBtn);

        context = this;
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        idUniversityNum = bundle.getString("universityId");
        subjectId = bundle.getInt("subjectId");
        groupId = bundle.getInt("groupId");
        mark = bundle.getInt("mark");

        addMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MarksActivity.class);
                intent.putExtra("universityId", idUniversityNum);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });

        addEndorsement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EndorsementsActivity.class);
                intent.putExtra("universityId", idUniversityNum);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });

        addAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LabsActivity.class);
                intent.putExtra("universityId", idUniversityNum);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });

        editMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditMarkActivity.class);
                intent.putExtra("universityId", idUniversityNum);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("groupId", groupId);
                intent.putExtra("mark", mark);
                context.startActivity(intent);
            }
        });

        viewAllStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllStudentsActivity.class);
                intent.putExtra("universityId", idUniversityNum);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });

        viewAllScholars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllScholarsActivity.class);
                intent.putExtra("universityId", idUniversityNum);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
