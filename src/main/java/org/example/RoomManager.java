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

    public static void validateEmpty(String cmd){
        if (cmd.trim().isEmpty()){
            throw new IllegalArgumentException("방이름은 공백이 불가능합니다.");
        }
    }

    public void createThread(Socket child) {
        Thread thread = new Thread(new ServiceThread(child));
        thread.start();
    }
}
