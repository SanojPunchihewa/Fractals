/*
*   ========= CO225 Project 01 =========
*
*   Author : Sanoj Punchihewa   E/14/262
*   Last Modified : 13/09/2017
*
*   ====================================
*
*   Class used to draw the Julia Set
*/

public class Julia extends Mandelbrot {

    private double constant_real;
    private double constant_img;

    public Julia(Double constant_realPart, Double constant_imgPart, int iter){
        super(iter);
        this.constant_real = constant_realPart;
        this.constant_img = constant_imgPart;
    }

    public Julia(Double constant_realPart, Double constant_imgPart){
        super();
        this.constant_real = constant_realPart;
        this.constant_img = constant_imgPart;
    }

    public Julia(){
        super();
        this.constant_real = Support.DEFAULT_C_JULIA_REAL;
        this.constant_img = Support.DEFAULT_C_JULIA_IMG;
    }

    @Override
    public int checkSet(double realPart, double imgPart, int max) {

        double real = realPart;
        double img = imgPart;

        for (int t = 0; t < max; t++) {
			
			double sqreReal = real*real;
			double sqreImg = img*img;
			
            if (sqreReal + sqreImg > 4.0) return t;
			
			double temp_img = 2*real*img;
            real = sqreReal - sqreImg + constant_real;
            img = temp_img + constant_img;
			
        }

        return max;
    }

}
