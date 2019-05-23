package example.sirapob.testgoogle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class NewsRecyclerView extends RecyclerView.Adapter<NewsRecyclerView.MyViewHolder> {

    private Context mContext;
    private List<News> mData;
    RequestOptions option;

    public NewsRecyclerView(Context mContext, List<News> mdata) {
        this.mContext = mContext;
        this.mData = mdata;

        option = new RequestOptions().placeholder(R.drawable.loading_shape).error((R.drawable.loading_shape));
    }

    @NonNull
    @Override
    public NewsRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.news_row_item, viewGroup,false);
        return new NewsRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

        final int position = i;
        myViewHolder.name_tv.setText(mData.get(i).getName());
        myViewHolder.detail_tv.setText(mData.get(i).getDetail());
        myViewHolder.date_tv.setText(mData.get(i).getDate());

        Glide.with(mContext).load(mData.get(i).getImage()).apply(option).into(myViewHolder.img);

        myViewHolder.news_lin.setOnClickListener(new View.OnClickListener() {
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

    LinearLayout news_lin;
    TextView name_tv;
    ImageView img;
    TextView detail_tv;
    TextView date_tv;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        news_lin = itemView.findViewById(R.id.news_lin);
        name_tv = itemView.findViewById(R.id.name_news);
        img = itemView.findViewById(R.id.pic_news);
        detail_tv = itemView.findViewById(R.id.detail_news);
        date_tv = itemView.findViewById(R.id.date_news);
    }
}
}
