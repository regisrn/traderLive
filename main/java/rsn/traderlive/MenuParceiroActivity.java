package rsn.traderlive;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MenuParceiroActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    public static final String API_KEY = "AIzaSyDhtAf-AtkySxbuSidKK04M7-Tws3SEKzo";
    //A classe video tb utiliza a mesma chave.

    public static String VIDEO_ID;

    YouTubePlayerView youTubePlayerView;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference rf = firebaseDatabase.getReference();
    public static String FIREBASE_TIT = "parceiros";
    public static String FIREBASE_INFO1 = "info1";
    public static String FIREBASE_VIDEO1 = "video1";
    private static final String FIREBASEDB_ALERTA = "alerta";



    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_menu_parceiro);


        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube);

        youTubePlayerView.initialize(API_KEY,this);


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                VIDEO_ID = dataSnapshot.child(FIREBASE_TIT).child(FIREBASE_VIDEO1).getValue(String.class);
                //((TextView)findViewById(R.id.info1)).setText(dataSnapshot.child(FIREBASE_TIT).child(FIREBASE_INFO1).getValue(String.class));

                Snackbar.make(findViewById(R.id.layout_id), dataSnapshot.child(FIREBASE_TIT).child(FIREBASEDB_ALERTA).getValue(String.class), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).setDuration(30000)
                        .show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {


        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this,"Erro ao carregar vídeo", Toast.LENGTH_SHORT).show();
    }


    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {        }

        @Override
        public void onPaused() {        }

        @Override
        public void onStopped() {        }

        @Override
        public void onBuffering(boolean b) {        }

        @Override
        public void onSeekTo(int i) {       }
    };


    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {        }

        @Override
        public void onLoaded(String s) {        }

        @Override
        public void onAdStarted() {        }

        @Override
        public void onVideoStarted() {        }

        @Override
        public void onVideoEnded() {        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {        }
    };


    /*
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference rf = firebaseDatabase.getReference();

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_parceiro);

        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube);


        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo("zBiEjvzPkvg");
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                };


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        youTubePlayerView.setMinimumHeight(300);
        youTubePlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize(API_KEY, onInitializedListener);
            }
        });





    }
*/
/*    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);


        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //String url = "<iframe width=\"90%\" height=\"90%\" src=\"https://www.youtube.com/embed/3LiubyYpEUk\" frameborder=\"0\" allowfullscreen></iframe>";

                StringBuilder url = new StringBuilder();
                url.append("<iframe width=\"95%\" height=\"95%\" src=\"");
                url.append(dataSnapshot.child("parceiros").child("video1").getValue(String.class));
                url.append("\" frameborder=\"0\" allowfullscreen></iframe>");


                webView.loadData(url.toString(), "text/html", "utf-8");
                webView.setWebChromeClient(new WebChromeClient());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        intent.setClass(MenuParceiroActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();


    }

}
