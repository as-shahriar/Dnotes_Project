package com.shahriar.asif.dnotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
    Button cancle,save,delete;
    EditText title,text;
    AlertDialog.Builder malert;
    String titles,texts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        delete = (Button) findViewById(R.id.deleteid);
        cancle = (Button) findViewById(R.id.cancelid2);
        save = (Button) findViewById(R.id.saveid2);
        title = (EditText) findViewById(R.id.titleid2);
        text = (EditText) findViewById(R.id.textid2);

        title.setText(MainActivity.stitle);
        text.setText(MainActivity.stext);

        delete.setOnClickListener(new View.OnClickListener() {     //problem unsolved
            @Override
            public void onClick(View v) {

                malert=new AlertDialog.Builder(Main3Activity.this);
                malert.setTitle("Delete");
                malert.setMessage("Are you sure to delete "+title.getText()+"?");
                malert.setCancelable(false);
                malert.setIcon(R.drawable.ic_action_delete);
                malert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.database.execSQL("delete from info where id='"+MainActivity.sid+"';");
                        Toast.makeText(Main3Activity.this,"Deleted",Toast.LENGTH_SHORT).show();

                        Cursor cursor = MainActivity.database.rawQuery("select * from info;",null);
                        if (cursor.moveToFirst()){
                            do {
                                int id=cursor.getInt(0);
                                if(MainActivity.sid<id){

                                    MainActivity.database.execSQL("update info set id='"+(id-1)+"' where id='"+id+"';");

                                }
                            }while (cursor.moveToNext());
                        }
                        cursor.close();

                        onBackPressed();
                    }
                });

                malert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                malert.create();
                malert.show();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titles = title.getText().toString();
                texts = text.getText().toString();
                if(titles.isEmpty() || texts.isEmpty())
                    Toast.makeText(Main3Activity.this,"Missing Title and Text",Toast.LENGTH_SHORT).show();
                else {
                    MainActivity.database.execSQL("update info set title='" + titles + "',text='" + texts + "' where id='"+MainActivity.sid+"';");

                    Toast.makeText(Main3Activity.this,"Updated",Toast.LENGTH_SHORT).show();
                    onBackPressed();

                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(Main3Activity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
