package rsn.traderlive;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;




public class RefreshService extends Service {


    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Aqui--> ", "onStartCommand");
        // START_STICKY serve para executar seu serviço até que você pare ele, é reiniciado automaticamente sempre que termina

        //startActivity(new Intent(NotificationService.this, LoginActivity.class));

        Intent dialogIntent = new Intent(this, HomeActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);

        return START_STICKY;
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}
