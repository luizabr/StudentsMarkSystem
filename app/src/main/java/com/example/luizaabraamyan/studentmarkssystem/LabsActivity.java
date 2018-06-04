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

public class LabsActivity extends Activity {

    private RecyclerView recyclerView;
    private LabAdapter adapter;
    SQLiteDatabase db;
    DBHelper dbHelper;
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
        groupId = bundle.getInt("groupId");
        final int subjectId = bundle.getInt("subjectId");

        Cursor cursor = db.rawQuery("SELECT * FROM labs "
                + " WHERE labs.subjectID = '" + subjectId + "';", null);

        int cursorSize = cursor.getCount();
        if (cursorSize != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Lab lab = new Lab();
                    lab.setLabId(cursor.getInt(cursor.getColumnIndex("labId")));
                    lab.setPrettyName(cursor.getString(cursor.getColumnIndex("prettyName")));
//                    lab.setIsPresent(cursor.getInt(cursor.getColumnIndex("isPresent")));
                    labs.add(lab);
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(this,
                    "Няма регистрирани упражнения!", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_view_labs);
        adapter = new LabAdapter(this, labs);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}