package com.example.quocanhnguyen.facebookapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class FunctionScreen extends AppCompatActivity {

    EditText edtTitle, edtDescription, edtURL;
    Button btnShareLink, btnShareImage, btnPickVideo, btnShareVideo;
    ImageView imgViewImage;
    VideoView vidView;
    ShareDialog shareDialog;
    ShareLinkContent shareLinkContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_screen);

        Implement();
        shareDialog = new ShareDialog(FunctionScreen.this);
        btnShareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ShareDialog.canShow(ShareLinkContent.class)){
                    shareLinkContent = new ShareLinkContent.Builder()
                            .setContentTitle(edtTitle.getText().toString())
                            .setContentDescription(edtDescription.getText().toString())
                            .setContentUrl(Uri.parse(edtURL.getText().toString()))
                            .build();
                }
                shareDialog.show(shareLinkContent);
            }
        });
    }

    private void Implement() {
        edtTitle = (EditText)findViewById(R.id.editTextTitle);
        edtDescription = (EditText)findViewById(R.id.editTextDescription);
        edtURL = (EditText)findViewById(R.id.editTextURL);
        btnShareLink = (Button)findViewById(R.id.buttonShareLink);
        btnShareImage = (Button)findViewById(R.id.buttonShareImage);
        btnPickVideo = (Button)findViewById(R.id.buttonPickVideo);
        btnShareVideo = (Button)findViewById(R.id.buttonShareVideo);
        imgViewImage = (ImageView)findViewById(R.id.imageViewImage);
        vidView = (VideoView)findViewById(R.id.videoView);
    }
}
