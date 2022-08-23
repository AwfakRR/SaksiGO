package com.development.saksigo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class PhotoProfileActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;
    EditText editTextNameProfile;
    String stringNameProfile, stringNameProfileDatabase;


    Button buttonSavePicture;
    CircleImageView circleImageViewProfilePic;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            circleImageViewProfilePic.setImageURI(imageUri);
        }else {
            Toast.makeText(this, "Error, try again.", Toast.LENGTH_SHORT).show();

        }
    }
    private void uploadProfileImage(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Set your profile");
        progressDialog.setMessage("Please wait while we are setting your data");
        progressDialog.show();

        if (imageUri !=null){
            final StorageReference fileRef = storageProfilePicsRef.child(mAuth.getCurrentUser().getUid()+ ".jpg");

            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }) .addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUrl = task.getResult();
                        myUri = downloadUrl.toString();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("image", myUri);

                        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);

                        progressDialog.dismiss();
                    }
                }
            });
        }
        else {
            progressDialog.dismiss();
        }
    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_profile);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getSupportActionBar().setTitle("Change Picture");
        getSupportActionBar().setElevation(0);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://saksigo-30792-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("KeyPartner");
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("gs://saksigo-30792.appspot.com/KeyPartner");

        editTextNameProfile = findViewById(R.id.editText_nameProfile);
        circleImageViewProfilePic = findViewById(R.id.circleImageView_profilePhotoEdit);
        buttonSavePicture = findViewById(R.id.button_savePic);

        getUserinfo();

        stringNameProfile = editTextNameProfile.getText().toString();

        buttonSavePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                uploadProfileImage();
                if(!stringNameProfile.equals(stringNameProfileDatabase)){
                uploadFullName();
            }
            }
        });

        circleImageViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setAspectRatio(1,1).start(PhotoProfileActivity.this);
            }
        });





    }

    private void uploadFullName() {
        stringNameProfile = editTextNameProfile.getText().toString();
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("fullname", stringNameProfile);

        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
    }

    private void getUserinfo() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0)
                {
                    if (dataSnapshot.hasChild("image")){
                        String image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(circleImageViewProfilePic);
                    }
                    if (dataSnapshot.hasChild("fullname")){
                        stringNameProfileDatabase = dataSnapshot.child("fullname").getValue().toString();
                        String fullname = dataSnapshot.child("fullname").getValue().toString();
                        editTextNameProfile.setText(fullname);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}