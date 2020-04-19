package au.edu.unsw.infs3634.GoogleLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

// Code below referenced from Google Developers documentation - Google Sign-In for Android
// Availability: https://developers.google.com/identity/sign-in/android/people

public class GoogleActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    Button signoutBtn;
    TextView nameTV;
    TextView emailTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        signoutBtn = findViewById(R.id.signoutBtn);
        nameTV = findViewById(R.id.name);
        emailTV = findViewById(R.id.email);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(GoogleActivity.this);
        if (acct != null) {
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();

            nameTV.setText("Name: "+personGivenName + " " + personFamilyName);
            emailTV.setText("Email: "+personEmail);
        }

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(GoogleActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(GoogleActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}
