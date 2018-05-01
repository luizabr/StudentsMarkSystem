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

    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;
    int newMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mark);

        enterFacNum = findViewById(R.id.enterFacNum);
        search = findViewById(R.id.searchBtn);
        stName = findViewById(R.id.stNameLbl);
        stMark = findViewById(R.id.stMark);
        saveChanges = findViewById(R.id.saveChangesBtn);
        context = this;
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facNum = enterFacNum.getText().toString();
                Cursor cursor = db.rawQuery("SELECT * FROM students " +
                        "WHERE students.studentFacNum = '" + facNum + "';", null);
                if(cursor != null){
                    if(cursor.moveToFirst()) {
                        stName.setText(cursor.getString(cursor.getColumnIndex("studentName")));
                        stMark.setText(cursor.getString(cursor.getColumnIndex("studentMark")));
                    }else {
                        Toast.makeText(context, "No student with this faculty number !", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "No student with this faculty number !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stMark.getText().length() == 0){
                    Toast.makeText(context, "Enter new grade!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(Integer.valueOf(stMark.getText().toString()) <= 1 ||
                        Integer.valueOf(stMark.getText().toString()) >= 7){
                    Toast.makeText(getApplicationContext(),
                            "Enter mark between 2 and 6!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    newMark = Integer.valueOf(stMark.getText().toString());
                    db.execSQL("UPDATE students SET studentMark = '" + newMark + "';");
                    Toast.makeText(getApplicationContext(),
                            "Mark is successfully updated!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MenuActivity.class);
                    context.startActivity(intent);
                }
            }
        });


    }
}
