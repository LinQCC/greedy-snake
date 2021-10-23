package com.linq;

import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author babei
 * @date 2021/10/23
 */
@Data
public class Snake {

    private static final int SNAKE_BODY_WIDTH = 50;

    private static final int SNAKE_BODY_HEIGHT = 50;

    private static final int SPEED = 50;

    private int length;

    private DirectionEnum oldDirection;

    private DirectionEnum direction;

    private List<SnakeNode> bodyList = new ArrayList<>();

    public Snake(int x, int y, int length, DirectionEnum direction) {
        this.length = length;
        this.direction = direction;
        this.oldDirection = direction;
        for (int i = 0; i < length; i++) {
            SnakeNode node = new SnakeNode(x, y, direction);
            bodyList.add(node);
            switch (direction) {
                case LEFT:
                    x += SNAKE_BODY_WIDTH;
                    break;
                case UP:
                    y += SNAKE_BODY_HEIGHT;
                    break;
                case RIGHT:
                    x -= SNAKE_BODY_WIDTH;
                    break;
                case DOWN:
                    y -= SNAKE_BODY_HEIGHT;
                    break;
            }
        }
    }

    public void setHeadDirection(DirectionEnum direction) {
        this.direction = direction;
        bodyList.get(0).setDirection(direction);
    }

    public void paint(Graphics g) {

        bodyList.stream().forEach((body) -> {
            body.paint(g);
        });
    }

    public void move() {

        SnakeNode head = bodyList.get(0);
        int newHeadX;
        int newHeadY;
        switch (head.getDirection()) {
            case LEFT:
                newHeadX = head.getX() - SPEED;
                head.setX(newHeadX);
                head.getRect().x = newHeadX;
                break;
            case UP:
                newHeadY = head.getY() - SPEED;
                head.setY(head.getY() - SPEED);
                head.getRect().y = newHeadY;
                break;
            case RIGHT:
                newHeadX = head.getX() + SPEED;
                head.setX(newHeadX);
                head.getRect().x = newHeadX;
                break;
            case DOWN:
                newHeadY = head.getY() + SPEED;
                head.setY(head.getY() + SPEED);
                head.getRect().y = newHeadY;
                break;
            default:
                break;
        }

        DirectionEnum preDirection = oldDirection;
        for (int i = 1; i < bodyList.size(); i++) {
            SnakeNode node = bodyList.get(i);
            DirectionEnum tempDir = node.getDirection();
            node.setDirection(preDirection);
            //System.out.println("设置节点" + i + "方向为节点" + (i - 1) + "的" + preDirection + "方向");
            DirectionEnum direction = node.getDirection();

            int newX;
            int newY;
            switch (direction) {
                case LEFT:
                    newX = node.getX() - SPEED;
                    node.setX(newX);
                    node.getRect().x = newX;
                    break;
                case UP:
                    newY = node.getY() - SPEED;
                    node.setY(node.getY() - SPEED);
                    node.getRect().y = newY;
                    break;
                case RIGHT:
                    newX = node.getX() + SPEED;
                    node.setX(newX);
                    node.getRect().x = newX;
                    break;
                case DOWN:
                    newY = node.getY() + SPEED;
                    node.setY(node.getY() + SPEED);
                    node.getRect().y = newY;
                    break;
                default:
                    break;
            }
            preDirection = tempDir;
        }
        oldDirection = head.getDirection();
    }

    public void eat(SnakeFrame snakeFrame) {
        List<Food> foodList = snakeFrame.getFoodList();
        Rectangle headRect = bodyList.get(0).getRect();
        System.out.println("headRect:" + "(" + headRect.getX() + "," + headRect.getY() + ")");
        for (int i = 0; i < foodList.size(); i++) {
            Food food = foodList.get(i);
            Rectangle foodRect = food.getRect();
            System.out.println("foodRect:" + "(" + foodRect.getX() + "," + foodRect.getY() + ")");
            if (headRect.intersects(foodRect)) {
                System.out.println("吃掉了食物*************************");
                foodList.remove(food);
                group(food.getAmount());
            }
        }
        System.out.println("-----------------------------------------------");

    }

    private void group(int foodAmount) {

        SnakeNode tailNode = bodyList.get(bodyList.size() - 1);
        for (int i = 0; i < foodAmount; i++) {
            int newNodeX = 0;
            int newNodeY = 0;
            switch (tailNode.getDirection()) {
                case LEFT:
                    newNodeX = tailNode.getX() + SNAKE_BODY_WIDTH;
                    newNodeY = tailNode.getY();
                    break;
                case UP:
                    newNodeX = tailNode.getX();
                    newNodeY = tailNode.getY() + SNAKE_BODY_HEIGHT;
                    break;
                case RIGHT:
                    newNodeX = tailNode.getX() - SNAKE_BODY_WIDTH;
                    newNodeY = tailNode.getY();
                    break;
                case DOWN:
                    newNodeX = tailNode.getX();
                    newNodeY = tailNode.getY() - SNAKE_BODY_HEIGHT;
                    break;
                default:
                    break;
            }
            SnakeNode newNode = new SnakeNode(newNodeX, newNodeY, tailNode.getDirection());
            bodyList.add(newNode);
        }
    }

    @Data
    private class SnakeNode {

        private int x;

        private int y;

        private Color nodeColor;

        private DirectionEnum direction;

        private Rectangle rect;

        public SnakeNode(int x, int y, Color nodeColor, DirectionEnum direction) {
            this.x = x;
            this.y = y;
            this.nodeColor = nodeColor;
            this.direction = direction;
            rect = new Rectangle(x, y, SNAKE_BODY_WIDTH, SNAKE_BODY_HEIGHT);
        }

        public SnakeNode(int x, int y, DirectionEnum direction) {
            this(x, y, Color.BLACK, direction);
        }

        public void paint(Graphics g) {

            if (nodeColor == Color.RED) {
                System.out.println("绘制头节点:(" + x + "," + y + ")");
            }

            Color color = g.getColor();
            g.setColor(nodeColor);
            g.fillRect(x, y, SNAKE_BODY_WIDTH, SNAKE_BODY_HEIGHT);
            g.setColor(color);
        }
    }
}
