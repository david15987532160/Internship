package com.example.quocanhnguyen.facebookapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ProfilePictureView profilePictureView;
    LoginButton loginButton;
    Button btnLogout, btnFunction;
    TextView txtvName, txtvEmail, txtvFirstName;
    CallbackManager callbackManager;
    String email, name, firstname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);

        Implement();

        setVisibility();
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        setLogin_Button();
        setLogout_Button();
        ChangeScreen();

//        try {
//        PackageInfo info = null;
//        try {
//            info = getPackageManager().getPackageInfo(
//                    "com.example.quocanhnguyen.facebookapp",
//                    PackageManager.GET_SIGNATURES);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        for (Signature signature : info.signatures) {
//            MessageDigest md = MessageDigest.getInstance("SHA");
//            md.update(signature.toByteArray());
//            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//        }
//    } catch (NoSuchAlgorithmException e) {
//
//    }
    }

    private void setVisibility() {
        btnFunction.setVisibility(View.INVISIBLE);
        btnLogout.setVisibility(View.INVISIBLE);
        txtvName.setVisibility(View.INVISIBLE);
        txtvEmail.setVisibility(View.INVISIBLE);
        txtvFirstName.setVisibility(View.INVISIBLE);
    }

    private void ChangeScreen() {
        btnFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FunctionScreen.class);
                startActivity(intent);
            }
        });
    }

    private void setLogout_Button() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                setVisibility();
                txtvEmail.setText("");
                txtvName.setText("");
                txtvFirstName.setText("");
                profilePictureView.setProfileId(null);
                loginButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setLogin_Button() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.INVISIBLE);
                btnFunction.setVisibility(View.VISIBLE);
                btnLogout.setVisibility(View.VISIBLE);
                txtvName.setVisibility(View.VISIBLE);
                txtvEmail.setVisibility(View.VISIBLE);
                txtvFirstName.setVisibility(View.VISIBLE);
                result();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void result() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
//                Log.d("JSON", response.getJSONObject().toString());
                try {
                    email = object.getString("email");
                    name = object.getString("name");
                    firstname = object.getString("first_name");

                    profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
                    txtvName.setText(name);
                    txtvEmail.setText(email);
                    txtvFirstName.setText(firstname);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name, email, first_name");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }


    private void Implement() {
        profilePictureView = (ProfilePictureView) findViewById(R.id.imageProfilePictureView);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        btnLogout = (Button) findViewById(R.id.buttonLogout);
        btnFunction = (Button) findViewById(R.id.buttonFunction);
        txtvName = (TextView) findViewById(R.id.textViewName);
        txtvEmail = (TextView) findViewById(R.id.textViewEmail);
        txtvFirstName = (TextView) findViewById(R.id.textViewFirstName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }
}
