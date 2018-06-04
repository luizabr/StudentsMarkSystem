package com.example.luizaabraamyan.studentmarkssystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditMarkActivity extends Activity {

    EditText enterFacNum;
    Button search;
    TextView stName;
    EditText stMark;
    Button saveChanges;
    Button home;

    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

    int newMark;
    int oldMark;

    int groupId;
    int subjectId;
    String idUniversityNum;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mark);

        enterFacNum = findViewById(R.id.enterFacNum);
        search = findViewById(R.id.searchBtn);
        stName = findViewById(R.id.stNameLbl);
        stMark = findViewById(R.id.stMark);
        saveChanges = findViewById(R.id.saveChangesBtn);
        home = findViewById(R.id.homeBtn);
        context = this;
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        groupId = bundle.getInt("groupId");
        subjectId = bundle.getInt("subjectId");
        idUniversityNum = bundle.getString("idUniversityNum");
        oldMark = bundle.getInt("mark");


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facNum = enterFacNum.getText().toString();
//                Cursor cursor = db.rawQuery("SELECT * FROM students " +
//                        "WHERE students.studentFacNum = '" + facNum + "';", null);

//                Cursor cursor = db.rawQuery("SELECT studentName, markNumber FROM students" +
//                        " JOIN students_marks_table" +
//                        " ON(students_marks_table.studentId = students.studentId) JOIN " +
//                        "marks ON(students_marks_table.markId = marks.markId)" +
//                        "WHERE students.studentFacNum = '" + facNum + "';", null);

                Cursor cursor = db.rawQuery("SELECT * FROM students" +
                        " WHERE students.studentFacNum = '" + facNum + "';", null);

//                Cursor cursor2 = db.rawQuery("SELECT markNumber FROM marks JOIN " +
//                        "students_marks_table ON(students_marks_table.markId = marks.markId) " +
//                        "JOIN students ON(students_marks_table.studentId = students.studentId)" +
//                        " WHERE students.studentFacNum = '" + facNum + "';", null);
//
                int cursorSize = cursor.getCount();
//                int cursorSize2 = cursor2.getCount();
                if(cursorSize != 0){
                    if(cursor.moveToFirst()) {
                        stName.setText(cursor.getString(
                                cursor.getColumnIndex("studentName")));
                        stMark.setText(String.valueOf(oldMark));
                    }else {
                        Toast.makeText(context,
                                "Няма студент с такъв факултетен номер !", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context,
                            "Няма студент с такъв факултетен номер !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stMark.getText().length() == 0){
                    Toast.makeText(context, "Въведете нова оценка!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(Integer.valueOf(stMark.getText().toString()) <= 1 ||
                        Integer.valueOf(stMark.getText().toString()) >= 7){
                    Toast.makeText(getApplicationContext(),
                            "Въведете оценка между 2 и 6!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    newMark = Integer.valueOf(stMark.getText().toString());
//                    db.execSQL("UPDATE students SET studentMark = '" + newMark
//                             + "' WHERE students.studentFacNum = " + enterFacNum.getText() + ";");
//                    db.execSQL("UPDATE students_marks_table " +
//                            "JOIN students ON(students_marks_table.studentId = students.studentId) " +
//                            "JOIN marks ON(students_marks_table.markId = marks.markId) " +
//                            "JOIN groups ON (students.groupId = groups.groupId) " +
//                            "JOIN teachers_subjects_groups ON (teachers_subjets_groups.groupId = groups.groupId) " +
//                            "JOIN teachers ON (teachers_subjets_groups.teacherId = teachers.teacherId) " +
//                            "SET students_marks_table.markId = marks.markId WHERE marks.markNumber = '" + newMark +
//                            "' AND students.studentFacNum = " + enterFacNum.getText() +
//                            " AND teachers_subjects_groups.subjectId = '" + subjectId +
//                            "' AND teachers.universityId = '" + idUniversityNum + "';");

//                    db.execSQL("UPDATE students_marks_table " +
//                            "SET students_marks_table.markId = (SELECT marks.markId " +
//                            "WHERE students_marks_table.markId = marks.markId " +
//                            "AND marks.markNumber = '" + newMark +"' WHERE EXISTS " +
//                            "SELECT * FROM marks WHERE marks.markId = students_marks_table.markId);");


                    db.execSQL("UPDATE students_marks_table " +
                            "SET markId = (SELECT marks.markId FROM marks " +
                            "WHERE marks.markId = students_marks_table.markId " +
                            "AND marks.markNumber = '" + newMark +"' );");





//                            "JOIN students ON(students_marks_table.studentId = students.studentId) " +
//                            "JOIN marks ON(students_marks_table.markId = marks.markId) " +
//                            "JOIN groups ON (students.groupId = groups.groupId) " +
//                            "JOIN teachers_subjects_groups ON (teachers_subjets_groups.groupId = groups.groupId) " +
//                            "JOIN teachers ON (teachers_subjets_groups.teacherId = teachers.teacherId) " +
//
//                            "' AND students.studentFacNum = " + enterFacNum.getText() +
//                            " AND teachers_subjects_groups.subjectId = '" + subjectId +
//                            "' AND teachers.universityId = '" + idUniversityNum + "';");

                    Toast.makeText(getApplicationContext(),
                            "Оценката е успешно променена!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, MenuActivity.class);
//                    context.startActivity(intent);
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("groupId", groupId);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("idUniversityNum", idUniversityNum);
//                intent.putExtra("bundle", bundle);
                context.startActivity(intent);
            }
        });
    }
}
