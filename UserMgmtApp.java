
package com.example;

import com.example.dao.*;
import com.example.model.User;
import java.util.*;
public class UserMgmtApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserDAO userDAO = new UserDAOImpl();
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== User Management System =====");
            System.out.println("1. Add User");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.println("4. Get User By ID");
            System.out.println("5. Get All Users");
            System.out.println("6. Search By Name");
            System.out.println("7. Search By Email");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> addUser();
                case 2 -> updateUser();
                case 3 -> deleteUser();
                case 4 -> getUserById();
                case 5 -> getAllUsers();
                case 6 -> searchByName();
                case 7 -> searchByEmail();
                case 8 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
    private static void addUser() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        userDAO.addUser(new User(name, email, age));
    }
    private static void updateUser() {
        System.out.print("Enter user ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter new name: ");
        String name = sc.nextLine();
        System.out.print("Enter new email: ");
        String email = sc.nextLine();
        System.out.print("Enter new age: ");
        int age = sc.nextInt();
        userDAO.updateUser(new User(id, name, email, age));
    }
    private static void deleteUser() {
        System.out.print("Enter user ID: ");
        int id = sc.nextInt();
        userDAO.deleteUser(id);
    }
    private static void getUserById() {
        System.out.print("Enter user ID: ");
        int id = sc.nextInt();
        User user = userDAO.getUserById(id);
        System.out.println(user != null ? user : "User not found!");
    }
    private static void getAllUsers() {
        List<User> list = userDAO.getAllUsers();
        list.forEach(System.out::println);
    }
    private static void searchByName() {
        System.out.print("Enter name keyword: ");
        String name = sc.nextLine();
        List<User> list = userDAO.searchByName(name);
        list.forEach(System.out::println);
    }
    private static void searchByEmail() {
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        User user = userDAO.searchByEmail(email);
        System.out.println(user != null ? user : "User not found!");
    }
}
