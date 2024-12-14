import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainMenu {

    //private final KidDAO kidDAO = new KidDAO();
    private final TeacherMenu teacherMenu;
    private final ParentMenu parentMenu;
    private final SharedInformation sharedInformation = new SharedInformation();

    public MainMenu() throws FileNotFoundException {
        teacherMenu = new TeacherMenu(sharedInformation);
        parentMenu = new ParentMenu(sharedInformation);
        System.out.println("Welcome to Daycare Rainbow.");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Please choose whether you are a teacher or a parent.\n 1. Teacher  2. Parent");
            if (scanner.hasNextInt()) {
                int temp = scanner.nextInt();
                if (temp == 1) {
                    teacherMenu.chooseMenuForTeacher();
                }
                if (temp == 2) {
                    parentMenu.chooseMenuForParent();
                }
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        new MainMenu();
    }

}
