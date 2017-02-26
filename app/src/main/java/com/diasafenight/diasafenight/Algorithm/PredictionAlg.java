package com.diasafenight.diasafenight.Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Home on 02.01.2017.
 */


public class PredictionAlg {

    public int CONST_P2 = 90;
    public int CONST_P3 = 108;
    public int CONST_P4 = 126;
    public int CONST_P5 = 144;
    public int CONST_P6 = 162;
    public double CONST_P7 = 4.4;
    public double CONST_P8 = 0.05;
    public  double CONST_P10 = 0.02;


    public int get_predictor1(List<Integer> x_measurements) {
        return 1;
    }
    public int get_predictor2(List<Integer> x_measurements) {
        int Mi = x_measurements.size();
        if(x_measurements.get(Mi-1)<=CONST_P2){
            return 1;
        } else {
            return -1;
        }
    }
    public int get_predictor3(List<Integer> x_measurements) {
        int Mi = x_measurements.size();
        if(x_measurements.get(Mi-1)<=CONST_P3){
            return 1;
        } else {
            return -1;
        }
    }
    public int get_predictor4(List<Integer> x_measurements) {
        int Mi = x_measurements.size();
        if(x_measurements.get(Mi-1)<=CONST_P4){
            return 1;
        } else {
            return -1;
        }
    }
    public int get_predictor5(List<Integer> x_measurements) {
        int Mi = x_measurements.size();
        if(x_measurements.get(Mi-1)<=CONST_P5){
            return 1;
        } else {
            return -1;
        }
    }
    public int get_predictor6(List<Integer> x_measurements) {
        int Mi = x_measurements.size();
        if(x_measurements.get(Mi-1)<=CONST_P6){
            return 1;
        } else {
            return -1;
        }
    }

    public double get_value(int x) {

        return 1.509*(Math.pow((Math.log(x)),1.084) - 5.3811);
    }
    public double get_R(int x)
    {
        double val = this.get_value(x);
        if(val<0){
            return val*val;
        } else {
            return 0;
        }
    }
    public double get_LBGI(List<Integer> x_measurements)
    {
        //double res = 10;
        int Mi = x_measurements.size();
        double sum = 0;
        for(int i = 0;i<Mi;i++) {
            sum+=this.get_R(x_measurements.get(i));
        }
        //res/= Mi;//div by n of measur. in end for for greater accuracy
        double res = (sum*10)/Mi;
        return res;
    }

    public int get_predictor7(List<Integer> x_measurements) {
        if(this.get_LBGI(x_measurements)>=CONST_P7){
            return 1;
        } else {
            return -1;
        }
    }

    public int get_predictor8(List<Integer> x_measurements) {
        int Mi = x_measurements.size();
        if(this.get_R(x_measurements.get(Mi-1))>=CONST_P8){
            return 1;
        } else {
            return -1;
        }
    }
    public double get_HI(List<Integer> x_measurements){
        int Mi = x_measurements.size();
        double sum =0;
        for(int i = 0;i<Mi;i++){
            if(x_measurements.get(i)<95) {
                sum += Math.pow((95 - x_measurements.get(i)), 2);
            }
        }
        return  sum/(30*Mi);
    }
    public int get_predictor9(List<Integer> x_measurements) {
        if(get_HI(x_measurements)>=0.4) {
            return 1;
        } else {
            return -1;
        }
    }
    public double get_Grade_X(int x) {
        if(x>37 && x<630) {
            return 425*Math.pow((Math.log10(Math.log10(((double) x)/18.0))+0.16),2);
        } else {
            return 50;
        }
    }
    public double get_Grade_HI(List<Integer> x_measurements){
        double result = 1;
        double gradVal = 0;
        double gradVal_filtered = 0;
        for(int i = 0;i< x_measurements.size();i++){
            gradVal += get_Grade_X(x_measurements.get(i));
            if(x_measurements.get(i)<90)
            {
                gradVal_filtered += get_Grade_X(x_measurements.get(i));
            }
            result*= Math.pow(x_measurements.get(i),gradVal);
            result/= gradVal;
        }
        return 100* gradVal_filtered/gradVal;
        //return 100* result;


        /////////////////// question
    }
    public int get_predictor10(List<Integer> x_measurements) {
        if(get_Grade_HI(x_measurements)>=CONST_P10) {
            return 1;
        } else {
            return -1;
        }
    }

