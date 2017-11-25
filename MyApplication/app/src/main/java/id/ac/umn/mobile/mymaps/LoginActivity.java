package id.ac.umn.mobile.mymaps;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Modules.*;


import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    //property
    private EditText txtUsername,txtPassword;

    Button btnLogin;

    //AlertDialog alert = new AlertDialog(this);
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = (EditText)findViewById(R.id.input_username);
        txtPassword = (EditText)findViewById(R.id.input_password);

        session = new SessionManagement(getApplicationContext());

        //kasih tau status sekarang
        //Toast.makeText(getApplicationContext(), "User login status : "+session.isLoggedIn(), Toast.LENGTH_LONG).show();

        TextView signUpLabel = (TextView) findViewById(R.id.link_signup);
        signUpLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(txtUsername.getText().toString().equals("")){
                    txtUsername.setError("username tidak boleh kosong");
                }
                if(txtPassword.getText().toString().equals("")){
                    txtPassword.setError("password tidak boleh kosong");
                }
                if(!txtUsername.getText().toString().equals("") & !txtPassword.getText().toString().equals("")){
                    loginAuthentication();
                }
            }
        });
    }

    private void loginAuthentication(){
        final String username = txtUsername.getText().toString();
        final String password = txtPassword.getText().toString();

        AesEncrypt encryptAES = new AesEncrypt();
        final String passwordEncrypt = encryptAES.encrypt(password);

        class loginAuth extends AsyncTask<Void,Void,String>{
            ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Authentication..");
                progressDialog.show();
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                progressDialog.dismiss();
                //bagian sini dimodifikasi liat volley dari hasil url
                System.out.println("nilai dari s :"+s);

                if(s.equals("sukses")){
                    Toast.makeText(getApplicationContext(),"Login berhasil!",Toast.LENGTH_SHORT).show();
                    session.createLoginSession(username,password);
                    Intent i = new Intent(getApplicationContext(), ScrollMenu.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Username atau password yang dimasukan salah!",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            protected String doInBackground(Void... v){
                HashMap<String, String>params = new HashMap<>();
                params.put(Config.KEY_USERNAME,username);
                params.put(Config.KEY_PASSWORD,passwordEncrypt);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_LOGIN_AUTH,params);
                return res;
            }
        }
        loginAuth login = new loginAuth();
        login.execute();
    }
}
