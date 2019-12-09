package com.shahriar.asif.dnotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    Button cancle,save;
    EditText title,text;

    String titles,texts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        cancle = (Button) findViewById(R.id.cancelid);
        save = (Button) findViewById(R.id.saveid);
        title = (EditText) findViewById(R.id.titleid);
        text = (EditText) findViewById(R.id.textid);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titles = title.getText().toString();
                texts = text.getText().toString();
                titles=titles.trim();
                if(titles.isEmpty() || texts.isEmpty())
                    Toast.makeText(Main2Activity.this,"Missing Title or Text",Toast.LENGTH_SHORT).show();
                else {

                    MainActivity.database.execSQL("Insert into info (id,title,text) values('" + (MainActivity.maxuser + 1) + "','" + titles + "','" + texts + "');");

                    Toast.makeText(Main2Activity.this,"Saved",Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(Main2Activity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}


