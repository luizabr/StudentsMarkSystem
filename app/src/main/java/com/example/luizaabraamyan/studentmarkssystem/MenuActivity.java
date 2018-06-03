package com.example.luizaabraamyan.studentmarkssystem;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity {


    Button addMark;
    Button addEndorsement;
    Button addAttendance;
    Button editMark;
    Button viewAllStudents;
    Button viewAllScholars;
    Button logout;

    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

//    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            subjectId = intent.getExtras().getInt("subjectId");
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("subjectIntent"));
        setContentView(R.layout.activity_menu);

        addMark = findViewById(R.id.addMarkBtn);
        addEndorsement = findViewById(R.id.addEndorsementBtn);
        addAttendance = findViewById(R.id.addAttendanceBtn);
        editMark = findViewById(R.id.editMarkBtn);
        viewAllStudents = findViewById(R.id.viewStudentsBtn);
        viewAllScholars = findViewById(R.id.viewScholarsBtn);
        logout = findViewById(R.id.logoutBtn);

        context = this;
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final int groupId = bundle.getInt("groupId");
        final int subjectId = bundle.getInt("subjectId");

        addMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MarksActivity.class);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });

        addEndorsement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EndorsementsActivity.class);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });

        addAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(context, LabsActivity.class);
               intent.putExtra("groupId", groupId);
               intent.putExtra("subjectId", subjectId);
               context.startActivity(intent);
            }
        });


        editMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditMarkActivity.class);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });

        viewAllStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, ViewAllStudentsActivity.class);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });

        viewAllScholars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, ViewAllScholarsActivity.class);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("groupId", groupId);
                context.startActivity(intent);
            }
        });
    }
}
