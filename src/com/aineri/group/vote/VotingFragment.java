package com.aineri.group.vote;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class VotingFragment extends Fragment {
	View view;
	ActionBar actionBar;
	Button btnSubmit;
	TextView tvTopic,tvRemain;
	RadioGroup rgOptions;
	RadioButton rb1,rb2,rb3,rb4,rbSeleted;
	Votes vote;
	DatabaseHelper db;
	FragmentManager fm ;
	AdView adView = null;
	
	public void setVotes(Votes _vote){
		this.vote = _vote; 
	}
	
	public static VotingFragment newInstance(){
		VotingFragment fragment = new VotingFragment();
		return fragment;
	}

	public VotingFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_voting,container, false);
		db = new DatabaseHelper(getActivity().getApplicationContext());
		fm = getFragmentManager();
		tvTopic = (TextView)view.findViewById(R.id.tvTopic);
		tvRemain = (TextView)view.findViewById(R.id.tvRemain);
		rgOptions = (RadioGroup)view.findViewById(R.id.rgOptions);
		rb1 =(RadioButton)view.findViewById(R.id.rb1);
		rb2 =(RadioButton)view.findViewById(R.id.rb2);
		rb3 =(RadioButton)view.findViewById(R.id.rb3);
		rb4 =(RadioButton)view.findViewById(R.id.rb4);
		btnSubmit = (Button)view.findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getActivity(), getRemainingTime(vote.getCreatedon()), Toast.LENGTH_LONG).show();
				int selectedId = rgOptions.getCheckedRadioButtonId();
				rbSeleted = (RadioButton) view.findViewById(selectedId);				
		        new AlertDialog.Builder(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_DARK)
		        .setIcon(android.R.drawable.ic_dialog_alert)
		        .setTitle("Confirmation")
		        .setMessage("Do you want to submit?")
		        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

		            @Override
		            public void onClick(DialogInterface dialog, int which) {	                
		            	db.createVote(vote.getId(), rbSeleted.getText().toString());
		            	Toast.makeText(getActivity(), "Your vote submitted successfully", Toast.LENGTH_SHORT).show();
		            }

		        })
		        .setNegativeButton("No", null)
		        .show();				
			}
		});
		
        adView = (AdView)view.findViewById(R.id.adview);  
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);		
		return view;
	}
	


	@Override
	public void onResume() {
		actionBar = getActivity().getActionBar();
		actionBar.show();
		actionBar.setTitle("Voting");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);		
		tvTopic.setText(this.vote.getTopic());
		if(!vote.getChoice1().isEmpty()){
			rb1.setText(vote.getChoice1());
		}else{
			rb1.setVisibility(View.GONE);
		}
		
		if(!vote.getChoice2().isEmpty()){
			rb2.setText(vote.getChoice2());
		}else{
			rb2.setVisibility(View.GONE);
		}
		
		if(!vote.getChoice3().isEmpty()){
			rb3.setText(vote.getChoice3());
		}else{
			rb3.setVisibility(View.GONE);
		}
		
		if(!vote.getChoice4().isEmpty()){
			rb4.setText(vote.getChoice4());
		}else{
			rb4.setVisibility(View.GONE);
		}		
		tvRemain.setText("Topic will remain close on: "+getRemainingTime(vote.getCreatedon()));
		super.onResume();
	}
	
	public int getHour(int mins){
		return mins/60;
	}
	
	public int getMins(int mins){
		return mins%60;
	}	
	
	public String getRemainingTime(String createdon){
		String addedtime = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date dtc = dateFormat.parse(vote.getCreatedon());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dtc);
			calendar.add(Calendar.HOUR, getHour(vote.getTimer()));
			calendar.add(Calendar.MINUTE, getMins(vote.getTimer()));			
			addedtime = dateFormat.format(calendar.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addedtime;
	}
	
}
