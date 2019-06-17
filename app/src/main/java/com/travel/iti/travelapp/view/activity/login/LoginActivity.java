package com.travel.iti.travelapp.view.activity.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.databinding.ActivityLoginBinding;
import com.travel.iti.travelapp.repository.local.PrefManager;
import com.travel.iti.travelapp.repository.model.User;
import com.travel.iti.travelapp.view.activity.home.MainActivity;
import com.travel.iti.travelapp.view.activity.signup.SignUpActivity;

public class LoginActivity extends AppCompatActivity  implements LoginView{

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "tag";
    private LoginViewModel loginViewModel;
    private GoogleSignInClient mGoogleSignInClient;
    private  SignInButton signInButton;
    private ActivityLoginBinding binding;
    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.init(this);
        prefManager=new PrefManager(this);

        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);

        binding.setLifecycleOwner(this);

        binding.setLoginViewModel(loginViewModel);

        loginViewModel.loginData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                binding.progressView.setVisibility(View.GONE);
                prefManager.setUserId(user.getId());
                prefManager.setUserData(user);
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        loginViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User loginUser) {

                if (TextUtils.isEmpty(loginUser.getEmail())) {
                    binding.txtEmailAddress.setError("Enter an E-Mail Address");
                    binding.txtEmailAddress.requestFocus();
                }
                else if (TextUtils.isEmpty(loginUser.getPassword())) {
                    binding.txtPassword.setError("Enter a Password");
                    binding.txtPassword.requestFocus();
                }
                else {
                    binding.progressView.setVisibility(View.VISIBLE);
                    loginViewModel.signIn(loginUser);
                }

            }
        });

        binding.btnsignup.setOnClickListener((View v)-> {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
        });
//        signInButton = findViewById(R.id.sign_in_button);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//
//            }
//        });
//
//        // Configure sign-in to request the user's ID, email address, and basic
//        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });
//
//
//    }


//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
//    }
//
//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//
//          //  firebaseAuthWithGoogle(account);
//            updateUI1(account);
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//           // updateUI(null);
//        }
//    }
//
//    private void updateUI1(GoogleSignInAccount user) {
//        if(user!=null) {
//            Toast.makeText(LoginActivity.this, "SignIn Success! ", Toast.LENGTH_LONG).show();
//        }
//        else{
//            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//        }
//
>>>>>>> 9ff9c65f83c0f75ab2c6c0371ab76014dd91fa48
    }

    public void onClick(View view) {

    }

    @Override
    public void showSuccess(String success) {
        binding.progressView.setVisibility(View.GONE);

        Toast.makeText(this, success , Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String error) {
        binding.progressView.setVisibility(View.GONE);
        Toast.makeText(this, error , Toast.LENGTH_LONG).show();

    }
}
