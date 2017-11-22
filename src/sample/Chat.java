package sample;

import javafx.scene.control.TextArea;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Chat implements Runnable {
    public TextArea message;
    private String name1;
    private String ip1;
    private String port1;

    private InetAddress ipAddress;
    private Socket socket;
    private DataInputStream in;
    String mes;
    Thread thread;

    public Chat(String ip, String port, String name, TextArea textArea1){
        port1 = port;
        name1 = name;
        ip1 = ip;
        message = textArea1;
    }

    public void starting(){
        try {
            ipAddress = InetAddress.getByName(ip1); // создаем объект который отображает вышеописанный IP-адрес.
            socket = new Socket(ipAddress, Integer.parseInt(port1)); // создаем сокет используя IP-адрес и порт сервера.
            OutputStream sout = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(sout);
            InputStream sin = socket.getInputStream();
            in = new DataInputStream(sin);

            //отпраляем имя сервер
            out.writeUTF(name1);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void finish(){
        try {
            socket.close();//закрываем сокет
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        thread = Thread.currentThread();
        starting();
        while(true) {
            System.out.println("wait...");
            if(in!=null) { //если открытый входной поток данных
                System.out.println("read...");
                try {
                    mes = in.readUTF();//считываем сообщение
                    if (!mes.equals("")) {//если сообщение не пустое
                        System.out.println(mes);
                        message.appendText("\n"+mes);//выводим сообщение
                        if(!socket.isConnected()){//если сокет закрытый
                            break;//обрываем цикл
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
            if (thread.isInterrupted()){
                finish();
                break;
            }
        }
    }
}
