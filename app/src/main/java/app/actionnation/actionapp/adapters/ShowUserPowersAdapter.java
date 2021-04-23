package app.actionnation.actionapp.adapters;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import app.actionnation.actionapp.ActivityAttention;
import app.actionnation.actionapp.ActivityEatHealthy;
import app.actionnation.actionapp.ActivityExperienceNature;
import app.actionnation.actionapp.ActivityHappiness;
import app.actionnation.actionapp.ActivityIntegrityMain;
import app.actionnation.actionapp.ActivityOurBelief;
import app.actionnation.actionapp.ActivityRevealStory;
import app.actionnation.actionapp.Activity_TrueLearning;
import app.actionnation.actionapp.Database_Content.CommonData;
import app.actionnation.actionapp.HabitTraking;
import app.actionnation.actionapp.MeditationActivity;
import app.actionnation.actionapp.R;
import app.actionnation.actionapp.Storage.Constants;

public class ShowUserPowersAdapter extends FirebaseRecyclerAdapter<CommonData, ShowUserPowersAdapter.ViewHolderMainObjective> {

    private ShowUserPowersAdapter.OnItemClickListener listener;
    private ArrayList<Integer> userGameArray;
    private ArrayList<String> userArrayCaptains;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ShowUserPowersAdapter(@NonNull FirebaseRecyclerOptions<CommonData> options, ArrayList<Integer> userGameArray, ArrayList<String> userArrayCaptains) {
        super(options);
        this.userGameArray = userGameArray;
        this.userArrayCaptains = userArrayCaptains;
    }


