package ai.meya.orb_demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ai.meya.orb.Orb;
import ai.meya.orb.OrbConnectionOptions;
import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterFragment;

public class ChatFragment extends FlutterFragment {
    private static final String TAG = "ChatFragment";

    public Orb orb;

    public static class Builder extends FlutterFragment.CachedEngineFragmentBuilder {
        private Builder(@NonNull String engineId) {
            super(ChatFragment.class, engineId);
        }

        public Bundle getArgs() {
            return super.createArgs();
        }
    }

    public static Builder createWithCachedEngine(@NonNull String engineId) {
        return new Builder(engineId);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getFlutterEngine() != null) {
            Log.d(TAG, "Existing Flutter engine");
            this.orb = new Orb(getContext(), this.getFlutterEngine());
        } else {
            Log.d(TAG, "New Flutter engine");
            this.orb = new Orb(getContext());
        }

        Bundle bundle = this.getArguments();
        String gridUrl = bundle.getString("gridUrl");
        String appId = bundle.getString("appId");
        String integrationId = bundle.getString("integrationId");

        OrbConnectionOptions connectionOptions = new OrbConnectionOptions(
            gridUrl,
            appId,
            integrationId
        );
        connectionOptions.enableCloseButton = false;

        orb.connect(connectionOptions);
    }
}