package com.tecraa.mycontact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tecraa.mycontact.databinding.ActivityAddContactBinding;
import com.tecraa.mycontact.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactListener {

    ActivityMainBinding binding;

    ContactDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = ContactDatabase.getDatabase(getApplicationContext());

        getSupportActionBar().hide();

        binding.addContactBtn.setOnClickListener(v ->{
            startActivity(new Intent(MainActivity.this,AddContactActivity.class));
        });
        binding.addNewContactBtn.setOnClickListener(v ->{
            startActivity(new Intent(MainActivity.this,AddContactActivity.class));
        });

        setDataToRecycleList();



        





    }

    private void setDataToRecycleList() {
        List<Contact> contactList = database.getDao().getAllUser();

        ContactAllAdapter adapter = new ContactAllAdapter(MainActivity.this, contactList,this);
        binding.contactListRV.setAdapter(adapter);
    }

    @Override
    public void viewUser(Contact contact) {
        Intent intent = new Intent(getApplicationContext(), AddContactActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        setDataToRecycleList();
        super.onResume();
    }
}