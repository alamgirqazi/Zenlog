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
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText txtEmail;
    private  EditText txtPassword;
    private TextView txtViewSignUp;
    private TextView txtViewNotHaveAccount;

    private ProgressDialog progressbar;
private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtViewSignUp = (TextView) findViewById(R.id.txtViewSignUp);
        txtViewNotHaveAccount = (TextView) findViewById(R.id.txtViewNotHaveAccount);
        btnLogin =  (Button) findViewById(R.id.btnLogin);



        txtViewSignUp.setFocusable(false);
        txtViewNotHaveAccount.setFocusable(false);

        txtViewSignUp.setClickable(true);




        progressbar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!= null)
        {
            //directly start profile activity

            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class)); //cannot use 'this' as we are in actionlistener


        }

        // Setting Action listeners

        btnLogin.setOnClickListener(this);
        txtViewSignUp.setOnClickListener(this);


    }



    private void userLogin()
    {
String email = txtEmail.getText().toString().trim();
String password = txtPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {

            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return;

        }
        if (TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

//Validations are ok hence

        progressbar.setMessage("Logging In ...");
        progressbar.show();



        mAuth.signInWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressbar.dismiss();
                        // Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {

                            //start profile activity


                            Toast.makeText(LoginActivity.this, "Login Successfully",

                                    Toast.LENGTH_SHORT).show();
                            finish();
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class)); //cannot use 'this' as we are in actionlistener


                        }

                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {

                            Toast.makeText(LoginActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                            progressbar.dismiss();
                        }
                    }

                });

    }







    @Override
    public void onClick(View v) {

        if(v==btnLogin)
        {
            userLogin();
        }
        if(v==txtViewSignUp)
        {
            //close current activity
            finish();

            startActivity(new Intent( this, MainActivity.class));
        }







    }
}
