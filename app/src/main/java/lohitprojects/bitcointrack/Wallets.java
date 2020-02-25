package lohitprojects.bitcointrack;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Wallets extends AppCompatActivity implements View.OnClickListener {
    private InterstitialAd mInterstitialAd;
    private TextView unocoin, localbitcoins, zebpay, coinsecure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallets);
        unocoin = findViewById(R.id.unocoin);
        localbitcoins = findViewById(R.id.localbitcoins);
        zebpay = findViewById(R.id.zebpay);
        coinsecure = findViewById(R.id.coinsecure);

        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
               .build();
        adView.loadAd(adRequest);

        // Create the next level button, which tries to show an interstitial when clicked.
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
        unocoin.setOnClickListener(this);
        localbitcoins.setOnClickListener(this);
        zebpay.setOnClickListener(this);
        coinsecure.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wallets, menu);
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
       //  startActivity(new Intent(Wallets.this,NavigationActivity.class));
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                startActivity(new Intent(Wallets.this,NavigationActivity.class));
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
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onClick(View view) {
        if(view == unocoin)
        {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.unocoin.com/"));
            startActivity(browserIntent);

        }
        if(view == localbitcoins)
        {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://localbitcoins.com/"));
            startActivity(browserIntent);

        }
        if(view == zebpay)
        {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.zebpay.com/"));
            startActivity(browserIntent);

        }
        if(view == coinsecure)
        {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://coinsecure.in/"));
            startActivity(browserIntent);

        }
    }
}
