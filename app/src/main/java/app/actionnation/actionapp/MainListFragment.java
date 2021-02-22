package app.actionnation.actionapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import app.actionnation.actionapp.Database_Content.Challenges;
import app.actionnation.actionapp.Database_Content.Education;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MainListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    //Firebase from Data


    List<Challenges> list = new ArrayList<>();
    RecyclerView recyclerView;
    private String onlineUserID, strTab;
    private static final String TAG = "MainListFragment:Log";

    FirebaseRecyclerAdapter fbAdapter;

    public MainListFragment() {
    }

    public MainListFragment(String str) {

        strTab = str;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MainListFragment newInstance(int columnCount) {
        MainListFragment fragment = new MainListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child(getString(R.string.fb_Education));

        if (strTab.equals(getString(R.string.tabs_challenges)))
            query = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Education)).orderByChild(getString(R.string.fb_status)).equalTo(1);
        if (strTab.equals(getString(R.string.tabs_leadership)))
            query = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Education)).orderByChild(getString(R.string.fb_status)).equalTo(0);
        if (strTab.equals(getString(R.string.tabs_nlw)))
            query = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Education)).orderByChild(getString(R.string.fb_status)).equalTo(2);
        FirebaseRecyclerOptions<Education> options = new FirebaseRecyclerOptions.Builder<Education>()
                .setQuery(query, Education.class)
                .build();
        String S = strTab;

        fbAdapter = new FirebaseRecyclerAdapter<Education, RedirectFromMain.FindViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RedirectFromMain.FindViewHolder holder, int position, @NonNull Education mValues) {
                holder.mIdView.setText((mValues.getEdu_name()));
                holder.mIdView.setTag(fbAdapter.getRef(position).getKey());
                holder.mImageView.setTag((String.valueOf(mValues.getEdu_name())));

                // final String key =  userList.get(position).getKey();

                Glide.with(getActivity().getApplicationContext())
                        .asBitmap()
                        .load(mValues.getUrl())
                        //  .override(180, 180)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                holder.mImageView.setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                            }
                        });

                holder.mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (strTab.equals(getString(R.string.tabs_challenges))) {
                            Intent in = getActivity().getIntent();
                            String StrData = in.getStringExtra(getString(R.string.Intent_Auth));
                            Intent homepage = new Intent(view.getContext(), Detail_Challenges.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString(getString(R.string.Intent_EduId), holder.mIdView.getTag().toString());
                            mBundle.putString(getString(R.string.Intent_EduName), holder.mImageView.getTag().toString());

                            mBundle.putString(getString(R.string.Intent_Auth), StrData);
                            homepage.putExtras(mBundle);
                            view.getContext().startActivity(homepage);
                            getActivity().finish();
                        }
                        if (strTab.equals(getString(R.string.tabs_leadership))) {
                            Intent in = getActivity().getIntent();
                            String StrData = in.getStringExtra(getString(R.string.Intent_Auth));
                            Intent homepage = new Intent(view.getContext(), LeadershipInitiative.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString(getString(R.string.Intent_EduId), holder.mIdView.getTag().toString());
                            mBundle.putString(getString(R.string.Intent_EduName), holder.mImageView.getTag().toString());
                            mBundle.putString(getString(R.string.Intent_Auth), StrData);
                            homepage.putExtras(mBundle);
                            view.getContext().startActivity(homepage);
                            getActivity().finish();
                        }
                        if (strTab.equals(getString(R.string.tabs_nlw))) {
                            Intent in = getActivity().getIntent();
                            String StrData = in.getStringExtra(getString(R.string.Intent_Auth));
                            String StrLeadershipWay = holder.mImageView.getTag().toString();

                            if (StrLeadershipWay.equals(getString(R.string.Leadership_NLW))) {
                                Intent homepage = new Intent(view.getContext(), nlw.class);
                                Bundle mBundle = new Bundle();
                                mBundle.putString(getString(R.string.Intent_EduId), holder.mIdView.getTag().toString());
                                mBundle.putString(getString(R.string.Intent_EduName), holder.mImageView.getTag().toString());
                                mBundle.putString(getString(R.string.Intent_Auth), StrData);
                                homepage.putExtras(mBundle);
                                view.getContext().startActivity(homepage);
                                getActivity().finish();
                            }
                        }
                    }
                });

            }

            @NonNull
            @Override
            public RedirectFromMain.FindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mainlist, parent, false);
                RedirectFromMain.FindViewHolder viewHolder = new RedirectFromMain.FindViewHolder(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(fbAdapter);
        fbAdapter.startListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainlist_list, container, false);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        onlineUserID = mAuth.getCurrentUser().getUid();
        FirebaseUser user = mAuth.getCurrentUser();
        recyclerView = (RecyclerView) view.findViewById(R.id.listChal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(false);
        fetch();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Challenges item);
    }
}
