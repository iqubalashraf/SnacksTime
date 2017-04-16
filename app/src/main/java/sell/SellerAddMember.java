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
public class SellerAddMember extends AppCompatActivity {
    String serverResponse="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_add_member);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }
    public void addMemberActivity(View view){
        EditText nameEditText = (EditText)findViewById(R.id.customerRegistrationName);
        EditText customerIDEditText = (EditText)findViewById(R.id.customerID);
        EditText customerPassEditText = (EditText)findViewById(R.id.customerRegistrationPassword);
        EditText customerInitialMoney = (EditText)findViewById(R.id.customerInitialMoney);
        String nameString = nameEditText.getText().toString();
        String customerIDString = customerIDEditText.getText().toString();
        String customerPassString = customerPassEditText.getText().toString();
        String customerInitialMoneyString = customerInitialMoney.getText().toString();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(MainActivity.SERVER_IP_ADDRESS)
                .appendPath("snackstime")
                .appendPath("buyerRegistration.jsp")
                .appendQueryParameter("name", nameString)
                .appendQueryParameter("email",customerIDString)
                .appendQueryParameter("password",customerPassString)
                .appendQueryParameter("amount",customerInitialMoneyString);
        String myUrl = builder.build().toString();
        myUrl = myUrl.replace("%3A",":");
        serverResponse="";
        Log.d("url:-", myUrl);
        AddMember addMember = new AddMember();
        addMember.execute(myUrl);
    }
    public class AddMember extends AsyncTask<String, Void, Boolean> {
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
            serverResponse = serverResponse.replace(" ","");
            int check = Integer.parseInt(serverResponse);
            if (check == 1) {
                Toast.makeText(getApplicationContext(), "Customer Added Successfully", Toast.LENGTH_LONG).show();
                Intent sellerHome = new Intent(SellerAddMember.this,SellerHome.class);
                startActivity(sellerHome);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Something went wrong, Try again later", Toast.LENGTH_LONG).show();
            }
            Log.d("CustomerRegistartn:// ", "onPostExecution ");
            //setContentView(R.layout.technician_registration_successful);
        }
    }
    @Override
    public void onBackPressed(){
        Intent sellerHome = new Intent(SellerAddMember.this,SellerHome.class);
        startActivity(sellerHome);
        finish();
        Log.d("CDA", "onBackPressed Called");
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
