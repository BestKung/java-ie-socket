/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iesocket;

import th.co.best.chatgui.*;
import th.co.best.thread.*;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author BestKung
 */
public class ManageFile {

    public String sendFile(File file, DataOutput send) {
        try {
            DataInputStream inputFile = new DataInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputFile.read(buffer)) != -1) {
                send.write(buffer, 0, len);
            }
            return "Send Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Send Faile";
        }
    }

    public String reseivedFile(File file, DataInputStream inputFile, long fileSize) {
        try {
            DataOutputStream saveFile = new DataOutputStream(new FileOutputStream(file));
            byte[] buffer = new byte[4096];
            int len = -1;
            int size = 0;
            System.out.println("Hello");
            while ((len = inputFile.read(buffer)) != -1) {
                saveFile.write(buffer, 0, len);
                size += len;
                System.out.println(len+"-------->"+size);
                if (size == fileSize) {
                    saveFile.close();
                    break;
                }
            }
            return "Save Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Save Faile";
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
