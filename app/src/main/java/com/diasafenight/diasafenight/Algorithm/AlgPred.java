package com.diasafenight.diasafenight.Algorithm;

import com.diasafenight.diasafenight.Model.MeasurementInput;

import java.util.*;

/**
 * Created by Home on 02.01.2017.
 */
public class AlgPred {
    public int  getProbGipoglik(ArrayList<MeasurementInput> args, String type) {
        List<Integer> vars_Measurments =  new ArrayList<Integer>();
        PredictionAlg predict = new PredictionAlg();
        int convert_coef = 1;
        if(type.equals("mmol/l"))
            convert_coef = 18;

        for(int i = 0;i<args.size();i++){
            vars_Measurments.add((int)( args.get(i).Value * convert_coef ));
        }
        //vars_Measurments.add(111);
        //vars_Measurments.add(73);
        //vars_Measurments.add(275);
        //vars_Measurments.add(95);
        //vars_Measurments.add(120);
        //vars_Measurments.add(200);
        //vars_Measurments.add(50);
        //93, 90, 189, 159
        //111, 73, 275, 95

        //REZULT
        int prob = getProbability(vars_Measurments);
        return prob;
    }

    public static int getProbability(List<Integer> vars_x) {
        System.out.println("www");
        PredictionAlg p = new PredictionAlg();
        List<List<Integer>> predictorsMatr = p.ReadStaticDb();
        int[][] arrPred = new int[predictorsMatr.size()][predictorsMatr.get(0).size()];
        for (int i = 0; i < predictorsMatr.size(); i++) {
            for (int j = 0; j < predictorsMatr.get(0).size(); j++) {
                arrPred[i][j] = predictorsMatr.get(i).get(j);
            }
        }
        for (int i = 0; i < predictorsMatr.size(); i++) {
            predictorsMatr.get(i).clear();
        }
        predictorsMatr.clear();
        int[][] matr_D = new int[150][150]; //D = NI-E
        for (int i = 0; i < 150; i++) {
            for (int j = 0; j < 150; j++) {
                matr_D[i][j] = -1;
            }
            matr_D[i][i] = 149;
        }
        //a
        double[][] matr_A = new double[8][8];
        double[][] matr_A_inv = new double[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int matrixesMultRes = 0;
                for (int k = 0; k < 150; k++) {
                    matrixesMultRes += arrPred[k][i] * arrPred[k][j];
                }
                matr_A[i][j] = 150 * matrixesMultRes;
                matr_A_inv[i][j] = matr_A[i][j];
            }
        }

        int[] vect_y = {-1, -1, -1, 1, -1, -1, -1, -1, -1, -1, 1, -1, 1, -1, -1, -1, -1, 1, 1, -1, -1,
                -1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1,
                1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1, 1, -1, -1, 1, 1, -1, -1,
                -1, -1, 1, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1, 1, 1, -1, 1, -1, -1,
                -1, 1, -1, -1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, -1,
                1, 1, -1, -1, -1, 1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1,
                -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, -1, 1, -1, 1, -1, -1, -1, 1, 1, 1};

        int[] vect_b = new int[8];
        int[] v_p_b1 = new int[150];
        for (int i = 0; i < 150; i++) {
            for (int j = 0; j < 150; j++) {
                v_p_b1[i] += (matr_D[i][j] * vect_y[j]);
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 150; j++) {
                vect_b[i] += arrPred[j][i] * v_p_b1[j];
            }
        }