    public List<Integer> getPredictoList (List<Integer> x_measurements){
        List<Integer> predictors =  new ArrayList<Integer>();
        predictors.add(get_predictor1(x_measurements));
        predictors.add(get_predictor2(x_measurements));
        predictors.add(get_predictor3(x_measurements));
        predictors.add(get_predictor4(x_measurements));
        predictors.add(get_predictor5(x_measurements));
        predictors.add(get_predictor6(x_measurements));
        predictors.add(get_predictor7(x_measurements));
        predictors.add(get_predictor8(x_measurements));
        predictors.add(get_predictor9(x_measurements));
        predictors.add(get_predictor10(x_measurements));
//            for(int i = 0;i<predictors.size();i++){
//                System.out.println(predictors.get(i));
//            }
        return  predictors;
    }

    //public List<Double> getPredictionVars (List<Integer> imputVars){
    public List<Integer> getPredictionVars (List<Integer> imputVars){
//
// Algorythm p = new Algorythm();
//        List<Integer> vars =  new ArrayList<Integer>();
//        // place for get data from database for current day
//        vars.add(40);
//        vars.add(55);
//        vars.add(50);
//        vars.add(50);
        //List<Double> pred = this.getPredictoList(imputVars);
        List<Integer> pred = this.getPredictoList(imputVars);
        return  pred;
    }
    public List<List<Integer>> ReadStaticDb() {
        int StaticArr[][] = {
                {115, 155, 196, 212, -1
                }, {104, 94, 195, 137, -1
        }, {111, 94, 132, 116, -1
        }, {105, 75, 165, 97, 1
        }, {93, 91, 164, 101, -1
        }, {121, 184, 203, 102, -1
        }, {145, 137, 169, 162, -1
        }, {192, 158, 200, 127, -1
        }, {172, 131, 163, 184, -1
        }, {235, 157, 223, 112, -1
        }, {116, 227, 210, 149, 1
        }, {264, 185, 242, 211, -1
        }, {83, 183, 273, 162, 1
        }, {168, 153, 240, 149, -1
        }, {151, 223, 120, 201, -1
        }, {131, 215, 215, 126, -1
        }, {100, 213, 237, 171, -1
        }, {125, 201, 187, 110, 1
        }, {81, 97, 93, 148, 1
        }, {167, 125, 135, 172, -1
        }, {112, 182, 80, 116, -1
        }, {164, 164, 239, 201, -1
        }, {209, 106, 226, 133, -1
        }, {104, 158, 147, 199, -1
        }, {187, 157, 131, 135, -1
        }, {162, 164, 132, 196, -1
        }, {97, 159, 106, 206, -1,
        }, {146, 59, 79, 85, 1
        }, {205, 91, 140, 144, -1
        }, {113, 126, 134, 152, -1
        }, {171, 106, 195, 137, -1
        }, {178, 133, 135, 169, -1
        }, {86, 135, 196, 90, 1
        }, {135, 144, 72, 79, 1
        }, {80, 101, 114, 120, -1
        }, {197, 183, 131, 152, -1
        }, {203, 177, 183, 90, -1
        }, {173, 124, 162, 127, -1
        }, {119, 81, 149, 148, -1
        }, {193, 137, 90, 144, -1
        }, {175, 164, 98, 133, -1
        }, {104, 169, 158, 206, -1
        }, {66, 110, 151, 160, 1
        }, {205, 151, 188, 109, -1
        }, {158, 175, 208, 123, -1
        }, {149, 210, 222, 106, -1
        }, {193, 143, 328, 133, -1
        }, {135, 100, 175, 125, -1
        }, {115, 101, 157, 63, 1
        }, {115, 145, 210, 120, -1
        }, {196, 194, 118, 154, -1
        }, {214, 171, 195, 242, -1
        }, {193, 203, 234, 206, -1
        }, {176, 186, 242, 153, -1
        }, {184, 129, 174, 160, -1
        }, {204, 116, 182, 116, -1
        }, {80, 199, 162, 65, 1
        }, {255, 142, 148, 130, -1
        }, {124, 137, 175, 114, -1
        }, {71, 209, 168, 162, 1
        }, {105, 189, 119, 84, 1
        }, {87, 151, 185, 109, -1
        }, {195, 186, 255, 121, -1
        }, {184, 112, 161, 158, -1
        }, {202, 155, 129, 130, -1
        }, {189, 95, 196, 87, 1
        }, {159, 104, 147, 127, -1
        }, {133, 96, 149, 123, -1
        }, {144, 94, 111, 114, -1
        }, {123, 98, 130, 190, -1
        }, {79, 114, 153, 69, 1
        }, {194, 233, 253, 240, -1
        }, {158, 110, 108, 145, -1
        }, {208, 105, 107, 206, -1
        }, {170, 126, 80, 225, -1
        }, {207, 126, 78, 94, -1
        }, {200, 90, 130, 122, -1
        }, {121, 96, 203, 152, -1
        }, {98, 71, 155, 210, 1
        }, {166, 100, 69, 91, 1
        }, {153, 104, 160, 112, -1
        }, {206, 63, 78, 82, 1
        }, {102, 197, 94, 133, -1
        }, {135, 122, 137, 210, -1
        }, {100, 200, 118, 92, -1
        }, {144, 247, 218, 90, 1
        }, {129, 195, 157, 230, -1
        }, {110, 158, 134, 214, -1
        }, {109, 118, 101, 118, -1
        }, {97, 123, 85, 120, -1
        }, {72, 64, 132, 67, 1
        }, {63, 76, 89, 97, 1
        }, {223, 263, 237, 274, -1
        }, {100, 158, 220, 269, -1
        }, {90, 265, 274, 265, -1
        }, {197, 235, 297, 268, -1
        }, {269, 133, 128, 140, -1
        }, {231, 190, 147, 256, -1
        }, {271, 135, 148, 154, -1
        }, {144, 146, 115, 108, 1
        }, {115, 160, 44, 64, 1
        }, {39, 96, 140, 72, 1
        }, {77, 179, 88, 132, 1
        }, {49, 110, 77, 131, 1
        }, {125, 105, 119, 82, 1
        }, {93, 90, 189, 159, -1
        }, {111, 73, 275, 95, 1
        }, {151, 96, 237, 76, 1
        }, {204, 104, 164, 116, -1
        }, {273, 143, 191, 140, -1
        }, {177, 124, 211, 200, -1
        }, {141, 130, 169, 87, 1
        }, {172, 209, 274, 204, -1
        }, {115, 129, 196, 198, -1
        }, {135, 53, 152, 53, 1
        }, {211, 110, 102, 104, -1
        }, {113, 116, 100, 172, -1
        }, {173, 114, 160, 202, -1
        }, {174, 124, 192, 174, -1
        }, {103, 154, 184, 129, -1
        }, {260, 128, 183, 143, -1
        }, {202, 170, 184, 159, -1
        }, {186, 88, 108, 197, -1
        }, {177, 117, 124, 88, 1
        }, {152, 179, 226, 208, -1
        }, {144, 142, 165, 261, -1
        }, {118, 144, 114, 154, -1
        }, {121, 153, 145, 147, -1
        }, {146, 116, 98, 217, -1
        }, {121, 173, 106, 77, 1
        }, {226, 122, 126, 195, -1
        }, {245, 149, 81, 106, -1
        }, {191, 94, 177, 117, -1
        }, {84, 139, 111, 113, -1
        }, {158, 165, 173, 176, -1
        }, {134, 153, 91, 139, -1
        }, {123, 81, 202, 215, -1
        }, {139, 156, 124, 172, -1
        }, {137, 83, 89, 101, 1
        }, {79, 80, 103, 92, 1
        }, {92, 96, 98, 124, -1
        }, {137, 77, 122, 74, 1
        }, {130, 85, 87, 210, -1
        }, {190, 153, 125, 118, 1
        }, {164, 146, 118, 144, -1
        }, {143, 123, 106, 106, -1
        }, {126, 124, 133, 177, -1
        }, {113, 142, 246, 113, 1
        }, {115, 208, 259, 99, 1
        }, {106, 198, 205, 115, 1}
        };

        for (int i = 0; i < StaticArr.length; i++) {
            for (int j = 0; j < StaticArr[0].length; j++) {
                System.out.print(StaticArr[i][j] + ";");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        //List<List<Double>> predictorMatrix = new ArrayList<List<Double>>();
        List<List<Integer>> predictorMatrix = new ArrayList<List<Integer>>();

        for (int i = 0; i < 150; i++) {
            List<Integer> vars = new ArrayList<Integer>();
            for (int j = 0; j < StaticArr[i].length - 1; j++) {
                System.out.print(StaticArr[i][j] + " ");
                vars.add(StaticArr[i][j]);
            }

            System.out.println();

            predictorMatrix.add(this.getPredictionVars(vars));
            vars.clear();

            //Arrays.copyOfRange(StaticArr[i],0,5);
        }
        for (int i = 0; i < predictorMatrix.size(); i++) {
            for (int j = 0; j < predictorMatrix.get(i).size(); j++) {
                System.out.print("\t" + predictorMatrix.get(i).get(j));
            }
            System.out.println();
        }
        return predictorMatrix;
    }
}

