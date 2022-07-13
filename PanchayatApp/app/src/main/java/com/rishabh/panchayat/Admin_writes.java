package com.rishabh.panchayat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rishabh.panchayat.databinding.ActivityAdminBinding;
import com.rishabh.panchayat.databinding.ActivityAdminWritesBinding;

public class Admin_writes extends AppCompatActivity {

    ActivityAdminWritesBinding binding;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminWritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        reference= FirebaseDatabase.getInstance().getReference().child("data");
        binding.signIn.setOnClickListener(v->{
            if(!binding.enterYourWard.getGetTextValue().trim().equals("")) {
                if (!binding.meeting.getGetTextValue().trim().equals("") || !binding.rashan.getGetTextValue().trim().equals("")) {
                    String push = reference.push().getKey();
                    if (!binding.meeting.getGetTextValue().trim().equals("")) {
                        reference.child(push).child("details").setValue(binding.meeting.getGetTextValue().trim() + " " + binding.rashan.getGetTextValue().trim());
                        reference.child(push).child("ward").setValue(binding.enterYourWard.getGetTextValue().trim());
                        Toast.makeText(this, "Uploaded successfully.", Toast.LENGTH_SHORT).show();
                        binding.meeting.setTextValue("");
                        binding.rashan.setTextValue("");
                        binding.enterYourWard.setTextValue("");
                    }
                    else {
                        reference.child(push).child("ward").setValue(binding.enterYourWard.getGetTextValue().trim());
                        reference.child(push).child("details").setValue(binding.rashan.getGetTextValue().trim() + " " + binding.meeting.getGetTextValue().trim());
                        Toast.makeText(this, "Uploaded successfully.", Toast.LENGTH_SHORT).show();
                        binding.meeting.setTextValue("");
                        binding.rashan.setTextValue("");
                        binding.enterYourWard.setTextValue("");
                    }
                }
                else{
                    Toast.makeText(this, "Please enter the details.", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Enter ward no.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}