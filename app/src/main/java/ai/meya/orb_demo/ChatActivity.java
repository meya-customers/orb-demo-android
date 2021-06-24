package ai.meya.orb_demo;

import ai.meya.orb.Orb;
import ai.meya.orb.OrbActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import ai.meya.orb.OrbConnectionOptions;
import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;


public class ChatActivity extends OrbActivity {
    private static final String TAG = "ChatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }
                orb.deviceToken = task.getResult();
                orbConnect();
            }
        });
    }

    private void orbConnect() {
        String platformVersion = "Android " + android.os.Build.VERSION.RELEASE;

        String gridUrl = getIntent().getStringExtra("gridUrl");
        if (gridUrl == null) gridUrl = "https://grid.meya.ai";

        String appId = getIntent().getStringExtra("appId");
        if (appId == null) appId = "app-73c6d31d4f544a72941e21fb518b5737";

        String integrationId = getIntent().getStringExtra("integrationId");
        if (integrationId == null) integrationId = "integration.orb";

        Map<String, Object> pageContext = new HashMap<>();
        pageContext.put("platform_version", platformVersion);
        pageContext.put("key1", 1235);

        Map<String, Object> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", 12345.9);
        data.put("bool", true);
        pageContext.put("data", data);

        OrbConnectionOptions connectionOptions = new OrbConnectionOptions(
                gridUrl,
                appId,
                integrationId,
                pageContext
        );

        if (!orb.ready) {
            orb.setOnReadyListener(new Orb.ReadyListener() {
                public void onReady() {
                    Log.d(TAG, "Orb runtime ready");
                    orb.connect(connectionOptions);
                }
            });
        } else {
            orb.connect(connectionOptions);
        }

        orb.setOnCloseUiListener(new Orb.CloseUiListener() {
            @Override
            public void onCloseUi() {
                Log.d(TAG, "Close Orb");
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (orb != null) {
            Log.d(TAG, "Disconnect Orb");
            orb.disconnect();
        }
        super.onDestroy();
    }
}