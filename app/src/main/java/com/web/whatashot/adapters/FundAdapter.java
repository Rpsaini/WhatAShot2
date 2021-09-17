package com.web.whatashot.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.web.whatashot.MainActivity;
import com.web.whatashot.R;
import com.web.whatashot.fragments.FundFragment;
import com.web.whatashot.kyc.BalanceFulledetails;
//import com.web.whatashot.kyc.PersonalDetails;

import org.json.JSONArray;
import org.json.JSONObject;


public class FundAdapter extends RecyclerView.Adapter<FundAdapter.MyViewHolder> {
    private MainActivity ira1;
    private JSONArray moviesList;
    private FundFragment fundFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_currency_name, tv_balance;

        LinearLayout ll_fund_list_row;
        ImageView img_currencyicon;


        public MyViewHolder(View view) {
            super(view);


            txt_currency_name = view.findViewById(R.id.txt_currency_name);
            tv_balance = view.findViewById(R.id.tv_balance);
            ll_fund_list_row = view.findViewById(R.id.ll_fund_list_row);
            img_currencyicon = view.findViewById(R.id.img_currencyicon);


        }
    }


    public FundAdapter(JSONArray moviesList, MainActivity mainActivity, FundFragment fundFragment) {
        this.moviesList = moviesList;
        this.ira1 = mainActivity;
        this.fundFragment = fundFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fund_adapter_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {

            JSONObject dataObj = moviesList.getJSONObject(position);
//            if (position % 2 == 0) {
//                holder.ll_fund_list_row.setBackgroundColor(ira1.getResources().getColor(R.color.section_color_lite));
//            } else {
//                holder.ll_fund_list_row.setBackgroundColor(ira1.getResources().getColor(R.color.section_color));
//            }

            holder.txt_currency_name.setText(dataObj.getString("symbol"));
            holder.tv_balance.setText(dataObj.getString("available_balance"));
            showImage(dataObj.getString("icon"), holder.img_currencyicon);

            holder.ll_fund_list_row.setTag(dataObj);
            holder.ll_fund_list_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        JSONObject data = new JSONObject(v.getTag().toString());
                        String symbol = data.getString("symbol");
                        Intent intent = new Intent(ira1, BalanceFulledetails.class);
                        intent.putExtra("data", v.getTag() + "");
                        ira1.startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return moviesList.length();
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