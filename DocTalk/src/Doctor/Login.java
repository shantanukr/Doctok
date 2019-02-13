package Doctor;

public class Login {


    private String emailid;
    private String password;
    public Login(String emailid,String password)
    {
        this.emailid=emailid;
        this.password=password;
    }
    public String getEmailid() {
        return emailid;
    }

    public String getPassword() {
        return password;
    }
    public void validate()
    {
     //valid
        respond_valid();
        //not valid
        respond_invalid();
    }
    public void respond_valid()
    {

    }
    public void respond_invalid()
    {

    }
    public void keeploggedin()
    {

    }
    public void logout()
    {

    }
}

