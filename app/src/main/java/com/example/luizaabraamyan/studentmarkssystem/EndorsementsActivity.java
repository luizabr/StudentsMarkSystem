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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EndorsementsActivity extends Activity {

    private RecyclerView recyclerView;
    private EndorsementAdapter adapter;
    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

    int groupId;
    TextView facNum;

    Button endorseAll;
    Button save;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endorsement);

        final List<Student> students = new ArrayList<>();

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        context = this;

        Intent intent = this.getIntent();
        final Bundle bundle = intent.getExtras();
        groupId = bundle.getInt("groupId");

        endorseAll = findViewById(R.id.endorseAllBtn);
        save = findViewById(R.id.saveBtn);
        home = findViewById(R.id.homeBtn);


        Cursor cursor = db.rawQuery("SELECT * FROM students " +
                "JOIN groups ON (groups.groupId = students.groupId)"
                + " WHERE groups.groupId = '" + groupId + "';", null);

        final int cursorSize = cursor.getCount();

        if(cursorSize != 0){
            if(cursor.moveToFirst()){
                do{
                    Student student = new Student();
                    student.setId(cursor.getInt(
                            cursor.getColumnIndex("studentId")));
                    student.setName(cursor.getString(
                            cursor.getColumnIndex("studentName")));
                    student.setFacNum(cursor.getString(
                            cursor.getColumnIndex("studentFacNum")));
                    student.setIsEndorsed(cursor.getInt(
                            cursor.getColumnIndex("isStudentEndorsed")));
                    student.setNote(cursor.getString(
                            cursor.getColumnIndex("studentNote")));
                    students.add(student);
                }while(cursor.moveToNext());
            }
        }else{
            Toast.makeText(this,
                    "Няма регистрирани студенти в тази група!", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_view_student_endorsement);
        adapter = new EndorsementAdapter(this, students);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        endorseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < cursorSize; i++) {
                    View view = recyclerView.getChildAt(i);

                    CheckBox endorseCheck = view.findViewById(R.id.isEndorsedBox);
                    //See if this works, performClick() might be used
                    endorseCheck.setChecked(true);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < cursorSize; i++){
                    View view = recyclerView.getChildAt(i);
                    CheckBox endorseCheck = view.findViewById(R.id.isEndorsedBox);
                    facNum = view.findViewById(R.id.facNum);

                    int isEndorsed = 0;
                    if(endorseCheck.isChecked()){
                        isEndorsed = 1;
                    }

                    EditText stNote = view.findViewById(R.id.note);
                    String note = stNote.getText().toString();

                        db.execSQL("UPDATE students SET isStudentEndorsed = '" +
                                + isEndorsed + "', studentNote = '" + note + "' WHERE "
                                + "students.studentFacNum = " + facNum.getText() + ";");
                    }

                    Toast.makeText(getApplicationContext(),
                            "Промените бяха записани успешно!", Toast.LENGTH_SHORT).show();

                }
        });

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
