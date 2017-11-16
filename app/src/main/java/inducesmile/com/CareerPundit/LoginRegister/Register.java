package inducesmile.com.CareerPundit.LoginRegister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import inducesmile.com.CareerPundit.R;
import inducesmile.com.CareerPundit.temp.DatabaseOperations;


public class Register extends ActionBarActivity implements View.OnClickListener{
    EditText etName, etEmail, etPassword;
    DatePicker datePicker;
    Button bRegister;
    Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etName);
        datePicker=(DatePicker) findViewById(R.id.datePicker);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRegister:
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                String dob =Long.toString(datePicker.getDayOfMonth())+"/"+Long.toString(datePicker.getMonth())+"/"+Long.toString(datePicker.getYear());
              /*  if (!(password.equals(cpass)))
                {
                    Toast.makeText(getBaseContext(), "passwords dont match", Toast.LENGTH_LONG).show();
                    .setText("");
                    userpass.setText("");
                    cnfrmpass.setText("");

                }
                */
                   DatabaseOperations db=new DatabaseOperations(ctx);
                    db.putInformation(db, name, email, password, dob);
                    Toast.makeText(getBaseContext(),"registration success",Toast.LENGTH_LONG).show();

             User user = new User(name,email, password,dob);
                //registerUser(user);
             startActivity(new Intent(this, Login.class));

                break;
        }
    }
/*
    private void registerUser(User user) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                Intent loginIntent = new Intent(Register.this, Login.class);
                startActivity(loginIntent);
            }
        });
    }*/
    }
