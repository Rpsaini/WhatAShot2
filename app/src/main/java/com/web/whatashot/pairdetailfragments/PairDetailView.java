package com.web.whatashot.pairdetailfragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.dialogsnpickers.DialogCallBacks;
import com.app.dialogsnpickers.SimpleDialog;
import com.app.vollycommunicationlib.CallBack;
import com.app.vollycommunicationlib.ServerHandler;
import com.ncorti.slidetoact.SlideToActView;
import com.web.whatashot.BaseActivity;
import com.web.whatashot.DefaultConstants;
import com.web.whatashot.R;
import com.web.whatashot.allorders.AllOrdersFrag;
import com.web.whatashot.communication.SocketHandlers;
import com.web.whatashot.trades.TradesFrag;
import com.web.whatashot.utilpackage.UtilClass;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.socket.emitter.Emitter;

public class PairDetailView extends BaseActivity
  {
    //sell_price ,buy_price
    public String pair_id = "", pair_name = "", volume = "", change = "", buy_price = "", sell_price = "";
    public String mainPair = "", sub_pair = "",joinedPair="";
    public TextView txt_price;
    SocketHandlers socketHandlers;
    private Fragment commonFragent;
    private  TextView orderConfirm;


    private TextView txt_main_pair, txt_sub_pair, txt_change;
    RelativeLayout rr_change;
    ImageView img_arrow;
    private String isLimitOrMarket = "1"; //1 for market 2 for limit

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair_detail_view);
        getSupportActionBar().hide();
        socketHandlers = new SocketHandlers();
        socketHandlers.createConnection();

        initiateObj();
        init();
        bottomNavigation();
        allOrders();
    }

    void init()
        {
        try {
            txt_main_pair = findViewById(R.id.txt_main_pair);
            txt_sub_pair = findViewById(R.id.txt_sub_pair);
            txt_price = findViewById(R.id.txt_price);
            rr_change = findViewById(R.id.rr_change);
            img_arrow = findViewById(R.id.img_arrow);
            txt_change = findViewById(R.id.txt_change);


            String pairData = getIntent().getStringExtra(DefaultConstants.pair_data);
            JSONObject jsonObject = new JSONObject(pairData);

            pair_id = jsonObject.getString("pair_id");
            pair_name = jsonObject.getString("symbol");

            volume = jsonObject.getString("volume");
            change = jsonObject.getString("change");

            initRate(change, jsonObject.getString("buy_price"), jsonObject.getString("sell_price"));
            findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bottomNavigation()
    {
        final TextView txt_allorder = findViewById(R.id.txt_allorder);
        final TextView text_buy_sell = findViewById(R.id.text_buy_sell);
        final TextView txt_chart = findViewById(R.id.txt_chart);
        final TextView txt_openOrders = findViewById(R.id.txt_openOrders);
        final TextView txt_trades = findViewById(R.id.txt_trades);


        if (txt_allorder != null)
        {
            txt_allorder.setTextColor(getResources().getColor(R.color.grey_dark));
            text_buy_sell.setTextColor(getResources().getColor(R.color.grey_dark));
            txt_chart.setTextColor(getResources().getColor(R.color.grey_dark));
            txt_openOrders.setTextColor(getResources().getColor(R.color.white));
            txt_trades.setTextColor(getResources().getColor(R.color.grey_dark));

            txt_allorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txt_allorder.setTextColor(getResources().getColor(R.color.white));
                    text_buy_sell.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_chart.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_openOrders.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_trades.setTextColor(getResources().getColor(R.color.grey_dark));
                    myOrders();
                }
            });

            text_buy_sell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buysell();
                }
            });

            txt_chart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txt_allorder.setTextColor(getResources().getColor(R.color.grey_dark));
                    text_buy_sell.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_chart.setTextColor(getResources().getColor(R.color.white));
                    txt_openOrders.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_trades.setTextColor(getResources().getColor(R.color.grey_dark));
                    chartFrag();
                }
            });

            txt_openOrders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txt_allorder.setTextColor(getResources().getColor(R.color.grey_dark));
                    text_buy_sell.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_chart.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_openOrders.setTextColor(getResources().getColor(R.color.white));
                    txt_trades.setTextColor(getResources().getColor(R.color.grey_dark));
                    allOrders();
                }
            });

            txt_trades.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txt_allorder.setTextColor(getResources().getColor(R.color.grey_dark));
                    text_buy_sell.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_chart.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_openOrders.setTextColor(getResources().getColor(R.color.grey_dark));
                    txt_trades.setTextColor(getResources().getColor(R.color.white));
                    trades();
                }
            });
        }
    }

    private void myOrders() {
        PairOrderFragment allorderfrg = new PairOrderFragment();
        Bundle args = new Bundle();
        args.putString(DefaultConstants.pair_id, pair_id);
        allorderfrg.setArguments(args);
        replaceMainFragment(allorderfrg, "allorders");
        commonFragent = allorderfrg;
    }

    private void buysell() {

        buysellDialog();
        initbuysell();
    }

    private void chartFrag() {
        ChartFragment chartFrg = new ChartFragment();
        Bundle args = new Bundle();
        chartFrg.setArguments(args);
        replaceMainFragment(chartFrg, "allorders");
        commonFragent = chartFrg;
    }

    private void allOrders() {
        AllOrdersFrag frg = new AllOrdersFrag();
        Bundle args = new Bundle();
        frg.setArguments(args);
        replaceMainFragment(frg, "allorders");
        commonFragent = frg;
    }

    private void trades()
    {
        TradesFrag frg = new TradesFrag();
        Bundle args = new Bundle();
        frg.setArguments(args);
        replaceMainFragment(frg, "allorders");
        commonFragent = frg;
    }


    private void replaceMainFragment(Fragment upcoming, String tag) {
        FragmentTransaction ft_main = getSupportFragmentManager().beginTransaction();
        ft_main.replace(R.id.fragment_container, upcoming);
        ft_main.commit();
    }


    public void initRate(String changenew, String currentBuyPrice1, String sellPrice1) {

        buy_price = currentBuyPrice1;
        sell_price = sellPrice1;
        change = changenew;



//        double dChange = Double.parseDouble(change);
        if (changenew.contains("+")) {
            rr_change.setBackgroundResource(R.drawable.green_drawable);
            img_arrow.setRotation(270);
        } else if (changenew.contains("-")) {
            rr_change.setBackgroundResource(R.drawable.round_corner_drawable);
            img_arrow.setRotation(90);
        } else {
            rr_change.setBackgroundResource(R.drawable.green_drawable);
            img_arrow.setRotation(270);
        }
        txt_change.setText(change);


        String ar[] = pair_name.split("\\/");
        txt_sub_pair.setText("/" + ar[1]);
        txt_main_pair.setText(ar[0]);

        mainPair = ar[0];
        sub_pair = ar[1];

        joinedPair=mainPair+"-"+sub_pair;

        if(str_side.equalsIgnoreCase("buy")) {
            txt_price.setText(sell_price);
            txt_price.setTextColor(getResources().getColor(R.color.greencolor));
        } else {
            txt_price.setText(buy_price);
            txt_price.setTextColor(getResources().getColor(R.color.darkRed));
        }


        if(buySellDialog != null && buySellDialog.isShowing()) {
            EditText ed_amount = buySellDialog.findViewById(R.id.ed_amount);
            if (isLimitOrMarket.equalsIgnoreCase("1")) {
                ed_amount.setEnabled(false);
                if (str_side.equalsIgnoreCase("buy")) {
                    ed_amount.setText(sell_price.replace(",", ""));

                } else {
                    ed_amount.setText(buy_price.replace(",", ""));

                }
            } else {
                ed_amount.setEnabled(true);

            }
        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getDataOfPairs();
    }

    private void getDataOfPairs() {

      socketHandlers.socket.off("broadcast_sent_client");
        socketHandlers.socket.on("broadcast_sent_client", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(args[0] + "");
                            if(jsonObject.has("trades_order"))
                            {
                                String pairId = jsonObject.getString("pair_id");
                                if (pair_id.equalsIgnoreCase(pairId)) {
                                    if (commonFragent instanceof TradesFrag)//for trad fragment
                                    {
                                        TradesFrag tradesFrag = (TradesFrag) commonFragent;
                                        tradesFrag.allTradesOrder.clear();
                                        JSONArray da = new JSONArray(jsonObject.getString("trades_order"));
                                        for (int x = 0; x < da.length(); x++) {
                                            JSONArray innerDataAr = da.getJSONArray(x);
                                            String price = innerDataAr.get(0).toString();
                                            String voluem = innerDataAr.get(1).toString();
                                            String date = innerDataAr.get(2).toString();

                                            JSONObject newSocketDataObj = new JSONObject();
                                            newSocketDataObj.put("price", price);
                                            newSocketDataObj.put("modified", date);
                                            newSocketDataObj.put("volume", voluem);

                                            tradesFrag.allTradesOrder.add(newSocketDataObj);

                                        }
                                        tradesFrag.init(tradesFrag.allTradesOrder);
                                    }
                                }
                            }
                            if(commonFragent instanceof AllOrdersFrag) {
                                //for buy/sell orders
                                if(jsonObject.has("buy_data") && jsonObject.has("sell_data"))
                                {
                                    String pairId = jsonObject.getString("pair_id");
                                    if (pair_id.equalsIgnoreCase(pairId)) {
                                        JSONArray buyAr = new JSONArray(jsonObject.getString("buy_data"));
                                        JSONArray sellAr = new JSONArray(jsonObject.getString("sell_data"));



                                        AllOrdersFrag openOrderfrg = (AllOrdersFrag) commonFragent;
                                        openOrderfrg.buyAr.clear();
                                        openOrderfrg.sellAr.clear();

                                        double totalBuyVolume=0,totalsellvolume=0;
                                        for(int x = 0; x < buyAr.length(); x++)
                                        {
                                            JSONObject buyObj = new JSONObject(buyAr.get(x) + "");
                                            JSONObject newbuyObj = new JSONObject();
                                            newbuyObj.put("price", buyObj.getString("0"));
                                            newbuyObj.put("volume", buyObj.getString("1"));
                                            openOrderfrg.buyAr.add(newbuyObj);
                                            totalBuyVolume=Double.parseDouble(buyObj.getString("1").replace(",",""))+totalBuyVolume;

                                        }
                                        for (int x = 0; x < sellAr.length(); x++) {
                                            JSONObject sellObj = new JSONObject(sellAr.get(x) + "");
                                            JSONObject newsellObj = new JSONObject();
                                            newsellObj.put("price", sellObj.getString("2"));
                                            newsellObj.put("volume", sellObj.getString("1"));
                                            openOrderfrg.sellAr.add(newsellObj);
                                            totalsellvolume=Double.parseDouble(sellObj.getString("1").replace(",",""))+totalsellvolume;

                                        }
                                        openOrderfrg.init(openOrderfrg.sellAr, openOrderfrg.buyAr);

                                    }
                                }
                            }
                            //for pairdetail activity
                            if (jsonObject.has("market_data"))
                             {
                                String pairId = jsonObject.getString("pair_id");
                                if (pair_id.equalsIgnoreCase(pairId)) {
                                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("market_data"));

                                    String buy_market = jsonObject1.getString("buy_market");
                                    String sell_market = jsonObject1.getString("sell_market");
                                    change = jsonObject1.getString("change");
                                    initRate(change, buy_market, sell_market);
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        });
    }


    //apply order===

    private String pairid = "";
    public String str_side = "buy";
    private EditText ed_quantity, ed_amount;
    private Dialog buySellDialog;

    private void initbuysell() {
        TextView buytab = buySellDialog.findViewById(R.id.tab_buy);
        TextView selltab = buySellDialog.findViewById(R.id.tab_sell);
       View buy_view= buySellDialog.findViewById(R.id.buy_view);
      View sell_view=  buySellDialog.findViewById(R.id.sell_view);

        ed_quantity = buySellDialog.findViewById(R.id.ed_quantity);
        ed_amount = buySellDialog.findViewById(R.id.ed_amount);
        //final TextView txt_buy = buySellDialog.findViewById(R.id.txt_buy);
        //final TextView txt_sell = buySellDialog.findViewById(R.id.txt_sell);
        final TextView txt_maincurrency = buySellDialog.findViewById(R.id.txt_maincurrency);
        final TextView txt_subcurrency = buySellDialog.findViewById(R.id.txt_subcurrency);

        txt_maincurrency.setText(mainPair);
        txt_subcurrency.setText(sub_pair);

//        txt_buy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                calculateOrderPrice();
//            }
//        });

//        txt_sell.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                calculateOrderPrice();
//            }
//        });

        sliderListener();
        buytab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_side = "buy";
                buy_view.setVisibility(View.VISIBLE);
                sell_view.setVisibility(View.GONE);
                placeorder_slider.resetSlider();
                initRate(change, buy_price, sell_price);

            }
        });

        selltab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy_view.setVisibility(View.INVISIBLE);
                sell_view.setVisibility(View.VISIBLE);
                str_side = "sell";
                placeorder_slider.resetSlider();
                initRate(change, buy_price, sell_price);

            }
        });


    }

    private void calculateOrderPrice()
    {
        if (validationRule.checkEmptyString(ed_amount) == 0) {
            alertDialogs.alertDialog(this, getResources().getString(R.string.Response), "Enter "+pair_name.split("/")[0]+" amount.", getResources().getString(R.string.ok), "", new DialogCallBacks() {
                @Override
                public void getDialogEvent(String buttonPressed) {
                    placeorder_slider.resetSlider();
                }
            });
            return;
        }

        if (validationRule.checkEmptyString(ed_quantity) == 0) {
            alertDialogs.alertDialog(this, getResources().getString(R.string.Response), "Enter "+pair_name.split("/")[0]+" Quantity.", getResources().getString(R.string.ok), "", new DialogCallBacks() {
                @Override
                public void getDialogEvent(String buttonPressed) {
                    placeorder_slider.resetSlider();
                }
            });
            return;
        }

        Map<String, String> m = new HashMap<>();
        m.put("isherder", "true");
        if (isLimitOrMarket.equalsIgnoreCase("1")) {
            m.put("order_type", "market");
        } else {
            m.put("order_type", "limit");
        }

        m.put("pair_id", pair_id);
        m.put("side", str_side);
        m.put("amount", ed_quantity.getText().toString());
        m.put("price", ed_amount.getText().toString());
        m.put("market_type", "exchange");
        m.put("Version", getAppVersion());
        m.put("PlatForm", "android");
        m.put("Timestamp", System.currentTimeMillis() + "");


        m.put(DefaultConstants.r_token, getNewRToken() + "");


        calCulateOrder(m);
    }

    private void calCulateOrder(Map map) {

        map.put("token", savePreferences.reterivePreference(this, DefaultConstants.token));
        map.put("DeviceToken", getDeviceToken() + "");
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("X-API-KEY", UtilClass.xApiKey);
        headerMap.put("Rtoken", getNewRToken() + "");


        new ServerHandler().sendToServer(this, getApiUrl() + "calculate-order-price", map, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {

                    JSONObject obj = new JSONObject(dta);
                    if (obj.getBoolean("status")) {
                        try {
                            if(obj.has("token"))
                            {
                                savePreferences.savePreferencesData(PairDetailView.this,obj.getString("token"),DefaultConstants.token);
                                savePreferences.savePreferencesData(PairDetailView.this,obj.getString("r_token"),DefaultConstants.r_token);
                            }
                            shoOrderEstimation(obj, map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        placeorder_slider.resetSlider();
                        alertDialogs.alertDialog(PairDetailView.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {
                                unauthorizedAccess(obj);
                            }
                        });

                    }
                } catch (Exception e) {
                    placeorder_slider.resetSlider();
                    e.printStackTrace();
                }

            }
        });

    }

    Dialog showOrderPlacedConfirmDia;

    private void shoOrderEstimation(JSONObject obj, final Map<String, String> m) {

        try
          {
            showOrderPlacedConfirmDia = new Dialog(this);
            showOrderPlacedConfirmDia.setContentView(R.layout.show_order_place_dialog);


            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = showOrderPlacedConfirmDia.getWindow();
            lp.copyFrom(window.getAttributes());
            showOrderPlacedConfirmDia.setCancelable(true);
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
            showOrderPlacedConfirmDia.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            showOrderPlacedConfirmDia.show();

            TextView orderTotalamount = showOrderPlacedConfirmDia.findViewById(R.id.orderTotalamount);
            TextView orderType = showOrderPlacedConfirmDia.findViewById(R.id.orderType);
            TextView orderPrice = showOrderPlacedConfirmDia.findViewById(R.id.orderPrice);
            TextView orderVolume = showOrderPlacedConfirmDia.findViewById(R.id.orderVolume);
            TextView orderSide = showOrderPlacedConfirmDia.findViewById(R.id.orderSide);


             orderConfirm = showOrderPlacedConfirmDia.findViewById(R.id.orderConfirm);
            TextView orderCancel = showOrderPlacedConfirmDia.findViewById(R.id.orderCancel);


            orderTotalamount.setText(obj.getString("total_price"));

            orderPrice.setText(obj.getString("price"));
            orderVolume.setText(obj.getString("volume"));
            orderSide.setText(obj.getString("side"));

            if (isLimitOrMarket.equalsIgnoreCase("1")) {
                orderType.setText("Market");
            } else {
                orderType.setText("Limit");
            }
            orderConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderConfirm.setEnabled(false);
                    placeBuySellOrder(m);
                }
            });

            orderCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeorder_slider.resetSlider();
                    showOrderPlacedConfirmDia.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void placeBuySellOrder(Map<String, String> m) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("X-API-KEY", UtilClass.xApiKey);
        headerMap.put("Rtoken", getNewRToken() + "");

        new ServerHandler().sendToServer(this, getApiUrl() + "place-order", m, 0, headerMap, 20000, R.layout.progressbar, new CallBack() {
            @Override
            public void getRespone(String dta, ArrayList<Object> respons) {
                try {
                    JSONObject obj = new JSONObject(dta);
                    if (obj.getBoolean("status"))
                      {
                        sendBroadCastWhaenOrderPlaced(m.get("pair_id"));
                        if(obj.has("token"))
                        {
                            savePreferences.savePreferencesData(PairDetailView.this,obj.getString("token"),DefaultConstants.token);
                            savePreferences.savePreferencesData(PairDetailView.this,obj.getString("r_token"),DefaultConstants.r_token);

                        }
                        alertDialogs.alertDialog(PairDetailView.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {
                                showOrderPlacedConfirmDia.dismiss();
                                downSourceDestinationView(ll_buysell, buySellDialog);

                            }
                        });

                    } else {
                        orderConfirm.setEnabled(true);
                        alertDialogs.alertDialog(PairDetailView.this, getResources().getString(R.string.Response), obj.getString("msg"), getResources().getString(R.string.ok), "", new DialogCallBacks() {
                            @Override
                            public void getDialogEvent(String buttonPressed) {
                                unauthorizedAccess(obj);
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void sendBroadCastWhaenOrderPlaced(String pairId) {
        try
        {
            JSONObject map = new JSONObject();
            map.put("pair_id", pairId);
            map.put("type", "app");

            System.out.println("Pair id==="+map);
            socketHandlers.socket.emit("broadcast_sent_server", map + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    LinearLayout ll_buysell;

    private void buysellDialog() {
        try {
            str_side = "buy";
            SimpleDialog simpleDialog = new SimpleDialog();
            buySellDialog = simpleDialog.simpleDailog(this, R.layout.buy_slle_dialog, new ColorDrawable(getResources().getColor(R.color.translucent_black)), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
            final ImageView closeDialogBtn = buySellDialog.findViewById(R.id.closeBtn);
            ll_buysell = buySellDialog.findViewById(R.id.ll_buysell);

            animateUp(ll_buysell);
            closeDialogBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downSourceDestinationView(ll_buysell, buySellDialog);
                }
            });


            Spinner spiinerOrderType = buySellDialog.findViewById(R.id.spiinerOrderType);
            ArrayList<String> typeAr = new ArrayList<>();
            typeAr.add("Market");
            typeAr.add("Limit");

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item,
                            typeAr); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            spiinerOrderType.setAdapter(spinnerArrayAdapter);

            spiinerOrderType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        isLimitOrMarket = "1";
                    } else {
                        isLimitOrMarket = "2";
                    }
                    initRate(change, buy_price, sell_price);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            initRate(change, buy_price, sell_price);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        socketHandlers.socket.off("broadcast_sent_client");

    }

      SlideToActView  placeorder_slider;
    private void sliderListener()
    {
          placeorder_slider=buySellDialog.findViewById(R.id.placeorder_slider);
        placeorder_slider.setOnSlideToActAnimationEventListener(new SlideToActView.OnSlideToActAnimationEventListener() {
            @Override
            public void onSlideCompleteAnimationStarted(@NotNull SlideToActView slideToActView, float v) {

            }

            @Override
            public void onSlideCompleteAnimationEnded(@NotNull SlideToActView slideToActView) {
                calculateOrderPrice();
            }

            @Override
            public void onSlideResetAnimationStarted(@NotNull SlideToActView slideToActView) {

            }

            @Override
            public void onSlideResetAnimationEnded(@NotNull SlideToActView slideToActView) {

            }
        });


    }




  }

