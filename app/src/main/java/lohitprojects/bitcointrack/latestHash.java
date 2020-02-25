package lohitprojects.bitcointrack;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class latestHash extends AppCompatActivity {
   private InterstitialAd mInterstitialAd;
   private String latestHashUrl= "https://blockchain.info/q/latesthash";
   private TextView latestHashtext;
    AdView adView;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_hash);
     latestHashtext = findViewById(R.id.latestHashtext);
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
         adView= (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);

        showlatesthash();
    }

    private void showlatesthash() {

        StringRequest postRequest = new StringRequest(Request.Method.GET,latestHashUrl ,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        String str =  response.toString();
                        latestHashtext.setText(str);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        MySingelton.getInstance(latestHash.this).addToRequestQue(postRequest);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_latest_hash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "https://play.google.com/store/apps/details?id=lohitprojects.bitcointrack&hl=en";
            String shareContent1="\n✦ Bitcoin Track\n";
            String shareContent2="✦ Everything you need to know about Bitcoin \n✦ Live bitcoin rate Tracking in any Currency | Wallets | Mining | Historical Records";
            String shareContent3 = "\n✦ ";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareContent1+shareContent2+shareContent3+shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

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
              //  startActivity(new Intent(latestHash.this,Mining.class));
            }

            @Override
            public void onAdClosed() {
                startActivity(new Intent(latestHash.this,Mining.class));
            }
        });
        return interstitialAd;
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
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }

}
