package app.actionnation.actionapp;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentShowOneStory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentShowOneStory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_IMAGEURL = "param1";
    private static final String ARG_USERNAME = "param2";
    private static final String ARG_USERSTORY = "param3";
    private static final String ARG_USERLIKECOUNT = "param4";
    private static final String ARG_USERCOMMENTCOUNT = "param5";
    private static final String ARG_USERSHARECOUNT = "param6";
    private static final String ARG_FBID = "param7";
    private static final String ARG_DOCUMENTID = "param8";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mUserImageUrl, mUserName, mUserStory, mCountLikes, mCountShares, mCountComments, mFbId, mDocumentId;

    private TextView tv_userName, tv_userStory, tvUserLikesCount, tvUserCommentCount, tvSharesCount;
    private de.hdodenhof.circleimageview.CircleImageView imageView;
    private ImageButton imageBtnLike, imageBtnShare, imageBtnComment;
    private Button BtnShare;


    public FragmentShowOneStory() {
        // Required empty public constructor
    }


    public static FragmentShowOneStory newInstance(ArrayList<String> storyCommentObject) {
        FragmentShowOneStory frag = new FragmentShowOneStory();
        Bundle args = new Bundle();
        args.putString(ARG_DOCUMENTID, storyCommentObject.get(0));
        args.putString(ARG_FBID, storyCommentObject.get(1));
        args.putString(ARG_IMAGEURL, storyCommentObject.get(2));
        args.putString(ARG_USERNAME, storyCommentObject.get(3));
        args.putString(ARG_USERSTORY, storyCommentObject.get(4));
        args.putString(ARG_USERLIKECOUNT, storyCommentObject.get(5));
        args.putString(ARG_USERCOMMENTCOUNT, storyCommentObject.get(6));
        args.putString(ARG_USERSHARECOUNT, storyCommentObject.get(7));

        frag.setArguments(args);
        return frag;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentShowOneStory.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentShowOneStory newInstance(String param1, String param2) {
        FragmentShowOneStory fragment = new FragmentShowOneStory();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDocumentId = getArguments().getString(ARG_DOCUMENTID);
            mCountComments = getArguments().getString(ARG_USERCOMMENTCOUNT);
            mCountLikes = getArguments().getString(ARG_USERLIKECOUNT);
            mCountShares = getArguments().getString(ARG_USERSHARECOUNT);
            mFbId = getArguments().getString(ARG_FBID);
            mUserImageUrl = getArguments().getString(ARG_IMAGEURL);
            mUserName = getArguments().getString(ARG_USERNAME);
            mUserStory = getArguments().getString(ARG_USERSTORY);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_one_story, container, false);
        tv_userName = view.findViewById(R.id.tv_fm_oneStory_userName);
        tv_userStory = view.findViewById(R.id.tv_fm_oneStory_userStory);
        tvUserLikesCount = view.findViewById(R.id.tv_fm_oneStory_countUserLikes);
        tvUserCommentCount = view.findViewById(R.id.tv_fm_oneStory_countUserComments);
        tvSharesCount = view.findViewById(R.id.tv_fm_oneStory_countUserShares);
        imageView = view.findViewById(R.id.profile_fm_oneStory_image);
        imageBtnLike = view.findViewById(R.id.imgBtn_fm_oneStory_countUserLikes);
        imageBtnShare = view.findViewById(R.id.imgBtn_fm_oneStory_countUserShares);
        imageBtnComment = view.findViewById(R.id.imgBtn_fm_oneStory_countUserComments);
        BtnShare = view.findViewById(R.id.Btn_UserShares);



        tv_userName.setText(mUserName);
        tv_userStory.setText(mUserStory);
        tvSharesCount.setText(mCountShares);
        tvUserCommentCount.setText(mCountComments);
        tvUserLikesCount.setText(mCountLikes);


        BtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Date c = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                String strBody = mUserStory + " #ActionNation #IamTheHero";
                String strSubject = "MY Story " + formattedDate;
                String strTitle = mUserName + "'s Story";

                CommonClass cls = new CommonClass();
                cls.ShareToOtherPlatforms("", strBody, strSubject, strTitle, getContext());

            }
        });


        Glide.with(getContext())
                .asBitmap()
                .load(mUserImageUrl)
                //  .override(180, 180)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                    }
                });




        return view;
    }
}