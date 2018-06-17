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
import android.widget.Toast;

import com.example.luizaabraamyan.studentmarkssystem.DBHelper;
import com.example.luizaabraamyan.studentmarkssystem.R;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    EditText idUniversityNum;
    Button login;
    Button GDPRBtn;

    SQLiteDatabase db;
    DBHelper dbHelper;
    Cursor cursor;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idUniversityNum = findViewById(R.id.universityID);
        login = findViewById(R.id.loginBtn);
        GDPRBtn = findViewById(R.id.GDPRBtn);
        context = this;
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idUniversityNum.getText().length() == 0) {
                    Toast.makeText(context,
                            "Полето е празно!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (idUniversityNum.getText().length() != 5) {
                    Toast.makeText(context,
                            "Грешен идентификационен номер!", Toast.LENGTH_SHORT).show();
                } else if (idUniversityNum.getText().length() == 5) {
                    cursor = db.rawQuery("SELECT * FROM teachers "
                            + "WHERE universityId = '"
                            + idUniversityNum.getText()
                            + "';", null);
                    if (cursor.getCount() == 0) {
                        Toast.makeText(context,
                                "Несъществуващ идентификационен номер!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(context, SubjectsActivity.class);
                        intent.putExtra("universityId", idUniversityNum.getText().toString());
                        context.startActivity(intent);
                    }
                }
                clearText();
            }
        });

        GDPRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < 2; i++){
                    Toast.makeText(context,
                            "Здравейте,Поверителността и сигурността на личните данни " +
                                    "винаги е била от първостепенно значение. В тази връзка, " +
                                    "Ви информираме, че считано от 25.05.2018 г. влизат в сила нови " +
                                    "европейски изисквания за защита на личните данни. Съхраняването" +
                                    "на личните данни на студентите, се осъществява съгласно Политика за" +
                                    "обработване и защита на лични данни на студенти.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void clearText() {
        idUniversityNum.setText("");
    }
}
