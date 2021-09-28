package com.web.whatashot.adapters;

import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;


import com.web.whatashot.DefaultConstants;
import com.web.whatashot.MainActivity;
import com.web.whatashot.R;
import com.web.whatashot.pairdetailfragments.PairDetailView;

import java.util.HashMap;
import java.util.Map;


public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MyViewHolder> {
    private MainActivity ira1;
    private JSONArray moviesList;
    Map<Integer,String> changeMap=new HashMap<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_subpair, txt_mainpar, txt_price, txt_volume, txt_change;
        ImageView img_arrow;
        LinearLayout ll_market_order_list_row;
        RelativeLayout rr_change;


    public MyViewHolder(View view) {
            super(view);
            img_arrow = view.findViewById(R.id.img_arrow);
            txt_subpair = view.findViewById(R.id.txt_subpair);
            txt_mainpar = view.findViewById(R.id.txt_mainpar);
            txt_price = view.findViewById(R.id.txt_price);
            txt_volume = view.findViewById(R.id.txt_volume);
            txt_change = view.findViewById(R.id.txt_change);
            rr_change = view.findViewById(R.id.rr_change);
            ll_market_order_list_row = view.findViewById(R.id.ll_market_order_list_row);

        }
    }


    public MarketAdapter(JSONArray moviesList, MainActivity mainActivity) {
        this.moviesList = moviesList;
        ira1=mainActivity;
        changeMap.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.market_order_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {


            JSONObject object=moviesList.getJSONObject(position);
            int pair_id=Integer.parseInt(object.getString("pair_id"));
            String change=object.getString("change").replace("%","");

            if(changeMap.containsKey(pair_id))
            {
                if (!changeMap.get(pair_id).equalsIgnoreCase(change))
                {

                    changeMap.put(pair_id,change);
                    if (change.contains("+"))
                    {
                        animationEffect(1, holder.ll_market_order_list_row, position);
                    }
                    else if (change.contains("-")) {
                        animationEffect(-1, holder.ll_market_order_list_row, position);

                    }
                    else
                    {
                        animationEffect(1, holder.ll_market_order_list_row, position);
                    }
                }
            }
            else
            {
                changeMap.put(pair_id,change);
            }

            String ar[]=object.getString("symbol").split("\\/");
            holder.txt_subpair.setText(ar[0]);
            holder.txt_mainpar.setText("/"+ar[1]);
            holder.txt_price.setText(object.getString("price"));
            holder.txt_volume.setText(object.getString("volume"));
            holder.txt_change.setText(change);


            if(change.contains("+"))
            {
                holder.txt_price.setTextColor(ira1.getResources().getColor(R.color.greencolor));
                holder.rr_change.setBackgroundResource(R.drawable.green_drawable);
                holder.img_arrow.setRotation(270);

            }
            else if(change.contains("-"))
            {

                holder.txt_price.setTextColor(ira1.getResources().getColor(R.color.darkRed));
                holder.rr_change.setBackgroundResource(R.drawable.round_corner_drawable);
                holder.img_arrow.setRotation(90);

            }
            else
            {

                holder.txt_price.setTextColor(ira1.getResources().getColor(R.color.greencolor));
                holder.rr_change.setBackgroundResource(R.drawable.green_drawable);
                holder.img_arrow.setRotation(270);

            }

            holder.ll_market_order_list_row.setTag(object+"");
            holder.ll_market_order_list_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(ira1, PairDetailView.class);
                        intent.putExtra(DefaultConstants.pair_data, v.getTag() + "");
                        ira1.startActivity(intent);
                       }
                     catch (Exception e)
                      {
                        e.printStackTrace();
                      }
                }
            });









          

//
//            if(position%2==0)
//            {
//                holder.ll_market_order_list_row.setBackgroundColor(ira1.getResources().getColor(R.color.section_color_lite));
//
//            }
//            else
//            {
//                holder.ll_market_order_list_row.setBackgroundColor(ira1.getResources().getColor(R.color.section_color));
//            }



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



    private void animationEffect(int x,LinearLayout linearLayout,int position)
    {
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               if(x<0)
               {
                   linearLayout.setBackgroundColor(ira1.getResources().getColor(R.color.light_color_red));
               }
               else if(x>0)
               {

                   linearLayout.setBackgroundColor(ira1.getResources().getColor(R.color.buy_line_color));
               }
               else
               {
                   linearLayout.setBackgroundColor(ira1.getResources().getColor(R.color.buy_line_color));
               }
           }
       },500) ;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                if(position%2==0)
                {
                    linearLayout.setBackgroundColor(ira1.getResources().getColor(R.color.white));

                }
                else
                {
                    linearLayout.setBackgroundColor(ira1.getResources().getColor(R.color.white));
                }
            }
        },1500);


    }
}