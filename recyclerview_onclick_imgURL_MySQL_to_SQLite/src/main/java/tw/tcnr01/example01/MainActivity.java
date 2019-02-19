package tw.tcnr01.example01;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private  ArrayList<ExampleItem> mExampleList;

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonInsert,buttonRemove;
    private EditText editTextInsert,editTextRemove;
    private String sqlctl;//mysql語法
    private String TAG = "tcnr01=>";
    private String mId,mName,mImgURL;
    private String mSQL = "CREATE TABLE " + "test" + " ( "
            + "ID INTEGER PRIMARY KEY," + "name TEXT NOT NULL," + "imgURL TEXT NOT NULL"
            + ");";

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-------------抓取遠端資料庫設定執行續------------------------------
        StrictMode.setThreadPolicy(new
                StrictMode.
                        ThreadPolicy.Builder().
                detectDiskReads().
                detectDiskWrites().
                detectNetwork().
                penaltyLog().
                build());
        StrictMode.setVmPolicy(
                new
                        StrictMode.
                                VmPolicy.
                                Builder().
                        detectLeakedSqlLiteObjects().
                        penaltyLog().
                        penaltyDeath().
                        build());
//---------------------------------------------------------------------
        dbHelper = new DBHelper(getApplicationContext(),"i_culture",null,1,"test",mSQL);
        setupViewComponent();

    }

    private void setupViewComponent() {

        buttonInsert = (Button)findViewById(R.id.button_insert);
        buttonRemove = (Button)findViewById(R.id.button_remove);
        editTextInsert = (EditText)findViewById(R.id.edittext_insert);
        editTextRemove = (EditText)findViewById(R.id.edittext_remove);

        buttonInsert.setOnClickListener(this);
        buttonRemove.setOnClickListener(this);

        sqlctl = "SELECT * FROM test ORDER BY id ASC";
        //將MySQL的資料匯入SQL
        Mysqlsel(sqlctl);
        //將SQL裡的資料丟進mExampleList();
        buildExampleList();
       // createExampleList();
        buildRecyclerView();
    }

    private void buildExampleList() {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("test",null,null,null,null,null,null);
        cursor.moveToFirst();
        do {
            mId = cursor.getString(0);//ID
            mName = cursor.getString(1);//name
            mImgURL = cursor.getString(2);//imgURL
            Log.d(TAG,"mId="+mId+" mName= "+mName+" mURL="+mImgURL);

            mExampleList.add(new ExampleItem(mImgURL,mId,mName));

        }while (cursor.moveToNext());

        cursor.close();

       // db.close();
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);//"true"使recyclerview不會因item的多寡而改變大小
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter =  new ExampleAdapter(mExampleList);

        //由於Picasso需要用到context所以需將MainActivity傳過去
        mAdapter.setContext(getApplicationContext());


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //************對mAdaptor設監聽
        mAdapter.setOnItemClickListerner(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position,"Clicked");
            }
        });
    }

//    private void createExampleList() {
//        //************可用巨集做(將從資料庫抓來的資料丟入個別的陣列後,再做巨集,        所以資料的命名最好有規則性  ex:   img01,img02.........)
//        mExampleList = new ArrayList<>();
//        mExampleList.add(new ExampleItem(R.drawable.ic_android,"Line 1","Line 2"));
//        mExampleList.add(new ExampleItem(R.drawable.ic_audio,"Line 3","Line 4"));
//        mExampleList.add(new ExampleItem(R.drawable.ic_sun,"Line 5","Line 6"));
//        //*********************************
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_insert:
                //取得edittext上的數字作為index的位置
                int position = Integer.parseInt(editTextInsert.getText().toString());
                insertItem(position);
                break;

            case R.id.button_remove:
                //取得edittext上的數字作為index的位置
                position = Integer.parseInt(editTextRemove.getText().toString());
                removeItem(position);
                break;
        }
    }
    //移除一個item
    private void removeItem(int position) {
        //判斷position是否合理(ex position=3 list的size(item個數)卻只有1 會導致閃退)
        if(position>=mExampleList.size()){
            return;
        }
        //在第幾個位置上移除item
        mExampleList.remove(position);

       /* //使整個recyclerview重整,沒有插入或移除的動畫
        mAdapter.notifyDataSetChanged();*/

       //僅移除某位置的item,有移除的動畫
       mAdapter.notifyItemRemoved(position);
    }
    //新增一個item
    private void insertItem(int position) {
        //判斷position是否合理(ex position=3 list的size(item個數)卻只有1 會導致閃退)
        if(position>mExampleList.size()){
            return;
        }
        //在第幾個位置上插入item
        mExampleList.add(position,new ExampleItem(mImgURL,"New Item"+position,"Line"+position));

        /*//使整個recyclerview重整,沒有插入或移除的動畫
        mAdapter.notifyDataSetChanged();*/

        //僅在某位置新增item,有插入的動畫
        mAdapter.notifyItemInserted(position);
    }

    //改變item上的data
    private void changeItem(int position,String text){
        mExampleList.get(position).changeText1(text);
        //告知某個位置item上的值有了改變
        mAdapter.notifyItemChanged(position);
    }
//將的得到的MySQL資料匯入SQL
        private void Mysqlsel(String sqlctl) {

            db = dbHelper.getWritableDatabase();


            try {
                String result = DBConnector.executeQuery(sqlctl);
                mExampleList = new ArrayList<>();
                /**************************************************************************
                 * SQL 結果有多筆資料時使用JSONArray
                 * 只有一筆資料時直接建立JSONObject物件 JSONObject
                 * jsonData = new JSONObject(result);
                 **************************************************************************/
                //幾筆資料
                JSONArray jsonArray = new JSONArray(result);
                // ---

                if (jsonArray.length() > 0) { // MySQL 連結成功有資料

                    db.delete("test",null,null);// 匯入前,刪除所有SQLite資料

                    //幾個欄位
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonData = jsonArray.getJSONObject(i);
                        // // 取出 jsonObject 中的字段的值的空格

                        ContentValues newRow = new ContentValues();

                        Iterator itt = jsonData.keys();

                        while (itt.hasNext()) {
                            String key = itt.next().toString();
                            Log.d(TAG, "key=" + key);
                            String value = jsonData.getString(key);
                            if (value == null) {
                                continue;
                            } else if ("".equals(value.trim())) {
                                continue;
                            } else {
                                jsonData.put(key, value.trim());
                            }
                            //Log.d(TAG,"value= "+value);
                            newRow.put(key, value); // 動態找出有幾個欄位

                            db.insert("test","NULL",newRow);

                        /*switch (key) {
                            case "ID":
                                mId = value;
                                break;

                            case "name":
                                mName = value;
                                break;

                            case "imgURL":
                                mImgURL = value;
                                break;
                        }*/
                        }

                        //mExampleList.add(new ExampleItem(mImgURL, mId, mName));


                    }
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }

            //db.close();

    }


}
