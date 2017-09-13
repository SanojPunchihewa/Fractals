/*
*   ========= CO225 Project 01 =========
*
*   Author : Sanoj Punchihewa   E/14/262
*   Last Modified : 13/09/2017
*
*   ====================================
*
*   Main Class
*/

import javax.swing.*;


public class Fractal {

    public static void main(String[] args){

        JFrame frame = new JFrame("Fractals");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(checkInput(args));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private static Mandelbrot checkInput(String[] arr){
        try {
            if(arr[0].equals("Mandelbrot")){
                if(arr.length == 6){
                    System.out.println("Current Set : Mandelbrot | real = [" + arr[1] + " , " + arr[2] + "] " +
                            " | img = [" + arr[3] + " , " + arr[4] + "] | Iterator = " + arr[5]);
                    return new Mandelbrot(Double.valueOf(arr[1]), Double.valueOf(arr[2]),
                            Double.valueOf(arr[3]), Double.valueOf(arr[4]), Integer.valueOf(arr[5]));
                }else if(arr.length == 5){
                    System.out.println("Current Set : Mandelbrot | real = [" + arr[1] + " , " + arr[2] + "] " +
                            " | img = [" + arr[3] + " , " + arr[4] + "] | Iterator = Default (1000)");
                    return new Mandelbrot(Double.valueOf(arr[1]), Double.valueOf(arr[2]),
                            Double.valueOf(arr[3]), Double.valueOf(arr[4]));
                }else {
                    System.out.println("Current Set : Mandelbrot | Parameters = Default");
                    return new Mandelbrot();
                }
            }else if (arr[0].equals("Julia")){
                if(arr.length == 4){
                    double realPart = Double.valueOf(arr[1]);
                    double imgPart = Double.valueOf(arr[2]);
                    int iter = Integer.valueOf(arr[3]);
                    System.out.println("Current Set : Julia | Constant = " + arr[1] + " " + arr[2] + "i | Iterator = " + arr[3]);
                    return new Julia(realPart, imgPart, iter);
                }else if(arr.length == 3){
                    System.out.println("Current Set : Julia | Constant = " + arr[1] + " " + arr[2] + "i | Iterator = Default");
                    double realPart = Double.valueOf(arr[1]);
                    double imgPart = Double.valueOf(arr[2]);
                    return new Julia(realPart, imgPart);
                }else{
                    System.out.println("Current Set : Julia | Parameters = Default");
                    return new Julia();
                }
            }else{
                System.out.println("Error : Invalid Input");
                printErrorMessage();
                System.exit(1);
            }
        }catch (Exception e){
            printErrorMessage();
            System.exit(1);
        }
        return null;
    }

    private static void printErrorMessage(){
        System.out.println("------------ HOW TO USE ------------");
        System.out.println("The first input argument must be the Fractal Type (Mandelbrot or Julia)");
        System.out.println("For example if u want Mandelbrot, >java Fractal Mandelbrot");
        System.out.println("Giving only one argument will use default values for other parameters");
        System.out.println();
        System.out.println("================================================");
        System.out.println("=== Generating Mandelbrot with custom values ===");
        System.out.println("Here you can give 4 or 5 arguments with first argument as Mandelbrot");
        System.out.println("Order of the arguments is as Follows,");
        System.out.println("java Fractal Mandelbrot <Lower_bound_of_Real> <Upper_bound_of_Real> <Lower_bound_of_Imaginary> " +
                "<Upper_bound_of_Imaginary> <Number_of_iterations>");
        System.out.println("Example,");
        System.out.println(">java Fractal Mandelbrot -0.5 0.5 -0.1 0.1 500");
        System.out.println();
        System.out.println("===== Generating Julia with custom values =====");
        System.out.println("Here you can give 2 or 3 arguments with first argument as Julia");
        System.out.println("Order of the arguments is as Follows,");
        System.out.println("java Fractal Julia <Real_part_of_Constant> <Imaginary_part_of_Constant> <Number_of_iterations>");
        System.out.println("Example,");
        System.out.println(">java Fractal Julia 0.12 -0.5 600");
    }

}
