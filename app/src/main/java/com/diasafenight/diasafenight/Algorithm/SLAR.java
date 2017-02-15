package com.diasafenight.diasafenight.Algorithm;

/**
 * Created by VladPetrus on 18.01.2017.
 */


public class SLAR {
    public static double[][] MultiplyMatrix(double[][] M1, double[][] M2)
    {
        int N = M1.length;
        double[][] M = new double[N][N];

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                M[i][j] = 0;
                for (int k = 0; k < N; k++)
                {
                    M[i][j] += M1[i][k] * M2[k][j];
                }
            }
        }
        return M;
    }

    public static double[] MultiplyMatrixVector(double[][] M, double[] v)
    {
        int N = v.length;
        double[] r = new double[N];

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                r[i] += M[i][j] * v[j];
            }
        }

        return r;
    }
}
