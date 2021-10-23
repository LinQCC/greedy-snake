package com.linq;

/**
 * @author babei
 * @date 2021/10/23
 */
public class SnakeGame {

    public static void main(String[] args) {

        SnakeFrame snakeFrame = new SnakeFrame();

        // 重绘线程
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                snakeFrame.repaint();
            }
        }).start();

    }
}
