import java.io.*;
import java.io.Serializable;

public class User implements Serializable
{
	private String userName;
	private String password;
	private String userType;
	private String emailID;
	private String contactNo;

	User() {}
	User(String uname, String password, String userType, String email, String contactNo)
	{
		this.userName = uname;
		this.password = password;
		this.userType = userType;
		this.emailID = email;
		this.contactNo = contactNo;
	}
	
	public String getUserName()
	{
		return(this.userName);
	}
	public String getPassword()
	{
		return(this.password);
	}
	public String getUserType()
	{
		return(this.userType);
	}
	public String getEmailID()
	{
		return(this.emailID);
	}
	public String getContactNo()
	{
		return(this.contactNo);
	}
	
	public void setUserName(String uname)
	{
		this.userName = uname;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public void setUserType(String userType)
	{
		this.userType = userType;
	}
	public void setEmailID(String email)
	{
		this.emailID = email;
	}
	public void setContactNo(String contactNo)
	{
		this.contactNo = contactNo;
	}
}
