package com.sameera.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editTextTask,editTextID;
    Button btnAddTask,btnRead,btnUpdate,btnDel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editTextTask = findViewById(R.id.editTextnewtask);
        editTextID = findViewById(R.id.editTextID);
        btnAddTask = findViewById(R.id.btn_add);
        btnRead = findViewById(R.id.btn_Read);
        btnUpdate = findViewById(R.id.btn_update);
        btnDel = findViewById(R.id.btn_delete);

        addData();
        viewAll();
        updateData();
        deleteData();
    }

    public void addData(){
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = myDb.insertData(editTextTask.getText().toString());
                if(isInserted == true)
                    Toast.makeText(MainActivity.this,"Task Inserted Successfully",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Task Not Inserted",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewAll(){
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor results =  myDb.getAllData();
                if(results.getCount() ==0){
                    showMassage("Error Message:","Sorry No data available..!");
                    return;
                }
                StringBuffer buffer =new StringBuffer();
                while (results.moveToNext()){
                    buffer.append("ID:- "+results.getString(0)+"\n");
                    buffer.append("TASK:- "+results.getString(1)+"\n \n");

                    showMassage("To Do",buffer.toString());
                }
            }
        });
    }

    public void showMassage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = myDb.updateData(editTextID.getText().toString(),editTextTask.getText().toString());
                if(isUpdate==true)
                    Toast.makeText(MainActivity.this,"Task updated Successfully",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Task Not updated",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteData(){
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer delRows = myDb.deleteData(editTextID.getText().toString());
                if(delRows>0)
                    Toast.makeText(MainActivity.this,"Task Delete Successfully",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Task Delete Successfully",Toast.LENGTH_LONG).show();
            }
        });
    }

}
