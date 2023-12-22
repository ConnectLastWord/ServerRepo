package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ApplicationService {

    private ServerSocket server;
    private Socket child;
    InputStream is;        //클라이언트와 연결된 입력 스트림 저장
    ObjectInputStream ois; //클라이언트로부터 데이터를 전송받기 위한 스트림

    public ApplicationService() {
        // applicationService 역할 = 클라이언트와 연결되는 소켓관리
        try {
            server = new ServerSocket(8080);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void run() {
        System.out.println("**** 채팅 서버*****");
        System.out.println("서버는 클라이언트 소켓의 접속 요청을 기다리고 있음");

        while (true) {
            try {
                child = server.accept();

            } catch (Exception e) {
                e.printStackTrace();  //에러 메시지를 출력하고
                System.exit(0);       //프로그램을 종료한다.
            }
        }//while 끝
    }

    public String getCommand() {
        try {
            while (true) {
                String messageFromClient = (String) ois.readObject();
                System.out.println("클라이언트로부터 받은 문자열: " + messageFromClient);
                if (messageFromClient.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    }
}
