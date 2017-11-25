package id.ac.umn.mobile.mymaps;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;

import Modules.AesEncrypt;
import Modules.SessionManagement;


public class MainActivity extends AppCompatActivity {

    SessionManagement session;
    Button btnLogout;
    TextView txtUname, txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mytollbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(mytollbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*mytollbar.setNavigationIcon(getResources().getDrawable(R.drawable.));
        mytollbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        session = new SessionManagement(getApplicationContext());
        txtUname = (TextView) findViewById(R.id.lblUsername);
        txtPass = (TextView) findViewById(R.id.lblPassword);

        btnLogout = (Button) findViewById(R.id.logoutBtn);
        Toast.makeText(getApplicationContext(),"User Login Status : "+ session.isLoggedIn(), Toast.LENGTH_LONG).show();

        session.checkLogin();
        HashMap<String, String> user = session.getUserDetail();

        String username = user.get(SessionManagement.KEY_UNAME);
        String password = user.get(SessionManagement.KEY_PASS);

        txtUname.setText(Html.fromHtml("Name :<b>"+username+"</b>"));
        txtPass.setText(Html.fromHtml("Password :<b>"+password+"</b>"));

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });
    }
}
