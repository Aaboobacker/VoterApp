package com.aineri.group.vote;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AlltopicFragment extends Fragment {
	View view;
	ActionBar actionBar;
	ListView lvTopics;
	DatabaseHelper db;
	private List<Votes> votesList = null;		
	public static ResultFragment resultFragment;
	FragmentManager fm ;
	PendingAdapter adapter;
	
	public static AlltopicFragment newInstance(){
		AlltopicFragment fragment = new AlltopicFragment();
		return fragment;
	}

	public AlltopicFragment(){
		
	}	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_alltopic,container, false);
		db = new DatabaseHelper(getActivity().getApplicationContext());
		fm = getFragmentManager();
		lvTopics = (ListView)view.findViewById(R.id.lvTopics);	
		return view;
	}	
	
	@Override
	public void onResume() {
		actionBar = getActivity().getActionBar();
		actionBar.show();
		actionBar.setTitle("All Topics");		
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		votesList = db.getAllVotes();
		adapter = new PendingAdapter(getActivity(), votesList);
		lvTopics.setAdapter(adapter);
		lvTopics.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				FragmentTransaction ft ;
				if(resultFragment == null) {
					resultFragment = ResultFragment.newInstance();
				}
				resultFragment.setTopicId(votesList.get(pos).getId());
				ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_right,R.animator.slide_in_left,R.animator.slide_out_right);
				ft.replace(R.id.container, resultFragment);
				ft.addToBackStack(null);
				ft.commit(); 				
				
			}
		});		
		super.onResume();
	}	
}