        //step 21
        //generating of Invert A-matrix
        matr_A_inv = HouseHolder.FindInvertMatrix(matr_A);
        //Matrix.invert(matr_A_inv);
        double[][] C = Matrix.multiply(matr_A,matr_A_inv);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(C[i][j]+" ");
            }
            System.out.println();
        }

        double[] vect_C = new double[8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                vect_C[i] += matr_A_inv[i][j] * vect_b[j];
            }
        }

        //spep 22
        int[] validate_predictors = {0, 6, 7, 8, 9}; //1,7,8,9,10
        double[][] matr_A_wave = new double[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 150; k++) {
                    matr_A_wave[i][j] += arrPred[k][validate_predictors[i]] * arrPred[k][validate_predictors[j]];
                }
                matr_A_wave[i][j] *= 150; //N*pi*pj
            }
        }

        //step 23
        int[] vect_B_wave = new int[5];
        for (int i = 0; i < 5; i++) {
            //for (int j = 0; j < 5; j++) {
            for (int k = 0; k < 150; k++) {
                //vect_B_wave[i] += arrPred[k][validate_predictors[j]] * v_p_b1[k];
                vect_B_wave[i] += arrPred[k][validate_predictors[i]] * v_p_b1[k];
            }
            //}
        }
        double[][] matr_A_wave_inv = new double[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matr_A_wave_inv[i][j] = matr_A_wave[i][i];
            }
        }


        System.out.println("1123123123123");
        //Matrix.invert(matr_A_wave_inv);
        matr_A_wave_inv = HouseHolder.FindInvertMatrix(matr_A_wave);
        C = Matrix.multiply(matr_A_wave,matr_A_wave_inv);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(C[i][j]+" ");
            }
            System.out.println();
        }

        //spep 24
        double[] vect_d = new double[5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                vect_d[i] += matr_A_wave_inv[i][j] * vect_B_wave[j];
            }
        }

        //step 25
        double[] vect_Pag_1 = new double[150];
        for (int i = 0; i < 150; i++) {
            vect_Pag_1[i] = vect_C[0] * arrPred[i][0] +
                    vect_C[1] * arrPred[i][1] +
                    vect_C[2] * arrPred[i][2] +
                    vect_C[3] * arrPred[i][3] +
                    vect_C[4] * arrPred[i][4] +
                    vect_C[5] * arrPred[i][5] +
                    vect_C[6] * arrPred[i][6] +
                    vect_C[7] * arrPred[i][7];
        }
        //step 26
        double[] vect_Pag_2 = new double[150];
        for (int i = 0; i < 150; i++) {
            vect_Pag_2[i] = vect_d[0] * arrPred[i][0] +
                    vect_d[1] * arrPred[i][6] +
                    vect_d[2] * arrPred[i][7] +
                    vect_d[3] * arrPred[i][8] +
                    vect_d[4] * arrPred[i][9];
        }
        System.out.println("C:");
        for(int i = 0;i< vect_C.length;i++)
        {
            System.out.println(vect_C[i]);
        }
        System.out.println("D:");
        for(int i = 0;i< vect_d.length;i++)
        {
            System.out.println(vect_d[i]);
        }

        //step 27
        //step 28
        //same transformation
        //step 29
        List<Double> uniq_ag1_list = new ArrayList<Double>();
        for (int i = 0; i < 150; i++) {
            if (!uniq_ag1_list.contains(vect_Pag_1[i])) {
                uniq_ag1_list.add(vect_Pag_1[i]);
            }
        }
        //step 30
        List<Double> uniq_ag2_list = new ArrayList<Double>();
        for (int i = 0; i < 150; i++) {
            if (!uniq_ag2_list.contains(vect_Pag_2[i])) {
                uniq_ag2_list.add(vect_Pag_2[i]);
            }
        }
        Collections.sort(uniq_ag1_list);
        Collections.sort(uniq_ag2_list);
        System.out.println("Vectors");
        for(int i = 0;i<uniq_ag1_list.size();i++ )
        {
            System.out.println(uniq_ag1_list.get(i)+" ");
        }
        System.out.println();
        for(int i = 0;i<uniq_ag2_list.size();i++ )
        {
            System.out.println(uniq_ag2_list.get(i)+" ");
        }
        System.out.println();
        /////////////////////////////////////////////////////////////////
        /////Current values per day
        /////
        /////////////////////////////////////////////////////////////////
        List<Integer> vars_Measurments = vars_x;
        PredictionAlg predict = new PredictionAlg();


        List<Integer> current_predict = predict.getPredictoList(vars_Measurments);
        /////////////////////////////////////////////////////////////////

        double val_Pag_1 = vect_C[0]* current_predict.get(0)+
                vect_C[1]* current_predict.get(1)+
                vect_C[2]* current_predict.get(2)+
                vect_C[3]* current_predict.get(3)+
                vect_C[4]* current_predict.get(4)+
                vect_C[5]* current_predict.get(5)+
                vect_C[6]* current_predict.get(6)+
                vect_C[7]* current_predict.get(7);

        double val_Pag_2 = vect_d[0]* current_predict.get(0)+
                vect_d[1]* current_predict.get(1)+
                vect_d[2]* current_predict.get(2)+
                vect_d[3]* current_predict.get(3)+
                vect_d[4]* current_predict.get(4);

        Collections.sort(uniq_ag1_list);
        Collections.sort(uniq_ag2_list);
        int risk_mode_1 = -1;
        int risk_mode_2 = -1;

        double par_1_min = uniq_ag1_list.get(0);
        double par_1_med = (uniq_ag1_list.get(0) + uniq_ag1_list.get(uniq_ag1_list.size() - 1)) / 2.0;
        double par_1_max =  uniq_ag1_list.get(uniq_ag1_list.size() - 1);

        double par_2_min = uniq_ag2_list.get(0);
        double par_2_med = (uniq_ag2_list.get(0) + uniq_ag2_list.get(uniq_ag2_list.size() - 1)) / 2.0;
        double par_2_max =  uniq_ag2_list.get(uniq_ag2_list.size() - 1);

        if(par_1_min >= val_Pag_1){
            risk_mode_1 = 0;
        } else {
            if (val_Pag_1 > par_1_min && val_Pag_1<= par_1_med) {
                risk_mode_1 = 1;
            } else {
                if( val_Pag_1 > par_1_med && val_Pag_1< par_1_max) {
                    risk_mode_1 = 2;
                } else {
                    risk_mode_1 = 3;
                }
            }
        }
        if(par_2_min >= val_Pag_2){
            risk_mode_2 = 0;
        } else {
            if (val_Pag_2 > par_2_min && val_Pag_2<= par_2_med) {
                risk_mode_2 = 1;
            } else {
                if( val_Pag_2 > par_2_med && val_Pag_2< par_2_max) {
                    risk_mode_2 = 2;
                } else {
                    risk_mode_2 = 3;
                }
            }
        }
        int [][] matr_result = {{12,18,31,34},
                {16,25,49,57},
                {30,39,64,73},
                {36,47,77,91}};

        Random rn = new Random();
        int pseudo_random = rn.nextInt(7) -3;
        System.out.println("Probability ="+ (matr_result[risk_mode_1][risk_mode_2] + pseudo_random));
        return (matr_result[risk_mode_1][risk_mode_2] + pseudo_random);

    }
}
