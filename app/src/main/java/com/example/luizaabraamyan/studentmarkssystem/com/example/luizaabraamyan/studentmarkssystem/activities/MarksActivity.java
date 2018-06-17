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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luizaabraamyan.studentmarkssystem.DBHelper;
import com.example.luizaabraamyan.studentmarkssystem.com.example
        .luizaabraamyan.studentmarkssystem.adapters.MarkAdapter;
import com.example.luizaabraamyan.studentmarkssystem.R;
import com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan
        .studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.objects.Student;

import java.util.ArrayList;
import java.util.List;

public class MarksActivity extends Activity {

    private RecyclerView recyclerView;
    private MarkAdapter adapter;
    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

    Button save;
    Button home;
    TextView facNum;

    String idUniversityNum;
    int subjectId;
    int groupId;
    int mark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        final List<Student> students = new ArrayList<>();

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        context = this;

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        idUniversityNum = bundle.getString("idUniversityNum");
        subjectId = bundle.getInt("subjectId");
        groupId = bundle.getInt("groupId");

        save = findViewById(R.id.saveBtn);
        home = findViewById(R.id.homeBtn);

        Cursor cursor = db.rawQuery("SELECT * FROM students " +
                "JOIN groups " +
                "ON (groups.groupId = students.groupId) " +
                "WHERE groups.groupId = '" + groupId + "';", null);

        final int cursorSize = cursor.getCount();
        if (cursorSize != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Student student = new Student();
                    student.setId(cursor.getInt(
                            cursor.getColumnIndex("studentId")));
                    student.setName(cursor.getString(
                            cursor.getColumnIndex("studentName")));
                    student.setFacNum(cursor.getString(
                            cursor.getColumnIndex("studentFacNum")));
                    students.add(student);
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(this,
                    "Няма регистрирани студенти в тази група!", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_view_students);
        adapter = new MarkAdapter(this, students);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < cursorSize; i++) {
                    View view = recyclerView.getChildAt(i);
                    EditText studentMark = view.findViewById(R.id.studentMark);
                    facNum = view.findViewById(R.id.facNum);

                    if (studentMark.getText().length() == 0) {
                        mark = 0;
                    } else if (Integer.valueOf(studentMark.getText().toString()) <= 1 ||
                            Integer.valueOf(studentMark.getText().toString()) >= 7) {
                        Toast.makeText(getApplicationContext(),
                                "Въведете оценка от 2 до 6!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        mark = Integer.valueOf(studentMark.getText().toString());
                    }
                    db.execSQL("UPDATE students SET studentTempMark = " +
                            "'" + mark + "' WHERE students.studentFacNum = " + facNum.getText() + ";");

                    db.execSQL("INSERT INTO students_marks SELECT students.studentId, markNumber FROM students " +
                            "JOIN students_marks ON(students_marks.studentId = students.studentId) " +
                            "JOIN marks ON(students_marks.markId = marks.markId) " +
                            "JOIN groups ON(students.groupId = groups.groupId) " +
                            "JOIN teachers_subjects_groups ON(teachers_subjects_groups.groupId = groups.groupId) " +
                            "JOIN teachers ON(teachers_subjects_groups.teacherId = teachers.teacherId) " +
                            " WHERE students.studentFacNum = " + facNum.getText() +
                            " AND teachers_subjects_groups.subjectId = '" + subjectId +
                            "' AND teachers.universityId = '" + idUniversityNum + "' AND marks.markNumber = '" + mark +
                            "';");
                }
                Toast.makeText(getApplicationContext(),
                        "Оценката е успешно записана!", Toast.LENGTH_SHORT).show();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("idUniversityNum", idUniversityNum);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("groupId", groupId);
                intent.putExtra("mark", mark);
                context.startActivity(intent);
            }
        });
    }
}

