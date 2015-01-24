package com.yahoo.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    private static final int REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();

        readItemsFromFile();

        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        lvItems.setAdapter(itemsAdapter);

        items.add("Complete TODO app");
        items.add("Read up on Android Intro");

        setUpListViewListener();

    }

    private void setUpListViewListener() {

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItemsToFile();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {

                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("taskText", items.get(pos));
                intent.putExtra("position", pos);
                startActivityForResult(intent, REQUEST_CODE, null);
            }
        });
    }

    private void readItemsFromFile() {
        File filesDir = getFilesDir();
        File todoItemsFile = new File(filesDir, "todoTasks.txt");
        try {

            items = new ArrayList<String>(FileUtils.readLines(todoItemsFile));

        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItemsToFile() {
        File filesDir = getFilesDir();
        File todoItemsFile = new File(filesDir, "todoTasks.txt");
        try {
            FileUtils.writeLines(todoItemsFile, items);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onAddItem(View v) {
        EditText etnewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etnewItem.getText().toString();
        itemsAdapter.add(itemText);
        etnewItem.setText("");
        writeItemsToFile();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String editedText = data.getExtras().getString("editedText");
            int position = data.getExtras().getInt("position");

            if (position != -1) {
                items.set(position, editedText);
                writeItemsToFile();
            }
            itemsAdapter.notifyDataSetChanged();


        }
    }
}
