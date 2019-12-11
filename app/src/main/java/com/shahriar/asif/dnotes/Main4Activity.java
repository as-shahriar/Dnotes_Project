package com.shahriar.asif.dnotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {

    ListView list;
    AlertDialog.Builder malert,bio;
    String dbname="note.db";
    Button search11;
    ImageView img;
    EditText editText;

    int ecount=0;
    static SQLiteDatabase database;
    ArrayList<String> arrayoftitle = new ArrayList();
    ArrayList<String> arrayoftext = new ArrayList();
    static String stitle,stext;
    static int sid,maxuser=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main4Activity .this , Main2Activity.class);
                startActivity(intent);
                finish();

            }
        });

        img = (ImageView) findViewById(R.id.file);
        list = (ListView) findViewById(R.id.listid);
        editText = (EditText) findViewById(R.id.search_text11);
        search11 = (Button) findViewById(R.id.search_btn11);
        maxuser=0;

        Intent intent = getIntent();
        String search = intent.getStringExtra("search_value");

        search11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Main4Activity.class);
                i.putExtra("search_value",editText.getText().toString());
                startActivity(i);
                editText.setText("");

            }
        });





        SQLiteDatabase data = getBaseContext().openOrCreateDatabase(dbname,MODE_PRIVATE,null);
        database=data;
        final SQLiteDatabase db =data;
        data.execSQL("create table if not exists info (id INTEGER, title TEXT,text TEXT);");

        Cursor cursor = data.rawQuery("select * from info where title like '%"+search+"%' or text like '%"+search+"%'  ;",null);
        if (cursor.moveToFirst()){
            do{
                arrayoftitle.add(cursor.getString(1));
                arrayoftext.add(cursor.getString(2));
                maxuser=cursor.getInt(0);

            }while (cursor.moveToNext());


        }
        cursor.close();

        if(arrayoftitle.isEmpty()){
            img.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);

        }

        else {

            img.setVisibility(View.INVISIBLE);
            list.setVisibility(View.VISIBLE);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayoftitle);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = list.getItemAtPosition(position).toString();
                Cursor cursor = db.rawQuery("select * from info where title = '"+title+"' ;",null);
                if (cursor.moveToFirst()){
                    do {
                            MainActivity.sid=cursor.getInt(0);
                            MainActivity.stitle = cursor.getString(1);
                            MainActivity.stext = cursor.getString(2);
                            break;

                    }while (cursor.moveToNext());
                }
                cursor.close();

                Intent intent = new Intent(Main4Activity .this,Main3Activity.class);
                startActivity(intent);
                finish();

            }
        });
        list.setLongClickable(true);




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
}
