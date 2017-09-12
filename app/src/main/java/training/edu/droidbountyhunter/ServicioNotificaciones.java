package training.edu.droidbountyhunter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import training.edu.data.DBProvider;
import training.edu.models.Fugitivo;
import training.edu.utils.NotifyManager;

/**
 * Created by Dan14z on 07/09/2017.
 */


public class ServicioNotificaciones extends Service {
    private static ServicioNotificaciones instance = null;
    private Timer timer;

    @Override
    public void onCreate() {
        Toast.makeText(this,"Servicio cread",Toast.LENGTH_SHORT).show();
        instance = this;
    }

    public void enviarNotificacion(){
        try{
            String mensaje = "";
            DBProvider db = new DBProvider(this);
            ArrayList<Fugitivo> fugitivosSinNotificar = db.ObtenerFugitivosNotificacion();
            ArrayList<String[]> logsSinNotificar = db.ObtenerLogsNotificacion();
            int added = fugitivosSinNotificar.size();
            int deleted = logsSinNotificar.size();
            if(added > 0){
                mensaje += "Añadiste " + added;
                if(deleted > 0){
                    mensaje += ", Eliminaste " + deleted;
                }
            }else if(deleted > 0){
                mensaje += "Eliminaste " + deleted;
            }else{
                mensaje = "";
            }

            if(mensaje.length() > 0){
                NotifyManager manager = new NotifyManager();
                manager.enviarNotificacion(this, Home.class, mensaje, "Notification DroidBountyHunter", R.mipmap.ic_launcher,0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static boolean isRunning(){
        return instance != null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Servicio Arrancado " + startId,Toast.LENGTH_SHORT).show();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                enviarNotificacion();
            }
        }, 0, 1000 * 60);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this,"Servicio detenido",Toast.LENGTH_SHORT).show();
        instance = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
