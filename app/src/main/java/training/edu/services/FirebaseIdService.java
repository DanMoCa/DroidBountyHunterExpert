package training.edu.services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Dan14z on 07/09/2017.
 */

public class FirebaseIdService extends FirebaseInstanceIdService {

    private static final String LOG_TAG = FirebaseIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(LOG_TAG,"Token: " + refreshedToken);
    }

    @Override
    protected Intent zzD(Intent intent) {
        intent.putExtra("","");
        return super.zzD(intent);
    }
}
