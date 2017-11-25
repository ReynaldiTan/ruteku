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

import java.util.HashMap;

import Modules.AesEncrypt;
import Modules.Config;
import Modules.RequestHandler;

public class SignUpActivity extends AppCompatActivity {
    //property
    private EditText txtUsername, txtEmail, txtPassword;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtUsername = (EditText)findViewById(R.id.input_name);
        txtEmail = (EditText)findViewById(R.id.input_email);
        txtPassword = (EditText)findViewById(R.id.input_password);

        TextView loginLabel = (TextView) findViewById(R.id.link_login);
        loginLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        btnSignup = (Button)findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsername.getText().toString().length() < 3){
                    txtUsername.setError("Username tidak boleh di bawah 3 karakter");
                }
                if(txtUsername.getText().toString().equals("")){
                    txtUsername.setError("Username tidak boleh kosong");
                }
                if(txtPassword.getText().toString().equals("")){
                    txtPassword.setError("Password tidak boleh kosong");
                }
                if(txtEmail.getText().toString().equals("")){
                    txtEmail.setError("Email tidak boleh kosong");
                }
                if(!txtEmail.getText().toString().equals("") & !txtPassword.getText().toString().equals("") & !txtUsername.getText().equals("") & txtUsername.getText().length()<3)
                {
                    signUpForm();
                }
            }
        });
    }

    private void signUpForm(){
        final String username = txtUsername.getText().toString();
        final String email = txtEmail.getText().toString();
        final String password = txtPassword.getText().toString();


        AesEncrypt encryptAES = new AesEncrypt();
        final String passwordEncrypt = encryptAES.encrypt(password);

        class signUpfrm extends AsyncTask<Void,Void,String>{
            ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                progressDialog.setMessage("Sign Up..");
                progressDialog.show();
            }
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                progressDialog.dismiss();

                if(s.equals("sukses")){
                    Toast.makeText(getApplicationContext(),"Daftar berhasil!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
                if(s.equals("gagal"))
                {
                    Toast.makeText(getApplicationContext(),"Username yang anda masukan telah terdaftar",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"mungkin koneksi bermasalah",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            protected String doInBackground(Void... v){
                HashMap<String, String>params = new HashMap<>();
                params.put(Config.KEY_USERNAME, username);
                params.put(Config.KEY_EMAIL, email);
                params.put(Config.KEY_PASSWORD, passwordEncrypt);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_SINGUP,params);
                return res;
            }
        }
        signUpfrm suf = new signUpfrm();
        suf.execute();
    }
}
