package ai.meya.orb_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class LaunchFragment extends Fragment {
    private static final String TAG = "ChatFragment";

    String gridUrl;
    String appId;
    String integrationId;
    String deviceToken;

    EditText gridUrlInput;
    EditText appIdInput;
    EditText integrationIdInput;

    Button launchOrbButton;
    Button launchActivityButton;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        Log.d(TAG, "Creating fragment");
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }
                deviceToken = task.getResult();
            }
        });
        return inflater.inflate(R.layout.fragment_launch, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridUrlInput = (EditText) view.findViewById(R.id.gridUrlInput);
        appIdInput = (EditText) view.findViewById(R.id.appIdInput);
        integrationIdInput = (EditText) view.findViewById(R.id.integrationIdInput);
        launchOrbButton = (Button) view.findViewById(R.id.launchOrbButton);
        launchActivityButton = (Button) view.findViewById(R.id.launchActivityButton);

        launchOrbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridUrl = gridUrlInput.getText().toString();
                appId = appIdInput.getText().toString();
                integrationId = integrationIdInput.getText().toString();

                ChatFragment.Builder builder = ChatFragment.createWithCachedEngine("orb_fragment");
                Bundle bundle = builder.getArgs();
                bundle.putString("gridUrl", gridUrl);
                bundle.putString("appId", appId);
                bundle.putString("integrationId", integrationId);
                bundle.putString("deviceToken", deviceToken);
                NavHostFragment.findNavController(LaunchFragment.this)
                        .navigate(R.id.action_LaunchFragment_to_placeholder, bundle);
            }
        });
        launchActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridUrl = gridUrlInput.getText().toString();
                appId = appIdInput.getText().toString();
                integrationId = integrationIdInput.getText().toString();

                Intent chatIntent = ChatActivity.createDefaultIntent(getContext(), ChatActivity.class);
                chatIntent.putExtra("gridUrl", gridUrl);
                chatIntent.putExtra("appId", appId);
                chatIntent.putExtra("integrationId", integrationId);
                startActivity(chatIntent);
            }
        });
    }
}