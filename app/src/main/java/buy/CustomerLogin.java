package buy;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import location.app.snackstime.MainActivity;
import location.app.snackstime.R;

/**
 * Created by ashrafiqubal on 20/04/16.
 */
public class CustomerLogin extends AppCompatActivity {
    String serverResponse = "";
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Customerogin: ", "onCreate");
        setContentView(R.layout.customer_login);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }
    public void CustomerLoginNow(View view){
        EditText customerUserName = (EditText)findViewById(R.id.customerName);
        EditText customerPassword = (EditText)findViewById(R.id.customerPassword);
        userName = customerUserName.getText().toString();
        String userPass = customerPassword.getText().toString();
        if (userName.length()<1){
            Toast.makeText(getApplicationContext(),"USERNAME can't be left blank",Toast.LENGTH_SHORT).show();
        }
        if(userPass.length()<4){
            Toast.makeText(getApplicationContext(),"Password should be greater than 3 ",Toast.LENGTH_SHORT).show();
        }
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(MainActivity.SERVER_IP_ADDRESS)
                .appendPath("snackstime")
                .appendPath("buyerLogin.jsp")
                .appendQueryParameter("email",userName)
                .appendQueryParameter("password", userPass);
        String myUrl = builder.build().toString();
        serverResponse = "";
        myUrl = myUrl.replace("%3A",":");
        CustomerLoginCheck customerLoginCheck = new CustomerLoginCheck();
        customerLoginCheck.execute(myUrl);
        Log.d("url:-", myUrl);
        //send to server
    }
    public class CustomerLoginCheck extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            Boolean prepared;
            try {
                String str;
                HttpClient myClient = new DefaultHttpClient();
                HttpGet get = new HttpGet(params[0]);
                HttpResponse myResponse = myClient.execute(get);
                BufferedReader br = new BufferedReader(new InputStreamReader(myResponse.getEntity().getContent()));
                while ((str = br.readLine()) != null) {
                    serverResponse =serverResponse+str;
                    Log.d("CustomerRegistration: ", serverResponse);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            prepared = true;
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            serverResponse = serverResponse.replace(" ","");
            double check = Double.parseDouble(serverResponse);
            if (check < 0) {
                Toast.makeText(getApplicationContext(), "UserID Password MisMatch", Toast.LENGTH_LONG).show();
            } else {
                Intent customerHome = new Intent(CustomerLogin.this,CustomerHome.class);
                customerHome.putExtra("customerEmail",userName);
                customerHome.putExtra("AMOUNT",serverResponse);
                startActivity(customerHome);
                finish();
            }
            Log.d("CustomerRegistartn:// ", "onPostExecution ");
            //setContentView(R.layout.technician_registration_successful);
        }
    }
    @Override
    public void onBackPressed(){
        Log.d("CDA", "onBackPressed Called");
        Intent mainActivity = new Intent(CustomerLogin.this,MainActivity.class);
        startActivity(mainActivity);
        finish();
    }
}
