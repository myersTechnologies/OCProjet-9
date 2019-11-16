package com.openclassrooms.realestatemanager.ui.fragments.main;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.Preferences;
import com.openclassrooms.realestatemanager.model.User;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.activities.second.SecondActivity;
import com.openclassrooms.realestatemanager.utils.Utils;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ConnectionFragment extends Fragment implements View.OnClickListener {

    private Button offlineLogin;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private FirebaseAuth mAuth;
    private Intent secondActivity;
    private RealEstateManagerAPIService service;
    private SignInButton googleSignIn;

    public ConnectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection, container, false);
        setViews(view);

        service = DI.getService();
        service.setActivity(getActivity(), "Connection");


        FacebookSdk.sdkInitialize(getContext());
        AppEventsLogger.activateApp(getActivity());

        mAuth = FirebaseAuth.getInstance();

        secondActivity = new Intent(getContext(), SecondActivity.class);

       int MY_CAMERA_REQUEST_CODE = 100;
       int MY_STORAGE_REQUEST_CODE = 90;
       int MY_LOCATION_REQUEST_CODE = 80;

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_STORAGE_REQUEST_CODE);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }

        callbackManager = CallbackManager.Factory.create();

        googleSignIn = view.findViewById(R.id.signInGoogle);
        loginButton =  view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);


        if (!Utils.isInternetAvailable(getActivity())) {
            loginButton.setEnabled(false);
            googleSignIn.setEnabled(false);
        } else {
            loginButton.setEnabled(true);
            googleSignIn.setEnabled(true);
            if (FirebaseAuth.getInstance().getCurrentUser() != null){
                loginButton.setEnabled(false);
                googleSignIn.setEnabled(false);
                offlineLogin.setEnabled(false);
                onAuthSuccess(FirebaseAuth.getInstance().getCurrentUser());
            } else {
                signInWithFacebook();
                signInWithGoogle();
            }

        }

        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
        if (service.getUser() != null){

        } else {
            signInWithFacebook();
            signInWithGoogle();
        }

    }

    public void signInWithFacebook(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                handleFacebookToken(loginResult.getAccessToken());
                            }

                            @Override
                            public void onCancel() {
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Toast.makeText(getContext(), exception.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
    private void handleFacebookToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            try {
                                FirebaseUser fbUser = task.getResult().getUser();
                                onAuthSuccess(fbUser);
                                startActivity(secondActivity);
                            } catch (NullPointerException e){
                                Toast.makeText(getContext(), "Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Failed",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    //set google sign in client, activity for result to get users email and name
    public void signInWithGoogle(){
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 5);
            }
        });
    }

    //Firebase authentification with google
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser fbUser = task.getResult().getUser();
                            onAuthSuccess(fbUser);
                            startActivity(secondActivity);
                        }
                    }
                });
    }

    public void onAuthSuccess(FirebaseUser user){
        String userName = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        addNewUser(user.getUid(), userName, email, String.valueOf(photoUrl));

    }

    //add new user to firebase real time data base
    public void addNewUser(String userId, String name, String email, String photoUri){
        User user = new User(userId, name, email, photoUri);
        service.setUser(user);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = firebaseDatabase.getReference("users");

        //save user to database
        SaveToDatabase database = SaveToDatabase.getInstance(getActivity());
        if (database.userDao().getUser() == null) {
            database.userDao().createUserTable(user);
        }


        //check preferences
        Preferences preferences = database.preferencesDao().getPreferences();
        if (preferences != null){
            if (preferences.getUserId().equals(userId)) {
                service.setPreferences(preferences);
            } else {
                service.setPreferences(setPreferences());
            }
        } else {
            service.setPreferences(setPreferences());
            database.preferencesDao().insertPreferences(setPreferences());
        }


        databaseRef.child(userId).setValue(user);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getContext(), SecondActivity.class);
                startActivity(intent);
            }
        }, 3000);


    }

    private Preferences setPreferences(){
        Preferences preferences = new  Preferences(service.getUser().getUserId(), service.getUser().getUserId(),
                "$", service.getUser().getName(), null, null, "sq");
        return preferences;
    }


    private void setViews(View view){

        offlineLogin = view.findViewById(R.id.offline_login);
        offlineLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent secondActivity = new Intent(getActivity(), SecondActivity.class);
        int id = view.getId();
        switch (id){
            case R.id.offline_login:
                User user = SaveToDatabase.getInstance(getActivity()).userDao().getUser();
                if (user != null) {
                    addNewUser(user.getUserId(), user.getName(), user.getEmail(), user.getPhotoUri());
                } else {
                    addNewUser("12221", "Offline user", "N/A", String.valueOf(R.drawable.main_image));
                }

                startActivity(secondActivity);
                return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 5) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }
            } catch (ApiException e) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
