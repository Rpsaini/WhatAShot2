package com.web.whatashot.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.web.whatashot.DefaultConstants;
import com.web.whatashot.MainActivity;
import com.web.whatashot.R;
import com.web.whatashot.adapters.FiatCurrenciesAdapter;
import com.web.whatashot.adapters.FundAdapter;
import com.web.whatashot.adapters.QuickBuyAdapter;
import com.web.whatashot.utilpackage.UtilClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuickBuyFragment extends Fragment {
   private View view;
   private MainActivity mainActivity;
   public QuickBuyFragment() {
   }
    public static QuickBuyFragment newInstance(String param1, String param2) {
        QuickBuyFragment fragment = new QuickBuyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.fragment_quick_buy, container, false);
        mainActivity= (MainActivity) getActivity();
        getQuickCurrency();

        return view;
    }


    private void showQuickBuyCurrency(JSONArray fiatAr)
    {
        RecyclerView fiat_balance_recyclerview=view.findViewById(R.id.quick_buy_coin_recycler);
        RelativeLayout relativeLayout = view.findViewById(R.id.rr_nodata_view);
        if (fiatAr.length() == 0) {
            relativeLayout.setVisibility(View.VISIBLE);
            fiat_balance_recyclerview.setVisibility(View.GONE);
        } else {
            relativeLayout.setVisibility(View.GONE);
            fiat_balance_recyclerview.setVisibility(View.VISIBLE);
        }


        QuickBuyAdapter mAdapter = new QuickBuyAdapter(fiatAr,(MainActivity) getActivity(),this);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        fiat_balance_recyclerview.setLayoutManager(horizontalLayoutManagaer);
        fiat_balance_recyclerview.setItemAnimator(new DefaultItemAnimator());
        fiat_balance_recyclerview.setAdapter(mAdapter);
    }


    private void getQuickCurrency()
    {

        Map<String, String> m=new HashMap<>();
        m.put("currency", "BTC");
        m.put("page", "1");
        m.put("token",mainActivity.savePreferences.reterivePreference(mainActivity, DefaultConstants.token)+"");
        m.put("DeviceToken",mainActivity.getDeviceToken()+"");

        Map<String,String> headerMap=new HashMap<>();
        headerMap.put("X-API-KEY", UtilClass.xApiKey);
        headerMap.put("Rtoken", mainActivity.getNewRToken()+"");


        new ServerHandler().sendToServer(mainActivity, mainActivity.getApiUrl()+"get-balances-call", m, 0,headerMap, 20000, R.layout.progressbar, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {

                    JSONObject obj = new JSONObject(dta);
                    Log.d("Fait",obj+"");
                    if (obj.getBoolean("status")) {
                        try
                        {
                            if(obj.has("token"))
                            {
                                mainActivity.savePreferences.savePreferencesData(mainActivity,obj.getString("token"),DefaultConstants.token);
                                mainActivity.savePreferences.savePreferencesData(mainActivity,obj.getString("r_token"),DefaultConstants.r_token);
                            }

                            JSONArray balances=obj.getJSONArray("balances");
                            JSONArray ctryptoAr=new JSONArray();
                            JSONArray fiatAr=new JSONArray();
                            for(int x=0;x<balances.length();x++)
                            {
                                String type=balances.getJSONObject(x).getString("type");
                                if(type.equalsIgnoreCase("fiat"))
                                {
                                    fiatAr.put(balances.getJSONObject(x));
                                }
                                else
                                {
                                    ctryptoAr.put(balances.getJSONObject(x));
                                }
                            }
                            showQuickBuyCurrency(fiatAr);
                        }
                        catch (Exception e)
                         {
                            e.printStackTrace();
                         }

                    } else {mainActivity.alertDialogs.alertDialog(mainActivity, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                        @Override
                        public void getDialogEvent(String buttonPressed) {
                            mainActivity.unauthorizedAccess(obj);
                        }
                    });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }





    private void showImage(final String url, final ImageView header_img) {

        Glide.with(mainActivity)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(3)))
                .into(header_img);
    }
}