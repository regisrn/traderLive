package rsn.traderlive;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {


    private static final String FIREBASEDB_LOGINSEFETUADOS = "__loginsEfetuados";


    private final String LOGIN = "LOGIN";
    private final String EMAIL = "EMAIL";
    private final String PASSWORD = "PASSWORD";
    private final String ACCESS_FROM = "ACCESS_FROM";
    private final String CAME_FROM_LOGIN = "CAME_FROM_LOGIN";


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();
    private DatabaseReference _loginsEfetuados;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    private UserLoginTask mAuthTask = null;
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        Button buttonEntrar = (Button) findViewById(R.id.buttonEntrar);

        final TextView changePass = (TextView) findViewById(R.id.change_pass);


        //((ProgressBar) findViewById(R.id.login_progress)).co


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            }
        };


        buttonEntrar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


        changePass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass.setTextColor(Color.rgb(255,127,80));
                changePass.setEnabled(false);
                changePass();
            }
        });

        //pega o último usuário logado no app
        getUser();



    }


    private void changePass() {

        View focusView = null;
        String email = mEmailView.getText().toString();

        if (mEmailView == null || TextUtils.isEmpty(email) || !isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            return;
        }

        mAuth.sendPasswordResetEmail(email);

        Toast.makeText(this, "Foi enviado um email com orientações para alteração da senha", Toast.LENGTH_LONG).show();


    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);


        boolean cancel = false;
        View focusView = null;


        if (mEmailView != null && mPasswordView != null) {
            // Check for a valid email address.
            if (TextUtils.isEmpty(mEmailView.getText().toString()) || !isEmailValid(mEmailView.getText().toString())) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mEmailView;
                cancel = true;
            }


            // Check for a valid password, if the user entered one.
            if (!cancel && (TextUtils.isEmpty(mPasswordView.getText().toString()) || !isPasswordValid(mPasswordView.getText().toString()))) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

        }else {
            cancel = true;
        }



        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(mEmailView.getText().toString(), mPasswordView.getText().toString());
            mAuthTask.execute((Void) null);
        }
    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }


        @Override
        protected Boolean doInBackground(Void... params) {


            mAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        //Toast.makeText(LoginActivity.this, "Email ou Senha incorreto(s)", Toast.LENGTH_SHORT).show();
                        mPasswordView.setError(getString(R.string.errorSenhaEmail));
                        mPasswordView.requestFocus();
                        mPasswordView.setText("");

                    }else{
                        //seta o usuário que está tentando logar
                        setUser(mEmailView.getText().toString(), mPasswordView.getText().toString());
                    }

                    mAuthTask = null;
                    showProgress(false);

                }
            });


            // TODO: register the new account here.
            return true;
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }




    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);



    }





    public void getUser() {
        SharedPreferences prefs = getSharedPreferences(LOGIN, MODE_PRIVATE);
        String email = prefs.getString(EMAIL, null);
        String password = prefs.getString(PASSWORD, null);
        ((EditText) findViewById(R.id.email)).setText(email);
        ((EditText) findViewById(R.id.password)).setText(password);
    }


    public void setUser(String email, String password) {

        //ref.child(FIREBASEDB_LOGINSEFETUADOS).child(email.substring(0,email.indexOf("@"))).setValue(email);

        SharedPreferences.Editor editor = getSharedPreferences(LOGIN, MODE_PRIVATE).edit();
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.putString(ACCESS_FROM, CAME_FROM_LOGIN);
        editor.commit();
    }




    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        boolean emailValid;

        if (email.contains("@") && email.contains(".") && email.length()>10) {
            return true;
        }

        return  false;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        boolean passValid;

        passValid = (password.length() >= 6 && password.length() <= 20);


        return passValid;
    }







    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
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
    }*/

}

