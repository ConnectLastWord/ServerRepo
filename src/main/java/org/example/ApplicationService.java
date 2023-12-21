package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

// Service
public class ApplicationService {
    // ApplicationService 객체 역할 = 클라이언트와 연결되는 소켓관리
    private ServerSocket server;
    private Socket child;
    private RoomManager mgr = new RoomManager();
    InputStream is;        //클라이언트와 연결된 입력 스트림 저장
    InputStreamReader isr; //클라이언트로부터 데이터를 전송받기 위한 스트림
    ObjectInputStream ois;
    String receiveData;


    public ApplicationService() {
        try {
            //1. 에코 서버 프로그램은 포트를 지정해서 서버 소쳇 생성부터 한다.
            server = new ServerSocket(8080);
        } catch (Exception e) {    //서버 소켓 생성에 실패하면
            e.printStackTrace();  //에러 메시지를 출력하고
            System.exit(0);       //프로그램을 종료한다.
        }
    }

    public void run() {
        System.out.println("**** 채팅 서버*****");
        System.out.println("서버는 클라이언트 소켓의 접속 요청을 기다리고 있음");
        // 생성자를 통해서 우리 프로그램에서 데몬 서버역할
        while (true) {
            try {
                child = server.accept();
                System.out.println(child.getInetAddress() + "로 부터 연결요청 받음");

                mgr.createThread(getCommand());
            } catch (Exception e) {
                e.printStackTrace();  //에러 메시지를 출력하고
                System.exit(0);       //프로그램을 종료한다.
            }
        }//while 끝
    }

    public String getCommand() {
        try {
            is = child.getInputStream();
            ois = new ObjectInputStream(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
