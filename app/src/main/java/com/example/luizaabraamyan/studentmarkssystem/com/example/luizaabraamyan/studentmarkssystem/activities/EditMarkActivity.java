package com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.activities;

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

import com.example.luizaabraamyan.studentmarkssystem.DBHelper;
import com.example.luizaabraamyan.studentmarkssystem.R;

public class EditMarkActivity extends Activity {

    EditText enterFacNum;
    Button search;
    Button saveChanges;
    Button home;
    TextView stName;
    EditText stMark;

    DBHelper dbHelper;
    SQLiteDatabase db;
    Context context;

    String idUniversityNum;
    int subjectId;
    int groupId;
    int oldMark;
    int newMark;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mark);

        enterFacNum = findViewById(R.id.enterFacNum);
        search = findViewById(R.id.searchBtn);
        saveChanges = findViewById(R.id.saveChangesBtn);
        home = findViewById(R.id.homeBtn);
        stName = findViewById(R.id.stNameLbl);
        stMark = findViewById(R.id.stMark);

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        context = this;

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        idUniversityNum = bundle.getString("idUniversityNum");
        subjectId = bundle.getInt("subjectId");
        groupId = bundle.getInt("groupId");
        oldMark = bundle.getInt("mark");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facNum = enterFacNum.getText().toString();

                Cursor cursor = db.rawQuery("SELECT * FROM students" +
                        " WHERE students.studentFacNum = '" + facNum + "';", null);

                int cursorSize = cursor.getCount();
                if (cursorSize != 0) {
                    if (cursor.moveToFirst()) {
                        stName.setText(cursor.getString(
                                cursor.getColumnIndex("studentName")));
                        stMark.setText(cursor.getString(
                                cursor.getColumnIndex("studentTempMark")));
                    } else {
                        Toast.makeText(context,
                                "Няма студент с такъв факултетен номер !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context,
                            "Няма студент с такъв факултетен номер !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stMark.getText().length() == 0) {
                    Toast.makeText(context, "Въведете нова оценка!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Integer.valueOf(stMark.getText().toString()) <= 1 ||
                        Integer.valueOf(stMark.getText().toString()) >= 7) {
                    Toast.makeText(getApplicationContext(),
                            "Въведете оценка между 2 и 6!", Toast.LENGTH_SHORT).show();
                    return;
                } else {


                    if(Integer.valueOf(stMark.getText().toString()) <= 1 || Integer.valueOf(stMark.getText().toString()) >= 7){
                        Toast.makeText(getApplicationContext(),
                                "Въведете оценка между 2 и 6!", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        newMark = Integer.valueOf(stMark.getText().toString());
                        db.execSQL("UPDATE students SET studentTempMark = '" + newMark
                                + "' WHERE students.studentFacNum = " + enterFacNum.getText() + ";");

                        db.execSQL("UPDATE students_marks " +
                                "SET markId = (SELECT marks.markId FROM marks " +
                                "WHERE marks.markId = students_marks.markId " +
                                "AND marks.markNumber = '" + newMark + "' );");

                        Toast.makeText(getApplicationContext(),
                                "Оценката е успешно променена!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("idUniversityNum", idUniversityNum);
                intent.putExtra("subjectId", subjectId);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });
    }
}
