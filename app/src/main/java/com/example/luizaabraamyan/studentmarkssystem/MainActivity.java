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
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    EditText idUniversityNum;
    Button login;
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
        context = this;
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idUniversityNum.getText().length() == 0) {
                    Toast.makeText(context, "Empty identification number field!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(idUniversityNum.getText().length() != 5) {
                    Toast.makeText(context, "Wrong identification number!", Toast.LENGTH_SHORT).show();
                } else if(idUniversityNum.getText().length() == 5){
                    cursor = db.rawQuery("SELECT * FROM teachers " +
                            "WHERE universityId = '" + idUniversityNum.getText() + "';", null);
                    if(cursor.getCount() == 0){
                        Toast.makeText(context, "Not existing identification number !", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(context, SubjectsActivity.class);
                        intent.putExtra("idUniversityNum", idUniversityNum.getText().toString());
                        context.startActivity(intent);

                    }
                }
                clearText();
            }
        });
        }

    public void clearText()
    {
        idUniversityNum.setText("");
    }

    public EditText getIdUniversityNum() {
        return idUniversityNum;
    }
}
