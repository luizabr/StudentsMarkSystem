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

    int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facNum = enterFacNum.getText().toString();
                Cursor cursor = db.rawQuery("SELECT * FROM students " +
                        "WHERE students.studentFacNum = '" + facNum + "';", null);
                if(cursor != null){
                    if(cursor.moveToFirst()) {
                        stName.setText(cursor.getString(
                                cursor.getColumnIndex("studentName")));
                        stMark.setText(cursor.getString(
                                cursor.getColumnIndex("studentMark")));
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
                    db.execSQL("UPDATE students SET studentMark = '" + newMark
                             + "' WHERE students.studentFacNum = " + enterFacNum.getText() + ";");
                    Toast.makeText(getApplicationContext(),
                            "Оценката е успешно променена!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MenuActivity.class);
                    context.startActivity(intent);
                }
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
