package Double;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static List<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("服务器已启动，等待客户端连接...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("客户端已连接");

                ClientHandler handler = new ClientHandler(clientSocket);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String response = processRequest(inputLine);
                    out.println(response);
                }

                out.close();
                in.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String processRequest(String request) {
            String[] parts = request.split(",");
            String command = parts[0];

            if ("ADD".equalsIgnoreCase((command))){
                String name = parts[1];
                String address = parts[2];
                String phoneNumber = parts[3];
                Contact contact = new Contact(name, address, phoneNumber);
                contacts.add(contact);
                StringBuilder response = new StringBuilder();
                response.append("添加通讯录成功，添加信息为:  ").append(contact.toString());
                return response.toString();
            } else if ("LIST".equalsIgnoreCase(command)) {
                StringBuilder response = new StringBuilder();
                for (Contact contact : contacts) {
                    response.append(contact.toString()).append("  ,  ");
                }
                if (response.length() > 0) {
                    response.setLength(response.length() - 5);
                }
                return response.toString();
            } else if("Delete".equalsIgnoreCase(command)){
                String deleteName = parts[1];
                for (Contact contact : contacts) {
                    if (contact.getName().equalsIgnoreCase(deleteName)) {
                        contacts.remove(contact);
                        return "删除姓名为" + deleteName + "的通讯录成功";
                    }
                }
                return "通讯录中查无此人，请检查后再尝试删除";
            } else if ("Query".equalsIgnoreCase(command)){
                String queryName = parts[1];
                for(Contact contact : contacts){
                    if(contact.getName().equalsIgnoreCase(queryName)){
                        return "查询成功： " + contact.toString();
                    }
                }
                return "通讯录查无此人，请检查后再尝试查询";
            } else if("Update".equalsIgnoreCase(command)){
                String updateName = parts[1];
                String newAddress = parts[2];
                String newPhoneNumber = parts[3];
                for (Contact contact : contacts) {
                    if (contact.getName().equalsIgnoreCase(updateName)) {
                        contact.setAddress(newAddress);
                        contact.setPhoneNumber(newPhoneNumber);
                        return "通讯录修改成功：" + contact.toString();
                    }
                }
                return "通讯录中查无此人，请检查后再尝试修改";
            }else if("Exit".equalsIgnoreCase(command)){
                return "退出成功,感谢使用";
            }
            else {
                return "非法请求";
            }
        }
    }
}



