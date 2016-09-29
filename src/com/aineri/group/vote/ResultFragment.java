package com.aineri.group.vote;

import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ResultFragment extends Fragment {
	View view;
	ActionBar actionBar;
	ListView lvResult;
	DatabaseHelper db;
	private List<Result> resultList = null;		
	public static VotingFragment votingFragment;
	FragmentManager fm ;
	ResultAdapter adapter;
	private int topicid;
	TextView tvResTopic;
	AdView adView = null;
	public void setTopicId(int id){
		this.topicid  = id;
	}
	
	public static ResultFragment newInstance(){
		ResultFragment fragment = new ResultFragment();
		return fragment;
	}

	public ResultFragment(){
		
	}	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_result,container, false);
		db = new DatabaseHelper(getActivity().getApplicationContext());
		tvResTopic = (TextView)view.findViewById(R.id.tvResultTopic);
		fm = getFragmentManager();
		lvResult = (ListView)view.findViewById(R.id.lvResult);
        adView = (AdView)view.findViewById(R.id.adview);  
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);		
		//Toast.makeText(getActivity(), String.valueOf(db.getVoteCount()), Toast.LENGTH_SHORT).show();

		return view;
	}	
	
	@Override
	public void onResume() {
		actionBar = getActivity().getActionBar();
		actionBar.show();
		actionBar.setTitle("Result");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);		
		tvResTopic.setText(db.getTopicName(topicid));		
		resultList = db.getResults(topicid);
		adapter = new ResultAdapter(getActivity(), resultList);
		lvResult.setAdapter(adapter);	 
		super.onResume();
	}	
	
}
