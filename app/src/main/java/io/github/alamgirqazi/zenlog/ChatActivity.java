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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private Button btn_send;
    private EditText input_msg;
    private TextView chat_conversation;
    private String room_name,user_name;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String temp_key;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_about:

                startActivity(new Intent(getApplicationContext(),AnimationActivity.class));
                return true;
//
//            case R.id.action_settings:
//
//                startActivity(new Intent(getApplicationContext(),AboutActivity.class));
//                return true;


            case R.id.action_github:

                startActivity(new Intent(getApplicationContext(),GithubActivity.class));
                return true;


            case R.id.action_logout:

                mAuth.signOut();
                finish();
                Toast.makeText(ChatActivity.this, "Logging out",

                        Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

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
        setContentView(R.layout.activity_chat);


      btn_send= (Button) findViewById(R.id.btn_send);
        input_msg= (EditText)findViewById(R.id.msg_input);
        chat_conversation= (TextView)findViewById(R.id.textView);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        user_name = user.getEmail();

//        int  index = user_name.indexOf("@");
//        if (index > 0)
//            user_name = user_name.substring(0, index);


        room_name= getIntent().getExtras().get("room_name").toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(room_name);

        getSupportActionBar().setTitle(room_name);


//        setTitle("Room - " + room_name);


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create random keys

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = databaseReference.push().getKey();
                databaseReference.updateChildren(map);


                DatabaseReference message_root = databaseReference.child(temp_key);


                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name", user_name);
                map2.put("msg", input_msg.getText().toString().trim());

                message_root.updateChildren(map2);
                input_msg.setText("");
                input_msg.setFocusable(true);

            }
        });


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


private String chat_user_name, chat_msg;

private void append_chat_conversation(DataSnapshot dataSnapshot)
{
    Iterator i = dataSnapshot.getChildren().iterator();

    while(i.hasNext())
    {

            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
        chat_user_name = (String) ((DataSnapshot)i.next()).getValue();

chat_conversation.append(chat_user_name + " : " + chat_msg +" \n");
    }
}

}
