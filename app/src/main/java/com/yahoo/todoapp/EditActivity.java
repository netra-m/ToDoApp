package com.yahoo.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditActivity extends ActionBarActivity {

    private String taskText;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        taskText = getIntent().getStringExtra("taskText");
        position = getIntent().getIntExtra("position",-1);

        EditText etOldText = (EditText) findViewById(R.id.etTaskText);
        etOldText.setText(taskText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSubmit(View V) {
        this.finish();
    }

    public void saveItem(View v) {
        EditText etTaskText = (EditText) findViewById(R.id.etTaskText);
        String itemText = etTaskText.getText().toString();

        Intent data = new Intent();
        data.putExtra("editedText",itemText);
        data.putExtra("position",position);
        setResult(RESULT_OK,data);

        finish();
    }
}
