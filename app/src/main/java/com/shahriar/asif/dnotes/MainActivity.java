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

public class MainActivity extends AppCompatActivity {

    ListView list;
    AlertDialog.Builder malert,bio;
    String dbname="note.db";
    Button search;
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
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , Main2Activity.class);
                startActivity(intent);
                finish();

            }
        });

        img = (ImageView) findViewById(R.id.file);
        list = (ListView) findViewById(R.id.listid);
        maxuser=0;
        editText = (EditText) findViewById(R.id.search_text);
        search = (Button) findViewById(R.id.search_btn);


        search.setOnClickListener(new View.OnClickListener() {
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

        Cursor cursor = data.rawQuery("select * from info;",null);
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

                Cursor cursor = db.rawQuery("select * from info;",null);
                if (cursor.moveToFirst()){
                    do {
                        sid=cursor.getInt(0);
                        if(position+1==sid) {
                            stitle = cursor.getString(1);
                            stext = cursor.getString(2);
                            break;
                        }
                    }while (cursor.moveToNext());
                }
                cursor.close();

                Intent intent = new Intent(MainActivity.this,Main3Activity.class);
                startActivity(intent);
                finish();

            }
        });
        list.setLongClickable(true);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                malert=new AlertDialog.Builder(MainActivity.this);
                malert.setTitle("Delete");
                malert.setMessage("Are you sure to delete this item?");
                malert.setIcon(R.drawable.ic_action_delete);
                malert.setCancelable(false);
                malert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.database.execSQL("delete from info where id='"+(position+1)+"';");
                        Toast.makeText(MainActivity.this,"Deleted",Toast.LENGTH_SHORT).show();

                        Cursor cursor = database.rawQuery("select * from info;",null);
                        if (cursor.moveToFirst()){
                            do {
                                int id=cursor.getInt(0);
                                if(position+1<id){

                                    database.execSQL("update info set id='"+(id-1)+"' where id='"+id+"';");

                                }
                            }while (cursor.moveToNext());
                        }
                        cursor.close();

                        finish();
                        startActivity(getIntent());
                    }
                });

                malert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                malert.create();
                malert.show();


                return true;
            }
        });



    }


    public void onBackPressed() {
        if(ecount==0) {
            Toast.makeText(MainActivity.this, "Press again to exit", Toast.LENGTH_SHORT).show();
            ecount++;
        }else
            finish();
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
