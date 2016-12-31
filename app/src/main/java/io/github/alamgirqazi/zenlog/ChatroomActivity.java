package io.github.alamgirqazi.zenlog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ChatroomActivity extends AppCompatActivity {

    private Button btn_add_room;
    private EditText room_name;
    private EditText room_name_edittext;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private FirebaseAuth mAuth;
    private ListView listView;
    //inorder to access Firebase database

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbutton, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_about:

                startActivity(new Intent(getApplicationContext(),AnimationActivity.class));
                return true;

//            case R.id.action_settings:
//
//                startActivity(new Intent(getApplicationContext(),AboutActivity.class));
//                return true;


            case R.id.action_github:

                startActivity(new Intent(getApplicationContext(),GithubActivity.class));
                return true;


            case R.id.action_logout:

                mAuth.signOut();
                Toast.makeText(ChatroomActivity.this, "Logging out",

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);


            btn_add_room = (Button) findViewById(R.id.btn_add_room);
        room_name = (EditText) findViewById(R.id.room_name_edittext);
        listView = (ListView) findViewById(R.id.listView);
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Chat Groups");


        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_of_rooms);
        listView.setAdapter(arrayAdapter);

btn_add_room.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        //inorder to update the database, we use a map

        Map<String,Object> map = new HashMap<String, Object>();
        map.put(room_name.getText().toString().trim(),"");
        root.updateChildren(map);
//





        Toast.makeText(ChatroomActivity.this, "group added ", Toast.LENGTH_LONG).show();
        room_name.setText("");
//        room_name_edittext.setText("");
        room_name.setFocusable(true);

    }
});


root.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        //we use this only

        //we use iterator to go through the database and read changes

        Iterator i = dataSnapshot.getChildren().iterator();
        Set<String> set = new HashSet<String>();

        while(i.hasNext())

        {
            set.add(((DataSnapshot)i.next()).getKey());

        }
        list_of_rooms.clear();
        list_of_rooms.addAll(set);

        //to see changes
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                //casting our view to get clicked listview item
                intent.putExtra("room_name",((TextView)view).getText().toString());
                startActivity(intent);

            }
        });

    }
}
