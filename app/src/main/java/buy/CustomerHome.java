package buy;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import location.app.snackstime.MainActivity;
import location.app.snackstime.R;

/**
 * Created by ashrafiqubal on 20/04/16.
 */
public class CustomerHome extends AppCompatActivity {
    String serverResponse = null,userEmail=null,serverResponse1;
    EditText editText ;
    int maxInt=0;
    int MAXITEM=0;
    TextView tv1,tv2,tv3,tv4,tv5;
    LinearLayout lv1;
    String amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_home);
        Intent intent = getIntent();
        Log.d("Check2", "Line Executed");
        amount = intent.getStringExtra("AMOUNT");
        userEmail = intent.getStringExtra("customerEmail");
        TextView textView = (TextView)findViewById(R.id.userNameAmount);
        textView.setText("Your Money "+amount);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(MainActivity.SERVER_IP_ADDRESS)
                .appendPath("snackstime")
                .appendPath("getItem.jsp");
        String myUrl = builder.build().toString();
        myUrl = myUrl.replace("%3A",":");
        GetItemList getItemList = new GetItemList();
        getItemList.execute(myUrl);
        Log.d("url:-", myUrl);
    }
    public class GetItemList extends AsyncTask<String, Void, Boolean> {
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
            Object obj = JSONValue.parse(serverResponse);
            JSONObject jsonObject = (JSONObject)obj;
            String MAX = (String)jsonObject.get("MAX");
            Log.d("MAX://",MAX);
            maxInt = Integer.parseInt(MAX);
            for (int i =0;i<maxInt;i++){
                if(i==0){
                    lv1 = (LinearLayout)findViewById(R.id.layout1);
                    lv1.setVisibility(View.VISIBLE);
                    tv1 = (TextView)findViewById(R.id.item1);
                    tv2 = (TextView)findViewById(R.id.price1);
                }
                if(i==1){
                    lv1 = (LinearLayout)findViewById(R.id.layout2);
                    lv1.setVisibility(View.VISIBLE);
                    tv1 = (TextView)findViewById(R.id.item2);
                    tv2 = (TextView)findViewById(R.id.price2);
                }
                if(i==2){
                    lv1 = (LinearLayout)findViewById(R.id.layout3);
                    lv1.setVisibility(View.VISIBLE);
                    tv1 = (TextView)findViewById(R.id.item3);
                    tv2 = (TextView)findViewById(R.id.price3);
                }
                if(i==3){
                    lv1 = (LinearLayout)findViewById(R.id.layout4);
                    lv1.setVisibility(View.VISIBLE);
                    tv1 = (TextView)findViewById(R.id.item4);
                    tv2 = (TextView)findViewById(R.id.price4);
                }
                if(i==4){
                    lv1 = (LinearLayout)findViewById(R.id.layout5);
                    lv1.setVisibility(View.VISIBLE);
                    tv1 = (TextView)findViewById(R.id.item5);
                    tv2 = (TextView)findViewById(R.id.price5);
                }
                String itemName = (String)jsonObject.get("NAME"+Integer.toString(i));
                tv1.setText(itemName);
                String itemPrice = (String)jsonObject.get("PRICE"+Integer.toString(i));
                tv2.setText(itemPrice);
            }
        }
    }
    public void placeOrder(View view){
        MAXITEM = 0;
        Object obj = JSONValue.parse(serverResponse);
        JSONObject jsonObject = (JSONObject)obj;
        String item1="0",item2="0",item3="0",item4="0",item5="0",itemName1="",itemName2="",itemName3="",itemName4="",itemName5="";
        for (int i =0;i<maxInt;i++){
            if(i==0){
                editText = (EditText)findViewById(R.id.amount1);
                item1 = editText.getText().toString();
                if(item1.equals(null)){
                    item1="0";
                }
                MAXITEM++;
                itemName1 = (String)jsonObject.get("NAME"+Integer.toString(0));
            }
            if(i==1){
                editText = (EditText)findViewById(R.id.amount2);
                item2 = editText.getText().toString();
                if(item2.equals(null)){
                    item2="0";
                }
                MAXITEM++;
                itemName2 = (String)jsonObject.get("NAME"+Integer.toString(1));
            }
            if(i==2){
                editText = (EditText)findViewById(R.id.amount3);
                item3 = editText.getText().toString();
                if(item3.equals(null)){
                    item3="0";
                }
                MAXITEM++;
                itemName3 = (String)jsonObject.get("NAME"+Integer.toString(2));
            }
            if(i==3){
                editText = (EditText)findViewById(R.id.amount4);
                item4 = editText.getText().toString();
                if(item4.equals(null)){
                    item4 = "0";
                }
                MAXITEM++;
                itemName4 = (String)jsonObject.get("NAME"+Integer.toString(3));
            }
            if(i==4){
                editText = (EditText)findViewById(R.id.amount5);
                item5 = editText.getText().toString();
                if(item5.equals(null)){
                    item5="0";
                }
                MAXITEM++;
                itemName5 = (String)jsonObject.get("NAME"+Integer.toString(4));
            }
        }
        if(MAXITEM==1){
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority(MainActivity.SERVER_IP_ADDRESS)
                    .appendPath("snackstime")
                    .appendPath("order.jsp")
                    .appendQueryParameter("email", userEmail)
                    .appendQueryParameter("limit", "1")
                    .appendQueryParameter("itemname1", itemName1)
                    .appendQueryParameter("quantity1",item1);
            String myUrl = builder.build().toString();
            myUrl = myUrl.replace("%3A",":");
            PlaceOrders placeOrders = new PlaceOrders();
            placeOrders.execute(myUrl);
            Log.d("url:-", myUrl);
        }
        if(MAXITEM==2){
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority(MainActivity.SERVER_IP_ADDRESS)
                    .appendPath("snackstime")
                    .appendPath("order.jsp")
                    .appendQueryParameter("email", userEmail)
                    .appendQueryParameter("limit", "2")
                    .appendQueryParameter("itemname1", itemName1)
                    .appendQueryParameter("quantity1",item1)
                    .appendQueryParameter("itemname2",itemName2)
                    .appendQueryParameter("quantity2",item2);
            String myUrl = builder.build().toString();
            myUrl = myUrl.replace("%3A",":");
            PlaceOrders placeOrders = new PlaceOrders();
            placeOrders.execute(myUrl);
            Log.d("url:-", myUrl);
        }
        if(MAXITEM==3){
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority(MainActivity.SERVER_IP_ADDRESS)
                    .appendPath("snackstime")
                    .appendPath("order.jsp")
                    .appendQueryParameter("limit", "3")
                    .appendQueryParameter("email",userEmail)
                    .appendQueryParameter("itemname1", itemName1)
                    .appendQueryParameter("quantity1", item1)
                    .appendQueryParameter("itemname2",itemName2)
                    .appendQueryParameter("quantity2",item2)
                    .appendQueryParameter("itemname3",itemName3)
                    .appendQueryParameter("quantity3",item3);
            String myUrl = builder.build().toString();
            myUrl = myUrl.replace("%3A",":");
            PlaceOrders placeOrders = new PlaceOrders();
            placeOrders.execute(myUrl);
            Log.d("url:-", myUrl);
        }
        if(MAXITEM==4){
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority(MainActivity.SERVER_IP_ADDRESS)
                    .appendPath("snackstime")
                    .appendPath("order.jsp")
                    .appendQueryParameter("limit", "4")
                    .appendQueryParameter("email",userEmail)
                    .appendQueryParameter("itemname1", itemName1)
                    .appendQueryParameter("quantity1", item1)
                    .appendQueryParameter("itemname2", itemName2)
                    .appendQueryParameter("quantity2", item2)
                    .appendQueryParameter("itemname3",itemName3)
                    .appendQueryParameter("quantity3",item3)
                    .appendQueryParameter("itemname4",itemName4)
                    .appendQueryParameter("quantity4",item4);
            String myUrl = builder.build().toString();
            myUrl = myUrl.replace("%3A",":");
            PlaceOrders placeOrders = new PlaceOrders();
            placeOrders.execute(myUrl);
            Log.d("url:-", myUrl);
        }
        if(MAXITEM==5){
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority(MainActivity.SERVER_IP_ADDRESS)
                    .appendPath("snackstime")
                    .appendPath("order.jsp")
                    .appendQueryParameter("limit", "5")
                    .appendQueryParameter("email",userEmail)
                    .appendQueryParameter("itemname1", itemName1)
                    .appendQueryParameter("quantity1",item1)
                    .appendQueryParameter("itemname2",itemName2)
                    .appendQueryParameter("quantity2",item2)
                    .appendQueryParameter("itemname3",itemName3)
                    .appendQueryParameter("quantity3",item3)
                    .appendQueryParameter("itemname4",itemName4)
                    .appendQueryParameter("quantity4",item4)
                    .appendQueryParameter("itemname5",itemName5)
                    .appendQueryParameter("quantity5",item5);
            String myUrl = builder.build().toString();
            myUrl = myUrl.replace("%3A",":");
            PlaceOrders placeOrders = new PlaceOrders();
            placeOrders.execute(myUrl);
            Log.d("url:-", myUrl);
        }
        MAXITEM=0;
    }
    public class PlaceOrders extends AsyncTask<String, Void, Boolean> {
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
                    serverResponse1 =str;
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
            serverResponse1 = serverResponse1.replace(" ","");
            int check = Integer.parseInt(serverResponse1);
            if (check <0) {
                Toast.makeText(getApplicationContext(), "Insufficient Amount, You need to add "+Integer.toString(check)+"Rs" , Toast.LENGTH_LONG).show();
            } else if(check>0 && check<Integer.parseInt(amount)) {
                Toast.makeText(getApplicationContext(), "Order Placed Successfully", Toast.LENGTH_LONG).show();
                TextView textView = (TextView)findViewById(R.id.userNameAmount);
                textView.setText("Your Money " + Integer.toString(check));
                for (int i =0;i<maxInt;i++){
                    if(i==0){
                        editText = (EditText)findViewById(R.id.amount1);
                        editText.setText("0");
                    }
                    if(i==1){
                        editText = (EditText)findViewById(R.id.amount2);
                        editText.setText("0");
                    }
                    if(i==2){
                        editText = (EditText)findViewById(R.id.amount3);
                        editText.setText("0");
                    }
                    if(i==3){
                        editText = (EditText)findViewById(R.id.amount4);
                        editText.setText("0");
                    }
                    if(i==4){
                        editText = (EditText)findViewById(R.id.amount5);
                        editText.setText("0");
                    }
                }
            }else {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
            Log.d("CustomerRegistartn:// ", "onPostExecution ");
            //setContentView(R.layout.technician_registration_successful);*/
        }
    }
    public void sendToMainActivity(View view){
        Intent mainActivity = new Intent(CustomerHome.this,MainActivity.class);
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
