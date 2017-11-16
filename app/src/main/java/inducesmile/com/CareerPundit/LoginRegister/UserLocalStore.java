package inducesmile.com.CareerPundit.LoginRegister;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tundealao on 29/03/15.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("name", user.name);
        userLocalDatabaseEditor.putString("email", user.email);
        userLocalDatabaseEditor.putString("password", user.password);
        userLocalDatabaseEditor.putString("dob", user.dob);
        userLocalDatabaseEditor.commit();
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    public User getLoggedInUser() {
        if(userLocalDatabase.getBoolean("testFinished", false)==false){
            return null;
        }
        else{
            String name=userLocalDatabase.getString("name","");
            String email=userLocalDatabase.getString("email","");
            String pwd=userLocalDatabase.getString("password","");
            String dob=userLocalDatabase.getString("dob","");
            User user=new User(name,email,pwd,dob);
            return user;
        }
    }
}

