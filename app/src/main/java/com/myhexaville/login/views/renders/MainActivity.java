package com.myhexaville.login.views.renders;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.myhexaville.login.R;
import com.myhexaville.login.controllers.VerificationController;
import com.myhexaville.login.databinding.ActivityMainBinding;
import com.myhexaville.login.views.renders.login.LoginFragment;
import com.myhexaville.login.views.renders.login.SignUpFragment;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.myhexaville.login.views.renders.FlexibleFrameLayout.ORDER_LOGIN_STATE;
import static com.myhexaville.login.views.renders.FlexibleFrameLayout.ORDER_SIGN_UP_STATE;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private boolean isLogin = true;
    VerificationController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.splash_screen);
        db = new VerificationController(this);
        Cursor res = db.getVerifiedUser();
        if(res.getCount() == 1){
            Bundle data = new Bundle();
            while(res.moveToNext()){
                data.putString("verificationCode", res.getString(0));
            }
            Intent fragmentView = new Intent(this, FragmentView.class);
            //TODO: Get the document ID from firebase too and push to next intent.
            fragmentView.putExtras(data);
            startActivity(fragmentView);
            this.finish();
        }else{

            binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
            LoginFragment topLoginFragment = new LoginFragment();
            SignUpFragment topSignUpFragment = new SignUpFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.sign_up_fragment, topSignUpFragment)
                    .replace(R.id.login_fragment, topLoginFragment)
                    .commit();

            binding.loginFragment.setRotation(-90);

            binding.button.setOnSignUpListener(topSignUpFragment);
            binding.button.setOnLoginListener(topLoginFragment);

            binding.button.setOnButtonSwitched(isLogin -> {
                binding.getRoot()
                        .setBackgroundColor(ContextCompat.getColor(
                                this,
                                isLogin ? R.color.colorPrimary : R.color.secondPage));
            });

            binding.loginFragment.setVisibility(INVISIBLE);

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        binding.loginFragment.setPivotX(binding.loginFragment.getWidth() / 2);
        binding.loginFragment.setPivotY(binding.loginFragment.getHeight());
        binding.signUpFragment.setPivotX(binding.signUpFragment.getWidth() / 2);
        binding.signUpFragment.setPivotY(binding.signUpFragment.getHeight());
    }

    public void switchFragment(View v) {
        if (isLogin) {
            binding.loginFragment.setVisibility(VISIBLE);
            binding.loginFragment.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    binding.signUpFragment.setVisibility(INVISIBLE);
                    binding.signUpFragment.setRotation(90);
                    binding.wrapper.setDrawOrder(ORDER_LOGIN_STATE);
                }
            });
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.secondPage)); //status bar or the time bar at the top
            }
        } else {
            binding.signUpFragment.setVisibility(VISIBLE);
            binding.signUpFragment.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    binding.loginFragment.setVisibility(INVISIBLE);
                    binding.loginFragment.setRotation(-90);
                    binding.wrapper.setDrawOrder(ORDER_SIGN_UP_STATE);
                }
            });
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
            }
        }

        isLogin = !isLogin;
        binding.button.startAnimation();
    }

}