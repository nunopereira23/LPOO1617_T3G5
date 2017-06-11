package com.cvc.game.android;

import com.cvc.game.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.cvc.game.CVCGame;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class AndroidLauncher extends AndroidApplication {
    TextView txtStatus;
    LoginButton login_button;
    CallbackManager callbackManager;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false; // Defaults to true
        config.useCompass = false; // Defaults to true
        initialize(new CVCGame(), config);
        initializeControls();
        loginWithFacebook();
    }


	private void initializeControls(){
        callbackManager = CallbackManager.Factory.create();
		txtStatus=(TextView)findViewById(R.id.login_button);
        login_button=(LoginButton)findViewById(R.id.login_button);
	}

	private void loginWithFacebook(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                txtStatus.setText("Login Success\n!" + loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                txtStatus.setText("Login Canceled.");
            }

            @Override
            public void onError(FacebookException error) {
                txtStatus.setText("Login Error: " +error.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}

