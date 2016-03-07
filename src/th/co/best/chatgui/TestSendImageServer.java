/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.best.gui;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author BestKung
 */
public class TestSendImageServer {
///////////////////////////////////////  message  ////////////////////////////////////
//    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = new ServerSocket(1111);
//        Socket socket = serverSocket.accept();
//        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
//        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        if (socket.isConnected()) {
//                System.out.println("Client Accept...");
//            }
//        while (true) {
//            String message = input.readLine();
//            System.out.println("Client : " + message);
//        }
//    }

///////////////////////////////////////  file  ////////////////////////////////////
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1111);
        Socket socket = serverSocket.accept();
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        while (true) {
            System.out.print("Input File : ");
            Scanner scanner = new Scanner(System.in);
            String pathFile = scanner.nextLine();
            output.writeBytes(pathFile + "\n");

            File file = new File(pathFile);
            DataInputStream inputFile = new DataInputStream(new FileInputStream(file));

            byte[] buffer = new byte[1024];
            int len = -1;
//            String data
            while ((len = inputFile.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
        }
    }

}
