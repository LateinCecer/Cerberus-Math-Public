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

package com.cerberustek.logic.math;

public class CerberusMath {

    public static final double DOT_THRESTHOLD = 0.9995;

    public static long counter = 0;

    public static Complexd exp(Complexf complex) {
        return new Complexd(Math.cos(complex.getIm()), Math.sin(complex.getIm())).
                mul(Math.exp(complex.getRe()));
    }

    public static float cull(float value, float max) {
        return Math.min(value, max);
    }

    public static float cull(float value, float max, float min) {
        return Math.max(Math.min(value, max), min);
    }

    public static Quaternionf slerp(Quaternionf start, Quaternionf end, float t) {
        // Normalize to avoid unwanted behaviour
        start = start.normalized();
        end = end.normalized();

        float dot = start.dot(end);

        // If the dot product is negative, slerp won't take
        // the shorter path. Note that start und -start are
        // equivalent when the negation is applied to all four
        // components. Fix by reversing one quaternion
        if (dot < 0.0f) {
            start = start.mul(-1f);
            dot *= -1;
        }

        if (dot > DOT_THRESTHOLD)
            return start.add(end.sub(start).mul(t)).normalized();

        // Since dot is in range [0, DOT_THRESHOLD], acos is safe to use
        float theta_0 = (float) Math.acos(dot); // Angle between inputs
        float theta = theta_0 * t; // Angle between start and output
        float sin_theta = (float) Math.sin(theta); // pragma once
        float sin_theta_0 = (float) Math.sin(theta_0); // pragma once

        float s0 = (float) Math.cos(theta) - dot * sin_theta / sin_theta_0; // == sin(theta_0 - theta) / sin(theta_0)
        float s1 = sin_theta / sin_theta_0;

        return start.mul(s0).add(end.mul(s1));
    }

    public static Quaterniond slerp(Quaterniond start, Quaterniond end, double t) {
        start = start.normalized();
        end = end.normalized();

        double dot = start.dot(end);

        if (dot < 0.0f) {
            start = start.mul(-1d);
            dot *= -1;
        }

        if (dot > DOT_THRESTHOLD)
            return start.add(end.sub(start).mul(t)).normalized();

        double theta_0 = Math.acos(dot);
        double theta = theta_0 * t;
        double sin_theta = Math.sin(theta);
        double sin_theta_0 = Math.sin(theta_0);

        double s0 = Math.cos(theta) - dot * sin_theta / sin_theta_0;
        double s1 = sin_theta / sin_theta_0;

        return start.mul(s0).add(end.mul(s1));
    }

    @SuppressWarnings("Duplicates")
    public static <T extends Comparable<T>> void heapSort(T[] data, int off, int len) {
        // construct heap
        for (int i = len / 2 - 1; i >= 0; i--)
            constructMaxHeap(data, i + off, len);

        // sort array
        T buf;
        for (int i = 0; i < len; i++) {
            buf = data[off];
            data[off] = data[len - i - 1];
            constructMaxHeap(data, off, len - i);
            data[len - i - 1] = buf;
        }
    }

    @SuppressWarnings("Duplicates")
    public static void heapSort(int[] data, int off, int len) {
        // construct heap
        for (int i = len / 2 - 1; i >= 0; i--)
            constructMaxHeap(data, i + off, len);

        // sort array
        int buf;
        for (int i = 0; i < len; i++) {
            buf = data[off];
            data[off] = data[len - i - 1];
            constructMaxHeap(data, off, len - i);
            data[len - i - 1] = buf;
        }
    }

    @SuppressWarnings("Duplicates")
    private static <T extends Comparable<T>> void constructMaxHeap(T[] data, int index, int size) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int less = index;

        // counter += 2;

        if (left < size && data[left].compareTo(data[less]) > 0)
            less = left;
        if (right < size && data[right].compareTo(data[less]) > 0)
            less = right;

