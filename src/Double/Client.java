package Double;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Scanner sc = new Scanner(System.in);
            // 用法示例：添加联系人
            while (true){
                System.out.println("请输入发送到服务器的指令(ADD,List,Query,Delete,Update,Exit):");
                String order = sc.nextLine();
                out.println(order);

                String response = in.readLine();
                System.out.println("服务器响应: " + response);
                System.out.println();
                if (order.equalsIgnoreCase("Exit")){
                    break;
                }
            }

            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

