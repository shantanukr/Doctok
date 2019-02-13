package Doctor;


public class Notification {
    private User user;
    private String[] notificationMessage;
    public Notification(User user)
    {
        this.user=user;
    }
    public void generate_NotificationMessage()
    {
        Diagnosis d=new Diagnosis(user);
        String code=d.getCurrentDiagnosisCode();
        //check for notificationtype and save the notification message in the variable
    }
    public void send_Notification()
    {

    }
    public void updateNotificationTab()
    {

    }

    public void showNotification()
    {

    }
}

