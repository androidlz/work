package com.seventeenok.test.IO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {

    private Selector selector;

    public void initServer(int port) throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        //设置为非阻塞
        socketChannel.configureBlocking(false);
        //将该通道对应的ServerSocket绑定到port端口
        socketChannel.socket().bind(new InetSocketAddress(port));
        //获得一个通道管理器
        selector = Selector.open();
        //将通道管理器和通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件，注册该事件后，
        //当该事件到达时，selector.select（）会返回，如果没到达，Selector.select()会一直阻塞
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 采用轮询的方式监听selector上是否有需要处理的事件，如有，则进行处理。
     *
     * @throws IOException
     */
    public void listenen() throws IOException {
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            if (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                iterator.remove();
                if (next.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) next.channel();
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    //向客户端发送消息
                    socketChannel.write(ByteBuffer.wrap(new String("向客户端发送消息").getBytes()));
                    //在和客户端连接成功之后，为了可以接收到客户端你的信息，给通道设置读权限
                    socketChannel.register(selector,SelectionKey.OP_READ);

                    //获得了可读事件
                }else if(next.isReadable()){
                    read(next);
                }
            }
        }
    }

    /**
     * 处理客户端发送过来信息的事件
     * @param next
     * @throws IOException
     */
    private void read(SelectionKey next) throws IOException{
        //服务器可读消息：得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) next.channel();

    }

}
