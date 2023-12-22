package org.example;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RoomManager {
    // RoomManager 역할 = 쓰레드만 관리한다.
    private final static Thread[] tArr = new Thread[5];
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public RoomManager() {
        tArr[0] = new Thread();
        tArr[0].setName("11");
        tArr[1] = new Thread();
        tArr[1].setName("22");
    }

    public static boolean validateThread(String cmd) {
        for (int i = 0; i < tArr.length; i++) {
            if (tArr[i].getName().equals(cmd)) {
                return true;
            }
        }
        return false;
    }

    public void createThread(Socket child) {
        Thread thread = new Thread(new ServiceThread(child));
        thread.start();
    }

    private boolean isEmptyByCommand(String cmd) {
        cmd = cmd.trim();
        for (Thread t : tArr) {
            if (t == null) {
                return false;
            }
            if (t.getName().equals(cmd) || cmd.isEmpty() || cmd.length() == 0) {
                return true;
            }
        }
        return false;
    }
}
