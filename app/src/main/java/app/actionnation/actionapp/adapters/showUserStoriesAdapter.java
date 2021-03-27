package app.actionnation.actionapp.adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.actionnation.actionapp.Database_Content.UserStories;
import app.actionnation.actionapp.Database_Content.UserStoryLikes;
import app.actionnation.actionapp.FragmentCreateStory;
import app.actionnation.actionapp.FragmentShowUserStory;
import app.actionnation.actionapp.R;
import app.actionnation.actionapp.Storage.UserStorageStoryObject;

public class showUserStoriesAdapter extends FirestoreRecyclerAdapter<UserStories, showUserStoriesAdapter.ViewHolderUserStory> {

    private OnItemClickListener listener;
    private UserStorageStoryObject userStorageStoryObject;

    public showUserStoriesAdapter(@NonNull FirestoreRecyclerOptions<UserStories> options, UserStorageStoryObject userStorageStoryObject) {
        super(options);
        this.userStorageStoryObject = userStorageStoryObject;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolderUserStory holder, int position, @NonNull UserStories model) {

        final String fbId = userStorageStoryObject.getFb_Id();
        final String userImg = userStorageStoryObject.getUserProfilePicUrl();
        final String userName = userStorageStoryObject.getUserName();

        ArrayList<String> dataForlikes = new ArrayList<>();
        dataForlikes.add(0, getSnapshots().getSnapshot(position).getId());
        dataForlikes.add(1, fbId);
        dataForlikes.add(2, userImg);
        dataForlikes.add(3, userName);
        dataForlikes.add(4, String.valueOf(model.getUserLikeCount()));

        holder.mIdStory.setText(model.getUserStory());
        holder.mNoOfComments.setText(String.valueOf(model.getUserCommentCount()));
        holder.mNoOfLikes.setText(String.valueOf(model.getUserLikeCount()));
        holder.mNoOfShares.setText(String.valueOf(model.getUserReshareCount()));
        holder.mUserName.setText(String.valueOf(model.getUserName()));
        holder.mIdStory.setTag(getSnapshots().getSnapshot(position).getId());
        holder.mBtnLikes.setTag(dataForlikes);


        ArrayList<String> dataForComments = new ArrayList<>();
        dataForComments.add(0, getSnapshots().getSnapshot(position).getId());
        dataForComments.add(1, fbId);
        dataForComments.add(2, userImg);
        dataForComments.add(3, userName);
        dataForComments.add(4, String.valueOf(model.getUserCommentCount()));
        holder.mBtnComments.setTag(dataForComments);


        ArrayList<String> dataForShares = new ArrayList<>();
        dataForShares.add(0, getSnapshots().getSnapshot(position).getId());
        dataForShares.add(1, fbId);
        dataForShares.add(2, userImg);
        dataForShares.add(3, userName);
        dataForShares.add(4, String.valueOf(model.getUserReshareCount()));
        holder.mBtnShares.setTag(model.getUserReshareCount());

        Glide.with(holder.mView.getContext())
                .asBitmap()
                .load(model.getUserProfilePicUrl())
                //  .override(180, 180)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.mUserImage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                    }
                });

        holder.mBtnLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                final int likeCount = Integer.parseInt(((ArrayList<String>) holder.mBtnLikes.getTag()).get(4));

                // createStoryLikes(db, likeCount, holder.mIdStory.getTag().toString(), "UserStoryLikes", fbId, holder.mNoOfLikes);

                ArrayList<String> getDataArrayList = (ArrayList<String>) holder.mBtnLikes.getTag();
                final String documentId = getDataArrayList.get(0);

                final DocumentReference docRef = db.collection("UserStoryLikes").document(fbId + "_" + documentId);

                int commentCount = 0;
                Map<String, Object> userVariable = new HashMap<>();
                userVariable.put("fb_Id", fbId);

                docRef.update(userVariable).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        UserStoryLikes usl = new UserStoryLikes();
                        usl.setFb_Id(fbId);
                        usl.setTimestamp(null);
                        usl.setUserStoryId(documentId);
                        docRef.set(usl).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                DocumentReference noteRef = db.collection("UserStories").document(documentId);
                                noteRef.update("userLikeCount", likeCount + 1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        holder.mNoOfLikes.setText(String.valueOf(likeCount + 1));
                                    }
                                });
                            }
                        });
                    }
                });


            }
        });

        holder.mBtnShares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ArrayList<String> getDataArrayList = (ArrayList<String>) holder.mBtnShares.getTag();
