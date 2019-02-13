package Doctor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class User {
    private String name;
    private String emailid;
    private String phone;
    private String dob;
    private String password;
    private String userid;
    private String gender;
    private String diagnosiscode;
    
    Connection con;
 	PreparedStatement pstmt;

    public void setDiagnosiscode(String diagnosiscode) {
        this.diagnosiscode = diagnosiscode;
    }
    
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
    public User(String name,String emailid,String phone,String dob,String password,String gender)
    {
        this.name=name;
        this.emailid=emailid;
        this.phone=phone;
        this.dob=dob;
        this.password=password;
        this.gender=gender;
        this.diagnosiscode="";
    }
    public String getName() {
        return name;
    }

    public String getEmailid() {
        return emailid;
    }

    public String getPhone() {
        return phone;
    }

    public String getDob() {
        return dob;
    }

    public String getPassword() {
        return password;
    }

    public String getUserid() {
        return userid;
    }



    public String getGender() {
        return gender;
    }
    public void create_Account()
    {
        //assign user id after updating and retireving form database
    	SQL obj=new SQL("root" ,"","user","doctok");
    	obj.insert_user(name,emailid,phone,dob,password,gender);
    	
    }
    public void showInfo()
    {
    	SQL obj=new SQL("root","","user","doctok");
    	obj.view_user(emailid);

    }
    public void uploadPhoto()
    {}
    public void deleteAccount()
    {
    	SQL obj=new SQL("root","","user","doctok");
    	

    }
    public void updateAccount()
    {
    	SQL obj=new SQL("root","","user","doctok");
    }
    
   


}


