package io.github.alamgirqazi.zenlog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.id.input;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private TextView txtViewUsername;
    private Button btnLogout;
private DatabaseReference databaseReference;
    private EditText txtCity, txtFullname;
    private Button btnSave;
    private Button btnChat;
    private Toast toast;
    private Toolbar toolbar;
    private long lastBackPressTime = 0;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_about:

                startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                return true;

   case R.id.action_settings:

       startActivity(new Intent(getApplicationContext(),Main2Activity.class));
       return true;


            case R.id.action_github:

       startActivity(new Intent(getApplicationContext(),GithubActivity.class));
                return true;


            case R.id.action_logout:

                mAuth.signOut();
                finish();
                Toast.makeText(ProfileActivity.this, "Logging out",

                        Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // Code for Back Buttton

    @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
            toast = Toast.makeText(this, "Press back again to close this app", Toast.LENGTH_SHORT);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbutton, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null)
        {
finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

databaseReference = FirebaseDatabase.getInstance().getReference();

FirebaseUser user = mAuth.getCurrentUser();



//        txtCity = (EditText) findViewById(R.id.txtCity);
//        txtFullname = (EditText) findViewById(R.id.txtFullname);
//        btnSave = (Button) findViewById(R.id.btnSave);


        String input = user.getEmail();

        int  index = input.indexOf("@");
        if (index > 0)
            input = input.substring(0, index);

        getSupportActionBar().setTitle("Welcome " + input + " !");


        txtViewUsername = (TextView) findViewById(R.id.txtViewUsername);
        txtViewUsername.setText("Welcome " + input + " !");



        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnChat = (Button) findViewById(R.id.btnChat);

btnChat.setOnClickListener(this);
btnLogout.setOnClickListener(this);
//btnSave.setOnClickListener(this);

    }

    //we need a java object
    private void saveUserInfo()
    {
            String name = txtFullname.getText().toString().trim();
            String city = txtCity.getText().toString().trim();

//Define object of the class we created

        UserInformation userInformation = new UserInformation(name,city);

        //need to log in with each users unique ID
        // we get it using Firebase Auth object

        FirebaseUser user = mAuth.getCurrentUser();

            // create a node inside that database with User ID
        //and store information inside

        databaseReference.child(user.getUid()).setValue(userInformation);

    Toast.makeText(this,"Information Saved",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {


        if(v==btnLogout)
        {
            mAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }        if(v==btnChat)
        {
//            finish();
            startActivity(new Intent(this,ChatroomActivity.class));
        }

            if(v==btnSave)
            {
                saveUserInfo();
            }

    }
}
