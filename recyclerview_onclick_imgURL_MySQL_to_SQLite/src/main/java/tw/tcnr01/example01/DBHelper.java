package tw.tcnr01.example01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public String sCreateTableCommand;
    // 資料庫名稱
    private static final String DB_FILE = "I_culture.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;
    // 資料表名稱
    //private static final String DB_TABLE = "test";    // 資料庫物件，固定的欄位變數
    private static  String DB_TABLE ;    // 資料庫物件，固 定的欄位變數

    //一支app在創建時是沒有SQLite 且不能用其他packagename的資料庫
   /* private static final String crTBsql = "CREATE TABLE " + DB_TABLE + " ( "
            + "id INTEGER PRIMARY KEY," + "name TEXT NOT NULL," + "grp TEXT,"
            + "address TEXT);";*/
   private static  String crTBsql;

    private static SQLiteDatabase database;
    private String TAG = "tcnr01=>";

    //----------------------------------------------
    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    public static SQLiteDatabase getDatabase(Context context){
        if (database == null || !database.isOpen())
        {
            database = new DBHelper(context, DB_FILE, null, VERSION,DB_TABLE,crTBsql)
                    .getWritableDatabase();
        }
        return database;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,String tbname,String sql){
        super(context, name, factory, version);
        DB_TABLE = tbname;
        crTBsql = sql;
        //super(context, "friends.db", null, 1);
        sCreateTableCommand = "";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(crTBsql);//建立一個新的member table
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.d(TAG, "onUpgrade()");

        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        onCreate(db);
    }

    public long insertRec(String b_name, String b_grp, String b_address) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues rec = new ContentValues();
        rec.put("name", b_name);//"name為欄位名稱"
        rec.put("grp", b_grp);
        rec.put("address", b_address);
        long rowID = db.insert(DB_TABLE, null, rec);//真正寫入資料庫
        db.close();
        return rowID;
    }

    public int RecCount() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE;//後面可再加條件
        Cursor recSet = db.rawQuery(sql, null);
        return recSet.getCount();
    }

    public String FindRec(String tname) {
        SQLiteDatabase db = getReadableDatabase();
        String fldSet = null;
        String sql = "SELECT * FROM " + DB_TABLE +
                " WHERE name LIKE ? ORDER BY id ASC";// ?是參數
        String[] args = {"%" + tname + "%"};

        Cursor recSet = db.rawQuery(sql, args);//取出多筆資料,  args 將會放在 ? 裡

        int columnCount = recSet.getColumnCount();//取得一列有幾個欄位
        Log.d(TAG,"ans:"+recSet.getCount());
        if (recSet.getCount() != 0)
        {
            recSet.moveToFirst();//移到第一筆資料

            fldSet = recSet.getString(0) + " "
                    +recSet.getString(1) + " "
                    +recSet.getString(2) + " "
                    +recSet.getString(3) + "\n";

            while (recSet.moveToNext())
            {
                for (int i = 0; i < columnCount; i++)
                {
                    fldSet += recSet.getString(i) + " ";
                }
                fldSet +="\n";
            }
        }
        recSet.close();
        db.close();
        return fldSet;
    }




// ---------

}
