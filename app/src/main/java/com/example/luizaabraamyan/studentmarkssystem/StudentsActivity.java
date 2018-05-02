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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StudentsActivity extends Activity {

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    SQLiteDatabase db;
    DBHelper dbHelper;
    int groupId;
    Button save;
    TextView facNum;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        final List<Student> students = new ArrayList<>();

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        context = this;

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        groupId = bundle.getInt("groupId");
        save = findViewById(R.id.saveBtn);


        Cursor cursor = db.rawQuery("SELECT * FROM students " +
                "JOIN groups ON (groups.groupId = students.groupId)"
                + " WHERE groups.groupId = '" + groupId + "';", null);

        final int cursorSize = cursor.getCount();

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Student student = new Student();
                    student.setId(cursor.getInt(
                            cursor.getColumnIndex("studentId")));
                    student.setName(cursor.getString(
                            cursor.getColumnIndex("studentName")));
                    student.setFacNum(cursor.getString(
                            cursor.getColumnIndex("studentFacNum")));
                    student.setMark(cursor.getInt(
                            cursor.getColumnIndex("studentMark")));
                    students.add(student);
                }while(cursor.moveToNext());
            }
        }else{
            Toast.makeText(this,
                    "Няма регистрирани студенти в тази група!", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_view_students);
        adapter = new StudentAdapter(this, students);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < cursorSize; i++){
                    View view = recyclerView.getChildAt(i);
                    EditText studentMark = view.findViewById(R.id.studentMark);
                    facNum = view.findViewById(R.id.facNum);

                    if(studentMark.getText().length() == 0){
                        Toast.makeText(getApplicationContext(),
                                "Всички оценки трябва да са попълнени!", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(Integer.valueOf(studentMark.getText().toString()) <= 1 ||
                             Integer.valueOf(studentMark.getText().toString()) >= 7){
                        Toast.makeText(getApplicationContext(),
                                "Въведете оценка от 2 до 6!", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        int mark = Integer.valueOf(studentMark.getText().toString());
                        db.execSQL("UPDATE students SET studentMark = " +
                                "'" + mark + "' WHERE students.studentFacNum = " + facNum.getText() + ";");
                    }
                }
                Intent intent = new Intent(context, MenuActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
