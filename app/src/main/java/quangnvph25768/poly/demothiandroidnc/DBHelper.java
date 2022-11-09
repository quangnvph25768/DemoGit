package quangnvph25768.poly.demothiandroidnc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "ASS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String dbNews = "CREATE TABLE BaiBao(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, link TEXT NOT NULL)";
        db.execSQL(dbNews);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS BaiBao");
            onCreate(db);
        }

    }
}
