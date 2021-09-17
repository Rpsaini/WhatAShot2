package com.web.whatashot;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.app.dialogsnpickers.AlertDialogs;
import com.app.dialogsnpickers.DialogCallBacks;
import com.web.whatashot.fragments.FundFragment;
import com.web.whatashot.fragments.HomeFragment;
import com.web.whatashot.fragments.MainFundFragment;
import com.web.whatashot.fragments.QuickBuyFragment;
import com.web.whatashot.orderpackage.MyOrderFragment;
import com.web.whatashot.utilpackage.UtilClass;

public class MainActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateObj();
        getSupportActionBar().hide();
        MarketFragment();
        bottomNavigation();
        init();
        savePreferences.savePreferencesData(MainActivity.this,"true", UtilClass.isLogin);
      }

    private void init()
    {
        findViewById(R.id.img_somoreoptions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showMoreOptions(v);
            }
        });
    }

    private void MarketFragment()
    {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        replaceMainFragment(homeFragment,"home");
    }

    private void orderFragment()
    {
        MyOrderFragment myOrderFrg = new MyOrderFragment();
        Bundle args = new Bundle();
        args.putString(DefaultConstants.CurrencyName,"");
        myOrderFrg.setArguments(args);
        replaceMainFragment(myOrderFrg,"order");
    }

    private void fundFragment()
    {
        MainFundFragment fundFragment = new MainFundFragment();
        Bundle args = new Bundle();
        fundFragment.setArguments(args);
        replaceMainFragment(fundFragment,"fundfrg");
    }

    private void loadQuickBuyFragment()
    {
        QuickBuyFragment fundFragment = new QuickBuyFragment();
        Bundle args = new Bundle();
        fundFragment.setArguments(args);
        replaceMainFragment(fundFragment,"quick");
    }


    private void replaceMainFragment(Fragment upcoming, String tag) {
       FragmentTransaction ft_main = getSupportFragmentManager().beginTransaction();
        ft_main.replace(R.id.fragment_container, upcoming);
        //ft_main.addToBackStack(tag);
        ft_main.commit();
    }


    private void bottomNavigation()
    {
       final TextView txt_market =findViewById(R.id.txt_market);
       final TextView txt_order =findViewById(R.id.text_order);
       final TextView txt_fund =findViewById(R.id.txt_fund);
       final TextView txt_quicknuy =findViewById(R.id.txt_quicknuy);
       if(txt_market!=null)
        {
           txt_market.setTextColor(getResources().getColor(R.color.black));
           txt_order.setTextColor(getResources().getColor(R.color.grey_dark));
           txt_fund.setTextColor(getResources().getColor(R.color.grey_dark));
            txt_quicknuy.setTextColor(getResources().getColor(R.color.grey_dark));

           txt_market.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   txt_market.setTextColor(getResources().getColor(R.color.black));
                   txt_order.setTextColor(getResources().getColor(R.color.grey_dark));
                   txt_fund.setTextColor(getResources().getColor(R.color.grey_dark));
                   txt_quicknuy.setTextColor(getResources().getColor(R.color.grey_dark));
                   MarketFragment();
               }
           });

           txt_order.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   txt_market.setTextColor(getResources().getColor(R.color.grey_dark));
                   txt_order.setTextColor(getResources().getColor(R.color.black));
                   txt_fund.setTextColor(getResources().getColor(R.color.grey_dark));
                   txt_quicknuy.setTextColor(getResources().getColor(R.color.grey_dark));
                   orderFragment();
               }
           });

           txt_fund.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   txt_market.setTextColor(getResources().getColor(R.color.grey_dark));
                   txt_order.setTextColor(getResources().getColor(R.color.grey_dark));
                   txt_fund.setTextColor(getResources().getColor(R.color.black));
                   txt_quicknuy.setTextColor(getResources().getColor(R.color.grey_dark));
                   fundFragment();
               }
           });

            txt_quicknuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txt_market.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_order.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_fund.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_quicknuy.setTextColor(getResources().getColor(R.color.black));
                    loadQuickBuyFragment();
                }
            });

       }

    }
    private void showMoreOptions(View imageView)
       {
        PopupMenu popup = new PopupMenu(MainActivity.this, imageView);
        popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {


                if(item.getItemId()==R.id.menu_account)
                {
                    Intent intent=new Intent(MainActivity.this,AccountScreen.class);
                    startActivity(intent);
                }


                if(item.getItemId()==R.id.menu_about)
                {
                 openExternalUrls(getApiUrl()+"contactus");
                }
               else if(item.getItemId()==R.id.menu_Privacy)
                {
                       openExternalUrls(getApiUrl()+"privacy-policy");
                }
                else if(item.getItemId()==R.id.menu_logout)
                {
                    AlertDialogs alertDialogs=new AlertDialogs();
                    alertDialogs.alertDialog(MainActivity.this, "Logout", "Would you like to logout ?", "Yes", "No", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {

                            if(buttonPressed.equalsIgnoreCase("Yes"))
                            {
                                logout();
                            }
                        }
                    });
                }

                return true;
            }
        });

        popup.show(); //showing popup menu

    }



    int exitCount=1;
    @Override
    public void onBackPressed() {
        if(exitCount>=2)
        {
            finishAffinity();
            super.onBackPressed();
        }
        else
        {
            Toast.makeText(MainActivity.this,"Tap again to exit "+getResources().getString(R.string.app_name)+" app",Toast.LENGTH_LONG).show();
            exitCount++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exitCount=1;
                }
            },3000);

        }

    }




}