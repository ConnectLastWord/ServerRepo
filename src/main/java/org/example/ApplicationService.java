package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// Service
public class ApplicationService {
    // ApplicationService 객체 역할 = 클라이언트와 연결되는 소켓관리
    private ServerSocket server;
    private Socket child;
    private RoomManager mgr = new RoomManager();

    public ApplicationService() {
        try {
            server = new ServerSocket(8080);
        } catch (Exception e) {    //서버 소켓 생성에 실패하면
            e.printStackTrace();  //에러 메시지를 출력하고
            System.exit(0);       //프로그램을 종료한다.
        }
    }

    public void run() throws IOException {
        System.out.println("**** 채팅 서버*****");
        System.out.println("서버는 클라이언트 소켓의 접속 요청을 기다리고 있음");
        // 생성자를 통해서 우리 프로그램에서 데몬 서버역할
        while (true) {
            try {
                child = server.accept();
                mgr.createThread(child);
            } catch (IllegalArgumentException e) {
                if (e.getMessage().equals("이미 존재하는 채팅방입니다.")) {
                    child.close();
                }
            } catch (Exception e) {
                e.printStackTrace();  //에러 메시지를 출력하고
                System.exit(0);       //프로그램을 종료한다.
            }
        }
    }//while 끝
}