    @Override
    protected void onBindViewHolder(@NonNull final ViewHolderMainObjective holder, final int position, @NonNull CommonData model) {
        holder.mIdView.setText(model.getDataString());
        holder.mImageView.setTag(model.getDataNumber());
        String strFirstLetter = model.getDataString().substring(0, 1);

        holder.mIdViewFirstLetter.setText(strFirstLetter);

        //Setting the check when things are done

        switch (position) {
            case 0:
                if (userGameArray == null || userGameArray.size() == 0)
                    break;
                if (userGameArray.get(Constants.Game_CP__UserPlaceWinScore) > 0 && userGameArray.get(Constants.Game_CP__UserSelfWinScore) > 0 && userGameArray.get(Constants.Game_CP__UserWordWinScore) > 0 && userGameArray.get(Constants.Game_CP__UserWorkWinScore) > 0) {
                    holder.mImageViewCheck.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                if (userGameArray == null || userGameArray.size() == 0)
                    break;
                if (userGameArray.get(Constants.Game_CP__UserDistractionScore) > 0 && userGameArray.get(Constants.Game_CP__UserTractionScore) > 0) {
                    holder.mImageViewCheck.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                if (userGameArray == null || userGameArray.size() == 0)
                    break;
                if (userGameArray.get(Constants.Game_CP__UserMeditationScore) > 0) {
                    holder.mImageViewCheck.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                if (userGameArray == null || userGameArray.size() == 0)
                    break;
                if (userGameArray.get(Constants.Game_CP__UserTrueLearningScore) > 0) {
                    holder.mImageViewCheck.setVisibility(View.VISIBLE);
                }
                break;
            case 4:
                if (userGameArray == null || userGameArray.size() == 0)
                    break;
                if (userGameArray.get(Constants.Game_CP__UserGratitudeScore) > 0 && userGameArray.get(Constants.Game_CP__UserForgivenessOutsideScore) > 0 && userGameArray.get(Constants.Game_CP__UserForgivenessSelfScore) > 0 && userGameArray.get(Constants.Game_CP__UserAbundanceScore) > 0) {
                    holder.mImageViewCheck.setVisibility(View.VISIBLE);
                }
                break;
            case 5:
                if (userGameArray == null || userGameArray.size() == 0)
                    break;
                if (userGameArray.get(Constants.Game_CP__UserEatHealthyScore) > 0 && userGameArray.get(Constants.Game_CP__UserAvoidForHealthScore) > 0 && userGameArray.get(Constants.Game_CP__UserExerciseScore) > 0) {
                    holder.mImageViewCheck.setVisibility(View.VISIBLE);
                }
                break;
            case 6:
                if (userGameArray == null || userGameArray.size() == 0)
                    break;
                if (userGameArray.get(Constants.Game_CP__UserHabitsScore) > 0) {
                    holder.mImageViewCheck.setVisibility(View.VISIBLE);
                }
                break;
            case 7:
                if (userGameArray == null || userGameArray.size() == 0)
                    break;
                if (userGameArray.get(Constants.Game_CP__UserExperienceNatureScore) > 0) {
                    holder.mImageViewCheck.setVisibility(View.VISIBLE);
                }
                break;
            case 8:
                if (userGameArray == null || userGameArray.size() == 0)
                    break;
                if (userGameArray.get(Constants.Game_CP__UserRevealStoryScore) > 0) {
                    holder.mImageViewCheck.setVisibility(View.VISIBLE);
                }
                break;
            case 9:
                if (userGameArray == null || userGameArray.size() == 0)
                    break;
                if (userGameArray.get(Constants.Game_CP__UserOurBeliefScore) > 0) {
                    holder.mImageViewCheck.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }


        // final String key =  userList.get(position).getKey();

        Glide.with(holder.mView.getContext())
                .asBitmap()
                .load(model.getDataUrl())
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

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int itemPosition = position;
                Intent i = null;
                switch (position) {
                    case 0:
                        i = new Intent(holder.mIdView.getContext(), ActivityIntegrityMain.class);
                        break;
                    case 1:
                        i = new Intent(holder.mIdView.getContext(), ActivityAttention.class);
                        break;
                    case 2:
                        i = new Intent(holder.mIdView.getContext(), MeditationActivity.class);
                        break;
                    case 3:
                        i = new Intent(holder.mIdView.getContext(), Activity_TrueLearning.class);
                        break;
                    case 4:
                        i = new Intent(holder.mIdView.getContext(), ActivityHappiness.class);
                        break;
                    case 5:
                        i = new Intent(holder.mIdView.getContext(), ActivityEatHealthy.class);
                        break;
                    case 6:
                        i = new Intent(holder.mIdView.getContext(), HabitTraking.class);
                        break;
                    case 7:
                        i = new Intent(holder.mIdView.getContext(), ActivityExperienceNature.class);
                        break;
                    case 8:
                        i = new Intent(holder.mIdView.getContext(), ActivityRevealStory.class);
                        break;
                    case 9:
                        i = new Intent(holder.mIdView.getContext(), ActivityOurBelief.class);
                        break;
                }


                Bundle mBundle = new Bundle();
                mBundle.putString(Constants.common_auth, Constants.common_google);
                mBundle.putStringArrayList(Constants.Intent_ArrayCaptain, userArrayCaptains);
                mBundle.putIntegerArrayList(Constants.Intent_ArrayGameScore, userGameArray);
                i.putExtras(mBundle);
                holder.mView.getContext().startActivity(i);
            }
        });


        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(Resources.getSystem().getString(R.string.Display_fields_Integrity))) {
                    Intent homepage = new Intent(view.getContext(), ActivityIntegrityMain.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString(Resources.getSystem().getString(R.string.common_auth), Resources.getSystem().getString(R.string.common_google));
                    mBundle.putStringArrayList(Resources.getSystem().getString(R.string.Intent_ArrayCaptain), userArrayCaptains);
                    mBundle.putIntegerArrayList(Resources.getSystem().getString(R.string.Intent_ArrayGameScore), userGameArray);
                    homepage.putExtras(mBundle);
                    holder.mView.getContext().startActivity(homepage);
                } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(Resources.getSystem().getString(R.string.Display_fields_Attention))) {
                    Intent homepage = new Intent(view.getContext(), ActivityAttention.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString(Resources.getSystem().getString(R.string.common_auth), Resources.getSystem().getString(R.string.common_google));
                    mBundle.putIntegerArrayList(Resources.getSystem().getString(R.string.Intent_ArrayGameScore), userGameArray);
                    mBundle.putStringArrayList(Resources.getSystem().getString(R.string.Intent_ArrayCaptain), userArrayCaptains);
                    homepage.putExtras(mBundle);
                    holder.mView.getContext().startActivity(homepage);
                } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(Resources.getSystem().getString(R.string.Display_fields_Meditation))) {
                    Intent homepage = new Intent(view.getContext(), MeditationActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString(Resources.getSystem().getString(R.string.common_auth), Resources.getSystem().getString(R.string.common_google));
                    mBundle.putStringArrayList(Resources.getSystem().getString(R.string.Intent_ArrayCaptain), userArrayCaptains);
                    mBundle.putIntegerArrayList(Resources.getSystem().getString(R.string.Intent_ArrayGameScore), userGameArray);
                    homepage.putExtras(mBundle);
                    holder.mView.getContext().startActivity(homepage);
                } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(Resources.getSystem().getString(R.string.Display_fields_TrueLearning))) {
                    Intent homepage = new Intent(view.getContext(), Activity_TrueLearning.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString(Resources.getSystem().getString(R.string.common_auth), Resources.getSystem().getString(R.string.common_google));
                    mBundle.putStringArrayList(Resources.getSystem().getString(R.string.Intent_ArrayCaptain), userArrayCaptains);
                    mBundle.putIntegerArrayList(Resources.getSystem().getString(R.string.Intent_ArrayGameScore), userGameArray);
                    homepage.putExtras(mBundle);
                    holder.mView.getContext().startActivity(homepage);
                } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(Resources.getSystem().getString(R.string.Display_fields_Happy))) {
                    Intent homepage = new Intent(view.getContext(), ActivityHappiness.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString(Resources.getSystem().getString(R.string.common_auth), Resources.getSystem().getString(R.string.common_google));
                    mBundle.putStringArrayList(Resources.getSystem().getString(R.string.Intent_ArrayCaptain), userArrayCaptains);
                    mBundle.putIntegerArrayList(Resources.getSystem().getString(R.string.Intent_ArrayGameScore), userGameArray);

                    homepage.putExtras(mBundle);
                    holder.mView.getContext().startActivity(homepage);
                } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(Resources.getSystem().getString(R.string.Display_fields_EatHealthy))) {
                    Intent homepage = new Intent(view.getContext(), ActivityEatHealthy.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString(Resources.getSystem().getString(R.string.common_auth), Resources.getSystem().getString(R.string.common_google));
                    mBundle.putStringArrayList(Resources.getSystem().getString(R.string.Intent_ArrayCaptain), userArrayCaptains);
                    mBundle.putIntegerArrayList(Resources.getSystem().getString(R.string.Intent_ArrayGameScore), userGameArray);

                    homepage.putExtras(mBundle);
                    holder.mView.getContext().startActivity(homepage);
                } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(Resources.getSystem().getString(R.string.Display_fields_Habit))) {
                    Intent homepage = new Intent(view.getContext(), HabitTraking.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString(Resources.getSystem().getString(R.string.common_auth), Resources.getSystem().getString(R.string.common_google));
                    mBundle.putStringArrayList(Resources.getSystem().getString(R.string.Intent_ArrayCaptain), userArrayCaptains);
                    mBundle.putIntegerArrayList(Resources.getSystem().getString(R.string.Intent_ArrayGameScore), userGameArray);

                    homepage.putExtras(mBundle);
                    holder.mView.getContext().startActivity(homepage);
                } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(Resources.getSystem().getString(R.string.Display_fields_Excercise))) {
                    Intent homepage = new Intent(view.getContext(), ActivityExperienceNature.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString(Resources.getSystem().getString(R.string.common_auth), Resources.getSystem().getString(R.string.common_google));
                    mBundle.putStringArrayList(Resources.getSystem().getString(R.string.Intent_ArrayCaptain), userArrayCaptains);
                    mBundle.putIntegerArrayList(Resources.getSystem().getString(R.string.Intent_ArrayGameScore), userGameArray);

                    homepage.putExtras(mBundle);
                    holder.mView.getContext().startActivity(homepage);
                } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(Resources.getSystem().getString(R.string.Display_fields_Review))) {
                    Intent homepage = new Intent(view.getContext(), ActivityRevealStory.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString(Resources.getSystem().getString(R.string.common_auth), Resources.getSystem().getString(R.string.common_google));
                    mBundle.putStringArrayList(Resources.getSystem().getString(R.string.Intent_ArrayCaptain), userArrayCaptains);
                    mBundle.putIntegerArrayList(Resources.getSystem().getString(R.string.Intent_ArrayGameScore), userGameArray);

                    homepage.putExtras(mBundle);
                    holder.mView.getContext().startActivity(homepage);
                } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(Resources.getSystem().getString(R.string.Display_fields_Visualization))) {
                    Intent homepage = new Intent(view.getContext(), ActivityOurBelief.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString(Resources.getSystem().getString(R.string.common_auth), Resources.getSystem().getString(R.string.common_google));
                    mBundle.putStringArrayList(Resources.getSystem().getString(R.string.Intent_ArrayCaptain), userArrayCaptains);
                    mBundle.putIntegerArrayList(Resources.getSystem().getString(R.string.Intent_ArrayGameScore), userGameArray);
                    homepage.putExtras(mBundle);
                    holder.mView.getContext().startActivity(homepage);
                }
            }
        });

    }

    @NonNull
    @Override
    public ViewHolderMainObjective onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mainlist, parent, false);
        ViewHolderMainObjective viewHolder = new ViewHolderMainObjective(view);
        return viewHolder;

    }

    public static class ViewHolderMainObjective extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ImageView mImageView;
        public final ImageView mImageViewCheck;
        public final TextView mIdViewFirstLetter;

        public CommonData mItem;

        public ViewHolderMainObjective(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_name);
            mImageView = view.findViewById(R.id.imgBtn);
            mImageViewCheck = view.findViewById(R.id.imgTick);
            mIdViewFirstLetter = view.findViewById(R.id.item_name_FirstLetter);

            /*
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getAdapterPosition(), v);

                    }
                }
            });*/

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int postion, View view);
    }


    public void setOnItemClickListener(ShowUserPowersAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
