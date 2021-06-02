package ai.meya.orb_demo;

import ai.meya.orb.Orb;
import ai.meya.orb.OrbActivity;

import android.os.Bundle;
import android.util.Log;

import ai.meya.orb.OrbConnectionOptions;

import java.util.HashMap;
import java.util.Map;


public class ChatActivity extends OrbActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Orb", "================================");
        String platformVersion = "Android " + android.os.Build.VERSION.RELEASE;

        Map<String, Object> pageContext = new HashMap<>();
        pageContext.put("platform_version", platformVersion);
        pageContext.put("key1", 1235);

        Map<String, Object> data = new HashMap<>();
        data.put("key1", "value1");
        data.put("key2", 12345.9);
        data.put("bool", true);
        pageContext.put("data", data);

        orb.setOnReadyListener(new Orb.ReadyListener() {
            public void onReady() {
                Log.d("Orb", "Orb runtime ready");
                orb.connect(new OrbConnectionOptions(
                        "https://grid-rvn-dev.meya.ai",
                        "app-edf4be8b0f984a8db8823d8074beeb83",
                        "integration.orb",
                        pageContext
                ));
            }
        });
    }
}