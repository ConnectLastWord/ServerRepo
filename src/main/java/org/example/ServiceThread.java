package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServiceThread implements Runnable {
    Socket child;          //클라이언트와 통신하기 위한 소켓

    InputStream is;        //클라이언트와 연결된 입력 스트림 저장
    ObjectInputStream ois; //클라이언트로부터 데이터를 전송받기 위한 스트림
    String receiveData;    //클라이언트로부터 전송받은 데이터를 저장하기 위한 변수

    public ServiceThread(Socket s) {
        child = s;
        try {
            System.out.println(child.getInetAddress() + "로 부터 연결요청 받음");
            is = child.getInputStream();
            ois = new ObjectInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectInputStream getOis() {
        return this.ois;
    }

    @Override
    public void run() {
        try {
            while (true) {
                receiveData = (String) ois.readObject();
                System.out.println(child.getInetAddress() + "의 메시지:" + receiveData);
                RoomManager.validateEmpty(receiveData);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("클라이언트가 강제 종료");
        } finally {
            try {
                is.close();
                ois.close();
                child.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void isEmptyByCommand() {
    }
}
