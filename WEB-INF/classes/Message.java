import java.io.*;
import java.io.Serializable;

public class Message implements Serializable
{
	private String sender;
	private String receiver;
	private String subject;
	private String msg;
	private String status;

	Message() {}
	Message(String sndr, String receiver, String subject, String message, String status)
	{
		this.sender = sndr;
		this.receiver = receiver;
		this.subject = subject;
		this.msg = message;
		this.status = status;
	}
	
	public String getSender()
	{
		return(this.sender);
	}
	public String getReceiver()
	{
		return(this.receiver);
	}
	public String getSubject()
	{
		return(this.subject);
	}
	public String getMessage()
	{
		return(this.msg);
	}
	public String getStatus()
	{
		return(this.status);
	}
	
	public void setSender(String sndr)
	{
		this.sender = sndr;
	}
	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	public void setMessage(String message)
	{
		this.msg = message;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
}
