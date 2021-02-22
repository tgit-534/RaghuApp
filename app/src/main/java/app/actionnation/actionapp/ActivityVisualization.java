package app.actionnation.actionapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;

public class ActivityVisualization extends BaseClassUser implements View.OnClickListener {
    String TAG = "ACTIVITY_VISUALIZATION";
    TextView txtVisualization;


    private ImageAdapter imageAdapter;

    ArrayList<String> f = new ArrayList<String>();// list of file paths
    File[] listFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization);
        generatePublicMenu();

        // Load Images

        getFromSdcard();
        GridView imagegrid = findViewById(R.id.PhoneImageGrid);
        imageAdapter = new ImageAdapter();
        imagegrid.setAdapter(imageAdapter);


        txtVisualization = findViewById(R.id.txt_v_des);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query query = db.collection(getString(R.string.fs_PersonVisualization)).whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                    for (DocumentSnapshot readData : queryDocumentSnapshots.getDocuments()) {
                        txtVisualization.setText(readData.get(getString(R.string.fs_PersonalVisualization_columnn)).toString());
                    }
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       /* Toast.makeText(FirestoreActivity22.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("Androidview", e.getMessage());*/
                    }
                });
    }


    public void getFromSdcard() {
        File file = new File(android.os.Environment.getExternalStorageDirectory(), getString(R.string.file_Visualization));
        // File file= new File(android.os.Environment.getExternalStorageDirectory(),);
        if (file.isDirectory()) {
            listFile = file.listFiles();

            for (int i = 0; i < listFile.length; i++) {
                f.add(listFile[i].getAbsolutePath());
            }
        }
    }

    @Override
    public void onClick(View v) {

    }


    public void deleteImage(String file_dj_path) {
        //String file_dj_path = Environment.getExternalStorageDirectory() + "/ECP_Screenshots/abc.jpg";
        File fdelete = new File(file_dj_path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                Log.e("-->", "file Deleted :" + file_dj_path);
                callBroadCast();
            } else {
                Log.e("-->", "file not Deleted :" + file_dj_path);
            }
        }
    }

    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                /*
                 *   (non-Javadoc)
                 * @see android.media.MediaScannerConnection.OnScanCompletedListener#onScanCompleted(java.lang.String, android.net.Uri)
                 */
                public void onScanCompleted(String path, Uri uri) {
                    Log.e("ExternalStorage", "Scanned " + path + ":");
                    Log.e("ExternalStorage", "-> uri=" + uri);
                }
            });
        } else {
            Log.e("-->", " < 14");
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public ImageAdapter() {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return f.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(
                        R.layout.gelleryitem, null);
                holder.imageview = convertView.findViewById(R.id.thumbImage);
                holder.btnEdit = convertView.findViewById(R.id.btn_gi_Delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
            holder.btnEdit.setTag(f.get(position));

            holder.imageview.setImageBitmap(myBitmap);

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteImage(holder.btnEdit.getTag().toString());
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        ImageView imageview;
        Button btnEdit;
    }

    public void RedirectVisualization(View view) {
        Intent intent = new Intent(ActivityVisualization.this, PersondetailsActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
        mBundle.putString(getString(R.string.Page_Redirect), getString(R.string.Page_Redirect_Visualization));
        intent.putExtras(mBundle);
        startActivity(intent);
    }
}




