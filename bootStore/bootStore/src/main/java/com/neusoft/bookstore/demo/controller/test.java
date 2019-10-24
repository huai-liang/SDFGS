package com.neusoft.bookstore.demo.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class test {
    public static void main(String[] args) {
//        int fn =fn(11);
//        System.out.println(fn);
        List<String> list=new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        Iterator<String> iterator=list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }
    public static int fn(int n){
        if (n<0){
            return n;
        }if (0==n){
            return 0;
        }else if (1==n){
            return 1;
        }else {
            return fn(n-1)+fn(n-2);
        }
    }
}
