package com.web.whatashot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;


import com.web.whatashot.R;
import com.web.whatashot.pairdetailfragments.PairDetailView;

import java.util.ArrayList;

public class PriceVolumeAdapter extends RecyclerView.Adapter<PriceVolumeAdapter.MyViewHolder> {
    private PairDetailView ira1;
    private ArrayList<JSONObject> moviesList;
    private String type = "";
    private Context context = null;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_volume, txt_price;
        LinearLayout ll_orders_layout;
        private RecyclerView buy_sell_lines_recycler;


        public MyViewHolder(View view) {
            super(view);
            txt_volume = view.findViewById(R.id.txt_volume);
            txt_price = view.findViewById(R.id.txt_price);
            ll_orders_layout = view.findViewById(R.id.ll_orders_layout);
            buy_sell_lines_recycler = view.findViewById(R.id.buy_sell_lines_recycler);

        }
    }


    public PriceVolumeAdapter(Context context, ArrayList<JSONObject> moviesList, PairDetailView mainActivity, String type) {
        this.moviesList = moviesList;
        this.type = type;
        ira1 = mainActivity;
        this.context = context;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.price_volume_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {


            JSONObject object = moviesList.get(position);

            if (type.equalsIgnoreCase("buy")) {
                holder.txt_price.setText(object.getString("price"));
                holder.txt_volume.setText(object.getString("volume"));
                holder.txt_price.setTextColor(ira1.getResources().getColor(R.color.greencolor));
                holder.txt_volume.setTextColor(ira1.getResources().getColor(R.color.grey_dark));




            } else {
                holder.txt_volume.setText(object.getString("price"));
                holder.txt_price.setText(object.getString("volume"));
                holder.txt_volume.setTextColor(ira1.getResources().getColor(R.color.darkRed));
                holder.txt_price.setTextColor(ira1.getResources().getColor(R.color.grey_dark));

            }
//            if (position % 2 == 1) {
//                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.lite_blue_30));
//            } else {
//                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.lite_green_30));
//            }




        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    private void setBuSellHorizonalLines(RecyclerView buy_sell_lines_recycler, int count,String type)
    {
        BuySellHillsAdapter linesViewAdapter = new BuySellHillsAdapter(ira1, count,type);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(ira1, LinearLayoutManager.HORIZONTAL, false);
        buy_sell_lines_recycler.setLayoutManager(horizontalLayoutManagaer);
        buy_sell_lines_recycler.setItemAnimator(new DefaultItemAnimator());
        if(type.equalsIgnoreCase("buy")) {
            horizontalLayoutManagaer.setReverseLayout(true);
        }
        buy_sell_lines_recycler.setAdapter(linesViewAdapter);
    }


}