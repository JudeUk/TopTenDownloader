package com.JukanaCodes.toptendownloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FeedAdapter extends ArrayAdapter {

    private static final String TAG = "FeedAdapter";
    private List<FeedResponseEntry> applications;
    private final int layoutResource;
    private final LayoutInflater layoutInflater;


    public FeedAdapter(@NonNull Context context, int resource, List<FeedResponseEntry> applications) {

        super(context, resource);
        this.applications = applications;
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);

    }

    private class FeedAdapterViewHolder {

        final private TextView tvName;
        final private TextView tvArtist;
        final private TextView tvSummary;

        public FeedAdapterViewHolder(View v) {

            tvName = v.findViewById(R.id.tvName);
            tvArtist = v.findViewById(R.id.tvArtist);
            tvSummary = v.findViewById(R.id.tvSummary);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        FeedAdapterViewHolder feedAdapterViewHolder;

        if (convertView == null) {

            convertView = layoutInflater.inflate(layoutResource, parent, false);
            feedAdapterViewHolder = new FeedAdapterViewHolder(convertView);
            convertView.setTag(feedAdapterViewHolder);
        } else {

            feedAdapterViewHolder = (FeedAdapterViewHolder) convertView.getTag();

        }
//        View view = layoutInflater.inflate(layoutResource,parent,false);


        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvArtist = convertView.findViewById(R.id.tvArtist);
        TextView tvSummary = convertView.findViewById(R.id.tvSummary);

        FeedResponseEntry currentApp = applications.get(position);
        tvName.setText(currentApp.getName());
        tvArtist.setText(currentApp.getArtist());
        tvSummary.setText(currentApp.getSummary());

        return convertView;

    }

    @Override
    public int getCount() {
        return applications.size();
    }
}
