package com.myhexaville.login.views.renders;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.myhexaville.login.R;
import com.myhexaville.login.views.fragments.AccountFragment;
import com.myhexaville.login.views.fragments.MessageFragment;
import com.myhexaville.login.views.fragments.NotificationFragment;

public class FragmentView extends AppCompatActivity {
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private MessageFragment messageFragment;
    private NotificationFragment notificationFragment;
    private AccountFragment accountFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view);

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        messageFragment = new MessageFragment();
        notificationFragment = new NotificationFragment();
        accountFragment = new AccountFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, messageFragment);
        fragmentTransaction.commit();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary)); //status bar or the time bar at the top
        }


        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_message:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(messageFragment);
                        return true;
                    case R.id.nav_notification:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(notificationFragment);
                        return true;
                    case R.id.nav_account:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(accountFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });


    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public void onButtonClick(View v){
        try{
            Toolbar mainbar = findViewById(R.id.mainbar);
            Toolbar toolbar = findViewById(R.id.toolbar);
            TextView t = (TextView) findViewById(R.id.textView);
            ImageButton mainbar_search = (ImageButton) findViewById(R.id.mainbar_search);
            ImageButton toolbar_back = (ImageButton) findViewById(R.id.toolbar_back);
            ImageButton toolbar_checked = (ImageButton) findViewById(R.id.toolbar_checked);
            EditText searcher =  (EditText) findViewById(R.id.toolbar_searcher);

            switch(v.getId()){
                case R.id.mainbar_search:
                    mainbar.setVisibility(v.INVISIBLE);
                    mainbar_search.setVisibility(v.INVISIBLE);
                    t.setVisibility(v.INVISIBLE);
                    toolbar.setVisibility(v.VISIBLE);
                    toolbar_back.setVisibility(v.VISIBLE);
                    searcher.setVisibility(v.VISIBLE);
                    toolbar_checked.setVisibility(v.VISIBLE);
                    break;
                case R.id.toolbar_back:
                    mainbar.setVisibility(v.VISIBLE);
                    mainbar_search.setVisibility(v.VISIBLE);
                    t.setVisibility(v.VISIBLE);
                    toolbar.setVisibility(v.INVISIBLE);
                    toolbar_back.setVisibility(v.INVISIBLE);
                    searcher.setVisibility(v.INVISIBLE);
                    toolbar_checked.setVisibility(v.INVISIBLE);
                    break;
                case R.id.floatingActionButton:
                    Intent newMessage = new Intent(getApplicationContext(), NewMessage.class);
                    startActivity(newMessage);
                default:
                    break;
            }
        }catch(Exception e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
