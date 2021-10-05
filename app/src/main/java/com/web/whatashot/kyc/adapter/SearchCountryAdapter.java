package com.web.whatashot.kyc.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.web.whatashot.DefaultConstants;
import com.web.whatashot.R;
import com.web.whatashot.kyc.SelectCountryScreen;
import com.web.whatashot.pairdetailfragments.PairDetailView;
import com.web.whatashot.search_currency.SearchCurrencyScreen;

import org.json.JSONObject;

import java.util.ArrayList;


public class SearchCountryAdapter extends RecyclerView.Adapter<SearchCountryAdapter.MyViewHolder> {
    private SelectCountryScreen ira1;
    private ArrayList<JSONObject> moviesList;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_Name;
        ImageView img_countryIcon,selectCountryCB;
        LinearLayout ll_market_order_list_row;


        public MyViewHolder(View view)
        {
            super(view);
            img_countryIcon = view.findViewById(R.id.img_countryIcon);
            txt_Name = view.findViewById(R.id.txt_Name);

            ll_market_order_list_row = view.findViewById(R.id.ll_market_order_list_row);
            selectCountryCB = view.findViewById(R.id.selectCountryCB);

        }
    }


    public SearchCountryAdapter(SelectCountryScreen showFiatCurrencyDepositWithdraw, ArrayList<JSONObject> dataAr) {
        this.moviesList = dataAr;
        this.ira1 = showFiatCurrencyDepositWithdraw;

    }
    public SearchCountryAdapter(SelectCountryScreen showFiatCurrencyDepositWithdraw) {
        this.ira1 = showFiatCurrencyDepositWithdraw;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_country_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       try {
           JSONObject object = moviesList.get(position);
           int pair_id = Integer.parseInt(object.getString("pair_id"));
           String change = object.getString("change").replace("%", "");


/*
           String ar[] = object.getString("symbol").split("\\/");
           holder.txt_subpair.setText(ar[0]);
           holder.txt_mainpar.setText("/" + ar[1]);
           holder.txt_price.setText(object.getString("price"));

           holder.txt_volume.setText(object.getString("volume"));
           holder.txt_change.setText(change);


           if (change.contains("+")) {
               holder.txt_price.setTextColor(ira1.getResources().getColor(R.color.greencolor));
               holder.rr_change.setBackgroundResource(R.drawable.green_drawable);
               holder.img_arrow.setRotation(270);

           } else if (change.contains("-")) {

               holder.txt_price.setTextColor(ira1.getResources().getColor(R.color.darkRed));
               holder.rr_change.setBackgroundResource(R.drawable.round_corner_drawable);
               holder.img_arrow.setRotation(90);

           } else {

               holder.txt_price.setTextColor(ira1.getResources().getColor(R.color.greencolor));
               holder.rr_change.setBackgroundResource(R.drawable.green_drawable);
               holder.img_arrow.setRotation(270);

           }

           holder.ll_market_order_list_row.setTag(object + "");
           holder.ll_market_order_list_row.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   try {
                       Intent intent = new Intent(ira1, PairDetailView.class);
                       intent.putExtra(DefaultConstants.pair_data, v.getTag() + "");
                       ira1.startActivity(intent);
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           });


           showImage(object.getString("icon"), holder.img_currencyicon);
           holder.txt_fullpar.setText(object.getString("name"));*/

       }
       catch (Exception e)
       {
           e.printStackTrace();
       }


    }

    @Override
    public int getItemCount()
    {
       // return moviesList.size();
        return 10;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void showImage(final String url, final ImageView header_img) {
        ira1.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(ira1)
                        .load(url)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(3)))
                        .into(header_img);
            }
        });
    }

}