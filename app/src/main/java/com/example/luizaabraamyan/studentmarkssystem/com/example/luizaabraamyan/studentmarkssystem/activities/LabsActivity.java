package com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.luizaabraamyan.studentmarkssystem.DBHelper;
import com.example.luizaabraamyan.studentmarkssystem.com.example.luizaabraamyan
        .studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.objects.Lab;
import com.example.luizaabraamyan
        .studentmarkssystem.com.example.luizaabraamyan.studentmarkssystem.adapters.LabAdapter;
import com.example.luizaabraamyan.studentmarkssystem.R;

import java.util.ArrayList;
import java.util.List;

public class LabsActivity extends Activity {

    private RecyclerView recyclerView;
    private LabAdapter adapter;
    SQLiteDatabase db;
    DBHelper dbHelper;

    String idUniversityNum;
    int subjectId;
    int groupId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labs);

        List<Lab> labs = new ArrayList<>();

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        idUniversityNum = bundle.getString("universityId");
        groupId = bundle.getInt("groupId");
        subjectId = bundle.getInt("subjectId");

        Cursor cursor = db.rawQuery("SELECT * FROM labs " +
                "WHERE labs.subjectID = '" + subjectId + "';", null);

        int cursorSize = cursor.getCount();
        if (cursorSize != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Lab lab = new Lab();
                    lab.setLabId(cursor.getInt(cursor.getColumnIndex("labId")));
                    lab.setPrettyName(cursor.getString(cursor.getColumnIndex("prettyName")));
                    labs.add(lab);
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(this,
                    "Няма регистрирани упражнения!", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_view_labs);
        adapter = new LabAdapter(this, labs, idUniversityNum, subjectId, groupId);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}