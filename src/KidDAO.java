import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class KidDAO {

    private static KidDAO instance;
    private List<DaycareGroup> daycareGroups = new ArrayList<DaycareGroup>();
    private List<Kid> kids = new ArrayList<>();

    public static KidDAO getInstance() {
        if (instance == null) {
            instance = new KidDAO();
        }
        return instance;
    }

    private KidDAO() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/DaycareGroups"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] groupData = line.split("\\|");
                if(groupData.length == 2) {
                    DaycareGroup daycareGroup = new DaycareGroup(groupData[0].trim());
                    daycareGroup.setContactInformation(groupData[1].trim());
                    daycareGroups.add(daycareGroup);
                }
            }
        } catch (IOException e) {
            System.out.println("No save daycare groups in file");
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/KidsInformation"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                String[] data = line.split("\\|");
                String daycareGroupName = data[1].trim();
                DaycareGroup daycareGroup = findDaycareGroup(daycareGroupName);

                if (daycareGroup == null) {
                    daycareGroup = new DaycareGroup(daycareGroupName);
                    daycareGroups.add(daycareGroup);
                }
                Kid tempKid = new Kid(data[0], daycareGroup);
                daycareGroup.addKidToDaycareGroup(tempKid);
                String[] dayCareSchedule = data[2].split(",");
                for (String daySchedule : dayCareSchedule) {
                    tempKid.updateDaycareSchedule(daySchedule);
                }
                if(data.length > 3) {
                    tempKid.setContactInformation(data[3]);
                }
                kids.add(tempKid);
            }
            saveDayCareGroupToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DaycareGroup> getDaycareGroups() {
        return daycareGroups;
    }

    public DaycareGroup findDaycareGroup(String daycareGroupName) {
        for (DaycareGroup dcg : daycareGroups) {
            if (dcg.getDaycareGroupName().equals(daycareGroupName)) {
                return dcg;
            }
        }
        return null;
    }

    public Kid findKid(String name) {
        for (Kid kid : kids) {
            if (kid.getKidName().equals(name)) {
                return kid;
            }
        }
        return null;
    }

    public void saveKidInformationToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/KidsInformation"))) {
            for (Kid kid : kids) {
                bufferedWriter.write(kid.toString() + "\n");
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void saveDayCareGroupToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/DaycareGroups"))) {
            for (DaycareGroup daycareGroup : daycareGroups) {
                bufferedWriter.write(daycareGroup.toString() + "\n");
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveNewKid(Kid kid) {
        kids.add(kid);
        saveKidInformationToFile();
    }

    public void removeKid(Kid kid){
        kids.remove(kid);
        saveKidInformationToFile();
        DaycareGroup daycareGroup = kid.getKidDaycareGroup();
        daycareGroup.removeKidFromDaycareGroup(kid);
    }

    public void addDaycareGroup(DaycareGroup daycareGroup) {
        daycareGroups.add(daycareGroup);
        saveDayCareGroupToFile();
    }
}
