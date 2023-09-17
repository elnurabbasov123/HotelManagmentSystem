package util;

public class MenuUtil {
    public static int entryMenu() {
        int option=InputUtil.getInstance().inputInt(
                "----|Hotel Managment System |----\n"+
                        "[0]->Exit\n"+
                        "[1]->Rezervation\n"+
                        "[2]->Show\n"+
                        "[3]->Search\n" +
                        "[4]->Update\n" +
                        "[5]->Cancel\n" +
                        "Please enter the option:"
        );
        return option;
    }
}
