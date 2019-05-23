package example.sirapob.testgoogle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class KKUAcRecyclerView extends RecyclerView.Adapter<KKUAcRecyclerView.MyViewHolder> {
    private Context mContext;
    private List<KKUActivities> mData;
    RequestOptions option;


    public KKUAcRecyclerView(Context mContext, List<KKUActivities> mdata) {
        this.mContext = mContext;
        this.mData = mdata;

        option = new RequestOptions().placeholder(R.drawable.loading_shape).error((R.drawable.loading_shape));
    }

    @NonNull
    @Override
    public KKUAcRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.kkuac_row_item, viewGroup,false);
        return new KKUAcRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KKUAcRecyclerView.MyViewHolder myViewHolder, int i) {

        final int position = i;
        myViewHolder.title_kkuac.setText(mData.get(i).getTitle());
        myViewHolder.place_kkuac.setText(mData.get(i).getPlace());
        String change = mData.get(i).getDateSt();
        String dateFormat = setFormatDate(change);
        //setColor(dateFormat);

        myViewHolder.dateST_kkuac.setText(dateFormat);
        myViewHolder.dateST_kkuac2.setText(dateFormat);
        myViewHolder.dateST_kkuac.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        setColor(myViewHolder, change);
        myViewHolder.linear_kkuac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mData.get(position).getLink()));
                mContext.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mData.size();

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linear_kkuac;
        TextView title_kkuac;
        TextView place_kkuac;
        TextView dateST_kkuac;
        TextView dateST_kkuac2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            linear_kkuac = itemView.findViewById(R.id.linear_kkuac);
            title_kkuac = itemView.findViewById(R.id.title_kkuac);
            place_kkuac = itemView.findViewById(R.id.place_kkuac);
            dateST_kkuac = itemView.findViewById(R.id.dateST_kkuac);
            dateST_kkuac2 = itemView.findViewById(R.id.dateST_kkuac2);
        }

    }

    public String setFormatDate(String time) {
        String yyyy = time.substring(0, 4);
        String MM = time.substring(5, 7);
        String dd = time.substring(8, 10);
        String janStr = mContext.getResources().getString(R.string.JAN);
        String febStr = mContext.getResources().getString(R.string.FEB);
        String marStr = mContext.getResources().getString(R.string.MAR);
        String aprStr = mContext.getResources().getString(R.string.APR);
        String mayStr = mContext.getResources().getString(R.string.MAY);
        String junStr = mContext.getResources().getString(R.string.JUN);
        String julStr = mContext.getResources().getString(R.string.JUL);
        String augStr = mContext.getResources().getString(R.string.AUG);
        String sepStr = mContext.getResources().getString(R.string.SEP);
        String octStr = mContext.getResources().getString(R.string.OCT);
        String novStr = mContext.getResources().getString(R.string.NOV);
        String decStr = mContext.getResources().getString(R.string.DEC);

        if (MM.equals("01")) {
            String jan = MM.replace("01", janStr);
            String newFormat = dd + " " + jan +" " + yyyy;
            return newFormat;
        } else if (MM.equals("02")) {
            String feb = MM.replace("02", febStr);
            String newFormat = dd + " " + feb + " " +yyyy;
            return newFormat;
        } else if (MM.equals("03")) {
            String mar = MM.replace("03", marStr);
            String newFormat = dd + " " + mar +" " + yyyy;
            return newFormat;
        } else if (MM.equals("04")) {
            String apr = MM.replace("04", aprStr);
            String newFormat = dd + " " + apr +" " + yyyy;
            return newFormat;
        } else if (MM.equals("05")) {
            String may = MM.replace("05", mayStr);
            String newFormat = dd + " " + may+ " " +yyyy;
            return newFormat;
        } else if (MM.equals("06")) {
            String jun = MM.replace("06", junStr);
            String newFormat = dd + " " + jun+" " + yyyy;
            return newFormat;
        } else if (MM.equals("07")) {
            String jul = MM.replace("07", julStr);
            String newFormat = dd + " " + jul+ " " +yyyy;
            return newFormat;
        } else if (MM.equals("08")) {
            String aug = MM.replace("08", augStr);
            String newFormat = dd + " " + aug+" " + yyyy;
            return newFormat;
        } else if (MM.equals("09")) {
            String sep = MM.replace("09", sepStr);
            String newFormat = dd + " " + sep+ " " +yyyy;
            return newFormat;
        } else if (MM.equals("10")) {
            String oct = MM.replace("10", octStr);
            String newFormat = dd + " " + oct+" " + yyyy;
            return newFormat;
        } else if (MM.equals("11")) {
            String nov = MM.replace("11", novStr);
            String newFormat = dd + " " + nov+ " " +yyyy;
            return newFormat;
        } else if (MM.equals("12")) {
            String dec = MM.replace("12", decStr);
            String newFormat = dd + " " + dec+ " " +yyyy;
            return newFormat;
        }
        return null;


    }


    public String setColor(KKUAcRecyclerView.MyViewHolder myViewHolder, String time)  {
        String dd = time.substring(8, 10);


        if (dd.equals("01")||dd.equals("13")) {
            myViewHolder.dateST_kkuac.setBackgroundResource(R.drawable.roundedrec1);
        } else if (dd.equals("02")||dd.equals("14")||dd.equals("25")) {
            myViewHolder.dateST_kkuac.setBackgroundResource(R.drawable.roundedrec2);
        } else if (dd.equals("03")||dd.equals("15")||dd.equals("26")) {
            myViewHolder.dateST_kkuac.setBackgroundResource(R.drawable.roundedrec3);
        } else if (dd.equals("04")||dd.equals("16")||dd.equals("27")) {
            myViewHolder.dateST_kkuac.setBackgroundResource(R.drawable.roundedrec5);
        } else if (dd.equals("05")||dd.equals("17")||dd.equals("28")) {
            myViewHolder.dateST_kkuac.setBackgroundResource(R.drawable.roundedrec4);
        } else if (dd.equals("06")||dd.equals("18")||dd.equals("29")) {
            myViewHolder.dateST_kkuac.setBackgroundResource(R.drawable.roundedrec6);
        } else if (dd.equals("07")||dd.equals("19")||dd.equals("30")) {
            myViewHolder.dateST_kkuac.setBackgroundResource(R.drawable.roundedrec7);
        } else if (dd.equals("08")||dd.equals("20")||dd.equals("31")) {
            myViewHolder.dateST_kkuac.setBackgroundResource(R.drawable.roundedrec8);
        } else if (dd.equals("09")||dd.equals("21")) {
            myViewHolder.dateST_kkuac.setBackgroundResource(R.drawable.roundedrec9);
        } else if (dd.equals("10")||dd.equals("22")) {
            myViewHolder.dateST_kkuac.setBackgroundResource(R.drawable.roundedrec10);
        } else if (dd.equals("11")||dd.equals("23")) {
            myViewHolder.dateST_kkuac.setBackgroundResource(R.drawable.roundedrec11);
        } else if (dd.equals("12")||dd.equals("24")) {
            myViewHolder.dateST_kkuac.setBackgroundResource(R.drawable.roundedrec12);
        }
        return null;


    }


}