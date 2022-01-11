package rsn.traderlive;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuProjetoPActivity extends AppCompatActivity {


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeto_p);

    }



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mRootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ((TextView)findViewById(R.id._projetoPName)).setText(dataSnapshot.child("_projetoPName").getValue(String.class));
                ((TextView)findViewById(R.id._projetoPInicio)).setText(dataSnapshot.child("_projetoPInicio").getValue(String.class));
                ((TextView)findViewById(R.id._projetoPObjetivo)).setText(dataSnapshot.child("_projetoPObjetivo").getValue(String.class));
                ((TextView)findViewById(R.id._projetoPPrazo)).setText(dataSnapshot.child("_projetoPPrazo").getValue(String.class));
                ((TextView)findViewById(R.id._projetoPBancaInicial)).setText(dataSnapshot.child("_projetoPBancaInicial").getValue(String.class));
                ((TextView)findViewById(R.id._projetoPBancaAtual)).setText(dataSnapshot.child("_projetoPBancaAtual").getValue(String.class));
                ((TextView)findViewById(R.id._projetoPLucroAcumulado)).setText(dataSnapshot.child("_projetoPLucroAcumulado").getValue(String.class));
                ((TextView)findViewById(R.id._projetoPLucro30Dias)).setText(dataSnapshot.child("_projetoPLucro30Dias").getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        intent.setClass(MenuProjetoPActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();


    }




}
