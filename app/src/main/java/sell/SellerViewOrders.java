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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import location.app.snackstime.MainActivity;
import location.app.snackstime.R;

/**
 * Created by ashrafiqubal on 20/04/16.
 */
public class SellerViewOrders extends AppCompatActivity {
    ListView lv;
    String serverResponse;
    List<String> techDetailsList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_view_order);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Intent get = getIntent();
        String serverString = get.getStringExtra("SERVERRESPONSE");
        Object obj = JSONValue.parse(serverString);
        JSONObject jsonObject = (JSONObject)obj;
        String max = (String)jsonObject.get("MAX");
        int max1 = Integer.parseInt(max);
        Log.d("Line Executed", "ok");
        for(int i=0;i<max1;i++){
            Log.d("Value i",Integer.toString(i));
            String name = (String)jsonObject.get("NAME"+Integer.toString(i));
            Log.d("Name: ",name);
            String quantity = (String)jsonObject.get("COUNT"+Integer.toString(i));
            Log.d("Price: ",quantity);
            String string = "Item: "+name+"     "+"Quantity: "+quantity;
            techDetailsList.add(string);
            Log.d("Array:", techDetailsList.toString());
        }
        lv = (ListView) findViewById(R.id.label);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SellerViewOrders.this, android.R.layout.simple_list_item_1,techDetailsList);
        lv.setAdapter(arrayAdapter);
        Log.d("PostExecution: ", "");
        Log.d("Tech Booking:// ", "onPostExecution ");
        Log.d("Line Executed", "ok");
    }
    public void resetOrder(View view){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(MainActivity.SERVER_IP_ADDRESS)
                .appendPath("snackstime")
                .appendPath("reset.jsp");
        String myUrl = builder.build().toString();
        myUrl = myUrl.replace("%3A",":");
        AddAmountClass addAmountClass = new AddAmountClass();
        addAmountClass.execute(myUrl);
        Log.d("url:-", myUrl);
    }
    public class AddAmountClass extends AsyncTask<String, Void, Boolean> {
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
            Log.d("CustomerRegistration: ", serverResponse);
            if (serverResponse.contains("1")) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                Intent sellerHome = new Intent(SellerViewOrders.this,SellerHome.class);
                startActivity(sellerHome);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Something went wrong, Try again later", Toast.LENGTH_LONG).show();
            }
            Log.d("CustomerRegistartn:// ", "onPostExecution ");
            //setContentView(R.layout.technician_registration_successful);*/
        }
    }
    @Override
    public void onBackPressed(){
        Intent sellerHome = new Intent(SellerViewOrders.this,SellerHome.class);
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
