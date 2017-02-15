package com.diasafenight.diasafenight.Algorithm;

import com.diasafenight.diasafenight.Algorithm.SLAR;

/**
 * Created by VladPetrus on 18.01.2017.
 */
public class HouseHolder
{
    static double[][] A, A1, Q_t, R;
//    static double[] b, x, y;
    static double det;
    static int N;

    public static void Init(double[][] M, double[] r)
    {
        A = M;
//        b = r;
        N = M.length;
//        x = new double[N];
//        y = new double[N];
        A1 = new double[N][N];
        Q_t = new double[N][N];
        R = new double[N][N];
        det = 1;
    }

    public static void QR()
    {
        double beta, mu;
        double[] w;
        double[][] Hi;

        for (int i = 0; i < N - 1; i++)
        {
            beta = 0;
            for (int k = i; k < N; k++)
            {
                beta += A[k][i] * A[k][i];
            }
            beta = Math.signum(-A[i][i]) * Math.sqrt(beta);

            mu = 1 / Math.sqrt(2 * beta * beta - 2 * beta * A[i][i]);

            w = new double[N];

            w[i] = mu * (A[i][i] - beta);
            for (int k = i + 1; k < N; k++)
            {
                w[k] = mu * A[k][i];
            }

            Hi = FindH(w, i);
            A = SLAR.MultiplyMatrix(Hi, A);

            Q_t = (i == 0) ? Hi : SLAR.MultiplyMatrix(Hi, Q_t);
        }
        R = A;

//        y = SLAR.MultiplyMatrixVector(Q_t, b);

//        double sum;
//        for (int i = N - 1; i > -1; i--)
//        {
//            sum = 0;
//            for (int k = i + 1; k < N; k++)
//            {
//                sum += R[i][k] * x[k];
//            }
//            x[i] = (y[i] - sum) / R[i][i];
//        }

        det = DetA();

        InvMatrix();
    }

    static double[][] FindH(double[] w, int i)
{
    int N = w.length;
            double[][] H = new double[N][N];

    for (int j = 0; j < N; j++)
    {
        for (int k = 0; k < N; k++)
        {
            H[j][k] = (-2) * w[j] * w[k];
            if (j == k) H[j][k] += 1;
        }
    }
    return H;
}

    static double DetA()
    {
        double res = 1;
        for (int i = 0; i < N; i++)
        {
            res *= R[i][i];
        }
        return res;
    }

    static void InvMatrix()
    {
        double sum;
        for (int i = N - 1; i > -1; i--)
        {
            for (int j = 0; j < N; j++)
            {
                sum = 0;
                for (int k = i + 1; k < N; k++)
                {
                    sum += R[i][k] * A1[k][j];
                }
                A1[i][j] = (Q_t[i][j] - sum) / R[i][i];
            }
        }
    }

    public static double[][] FindInvertMatrix(double[][] M1){
        Init(M1, null);
        QR();
        return A1;
    }
}