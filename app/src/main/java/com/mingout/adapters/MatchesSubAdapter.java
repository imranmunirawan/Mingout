package com.mingout.adapters;

/**
 * Created by Adnan Imtiaz on 10/29/2016.
 */
 import android.content.Context;
 import android.support.v7.widget.RecyclerView;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.ImageView;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.mingout.activities.MatchesActivity;
 import com.mingout.activities.R;
 import com.mingout.models.ChatRoomUserListModel;
 import com.mingout.models.MatchesSubModel;
 import com.squareup.picasso.Picasso;

 import java.util.List;

public class MatchesSubAdapter extends RecyclerView.Adapter<MatchesSubAdapter.MyViewHolder> {

    private List<ChatRoomUserListModel> subMatchesList;
    private MatchesActivity matchesActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView TV_title;
        public ImageView IV_main;

        public MyViewHolder(View view) {
            super(view);
            TV_title = (TextView) view.findViewById(R.id.TV_title);
            IV_main = (ImageView) view.findViewById(R.id.IV_main);
        }
    }


    public MatchesSubAdapter(List<ChatRoomUserListModel> moviesList, MatchesActivity matchesActivity) {
        this.subMatchesList = moviesList;
        this.matchesActivity = matchesActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ChatRoomUserListModel obj = subMatchesList.get(position);
        holder.TV_title.setText(obj.getName());

        if (!obj.getOriginal_picture().equals("")) {
            if (obj.getOriginal_picture().contains("http")) {
                Picasso.with(matchesActivity).load(obj.getOriginal_picture()).fit()
                        .centerCrop().into(holder.IV_main);
            } else {
                Picasso.with(matchesActivity)
                        .load("http://mingout.cloudapp.net/mingout-beta-api/assets/profile-images/"
                                + obj.getOriginal_picture()).fit().centerCrop()
                        .into(holder.IV_main);
            }
        } else if (!obj.getThumb_picture().equals("")) {
            Picasso.with(matchesActivity).load(obj.getThumb_picture()).fit().centerCrop().into(holder.IV_main);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchesActivity.setMainMatchPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subMatchesList.size();
    }
}
