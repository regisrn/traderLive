package rsn.traderlive;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    private static final String FIREBASEDB_ALERTA = "_alerta";
    private static final String FIREBASEDB_RESIGN = "_reSignIn";
    private static final String FIREBASEDB_LIVE1NOTIFICATION = "live1Notification";
    private static final String FIREBASEDB_LIVE1 = "live1";
    private static final String FIREBASEDB_LIVE2 = "live2";
    private final String LOGIN = "LOGIN";
    private final String ACCESS_FROM = "ACCESS_FROM";
    private final String CAME_FROM_LOGIN = "CAME_FROM_LOGIN";


    private TabBarShow mShowTabBar;


    private ViewPager mViewPager;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();
    private DatabaseReference _alerta;
    private DatabaseReference _reSignIn;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Toast toast;
    private long lastBackPressTime = 0;


    private DatabaseReference live1;
    private DatabaseReference live1Notification;
    private String ultimoKeyLive1 = "0";
    private String ultimoValueLive1 = "0";
    String accessFrom;
    List<JogoModel> jogos = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
            }
        };



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mShowTabBar = new TabBarShow(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mShowTabBar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);





/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.alerta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, aviso, Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();



        if (id == R.id.menu_videos) {

            Intent intent = new Intent();
            intent.setClass(HomeActivity.this, MenuVideosActivity.class);
            startActivity(intent);
            finish();

            return true;
        }


        if (id == R.id.menu_desempenho) {

            Intent intent = new Intent();
            intent.setClass(HomeActivity.this, MenuDesempenhoActivity.class);
            startActivity(intent);
            finish();

            return true;
        }

        if (id == R.id.menu_parceiros) {

            Intent intent = new Intent();
            intent.setClass(HomeActivity.this, MenuParceiroActivity.class);
            startActivity(intent);
            finish();

            return true;
        }

        if (id == R.id.menu_neteller) {

            Intent intent = new Intent();
            intent.setClass(HomeActivity.this, MenuNetellerActivity.class);
            startActivity(intent);
            finish();

            return true;
        }

        if (id == R.id.menu_ajuda) {

            Intent intent = new Intent();
            intent.setClass(HomeActivity.this, MenuAjudaActivity.class);
            startActivity(intent);
            finish();

            return true;
        }

        if (id == R.id.menu_sair) {

            mAuth.signOut();
            //mAuth = null;

            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        _alerta = ref.child(FIREBASEDB_ALERTA);
        _alerta.addValueEventListener(new ValueEventListener() {
            //mensagem rodapé
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Snackbar snack = Snackbar.make(findViewById(R.id.activity_home_id),
                        dataSnapshot.getValue(String.class), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).setDuration(5000).setActionTextColor(Color.BLACK);

                View sbView = snack.getView();
                ((TextView) sbView.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.BLACK);
                sbView.setBackgroundColor(Color.rgb(255,127,80));
                snack.show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        _reSignIn = ref.child(FIREBASEDB_RESIGN);
        _reSignIn.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //dataSnapshot.child(FIREBASEDB_RESIGN).getValue()
                //Log.d("ok", dataSnapshot.child(FIREBASEDB_RESIGN).getValue(String.class));
                if (dataSnapshot.getValue(Boolean.class)){
                    mAuth.signOut();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);


        live1 = ref.child(FIREBASEDB_LIVE1);
        live1.addValueEventListener(new ValueEventListener() {
            JogoModel jogo;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                jogos = new ArrayList<JogoModel>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ultimoKeyLive1 = postSnapshot.getKey().toString();
                    ultimoValueLive1 = postSnapshot.getValue().toString();

                    jogo = new JogoModel();
                    jogo.setJogo(postSnapshot.getKey());
                    jogo.setEntrada(postSnapshot.getValue(String.class));
                    jogos.add(jogo);
                }

                //lastMsgLive = ultimoKeyLive1+ultimoValueLive1;
                accessFrom = getSharedPreferencesNotifications();
                //Log.v("===> ", accessFrom);

                if ((mAuth != null) && (mAuth.getCurrentUser() != null)
                        //&& (mAuth.getCurrentUser().getEmail().equals("traderlivebr@gmail.com"))
                        && accessFrom != null
                        && !accessFrom.equals(CAME_FROM_LOGIN)
                        && !accessFrom.equals(ultimoKeyLive1+ultimoValueLive1)
                        && ultimoKeyLive1 != null
                        && !ultimoKeyLive1.trim().equals("")
                        && !ultimoKeyLive1.equals(" ")
                        && !ultimoKeyLive1.equals("   "))  {

                    sendNotification(ultimoKeyLive1, ultimoValueLive1);
                }

                setSharedPreferencesNotifications(ultimoKeyLive1, ultimoValueLive1);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });




/*
        live1Notification = ref.child(FIREBASEDB_LIVE1NOTIFICATION);
        live1Notification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //((TextView)view.findViewById(R.id._vivoProximoJogo)).setText(dataSnapshot.getValue(String.class));
                if (dataSnapshot.getValue().equals("exibir") && (mAuth != null) && (mAuth.getCurrentUser() != null)
                        && (mAuth.getCurrentUser().getEmail().equals("traderlivebr@gmail.com")) && (System.currentTimeMillis() > (time + 60000))){

                    time = System.currentTimeMillis();

                    stopNotification = true;
                    sendNotification(ultimoKeyLive1, ultimoValueLive1);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
/*
        //altera tag live1Notification para no
        if (stopNotification && mAuth != null && mAuth.getCurrentUser() != null && System.currentTimeMillis() > (time + 50000) &&
                (  mAuth.getCurrentUser().getEmail().equals("traderlivebr@gmail.com")
                        || mAuth.getCurrentUser().getEmail().equals("bcbarbosa20@gmail.com")
                        || mAuth.getCurrentUser().getEmail().equals("fernandopapini3@gmail.com")
                        || mAuth.getCurrentUser().getEmail().equals("cairotuf@gmail.com")
                )
                ) {


            live1Notification.setValue("no");
            stopNotification = false;

        }
*/


    }


    private void sendNotification(String ultimoKeyLive1, String ultimoValueLive1) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo))
                .setContentTitle(ultimoKeyLive1)
                .setContentText(ultimoValueLive1)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notificationBuilder.build());
    }



    public String getSharedPreferencesNotifications() {

        return getSharedPreferences(LOGIN, MODE_PRIVATE).getString(ACCESS_FROM, null);
    }


    public void setSharedPreferencesNotifications(String ultimoKeyLive1, String ultimoValueLive1) {
        SharedPreferences.Editor editor = getSharedPreferences(LOGIN, MODE_PRIVATE).edit();
        editor.putString(ACCESS_FROM, ultimoKeyLive1+ultimoValueLive1);
        editor.commit();
    }




    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



/*    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        finish();
    }
*/





/*
    @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 3000) {
            toast = Toast.makeText(this, "Pressione novamente para fechar o App.", Toast.LENGTH_SHORT);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
            //mAuth.signOut();

        } else {

            if (toast != null) {
                toast.cancel();
            }
            super.onBackPressed();

        }
    }
*/


    //retirar esse método
    public void sendNotification1(String ultimoKeyLive1, String ultimoValueLive1) {

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setTicker("TraderLive");
        builder.setContentTitle(ultimoKeyLive1);
        builder.setContentText(ultimoValueLive1);
        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo));
        builder.setContentIntent(p);

        Notification n = builder.build();
        n.flags = Notification.FLAG_AUTO_CANCEL;
        n.vibrate = new long[] {150,300,150,600};



        try {
            nm.notify(R.drawable.logo, n);
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(this, som);
            toque.play();

        }catch(Exception e){
            Log.e("Erro=>", "");
            e.printStackTrace();
        }

    }


}



