package org.example;

import java.net.Socket;

public class RoomManager {
    // RoomManager 역할 = 쓰레드만 관리한다.
    private static Thread[] tArr = new Thread[5];

    public RoomManager() {
        tArr[0] = new Thread();
        tArr[0].setName("11");
        tArr[1] = new Thread();
        tArr[1].setName("22");
    }

    public static boolean duplicateThread(String cmd) {
        for (int i = 0; i < tArr.length; i++) {
            if (tArr[i] != null) {
                if (tArr[i].getName().equals(cmd)) {
                    throw new IllegalArgumentException("중복된 채팅방 이름입니다.");
                }
            } else {
                return true;
            }
        }
        throw new IllegalArgumentException("더 이상 채팅방 생성이 어렵습니다.");
    }

    public static void addThread(Thread thread) {
        for (int i = 0; i < tArr.length; i++) {
            if (tArr[i] == null) {
                tArr[i] = thread;
                return;
            }
        }
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
