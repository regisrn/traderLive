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

public class MenuDesempenhoActivity extends AppCompatActivity {


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_desempenho);

    }



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mRootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ((TextView)findViewById(R.id.bancaInicial)).setText(dataSnapshot.child("desempenho").child("bancaInicial").getValue(String.class));
                ((TextView)findViewById(R.id.bancaAtual)).setText(dataSnapshot.child("desempenho").child("bancaAtual").getValue(String.class));
                ((TextView)findViewById(R.id.bancaLucroAcumulado)).setText(dataSnapshot.child("desempenho").child("bancaLucroAcumulado").getValue(String.class));
                ((TextView)findViewById(R.id.bancaLucroMesAnt)).setText(dataSnapshot.child("desempenho").child("bancaLucroMesAnt").getValue(String.class));
                ((TextView)findViewById(R.id.bancaLucroMesAtual)).setText(dataSnapshot.child("desempenho").child("bancaLucroMesAtual").getValue(String.class));
                ((TextView)findViewById(R.id.futLucroMesAnt)).setText(dataSnapshot.child("desempenho").child("futLucroMesAnt").getValue(String.class));
                ((TextView)findViewById(R.id.futLucroMesAtual)).setText(dataSnapshot.child("desempenho").child("futLucroMesAtual").getValue(String.class));
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
        intent.setClass(MenuDesempenhoActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();


    }




}
