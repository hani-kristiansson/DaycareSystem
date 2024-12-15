public interface IParentMenu {
    void chooseMenuForParent();

    void findChildNameForParent(String studentName);

    void updateSchedule(Kid kid);

    void updateAbsence(Kid kid);

    void showContactInfoOfDaycareGroup(Kid kid);
}
