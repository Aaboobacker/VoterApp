package com.aineri.group.vote;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
	View view;
	ActionBar actionBar;
	Editor editor;
	EditText etUsername,etPassword;
	Button btnLogin;
	private static final String PREFER_NAME = "PROFILE";
	int PRIVATE_MODE = 0;
	SharedPreferences pref;
	public static MainFragment mainFragment;
	
	public static LoginFragment newInstance(){
		LoginFragment fragment = new LoginFragment();
		return fragment;
	}

	public LoginFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_login,container, false);
		pref = getActivity().getApplicationContext().getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
		etUsername = (EditText)view.findViewById(R.id.etUsername);
		etPassword = (EditText)view.findViewById(R.id.etPassword);
		btnLogin = (Button)view.findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View btn) {
				if(etUsername.getText().toString().trim().equals("admin") && etPassword.getText().toString().trim().equals("admin")){
					editor = pref.edit();
					editor.putString("UN", etUsername.getText().toString().trim());
					editor.putString("PAS", etPassword.getText().toString().trim());
					editor.commit();
					
					if(mainFragment == null) {
						mainFragment = MainFragment.newInstance();
					}
					
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					
					ft.replace(R.id.container, mainFragment);
					ft.addToBackStack(null);
					ft.commit();					
				}else{
					Toast.makeText(getActivity(), "Inavlid Username or Password", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		actionBar = getActivity().getActionBar();
		actionBar.hide();
		super.onAttach(activity);
	}	
	
	
}
