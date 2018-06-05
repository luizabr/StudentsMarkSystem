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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luizaabraamyan.studentmarkssystem.DBHelper;
import com.example.luizaabraamyan.studentmarkssystem.R;
import com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan
        .studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.objects.Student;
import com.example.luizaabraamyan
        .studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.adapters.LabStudentAdapter;

import java.util.ArrayList;
import java.util.List;

public class LabStudentActivity extends Activity {

    private RecyclerView recyclerView;
    private LabStudentAdapter adapter;
    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

    TextView facNum;
    Button checkAll;
    Button save;
    Button home;

    String idUniversityNum;
    int subjectId;
    int groupId;
    int labId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_lab);

        final List<Student> students = new ArrayList<>();

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        context = this;

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        idUniversityNum = bundle.getString("universityId");
        subjectId = bundle.getInt("subjectId");
        groupId = bundle.getInt("groupId");
        labId = bundle.getInt("labId");

        checkAll = findViewById(R.id.checkAllBtn);
        save = findViewById(R.id.saveCheckedBtn);
        home = findViewById(R.id.homeBtn);


        Cursor cursor = db.rawQuery("SELECT * FROM students " +
                "JOIN students_labs " +
                "ON (students_labs.studentId = students.studentId) " +
                "WHERE students_labs.labId = '" + labId + "';", null);

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
                    student.setIsPresent(cursor.getInt(
                            cursor.getColumnIndex("isPresent")));
                    students.add(student);
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(this,
                    "Няма регистрирани студенти в тази група!", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_view_student_lab);
        adapter = new LabStudentAdapter(this, students);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < cursorSize; i++) {
                    View view = recyclerView.getChildAt(i);

                    CheckBox presenceCheck = view.findViewById(R.id.isPresentBox);
                    presenceCheck.setChecked(true);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < cursorSize; i++) {
                    View view = recyclerView.getChildAt(i);
                    CheckBox presenceCheck = view.findViewById(R.id.isPresentBox);
                    facNum = view.findViewById(R.id.facNum);

                    int isPresent = 0;
                    if (presenceCheck.isChecked()) {
                        isPresent = 1;
                    }

                    presenceCheck.setChecked(true);

                    db.execSQL("UPDATE students_labs " +
                            "SET isPresent = '" + isPresent + "' " +
                            "WHERE students_labs.labId = '" + labId + "';");

                }
                Toast.makeText(getApplicationContext(),
                        "Промените бяха записани успешно!", Toast.LENGTH_SHORT).show();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("universityId", idUniversityNum);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("groupId", groupId);
                intent.putExtra("labId", labId);
                context.startActivity(intent);
            }
        });
    }
}
