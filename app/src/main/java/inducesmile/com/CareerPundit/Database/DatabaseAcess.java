package inducesmile.com.CareerPundit.Database;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import inducesmile.com.CareerPundit.LoginRegister.User;

/**
 * Created by test on 14-03-2016.
 */
public class DatabaseAcess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAcess instance;


    public DatabaseAcess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }


    public static DatabaseAcess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAcess(context);
        }
        return instance;
    }


    public void open() {
        this.database = openHelper.getWritableDatabase();
    }


    public void close() {
        if (database != null) {
            this.database.close();
        }
    }


    public List<String> getQuestions() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT questions FROM questions", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public String interestResult(int questionLikes){
        String hollandCodes="";
        Cursor cursor;
        cursor = database.rawQuery("SELECT type FROM questions where q_id="+questionLikes+";", null);
        cursor.moveToFirst();
        hollandCodes=cursor.getString(0);
        cursor.close();
        return hollandCodes;

    }
    public ArrayList<String> getOccupationsFromCode(int[] code){
        ArrayList<String> list =new ArrayList<String>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT onetsoc_code FROM interests i1 WHERE EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.g' AND data_value="+code[0]+" AND onetsoc_code=i1.onetsoc_code) AND EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.h' AND data_value="+code[1]+" AND onetsoc_code=i1.onetsoc_code) AND  EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.i' AND data_value="+code[2]+" AND onetsoc_code=i1.onetsoc_code)\n" +
                "UNION ALL\n" +
                "SELECT DISTINCT onetsoc_code FROM interests i2 WHERE EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.g' AND data_value="+code[0]+" AND onetsoc_code=i2.onetsoc_code) AND EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.h' AND data_value="+code[2]+" AND onetsoc_code=i2.onetsoc_code) AND  EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.i' AND data_value="+code[1]+" AND onetsoc_code=i2.onetsoc_code)\n" +
                "UNION ALL\n" +
                "SELECT DISTINCT onetsoc_code FROM interests i3 WHERE EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.g' AND data_value="+code[1]+" AND onetsoc_code=i3.onetsoc_code) AND EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.h' AND data_value="+code[0]+" AND onetsoc_code=i3.onetsoc_code) AND  EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.i' AND data_value="+code[2]+" AND onetsoc_code=i3.onetsoc_code)\n" +
                "UNION ALL\n" +
                "SELECT DISTINCT onetsoc_code FROM interests i4 WHERE EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.g' AND data_value="+code[1]+" AND onetsoc_code=i4.onetsoc_code) AND EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.h' AND data_value="+code[2]+" AND onetsoc_code=i4.onetsoc_code) AND  EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.i' AND data_value="+code[0]+" AND onetsoc_code=i4.onetsoc_code)\n" +
                "UNION ALL\n" +
                "SELECT DISTINCT onetsoc_code FROM interests i5 WHERE EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.g' AND data_value="+code[2]+" AND onetsoc_code=i5.onetsoc_code) AND EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.h' AND data_value="+code[0]+" AND onetsoc_code=i5.onetsoc_code) AND  EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.i' AND data_value="+code[1]+" AND onetsoc_code=i5.onetsoc_code)\n" +
                "UNION ALL\n" +
                "SELECT DISTINCT onetsoc_code FROM interests i6 WHERE EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.g' AND data_value="+code[2]+" AND onetsoc_code=i6.onetsoc_code) AND EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.h' AND data_value="+code[1]+" AND onetsoc_code=i6.onetsoc_code) AND  EXISTS (SELECT * FROM interests WHERE element_id='1.B.1.i' AND data_value="+code[0]+" AND onetsoc_code=i6.onetsoc_code);", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;

    }
    public ArrayList<String> getOccupations(ArrayList<String> ocCode){
        String where="";
        ArrayList<String> occupation=new ArrayList<String>();
        for(int i=0;i<ocCode.size();i++){
            if (i==ocCode.size()-1)
                where=where+"'"+ocCode.get(i)+"'";
            else
                where=where+"'"+ocCode.get(i)+"'"+",";
        }
        Cursor cursor = database.rawQuery("SELECT title FROM occupation_data WHERE onetsoc_code IN("+where+");", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            occupation.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return occupation;

    }

}
