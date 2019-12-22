package com.seventeenok.test.sorrt;

public class StackUtil {
    class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
        }
    }

//    1.删除单链表的指定节点

    public static void deleteNode(Node head, Node node) {
        //删除头结点
        if (head == node) {
            node = null;
        } else {
            if (node.next != null) {
                while (head.next != node) {
                    head.next = null;
                }
            }
        }

    }
}
