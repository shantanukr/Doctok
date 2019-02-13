package Doctor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Signup {
     String name;
     String emailid;
     String phone;
     String dob;
     String password;
     String cPassword;
     String gender;
     
     Connection con;
 	PreparedStatement pstmt;
 	
 	public void getCon()
    {
        try
        {
        	Class.forName("com.mysql.jdbc.Driver");
        	con=DriverManager.getConnection("jdbc:mysql://localhost:3306/doctok","root","");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
     
    public void getDetails()
    {
        if(check_exist( emailid))
        {
        	failure();
        }
        else
        {
        	success();
        }
    }
    public void success()
    {
        User obj=new User(name,emailid,phone,dob,password,gender);
        obj.create_Account();
    }
    public void failure()
    {
        String s="user already exixt";
    }
    public boolean check_exist( String emailid)
    {
    	int count=0;
        getCon();
        try
	       {
	               pstmt = con.prepareStatement("select * from patient where email_id= ?");
	               pstmt.setString(1,emailid);
	               ResultSet rs=pstmt.executeQuery();
	               while(rs.next())
	               {
	            	   count++;
	               }
	       }catch(Exception e)
	       {
	           System.out.println(e);
	       }
        if(count==1)
			return true;
		else
			return false;
    	
    }
}

