package quangnvph25768.poly.demothiandroidnc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class NewsDAO {
    SQLiteDatabase db;

    public NewsDAO(Context context){
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertNews(BaiBao baiBao){
        ContentValues values = new ContentValues();
        values.put("title",baiBao.getTitle());
        values.put("link",baiBao.getLink());
         Log.d("zzzzzzz",baiBao.toString());
        return db.insert("BaiBao",null,values);
    }

    @SuppressLint("Range")
    public List<BaiBao> getData(String sql, String...selectionArgs){
        List<BaiBao> list = new ArrayList<BaiBao>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            BaiBao baiBao = new BaiBao();
            baiBao.setId(Integer.parseInt(c.getString(c.getColumnIndex("id"))));
            baiBao.setTitle(c.getString(c.getColumnIndex("title")));
            baiBao.setLink(c.getString(c.getColumnIndex("link")));
            list.add(baiBao);
        }
        return list;
    }

    public List<BaiBao> getAll(){
        String sql = "SELECT * FROM BaiBao";
        return getData(sql);
    }
}
