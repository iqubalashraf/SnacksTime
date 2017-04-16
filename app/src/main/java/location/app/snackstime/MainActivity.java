package location.app.snackstime;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import buy.CustomerLogin;
import sell.SellerLogin;

public class MainActivity extends AppCompatActivity {
    public static final String SERVER_IP_ADDRESS = "192.168.43.200:8080";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void SellerLoginPage(View view){
        try{
            //Log.d("MainActivity: ","SellerLogin");
            Intent sellerLogin = new Intent(MainActivity.this, SellerLogin.class);
            //Log.d("MainActivity: ","SellerLogin2");
            startActivity(sellerLogin);
            //Log.d("MainActivity: ", "SellerLogin3");
            finish();
        }catch (Exception e){
            //Log.d("MainActivity: ",e.getMessage());
        }

    }
    public void CustomerLoginPage(View view){
        Intent customerLogin = new Intent(MainActivity.this, CustomerLogin.class);
        startActivity(customerLogin);
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
