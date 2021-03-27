package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentShowOneStory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentShowOneStory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_IMAGEURL = "param1";
    private static final String ARG_USERNAME = "param1";
    private static final String ARG_USERSTORY = "param1";
    private static final String ARG_USERLIKECOUNT = "param1";
    private static final String ARG_USERCOMMENTCOUNT = "param1";
    private static final String ARG_USERSHARECOUNT = "param1";
    private static final String ARG_FBID = "param1";
    private static final String ARG_DOCUMENTID = "param1";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mUserImageUrl, mUserName, mUserStory, mCountLikes, mCountShares, mCountComments, mFbId, mDocumentId;


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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_one_story, container, false);
    }
}