        if (less != index) {
            T buf = data[index];
            data[index] = data[less];
            data[less] = buf;

            constructMaxHeap(data, less, size);
        }
    }

    private static void constructMaxHeap(int[] data, int index, int size) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int less = index;

        if (left < size && data[left] > data[less])
            less = left;
        if (right < size && data[right] > data[less])
            less = right;

        if (less != index) {
            int buf = data[index];
            data[index] = data[less];
            data[less] = buf;

            constructMaxHeap(data, less, size);
        }
    }

    @SuppressWarnings("Duplicates")
    private static <T extends Comparable<T>> void constructMaxHeap(T[] data, int index, int start, int end) {
        int left = index * 2 + 1 + start;
        int right = index * 2 + 2 + start;
        int less = index;

        // counter += 2;

        if (left <= end && data[left].compareTo(data[less]) > 0)
            less = left;
        if (right <= end && data[right].compareTo(data[less]) > 0)
            less = right;

        if (less != index) {
            T buf = data[index];
            data[index] = data[less];
            data[less] = buf;

            constructMaxHeap(data, less, start, end);
        }
    }

    @SuppressWarnings("Duplicates")
    private static <T extends Comparable<T>> void constructMinHeap(T[] data, int index, int start, int end) {
        int left = index * 2 + 1 + start;
        int right = index * 2 + 2 + start;
        int less = index;

        // counter += 2;

        if (less <= end && data[left].compareTo(data[less]) < 0)
            less = left;
        if (right <= end && data[right].compareTo(data[less]) < 0)
            less = right;

        if (less != index) {
            T buf = data[index];
            data[index] = data[less];
            data[less] = buf;

            constructMinHeap(data, less, start, end);
        }
    }

    @SuppressWarnings("Duplicates")
    private static <T extends Comparable<T>> void constructMinHeap(T[] data, int index, int size) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int less = index;

        // counter += 2;

        if (left < size && data[left].compareTo(data[less]) < 0)
            less = left;
        if (right < size && data[right].compareTo(data[less]) < 0)
            less = right;

        if (less != index) {
            swap(data, index, less);
            constructMinHeap(data, less, size);
        }
    }

    public static <T extends Comparable<T>> void quickHeapSort(T[] data, int start, int end) {
        if (end - start > 0) {
            int pivotIndex = (end + start) / 2;
            swap(data, pivotIndex, end);

            pivotIndex = partitionReverse(data, start, end);
            // System.out.println("Length: " + (end - start) + " partition: " + pivotIndex);
            if (pivotIndex <= (end - start) / 2) {
                twoLayerMaxHeap(data, start, pivotIndex - 1);
                swap(data, pivotIndex, end - pivotIndex + 1);
                quickHeapSort(data, start, end - pivotIndex);
            } else {
                twoLayerMinHeap(data, start, end - pivotIndex);
                swap(data, pivotIndex, end - pivotIndex + 1);
                quickHeapSort(data, end - pivotIndex + 2, end);
            }
        }
    }

    private static <T extends Comparable<T>> int spezialLeaf(T[] data, int start, int end) {
        int i;
        for (i = start + 1; 2 * i <= end;) {
            if (2 * i + 1 <= end && data[2 * i + 1].compareTo(data[2 * i]) > 0) {
                data[i] = data[2 * i + 1];
                i = 2 * i + 1;
            } else {
                data[i] = data[2 * i];
                i = 2 * i;
            }
        }
        return i;
    }

    @SuppressWarnings("Duplicates")
    private static <T extends Comparable<T>> void twoLayerMaxHeap(T[] data, int start, int end) {
        constructMaxHeap(data, 0, start, end);
        T buf;
        for (int i = start; i < end; i++) {
            buf = data[i + 1];
            data[spezialLeaf(data, start, end)] = buf;
        }
    }

    @SuppressWarnings("Duplicates")
    private static <T extends Comparable<T>> void twoLayerMinHeap(T[] data, int start, int end) {
        constructMinHeap(data, 0, start, end);
        T buf;
        for (int i = start; i < end; i++) {
            buf = data[i + 1];
            data[spezialLeaf(data, start, end)] = buf;
        }
    }

    @SuppressWarnings("Duplicates")
    public static <T extends Comparable<T>> void medianQuickSort(T[] data, int start, int end) {
        if (end - start > 0) {
            int pivotIndex;
            int middle = (end + start) / 2;
            int LM = data[start].compareTo(data[middle]);
            int MR = data[middle].compareTo(data[end]);
            int LR = data[start].compareTo(data[end]);
            // counter += 3;

            if (-LM == LR)
                pivotIndex = start;
            else {
                if (MR == -LR)
                    pivotIndex = end;
                else
                    pivotIndex = middle;
            }
            swap(data, pivotIndex, end);

            pivotIndex = partition(data, start, end);
            medianQuickSort(data, start, pivotIndex - 1);
            medianQuickSort(data, pivotIndex + 1, end);
        }
    }

    @SuppressWarnings("Duplicates")
    public static void medianQuickSort(int[] data, int start, int end) {
        if (end - start > 0) {
            int pivotIndex;
            int middle = (end + start) / 2;
            int LM = Integer.compare(data[start], data[middle]);
            int MR = Integer.compare(data[middle], data[end]);
            int LR = Integer.compare(data[start], data[end]);
            // counter += 3;

            if (-LM == LR)
                pivotIndex = start;
            else {
                if (MR == -LR)
                    pivotIndex = end;
                else
                    pivotIndex = middle;
            }
            swap(data, pivotIndex, end);

            pivotIndex = partition(data, start, end);
            medianQuickSort(data, start, pivotIndex - 1);
            medianQuickSort(data, pivotIndex + 1, end);
        }
    }

    @SuppressWarnings("Duplicates")
    public static <T extends Comparable<T>> void middleQuickSort(T[] data, int start, int end) {
        if (end - start > 0) {
            int pivotIndex = (end + start) / 2;
            swap(data, pivotIndex, end);

            pivotIndex = partition(data, start, end);
            middleQuickSort(data, start, pivotIndex - 1);
            middleQuickSort(data, pivotIndex + 1, end);
        }
    }

    @SuppressWarnings("Duplicates")
    public static void middleQuickSort(int[] data, int start, int end) {
        if (end - start > 0) {
            int pivotIndex = (end + start) / 2;
            swap(data, pivotIndex, end);

            pivotIndex = partition(data, start, end);
            middleQuickSort(data, start, pivotIndex - 1);
            middleQuickSort(data, pivotIndex + 1, end);
        }
    }

    @SuppressWarnings("Duplicates")
    public static <T extends Comparable<T>> void randomizedQuickSort(T[] data, int start, int end) {
        if (end - start > 0) {
            int pivotIndex = start + (int) (Math.random() * (end - start));
            swap(data, pivotIndex, end);

            pivotIndex = partition(data, start, end);
            randomizedQuickSort(data, start, pivotIndex - 1);
            randomizedQuickSort(data, pivotIndex + 1, end);
        }
    }

    @SuppressWarnings("Duplicates")
    public static void randomizedQuickSort(int[] data, int start, int end) {
        if (end - start > 0) {
            int pivotIndex = start + (int) (Math.random() * (end - start));
            swap(data, pivotIndex, end);

            pivotIndex = partition(data, start, end);
            randomizedQuickSort(data, start, pivotIndex - 1);
            randomizedQuickSort(data, pivotIndex + 1, end);
        }
    }

    public static <T extends Comparable<T>> void quickSort(T[] data, int start, int end) {
        if (end - start > 0) {
            int pivotIndex = partition(data, start, end);
            quickSort(data, start, pivotIndex - 1);
            quickSort(data, pivotIndex + 1, end);
        }
    }

    private static <T extends Comparable<T>> int partition(T[] data, int start, int end) {
        T pivotElement = data[end];
        int i = start - 1;
        for (int j = start; j < end; j++) {
            // counter++;
            if (data[j].compareTo(pivotElement) <= 0) {
                i++;
                swap(data, i, j);
            }
        }
        swap(data, i + 1, end);
        return i + 1;
    }

    private static int partition(int[] data, int start, int end) {
        int pivotElement = data[end];
        int i = start - 1;
        int buf;
        for (int j = start; j < end; j++) {
            if (data[j] <= pivotElement) {
                i++;

                buf = data[j];
                data[j] = data[i];
                data[i] = buf;
            }
        }

        buf = data[i + 1];
        data[i + 1] = data[end];
        data[end] = buf;
        return i + 1;
    }

    private static <T extends Comparable<T>> int partitionReverse(T[] data, int start, int end) {
        T pivotElement = data[end];
        int i = start - 1;
        for (int j = start; j < end; j++) {
            // counter++;
            if (data[j].compareTo(pivotElement) >= 0) {
                i++;
                swap(data, i, j);
            }
        }
        swap(data, i + 1, end);
        return i + 1;
    }

    public static <T extends Comparable<T>> void swap(T[] data, int a, int b) {
        T buf = data[a];
        data[a] = data[b];
        data[b] = buf;
    }

    public static void swap(int[] data, int a, int b) {
        int buf = data[a];
        data[a] = data[b];
        data[b] = buf;
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] data, int off, int len) {
        for (int i = off; i < len - 1; i++) {
            if (data[i].compareTo(data[i + 1]) > 0)
                return false;
        }
        return true;
    }

    public static Matrixxf solveXGauss(Matrixxf m, Matrixxf b) {
        Matrixxf out = new Matrixxf(b.rows(), b.columns());

        for (int c = 0; c < b.columns(); c++) {
            // column number c for matrix b
            Matrixxf bc = b.getColumn(c);
            // matrix of the complete linear equation
            Matrixxf equation = m.push(bc);

            // solve equation
            equation = equation.rref();

            // copy solved values over
            for (int r = 0; r < out.rows(); r++)
                out.set(r, c, equation.get(r, m.columns()));
        }
        return out;
    }

    public static Matrixxd solveXGauss(Matrixxd m, Matrixxd b) {
        Matrixxd out = new Matrixxd(b.rows(), b.columns());

        for (int c = 0; c < b.columns(); c++) {
            // column number c for matrix b
            Matrixxd bc = b.getColumn(c);
            // matrix of the complete linear equation
            Matrixxd equation = m.push(bc);

            // solve equation
            equation = equation.rref();

            // copy solved values over
            for (int r = 0; r < out.rows(); r++)
                out.set(r, c, equation.get(r, m.columns()));
        }
        return out;
    }

    public static Matrixxf solveXGaussSmart(Matrixxf m, Matrixxf b) {
        Matrixxf out = new Matrixxf(b.rows(), b.columns());

        for (int c = 0; c < b.columns(); c++) {
            // column number c for matrix b
            Matrixxf bc = b.getColumn(c);
            // matrix of the complete linear equation
            Matrixxf equation = m.push(bc);

            // solve equation
            equation = equation.smartRref();

            // copy solved values over
            for (int r = 0; r < out.rows(); r++)
                out.set(r, c, equation.get(r, m.columns()));
        }
        return out;
    }

    public static Matrixxd solveXGaussSmart(Matrixxd m, Matrixxd b) {
        Matrixxd out = new Matrixxd(b.rows(), b.columns());

        for (int c = 0; c < b.columns(); c++) {
            // column number c for matrix b
            Matrixxd bc = b.getColumn(c);
            // matrix of the complete linear equation
            Matrixxd equation = m.push(bc);

            // solve equation
            equation = equation.smartRref();

            // copy solved values over
            for (int r = 0; r < out.rows(); r++)
                out.set(r, c, equation.get(r, m.columns()));
        }
        return out;
    }

    public static Matrixxf solveX(Matrixxf l, Matrixxf u, Matrixxf b) {
        Matrixxf out = new Matrixxf(b.rows(), b.columns());

        int len = b.rows();
        float[] y = new float[len];
        for (int layer = 0; layer < b.columns(); layer++) {

            for (int i = 0; i < len; i++) {
                float sum = 0;
                for (int j = 0; j < i; j++)
                    sum += l.get(i, j) * y[j];
                y[i] = (b.get(i, layer) - sum) / l.get(i, i);
            }

            for (int i = u.rows() - 1; i >= 0; i--) {
                float sum = 0;
                for (int j = i + 1; j < u.columns(); j++)
                    sum += u.get(i, j) * out.get(j, layer);
                out.set(i, layer, (y[i] - sum) / u.get(i, i));
            }
        }
        return out;
    }

    public static Matrixxd solveX(Matrixxd l, Matrixxd u, Matrixxd b) {
        Matrixxd out = new Matrixxd(b.rows(), b.columns());

        int len = b.rows();
        double[] y = new double[len];
        for (int layer = 0; layer < b.columns(); layer++) {

            for (int i = 0; i < len; i++) {
                double sum = 0;
                for (int j = 0; j < i; j++)
                    sum += l.get(i, j) * y[j];
                y[i] = (b.get(i, layer) - sum) / l.get(i, i);
            }

            for (int i = u.rows() - 1; i >= 0; i--) {
                double sum = 0;
                for (int j = i + 1; j < u.columns(); j++)
                    sum += u.get(i, j) * out.get(j, layer);
                out.set(i, layer, (y[i] - sum) / u.get(i, i));
            }
        }
        return out;
    }

    public static Matrixxf diag(int n) {
        Matrixxf out = new Matrixxf(n, n);
        for (int i = 0; i < n; i++)
            out.set(i, i, 1);
        return out;
    }

    public static Matrixxd diagD(int n) {
        Matrixxd out = new Matrixxd(n, n);
        for (int i = 0; i < n; i++)
            out.set(i, i, 1);
        return out;
    }

    public static Matrixxf upper(Matrixxf m) {
        Matrixxf out = new Matrixxf(m.rows(), m.columns());

        for (int r = 0; r < out.rows(); r++) {
            for (int c = r; c < out.columns(); c++)
                out.set(r, c, m.get(r, c));
        }
        return out;
    }

    public static Matrixxd upper(Matrixxd m) {
        Matrixxd out = new Matrixxd(m.rows(), m.columns());

        for (int r = 0; r < out.rows(); r++) {
            for (int c = r; c < out.columns(); c++)
                out.set(r, c, m.get(r, c));
        }
        return out;
    }

    @SuppressWarnings("DuplicatedCode")
    public static Matrixxf lower(Matrixxf m) {
        Matrixxf out = new Matrixxf(m.rows(), m.columns());

        for (int r = 0; r < out.rows(); r++) {
            for (int c = 0; c < r; c++)
                out.set(r, c, m.get(r, c));
            out.set(r, r, 1);
        }
        return out;
    }

    @SuppressWarnings("DuplicatedCode")
    public static Matrixxd lower(Matrixxd m) {
        Matrixxd out = new Matrixxd(m.rows(), m.columns());

        for (int r = 0; r < out.rows(); r++) {
            for (int c = 0; c < r; c++)
                out.set(r, c, m.get(r, c));
            out.set(r, r, 1);
        }
        return out;
    }

    public static Matrixxf highPrecision(Matrixxf a, Matrixxf b, float error) {
        // Calculate lower and upper matrix
        Matrixxf lu = a.factorize();
        Matrixxf l = lower(lu);
        Matrixxf u = upper(lu);

        // Solve first iteration matrix
        Matrixxf x = solveX(l, u, b);
        // Calc first iteration error
        Matrixxf deltaB = a.mul(x);
        // iteratively reduce rounding errors
        Matrixxf deltaX = solveX(l, u, deltaB);
        while (deltaX.abs() > error) {
            deltaX = solveX(l, u, deltaB);
            x.subSelf(deltaX);
            deltaB = a.mul(x);
        }
        return x;
    }

    public static Matrixxd highPrecision(Matrixxd a, Matrixxd b, double error) {
        // Calculate lower and upper matrix
        Matrixxd lu = a.factorize();
        Matrixxd l = lower(lu);
        Matrixxd u = upper(lu);

        // Solve first iteration matrix
        Matrixxd x = solveX(l, u, b);
        // Calc first iteration error
        Matrixxd deltaB = a.mul(x);
        // iteratively reduce rounding errors
        Matrixxd deltaX = solveX(l, u, deltaB);
        while (deltaX.abs() > error) {
            deltaX = solveX(l, u, deltaB);
            x.subSelf(deltaX);
            deltaB = a.mul(x);
        }
        return x;
    }

    public static float scalar(Matrixxf a, Matrixxf b) {
        if (a.columns() != b.columns() || a.rows() != b.rows())
            throw new IllegalArgumentException("Invalid matrix format");

        float sum = 0;
        for (int j = 0; j < a.columns(); j++) {
            for (int i = 0; i < a.rows(); i++)
                sum += a.get(i, j) * b.get(i, j);
        }
        return sum;
    }

    public static double scalar(Matrixxd a, Matrixxd b) {
        if (a.columns() != b.columns() || a.rows() != b.rows())
            throw new IllegalArgumentException("Invalid matrix format");

        double sum = 0;
        for (int j = 0; j < a.columns(); j++) {
            for (int i = 0; i < a.rows(); i++)
                sum += a.get(i, j) * b.get(i, j);
        }
        return sum;
    }

    @SuppressWarnings("DuplicatedCode")
    public static Matrixxf createJacobi(Matrixxf a) {
        Matrixxf x = diag(a.rows());
        boolean flag = true;

        int k = 0;
        while (flag) {
            flag = false;
            for (int p = 0; p < a.rows(); p++) {
                for (int q = 0; q < a.rows(); q++) {
                    // skipp main diagonal elements
                    if (p == q)
                        continue;

                    // check for quasi zero elements
                    if ((Math.abs(a.get(p, q)) < Math.abs(a.get(p, q)) * 1e-9)
                            || (Math.abs(a.get(p, q)) < Math.abs(a.get(q, p)) * 1e-9)) {

                        continue;
                    } else
                        flag = true;

                    // generate t, s and c from matrix a
                    float theta = (a.get(q, p) - a.get(p, p)) / (2 * a.get(p, q));
                    float t = Math.signum(theta) / (float) (Math.abs(theta) + Math.sqrt(theta * theta + 1));

                    float c = 1f / (float) Math.sqrt(1 + t * t);
                    float s = c * t;

                    a.set(p, q, a.get(p, p) - t * a.get(p, q));
                    a.set(q, q, a.get(q, q) + t * a.get(p, q));
                    a.set(p, q, 0f);
                    a.set(q, p, 0);

                    for (int r = 0; r < a.rows(); r++) {
                        float temp = x.get(r, p);
                        x.set(r, p, c * x.get(r, p) - s * x.get(r, q));
                        x.set(r, q, c * x.get(r, q) + s * temp);

                        if (r == p || r == q)
                            continue;

                        temp = a.get(r, p);
                        a.set(r, p, c * a.get(r, p) - s * a.get(r, q));
                        a.set(r, q, c * a.get(r, q) + s * temp);

                        a.set(p, r, a.get(r, p));
                        a.set(q, r, a.get(r, q));
                    }
                }
            }
        }
        return x;
    }

    @SuppressWarnings("DuplicatedCode")
    public static Matrixxd createJacobi(Matrixxd a) {
        Matrixxd x = diagD(a.rows());
        boolean flag = true;

        int k = 0;
        while (flag) {
            flag = false;
            for (int p = 0; p < a.rows(); p++) {
                for (int q = 0; q < a.rows(); q++) {
                    // skipp main diagonal elements
                    if (p == q)
                        continue;

                    // check for quasi zero elements
                    if ((Math.abs(a.get(p, q)) < Math.abs(a.get(p, q)) * 1e-9)
                            || (Math.abs(a.get(p, q)) < Math.abs(a.get(q, p)) * 1e-9)) {

                        continue;
                    } else
                        flag = true;

                    // generate t, s and c from matrix a
                    double theta = (a.get(q, p) - a.get(p, p)) / (2 * a.get(p, q));
                    double t = Math.signum(theta) / (Math.abs(theta) + Math.sqrt(theta * theta + 1));

                    double c = 1f / Math.sqrt(1 + t * t);
                    double s = c * t;

                    a.set(p, q, a.get(p, p) - t * a.get(p, q));
                    a.set(q, q, a.get(q, q) + t * a.get(p, q));
                    a.set(p, q, 0f);
                    a.set(q, p, 0);

                    for (int r = 0; r < a.rows(); r++) {
                        double temp = x.get(r, p);
                        x.set(r, p, c * x.get(r, p) - s * x.get(r, q));
                        x.set(r, q, c * x.get(r, q) + s * temp);

                        if (r == p || r == q)
                            continue;

                        temp = a.get(r, p);
                        a.set(r, p, c * a.get(r, p) - s * a.get(r, q));
                        a.set(r, q, c * a.get(r, q) + s * temp);

                        a.set(p, r, a.get(r, p));
                        a.set(q, r, a.get(r, q));
                    }
                }
            }
        }
        return x;
    }

    public static Matrixxf extractEigenvalues(Matrixxf a) {
        Matrixxf out = new Matrixxf(a.rows(), 1);
        for (int i = 0; i < a.rows(); i++)
            out.set(i, 0, a.get(i, i));
        return out;
    }

    public static Matrixxd extractEigenvalues(Matrixxd a) {
        Matrixxd out = new Matrixxd(a.rows(), 1);
        for (int i = 0; i < a.rows(); i++)
            out.set(i, 0, a.get(i, i));
        return out;
    }
}
