package Trible;

import java.io.*;
import java.net.*;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("服务器正在等待客户端连接");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("成功与客户端连接");
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;
    private DatabaseServer databaseServer;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        databaseServer = new DatabaseServer();
    }

    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {
            while (true) {
                String request = (String) input.readObject();
                if (request.equalsIgnoreCase("EXIT")) {
                    break;
                } else if (request.equalsIgnoreCase("LIST")) {
                    // 查询联系人列表
                    List<Contact> contacts = databaseServer.getContacts();
                    output.writeObject(contacts);
                } else if (request.equalsIgnoreCase("ADD")) {
                    // 添加联系人
                    Contact contact = (Contact) input.readObject();
                    boolean success = databaseServer.addContact(contact);
                    output.writeObject(success);
                } else if (request.equalsIgnoreCase("UPDATE")) {
                    // 更新联系人
                    List<Contact> contacts = databaseServer.getContacts();
                    output.writeObject(contacts);
                    Contact contact = (Contact) input.readObject();
                    boolean success = databaseServer.updateContact(contact);
                    output.writeObject(success);
                } else if (request.equalsIgnoreCase("DELETE")) {
                    // 删除联系人
                    int contactId = (int) input.readObject();
                    boolean success = databaseServer.deleteContact(contactId);
                    output.writeObject(success);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}





