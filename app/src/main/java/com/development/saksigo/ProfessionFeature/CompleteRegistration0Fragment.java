package com.development.saksigo.ProfessionFeature;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.development.saksigo.IntroductionActivity;
import com.development.saksigo.PhotoProfileActivity;
import com.development.saksigo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;


import de.hdodenhof.circleimageview.CircleImageView;

public class CompleteRegistration0Fragment extends Fragment {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;

    CircleImageView circleImageViewProfilePic;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            circleImageViewProfilePic.setImageURI(imageUri);
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_complete_registration_0_fragment, container, false);



        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("KeyPartner");
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("gs://saksigo-30792.appspot.com/KeyPartner");

        circleImageViewProfilePic = root.findViewById(R.id.circleImageView_profilePhoto);

        Intent intent = new Intent(container.getContext(), PhotoProfileActivity.class);

        circleImageViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
//                CropImage.activity().setAspectRatio(1,1).start(getActivity());


            }
        });

        return root;
    }
}
