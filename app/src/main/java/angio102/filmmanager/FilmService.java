package angio102.filmmanager;

import android.app.Notification;
import android.app.Service;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


public class FilmService extends Service {
    private NotificationManager mNM;
    private String TAG = "LocalService";


    public class LocalBinder extends Binder {
        FilmService getService() {
            return FilmService.this;
        }
    }

    @Override
    public void onCreate() {
        Log.v(TAG, "onCreate: " + Thread.currentThread().getName());
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Display a notification about us starting.  We put an icon in the status bar.

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);


        CharSequence text = getText(R.string.local_service_started);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(FilmService.this, "Chapman")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle((intent.getStringExtra("filmN") + " is playing! ")) // title for notification
                .setContentText(text)// message for notification
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true); // clear notification after click


        Intent i = new Intent(FilmService.this, angio102.filmmanager.FilmDetails.class);
        i.putExtra("name",intent.getStringExtra("filmN"));
        i.putExtra("date",intent.getStringExtra("filmD"));
        i.putExtra("file",intent.getStringExtra("filmF"));

        PendingIntent pi = PendingIntent.getActivity(FilmService.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy()");
        // Cancel the persistent notification.
        mNM.cancel(R.string.local_service_started);

        // Tell the user we stopped.
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotificationManager.cancel(0);
    }

    String s = "";

    @Override
    public IBinder onBind(Intent intent) {
        s = intent.getStringExtra("test");
        return mBinder;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();


}
