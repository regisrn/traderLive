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

public class MenuNetellerActivity extends AppCompatActivity {


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_neteller);

    }



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mRootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ((TextView)findViewById(R.id.compVend)).setText(dataSnapshot.child("neteller").child("compVend").getValue(String.class));
                ((TextView)findViewById(R.id.banco)).setText(dataSnapshot.child("neteller").child("banco").getValue(String.class));
                ((TextView)findViewById(R.id.agencia)).setText(dataSnapshot.child("neteller").child("agencia").getValue(String.class));
                ((TextView)findViewById(R.id.contaCorrente)).setText(dataSnapshot.child("neteller").child("contaCorrente").getValue(String.class));
                ((TextView)findViewById(R.id.contatoTel)).setText(dataSnapshot.child("neteller").child("contatoTel").getValue(String.class));
                ((TextView)findViewById(R.id.contatoEmail)).setText(dataSnapshot.child("neteller").child("contatoEmail").getValue(String.class));
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
        intent.setClass(MenuNetellerActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();


    }




}
