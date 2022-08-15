package com.development.saksigo.ProfessionFeature;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.development.saksigo.IntroductionActivity;
import com.development.saksigo.PhotoProfileActivity;
import com.development.saksigo.R;
import com.theartofdev.edmodo.cropper.CropImage;


import de.hdodenhof.circleimageview.CircleImageView;

public class CompleteRegistration0Fragment extends Fragment {

    CircleImageView circleImageViewProfilePic;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_complete_registration_0_fragment, container, false);

        Intent intent = new Intent(container.getContext(), PhotoProfileActivity.class);



        circleImageViewProfilePic = root.findViewById(R.id.circleImageView_profilePhoto);

        circleImageViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity().setAspectRatio(1,1).start(getActivity());
//            startActivity(intent);

            }
        });

        return root;
    }
}
