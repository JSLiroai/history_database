package com.example.history;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    sampleData sampleData = new sampleData();
    String dbName = "history.db";
    int dbVersion = 1;
    DBHelper dbHelper;
    SQLiteDatabase db;
    String sql,data;
    ListView ListView;
    ArrayList<String> Datas = new ArrayList<String>();
    ArrayAdapter<String> Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this, dbName, null, dbVersion);
        db = dbHelper.getWritableDatabase();

        //쿼리를 통해 history.db에 있는 값들을 가져와서 Datas라는 리스트에 저장
        sql = "SELECT * FROM history;";
        Cursor cursor = db.rawQuery(sql,null);
        try{
            if(cursor.getCount() > 0){
                while(cursor.moveToNext()){
                    Datas.add(cursor.getString(1));
                }
            }
            else{
            }
        }finally{
            cursor.close();
        }

        Button add = (Button) findViewById(R.id.add);
        Button delete = (Button) findViewById(R.id.delete);
        ListView = (ListView)findViewById(R.id.list);

        if(!Datas.isEmpty()){
            Adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Datas);
            ListView.setAdapter(Adapter);
        }

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.add: //추가 버튼을 클릭했을 경우
                        if (Datas.isEmpty()) { //Datas 리스트가 비어있을 때 만약 비어있다면 값을 추가하고 list와 listview연
                            data = sampleData.sampleReturn();
                            //data = lineStrArr.getText().toString();
                            //데이터를 저장할 것이므로 경로 스트링 값이 리턴되는 메소드를 불러와서 그 값을 data에 저장해준다.
                            sql = String.format("INSERT INTO history VALUES(NULL,'%s');", data); //db에 데이터를 추가하는 쿼리
                            db.execSQL(sql);
                            Datas.add(data);
                            Adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, Datas);
                            ListView.setAdapter(Adapter);
                        }
                        //Datas 안에 값이 존재하는 경우
                        else {
                            data = sampleData.sampleReturn();
                            //data = lineStrArr.getText().toString();
                            //데이터를 저장할 것이므로 경로 스트링 값이 리턴되는 메소드를 불러와서 그 값을 data에 저장해준다.
                            sql = String.format("INSERT INTO history VALUES(NULL,'%s');", data);
                            db.execSQL(sql);
                            Datas.add(data);
                            Adapter.notifyDataSetChanged(); //Adapter에 list의 데이터 값이 변경되었음을 알려주어 사용자가 보고 있는 화면 갱신
                        }
                        break;

                    case R.id.delete: //삭제 버튼을 클릭했을 경우
                        if(!Datas.isEmpty()){
                            sql = "SELECT * FROM history;";
                            Cursor cs = db.rawQuery(sql,null);
                            int count = cs.getCount();
                            cs.moveToLast();
                            int lastid = cs.getInt(0); //커서가 읽은 0번쨰를 lastid로 설정
                            sql = "DELETE FROM history WHERE _id="+lastid+";"; //lastid에 해당하는 정보 삭제
                            db.execSQL(sql);
                            Datas.remove(count-1);
                            Adapter.notifyDataSetChanged();
                            cs.close();
                        }
                        break;

                }
            }
        };
        add.setOnClickListener(buttonListener);
        delete.setOnClickListener(buttonListener);
    }
}