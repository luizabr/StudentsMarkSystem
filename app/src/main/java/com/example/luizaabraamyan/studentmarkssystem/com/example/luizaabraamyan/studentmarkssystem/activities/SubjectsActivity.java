package com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.luizaabraamyan.studentmarkssystem.DBHelper;
import com.example.luizaabraamyan.studentmarkssystem.R;
import com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan
        .studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.objects.Subject;
import com.example.luizaabraamyan
        .studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.adapters.SubjectAdapter;

import java.util.ArrayList;
import java.util.List;

public class SubjectsActivity extends Activity {

    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    SQLiteDatabase db;
    DBHelper dbHelper;

    String idUniversityNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        List<Subject> subjects = new ArrayList<>();

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        idUniversityNum = bundle.getString("universityId");

        Cursor cursor = db.rawQuery("SELECT * FROM subjects " +
                "JOIN teachers_subjects_groups " +
                "ON (teachers_subjects_groups.subjectId = subjects.subjectId)" +
                "JOIN teachers " +
                "ON (teachers_subjects_groups.teacherId = teachers.teacherId) " +
                "WHERE teachers.universityId = '" + idUniversityNum + "';", null);

        int cursorSize = cursor.getCount();
        if (cursorSize != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Subject subject = new Subject();
                    subject.setId(cursor.getInt(cursor.getColumnIndex("subjectId")));
                    subject.setName(cursor.getString(cursor.getColumnIndex("subjectName")));
                    subjects.add(subject);
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(this, "Няма регистрирани предмети!", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_view_subjects);
        adapter = new SubjectAdapter(this, subjects, idUniversityNum);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

}
