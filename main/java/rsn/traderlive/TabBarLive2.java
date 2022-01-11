package rsn.traderlive;

/**
 * Created by RSN2 on 08/06/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class TabBarLive2 extends Fragment {


    private static final String ARG_SECTION_NAME = "section_name";
    private static final String FIREBASEDB_VIVOPROXJOGO = "live2Info";

    public TabBarLive2() {
    }

    View view;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();

    private DatabaseReference menu;
    private DatabaseReference live2Info;

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
        live2Info = ref.child(FIREBASEDB_VIVOPROXJOGO);

        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        live2Info.addValueEventListener(new ValueEventListener() {

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
                progressVisible(false);            }
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


