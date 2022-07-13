package com.rishabh.panchayat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rishabh.panchayat.databinding.ActivityAdminBinding;

import java.util.Objects;

public class Admin extends AppCompatActivity {

    ActivityAdminBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        reference= FirebaseDatabase.getInstance().getReference().child("admin");
        binding.signIn.setOnClickListener(v->{
            if(!binding.enterYourId.getGetTextValue().trim().equals("")){
                if(!binding.enterYourPass.getGetTextValue().trim().equals("")){
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int c=0;
                         for(DataSnapshot ds:snapshot.getChildren()){
                             if(Objects.equals(snapshot.child(ds.getKey()).child("id").getValue(String.class), binding.enterYourId.getGetTextValue().trim())
                                     &&
                                     Objects.equals(snapshot.child(ds.getKey()).child("pass").getValue(String.class), binding.enterYourPass.getGetTextValue().trim())){
                                 c=1;
                                 getSharedPreferences("Admin_entered",MODE_PRIVATE).edit()
                                         .putString("Admin_entered","yes").apply();
                                 Intent intent=new Intent(Admin.this,Home.class);
                                 startActivity(intent);
                                 finish();
                                 break;
                             }
                         }
                         if(c==0){
                             Toast.makeText(Admin.this, "No Account Exist.", Toast.LENGTH_SHORT).show();
                         }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
                else{
                    Toast.makeText(this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Please enter your ID", Toast.LENGTH_SHORT).show();
            }
        });
    }
}