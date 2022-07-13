package com.rishabh.panchayat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rishabh.panchayat.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    DatabaseReference reference;
    List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reference= FirebaseDatabase.getInstance().getReference().child("data");
        binding.admin.setOnClickListener(v->{
            Intent intent=new Intent(Home.this,Admin_writes.class);
            startActivity(intent);
        });

        LinearLayoutManager mManager = new LinearLayoutManager(Home.this);
        binding.recyclerView.setItemViewCacheSize(500);
        binding.recyclerView.setDrawingCacheEnabled(true);
        binding.recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recyclerView.setLayoutManager(mManager);
        String is_admin=getSharedPreferences("Admin_entered",MODE_PRIVATE)
                .getString("Admin_entered","no");
        binding.admin.setVisibility(View.GONE);
        binding.logout.setOnClickListener(v->{
            getSharedPreferences("Admin_entered",MODE_PRIVATE).edit()
                    .putString("Admin_entered","no").apply();
            Intent intent=new Intent(Home.this,MainActivity.class);
            startActivity(intent);
            finish();
        });
        if(is_admin.equals("yes")) {
            binding.admin.setVisibility(View.VISIBLE);
        }
        getData();
    }

    private void getData() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ward=getSharedPreferences("Admin_entered",MODE_PRIVATE)
                        .getString("Admin_entered","no");
                for(DataSnapshot ds:snapshot.getChildren()){
                    if(ward.equals(snapshot.child(ds.getKey()).child("ward").getValue(String.class)) || ward.equals("yes")){
                        list.add(snapshot.child(ds.getKey()).child("details").getValue(String.class));
                    }
                }
                Data_Adapter data_adapter=new Data_Adapter(list);
                data_adapter.notifyDataSetChanged();
                binding.recyclerView.setAdapter(data_adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}