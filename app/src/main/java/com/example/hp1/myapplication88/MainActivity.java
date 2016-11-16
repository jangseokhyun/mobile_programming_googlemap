package com.example.hp1.myapplication88;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    myDB my;
    EditText edittext1, edittext2, edittext3;
    TextView section, title, content;
    Button btn1, btn2, btn3;
    SQLiteDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittext1 = (EditText)findViewById(R.id.editText1);
        edittext2 = (EditText)findViewById(R.id.editText2);
        edittext3 = (EditText)findViewById(R.id.editText3);

        section = (TextView)findViewById(R.id.section);
        title = (TextView)findViewById(R.id.title);
        content = (TextView)findViewById(R.id.content);

        btn1 = (Button)findViewById(R.id.button1);
        btn2 = (Button)findViewById(R.id.button2);
        btn3 = (Button)findViewById(R.id.button3);

        my = new myDB(this);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql = my.getWritableDatabase();
                my.onUpgrade(sql, 1, 2);
                sql.close();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql = my.getReadableDatabase();
                Cursor cursor;
                cursor = sql.rawQuery("SELECT * FROM MEMBER;", null);
                String section2 = "분류" + "\r\n";
                String title2 = "제목" + "\r\n";
                String content2 = "내용" + "\r\n";

                while(cursor.moveToNext()) {
                    section2 += cursor.getString(0) + "\r\n";
                    title2 += cursor.getString(1) + "\r\n";
                    content2 += cursor.getString(2) + "\r\n";
                }
                section.setText(section2);
                title.setText(title2);
                content.setText(content2);
                cursor.close();
                sql.close();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql = my.getWritableDatabase();
                sql.execSQL("INSERT INTO member VALUES("+edittext1.getText().toString()+",'"
                        +edittext2.getText().toString()+"','"
                        +edittext3.getText().toString()+"');");
                sql.close();
                Toast.makeText(getApplicationContext(), "정보가 저장되었습니다.", Toast.LENGTH_LONG).show();
            }
        });


    }

    public class myDB extends SQLiteOpenHelper {
        public myDB(Context context) {
            super(context, "human", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table member " +
                    "(section char(10), title char(15), content char(50))");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS member");
            onCreate(db);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}