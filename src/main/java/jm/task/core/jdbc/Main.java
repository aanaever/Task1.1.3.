package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl user = new UserServiceImpl();
        user.createUsersTable();
        user.saveUser("Madara", "Uchiha", (byte) 112);
        user.saveUser("Itachi", "Uchiha", (byte) 21);
        user.saveUser("Sasuke", "Uchiha", (byte) 16);
        user.saveUser("Sarada", "Uchiha", (byte) 12);
        System.out.println(user.getAllUsers());
        user.cleanUsersTable();
        user.dropUsersTable();
    }
}
