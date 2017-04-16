package sell;

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
public class SellerLogin extends AppCompatActivity {
    String serverResponse="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_login);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }
    public void SellerLoginNow(View view){
        EditText sellerUsername = (EditText)findViewById(R.id.sellerName);
        EditText sellerPassword = (EditText)findViewById(R.id.sellerPassword);
        String userName = sellerUsername.getText().toString();
        String sellerPass = sellerPassword.getText().toString();
        if (userName.length()<1){
            Toast.makeText(getApplicationContext(), "USERNAME can't be left blank", Toast.LENGTH_SHORT).show();
        }
        if(sellerPass.length()<4){
            Toast.makeText(getApplicationContext(),"Password should be greater than 3 ",Toast.LENGTH_SHORT).show();
        }
        //send to server
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(MainActivity.SERVER_IP_ADDRESS)
                .appendPath("snackstime")
                .appendPath("sellerLogin.jsp")
                .appendQueryParameter("username",userName)
                .appendQueryParameter("pass",sellerPass);
        String myUrl = builder.build().toString();
        serverResponse = "";
        myUrl = myUrl.replace("%3A",":");
        SellerLoginCheck sellerLoginCheck = new SellerLoginCheck();
        sellerLoginCheck.execute(myUrl);
        //Intent sellerHomeActivity = new Intent(SellerLogin.this,SellerHome.class);
        //startActivity(sellerHomeActivity);
        Log.d("url:-", myUrl);
    }
    public class SellerLoginCheck extends AsyncTask<String, Void, Boolean> {
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
                    serverResponse = serverResponse+str;
                    Log.d("CustomerRegistration: ", str);
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
            Log.d("CustomerRegistration: ", "runs"+serverResponse);
            serverResponse = serverResponse.replace(" ","");
            int check = Integer.parseInt(serverResponse);
            if (check == 1) {
                Toast.makeText(getApplicationContext(), "Login Succcessful", Toast.LENGTH_LONG).show();
                Intent sellerHomeActivity = new Intent(SellerLogin.this,SellerHome.class);
                startActivity(sellerHomeActivity);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Username Password Mismatch", Toast.LENGTH_LONG).show();
                return;
            }
            Log.d("CustomerRegistartn:// ", "onPostExecution ");
          //  setContentView(R.layout.technician_registration_successful);
        }
    }
    @Override
    public void onBackPressed(){
        Log.d("CDA", "onBackPressed Called");
        Intent mainActivity = new Intent(SellerLogin.this,MainActivity.class);
        startActivity(mainActivity);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}