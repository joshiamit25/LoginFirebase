package in.naveen.loginfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
Button registeruser,skip;
    ImageView iv;
    ProgressDialog pd;

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.homeimage);
        inputEmail = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        registeruser = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.loginbutton);
        skip = (Button) findViewById(R.id.btn_skip);
        Animation an = AnimationUtils.loadAnimation(getApplication(), R.anim.animation_file);
        iv.startAnimation(an);
        auth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd= new ProgressDialog(MainActivity.this);

                pd.setMessage("Loading...");
                pd.show();
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email) && !isValidEmail(email)) {
                    Toast.makeText(getApplicationContext(), "Enter a valid email address!", Toast.LENGTH_SHORT).show();
                    pd.cancel();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    pd.cancel();
                    return;
                }

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        //inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        pd.cancel();
                                        Toast.makeText(MainActivity.this, "Try Again", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    pd.cancel();
                                    Intent intent = new Intent(MainActivity.this, WelcomeActivity
                                            .class);
                                    intent.putExtra("usernamekey", inputEmail.getText().toString());
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

            }
        });



        registeruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WelcomeActivity
                        .class);
                intent.putExtra("usernamekey", inputEmail.getText().toString());
                startActivity(intent);            }
        });

	


    }
	
	public final static boolean isValidEmail(CharSequence target)
	{
		return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}
	
}
