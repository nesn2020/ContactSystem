package Trible;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

             Scanner scanner = new Scanner(System.in)) {

            System.out.println("客户端成功连接到服务器");

            while (true) {
                System.out.println();
                System.out.println("发送到服务器的指令: LIST, ADD, UPDATE, DELETE, EXIT");
                System.out.print("输入指令: ");
                String choice = scanner.nextLine();
                output.writeObject(choice);

                if (choice.equalsIgnoreCase("EXIT")) {
                    break;
                } else if (choice.equalsIgnoreCase("LIST")) {
                    List<Contact> contacts = (List<Contact>) input.readObject();
                    displayContacts(contacts);
                } else if (choice.equalsIgnoreCase("ADD")) {
                    Contact contact = new Contact();
                    System.out.print("输入联系人姓名: ");
                    contact.setName(scanner.nextLine());
                    System.out.print("输入联系人地址: ");
                    contact.setAddress(scanner.nextLine());
                    System.out.print("输入联系人电话号码: ");
                    contact.setPhone(scanner.nextLine());
                    output.writeObject(contact);
                    boolean success = (boolean) input.readObject();
                    if (success) {
                        System.out.println("添加成功,添加通讯信息为");
                        System.out.println(contact.toString());
                    } else {
                        System.out.println("添加失败");
                    }
                } else if (choice.equalsIgnoreCase("UPDATE")) {
                    List<Contact> contacts = (List<Contact>) input.readObject();
                    displayContacts(contacts);

                    Contact contact = new Contact();
                    System.out.print("输入想要修改的联系人id: ");
                    int i = scanner.nextInt();
                    contact.setId(i);
                    System.out.print("输入联系人姓名: ");
                    contact.setName(scanner.nextLine());
                    System.out.print("输入联系人地址: ");
                    contact.setAddress(scanner.nextLine());
                    System.out.print("输入联系人电话号码: ");
                    contact.setPhone(scanner.nextLine());

                    output.writeObject(contact);
                    boolean success = (boolean) input.readObject();
                    if (success) {
                        System.out.println("修改成功，新通讯录信息为：");
                        System.out.println(contact.toString());
                    } else {
                        System.out.println("修改失败");
                    }
                } else if (choice.equalsIgnoreCase("DELETE")) {
                    System.out.print("输入要删除的联系人的id: ");
                    int contactId = Integer.parseInt(scanner.nextLine());
                    output.writeObject(contactId);
                    boolean success = (boolean) input.readObject();
                    if (success) {
                        System.out.println("删除成功");
                    } else {
                        System.out.println("删除失败");
                    }
                }
                else{
                    System.out.println("输入不合法请重试");
                    System.out.println();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void displayContacts(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("通讯录为空");
        } else {
            System.out.println("通讯录信息:");
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }
}




