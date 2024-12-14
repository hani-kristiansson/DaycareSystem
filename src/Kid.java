import java.util.Arrays;

public class Kid {

    private String kidName;
    private DaycareGroup kidDaycareGroup;
    private String[] daycareSchedule = new String[5];
    private String messageFromDaycare;
    private String contactInformation = "";

    public Kid(String kidName, DaycareGroup kidDaycareGroup) {
        this.kidName = kidName.trim();
        this.kidDaycareGroup = kidDaycareGroup;
    }

    public void setKidName(String kidName) {
        this.kidName = kidName;
    }

    public void setKidDaycareGroup(DaycareGroup kidDaycareGroup) {
        this.kidDaycareGroup = kidDaycareGroup;
    }

    public void setDaycareSchedule(String[] daycareSchedule) {
        this.daycareSchedule = daycareSchedule;
    }

    public Kid() {

    }

    public String getKidName() {
        return kidName;
    }

    public DaycareGroup getKidDaycareGroup() {
        return kidDaycareGroup;
    }

    public String[] getDaycareSchedule() {
        return daycareSchedule;
    }

    public void updateDaycareSchedule(String daySchedule) {
        String[] dayAndTime = daySchedule.trim().split(" ");
        switch (dayAndTime[0].trim()) {
            case "Mon":
                daycareSchedule[0] = daySchedule.trim();
                break;
            case "Tue":
                daycareSchedule[1] = daySchedule.trim();
                break;
            case "Wed":
                daycareSchedule[2] = daySchedule.trim();
                break;
            case "Thu":
                daycareSchedule[3] = daySchedule.trim();
                break;
            case "Fri":
                daycareSchedule[4] = daySchedule.trim();
                break;
            default:
                System.out.println("Invalid day format");
                break;
        }
    }

    public String getMessageFromDaycare() {
        return messageFromDaycare;
    }

    public void setMessageFromDaycare(String messageFromDaycare) {
        this.messageFromDaycare = messageFromDaycare;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public void switchKidsDaycareGroup(DaycareGroup daycareGroup) {
        if (daycareGroup != null) {
            System.out.println("Moving kid " + kidName + " from " + kidDaycareGroup.getDaycareGroupName() + " to " + daycareGroup.getDaycareGroupName());
            this.kidDaycareGroup.removeKidFromDaycareGroup(this);
            daycareGroup.addKidToDaycareGroup(this);
            this.kidDaycareGroup = daycareGroup;
        }
    }

    @Override
    public String toString() {
        return kidName + " | " + kidDaycareGroup.getDaycareGroupName() + " | " + String.join(", ", daycareSchedule) + " | " + contactInformation;
    }

    public void display() {
        System.out.println("----------------------");
        System.out.println("Student name "+kidName);
        System.out.println("Student contact information " + contactInformation);
        System.out.println("Student schedule " + String.join(", ", daycareSchedule));
    }
}
