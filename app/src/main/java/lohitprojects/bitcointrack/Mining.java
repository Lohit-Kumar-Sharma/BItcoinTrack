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

public class Mining extends AppCompatActivity implements View.OnClickListener {
  private InterstitialAd mInterstitialAd;
  private TextView totalBtc, lateddtBlck,latestHash, blockReward, avgtime, difficulty, avgTrans, hashtowin, eta, twentyhtranscount;
  private TextView resulttotalBtc, difficultyresult, avgtymbtwblck, avgtxnperblck, latesthash, reward, height, resulthashtowin, resulteta, resulttwentyhtranscount;
  private String totalbtcUrl = "https://blockchain.info/q/totalbc";
  private String difficultyUrl = "https://blockchain.info/q/getdifficulty";
  private String intervalurl = "https://blockchain.info/q/interval";
  private String transtotUrl = "https://blockchain.info/q/avgtxnumber";
  private String rewardUrl = "https://blockchain.info/q/bcperblock";
  private String latestBlockUrl = "https://blockchain.info/q/getblockcount";
  private String hashtowinUrl = "https://blockchain.info/q/hashestowin";
  private String etaUrl = "https://blockchain.info/q/eta";
  private String twentyfourUrl = "https://blockchain.info/q/24hrtransactioncount";

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mining);
        totalBtc = findViewById(R.id.totalBtc);
        lateddtBlck = findViewById(R.id.lateddtBlck);
        latestHash = findViewById(R.id.latestHash);
        blockReward = findViewById(R.id.blockReward);
        avgtime = findViewById(R.id.avgtime);
        difficulty = findViewById(R.id.difficulty);
        avgTrans = findViewById(R.id.avgTrans);
      hashtowin = findViewById(R.id.hashtowin);
      eta = findViewById(R.id.eta);
      twentyhtranscount = findViewById(R.id.twentyhtranscount);

      resulttotalBtc = findViewById(R.id.resulttotalBtc);
      difficultyresult = findViewById(R.id.difficultyresult);
      avgtymbtwblck = findViewById(R.id.avgtymbtwblck);
      avgtxnperblck = findViewById(R.id.avgtxnperblck);
      latesthash = findViewById(R.id.latesthash);
      reward = findViewById(R.id.reward);
      height = findViewById(R.id.height);
      resulthashtowin = findViewById(R.id.resulthashtowin);
      resulteta = findViewById(R.id.resulteta);
      resulttwentyhtranscount = findViewById(R.id.resulttwentyhtranscount);
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);

        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();

        totalBtc.setOnClickListener(this);
        lateddtBlck.setOnClickListener(this);
        latestHash.setOnClickListener(this);
        blockReward.setOnClickListener(this);
        avgtime.setOnClickListener(this);
        difficulty.setOnClickListener(this);
        avgTrans.setOnClickListener(this);
      hashtowin.setOnClickListener(this);
      eta.setOnClickListener(this);
      twentyhtranscount.setOnClickListener(this);

      latesthash.setOnClickListener(this);
         showTotalBtcData();
         showDifficulty();
         showavgtymbtwblck();
      showavgtxnperblck();
      showRewards();
      showHeight();
      showhashestowin();
      showeta();
      showtwentyfourhourstrans();
  }

    private void showtwentyfourhourstrans() {
        StringRequest postRequest = new StringRequest(Request.Method.GET, twentyfourUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        String str =  response.toString();
                        resulttwentyhtranscount.setText(str);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );

        MySingelton.getInstance(Mining.this).addToRequestQue(postRequest);

    }

    private void showeta() {
        StringRequest postRequest = new StringRequest(Request.Method.GET, etaUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        String str =  response.toString();
                        resulteta.setText(str);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );

        MySingelton.getInstance(Mining.this).addToRequestQue(postRequest);

    }

    private void showhashestowin() {
        StringRequest postRequest = new StringRequest(Request.Method.GET, hashtowinUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        String str =  response.toString();
                        resulthashtowin.setText(str);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );

        MySingelton.getInstance(Mining.this).addToRequestQue(postRequest);


    }

    private void showHeight() {

        StringRequest postRequest = new StringRequest(Request.Method.GET, latestBlockUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        String str =  response.toString();
                        height.setText(str);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );

        MySingelton.getInstance(Mining.this).addToRequestQue(postRequest);



    }

    private void showRewards() {

        StringRequest postRequest = new StringRequest(Request.Method.GET,rewardUrl ,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        String str =  response.toString();
                        double tot = Double.parseDouble(str);
                        tot = tot/100000000;
                        String str2 = String.valueOf(tot);
                        reward.setText(str2+" BTC");
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                                  }
                }
        );

        MySingelton.getInstance(Mining.this).addToRequestQue(postRequest);




    }

    private void showavgtxnperblck() {


        StringRequest postRequest = new StringRequest(Request.Method.GET,transtotUrl ,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        String str =  response.toString();
                        avgtxnperblck.setText(str);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        MySingelton.getInstance(Mining.this).addToRequestQue(postRequest);




    }

    private void showavgtymbtwblck() {

        StringRequest postRequest = new StringRequest(Request.Method.GET, intervalurl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        String str =  response.toString();
                        avgtymbtwblck.setText(str);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        MySingelton.getInstance(Mining.this).addToRequestQue(postRequest);






    }

    private void showDifficulty() {
        StringRequest postRequest = new StringRequest(Request.Method.GET, difficultyUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        String str =  response.toString();
                        difficultyresult.setText(str);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        MySingelton.getInstance(Mining.this).addToRequestQue(postRequest);




    }

    private void showTotalBtcData() {

        StringRequest postRequest = new StringRequest(Request.Method.GET, totalbtcUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        String str =  response.toString();
                        resulttotalBtc.setText(fetch(str)+" / 21000000");
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        MySingelton.getInstance(Mining.this).addToRequestQue(postRequest);
  }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mining, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(Mining.this,MiningInfo.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.adinterstitial1));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
              //  startActivity(new Intent(Mining.this,NavigationActivity.class));
            }

            @Override
            public void onAdClosed() {
             startActivity(new Intent(Mining.this,NavigationActivity.class));
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
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }


    @Override
    public void onClick(View view) {
        if(view == totalBtc)
        {
            Toast.makeText(Mining.this,"Total Bitcoins in circulation",Toast.LENGTH_SHORT).show();
        }
        if(view == lateddtBlck)
        {
            Toast.makeText(Mining.this,"Current block height in the longest chain",Toast.LENGTH_SHORT).show();
        }
        if(view == latestHash)
        {
            Toast.makeText(Mining.this,"Hash of the latest block",Toast.LENGTH_SHORT).show();
        }
        if(view == blockReward)
        {
            Toast.makeText(Mining.this,"Current block reward in BTC",Toast.LENGTH_SHORT).show();
        }
        if(view == avgtime)
        {
            Toast.makeText(Mining.this,"Average time between blocks in seconds",Toast.LENGTH_SHORT).show();
        }
        if(view == difficulty)
        {
            Toast.makeText(Mining.this,"Current difficulty target as a decimal number",Toast.LENGTH_SHORT).show();
        }
        if(view == avgTrans)
        {
            Toast.makeText(Mining.this,"Average number of transactions per block",Toast.LENGTH_SHORT).show();
        }
        if(view == latesthash)
        {
            startActivity(new Intent(Mining.this, lohitprojects.bitcointrack.latestHash.class));
        }
        if(view == hashtowin)
        {
            Toast.makeText(Mining.this,"Average number of hash attempts needed to solve a block",Toast.LENGTH_SHORT).show();
        }
         if(view == eta)
         {
             Toast.makeText(Mining.this,"estimated time until the next block (in seconds)",Toast.LENGTH_SHORT).show();
         }
          if(view == twentyhtranscount)
          {
              Toast.makeText(Mining.this,"Number of transactions in the past 24 hours",Toast.LENGTH_SHORT).show();

          }

    }
    private String fetch(String str) {
        double tot = Double.parseDouble(str);
        tot = tot/100000000;
        String str2 = String.format ("%.0f",tot);
        return str2;
    }

}
