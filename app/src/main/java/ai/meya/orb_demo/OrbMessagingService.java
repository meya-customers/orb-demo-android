package ai.meya.orb_demo;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import androidx.annotation.NonNull;

public class OrbMessagingService extends FirebaseMessagingService {
    private static final String TAG = "OrbMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            Log.d(TAG, "Message data payload: " + data);

            // Meya notifications always contain the "meya_integration_id" key to identify which
            // Orb Mobile integration in you app sent the notification.
            if (data.containsKey("meya_integration_id")) {
                // Handle Meya notifications
                if (remoteMessage.getNotification() != null) {
                    Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

                    // Uncomment this if you would like to launch the ChatActivity when a notification
                    // arrives
//                    Intent chatIntent = ChatActivity.createDefaultIntent(this, ChatActivity.class);
//                    chatIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    chatIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(chatIntent);
                }
            }
        }

        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }
}
