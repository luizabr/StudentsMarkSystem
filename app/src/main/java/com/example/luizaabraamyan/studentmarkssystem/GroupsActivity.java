package com.example.luizaabraamyan.studentmarkssystem;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends Activity {

    private RecyclerView recyclerView;
    private GroupAdapter adapter;
    SQLiteDatabase db;
    DBHelper dbHelper;
    //Why is this here
//    String groupNumber;
    int subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        List<Group> groups = new ArrayList<>();

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        subjectId = bundle.getInt("subjectId");

        Cursor cursor = db.rawQuery("SELECT * FROM groups " +
                "JOIN teachers_subjects_groups ON (teachers_subjects_groups.groupId = groups.groupId)"
                + " JOIN subjects ON (teachers_subjects_groups.subjectId = subjects.subjectId)"
                + " WHERE subjects.subjectId = '" + subjectId + "';", null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Group group = new Group();
                    group.setId(cursor.getInt(cursor.getColumnIndex("groupId")));
                    group.setGroupNumber(cursor.getInt(cursor.getColumnIndex("groupNumber")));
                    groups.add(group);
                }while(cursor.moveToNext());
            }
        }else{
            Toast.makeText(this,
                    "Няма регистрирани групи за този предмет!", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_view_groups);
        adapter = new GroupAdapter(this, groups);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
