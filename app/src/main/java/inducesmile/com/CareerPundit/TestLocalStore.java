package inducesmile.com.CareerPundit;

import android.content.Context;
import android.content.SharedPreferences;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by test on 12-03-2016.
 */
public class TestLocalStore {
    SharedPreferences testLocalDatabase;
    public TestLocalStore(Context context){
        testLocalDatabase = context.getSharedPreferences("testDetails", 0);
    }
    public void storeTestData(Test test) {
        SharedPreferences.Editor testLocalDatabaseEditor = testLocalDatabase.edit();
        testLocalDatabaseEditor.putString("testId",test.testId);
        testLocalDatabaseEditor.putString("startTime", test.startTime);
        testLocalDatabaseEditor.commit();
    }
    public void clearTestData() {
        SharedPreferences.Editor userLocalDatabaseEditor = testLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();

    }
    public void startTest(boolean startTest){
        SharedPreferences.Editor testLocalDatabaseEditor = testLocalDatabase.edit();
        testLocalDatabaseEditor.putBoolean("testStarted", startTest);
        testLocalDatabaseEditor.commit();
    }
    public void finishTest(boolean finishTest){
        SharedPreferences.Editor testLocalDatabaseEditor = testLocalDatabase.edit();
        testLocalDatabaseEditor.putBoolean("testFinished", finishTest );
        testLocalDatabaseEditor.commit();
    }
    public Test isTestFinished() throws ParseException {
        if(testLocalDatabase.getBoolean("testFinished", false)==false){
            return null;
        }
        else{
            String testId=testLocalDatabase.getString("testId","");
            String startTime=testLocalDatabase.getString("startTime","");
            Test test=new Test(testId,startTime);
            return test;
        }
    }
}
