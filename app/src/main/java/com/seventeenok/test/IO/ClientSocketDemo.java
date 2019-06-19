package com.seventeenok.test.IO;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketDemo {
    public void test() throws Exception {
        Socket socket = new Socket("localhost", 10086);
        //获取输出流 向服务端发送数据
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.write("用户名  ： lizhi");
        printWriter.flush();
        socket.shutdownOutput();
        InputStream is = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String data = "";
        if ((data = reader.readLine()) != null) {
            System.out.println("我是客户端 我说：" + data);
        }
        reader.close();
        is.close();
        printWriter.close();
        outputStream.close();
        socket.close();
    }
}
