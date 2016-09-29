package com.aineri.group.vote;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PendingAdapter extends BaseAdapter {

	private List<Votes> votesList = null;	
	private Context mContext;
	LayoutInflater inflater;
	public PendingAdapter(Context context, List<Votes> _votesList) {
		inflater = LayoutInflater.from(context);
        this.votesList = _votesList;
        this.mContext = context;
    }
	
	public class ViewHolder {		
		TextView tvTopic;
		TextView tvCreated;
	}	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return votesList.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return votesList.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();			
			view = inflater.inflate(R.layout.pending_list_item, null);			
			holder.tvTopic =  (TextView)view.findViewById(R.id.tvTopic);
			holder.tvCreated = (TextView)view.findViewById(R.id.tvCreated);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();			
		}		
		
		holder.tvTopic.setText(votesList.get(pos).getTopic());
		holder.tvCreated.setText("Created on: "+votesList.get(pos).getCreatedon());
		return view;
	}

}
