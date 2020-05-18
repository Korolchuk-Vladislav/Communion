package com.dreanei.communion.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerMessage {

	public static final ServerMessage ERROR_INVALID_PARAMS = new ServerMessage("error","invalid parameters");
	public static final ServerMessage ERROR_ACCESS_DENIED = new ServerMessage("error","you got no acccess");
	public static final ServerMessage ERROR_SERVER_EXCEPTION = new ServerMessage("error","server exception");
    public static final ServerMessage SUCCESS_MESSAGE = new ServerMessage("ok","operation executed successful");

    @SerializedName("status")
	@Expose
	private String status;
	@SerializedName("message")
	@Expose
	private String message;

	public ServerMessage(){ }
	
	public ServerMessage(String message){
		status = "ok";
		this.message = message;
	}
	
	public ServerMessage(String status, String message){
		this.status = status;
		this.message = message;
	}
	
	public String getStatus() {
		return status;
	}

	public ServerMessage setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ServerMessage setMessage(String message) {
		this.message = message;
		return this;
	}

}