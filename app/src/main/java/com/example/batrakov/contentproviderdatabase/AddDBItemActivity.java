package com.example.batrakov.contentproviderdatabase;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.batrakov.contentproviderdatabase.sqlite.DBContract;

import java.lang.ref.WeakReference;

/**
 * Activity allow to add new elements to Database and clear tables.
 */
public class AddDBItemActivity extends AppCompatActivity {

    private static final String FOXES = "foxes";
    private static final String DEFAULT_COLOR = "red";

    private static final int INSERT_TOKEN_FOX = 0;
    private static final int INSERT_TOKEN_BADGER = 1;

    private QueryHandler mQueryHandler;
    private EditText mNameEditText;
    private EditText mAgeEditText;
    private Spinner mSpinnerColor;
    private String mTypeChoice = FOXES;
    private String mColorChoice = DEFAULT_COLOR;

    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);

        mQueryHandler = new QueryHandler(getContentResolver(), this);

        setContentView(R.layout.add_item);

        mSpinnerColor = findViewById(R.id.color_choice);
        mNameEditText = findViewById(R.id.name_edit_text);
        mAgeEditText = findViewById(R.id.age_edit_text);
        Spinner spinnerType = findViewById(R.id.type_choice);
        Button addButton = findViewById(R.id.add_button);
        Button clearDatabaseButton = findViewById(R.id.clear_button);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> aParent, View aView, int aPosition, long aId) {
                mTypeChoice = getResources().getStringArray(R.array.type)[aPosition];
                if (mTypeChoice.equals(FOXES)) {
                    mSpinnerColor.setVisibility(View.VISIBLE);
                } else {
                    mSpinnerColor.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> aParent) {
            }
        });

        mSpinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> aParent, View aView, int aPosition, long aId) {
                mColorChoice = getResources().getStringArray(R.array.color)[aPosition];
            }

            @Override
            public void onNothingSelected(AdapterView<?> aParent) {
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {
                String name = mNameEditText.getText().toString();
                String age = mAgeEditText.getText().toString();
                if (!name.equals("") && !age.equals("")) {
                    addRowToDatabase(name, age);
                } else {
                    Toast.makeText(getBaseContext(), "Don't left fields empty", Toast.LENGTH_SHORT).show();
                }

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
    private void addRowToDatabase(String aName, String aAge) {
        ContentValues values = new ContentValues();
        values.put(DBContract.Entry.COLUMN_NAME_NAME, aName);
        values.put(DBContract.Entry.COLUMN_NAME_AGE, aAge);

        switch (mTypeChoice) {
            case "foxes":
                values.put(DBContract.Entry.COLUMN_NAME_COLOR, mColorChoice);
                mQueryHandler.startInsert(INSERT_TOKEN_FOX, null, DBContract.FIRST_TABLE_CONTENT_URI, values);
                break;
            case "badgers":
                mQueryHandler.startInsert(INSERT_TOKEN_BADGER, null, DBContract.SECOND_TABLE_CONTENT_URI, values);
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

    /**
     * Handler for executing long write operation in another thread.
     */
    private static class QueryHandler extends AsyncQueryHandler {

        private WeakReference<AddDBItemActivity> mReference;

        /**
         * Constructor.
         *
         * @param aContentResolver context ContentResolver.
         * @param aActivity current context Activity.
         */
        QueryHandler(ContentResolver aContentResolver, AddDBItemActivity aActivity) {
            super(aContentResolver);
            mReference = new WeakReference<>(aActivity);
        }

        @Override
        protected void onInsertComplete(int token, Object cookie, Uri uri) {
            AddDBItemActivity activity = mReference.get();
            if (activity != null) {
                switch (token) {
                    case INSERT_TOKEN_FOX:
                        Toast.makeText(activity, "+1 foxy", Toast.LENGTH_SHORT).show();
                        break;
                    case INSERT_TOKEN_BADGER:
                        Toast.makeText(activity, "+1 badger", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                activity.mAgeEditText.setText("");
                activity.mNameEditText.setText("");
                activity.mNameEditText.requestFocus();
            }
        }
    }
}