/*
                        UserStorageStoryObject userStorageStoryObject1 = new UserStorageStoryObject();


                        userStorageStoryObject.setFb_Id(getDataArrayList.get(1));
                        userStorageStoryObject.setUserProfilePicUrl(getDataArrayList.get(2));
                        userStorageStoryObject.setUserName(getDataArrayList.get(3));*/

                String documentId = getDataArrayList.get(0);
                String shareCount = getDataArrayList.get(4);


                FragmentManager fm =
                        ((FragmentActivity) holder.mView.getContext()).getSupportFragmentManager();
                FragmentCreateStory editNameDialogFragment = FragmentCreateStory.newInstance(getDataArrayList);
                editNameDialogFragment.show(fm, "fragment_edit_name");

                       /* FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference noteRef = db.collection(collectionReference).document(holder.mIdStory.getTag().toString());
                        int shareCount = Integer.valueOf(holder.mBtnShares.getTag().toString());
                        noteRef.update("userReshareCount", shareCount + 1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        });*/
            }
        });

        holder.mBtnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> getDataArrayList = (ArrayList<String>) holder.mBtnComments.getTag();


                        /*
                        UserStorageStoryObject userStorageStoryObject1 = new UserStorageStoryObject();


                        userStorageStoryObject.setFb_Id(getDataArrayList.get(1));
                        userStorageStoryObject.setUserProfilePicUrl(getDataArrayList.get(2));
                        userStorageStoryObject.setUserName(getDataArrayList.get(3));*/

                String documentId = getDataArrayList.get(0);
                String shareCount = getDataArrayList.get(4);


                FragmentManager fm =
                        ((FragmentActivity) holder.mView.getContext()).getSupportFragmentManager();
                FragmentCreateStory editNameDialogFragment = FragmentCreateStory.newInstance(getDataArrayList);
                editNameDialogFragment.show(fm, "fragment_edit_name");

            }
        });


    }

    @NonNull
    @Override
    public ViewHolderUserStory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_showstory,
                parent, false);
        return new ViewHolderUserStory(v);

    }


    public class ViewHolderUserStory extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdStory;
        public final TextView mNoOfLikes;
        public final TextView mNoOfComments;
        public final TextView mNoOfShares;

        public final ImageButton mBtnLikes;
        public final ImageButton mBtnShares;
        public final ImageButton mBtnComments;

        public final de.hdodenhof.circleimageview.CircleImageView mUserImage;
        public final TextView mUserName;

       /*
        public final ImageButton mLikes;
        public final ImageButton mReshare;
        public final ImageButton mComment;*/

        public FragmentShowUserStory.ViewHolderUserStory mItem;

        public ViewHolderUserStory(View view) {
            super(view);
            mView = view;
            mIdStory = view.findViewById(R.id.tv_lo_userStory);
            mNoOfLikes = view.findViewById(R.id.tv_lo_countUserLikes);
            mNoOfComments = view.findViewById(R.id.tv_lo_countUserComments);
            mNoOfShares = view.findViewById(R.id.tv_lo_countUserShares);
            mUserImage = view.findViewById(R.id.profile_lo_image);
            mUserName = view.findViewById(R.id.tv_lo_userName);
            mBtnLikes = view.findViewById(R.id.imgBtn_lo_countUserLikes);
            mBtnShares = view.findViewById(R.id.imgBtn_lo_countUserShares);
            mBtnComments = view.findViewById(R.id.imgBtn_lo_countUserComments);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdStory.getText() + "'";
        }


    }


    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
