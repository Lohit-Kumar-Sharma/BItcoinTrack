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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class HistoricalData extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    String URL1 =  "https://api.coindesk.com/v1/bpi/historical/close.json?start=2010-07-17&end=";
//    String URL2 ="2010-07-17";
//    String URL3 ="&end=";
    String URL4 ="";
    ListView listView;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>();
    ArrayAdapter adapter;
    Iterator<String> keysIterator;
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_data);

        // fetching today's date

        String currentDate = DateFormat.getDateInstance().format(new Date());
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);


        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");

        Date date = null;
        try {
            date = outputFormat.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = inputFormat.format(date);
        URL4 = outputDateStr;



        listView = findViewById(R.id.listViewHis);
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
//        Bundle bundle = getIntent().getExtras();
//       URL2= bundle.getString("key");
       loadData();
    }

    private void loadData() {
        String full_url = URL1+URL4;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                full_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject Ob = response.getJSONObject("bpi");
                            HashMap<String, String> map;
                            keysIterator =Ob.keys();
                            for(int i =0; keysIterator.hasNext();i++){
//                            while (keysIterator.hasNext())
//                            {
                                map = new HashMap<String, String>();
                                String keyStr = (String)keysIterator.next();
                                String valueStr = Ob.getString(keyStr);

                                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");

                                Date date = null;
                                try {
                                    date = inputFormat.parse(keyStr);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                String outputDateStr = outputFormat.format(date);

                                map.put("Rate ", valueStr+" USD");
                                map.put("Date ",outputDateStr);
                                arrayList.add(map);

                            }
                            Collections.reverse(arrayList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                }
        }
        );
        MySingelton.getInstance(HistoricalData.this).addToRequestQue(jsonObjectRequest);
        ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<HashMap<String,String>>();


        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);


        listView.setAdapter(adapter);

        Toast.makeText(HistoricalData.this,"Showing data Till Yesterday",Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//       showInterstitial();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historical_data, menu);
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
        interstitialAd.setAdUnitId(getString(R.string.adinterstitial1));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            //  startActivity(new Intent(HistoricalData.this,NavigationActivity.class));
            }

            @Override
            public void onAdClosed() {
                startActivity(new Intent(HistoricalData.this,NavigationActivity.class));
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
               .build();
        mInterstitialAd.loadAd(adRequest);
    }

}
