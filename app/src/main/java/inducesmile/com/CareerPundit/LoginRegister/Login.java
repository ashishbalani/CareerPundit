package inducesmile.com.CareerPundit.LoginRegister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import inducesmile.com.CareerPundit.Navigation.MainActivity;
import inducesmile.com.CareerPundit.R;
import inducesmile.com.CareerPundit.temp.DatabaseOperations;


public class Login extends ActionBarActivity implements View.OnClickListener {
    Button bLogin;
    TextView registerLink;
    EditText etEmail, etPassword;
    Context ctx=this;
    UserLocalStore userLocalStore;
    public String NAME,EMAIL;

    @Override
    protected void onStart() {
        super.onStart();
      }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        bLogin = (Button) findViewById(R.id.bLogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        registerLink = (TextView) findViewById(R.id.tvRegisterLink);
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        bLogin.setOnClickListener(this);
        registerLink.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bLogin: {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                User user = new User("",email, password,"");
                DatabaseOperations dop = new DatabaseOperations(ctx);
                Cursor cr = dop.getInformation(dop);
                NAME = "";
                this.EMAIL=email;
                cr.moveToFirst();
                boolean login_status = false;
                do {
                    if (email.equals(cr.getString(1)) && (password.equals(cr.getString(2)))) {
                        login_status = true;
                        NAME = cr.getString(0);
                    }
                } while (cr.moveToNext());

                if (login_status) {
                    Toast.makeText(getBaseContext(), "login success...\n WELCOME " + NAME, Toast.LENGTH_LONG).show();
                    user = new User("", email, password, "");
                    logUserIn(user);
                } else {
                    Toast.makeText(getBaseContext(), "login failed", Toast.LENGTH_LONG).show();
              }
             //   authenticate(user);
                break;
            }
            case R.id.tvRegisterLink:{
                Intent registerIntent = new Intent(Login.this, Register.class);
                startActivity(registerIntent);
                break;

            }
            case R.id.sign_in_button:{
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                Log.w("ads","ffsfs");
                startActivityForResult(signInIntent, 1);
                break;
            }

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Log.w("lds","ffsfs");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            Log.v("before account","oyoyoy");
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.v("ss","after account");
            // Signed in successfully, show authenticated UI.
            User user = new User(account.getGivenName(),account.getEmail(),"","");
            Log.v("ss","after user");
            logUserIn(user);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getBaseContext(), "login failed", Toast.LENGTH_LONG).show();

        }
    }
    /*
    private void authenticate(User user) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchUserDataAsyncTask(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                    showErrorMessage();
                } else {
                    logUserIn(returnedUser);
                }
            }
        });
    }
*/
    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Incorrect user details");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void logUserIn(User returnedUser) {
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        startActivity(new Intent(this, MainActivity.class));
    }
}
