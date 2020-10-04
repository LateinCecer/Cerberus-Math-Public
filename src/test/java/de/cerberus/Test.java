package de.cerberus;

import de.cerberus.logic.math.*;

public class Test {

    private static int counter = 0;

    public static void main(String... args) {
        System.out.println("Staring tests...");
        long startTime = System.currentTimeMillis();


        Matrixxd mat = new Matrixxd(6, 6);
        for (int i = 0; i < mat.rows(); i++) {
            for (int j = 0; j < mat.columns(); j++) {
                mat.set(i, j, Math.random());
            }
        }
        System.out.println("Matrix: \n" + mat + "\n\n");

        Matrixxd diag = CerberusMath.diagD(6);
        System.out.println("Diagonal: \n" + diag + "\n\n");


        Matrixxd inverted = CerberusMath.solveXGaussSmart(mat, diag);
        System.out.println("Inverted: \n" + inverted + "\n\n");


        Matrixxd verification = inverted.mul(mat);
        System.out.println("Verification: \n" + verification + "\n\n");


        int deltaT = (int) (System.currentTimeMillis() - startTime);
        System.out.println("Done testing " + counter + " tests in " + deltaT + "ms!");
    }
}
