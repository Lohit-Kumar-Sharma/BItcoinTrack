package lohitprojects.bitcointrack;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class choose_currency extends AppCompatActivity {
    // Remove the below line after defining your own ad unit ID.
    String URL = "https://api.coindesk.com/v1/bpi/supported-currencies.json";
    private ListView listView;
    ArrayList arrayList;
    private ArrayAdapter arrayAdapter;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_currency);
        listView= findViewById(R.id.listviewbt);


        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
.build();
        adView.loadAd(adRequest);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"You Tapped On "+arrayList.get(i),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(choose_currency.this,one_bitcoin_data.class);
                String data = (String) arrayList.get(i);
                intent.putExtra("key",data);
                startActivity(intent);


            }
        });
       show_currency();
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
    }

    private void show_currency() {
        Toast.makeText(choose_currency.this,"Fetching Currencies present all Over the world, Kindly Wait",Toast.LENGTH_LONG).show();

        arrayList = new ArrayList();


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject ob = response.getJSONObject(i);
                                String cur = ob.getString("currency");
                                arrayList.add(cur);

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred

                        Toast.makeText(choose_currency.this,"Check Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                }
        );

        MySingelton.getInstance(choose_currency.this).addToRequestQue(jsonArrayRequest);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(arrayAdapter);
        Toast.makeText(choose_currency.this,"Select your Currency",Toast.LENGTH_LONG).show();





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_currency, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(choose_currency.this,bitcoin.class));
        }

        return super.onOptionsItemSelected(item);
    }
    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            //    startActivity(new Intent(choose_currency.this,NavigationActivity.class));

            }

            @Override
            public void onAdClosed() {
                startActivity(new Intent(choose_currency.this,NavigationActivity.class));

            }
        });
        return interstitialAd;
    }
    public void onBackPressed() {
        super.onBackPressed();
//        showInterstitial();
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {


        }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        AdRequest adRequest = new AdRequest.Builder()
               .build();
        mInterstitialAd.loadAd(adRequest);
    }


}
