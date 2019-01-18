package com.csk.epay.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @program: Cheng
 * @description: 河内塔问题
 * @author: Mr.Cheng
 * @create: 2018-11-28 10:43
 **/
public class Hanoi {
    //Java程序的实现


    public static void main (String args[]) throws IOException {
        int n;
        BufferedReader buf;
        buf = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("请输入盘数：");
        n = Integer.parseInt(buf.readLine());
        Hanoi hanoi = new Hanoi();
        hanoi.move(n, 'A', 'B', 'C');
    }

    public void move (int n, char a, char b, char c) {
        if (n == 1) {
            System.out.println("盘 " + n + " 由 " + a + " 移至 " + c);
        } else {
            move(n - 1, a, c, b);
            System.out.println("盘 " + n + " 由 " + a + " 移至 " + c);
            move(n - 1, b, a, c);
        }
    }
}