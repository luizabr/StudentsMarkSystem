package com.example.luizaabraamyan.studentmarkssystem;

import android.app.Activity;
import android.app.AlertDialog;
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
                    showMessage("Error", "Please enter your identification number!");
                    return;
                } else if(idUniversityNum.getText().length() != 5) {
                    showMessage("Error", "Your identification number is wrong!");
                } else if(idUniversityNum.getText().length() == 5){
                    cursor = db.rawQuery("SELECT * FROM teachers " +
                            "WHERE universityId = '" + idUniversityNum.getText() + "';", null);
                    if(cursor.getCount() == 0){
                        showMessage("Error", "Your identification number is not in the database!");
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

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText()
    {
        idUniversityNum.setText("");
    }

    public EditText getIdUniversityNum() {
        return idUniversityNum;
    }
}
