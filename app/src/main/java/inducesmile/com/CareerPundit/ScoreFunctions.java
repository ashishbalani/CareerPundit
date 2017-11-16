package inducesmile.com.CareerPundit;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.util.StringTokenizer;

/**
 * Created by test on 14-03-2016.
 */
public class ScoreFunctions {
    SharedPreferences scoreLocalDatabase;
    public ScoreFunctions(Context context){
        scoreLocalDatabase=context.getSharedPreferences("scoreDetails",0);
    }
    public void storeInterestScoreData(Score score) {
        SharedPreferences.Editor testLocalDatabaseEditor = scoreLocalDatabase.edit();
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();

        for (int i = 0; i < score.like.length; i++) {
            str1.append(score.like[i]).append(",");
        }
        for (int i = 0; i < score.dislike.length; i++) {
            str2.append(score.dislike[i]).append(",");
        }
        testLocalDatabaseEditor.putString("like",str1.toString());
        testLocalDatabaseEditor.putString("dislike",str2.toString());
        testLocalDatabaseEditor.commit();
    }
    public void setScored(boolean isScored) {
        SharedPreferences.Editor scoreLocalDatabaseEditor = scoreLocalDatabase.edit();
        scoreLocalDatabaseEditor.putBoolean("isScored", isScored);
        scoreLocalDatabaseEditor.commit();
    }
    public void clearScoreData() {
        SharedPreferences.Editor scoreLocalDatabaseEditor = scoreLocalDatabase.edit();
        scoreLocalDatabaseEditor.clear();
        scoreLocalDatabaseEditor.commit();
    }


    public Score isTestScored() throws ParseException {
        if(scoreLocalDatabase.getBoolean("isScored", false)==false) {
            return null;
        }
        else{
            String like1 = scoreLocalDatabase.getString("like", "");
            String dislike1=scoreLocalDatabase.getString("dislike","");
            StringTokenizer st1 = new StringTokenizer(like1, ",");
            StringTokenizer st2 = new StringTokenizer(dislike1, ",");
            int[] like=new int[50];
            for(int i=0;i<49;i++){
                like[i]=Integer.parseInt(st1.nextToken());
            }
            int[] dislike=new int[50];
            for(int i=0;i<49;i++){
                like[i]=Integer.parseInt(st2.nextToken());
            }
            Score score=new Score(like,dislike);
            return score;
        }
    }

    public void scoreInterestTest(Score score){
        int like=Integer.parseInt(scoreLocalDatabase.getString("like",""));
        int dislike=Integer.parseInt(scoreLocalDatabase.getString("dislike", ""));



    }
}
