package com.linq;

import lombok.Data;

import java.awt.*;

/**
 * @author babei
 * @date 2021/10/24
 */
@Data
public class Food {

    private static final int FOOD_WIDTH = 50;

    private static final int FOOD_HEIGHT = 50;

    private int x;

    private int y;

    private int amount;

    private Color foddColor;

    private Rectangle rect;

    public Food(int x, int y, int amount, Color foddColor) {
        this.x = x;
        this.y = y;
        this.amount = amount;
        this.foddColor = foddColor;
        this.rect = new Rectangle(x, y, FOOD_WIDTH, FOOD_HEIGHT);
    }

    public void paint(Graphics g) {

        Color color = g.getColor();
        g.setColor(foddColor);
        g.fillOval(x, y, FOOD_WIDTH, FOOD_HEIGHT);
        g.setColor(color);
    }
}
