package com.example.batrakov.contentproviderdatabase;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.batrakov.contentproviderdatabase.sqlite.DBContract;

/**
 * Activity allow to add new elements to Database and clear tables.
 */
public class AddDBItemActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mAgeEditText;
    private String mTableChoice = "foxes";

    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(R.layout.add_item);

        Spinner spinner = findViewById(R.id.table_choice);
        mNameEditText = findViewById(R.id.name_edit_text);
        mAgeEditText = findViewById(R.id.age_edit_text);
        Button addButton = findViewById(R.id.add_button);
        Button clearDatabaseButton = findViewById(R.id.clear_button);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> aParent, View aView, int aPosition, long aId) {
                mTableChoice = getResources().getStringArray(R.array.choice)[aPosition];
            }

            @Override
            public void onNothingSelected(AdapterView<?> aParent) {
                mTableChoice = "foxes";
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {
                addRowToDatabase();
            }
        });

        clearDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {
                clearTables();
            }
        });
    }

    /**
     * Add new item to chosen table from Database.
     */
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
            default:
                break;
        }
    }

    /**
     * Send uri query to clear all tables.
     */
    private void clearTables() {
        getContentResolver().delete(Uri.parse("content://"
                + DBContract.CONTENT_AUTHORITY + "/"), null, null);
    }
}
