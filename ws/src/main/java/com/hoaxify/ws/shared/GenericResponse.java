package com.hoaxify.ws.shared;


public class GenericResponse {
	
	public GenericResponse(String mesagge) {
		super();
		this.mesagge = mesagge;
	}

	private String mesagge;
	

	public String getMesagge() {
		return mesagge;
	}

	public void setMesagge(String mesagge) {
		this.mesagge = mesagge;
	}	
	
}
