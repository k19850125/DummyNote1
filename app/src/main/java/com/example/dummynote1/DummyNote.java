package com.example.dummynote1;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class DummyNote extends AppCompatActivity implements ListView.OnItemClickListener {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummynote);
        listView = (ListView) findViewById(R.id.listView);
//        listView.setEmptyView(findViewById(R.id.empty));
        listView.setOnItemClickListener(this);
        mDbHelper = new DB(this).open();
        setAdapter();

    }

//    private String[] note_array = {
//            "Activities",
//            "Services",
//            "Content Providers",
//            "Broadcast Receiver"
//    };

    private DB mDbHelper;
    private Cursor mNotesCursor;

    private void setAdapter() {
        mDbHelper = new DB(this);
        mDbHelper.open();
        mNotesCursor = mDbHelper.getAll();
        startManagingCursor(mNotesCursor);
        String[] from = new String[]{"note", "created"};
        int[] to = new int[]{R.id.text1, R.id.text2};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.simple_list_item1,
                mNotesCursor, from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
    }

    int count = 0;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                    count++;
                    mDbHelper.create(count + " . note");
                    setAdapter();

                break;
            case 2:
                if(b==0){
                    mDbHelper.deleteAll();
                    count = 0;
                }
                else if(b>1){
                mDbHelper.delete(a);
                setAdapter();
                }
                break;
            case 3:
                mDbHelper.update(a, "");
                setAdapter();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "新增").setIcon(android.R.drawable.ic_menu_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 2, 0, "刪除").setIcon(android.R.drawable.ic_menu_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 3, 0, "修改").setIcon(android.R.drawable.ic_menu_edit).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    long a;
    int b;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        a = id;
        b = position;


    }
}
