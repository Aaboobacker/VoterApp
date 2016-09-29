package com.aineri.group.vote;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	FragmentManager fm ;
	public static MainFragment mainFragment;
	public static LoginFragment loginFragment;
	
	private static final String PREFER_NAME = "PROFILE";
	int PRIVATE_MODE = 0;
	SharedPreferences pref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pref = getApplicationContext().getSharedPreferences(PREFER_NAME, PRIVATE_MODE);	
		fm = getFragmentManager();
		if(pref.getAll().size() > 0){			
			if(mainFragment == null) {
				mainFragment = MainFragment.newInstance();
			}
			
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			
			ft.replace(R.id.container, mainFragment);
			ft.addToBackStack(null);
			ft.commit();		
		}else{
			if(loginFragment == null) {
				loginFragment = LoginFragment.newInstance();
			}
			
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			
			ft.replace(R.id.container, loginFragment);
			ft.addToBackStack(null);
			ft.commit();			
		}
	}

    @Override
    public void onBackPressed(){
    	
        if (fm.getBackStackEntryCount() > 1) {            
            fm.popBackStack();
        } else {   
        	finish();
            super.onBackPressed();            
        }     
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:		    	
	        if (fm.getBackStackEntryCount() > 1) {            
	            fm.popBackStack();
	        }
            return true;
		}
		return super.onOptionsItemSelected(item);
	}    
}
