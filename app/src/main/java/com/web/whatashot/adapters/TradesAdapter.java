package com.web.whatashot.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.web.whatashot.R;
import com.web.whatashot.pairdetailfragments.PairDetailView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TradesAdapter extends RecyclerView.Adapter<TradesAdapter.MyViewHolder> {
    private PairDetailView ira1;
    private ArrayList<JSONObject> moviesList;
    private Map<Integer, String> changeMap = new HashMap<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_time, txt_price, txt_volume;
        ImageView img_arrow;
        LinearLayout ll_open_order_list_row;
        RelativeLayout rr_change;


        public MyViewHolder(View view) {
            super(view);

            txt_time = view.findViewById(R.id.txt_time);
            txt_price = view.findViewById(R.id.txt_price);
            txt_volume = view.findViewById(R.id.txt_volume);
            ll_open_order_list_row = view.findViewById(R.id.ll_open_order_list_row);

        }
    }


    public TradesAdapter(ArrayList<JSONObject> moviesList, PairDetailView mainActivity) {
        this.moviesList = moviesList;
        ira1 = mainActivity;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trades_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {

            JSONObject jsonObject = moviesList.get(position);

            String price = jsonObject.getString("price");
//            int pair_id = position;
            holder.txt_price.setText(price);
            holder.txt_time.setText(jsonObject.getString("modified").split(" ")[1]);
            holder.txt_volume.setText(jsonObject.getString("volume"));


//            if (changeMap.containsKey(pair_id))
//            {
//                System.out.println("Change map===" + changeMap.get(pair_id) + "===" + price + "==" + pair_id);
//                if(!changeMap.get(pair_id).equalsIgnoreCase(price)) {
//                    System.out.println("matched===" + changeMap.get(pair_id) + "===" + price + "==" + pair_id);
//                    changeMap.put(pair_id, price);
//
//                    animationEffect(1, holder.ll_open_order_list_row, position);
//                }
//
//            } else {
//                changeMap.put(pair_id, price);
//            }


            if (position % 2 == 0) {
                holder.ll_open_order_list_row.setBackgroundColor(ira1.getResources().getColor(R.color.section_color_lite));

            } else {
                holder.ll_open_order_list_row.setBackgroundColor(ira1.getResources().getColor(R.color.section_color));
            }


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


//    private void animationEffect(int x, LinearLayout linearLayout, int position) {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//               if (x > 0) {
//
//                    linearLayout.setBackgroundColor(ira1.getResources().getColor(R.color.light_color_green));
//                }
//            }
//        }, 500);
//
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (position % 2 == 0)
//                {
//                    linearLayout.setBackgroundColor(ira1.getResources().getColor(R.color.section_color_lite));
//
//                } else {
//                    linearLayout.setBackgroundColor(ira1.getResources().getColor(R.color.section_color));
//                }
//            }
//        }, 1500);
//
//
//    }

}