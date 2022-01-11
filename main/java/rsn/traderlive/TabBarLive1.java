package rsn.traderlive;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class TabBarLive1 extends Fragment {


    private static final String ARG_SECTION_NAME = "section_name";
    private static final String FIREBASEDB_LIVE1PROXJOGO = "live1Info";


    public TabBarLive1() {
    }

    View view;


    private FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();

    private DatabaseReference menu;
    private DatabaseReference live1Info;

    List<JogoModel> jogos = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_bar_live, container, false);


        progressVisible(true);

        String stringFirebase = (String) getArguments().get(ARG_SECTION_NAME);


        menu = ref.child(stringFirebase);
        live1Info = ref.child(FIREBASEDB_LIVE1PROXJOGO);



        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onStart() {
        super.onStart();


        live1Info.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ((TextView)view.findViewById(R.id.live1Info)).setText(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        menu.addValueEventListener(new ValueEventListener() {
            JogoModel jogo;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.e("Count " ,""+dataSnapshot.getChildrenCount());

                jogos = new ArrayList<JogoModel>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    jogo = new JogoModel();
                    jogo.setJogo(postSnapshot.getKey());
                    jogo.setEntrada(postSnapshot.getValue(String.class));
                    jogos.add(jogo);
                }

                populateList();

                progressVisible(false);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressVisible(false);

            }
        });
    }



    public void populateList() {

        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listJogos);
        recyclerView.setAdapter(new TabItemShow(jogos));

    }



    private void progressVisible(Boolean b) {
        int visible = View.VISIBLE;
        if (!b) {
            visible = View.INVISIBLE;
        }

        view.findViewById(R.id.progress_list).setVisibility(visible);
        view.findViewById(R.id.insta).setVisibility(visible);
        view.findViewById(R.id.email).setVisibility(visible);


    }


}


