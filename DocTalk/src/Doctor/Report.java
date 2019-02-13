package Doctor;


public class Report
{
    private User user;
    public Report(User user)
    {
        this.user=user;
    }
    public boolean show_report()
    {
        //show the report to the user
        //or false
        return true;
    }
    public void generate_report()
    {
        validate_report();
    }
    public void validate_report()
    {if(show_report())
    {
        send_report(user.getEmailid());
    }
    else
    {
        //check and resolve error
        generate_report();
    }

    }
    public void send_report(String emailid)
    {

    }

}

