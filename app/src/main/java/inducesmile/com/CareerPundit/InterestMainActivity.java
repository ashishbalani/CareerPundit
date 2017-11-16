package inducesmile.com.CareerPundit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import inducesmile.com.CareerPundit.Navigation.MainActivity;
import inducesmile.com.CareerPundit.R;

public class InterestMainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_main);
        ArrayList<String> occupations=getIntent().getStringArrayListExtra("occode");
        List<String> display=occupations;
        ListView lv=(ListView)findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,display );
        lv.setAdapter(adapter);


    }

    public void HomePage(View view){
        Intent intent = new Intent(InterestMainActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void Quit(View view){
        finish();
        System.exit(0);
    }

}
