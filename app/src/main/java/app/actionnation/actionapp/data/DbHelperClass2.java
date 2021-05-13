package app.actionnation.actionapp.data;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import app.actionnation.actionapp.ActivityGameCreation;
import app.actionnation.actionapp.Database_Content.TeamGame;
import app.actionnation.actionapp.R;

public class DbHelperClass2 {


    String TAG = "DBHELPER CLASS2";

    public DbHelperClass2() {

    }

    public FirestoreRecyclerAdapter GetFireStoreAdapterSelectGame(com.google.firebase.firestore.Query query) {
      final FirestoreRecyclerOptions<TeamGame> options = new FirestoreRecyclerOptions.Builder<TeamGame>()
                .setQuery(query, TeamGame.class)
                .build();
        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<TeamGame, ActivityGameCreation.ViewHolderSelectGame>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ActivityGameCreation.ViewHolderSelectGame holder, int position, @NonNull TeamGame model) {
          //      holder.mIdSelectGame.setText(model.getGameName());
                holder.mIdGameName.setText(model.getGameName());
            }

            @NonNull
            @Override
            public ActivityGameCreation.ViewHolderSelectGame onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_gameselection, parent, false);
                return new ActivityGameCreation.ViewHolderSelectGame(view);
            }
        };
        return adapter;
    }


    public void updateFireUserData(String collectionReference, String userId, String dataVariable, Object dataObject, FirebaseFirestore db) {

        Map<String, Object> userVariable = new HashMap<>();
        userVariable.put(dataVariable, dataObject);

        db.collection(collectionReference).document(userId)
                .update(userVariable)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }


}
