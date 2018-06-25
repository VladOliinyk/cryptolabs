import java.util.Arrays;

public class Matrix {

    public static int[][] multiply(int[][] a, int[][] b) {
        return multiply(a, b, -1);
    }

    public static int[][] multiply(int[][] a, int[][] b, int mod) {

        int arow = a.length;
        int acol = a[0].length;

        int brow = b.length;
        int bcol = b[0].length;

        int[][] c = new int[arow][bcol];


        for (int i = 0; i < arow; i++) {
            for (int j = 0; j < bcol; j++) {

                for (int k = 0; k < acol; k++) {
                    int m1 = a[i][k];
                    int m2 = b[k][j];
                    c[i][j] += m1 * m2;
                }

                if (mod != -1) {
                    c[i][j] = c[i][j] % mod;
                }

            }
        }

//
//        System.out.println();
//        System.out.println();
//        System.out.println();
//
//        System.out.println("A:" + Arrays.deepToString(a));
//        System.out.println("B:" + Arrays.deepToString(b));
//        System.out.println("C:" + Arrays.deepToString(c));
//        System.out.println("D:" + Arrays.deepToString(new int[][]{{7, 8}, {0, 18}}) + " должно выйти");
//
//        System.out.println();
//        System.out.println();
//        System.out.println();


        return c;
    }


    public static void printMatrix(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j]);
                System.out.print(" ");
            }
            System.out.println("]");
        }

    }


    public static int[][] makeMatrixFromOneDimensionalArray(int[] msgCode) {
        // [a, b] to [ [a], [b] ]
        // [a, b, c] to  [ [a, b, c] ]
        // [a, b, c, d] to  [ [a, b], [c, d] ]

        int[][] result;
        if(msgCode.length == 2) {

            result = new int[2][1];
            result[0][0] = msgCode[0];
            result[1][0] = msgCode[1];

            return result;
        }
        if (msgCode.length % 2 == 1) {
            result = new int[msgCode.length][1];
            for (int i = 0; i < msgCode.length; i++) {
                result[i][0] = msgCode[i];
            }
        } else {
            int msize = msgCode.length / 2;
            result = new int[msize][msize];
            for (int i = 0; i < msize; i++) {
                for (int j = 0; j < msize; j++) {
                    result[i][j] = msgCode[i * msize + j];
                }
            }
        }

        return result;
    }


    public static int[] makeArrayFromMatrix(int[][] matrix) {
        // [ [a, b, c] ] to [a, b, c]
        // [ [a, b], [c, d] ] to [a, b, c, d]
        // [ [a, b, c], [d, e, f] ] to [a, b, c, d, e, f]
        // [ [a, b], [c, d], [e, f] ] to [a, b, c, d, e, f]


        int[] row = new int[matrix.length * matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                row[i * matrix[0].length + j] = matrix[i][j];
            }
        }

        return row;
    }

    public static double determinant(int key[][], int N) {
        //go double
        double[][] keyd = new double[key.length][key[0].length];
        for (int i = 0; i < keyd.length; i++) {
            for (int j = 0; j < key[0].length; j++) {
                keyd[i][j] = key[i][j];
            }
        }

        return determinant(keyd, N);
    }

    public static double determinant(double A[][], int N)
    {


        //find det
        double det=0;
        if(N == 1)
        {
            det = A[0][0];
        }
        else if (N == 2)
        {
            det = A[0][0]*A[1][1] - A[1][0]*A[0][1];
        }
        else
        {
            det=0;
            for(int j1=0;j1<N;j1++)
            {
                double[][] m = new double[N-1][];
                for(int k=0;k<(N-1);k++)
                {
                    m[k] = new double[N-1];
                }
                for(int i=1;i<N;i++)
                {
                    int j2=0;
                    for(int j=0;j<N;j++)
                    {
                        if(j == j1)
                            continue;
                        m[i-1][j2] = A[i][j];
                        j2++;
                    }
                }
                det += Math.pow(-1.0,1.0+j1+1.0)* A[0][j1] * determinant(m,N-1);
            }
        }
        return det;
    }

    public static boolean haveInversion(int[][] key) {

        //int[][] to float[][]
        float[][] fkey = new float[key.length][key[0].length];
        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < key.length; j++) {
                fkey[i][j] = key[i][j];
            }
        }


        float[][] fKeyInverse = Matrix.inversion(fkey);
        float[][] fKeyInverseInverse = Matrix.inversion(fKeyInverse);

        System.out.println(Arrays.deepToString(key));
        System.out.println(Arrays.deepToString(fkey));
        System.out.println(Arrays.deepToString(fKeyInverse));
        System.out.println(Arrays.deepToString(fKeyInverseInverse));

        return Matrix.equal(fKeyInverse, fKeyInverseInverse);

    }

    private static boolean equal(float[][] a, float[][] b) {
        for ( int i=0; i < a.length; i++) {
            for (int j=0; j < a[0].length; j++) {
                if (a[i][j] != b[i][j]) {
                    System.out.println("a["+i+"]["+j+"] != b["+i+"]["+j+"]  " + a[i][j] + " != " + b[i][j]);
                    return false;
                }
            }
        }

        return true;
    }

    public static float[][] inversion(float[][] A) {
        double temp;
        int N = A.length;

        float[][] E = new float[N][N];


        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                E[i][j] = 0f;

                if (i == j)
                    E[i][j] = 1f;
            }

        for (int k = 0; k < N; k++) {
            temp = A[k][k];

            for (int j = 0; j < N; j++) {
                A[k][j] /= temp;
                E[k][j] /= temp;
            }

            for (int i = k + 1; i < N; i++) {
                temp = A[i][k];

                for (int j = 0; j < N; j++) {
                    A[i][j] -= A[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }

        for (int k = N - 1; k > 0; k--) {
            for (int i = k - 1; i >= 0; i--) {
                temp = A[i][k];

                for (int j = 0; j < N; j++) {
                    A[i][j] -= A[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                A[i][j] = E[i][j];

        return E;
    }
}
