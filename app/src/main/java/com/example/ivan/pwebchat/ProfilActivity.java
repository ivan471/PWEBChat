package com.example.ivan.pwebchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

public class ProfilActivity extends AppCompatActivity {
    TextView nama,nomor,email;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        nama=(TextView)findViewById(R.id.tv_nama);
        nomor=(TextView)findViewById(R.id.tv_nomor);
        email=(TextView)findViewById(R.id.tv_email);
        user = getIntent().getParcelableExtra("user");

        nama.setText(user.getNama());
        nomor.setText(user.getTelepon());
        email.setText(user.getEmail());
    }


}
