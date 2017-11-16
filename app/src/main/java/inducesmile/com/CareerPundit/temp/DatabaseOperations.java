package inducesmile.com.CareerPundit.temp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by test on 12-03-2016.
 */
public class DatabaseOperations extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public String CREATE_QUERY="CREATE TABLE "+ TableData.Tableinfo.TABLE_NAME+"("+ TableData.Tableinfo.NAME+" TEXT,"+ TableData.Tableinfo.EMAIL+" TEXT,"+ TableData.Tableinfo.PWD+" TEXT,"+ TableData.Tableinfo.DOB+" TEXT);";


    public DatabaseOperations(Context context) {
        super(context, TableData.Tableinfo.DB_NAME, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void putInformation(DatabaseOperations dop,String name,String email,String pass,String dob)
    {
        SQLiteDatabase sq=dop.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TableData.Tableinfo.NAME, name);
        cv.put(TableData.Tableinfo.EMAIL,email);
        cv.put(TableData.Tableinfo.PWD, pass);
        cv.put(TableData.Tableinfo.DOB, dob);
        long k= sq.insert(TableData.Tableinfo.TABLE_NAME,null,cv);
        Log.d("database operations", "one row inserted");
    }

    public Cursor getInformation(DatabaseOperations dop)
    {
        SQLiteDatabase sq =dop.getReadableDatabase();
        String[] columns = {TableData.Tableinfo.NAME,TableData.Tableinfo.EMAIL, TableData.Tableinfo.PWD, TableData.Tableinfo.DOB};
        Cursor cr= sq.query(TableData.Tableinfo.TABLE_NAME,columns,null,null,null,null,null);
        return cr;
    }
}
