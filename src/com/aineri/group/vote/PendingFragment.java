package com.aineri.group.vote;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class PendingFragment extends Fragment {
	View view;
	ActionBar actionBar;
	ListView lvPendig;
	DatabaseHelper db;
	private List<Votes> votesList = null;	
	private ArrayList<Votes> votesTemp;		
	public static VotingFragment votingFragment;
	FragmentManager fm ;
	PendingAdapter adapter;
	public static PendingFragment newInstance(){
		PendingFragment fragment = new PendingFragment();
		return fragment;
	}

	public PendingFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_pending,container, false);
		db = new DatabaseHelper(getActivity().getApplicationContext());
		fm = getFragmentManager();
		lvPendig = (ListView)view.findViewById(R.id.lvPending);
		//Toast.makeText(getActivity(), String.valueOf(db.getVoteCount()), Toast.LENGTH_SHORT).show();

		return view;
	}
	
	@Override
	public void onResume() {
		actionBar = getActivity().getActionBar();
		actionBar.show();
		actionBar.setTitle("Pending Votes");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);		
		votesList = db.getAllVotes();	
		this.votesTemp = new ArrayList<Votes>();		
		for(int i=0;i< votesList.size();i++){
			Calendar c = Calendar.getInstance();
			if(!c.getTime().after(getRemainingTime(votesList.get(i).getCreatedon(), votesList.get(i).getTimer()))){
				votesTemp.add(votesList.get(i));
			}
		}
		adapter = new PendingAdapter(getActivity(), votesTemp);
		lvPendig.setAdapter(adapter);
		lvPendig.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				FragmentTransaction ft ;
				if(votingFragment == null) {
					votingFragment = VotingFragment.newInstance();
				}
				votingFragment.setVotes(votesTemp.get(pos));
				ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_right,R.animator.slide_in_left,R.animator.slide_out_right);
				ft.replace(R.id.container, votingFragment);
				ft.addToBackStack(null);
				ft.commit();				
				
			}
		});		
		super.onResume();
	}	
	
	public int getHour(int mins){
		return mins/60;
	}	
	
	public int getMins(int mins){
		return mins%60;
	}	
	
	public Date getRemainingTime(String createdon,int timer){
		Date remainTime = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date dtc = dateFormat.parse(createdon);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dtc);
			calendar.add(Calendar.HOUR, getHour(timer));
			calendar.add(Calendar.MINUTE, getMins(timer));			
			remainTime = calendar.getTime();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return remainTime;
	}	
}
