package ai.meya.orb_demo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

import android.view.Menu;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup toolbar and enable default toolbar back button
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavHostFragment navHostFragment = getNavHostFragment();
        navController = navHostFragment.getNavController();
        setSupportActionBar(toolbar);
        NavigationUI.setupActionBarWithNavController(this, navController);

        // Create a cached flutter engine to be used in the ChatFragment. This improves ChatFragment
        // load time.
        FlutterEngine flutterEngine = new FlutterEngine(getApplicationContext());
        flutterEngine.getDartExecutor().executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault());
        FlutterEngineCache.getInstance().put("orb_fragment", flutterEngine);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    public void onPostResume() {
       super.onPostResume();
       ChatFragment chatFragment = getChatFragment();
       if (chatFragment != null) chatFragment.onPostResume();
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
       super.onNewIntent(intent);
       ChatFragment chatFragment = getChatFragment();
       if (chatFragment != null) chatFragment.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        ChatFragment chatFragment = getChatFragment();
        if (chatFragment != null) chatFragment.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ChatFragment chatFragment = getChatFragment();
        if (chatFragment != null) chatFragment.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ChatFragment chatFragment = getChatFragment();
        if (chatFragment != null) chatFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onUserLeaveHint() {
        super.onUserLeaveHint();
        ChatFragment chatFragment = getChatFragment();
        if (chatFragment != null) chatFragment.onUserLeaveHint();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ChatFragment chatFragment = getChatFragment();
        if (chatFragment != null) chatFragment.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ChatFragment chatFragment = getChatFragment();
        if (chatFragment != null) chatFragment.onLowMemory();
    }

    private NavHostFragment getNavHostFragment() {
        return (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
    }

    private ChatFragment getChatFragment() {
        ChatFragment chatFragment = null;
        NavHostFragment navHostFragment = getNavHostFragment();

        if (navHostFragment != null) {
            List<Fragment> fragmentList = navHostFragment.getChildFragmentManager().getFragments();
            for (Fragment fragment: fragmentList) {
                if (fragment instanceof ChatFragment) {
                    chatFragment = (ChatFragment) fragment;
                    break;
                }
            }
        }
        return chatFragment;
    }
}