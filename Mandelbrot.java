/*
*   ========= CO225 Project 01 =========
*
*   Author : Sanoj Punchihewa   E/14/262
*   Last Modified : 13/09/2017
*
*   ====================================
*
*   Class used to draw the Mandelbrot Set
*/

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class Mandelbrot extends JPanel{

    private int iterations;
    private double real_lower_bound, img_lower_bound;
    private double real_range, img_range;
    private int fractal_matrix[][];

    public Mandelbrot(double real_lower_bound, double real_upper_bound,
                      double img_lower_bound, double img_upper_bound, int iter){
        this.iterations = iter;
        this.real_lower_bound = real_lower_bound;
        this.img_lower_bound = img_lower_bound;
        real_range = Math.abs(real_upper_bound-real_lower_bound);
        img_range = Math.abs(img_upper_bound-img_lower_bound);
        setPreferredSize(new Dimension(Support.WIDTH, Support.HEIGHT));
        this.fractal_matrix = new int[Support.WIDTH][Support.HEIGHT];
    }

    public Mandelbrot(double real_lower_bound, double real_upper_bound,
                      double img_lower_bound, double img_upper_bound){
        this(real_lower_bound, real_upper_bound, img_lower_bound, img_upper_bound, Support.DEFAULT_ITER);
    }

    public Mandelbrot(int iter){
        this(Support.DEFAULT_REAL_LOWER_BOUND, Support.DEFAULT_REAL_UPPER_BOUND,
                Support.DEFAULT_IMG_LOWER_BOUND, Support.DEFAULT_IMG_UPPER_BOUND, iter);
    }

    public Mandelbrot(){
        this(Support.DEFAULT_REAL_LOWER_BOUND, Support.DEFAULT_REAL_UPPER_BOUND,
                Support.DEFAULT_IMG_LOWER_BOUND, Support.DEFAULT_IMG_UPPER_BOUND, Support.DEFAULT_ITER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        long startTime = System.currentTimeMillis();

        calculateMatrix();

        for(int x = 0; x < Support.WIDTH; x++){
            for (int y = 0; y < Support.HEIGHT; y++){
                printPoint((Graphics2D) g, convertToColor(fractal_matrix[x][y], iterations), x, y);
            }
        }

        System.out.println("Time Taken : " + (System.currentTimeMillis()-startTime) + " ms");

    }

    private void loopPixels(int x_start, int x_end, int y_start, int y_end){
        for(int x = x_start; x <= x_end; x++){
            for (int y = y_start; y <= y_end; y++){

                double realPart = real_lower_bound + ((double)x/Support.WIDTH)*real_range;
                double imgPart = img_lower_bound + ((double)y/Support.HEIGHT)*img_range;

                int checkValue = checkSet(realPart, imgPart, iterations);

                fractal_matrix[x][y] = checkValue;
            }
        }
    }

    private void calculateMatrix(){

        Thread thread1 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        loopPixels(0, Support.WIDTH/2 - 1, 0, Support.HEIGHT/2 - 1);
                    }
                }
        );
        thread1.start();

        Thread thread2 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        loopPixels(Support.WIDTH/2, Support.WIDTH-1, 0, Support.HEIGHT/2 - 1);
                    }
                }
        );
        thread2.start();

        Thread thread3 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        loopPixels(0, Support.WIDTH/2 - 1, Support.HEIGHT/2, Support.HEIGHT-1);
                    }
                }
        );
        thread3.start();

        Thread thread4 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        loopPixels(Support.WIDTH/2, Support.WIDTH-1, Support.HEIGHT/2, Support.HEIGHT-1);
                    }
                }
        );
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public int checkSet(double realPart, double imgPart, int max) {

        double real = realPart;
        double img = imgPart;

        for (int t = 0; t < max; t++) {
			
			double sqreReal = real*real;
			double sqreImg = img*img;
			
            if (sqreReal + sqreImg > 4.0) return t;
			
			double temp_img = 2*real*img;
            real = sqreReal - sqreImg + realPart;
            img = temp_img + imgPart;

        }

        return max;
    }

    private static Color convertToColor(int value, int max){

        if(value == max)return Color.BLACK;
        return Color.getHSBColor((value*5.0f)/max, 1.0f, 1.0f);

    }

    private void printPoint(Graphics2D frame, Color c, int x, int y) {

        frame.setColor(c);
        frame.draw(new Line2D.Double(x, y, x, y));

    }

}
