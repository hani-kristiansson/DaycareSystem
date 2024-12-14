import java.util.ArrayList;
import java.util.List;

public class DaycareGroup {

    private String daycareGroupName;
    private List<Kid> kids;
    private String groupMessage;
    private String contactInformation = "";

    public DaycareGroup(String daycareGroupName) {
        this.daycareGroupName = daycareGroupName.trim();
        kids = new ArrayList<>();
    }

    public String getDaycareGroupName() {
        return daycareGroupName;
    }

    public void addKidToDaycareGroup(Kid kid){
        kids.add(kid);
    }

    public void removeKidFromDaycareGroup(Kid kid){
        kids.remove(kid);
    }

    public void updateGroupMessage(String message){
        groupMessage = message;
    }

    public String getGroupMessage() {
        return groupMessage;
    }

    public List<Kid> getKids() {
        return kids;
    }

    public void display() {
        System.out.println("Daycare group "+daycareGroupName);

    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    @Override
    public String toString() {
        return daycareGroupName + " | " + contactInformation;
    }
}
