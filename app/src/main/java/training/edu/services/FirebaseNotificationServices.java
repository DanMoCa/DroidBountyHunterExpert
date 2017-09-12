package training.edu.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import training.edu.droidbountyhunter.Home;
import training.edu.droidbountyhunter.R;
import training.edu.utils.NotifyManager;

/**
 * Created by Dan14z on 07/09/2017.
 */

public class FirebaseNotificationServices extends FirebaseMessagingService {

    private static final String LOG_TAG = FirebaseNotificationServices.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.v(LOG_TAG,"From: " + remoteMessage.getFrom());
        Log.v(LOG_TAG,"Notification Message Body: " + remoteMessage.getNotification().getBody());

        NotifyManager manager = new NotifyManager();
        String body = remoteMessage.getNotification().getBody();
        String title = (remoteMessage.getNotification().getTitle() != "" ? remoteMessage.getNotification().getTitle() : "Notificacion Push");
        manager.enviarNotificacion(this, Home.class, body, title, R.mipmap.ic_launcher,0);
    }
}
