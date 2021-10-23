package com.linq;

import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author babei
 * @date 2021/10/23
 */
@Data
public class SnakeFrame extends JFrame {

    public static final int GAME_WIDTH = 1080;

    public static final int GAME_HEIGHT = 960;

    private static Random random = new Random();

    private Snake snake = new Snake(500, 500, 5, DirectionEnum.RIGHT);

    private List<Food> foodList = new ArrayList<>();

    public SnakeFrame() throws HeadlessException {

        setTitle("Snake");
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new SnakeKeyListener());
        setVisible(true);
    }


    private Image offScreenImage = null;

    @Override
    public void paint(Graphics g) {

        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics graphics = offScreenImage.getGraphics();

        super.paint(graphics);

        if (foodList.size() < 2) {
            Food food = new Food(random.nextInt(GAME_WIDTH), random.nextInt(GAME_HEIGHT), 1, Color.RED);
            foodList.add(food);
        }
        for (Food food : foodList) {
            food.paint(graphics);
        }

        snake.move();

        snake.eat(this);

        snake.paint(graphics);
        g.drawImage(offScreenImage, 0, 0, null);

    }

    private class SnakeKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    if (snake.getDirection() != DirectionEnum.RIGHT) {
                        snake.setHeadDirection(DirectionEnum.LEFT);
                    }
                    break;
                case KeyEvent.VK_W:
                    if (snake.getDirection() != DirectionEnum.DOWN) {
                        snake.setHeadDirection(DirectionEnum.UP);
                    }
                    break;
                case KeyEvent.VK_D:
                    if (snake.getDirection() != DirectionEnum.LEFT) {
                        snake.setHeadDirection(DirectionEnum.RIGHT);
                    }
                    break;
                case KeyEvent.VK_S:
                    if (snake.getDirection() != DirectionEnum.UP) {
                        snake.setHeadDirection(DirectionEnum.DOWN);
                    }
                    break;
                case KeyEvent.VK_K:
                    repaint();
                default:
                    break;
            }
        }

    }
}
