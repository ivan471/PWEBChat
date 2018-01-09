package com.example.ivan.pwebchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Date;

public class UserListActivity extends AppCompatActivity {

    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myuser = database.getReference("users");

    ArrayList<User> users = new ArrayList<>();
    RecyclerView rvusers;
    UserListAdapter adapter;
    LinearLayout linearLayout;
    CardView cardView;
    User user;
    SharedPreferences mylocaldata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        mylocaldata = getSharedPreferences("mylocaldata", MODE_PRIVATE);
        user = getIntent().getParcelableExtra("user");
        myuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue( User.class );
                    users.add(user);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        rvusers =(RecyclerView)findViewById(R.id.rvusers);
        rvusers.setHasFixedSize(true);
        rvusers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserListAdapter(this,users);
        rvusers.setAdapter(adapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuhome) {
            Intent intent = new Intent(UserListActivity.this, MainActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
        } else if(item.getItemId() == R.id.menuprofil) {
            Intent intent = new Intent(UserListActivity.this, ProfilActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menuLogout) {
            startActivity(new Intent(UserListActivity.this, LoginActivity.class));
            finish();
        }
        return true;
    }

}
