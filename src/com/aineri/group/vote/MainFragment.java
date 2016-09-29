package com.aineri.group.vote;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment implements OnClickListener {
	View view;
	ActionBar actionBar;
	Button btnCreate,btnPending,btnResult;
	public static CreateFragment createFragment;
	public static PendingFragment pendingFragment;
	public static AlltopicFragment alltopicFragment;
	FragmentManager fm ;
	
	public static MainFragment newInstance(){
		MainFragment fragment = new MainFragment();
		return fragment;
	}

	public MainFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_main,container, false);
		fm = getFragmentManager();
		btnCreate = (Button)view.findViewById(R.id.btnCreate);
		btnPending = (Button)view.findViewById(R.id.btnPending);
		btnResult = (Button)view.findViewById(R.id.btnResult);
		btnCreate.setOnClickListener(this);
		btnPending.setOnClickListener(this);
		btnResult.setOnClickListener(this);
		return view;
	}
	


	@Override
	public void onResume() {
		actionBar = getActivity().getActionBar();
		actionBar.show();
		actionBar.setTitle(getResources().getString(R.string.app_name));
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction ft ;
		switch (v.getId()) {
		case R.id.btnCreate:
			if(createFragment == null) {
				createFragment = CreateFragment.newInstance();
			}
			
			ft = getFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_right,R.animator.slide_in_left,R.animator.slide_out_right);
			ft.replace(R.id.container, createFragment);
			ft.addToBackStack(null);
			ft.commit();			
			break;
			
		case R.id.btnPending:
			if(pendingFragment == null) {
				pendingFragment = PendingFragment.newInstance();
			}
			
			ft = getFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_right,R.animator.slide_in_left,R.animator.slide_out_right);
			ft.replace(R.id.container, pendingFragment);
			ft.addToBackStack(null);
			ft.commit();			
			break;	
			
		case R.id.btnResult:
			if(alltopicFragment == null) {
				alltopicFragment = AlltopicFragment.newInstance();
			}
			
			ft = getFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_right,R.animator.slide_in_left,R.animator.slide_out_right);
			ft.replace(R.id.container, alltopicFragment);
			ft.addToBackStack(null);
			ft.commit();			
			break;				

		default:
			break;
		}
		
	}		
}
