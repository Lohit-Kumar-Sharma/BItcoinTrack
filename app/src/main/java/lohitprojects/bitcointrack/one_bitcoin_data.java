package lohitprojects.bitcointrack;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class one_bitcoin_data extends AppCompatActivity {
//https://api.coindesk.com/v1/bpi/currentprice/AUD.json
    private InterstitialAd mInterstitialAd;
    private String Url_Current_rate_usd = "https://api.coindesk.com/v1/bpi/currentprice/";
    private String Url2 = "";
    private String Url3 = ".json";
    AdView adView, adView2;
    private TextView display_usd, updated_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_bitcoin_data);
        adView = (AdView) findViewById(R.id.adView);
        display_usd = findViewById(R.id.display_result);
        updated_time = findViewById(R.id.latestHashtext);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        AdRequest adRequest2 = new AdRequest.Builder()
                .build();

      ///  adView.loadAd(adRequest);
        adView.loadAd(adRequest2);


        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
        Bundle bundle = getIntent().getExtras();
        Url2 = bundle.getString("key");

        loaddata();
        }


    private void loaddata() {
        String full_url = Url_Current_rate_usd+Url2+Url3;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                full_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject Ob = response.getJSONObject("bpi");
                            JSONObject ob1 = Ob.getJSONObject(Url2);
                            String data = ob1.getString("rate");
                            String descrip = ob1.getString("description");
                            display_usd.setText(data+" "+Url2);
                           // description.setText(descrip);
                            JSONObject object = response.getJSONObject("time");
                            String time = object.getString("updated");
                            updated_time.setText("Updated @ "+time);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(one_bitcoin_data.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        }
        );
        MySingelton.getInstance(one_bitcoin_data.this).addToRequestQue(jsonObjectRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_one_bitcoin_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(one_bitcoin_data.this,bitcoin.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.adinterstitial2));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
//startActivity(new Intent(one_bitcoin_data.this,NavigationActivity.class));
            }

            @Override
            public void onAdClosed() {
                startActivity(new Intent(one_bitcoin_data.this,NavigationActivity.class));
                }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }

}
