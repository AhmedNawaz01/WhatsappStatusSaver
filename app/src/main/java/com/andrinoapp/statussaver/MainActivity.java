package com.andrinoapp.statussaver;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SymbolTable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.andrinoapp.statussaver.Adapter.ViewPagerWAAdapter;
import com.andrinoapp.statussaver.Fragments.WAAboutFragment;
import com.andrinoapp.statussaver.Fragments.WAImageFragment;
import com.andrinoapp.statussaver.Fragments.WAVideoFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.util.ArrayList;

import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;
    TabLayout tabLayout;
     SmoothBottomBar bottomBar;
    ViewPagerWAAdapter adapter;
    ArrayList<Fragment> fragmentArrayList ;
    TextView nav_share , nav_rate , nav_suggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager_wa);

        nav_share = findViewById(R.id.nav_share_action);
        nav_rate = findViewById(R.id.nav_rate_it);
        nav_suggestions = findViewById(R.id.nav_suggestions);


        nav_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareApp();
            }
        });
        nav_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RateApp();
            }
        });
        nav_suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuggestionApp();
            }
        });

       bottomBar = (SmoothBottomBar) findViewById(R.id.bottomBar);
        /////////////////////////////////////////////////////////////
       AppRate.with(this)
                .setInstallDays(0) // default 10, 0 means install day.
                .setLaunchTimes(5) // default 10
                .setRemindInterval(10) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(int which) {
                        Log.d(MainActivity.class.getName(), Integer.toString(which));
                    }
                })
                .monitor();

        // Show a dialog if meets conditions
        AppRate.showRateDialogIfMeetsConditions(this);
///////////////////////////////////////////////////////////////////////////

        try {
            if (!isMyServiceRunning(Class.forName("com.whatsappstatus.saver.service.NotificationService"))) {
                try {
                    startService(new Intent(this, Class.forName("com.whatsappstatus.saver.service.NotificationService")));
                } catch (Throwable e) {
                    throw new NoClassDefFoundError(e.getMessage());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        stash();

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.burger);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


            Fragment imagefragment = new WAImageFragment();
            Fragment videofragment = new WAVideoFragment();
            Fragment aboutfragment =  new WAAboutFragment();

            fragmentArrayList = new ArrayList<>();
            fragmentArrayList.add(imagefragment);
            fragmentArrayList.add(videofragment);
            fragmentArrayList.add(aboutfragment);

            FragmentManager fm = getSupportFragmentManager();
            adapter = new ViewPagerWAAdapter(fm,fragmentArrayList);

            viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.setItemActiveIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                viewPager.setCurrentItem(i);
                return false;
            }
        });




    }

    /*  private void replaceFragment(Fragment newFragment, String tag) {
          FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
          ft.replace(R.id.viewPager_wa, newFragment, tag)
                  .commit();

      }*/
    private void replaceFragment(Fragment newFragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framelayout, newFragment, tag)
                .commit();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {

            return true;
        }
        if (id == R.id.action_help) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.tut, (ViewGroup) null);
           // final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final Dialog sortDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
            sortDialog.setContentView(inflate);
            sortDialog.show();
            ImageButton positive_btn = (ImageButton) sortDialog.findViewById(R.id.btn_ok);
            positive_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    sortDialog.dismiss();
                }
            });
        }


        return super.onOptionsItemSelected(item);
    }
    private  void RateApp(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "Status Saver")));
    }

    private  void  SuggestionApp(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"xyz_mailreciepent@abc.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "StatusSaver Suggestions");
        i.putExtra(Intent.EXTRA_TEXT   , "body of email");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void ShareApp(){

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
   @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();





       if (id == R.id.nav_share_action) {
          Intent intent = new Intent("android.intent.action.SEND");
           intent.setType("text/plain");
           intent.putExtra("android.intent.extra.SUBJECT", "Share App");
           intent.putExtra("android.intent.extra.TEXT", "");
           startActivity(Intent.createChooser(intent, "Share Via"));
           return true;
          /* try {
               Intent shareIntent = new Intent(Intent.ACTION_SEND);
               shareIntent.setType("text/plain");
               shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
               String shareMessage= "\nLet me recommend you this application\n\n";
               shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
               shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
               startActivity(Intent.createChooser(shareIntent, "choose one"));
           } catch(Exception e) {
               //e.toString();
           }*/
       }
       else if(id == R.id.nav_rate_it)
       {
           //AppRate.with(this).showRateDialog(this);
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "Status Saver")));
       }
       else if(id == R.id.nav_suggestions)
       {
           Intent i = new Intent(Intent.ACTION_SEND);
           i.setType("message/rfc822");
           i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"xyz_mailreciepent@abc.com"});
           i.putExtra(Intent.EXTRA_SUBJECT, "StatusSaver Suggestions");
           i.putExtra(Intent.EXTRA_TEXT   , "body of email");
           try {
               startActivity(Intent.createChooser(i, "Send mail..."));
           } catch (android.content.ActivityNotFoundException ex) {
               Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
           }
       }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void stash() {
        File file = new File(new StringBuffer().append(Environment.getExternalStorageDirectory()).append(File.separator).toString() + "WhatsApp/Media/.Statuses");
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        file = new File(Environment.getExternalStorageDirectory() + File.separator + "WhatsApp Business/Media/.Statuses");
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        file = new File(Environment.getExternalStorageDirectory() + File.separator + "GBWhatsApp/Media/.Statuses");
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        new File(Environment.getExternalStorageDirectory() + File.separator + "StorySaver/").mkdirs();
    }

    private boolean checkInstallation() {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo("com.whatsapp.w4b", PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private boolean isMyServiceRunning(Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }



}
