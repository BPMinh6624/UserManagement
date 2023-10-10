/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.usermanagement.DataAccess;

import com.mycompany.usermanagement.Common.Library;
import com.mycompany.usermanagement.Common.Validation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author tuanh
 */
public class UserDAO {
    private static UserDAO instance = null;
   
    Library l;
    Validation valid;
    public UserDAO() {
        l = new Library();
       
    }

    public static UserDAO Instance() {
        if (instance == null) {
            synchronized (UserDAO.class) {
                if (instance == null) {
                    instance = new UserDAO();
                }
            }
        }
        return instance;
    }

    public void createNewAccount() {
        if (!valid.checkFileExist()) {
            System.out.println("File is not exist!");
            return;
        }
        String username = l.inputString("Enter Username: ");
        while (!valid.checkInputUsername(username)) {
            username = l.inputString("Enter username again: ");
        }
        String password = l.inputString("Enter password: ");
        while (!valid.checkInputPassword(password)) {
            password = l.inputString("Enter password again: ");
        }
        addAccountData(username, password);

    }

    public void loginSystem() {
        if (!valid.checkFileExist()) {
            System.out.println("File is not exist!");
            return;
        }
        String username = l.inputString("Enter Username: ");
        while (!valid.checkInputUsernameLogin(username)) {
            username = l.inputString("Enter Username: ");
        }
        String password = l.inputString("Enter Password: ");
        while (!valid.checkInputPassword(password)) {
            password = l.inputString("Enter Password again: ");
        }

        if (!valid.checkUsernameExist(username)) {
            if (!password.equalsIgnoreCase(passwordByUsername(username))) {
                System.err.println("Username or Password is incorrect!!!");
            } else {
                System.out.println("Login successful!");
            }

        } else {
            System.err.println("Username or Password is incorrect!!!");
        }
    }

    public void addAccountData(String username, String password) {
        File file = new File("src\\user.dat");
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(username + ";" + password + "\n");
            fileWriter.close();
            System.err.println("Create successful!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String passwordByUsername(String username) {
        File file = new File("src\\user.dat");
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] account = line.split(";");
                if (username.equalsIgnoreCase(account[0])) {
                    return account[1];
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
