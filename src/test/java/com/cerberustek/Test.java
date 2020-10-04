/*
 * Cerberus-Math is a simple OpenGL-compatible math library.
 * Visit https://cerberustek.com for more details
 * Copyright (c)  2020  Adrian Paskert
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See the file LICENSE included with this
 * distribution for more information.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.cerberustek;

import com.cerberustek.logic.math.CerberusMath;
import com.cerberustek.logic.math.Matrixxd;

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
