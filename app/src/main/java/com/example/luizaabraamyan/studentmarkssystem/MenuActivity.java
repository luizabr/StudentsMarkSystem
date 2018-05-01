package com.example.luizaabraamyan.studentmarkssystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity {

    TextView successMessage;
    Button editMark;
    Button viewAllStudents;
    Button logout;
    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        successMessage = findViewById(R.id.successMsgLbl);
        editMark = findViewById(R.id.editMarkBtn);
        viewAllStudents = findViewById(R.id.viewStudentsBtn);
        logout = findViewById(R.id.logoutBtn);
        context = this;
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        editMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditMarkActivity.class);
                context.startActivity(intent);
            }
        });

        viewAllStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                    Intent intent = new Intent(context, ViewAllStudentsActivity.class);
                    context.startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
