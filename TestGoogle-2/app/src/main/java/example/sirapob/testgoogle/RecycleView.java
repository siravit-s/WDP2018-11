package example.sirapob.testgoogle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecycleView {
    private Context mContext;
    private ActivitiesAdapter mActivitiesAdapter;
    private TextView dateSt_tv;
    private List<Activities> activities = new ArrayList<>();


    public void setConfig(RecyclerView recyclerView, Context context, List<Activities> activities, List<String> keys) {
        mContext = context;
        mActivitiesAdapter = new ActivitiesAdapter(activities, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mActivitiesAdapter);
    }

    class ActivitieItemView extends RecyclerView.ViewHolder {
        private TextView title_tv;
        private TextView timeSt_tv;
        private TextView place_tv;
        private String keys;

        private LinearLayout event_ll;

        public ActivitieItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.activities_list, parent, false));

            title_tv = itemView.findViewById(R.id.title_tv);
            dateSt_tv = itemView.findViewById(R.id.dateSt_tv);
            timeSt_tv = itemView.findViewById(R.id.timeSt_tv);
            place_tv = itemView.findViewById(R.id.place_tv);
            event_ll = itemView.findViewById(R.id.event_ll);
        }

        public void bind(Activities activities, String key) {
            title_tv.setText(activities.getEv_title());
//            Intent intent = new Intent(mContext, MainPageActivity.class);
//            intent.putExtra("Ac_title", title_tv.getText());
            final String desc = activities.getEv_desc();
            String change = activities.getEv_dateSt();
            String test = setFormatDate(change);
            dateSt_tv.setText(test);
            dateSt_tv.setTextColor(Color.WHITE);
            dateSt_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
            setColor(change);

//           dateSt_tv.setText(activities.getEv_dateSt());

//            String getDate = parseDateToddMMyyyy(activities.getEv_timeSt());
//            dateSt_tv.setText(getDate);

            final String get_dateEn = activities.getEv_dateEn();
            final String dateEn_str = setFormatDate(get_dateEn);
            timeSt_tv.setText(activities.getEv_timeSt());
            final String timeEn = activities.getEv_timeEn();
            place_tv.setText(activities.getEv_place());
            final String img_url = activities.getEv_imgUrl();
            keys = key;

            event_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailAcActivity.class);
                    intent.putExtra("Ac_title", title_tv.getText());
                    intent.putExtra("Ac_desc", desc);
                    intent.putExtra("Ac_dateSt", dateSt_tv.getText());
                    intent.putExtra("Ac_dateEn", dateEn_str);
                    intent.putExtra("Ac_timeSt", timeSt_tv.getText());
                    intent.putExtra("Ac_timeEn", timeEn);
                    intent.putExtra("Ac_place", place_tv.getText());
                    intent.putExtra("Ac_imgUrl", img_url);
                    intent.putExtra("keys", keys);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitieItemView> {
        private List<Activities> mActivities;
        private List<String> mKeys;

        @NonNull
        @Override
        public ActivitieItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ActivitieItemView(viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull ActivitieItemView activitieItemView, int i) {
            activitieItemView.bind(mActivities.get(i), mKeys.get(i));
        }

        @Override
        public int getItemCount() {
            return mActivities.size();
        }

        public ActivitiesAdapter(List<Activities> mActivities, List<String> mKeys) {
            this.mActivities = mActivities;
            this.mKeys = mKeys;
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
    public String setColor(String time) {
        String yyyy = time.substring(0, 4);
        String MM = time.substring(5, 7);
        String dd = time.substring(8, 10);

        if (dd.equals("01")||dd.equals("13")) {
            dateSt_tv.setBackgroundResource(R.drawable.roundedrec1);
        } else if (dd.equals("02")||dd.equals("14")||dd.equals("25")) {
            dateSt_tv.setBackgroundResource(R.drawable.roundedrec2);
        } else if (dd.equals("03")||dd.equals("15")||dd.equals("26")) {
            dateSt_tv.setBackgroundResource(R.drawable.roundedrec3);
        } else if (dd.equals("04")||dd.equals("16")||dd.equals("27")) {
            dateSt_tv.setBackgroundResource(R.drawable.roundedrec5);
        } else if (dd.equals("05")||dd.equals("17")||dd.equals("28")) {
            dateSt_tv.setBackgroundResource(R.drawable.roundedrec4);
        } else if (dd.equals("06")||dd.equals("18")||dd.equals("29")) {
            dateSt_tv.setBackgroundResource(R.drawable.roundedrec6);
        } else if (dd.equals("07")||dd.equals("19")||dd.equals("30")) {
            dateSt_tv.setBackgroundResource(R.drawable.roundedrec7);
        } else if (dd.equals("08")||dd.equals("20")||dd.equals("31")) {
            dateSt_tv.setBackgroundResource(R.drawable.roundedrec8);
        } else if (dd.equals("09")||dd.equals("21")) {
            dateSt_tv.setBackgroundResource(R.drawable.roundedrec9);
        } else if (dd.equals("10")||dd.equals("22")) {
            dateSt_tv.setBackgroundResource(R.drawable.roundedrec10);
        } else if (dd.equals("11")||dd.equals("23")) {
            dateSt_tv.setBackgroundResource(R.drawable.roundedrec11);

        } else if (dd.equals("12")||dd.equals("24")) {
            dateSt_tv.setBackgroundResource(R.drawable.roundedrec12);
        }
        return null;


    }

    public void updateList(List<Activities> newList) {
        activities = new ArrayList<>();
        activities.addAll(newList);
    }


}
