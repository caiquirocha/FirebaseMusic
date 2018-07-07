package info.androidhive.firebase;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class bottom_navi extends AppCompatActivity {

    private BottomNavigationView mMainnav;
    private FrameLayout mMainFrame;
    private HomeFragment homeFragment;
    private AudioTab audiotab;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navi);

        mMainFrame=findViewById(R.id.main_frame);
        mMainnav=findViewById(R.id.main_nav);

        homeFragment = new HomeFragment();
        audiotab = new AudioTab();
        profileFragment = new ProfileFragment();

        setFragment(homeFragment);
        mMainnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.nav_songs:
                        setFragment(audiotab);
                        return true;
                    case R.id.nav_profile:
                        setFragment(profileFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }
}
