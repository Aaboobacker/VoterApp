package com.aineri.group.vote;

import java.util.List;

import com.aineri.group.vote.PendingAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ResultAdapter extends BaseAdapter {
	
	private List<Result> resultList = null;	
	private Context mContext;
	LayoutInflater inflater;
	
	public ResultAdapter(Context context, List<Result> _resultList) {
		inflater = LayoutInflater.from(context);
        this.resultList = _resultList;
        this.mContext = context;
    }	
	
	public class ViewHolder {		
		TextView tvOption;
		TextView tvCount;
	}	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return resultList.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return resultList.get(pos);
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
			view = inflater.inflate(R.layout.result_list_item, null);			
			holder.tvOption =  (TextView)view.findViewById(R.id.tvOption);
			holder.tvCount = (TextView)view.findViewById(R.id.tvCount);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();			
		}		
		
		holder.tvOption.setText(resultList.get(pos).getOption());
		holder.tvCount.setText(String.valueOf(resultList.get(pos).getVoteCount())+" Votes");
		return view;
	}

}
