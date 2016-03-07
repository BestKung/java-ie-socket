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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author BestKung
 */
public class TestSendImageClient {
    ///////////////////////////////////////  message  ////////////////////////////////////
//    public static void main(String[] args) throws IOException {
//        Socket socket = new Socket("localhost", 1111);
//        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
//        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        while(true){
//            System.out.print("input : ");
//            Scanner scanner = new Scanner(System.in);
//            String inputMessage = scanner.nextLine();
//            output.writeBytes(inputMessage+"\n");
//        }
//    }

///////////////////////////////////////  file  ////////////////////////////////////
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1111);
        BufferedReader fileName = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataInputStream inputFile = null;
        DataOutputStream saveFile = null;
        while (true) {
            inputFile = new DataInputStream(socket.getInputStream());
            String pathfile = "F:\\" + findName(fileName.readLine());
            System.out.println("FileName : " + pathfile);
            File file = new File(pathfile);
            saveFile = new DataOutputStream(new FileOutputStream(file));
            byte[] buffer = new byte[4096];
            int len = -1;
            while ((len = inputFile.read(buffer)) != 1) {
                saveFile.write(buffer, 0, len);
                System.out.println("index : "+len);
            }
            saveFile.close();
        }
    }

    public static String findName(String fileName) {
        String name = "";
        for (int i = 0; i < fileName.length(); i++) {
            char c = fileName.charAt(i);
            if (c == '\\') {
                name = "";
                continue;
            }
            name += c;
        }
        return name;
    }
}
