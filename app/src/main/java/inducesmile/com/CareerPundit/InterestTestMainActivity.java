package inducesmile.com.CareerPundit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import inducesmile.com.CareerPundit.Database.DatabaseAcess;
import inducesmile.com.CareerPundit.LoginRegister.Login;
import inducesmile.com.CareerPundit.LoginRegister.User;
import inducesmile.com.CareerPundit.LoginRegister.UserLocalStore;
import inducesmile.com.CareerPundit.Navigation.MainActivity;
import inducesmile.com.CareerPundit.R;

public class InterestTestMainActivity extends ActionBarActivity implements View.OnClickListener {
    ImageButton imageButton1,imageButton2;
    FrameLayout frameLayout;
    String photos[];
    int i,q;
    Drawable drawable;
    TextView Questions,occupations;
    List<String> questions;
    public int like,dislike,questionLikes[],questionDislikes[];
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_test_main);
        String photos[]={"one","two","three","four","five","six","seven","eight","nine","ten","eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen","twenty","twentyone","twentytwo","twentythree","twentyfour","twentyfive","twentysix","twentyseven","twentyeight","twentynine","thirty","thirtyone","thirtytwo","thirtythree","thirtyfour","thirtyfive","thirtysix","thirtyseven","thirtyeight","thirtynine","fourty","fourtyone","fourtytwo","fourtythree","fourtyfour","fourtyfive","fourtysix","fourtyseven","fourtyeight"};
        i=0;q=0;like=0;dislike=0;
        this.photos=photos;
        occupations=(TextView)findViewById(R.id.textView2);
        questionLikes=new int[50];
        questionDislikes=new int[50];
        frameLayout=(FrameLayout) findViewById(R.id.fl);
        imageButton1=(ImageButton) findViewById(R.id.likeB);
        imageButton2=(ImageButton) findViewById(R.id.dislikeB);
        frameLayout.setBackgroundResource(R.drawable.one);
        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        DatabaseAcess databaseAccess = DatabaseAcess.getInstance(this);
        databaseAccess.open();
        Questions=(TextView)findViewById(R.id.ques);
        questions=databaseAccess.getQuestions();
        databaseAccess.close();
        Questions.setText(questions.get(i));
        for(int i=0;i<50;i++){
            this.questionLikes[i]=0;
            this.questionDislikes[i]=0;
        }

        /*Score score=new Score(questionLikes,questionDislikes);
        Initialize initialize=new Initialize(this);
        initialize.init(score, new InitializeCallback() {
            @Override
            public void done(Score score) {
                questionLikes=score.like;
                questionDislikes=score.dislike;
            }
        });
*/

    }



    @Override
    public void onClick(View view) {
        this.view=view;
        switch(view.getId()){
            case R.id.likeB:{
                this.questionLikes[like]=i+1;
                like++;
                nextQuestion();
                break;
            }
            case R.id.dislikeB:{
                this.questionDislikes[dislike]=i+1;
                dislike++;
                nextQuestion();
                break;
            }
        }



    }

    private void nextQuestion() {
        this.i++;
        if(i<=photos.length-1){
            String currentQuestion=questions.get(i);
            Questions.setText(currentQuestion);
            int resourceId=getResources().getIdentifier(photos[i],"drawable",getPackageName());
            frameLayout.setBackgroundResource(resourceId);

        }
        else{
            TestLocalStore testLocalStore=new TestLocalStore(this);
            testLocalStore.finishTest(true);
            ScoreFunctions scoreFunctions=new ScoreFunctions(this);
            Score interestScore=new Score(questionLikes,questionDislikes);
            scoreFunctions.storeInterestScoreData(interestScore);
            scoreFunctions.setScored(true);

            GenerateResult generateResult=new GenerateResult(this);
            generateResult.storeInterestResultDataInBackground(interestScore, new GetResultCallback() {

                @Override
                public void done(ArrayList<String> result) {
                   Intent intent=new Intent(view.getContext(),InterestMainActivity.class);
                    intent.putStringArrayListExtra("occode", result);
                    startActivity(intent);
                   finish();
                }
            });
        }
    }


}
