package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class RoomImpl implements Runnable, Room {
    private static Socket child;          //클라이언트와 통신하기 위한 소켓
    InputStream is;        //클라이언트와 연결된 입력 스트림 저장
    InputStreamReader isr; //클라이언트로부터 데이터를 전송받기 위한 스트림
    ObjectInputStream ois;
    String receiveData;    //클라이언트로부터 전송받은 데이터를 저장하기 위한 변수

    @Override
    public void run() {
        while (true) {
            try {
                receiveData = String.valueOf(ois.readObject());
                System.out.println(receiveData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
