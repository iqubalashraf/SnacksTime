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
public class SellerHome extends AppCompatActivity{
    String serverResponse = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_home);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }
    public void addMember(View view){
        Intent addMemberActivity = new Intent(SellerHome.this,SellerAddMember.class);
        startActivity(addMemberActivity);
        finish();
    }
    public void addMoney(View view){
        Intent addMoneyActivity = new Intent(SellerHome.this,SellerAddMoney.class);
        startActivity(addMoneyActivity);
        finish();
    }
    public void editMenu(View view){
        setContentView(R.layout.seller_menu);
    }
    public void editMenuBack(View view){
        setContentView(R.layout.seller_home);
    }
    public void addItemToMenu(View view){
        Intent addItem = new Intent(SellerHome.this,SellerAddItem.class);
        startActivity(addItem);
        finish();
    }
    public void removeItemToMenu(View view){
        Intent removeItem = new Intent(SellerHome.this,RemoveItem.class);
        startActivity(removeItem);
        finish();
    }
    public void modifyItemToMenu(View view){
        Intent modifyItem = new Intent(SellerHome.this,ModifyItem.class);
        startActivity(modifyItem);
        finish();
    }
    public void myOrders(View view){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(MainActivity.SERVER_IP_ADDRESS)
                .appendPath("snackstime")
                .appendPath("viewOrder.jsp");
        String myUrl = builder.build().toString();
        myUrl = myUrl.replace("%3A",":");
        ViewOrders viewOrders = new ViewOrders();
        viewOrders.execute(myUrl);
        Log.d("url:-", myUrl);
    }
    public class ViewOrders extends AsyncTask<String, Void, Boolean> {
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
                    serverResponse =str;
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
            if(serverResponse != null){
                Intent allOrders = new Intent(SellerHome.this,SellerViewOrders.class);
                allOrders.putExtra("SERVERRESPONSE",serverResponse);
                startActivity(allOrders);
                finish();
            }
            /*serverResponse = serverResponse.replace(" ","");
            int check = Integer.parseInt(serverResponse);
            if (check == 1) {
                Toast.makeText(getApplicationContext(), "Money Added Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Something went wrong, Try again later", Toast.LENGTH_LONG).show();
            }
            Log.d("CustomerRegistartn:// ", "onPostExecution ");
            //setContentView(R.layout.technician_registration_successful);*/
        }
    }
    public void sendToMainActivity(View view){
        Intent mainActivity = new Intent(SellerHome.this,MainActivity.class);
        startActivity(mainActivity);
        finish();
    }
    @Override
    public void onBackPressed(){
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
