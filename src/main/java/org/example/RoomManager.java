package org.example;

public class RoomManager {
    // RoomManager 역할 = 쓰레드만 관리한다.
    private static Thread[] tArr = new Thread[5];

    public static boolean duplicateThread(String cmd) {
        for (int i = 0; i < tArr.length; i++) {
            System.out.println(i);
            if (tArr[i] != null) {
                System.out.println(tArr[i].getName() + " / " + cmd);
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
}
