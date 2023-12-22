package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MyClient {

    String ipAddress = "172.16.0.64";
    public int portNum = 5000;

    Socket conn = null;

    BufferedReader br = null;
    // 직렬화
    OutputStream os;
    ObjectOutputStream oos;// 내보내기전 쪼개기 작업을 수행해줌
    // 역직렬화
    String receiveData;
    InputStream is;
    ObjectInputStream ois;

    // 생성자
    public MyClient() throws IOException {
        System.out.println("***클라이언트 실행***");

        try {
            conn = new Socket(ipAddress, portNum);// 1. 이때 Connection 요청을 함 - session(서버와 클라이언트의 연결)
            // ex) 동접자 100명-> session 100개

            br = new BufferedReader(new InputStreamReader(System.in));

            // 2. 키보드로부터 전송시킬 데이터를 입력받아 놓는 것
            System.out.println("입력---> ");

            // 3. 데이터 전송
            String sendData = "";// Object
            os = conn.getOutputStream();
            oos = new ObjectOutputStream(os);// 보조스트림

            while ((sendData = br.readLine()) != null) {
                System.out.println(sendData);
                oos.writeObject(sendData);// 직렬화
                // 출력버퍼(너무 크면 이곳저곳에 부딪쳐서 손실이 있어날 수 있음)
                oos.flush();
                if (sendData.equals("quit")) {
                    break;
                }
            }

            // 4. 에코서버에서 전송받은 메세지를 출력
            is = conn.getInputStream();
            ois = new ObjectInputStream(is); // 바이트 --> Object
            receiveData = (String) ois.readObject();
            System.out.println(conn.getInetAddress() + "로부터 받은 메세지: " + receiveData);

            // 5.커넥션 해제
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("문자열로 변환할 수 없습니다.");
        } finally {

            if (br != null) {
                br.close();
            }
            if (oos != null) {
                oos.close();
            }
            if (os != null) {
                os.close();
            }
            if (ois != null) {
                ois.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }
}