package inducesmile.com.CareerPundit.Navigation;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import inducesmile.com.CareerPundit.Assessment;
import inducesmile.com.CareerPundit.InterestTestMainActivity;
import inducesmile.com.CareerPundit.LoginRegister.Login;
import inducesmile.com.CareerPundit.LoginRegister.User;
import inducesmile.com.CareerPundit.LoginRegister.UserLocalStore;
import inducesmile.com.CareerPundit.OccupationSearch;
import inducesmile.com.CareerPundit.Profile;
import inducesmile.com.CareerPundit.R;
import inducesmile.com.CareerPundit.ScoreFunctions;
import inducesmile.com.CareerPundit.Test;
import inducesmile.com.CareerPundit.TestLocalStore;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    String[]titles = {"","Profile", "Assessment", "Job Search", "Help"};
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar topToolBar;
    TestLocalStore testLocalStore;
    UserLocalStore userLocalStore;
    Button beginTest;
    User user;
    TextView tv;
     ImageView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleView=(ImageView)findViewById(R.id.circleView);
        testLocalStore=new TestLocalStore(this);
        userLocalStore=new UserLocalStore(this);
        try{
            Test test=testLocalStore.isTestFinished();
            if(test!=null){

                Fragment fragment = new Assessment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }try{
            user=userLocalStore.getLoggedInUser();
            ContextWrapper cw = new ContextWrapper(this);
            Bitmap dp = BitmapFactory.decodeFile(user.name+".jpg");

        }
        catch (NullPointerException ne){
        }
        beginTest=(Button)findViewById(R.id.beginTest);
        beginTest.setOnClickListener(this);
        tv=(TextView) findViewById(R.id.textView3);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/LDFComicSansLight.ttf");
        tv.setTypeface(face);
        tv.setText(R.string.description);
        topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.header_list, null, false);

        mDrawerList.addHeaderView(listHeaderView);

        List<ItemObject> listViewItems = new ArrayList<ItemObject>();
        listViewItems.add(new ItemObject("Profile", R.drawable.profile));
        listViewItems.add(new ItemObject("Assessment", R.drawable.assessment));
        listViewItems.add(new ItemObject("Occupation Search", R.drawable.jobsearch));
        listViewItems.add(new ItemObject("Help", R.drawable.help));


        mDrawerList.setAdapter(new CustomAdapter(this, listViewItems));

        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };


        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // make Toast when click
               // Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_LONG).show();
                selectItemFragment(position);
            }
        });
    }



    private void selectItemFragment(int position){

        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position) {
            case 1:
                fragment = new Profile();
                break;
            case 2:
                fragment= new Assessment();
                 break;
            case 3:
                fragment = new OccupationSearch();
                break;
            case 4:
             //   fragment = new Assessment();
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(titles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TestLocalStore testLocalStore=new TestLocalStore(this);
        UserLocalStore userLocalStore=new UserLocalStore(this);
        ScoreFunctions scoreFunctions=new ScoreFunctions(this);
        scoreFunctions.clearScoreData();
        testLocalStore.clearTestData();
        userLocalStore.clearUserData();
        scoreFunctions.setScored(false);
        userLocalStore.setUserLoggedIn(false);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, InterestTestMainActivity.class));
        finish();

    }
}
