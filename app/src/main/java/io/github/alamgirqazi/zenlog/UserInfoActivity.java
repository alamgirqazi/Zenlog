package io.github.alamgirqazi.zenlog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper myDb;
    private EditText editTextName, editTextCity, editTextAge,editTextEmail;
    private Button btnSave;
    private FirebaseAuth mAuth;
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        myDb = new DatabaseHelper(this);


  editTextName= (EditText)findViewById(R.id.editTextName);
        editTextCity= (EditText)findViewById(R.id.editTextCity);
        editTextAge= (EditText)findViewById(R.id.editTextAge);
        editTextEmail= (EditText)findViewById(R.id.editTextEmail);

        btnSave= (Button) findViewById(R.id.btnSave);


            btnSave.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        user_name = user.getEmail();

editTextEmail.setText(user_name);

        editTextEmail.setFocusable(false);

    }


    @Override
    public void onClick(View v) {

        if(v==btnSave)

        {
            if(  editTextName.getText().toString().equals("") || editTextCity.getText().toString().equals("") || editTextAge.getText().toString().equals("") )
            {
                Toast.makeText(UserInfoActivity.this,"Error! Some fields are missing",Toast.LENGTH_LONG).show();

            }
else
            {

 boolean isInserted = myDb.insertData(editTextEmail.getText().toString(),
        editTextName.getText().toString(),
        editTextCity.getText().toString(),
        editTextAge.getText().toString()
);

            if (isInserted=true)
            {
                Toast.makeText(UserInfoActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
            }
                else
                Toast.makeText(UserInfoActivity.this,"Error",Toast.LENGTH_LONG).show();

        }

    }}
}
