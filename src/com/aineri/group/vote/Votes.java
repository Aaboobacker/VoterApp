package com.aineri.group.vote;

public class Votes {
	
	private int Id;
	private String Topic;
	private String Choice1;
	private String Choice2;
	private String Choice3;
	private String Choice4;
	private String Createdon;
	private int Timer;
	private int Status; /* 1-Active, 0 - Inactive */
	
	public int getId(){
		return Id;
	}	
	public void setId(int id){
		this.Id=id;
	}
	
	public String getTopic(){
		return Topic;
	}	
	public void setTopic(String topic){
		this.Topic=topic;
	}	
	
	public String getChoice1(){
		return Choice1;
	}	
	public void setChoice1(String choice1){
		this.Choice1=choice1;
	}	
	
	public String getChoice2(){
		return Choice2;
	}	
	public void setChoice2(String choice2){
		this.Choice2=choice2;
	}
	
	public String getChoice3(){
		return Choice3;
	}	
	public void setChoice3(String choice3){
		this.Choice3=choice3;
	}
	
	public String getChoice4(){
		return Choice4;
	}	
	public void setChoice4(String choice4){
		this.Choice4=choice4;
	}	
	
	public int getTimer(){
		return Timer;
	}	
	public void setTimer(int timer){
		this.Timer=timer;
	}	
	
	public String getCreatedon(){
		return Createdon;
	}	
	public void setCreatedon(String createdon){
		this.Createdon=createdon;
	}
	
	public int getStatus(){
		return Status;
	}	
	public void setStatus(int status){
		this.Status=status;
	}	
	
}
