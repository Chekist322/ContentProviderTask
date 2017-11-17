package com.example.batrakov.contentproviderdatabase;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.batrakov.contentproviderdatabase.sqlite.DBContract;

public class AddDBItemActivity extends AppCompatActivity {

    Spinner mSpinner;
    EditText mNameEditText;
    EditText mAgeEditText;
    Button mAddButton;
    Button mClearDatabaseButton;

    String mTableChoice = "foxes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        mSpinner = findViewById(R.id.table_choice);
        mNameEditText = findViewById(R.id.name_edit_text);
        mAgeEditText = findViewById(R.id.age_edit_text);
        mAddButton = findViewById(R.id.add_button);
        mClearDatabaseButton = findViewById(R.id.clear_button);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTableChoice = getResources().getStringArray(R.array.choice)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTableChoice = "foxes";
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRowToDatabase();
            }
        });

        mClearDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTables();
            }
        });
    }

    private void addRowToDatabase() {
        String name = String.valueOf(mNameEditText.getText());
        String age = String.valueOf(mAgeEditText.getText());

        ContentValues values = new ContentValues();
        values.put(DBContract.Entry.COLUMN_NAME_NAME, name);
        values.put(DBContract.Entry.COLUMN_NAME_AGE, age);

        switch (mTableChoice) {
            case "foxes":
                System.out.println("adding fox");
                getContentResolver().insert(DBContract.FIRST_TABLE_CONTENT_URI, values);
                break;
            case "badgers":
                getContentResolver().insert(DBContract.SECOND_TABLE_CONTENT_URI, values);
                break;
        }
    }

    private void clearTables() {
        getContentResolver().delete(Uri.parse("content://"
                + DBContract.CONTENT_AUTHORITY + "/"), null, null);
    }
}
