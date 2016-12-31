package io.github.alamgirqazi.zenlog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;


//To add click functionality we implement
public class MainActivity extends AppCompatActivity implements View.OnClickListener{



   private Button btnRegister;
    private EditText txtPassword;
    private EditText txtEmail;
    private TextView txtViewSignIn;
    private TextView txtViewAlreadyUsing;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog progressbar;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
progressbar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();



        if(mAuth.getCurrentUser()!= null)
        {
            //directly start profile activity

            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class)); //cannot use 'this' as we are in actionlistener


        }



    //initialize Views

        btnRegister = (Button)findViewById(R.id.btnRegister);
         txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtViewSignIn = (TextView)findViewById(R.id.txtViewSignIn);
        txtViewAlreadyUsing = (TextView)findViewById(R.id.txtViewAlreadyUsing);

        txtViewSignIn.setFocusable(false);
        txtViewAlreadyUsing.setFocusable(false);

        txtViewSignIn.setClickable(true);

    btnRegister.setOnClickListener(this);
        txtViewSignIn.setOnClickListener(this);

    }


    private void registerUser() {

        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {

            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return;

        }
        if (TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(password) && (TextUtils.isEmpty(email))) {

            Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

//Validations are ok hence

        progressbar.setMessage("Registering User ...");
        progressbar.show();



        // [START create_user_with_email]

        mAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                       // Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {

                            Toast.makeText(MainActivity.this, "Registered Successfully",

                                    Toast.LENGTH_SHORT).show();
                            progressbar.hide();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));


                        }

                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {

                            Toast.makeText(MainActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                            progressbar.dismiss();
                        }


                    }

                });

                    }

                    @Override
                    public void onClick(View v) {

                        if (v == btnRegister) {
                            registerUser();

                        }

                        if (v == txtViewSignIn) {
                            //Open Login Activity
                       finish();
                       startActivity(new Intent(this, LoginActivity.class));
                        }


                    }
                }
