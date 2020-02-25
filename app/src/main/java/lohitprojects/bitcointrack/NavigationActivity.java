package lohitprojects.bitcointrack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
  // url of total bitcoins https://blockchain.info/q/totalbc


    private String UrlTBitcoins = "https://blockchain.info/q/totalbc";

    private ImageButton currentRateUsd, current_rate_currency, yesterday_rate_usd, historical_data, minor_section;
    private TextView textViewTBitcoins;
    protected void onStart() {
        super.onStart();

        ConnectivityManager ConnectionManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true )
        {

        }
        else
        {
            Toast.makeText(NavigationActivity.this, "Switch ON your internet!", Toast.LENGTH_LONG).show();

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        currentRateUsd = findViewById(R.id.current_rate_usd);
        current_rate_currency = findViewById(R.id.current_rate_any_currency);
        yesterday_rate_usd = findViewById(R.id.yesterday_rate_usd);
        historical_data =findViewById(R.id.historical_data_imageview);
        minor_section =findViewById(R.id.minor_section);
        textViewTBitcoins = findViewById(R.id.tBitcoinsTv);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
           .build();
        adView.loadAd(adRequest);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu m = navigationView .getMenu();

        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for applying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    SpannableString s = new SpannableString(subMenuItem.getTitle());
                    s.setSpan(new TypefaceSpan("fonts/asap.ttf"), 0, s.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    subMenuItem.setTitle(s);
                }
            }

        }



        currentRateUsd.setOnClickListener(this);
        current_rate_currency.setOnClickListener(this);
        yesterday_rate_usd.setOnClickListener(this);
        historical_data.setOnClickListener(this);
        minor_section.setOnClickListener(this);

        loadTbitcoins();
       }

    private void loadTbitcoins() {
        StringRequest postRequest = new StringRequest(Request.Method.GET, UrlTBitcoins,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
//                      Fetching total bitcoins in market
                        String str =  response.toString();
                        textViewTBitcoins.setText("Total Bitcoins in Market: "+fetch(str)+" / 21000000 BTC");
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
//                            Log.d("Error.Response", response);

                    }
                }
        );

        MySingelton.getInstance(NavigationActivity.this).addToRequestQue(postRequest);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
        finishAffinity();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
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
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=lohitprojects.bitcointrack&hl=en"));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else
         if (id == R.id.nav_bitcoins) {
               startActivity(new Intent(NavigationActivity.this,bitcoin.class));
        } else if (id == R.id.nav_wallet) {
             startActivity(new Intent(NavigationActivity.this,Wallets.class));

         }else if (id == R.id.nav_mining) {
             startActivity(new Intent(NavigationActivity.this,MiningInfo.class));

         }
         else if (id == R.id.nav_share) {
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
         else if(id== R.id.nav_aboutUs){
         startActivity(new Intent(NavigationActivity.this, aboutUs.class));
         }
//         else if (id == R.id.nav_donate) {
//                 Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.me/Lohit01"));
//                 startActivity(browserIntent);

         else if (id == R.id.nav_rate){
             Intent intent = new Intent(Intent.ACTION_VIEW);
             intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=lohitprojects.bitcointrack&hl=en"));
             startActivity(intent);
         }
         else if (id == R.id.more_apps)
         {
             Intent intent = new Intent(Intent.ACTION_VIEW);
             intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Lohit+Kumar"));
             startActivity(intent);

         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == currentRateUsd)
        {
            Intent intent = new Intent(NavigationActivity.this,one_bitcoin_data.class);
            intent.putExtra("key","USD");
            startActivity(intent);
        }
        if(view == current_rate_currency)
        {
            startActivity(new Intent(NavigationActivity.this,choose_currency.class));
        }
        if(view == yesterday_rate_usd)
        {
            startActivity(new Intent(NavigationActivity.this,Yesterdays_rate.class));

        }
        if(view == historical_data)
        {
            startActivity(new Intent(NavigationActivity.this, HistoricalData.class));
        }
        if(view == minor_section)
        {
            startActivity(new Intent(NavigationActivity.this, Mining.class));
        }
    }

    private String fetch(String str) {
        double tot = Double.parseDouble(str);
        tot = tot/100000000;
        String str2 = String.format ("%.0f",tot);
        return str2;
    }

}
