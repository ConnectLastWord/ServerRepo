package org.example;

public class RoomManager {
    // RoomManager 역할 = 쓰레드만 관리한다.
    private final static Thread[] tArr = new Thread[5];

    public boolean checkRoomName(String roomName) {
        for (int i = 0; i < 5; i++) {
            if (tArr[i].getName().equals(roomName)) {
                return true;
            }
        }
        return false;
    }

    public void createThread(String roomName) {
        Room room = null;
        if (!checkRoomName(roomName)) {
            room = new RoomImpl();
            for (int i = 0; i < tArr.length; i++) {
                if (tArr[i] == null) {
                    tArr[i] = new Thread(room);
                    return;
                }
            }
        }
    }
}
