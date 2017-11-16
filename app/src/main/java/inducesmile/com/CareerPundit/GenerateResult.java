package inducesmile.com.CareerPundit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import inducesmile.com.CareerPundit.Database.DatabaseAcess;


/**
 * Created by test on 19-03-2016.
 */
public class GenerateResult{
    ProgressDialog progressDialog;
    Context context;
        public GenerateResult(Context context) {
            this.context=context;
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Processing...");
            progressDialog.setMessage("Please wait...");
        }
    public void storeInterestResultDataInBackground(Score score,
                                          GetResultCallback resultCallback) {
        progressDialog.show();
        new storeInterestResultDataAsyncTask(score, resultCallback).execute();
    }

    public class storeInterestResultDataAsyncTask extends AsyncTask<Void,Void,ArrayList<String>> {
        Score interestScore;
        GetResultCallback resultCallback;

        public storeInterestResultDataAsyncTask(Score interestScore, GetResultCallback resultCallback) {
            this.interestScore=interestScore;
            this.resultCallback=resultCallback;

        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            int questionLikes[]=interestScore.like;
            String codeForQues="";
            DatabaseAcess databaseAccess = DatabaseAcess.getInstance(context);
            databaseAccess.open();
            int p=0;
            while(questionLikes[p]!=0){
                codeForQues=codeForQues+databaseAccess.interestResult(questionLikes[p]);
                p++;
            }
            Log.d("codeforq",questionLikes[0]+" "+questionLikes[1]);

            int r=freq(codeForQues, 'R');
            int i=freq(codeForQues,'I');
            int a=freq(codeForQues,'A');
            int s=freq(codeForQues,'S');
            int e=freq(codeForQues,'E');
            int c=freq(codeForQues,'C');
            int abc[]={r,i,a,s,e,c};
            int temp[]=abc;
          //  InsertionSort(temp);
            Integer[] ab=new Integer[temp.length];
            int k = 0;
            for (int value : temp) {
                ab[k++] = Integer.valueOf(value);
            }

            ArrayIndexComparator arrayIndexComparator=new ArrayIndexComparator(ab);
            Integer[] indexes=arrayIndexComparator.createIndexArray();
            Arrays.sort(indexes,arrayIndexComparator);k=0;
            for(int l=indexes.length-1;l>=0;l--){
                temp[l]=indexes[k]+1;
                k++;
            }

            /*for(int k=0;k<temp.length;k++){
                for(int l=0;l<abc.length;l++){
                    if(temp[k]==abc[l]){
                      code[k]=l+1;
                    }
                }
            }*/
            Log.d("value of r", r + "");
            Log.d("value of i", i + "");
            Log.d("value of a", a + "");
            Log.d("value of s", s + "");
            Log.d("value of e", e + "");
            Log.d("value of c", c + "");

            Log.d("value of temp", temp[0] + "");
            Log.d("value of temp", temp[1] + "");
            Log.d("value of temp", temp[2] + "");

            Log.d("codeforq", codeForQues);
            ArrayList<String> occupationCode=databaseAccess.getOccupationsFromCode(temp);
            ArrayList<String> occupation=databaseAccess.getOccupations(occupationCode);

            return occupation;
        }


        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            resultCallback.done(result);
        }
        public int freq(String codeForQues,char toFind )
        {
            int s=0,i=0;
           for(i=0;i<codeForQues.length();i++){
                if(codeForQues.charAt(i)==toFind)
                    s++;
            }
            return s;
        }
        public void InsertionSort( int [ ] num)
        {
            int j,i,key;                     // the number of items sorted so far

            for (j = 1; j < num.length; j++)    // Start with 1 (not 0)
            {
                key = num[ j ];
                for(i = j - 1; (i >= 0) && (num[ i ] < key); i--)   // Smaller values are moving up
                {
                    num[ i+1 ] = num[ i ];
                }
                num[ i+1 ] = key;    // Put the key in its proper location
            }
        }

    }
}
