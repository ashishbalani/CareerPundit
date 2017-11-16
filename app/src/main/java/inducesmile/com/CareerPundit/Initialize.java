package inducesmile.com.CareerPundit;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by test on 21-03-2016.
 */
public class Initialize {
    Context context;
    public Initialize(Context context){
        this.context=context;
    }

    public void init(Score score,InitializeCallback initializeCallback){
        new initAsyncTask(score,initializeCallback).execute();
    }

    private class initAsyncTask extends AsyncTask<Void, Void, Score>{
        Score score;
        InitializeCallback initializeCallback;
        public initAsyncTask(Score score, InitializeCallback initializeCallback) {
          this.score=score;
            this.initializeCallback=initializeCallback;
        }

        @Override
        protected Score doInBackground(Void... voids) {
            for(int i=0;i<50;i++){
                score.like[i]=0;
                score.dislike[i]=0;
            }
            return score;
        }
        protected void onPostExecute(Score score) {
            super.onPostExecute(score);
            initializeCallback.done(score);
        }
    }
}
