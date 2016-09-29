package com.aineri.group.vote;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateFragment extends Fragment {
	View view;
	ActionBar actionBar;
	Spinner timer;
	Button btnInvite,btnSend;
	EditText etTopic,etChoice1, etChoice2, etChoice3, etChoice4;
	private static final int PICK_CONTACT_REQUEST = 1;
	DatabaseHelper db;
	Votes votes;
	public static CreateFragment newInstance(){
		CreateFragment fragment = new CreateFragment();
		return fragment;
	}

	public CreateFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_create,container, false);
		db = new DatabaseHelper(getActivity().getApplicationContext());
		votes =  new Votes();
		timer = (Spinner)view.findViewById(R.id.timer);
		etTopic = (EditText)view.findViewById(R.id.etTopic);
		etChoice1 = (EditText)view.findViewById(R.id.etChoice1);
		etChoice2 = (EditText)view.findViewById(R.id.etChoice2);
		etChoice3 = (EditText)view.findViewById(R.id.etChoice3);
		etChoice4 = (EditText)view.findViewById(R.id.etChoice4);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.timer_array, android.R.layout.simple_spinner_item);	
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		timer.setAdapter(adapter);
		btnInvite = (Button)view.findViewById(R.id.btnInvite);
		btnSend = (Button)view.findViewById(R.id.btnSend);
		
		btnInvite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    Intent pickContactIntent = new Intent( Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI );
			    pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
			    startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);				
				
			}
		});
		
		btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int timerval = 0;
				votes.setTopic(etTopic.getText().toString().trim());
				votes.setChoice1(etChoice1.getText().toString().trim());
				votes.setChoice2(etChoice2.getText().toString().trim());
				votes.setChoice3(etChoice3.getText().toString().trim());
				votes.setChoice4(etChoice4.getText().toString().trim());
				if(timer.getSelectedItem().equals("30 mins")){
					timerval = 30;
				}else if(timer.getSelectedItem().equals("1 hour")){
					timerval = 60;
				}else if(timer.getSelectedItem().equals("2 hours")){
					timerval = 60*2;
				}else if(timer.getSelectedItem().equals("4 hours")){
					timerval = 60*4;
				}else if(timer.getSelectedItem().equals("8 hours")){
					timerval = 60*8;
				}else if(timer.getSelectedItem().equals("16 hours")){
					timerval = 60*16;
				}else{
					timerval = 60*24;
				}
				votes.setTimer(timerval);
				votes.setStatus(1);
				db.createTopic(votes);	
				db.closeDB();
				
				etTopic.setText("");
				etChoice1.setText("");
				etChoice2.setText("");
				etChoice3.setText("");
				etChoice4.setText("");
				timer.setSelection(0);
				Toast.makeText(getActivity(), "Voting topic created successfully", Toast.LENGTH_LONG).show();
			}
		});
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		actionBar = getActivity().getActionBar();
		actionBar.show();
		actionBar.setTitle("Create Vote");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);		
		super.onAttach(activity);
	}
	
	@Override
	public void onActivityResult( int requestCode, int resultCode, Intent intent ) {

	    super.onActivityResult( requestCode, resultCode, intent );
	    String phoneNumber = "";
	    if ( requestCode == PICK_CONTACT_REQUEST ) {

	        if ( resultCode == Activity.RESULT_OK ) {
	                Uri contactData  = intent.getData();
	                Cursor c =  getActivity().getContentResolver().query(contactData, null, null, null, null);
	                if (c.moveToFirst()) {
	                	int  phoneIndex =c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
	                	phoneNumber = c.getString(phoneIndex);	     
	                }
	                Toast.makeText(getActivity(), "Phone number: "+phoneNumber+" added to invite", Toast.LENGTH_SHORT).show();	                
	                c.close();
	            }
	        }
	    }
		
}
