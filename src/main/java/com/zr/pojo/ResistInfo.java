package com.zr.pojo;

public class ResistInfo {

	
	private String chipId;
	private String resistValue;
	private String dataTime;
	
	
	public ResistInfo() {
		// TODO Auto-generated constructor stub
	}


	public String getChipId() {
		return chipId;
	}


	public void setChipId(String chipId) {
		this.chipId = chipId;
	}


	public String getResistValue() {
		return resistValue;
	}


	public void setResistValue(String resistValue) {
		this.resistValue = resistValue;
	}


	public String getDataTime() {
		return dataTime;
	}


	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}


	public ResistInfo(String chipId, String resistValue, String dataTime) {
		super();
		this.chipId = chipId;
		this.resistValue = resistValue;
		this.dataTime = dataTime;
	}
	
	
}
