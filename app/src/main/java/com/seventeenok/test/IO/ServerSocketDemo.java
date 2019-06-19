package com.seventeenok.test.IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketDemo {
    public void test() throws IOException {
        /*
         * 基于TCP协议的Socket通信，实现用户登录，服务端
         * */
        //1.创建服务器ServerSocket，指定绑定的端口，并监听此端口。
        ServerSocket socket = new ServerSocket(10086);
        //2 调用accept方法开始监听，等待客户端的连接    等待与客户端三次握手之后 才能算连接全部完成
        //  serversocket 的列表中每一个关联的数据结构 都代表着关联的客户端链接
        Socket accept = socket.accept();
        //3 获取输入流  并读取客户信息
        InputStream inputStream = accept.getInputStream();
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(streamReader);
        String s = "";
        while ((s = reader.readLine()) != null) {
            System.out.println("客户端说"+s);
        }
        accept.shutdownInput();
        //4 获取输出流  响应客户端请求
        OutputStream outputStream=accept.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        PrintWriter printWriter=new PrintWriter(outputStreamWriter);
        printWriter.write("登录成功");
        printWriter.flush();

        printWriter.close();
        outputStreamWriter.close();
        outputStream.close();
        reader.close();
        streamReader.close();
        inputStream.close();
        accept.close();
        socket.close();

    }
}
