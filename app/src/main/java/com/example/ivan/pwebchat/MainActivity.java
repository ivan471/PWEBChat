package com.example.ivan.pwebchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference("chats");
    ArrayList<Chat> chats = new ArrayList<>();

    EditText etKetik;
    Button btSend;
    RecyclerView rvChats;
    ChatListAdapter adapter;
    User user;
    SharedPreferences mylocaldata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mylocaldata = getSharedPreferences("mylocaldata", MODE_PRIVATE);
        user = getIntent().getParcelableExtra("user");
        if ( user == null ){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chats.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Chat chat = postSnapshot.getValue( Chat.class );
                    chats.add(chat);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        rvChats = (RecyclerView)findViewById(R.id.rvchats);
        rvChats.setHasFixedSize(true);
        rvChats.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatListAdapter(this, chats);
        rvChats.setAdapter(adapter);
        etKetik=(EditText)findViewById(R.id.etketik);
        btSend=(Button)findViewById(R.id.btsend);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chat chat = new Chat();
                chat.setPesan(etKetik.getText().toString());
                chat.setTanggal(new Date().getTime());
                chat.setSender(user);
                chat.send();
                etKetik.setText("");

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuprofil) {
            Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }else if (item.getItemId() == R.id.menuUser) {
            startActivity(new Intent(MainActivity.this, UserListActivity.class));
        } else if (item.getItemId() == R.id.menuLogout) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        return true;
    }

}
