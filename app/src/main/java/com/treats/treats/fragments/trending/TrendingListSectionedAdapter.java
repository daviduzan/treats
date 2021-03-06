package com.treats.treats.fragments.trending;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.treats.treats.R;
import com.treats.treats.models.ServerModels;

import java.util.ArrayList;

/**
 * Created by david.uzan on 9/1/2016.
 */
public class TrendingListSectionedAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder> {

    public static final int TAG_KEY_SECTION = 0;
    public static final int TAG_KEY_RELATIVE_POSITION = 1;

    private View.OnClickListener mOnClickListener;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<ServerModels.TrendingGroupSM> mTrendings;

    public TrendingListSectionedAdapter(ArrayList<ServerModels.TrendingGroupSM> trendingGroupSMs) {
        mTrendings = trendingGroupSMs;

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mOnItemClickListener != null) {

                    mOnItemClickListener.onItemClick((int) view.getTag(TAG_KEY_SECTION), (int) view.getTag(TAG_KEY_RELATIVE_POSITION));
                }
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Change inflated layout based on 'header'.
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType == VIEW_TYPE_HEADER ? R.layout.category_list_group_header : R.layout.category_list_item, parent, false);
        return new TrendingListViewHolder(v, mOnClickListener);

    }

    @Override
    public int getSectionCount() {
        return mTrendings.size();
    }

    @Override
    public int getItemCount(int section) {
        return mTrendings.get(section).getMembers().size();
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {
        // Setup header view.
        ((TrendingListViewHolder) holder).bindHeaderData(mTrendings.get(section).getHeadline_one());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int section, int relativePosition, int absolutePosition) {
        // Setup non-header view.
        // 'section' is section index.
        // 'relativePosition' is index in this section.
        // 'absolutePosition' is index out of all non-header items.
        // See sample project for a visual of how these indices work.

        String imageUrl = mTrendings.get(section).getMembers().get(relativePosition).getImage();
        ((TrendingListViewHolder) holder).bindItemData(section, relativePosition, imageUrl);
        // TODO get the member from events/places table and set values
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setData(ArrayList<ServerModels.TrendingGroupSM> data) {
        mTrendings = data;
        notifyDataSetChanged();
    }

    interface OnItemClickListener {
        void onItemClick(int section, int relativePosition);
    }

    private static class TrendingListViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;
        TextView tvLabel;
        int section;

        TrendingListViewHolder(View v, View.OnClickListener clickListener) {
            super(v);
            this.itemView.setOnClickListener(clickListener);
            tvItem = (TextView) v.findViewById(R.id.temp_text_list);
            tvLabel = (TextView) v.findViewById(R.id.tv_category);
        }

        void bindItemData(int section, int relativePosition, String data) {
            itemView.setTag(TAG_KEY_SECTION, section);
            itemView.setTag(TAG_KEY_RELATIVE_POSITION, relativePosition);
            tvItem.setText(data);
        }

        void bindHeaderData(String data) {
            tvLabel.setText(data);
        }
    }
}

