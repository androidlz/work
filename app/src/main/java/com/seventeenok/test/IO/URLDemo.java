package com.seventeenok.test.IO;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class URLDemo {
    public static void test() throws Exception {
        //使用URL读取网页内容
        URL url = new URL("www.baidu.com");
        InputStream inputStream = url.openStream();//通过openStream方式获取资源的字节输入流
        InputStreamReader streamReader=new InputStreamReader(inputStream,"UTF-8");//将字节输入流转化为字符输入流  不指定编码  中文会乱码
        BufferedReader reader=new BufferedReader(streamReader);
        String data = reader.readLine();
        while (data!=null){
            data=reader.readLine();
        }
        reader.close();
        streamReader.close();
        inputStream.close();

    }
}
