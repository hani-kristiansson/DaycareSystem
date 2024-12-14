import java.io.IOException;
import java.util.Scanner;

public class ParentMenu {

    private final KidDAO kidDAO;
    private final SharedInformation sharedInformation;

    public ParentMenu(SharedInformation sharedInformation) {
        this.kidDAO = KidDAO.getInstance();
        this.sharedInformation = sharedInformation;
    }

    public void chooseMenuForParent() {
        Scanner scanner = new Scanner(System.in);
        String studentName;

        while (true) {
            System.out.println("Type your child full name");
            if (scanner.hasNextLine()) {
                studentName = scanner.nextLine();
                findChildNameForParent(studentName);
            } else {
                break;
            }
        }
    }

    public void findChildNameForParent(String studentName) {
        Scanner scanner = new Scanner(System.in);
        Kid kid = kidDAO.findKid(studentName);

        if (kid == null) {
            System.out.println("No such child found");
            return;
        }

        while (true) {
            System.out.println("""
                    Choose menu.
                    1. see messages
                    2. update schedule
                    3. report absence
                    4. see contact info
                    5. go back to previous menu
                    """);
            if (scanner.hasNextInt()) {
                int option = scanner.nextInt();

                if (option == 1) {
                    System.out.println("You have chosen 'Message from Daycare'");
                    displayMessageForParents(kid);
                }
                else if (option == 2) {
                    System.out.println("You have chosen 'update schedule'. Choose a day you want to update");
                    updateSchedule(kid);
                }
                else if (option == 3) {
                    System.out.println("You have chosen 'report absence'. Choose a day you want to report");
                    updateAbsence(kid);
                }
                else if (option == 4) {
                    System.out.println("You have chosen 'see contact info'.Type Daycare Group to see contact information");
                    showContactInfoOfDaycareGroup(kid);
                } else if (option == 5) {
                    System.out.println("Logging out as parent");
                    break;
                }
            }
        }
    }

    private void displayMessageForParents(Kid kid){
        if(sharedInformation.getMessageToAllParents() != null){
            System.out.println("\nMessage for all parents from the teachers: \n");
            System.out.println(sharedInformation.getMessageToAllParents());
            System.out.println("-------------------------------------\n\n");
        }
        if(kid.getKidDaycareGroup().getGroupMessage()!= null){
            System.out.println("\nMessage for daycare group "+kid.getKidDaycareGroup().getDaycareGroupName()+"\n");
            System.out.println(kid.getKidDaycareGroup().getGroupMessage());
            System.out.println("-------------------------------------\n\n");
        }
        if(kid.getMessageFromDaycare() != null){
            System.out.println("\nMessage for kid "+kid.getKidName()+"\n");
            System.out.println(kid.getMessageFromDaycare());
            System.out.println("-------------------------------------\n\n");
        }
    }

    public void updateSchedule(Kid kid) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose which day you want to update \n 1. Mon, 2. Tue, 3. Wed, 4. Thu, 5. Fri, 6. done updating");
            if (scanner.hasNextLine()) {
                int option = Integer.parseInt(scanner.nextLine());
                //To save kids information directly without asking for time first
                if (option == 6) {
                    System.out.println(kid.toString());
                    kidDAO.saveKidInformationToFile();
                    break;
                }
                System.out.println("Type start time, end time in the format of HH:mm-HH:mm (24h)");
                String startTimeAndEndTime = scanner.nextLine();

                if (option == 1) {
                    kid.updateDaycareSchedule("Mon " + startTimeAndEndTime);
                } else if (option == 2) {
                    kid.updateDaycareSchedule("Tue " + startTimeAndEndTime);
                } else if (option == 3) {
                    kid.updateDaycareSchedule("Wed " + startTimeAndEndTime);
                } else if (option == 4) {
                    kid.updateDaycareSchedule("Thu " + startTimeAndEndTime);
                } else if (option == 5) {
                    kid.updateDaycareSchedule("Fri " + startTimeAndEndTime);
                } else {
                    System.out.println("Invalid option");
                }
            }
        }
    }

    public void updateAbsence(Kid kid) {
        Scanner scanner = new Scanner(System.in);
        String absence = "absence";

        while (true) {
            System.out.println("Choose which day you will be absent \n 1. Mon, 2. Tue, 3. Wed, 4. Thu, 5. Fri, 6. done updating");
            if (scanner.hasNextLine()) {
                int option = Integer.parseInt(scanner.nextLine());
                //To save kids information directly without asking for time first
                if (option == 6) {
                    System.out.println(kid.toString());
                    kidDAO.saveKidInformationToFile();
                    break;
                }
                if (option == 1) {
                    kid.updateDaycareSchedule("Mon " + absence);
                } else if (option == 2) {
                    kid.updateDaycareSchedule("Tue " + absence);
                } else if (option == 3) {
                    kid.updateDaycareSchedule("Wed " + absence);
                } else if (option == 4) {
                    kid.updateDaycareSchedule("Thu " + absence);
                } else if (option == 5) {
                    kid.updateDaycareSchedule("Fri " + absence);
                } else {
                    System.out.println("Invalid option");
                }
            }
        }
    }

    public void showContactInfoOfDaycareGroup(Kid kid) {
        System.out.println("Contact information for student daycare group");
        System.out.println(kid.getKidDaycareGroup().getContactInformation()+"\n\n");
    }
}