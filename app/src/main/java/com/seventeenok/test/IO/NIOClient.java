package com.seventeenok.test.IO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOClient {

    private Selector selector;

    /**
     * 获得一个连接Socket通道，并对通道进行初始化工作
     * @param ip
     * @param port
     */
    public void initClient(String ip,int port) throws IOException {
        //获得一个Socket通道
        SocketChannel channel= SocketChannel.open();
        //设置通道为非阻塞
        channel.configureBlocking(false);
        //获得一个通道管理器
        selector = Selector.open();
        channel.finishConnect();
        //客户端连接服务器，其实方法并没有实现连接，需要在listen()方法中调用channel.finishConnect()才能完成连接
        channel.connect(new InetSocketAddress(ip,port));
        //将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件
        channel.register(selector,SelectionKey.OP_CONNECT);
    }

    /**
     * 采用轮询的方式监听selector上是否有需要处理的事件，如有，则进行处理。
     */
    public void listen() throws IOException {
        while (true){
            selector.select();
            //获得selector中选中项的迭代器
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                //删除已选中的key,以防重复处理
//奥术大师多
                //asda
            }



        }
    }
}
