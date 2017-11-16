package inducesmile.com.CareerPundit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.text.ParseException;
import java.util.Calendar;

import android.widget.ImageView;
import android.widget.ListAdapter;

import java.sql.Time;
import java.util.Calendar;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import inducesmile.com.CareerPundit.Navigation.CustomAdapter;
import inducesmile.com.CareerPundit.Navigation.ItemObject;
import inducesmile.com.CareerPundit.Navigation.MainActivity;

import static inducesmile.com.CareerPundit.R.layout.abc_activity_chooser_view_list_item;


/**
 * A simple {@link Fragment} subclass.
 */
public class Assessment extends Fragment implements AdapterView.OnItemClickListener {
    public Assessment() {
        // Required empty public constructor
    }
    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.assessment, container, false);
        ListView lv = (ListView) view.findViewById(R.id.listView);
        String list[]={"Interest","Personality","Aptitude"};
        List<ItemObject> listViewItems = new ArrayList<ItemObject>();
        ScoreFunctions scoreFunctions=new ScoreFunctions(getActivity());
        try {
            Score score=scoreFunctions.isTestScored();
            if(score!=null){
                listViewItems.add(new ItemObject("Interest", R.drawable.tick));
                listViewItems.add(new ItemObject("Personality",0));
                listViewItems.add(new ItemObject("Aptitude", 0));

            }
            else {
                listViewItems.add(new ItemObject("Interest", 0));
                listViewItems.add(new ItemObject("Personality",0));
                listViewItems.add(new ItemObject("Aptitude", 0));

            }


        } catch (ParseException e) {
            e.printStackTrace();
        }




        lv.setAdapter( new CustomAdapter(getActivity(),listViewItems));
        lv.setOnItemClickListener(this);
        return view;

    }




    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch(i){
                case 0: {
                    TestLocalStore testLocalStore=new TestLocalStore(getActivity());
                    Calendar calendar=Calendar.getInstance();
                    testLocalStore.storeTestData(new Test("i", calendar.getTime().toString()));
                    startActivity(new Intent(getActivity(), InterestTestMainActivity.class));
                    getActivity().finish();
                    break;
                }
                case 1:{
                    ScoreFunctions scoreFunctions=new ScoreFunctions(getActivity());
                    try {
                        if(scoreFunctions.isTestScored()==null){
                            Toast.makeText(getActivity(),"Complete Interest Test",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(),"Test is currently not availible",Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                case 2:{
                    ScoreFunctions scoreFunctions=new ScoreFunctions(getActivity());
                    try {
                        if(scoreFunctions.isTestScored()==null){
                            Toast.makeText(getActivity(),"Complete Interest Test",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(),"Test is currently not availible",Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
    }
}
