package com.gangula.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    EditText editTitle, editDescription, mFUpdate;
    Button addData, viewData, deleteData, updateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        addData = findViewById(R.id.insrtData);
        viewData = findViewById(R.id.vData);
        updateData = findViewById(R.id.udtData);
        deleteData = findViewById(R.id.delData);

        // calling for the addData function.
        addData();
        viewData();
        updateData();
        deleteData();
    }

    // set on click listener to the insert data
    public void addData(){
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTitle = findViewById(R.id.taskTitle);
                editDescription = findViewById(R.id.taskDescription);

                boolean inserted = myDb.insertData(editTitle.getText().toString(), editDescription.getText().toString());

                // get some output to the screen
                Log.i("DATA", editTitle.getText().toString() + " " + editDescription.getText().toString());

                if (inserted){
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // view all data
    public void viewData(){
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor result = myDb.getAllData();
                if(result.getCount() == 0){
                    showMessage("Error message", "No data found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()){
                    buffer.append("ID: " + result.getString(0) + "\n");
                    buffer.append("Task Title: " + result.getString(1) + "\n");
                    buffer.append("Description: " + result.getString(2) + "\n");

                }

                showMessage("list of Data", buffer.toString());
            }
        });
    }

    private void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }

    public void updateData(){
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTitle = findViewById(R.id.taskTitle);
                editDescription = findViewById(R.id.taskDescription);
                mFUpdate = findViewById(R.id.mFUpdate);

                Log.i("DATA", mFUpdate.getText().toString());
                boolean isUpdate = myDb.updateData(mFUpdate.getText().toString(), editTitle.getText().toString(), editDescription.getText().toString());
                if(isUpdate){
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteData(){
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleteData = myDb.deleteData(mFUpdate.getText().toString());
                if(deleteData > 0){
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}