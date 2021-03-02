package app.actionnation.actionapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

public class TestCoding extends AppCompatActivity {

    Button btnCreateLink, btnShareLink;
    private String[] pickerVals;
    NumberPicker picker1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_coding);
        btnCreateLink = findViewById(R.id.btn_tc_CreateLink);
        btnShareLink = findViewById(R.id.btn_tc_ShareLink);

        picker1 = findViewById(R.id.numberpicker_main_picker);
        picker1.setMaxValue(4);
        picker1.setMinValue(0);
        pickerVals  = new String[] {"dog", "cat", "lizard", "turtle", "axolotl"};
        picker1.setDisplayedValues(pickerVals);

        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker1 = picker1.getValue();
                Log.d("picker value", pickerVals[valuePicker1]);
            }
        });
/*
        btnCreateLink.setOnClickListener(new View.OnClickListener().OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateLink();
            }
        });*/

        btnCreateLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateLink();

            }
        });
    }

    public void CreateLink() {
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.thegreatindiantreasure.com/"))
                .setDomainUriPrefix("https://actionnation.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();
        shortenLongLink(dynamicLink.getUri());
        Log.e("TestCoding", dynamicLink.getUri().toString());
    }

    public void shortenLongLink(Uri shortUri) {
        // [START shorten_long_link]
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(shortUri)
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {

                            // Short link created

                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            Log.e("Short Link TestCoding", shortLink.toString());

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
                            intent.setType("text/plain");
                            startActivity(intent);


                        } else {

                            // Error

                            /*

                            Android Studio Command Type	Mac OS X Shortcuts
Reformat code	OPTION + CMD + L
Show selected API documentation	F1 / FUNCTION + F1
Generate Source Code	CMD + N
Jump to source	CMD + DOWN ARROW KEY
Delete Line	CMD + BACKSPACE
Search by symbol name	OPTION + CMD + O
Build	CMD + F9
Build and Run	CTRL + R
Toggle Project Sidebar Visibility	CMD + 1
Open Class	CMD + O
Open File ( including resources)	CMD + SHIFT + O
Recent Files Opened	CMD + E
Recently edited files	CMD + SHIFT + E
Previous Next/Previous Error	F2 / FUNCTION F2
Last Edited Location	CMD + SHIFT + BACKSPACE
Last Location	CMD + [ and CMD + ]
Go to Declaration	CMD + B
Go to Super	CMD + Y
Next Word Navigation	ALT + LEFT/RIGHT ARROW KEY
Find	CMD + F
Find in Path	SHIFT + CMD + F
Refactor Class, Method	CTRL + T




                             */


                            // ...

                        }
                    }
                });
    }

}
