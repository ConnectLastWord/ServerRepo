package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServiceThread implements Runnable {
    Socket child;          //클라이언트와 통신하기 위한 소켓
    InputStream is;        //클라이언트와 연결된 입력 스트림 저장
    ObjectInputStream ois; //클라이언트로부터 데이터를 전송받기 위한 스트림
    OutputStream os;
    ObjectOutputStream oos;
    String receiveData;    //클라이언트로부터 전송받은 데이터를 저장하기 위한 변수

    public ServiceThread(Socket s) {
        child = s;
        try {
            System.out.println(child.getInetAddress() + "로 부터 연결요청 받음");
            is = child.getInputStream();
            ois = new ObjectInputStream(is);
            os = child.getOutputStream();
            oos = new ObjectOutputStream(os);
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
                try {
                    RoomManager.duplicateThread(receiveData);

                } catch (IllegalArgumentException e) {
                    oos.writeObject(e.getMessage());
                    oos.flush();
                    continue;
                }
                Thread.currentThread().setName(receiveData);
                RoomManager.addThread(Thread.currentThread());
                oos.writeObject(receiveData + "채팅방이 생성되었습니다.");
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("클라이언트가 강제 종료");
        } finally {
            try {
                is.close();
                ois.close();
                os.close();
                oos.close();
                child.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void isEmptyByCommand() {
    }
}
