package com.tecraa.mycontact;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tecraa.mycontact.databinding.ActivityAddContactBinding;

public class AddContactActivity extends AppCompatActivity {

    ActivityAddContactBinding binding;

    Contact editContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().hasExtra("contact")){
            editContact = (Contact) getIntent().getSerializableExtra("contact");
            binding.addNewContactBtn.setText("Update Contact");
            binding.profileTitleTV.setText("View Profile");
            binding.addNewContactBtn.setVisibility(View.GONE);
            binding.contactEditBtn.setVisibility(View.VISIBLE);
            binding.contactDeleteBtn.setVisibility(View.VISIBLE);



            setContent();

            editTextEditFalse();


        }else {

            binding.addNewContactBtn.setText("Add New Contact");
            binding.profileTitleTV.setText("New Profile");
            createPreview();

        }

        getSupportActionBar().hide();


        binding.addNewContactBtn.setOnClickListener(v->{
            
            manageContact();

        });
        binding.contactEditBtn.setOnClickListener(view -> {
            editTextEditTrue();
        });

        binding.addContactBackBtn.setOnClickListener(v->{
            finish();
        });
        binding.updateCancelBtn.setOnClickListener(v -> {
            updateCancel();
        });

        binding.contactDeleteBtn.setOnClickListener(v->{

            deleteContact(editContact);
        });

    }

    private void deleteContact(Contact editContact) {


        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddContactActivity.this);


        builder.setTitle("Delete")
                .setMessage("Are you sure want to delete this contact?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ContactDatabase.getDatabase(getApplicationContext()).getDao().deleteContact(editContact);
                        finish();
                    }
                })
                .setNegativeButton("NO",null)
                .setIcon(R.drawable.ic_warning)
                .show();

    }


    private void setContent() {
        Glide.with(AddContactActivity.this).load(editContact.getImageUrl()).placeholder(R.drawable.img_profile).into(binding.addImgPreview);
        binding.addNamePreview.setText(editContact.getName());
        binding.addRelationTypePreview.setText(editContact.getRelationType());

        binding.addNameInput.setText(editContact.getName());
        binding.addPhoneInput.setText(editContact.getNumber());
        binding.addEmailInput.setText(editContact.getEmail());
        binding.addRelationTypeInput.setText(editContact.getRelationType());
        binding.addImgUrlInput.setText(editContact.getImageUrl());
    }

    private void updateCancel() {
        binding.profileTitleTV.setText("View Profile");
        binding.addNameInput.setEnabled(false);
        binding.addPhoneInput.setEnabled(false);
        binding.addEmailInput.setEnabled(false);
        binding.addRelationTypeInput.setEnabled(false);
        binding.addImgUrlInput.setEnabled(false);
        binding.addNewContactBtn.setVisibility(View.GONE);
        binding.updateCancelBtn.setVisibility(View.GONE);
        binding.contactEditBtn.setVisibility(View.VISIBLE);
        binding.contactDeleteBtn.setVisibility(View.VISIBLE);
        setContent();
    }

    private void editTextEditFalse() {
        binding.addNameInput.setEnabled(false);
        binding.addPhoneInput.setEnabled(false);
        binding.addEmailInput.setEnabled(false);
        binding.addRelationTypeInput.setEnabled(false);
        binding.addImgUrlInput.setEnabled(false);


    }

    private void editTextEditTrue() {
        binding.profileTitleTV.setText("Update Profile");
        binding.addNameInput.setEnabled(true);
        binding.addPhoneInput.setEnabled(true);
        binding.addEmailInput.setEnabled(true);
        binding.addRelationTypeInput.setEnabled(true);
        binding.addImgUrlInput.setEnabled(true);
        binding.addNewContactBtn.setVisibility(View.VISIBLE);
        binding.updateCancelBtn.setVisibility(View.VISIBLE);
        binding.contactEditBtn.setVisibility(View.GONE);
        binding.contactDeleteBtn.setVisibility(View.GONE);
        binding.addNameInput.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.addNameInput, InputMethodManager.SHOW_IMPLICIT);



    }

    private void manageContact() {


        if(binding.addNameInput.getText().toString().equals("") || binding.addPhoneInput.getText().toString().equals("")){
            Toast.makeText(AddContactActivity.this, "Please fill input",
                    Toast.LENGTH_LONG).show();
        }else {
            String name = binding.addNameInput.getText().toString().trim();
            String phone = binding.addPhoneInput.getText().toString().trim();
            String email = binding.addEmailInput.getText().toString().trim();
            String relation = binding.addRelationTypeInput.getText().toString().trim();
            String image = binding.addImgUrlInput.getText().toString().trim();

            if (getIntent().hasExtra("contact")){
                updateContactToDB(editContact.id,name,phone,email,relation,image);
            }else {
                saveContactToDB(name,phone,email,relation,image);
            }


        }
    }

    private void updateContactToDB(int id, String name, String phone, String email, String relation, String image) {

        editContact.setId(id);
        editContact.setName(name);
        editContact.setNumber(phone);
        editContact.setEmail(email);
        editContact.setRelationType(relation);
        editContact.setImageUrl(image);
        ContactDatabase.getDatabase(getApplicationContext()).getDao().updateContact(editContact);
        finish();


    }

    private void saveContactToDB(String name, String phone, String email, String relation, String image) {

        Contact contact = new Contact();
        contact.setName(name);
        contact.setNumber(phone);
        contact.setEmail(email);
        contact.setRelationType(relation);
        contact.setImageUrl(image);
        ContactDatabase.getDatabase(getApplicationContext()).getDao().insertContact(contact);
        finish();

    }


    private void createPreview() {
        binding.addNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String getfullname = String.valueOf(charSequence);

                if (getfullname.equals("")){
                    binding.addNamePreview.setText("Name Preview");
                }else{
                    binding.addNamePreview.setText(getfullname);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.addRelationTypeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String getRelationName = String.valueOf(charSequence);

                if (getRelationName.equals("")){
                    binding.addRelationTypePreview.setText("Relation");
                }else{
                    binding.addRelationTypePreview.setText(getRelationName);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.addImgUrlInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String getImgUrl = String.valueOf(charSequence);

                Glide.with(AddContactActivity.this).load(getImgUrl).placeholder(R.drawable.img_profile).into(binding.addImgPreview);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}