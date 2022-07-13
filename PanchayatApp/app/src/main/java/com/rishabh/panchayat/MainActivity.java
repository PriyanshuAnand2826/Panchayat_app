package com.rishabh.panchayat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.rishabh.panchayat.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.admin.setOnClickListener(v->{
            Intent intent=new Intent(MainActivity.this,Admin.class);
            startActivity(intent);
        });
        binding.signIn.setOnClickListener(v->{
            if(!binding.enterYourWard.getGetTextValue().trim().equals("")){
                getSharedPreferences("Admin_entered",MODE_PRIVATE).edit()
                        .putString("Admin_entered",binding.enterYourWard.getGetTextValue().trim()).apply();
                Intent intent=new Intent(MainActivity.this,Home.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String logged=getSharedPreferences("Admin_entered",MODE_PRIVATE)
                .getString("Admin_entered","no");
        if(logged.equals("yes")){
            Intent intent=new Intent(MainActivity.this,Home.class);
            startActivity(intent);
            finish();
        }
        else if(!logged.equals("no")){
            Intent intent=new Intent(MainActivity.this,Home.class);
            startActivity(intent);
            finish();
        }
    }
}