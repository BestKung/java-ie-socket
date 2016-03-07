/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.best.thread;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BestKung
 */
public class ServerChat implements Runnable {

    ServerSocket serverSocket = null;
    Socket socket = null;
    DataOutputStream send = null;
    BufferedReader reseived = null;
    Thread showMessage = null, sendMessage = null;
    String input = "", output = "";
    DataInputStream inputFile = null;
    PrintWriter printWriter;

    public ServerChat(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println(".....................Server Start.......................");
            InetAddress address = InetAddress.getLocalHost();
            System.out.println("Server open on port number : " + serverSocket.getLocalPort() + " Server IP : " + address);
            System.out.println("...................Server is running......................");
            socket = serverSocket.accept();
            inputFile = new DataInputStream(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            if (socket.isConnected()) {
                System.out.println("Client Connect...!!");
            }
            showMessage = new Thread(this);
            sendMessage = new Thread(this);

            showMessage.start();
            sendMessage.start();

        } catch (Exception e) {
        }
    }

    @Override
    public void run() {
        try {
            if (Thread.currentThread() == sendMessage) {
                   while (true) {
                    send = new DataOutputStream(socket.getOutputStream());
                    Scanner scanner = new Scanner(System.in);
                    input = scanner.nextLine();
                    printWriter.println(input);
                    if (input.equalsIgnoreCase("f")) {
                        System.out.print("Upload file : ");
                        String fileName = scanner.nextLine();
                        File file = new File(fileName);
                        printWriter.println(file.length() + "&&" + file.getName());
                        System.out.println(new ManageFile().sendFile(file, send));
                        continue;
                    }
                }
            } else {
                while (true) {
                    reseived = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    output = reseived.readLine();
                    if ((output.charAt(0) == 'f' || output.charAt(0) == 'F') && output.length() == 1) {
                        String fileInformation = reseived.readLine();
                        String spt_file[] = fileInformation.split("&&");
                        File file = new File("F:\\" + spt_file[1]);
                        System.out.println(new ManageFile().reseivedFile(file, inputFile, Long.parseLong(spt_file[0])));

                    } else {
                        System.out.println("Client : " + output);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input port number : ");
        int port = scanner.nextInt();
        ServerChat chat = new ServerChat(port);
    }

}
