package ai.meya.orb_demo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

import android.view.Menu;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup toolbar and enable default toolbar back button
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
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

    private ChatFragment getChatFragment() {
        return (ChatFragment) getSupportFragmentManager().findFragmentById(R.id.ChatFragment);
    }
}