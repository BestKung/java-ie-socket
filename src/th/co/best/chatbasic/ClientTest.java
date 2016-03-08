/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.co.best.chatbasic;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author BestKung
 */
public class ClientTest {

    
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.1.101", 9091);
        String modifierSentence = null;
        BufferedReader bufferedReader = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            while (true) {
                String fileName = "";
                System.out.println("Upload File.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Input Path File : ");
                fileName = scanner.nextLine();
                System.out.println(fileName);
                dataOutputStream.writeBytes(fileName + "\n");
                modifierSentence = bufferedReader.readLine();
                System.out.println("From Server" + modifierSentence);
                File file = new File(fileName);
                System.out.println(file);
                dataInputStream = new DataInputStream(new FileInputStream(file));
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = dataInputStream.read(buffer)) != -1) {
                    dataOutputStream.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
