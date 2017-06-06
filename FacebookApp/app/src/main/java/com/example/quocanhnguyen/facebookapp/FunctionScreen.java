package com.example.quocanhnguyen.facebookapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class FunctionScreen extends AppCompatActivity {

    EditText edtTitle, edtDescription, edtURL;
    Button btnShareLink, btnShareImage, btnPickVideo, btnShareVideo;
    ImageView imgViewImage;
    VideoView vidView;
    ShareDialog shareDialog;
    ShareLinkContent shareLinkContent;
    public static int SELECT_IMAGE = 1;
    public static int PICK_VIDEO = 2;
    Bitmap bitmap;
    Uri selectVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_screen);

        Implement();
        shareDialog = new ShareDialog(FunctionScreen.this);
        btnShareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    shareLinkContent = new ShareLinkContent.Builder()
                            .setContentTitle(edtTitle.getText().toString())
                            .setContentDescription(edtDescription.getText().toString())
                            .setContentUrl(Uri.parse(edtURL.getText().toString()))
                            .build();
                }
                shareDialog.show(shareLinkContent);
            }
        });

        imgViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_IMAGE);
            }
        });

        btnShareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bitmap)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content);
            }
        });

        btnPickVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(intent, PICK_VIDEO);
            }
        });

        btnShareVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareVideo shareVideo = null;
                shareVideo = new ShareVideo.Builder()
                        .setLocalUrl(selectVideo)
                        .build();
                ShareVideoContent content = new ShareVideoContent.Builder()
                        .setVideo(shareVideo)
                        .build();
                shareDialog.show(content);
                vidView.stopPlayback();
            }
        });
    }

    private void Implement() {
        edtTitle = (EditText) findViewById(R.id.editTextTitle);
        edtDescription = (EditText) findViewById(R.id.editTextDescription);
        edtURL = (EditText) findViewById(R.id.editTextURL);
        btnShareLink = (Button) findViewById(R.id.buttonShareLink);
        btnShareImage = (Button) findViewById(R.id.buttonShareImage);
        btnPickVideo = (Button) findViewById(R.id.buttonPickVideo);
        btnShareVideo = (Button) findViewById(R.id.buttonShareVideo);
        imgViewImage = (ImageView) findViewById(R.id.imageViewImage);
        vidView = (VideoView) findViewById(R.id.videoView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgViewImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == PICK_VIDEO && resultCode == RESULT_OK) {
            selectVideo = data.getData();
            vidView.setVideoURI(selectVideo);
            vidView.start();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
