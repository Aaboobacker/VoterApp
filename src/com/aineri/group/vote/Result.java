package com.aineri.group.vote;

public class Result {
	private String Option;
	private int VoteCount;
	
	public String getOption(){
		return Option;
	}	
	public void setOption(String option){
		this.Option=option;
	}
	
	public int getVoteCount(){
		return VoteCount;
	}	
	public void setVoteCount(int voteCount){
		this.VoteCount=voteCount;
	}	
}
