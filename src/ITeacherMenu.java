public interface ITeacherMenu {
    void chooseMenuForTeacher();

    Kid findChildNameForTeacher();

    void listAllStudents();

    void addNewStudent();

    void removeStudent();

    void moveStudentToOtherGroup(Kid kid);

    void sendMessageToAllParents();

    void sendMessageToOneDaycareGroup();

    void updateContactInformationDaycareGroup();

    void sendMessageToOneParent();

    void updateStudentContactInformation();

    void updateSchedule(Kid kid);

    void updateAbsence(Kid kid);

    void createNewGroup();
}
