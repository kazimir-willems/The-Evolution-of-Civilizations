package seow.evolution.com.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import seow.evolution.com.R;
import seow.evolution.com.ui.MainActivity;
import seow.evolution.com.util.MyNotificationManager;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(500);
            sendPushNotification(remoteMessage.getData().toString());
        }

        if (remoteMessage.getNotification() != null) {
            String message = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Body: " + message);
            showNotification(message);
        }
    }

    private void showNotification(String message) {
        //optionally we can display the json into log
        MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

        //creating an intent for the notification
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        mNotificationManager.showSmallNotification(getApplicationContext().getResources().getString(R.string.app_name), message, intent);
    }

    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
    private void sendPushNotification(String contentString) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + contentString);
        try {
            //getting the json data
            JSONObject json = new JSONObject(contentString);
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("sender");
            String message = data.getString("message");
            String imageUrl = data.getString("image");

            Log.v("Image", imageUrl);

            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            //if there is no image
            if(imageUrl.isEmpty()){
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

}