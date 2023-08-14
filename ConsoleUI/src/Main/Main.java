package Main;

import menu.api.Menu;
import menu.impl.MenuImpl;

public class Main {
    public static void main(String[] args) {
        Menu menu = new MenuImpl();

        menu.show();
    }
}
