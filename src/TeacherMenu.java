import java.util.List;
import java.util.Scanner;

public class TeacherMenu {
    private final KidDAO kidDAO;
    private final SharedInformation sharedInformation;

    public TeacherMenu(KidDAO kidDAO, SharedInformation sharedInformation) {
        this.kidDAO = kidDAO;
        this.sharedInformation = sharedInformation;
    }

    private final String optionsMenyForTeachers = """
            
            Make you choice
            1. Add new student
            2. Remove current student
            3. Send message to all parents
            4. Send message to one daycare group
            5. Send message to one students parent
            6. Update student schedule
            7. Report student absence
            8. Update student contact information
            9. Move student to other group
            10. Update general information
            11. Update contact information for daycare group
            12. List all students
            13. Create new daycare group
            14. Go back to previous menu
            
            """;

    public void chooseMenuForTeacher() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {

            System.out.println(optionsMenyForTeachers);

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 1) {
                    addNewStudent();
                } else if (choice == 2) {
                    removeStudent();
                } else if (choice == 3) {
                    sendMessageToAllParents();
                } else if (choice == 4) {
                    sendMessageToOneDaycareGroup();
                } else if (choice == 5) {
                    sendMessageToOneParent();
                } else if (choice == 6) {
                    Kid kid = findChildNameForTeacher();
                    updateSchedule(kid);
                } else if (choice == 7) {
                    Kid kid = findChildNameForTeacher();
                    updateAbsence(kid);
                } else if (choice == 8) {
                    updateStudentContactInformation();
                } else if (choice == 9) {
                    Kid kid = findChildNameForTeacher();
                    moveStudentToOtherGroup(kid);
                } else if (choice == 10) {
                    updateGeneralInformation();
                } else if (choice == 11) {
                    updateContactInformationDaycareGroup();
                } else if (choice == 12) {
                    listAllStudents();
                } else if (choice == 13) {
                    createNewGroup();
                } else if (choice == 14) {
                    break;
                } else {
                    System.out.println("Invalid choice");
                }
            }
        }
    }

    private void updateGeneralInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the new general information");
        String generalInformation = scanner.nextLine();
        if (generalInformation != null) {
            sharedInformation.setDaycareInformation(generalInformation);
        }
    }

    public Kid findChildNameForTeacher() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the student");
        while (true) {
            String kidName = scanner.nextLine();
            if (kidName == null) {
                return null;
            }
            Kid kid = kidDAO.findKid(kidName);

            if (kid == null) {
                System.out.println("Can not find kid in the system");
            } else {
                return kid;
            }
        }
    }

    public void listAllStudents() {
        System.out.println("Listing all groups and student. \n");
        List<DaycareGroup> daycareGroupList = kidDAO.getDaycareGroups();

        for (DaycareGroup daycareGroup : daycareGroupList) {
            System.out.println("+++++++++++++++++++");
            daycareGroup.display();
            System.out.println();
            for (Kid kid : daycareGroup.getKids()) {
                kid.display();
            }
            System.out.println();
        }
    }

    public void addNewStudent() {
        System.out.println("Enter the full name of the new student");
        Scanner scanner = new Scanner(System.in);
        String kidName = scanner.nextLine();

        Kid kid = new Kid();
        kid.setKidName(kidName);

        System.out.println("Enter the name of daycare group");
        String daycareGroupName = scanner.nextLine();
        DaycareGroup daycareGroup = kidDAO.findDaycareGroup(daycareGroupName);
        kid.setKidDaycareGroup(daycareGroup);

        System.out.println("Confirm schedule of new student");
        Boolean confirmScheduleOfNewKid = scanner.nextBoolean();
        if (confirmScheduleOfNewKid) {
            String[] fixedScheduleForNewKid = {"Mon 09:00-13:00", "Tue 09:00-13:00", "Wed 09:00-13:00", "Thu 09:00-13:00", "Fri 09:00-13:00"};
            kid.setDaycareSchedule(fixedScheduleForNewKid);
        }
        daycareGroup.addKidToDaycareGroup(kid);
        kidDAO.saveNewKid(kid);
    }

    public void removeStudent() {
        Kid kid = findChildNameForTeacher();
        if (kid != null) {
            kidDAO.removeKid(kid);
        }
    }

    public void moveStudentToOtherGroup(Kid kid) {
        System.out.println("Please write which daycare group you want to move the student");
        Scanner scanner = new Scanner(System.in);
        String daycareGroupName = scanner.nextLine();
        if (daycareGroupName != null) {
            DaycareGroup daycareGroup = kidDAO.findDaycareGroup(daycareGroupName);
            kid.switchKidsDaycareGroup(daycareGroup);
        }
    }

    public void sendMessageToAllParents() {
        System.out.println("Please write message to share to all parents");
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        if (message != null) {
            sharedInformation.setMessageToAllParents(message);
            System.out.println("Message sent to all parents");
        }
    }

    public void sendMessageToOneDaycareGroup() {
        System.out.println("Please write which daycare group you want to send message");
        Scanner scanner = new Scanner(System.in);
        String daycareGroupName = scanner.nextLine();
        if (daycareGroupName == null) {
            System.out.println("Could not find daycare group");
        } else {
            DaycareGroup daycareGroup = kidDAO.findDaycareGroup(daycareGroupName);
            if (daycareGroup != null) {
                System.out.println("Please write message to the group");
                String message = scanner.nextLine();
                daycareGroup.updateGroupMessage(message);
                System.out.println("Message sent");
            }
        }
    }

    public void updateContactInformationDaycareGroup() {
        System.out.println("Please write which daycare group you want to update contact details");
        Scanner scanner = new Scanner(System.in);
        String daycareGroupName = scanner.nextLine();
        if (daycareGroupName == null) {
            System.out.println("Could not find daycare group");
        } else {
            DaycareGroup daycareGroup = kidDAO.findDaycareGroup(daycareGroupName);
            if (daycareGroup != null) {
                System.out.println("Please write the new contact information for " + daycareGroupName);
                String contactInformation = scanner.nextLine();
                daycareGroup.setContactInformation(contactInformation);
                kidDAO.saveDayCareGroupToFile();
                System.out.println("Information updated");
            }
        }
    }

    public void sendMessageToOneParent() {
        Scanner scanner = new Scanner(System.in);
        Kid kid = findChildNameForTeacher();

        System.out.println("Please write the message to the parents of " + kid.getKidName());
        String message = scanner.nextLine();
        if (message != null) {
            kid.setMessageFromDaycare(message);
            System.out.println("Message sent");
        }

    }

    public void updateStudentContactInformation() {
        Scanner scanner = new Scanner(System.in);
        Kid kid = findChildNameForTeacher();

        System.out.println("Please write the new contact information for " + kid.getKidName());
        String message = scanner.nextLine();
        if (message != null) {
            kid.setMessageFromDaycare(message);
            System.out.println("Message sent");
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

    public void createNewGroup() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type a name of new group");
        String newGroupName = scanner.nextLine();
        System.out.println("Type a contact info of a new group");
        String contactInformation = scanner.nextLine();

        DaycareGroup daycareGroup = new DaycareGroup(newGroupName);
        daycareGroup.setContactInformation(contactInformation);
        kidDAO.addDaycareGroup(daycareGroup);
    }
}
