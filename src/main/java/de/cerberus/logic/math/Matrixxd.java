/*
 * Cerberus-Math is a simple OpenGL-compatible math library.
 * Visit https://cerberustek.com for more details
 * Copyright (c)  2020  Adrian Paskert
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
 * along with this program. See the file COPYING.txt included with this
 * distribution for more information.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package de.cerberus.logic.math;

import java.util.Arrays;

/**
 * Created by LateinCecker on 29.08.2016.
 */
@SuppressWarnings("DuplicatedCode")
public class Matrixxd {

    protected double m[][];

    public Matrixxd(int rows, int cols) {
        m = new double[rows][cols];
    }

    public Matrixxd(double[][] m) {
        this.m = m;
    }

    /**
     * This method will convert this matrix to a RREF (Reduced row-echelon form) using the Gauss-Jordan algorithm
     * @return converted matrix
     */
    @SuppressWarnings("DuplicatedCode")
    public Matrixxd rref() {
        Matrixxd copy = copy();

        int i = 0;
        int j = 0;

        while (i < rows() && j < columns()) {
            // check if the pivot element of the current row is zero.
            // If this is the case and there are other elements remaining
            // in the current column that are not zero, the current
            // pivot row has to be swapped with some other row in which
            // the pivot element for the current column is not zero.
            if (copy.m[i][j] == 0) {
                // find the next possible pivot element on the current
                // column that is not equal to zero
                double[] buffer = copy.m[i];
                for (int k = i + 1; k < rows(); k++) {
                    if (copy.m[k][j] != 0) {
                        copy.m[i] = copy.m[k];
                        copy.m[k] = buffer;
                        break;
                    }
                }

                // if all remaining elements on the current column are
                // zero, do nothing and move on with the next column
                if (buffer == copy.m[i])
                    j++;
            } else {
                // divide all elements on the current column by the
                // current pivot element
                double pivot = copy.m[i][j];
                for (int l = 0; l < columns(); l++)
                    copy.m[i][l] /= pivot;

                for (int y = 0; y < rows(); y++) {
                    if (y != i) {
                        // subtract the pivot row from all rows of the
                        // matrix except the pivot row itself
                        double mul = copy.m[y][j];
                        for (int x = 0; x < columns(); x++)
                            copy.m[y][x] -= mul * copy.m[i][x];
                    }
                }
                // move on with the next row nad column
                i++;
                j++;
            }
        }
        return copy;
    }

    @SuppressWarnings("DuplicatedCode")
    public Matrixxd smartRref() {
        Matrixxd copy = copy();

        int i = 0;
        int j = 0;

        while (i < rows() && j < columns()) {

            // find better pivot element (element of the current
            // column with the max absolute value)
            int permute = i;
            for (int l = i + 1; l < rows(); l++) {
                if (Math.abs(copy.m[l][j]) > Math.abs(copy.m[permute][j]))
                    permute = l;
            }

            if (permute != i) {
                // swap row "i" with row "permute"
                double[] buffer = copy.m[i];
                copy.m[i] = copy.m[permute];
                copy.m[permute] = buffer;
            } else if (m[i][j] == 0) {
                // if all remaining elements on the current column are
                // zero, do nothing and move on with the next column
                j++;
                continue;
            }

            // divide all elements on the current column by the
            // current pivot element
            double pivot = copy.m[i][j];
            for (int l = 0; l < columns(); l++)
                copy.m[i][l] /= pivot;

            for (int y = 0; y < rows(); y++) {
                if (y != i) {
                    // subtract the pivot row from all rows of the
                    // matrix except the pivot row itself
                    double mul = copy.m[y][j];
                    for (int x = 0; x < columns(); x++)
                        copy.m[y][x] -= mul * copy.m[i][x];
                }
            }
            // move on with the next row and column
            i++;
            j++;
        }
        return copy;
    }

    public Matrixxd factorize() {
        Matrixxd out = new Matrixxd(m.length, m[0].length);

        for (int j = 0; j < out.columns(); j++) {
            // compute entries for upper matrix for this column
            for (int i = 0; i <= j; i++) {
                double sum = 0;
                for (int k = 0; k <= i; k++)
                    sum += (k == i ? 1 : out.m[i][k]) * out.m[k][j];
                out.m[i][j] = m[i][j] - sum;
            }

            // compute entries for the lower matrix for this column
            for (int i = j + 1; i < out.rows(); i++) {
                double sum = 0;
                for (int k = 0; k < j; k++)
                    sum += (k == i ? 1 : out.m[i][k]) * out.m[k][j];
                out.m[i][j] = (m[i][j] - sum) / out.m[j][j];
            }
        }
        return out;
    }

    public Matrixxd cholesky() {
        Matrixxd out = new Matrixxd(rows(), columns());

        for (int j = 0; j < out.columns(); j++) {
            for (int i = 0; i < j; i++) {
                double sum = 0;
                for (int k = 0; k < i; k++)
                    sum += out.m[i][k] * out.m[j][k];
                out.m[j][i] = (m[j][i] - sum) / out.m[i][i];
            }
        }

        for (int j = 0; j < out.columns(); j++) {
            double sum = 0;
            for (int k = 0; k < j; k++)
                sum += out.m[j][k] * out.m[j][k];
            out.m[j][j] = (double) Math.sqrt(m[j][j] - sum);
        }
        return out;
    }

    public boolean isRRF() {
        for (int j = 1; j < rows(); j++) {
            for (int i = 0; i < j; i++) {
                if (m[j][i] != 0)
                    return false;
            }
        }
        return true;
    }

    public double det() {
        if (isRRF()) {
            return diagProduct();
        } else {
            return factorize().diagProduct();
        }
    }

    public double abs() {
        double sum = 0;
        for (int j = 0; j < columns(); j++) {
            for (int i = 0; i < rows(); i++)
                sum += m[i][j] * m[i][j];
        }
        return (double) Math.sqrt(sum);
    }

    public double absSquared() {
        double sum = 0;
        for (int j = 0; j < columns(); j++) {
            for (int i = 0; i < rows(); i++)
                sum += m[i][j] * m[i][j];
        }
        return sum;
    }

    public Matrixxd mul(Matrixxd other) {
        if (columns() != other.rows())
            throw new IllegalArgumentException("Invalid matrix format!");

        Matrixxd out = new Matrixxd(rows(), columns());

        for (int j = 0; j < rows(); j++) {
            // iterate through rows
            for (int i = 0; i < other.columns(); i++) {
                // iterate through columns
                double sum = 0;
                for (int k = 0; k < columns(); k++)
                    sum += m[j][k] * other.m[k][i];
                out.m[j][i] = sum;
            }
        }
        return out;
    }

    public Matrixxd mulSelf(Matrixxd other) {
        if (columns() != other.rows())
            throw new IllegalArgumentException("Invalid matrix format!");

        Matrixxd copy = copy();

        for (int columnOther = 0; columnOther < other.columns(); columnOther++) {
            for (int row = 0; row < copy.rows(); row++) {
                double sum = 0;
                for (int column = 0; column < copy.columns(); column++)
                    sum += copy.m[row][column] * other.m[column][columnOther];
                m[row][columnOther] = sum;
            }
        }
        return this;
    }

    public Matrixxd add(Matrixxd other) {
        if (columns() != other.columns() || rows() != other.rows())
            throw new IllegalArgumentException("Invalid matrix format!");

        Matrixxd out = new Matrixxd(rows(), columns());

        for (int row = 0; row < rows(); row++) {
            for (int column = 0; column < columns(); column++)
                out.m[row][column] = m[row][column] + other.m[row][column];
        }
        return out;
    }

    public Matrixxd addSelf(Matrixxd other) {
        if (columns() != other.columns() || rows() != other.rows())
            throw new IllegalArgumentException("Invalid matrix format!");

        for (int row = 0; row < rows(); row++) {
            for (int column = 0; column < columns(); column++)
                m[row][column] += other.m[row][column];
        }
        return this;
    }

    public Matrixxd sub(Matrixxd other) {
        if (columns() != other.columns() || rows() != other.rows())
            throw new IllegalArgumentException("Invalid matrix format!");

        Matrixxd out = new Matrixxd(rows(), columns());
        for (int row = 0; row < rows(); row++) {
            for (int column = 0; column < columns(); column++)
                out.m[row][column] = m[row][column] - other.m[row][column];
        }
        return out;
    }

    public Matrixxd subSelf(Matrixxd other) {
        if (columns() != other.columns() || rows() != other.rows())
            throw new IllegalArgumentException("Invalid matrix format!");

        Matrixxd out = new Matrixxd(rows(), columns());

        for (int row = 0; row < rows(); row++) {
            for (int column = 0; column < columns(); column++)
                m[row][column] -= other.m[row][column];
        }
        return out;
    }

    public Matrixxd mul(double f) {
        Matrixxd out = new Matrixxd(rows(), columns());

        for (int r = 0; r < rows(); r++) {
            for (int c = 0; c < columns(); c++)
                out.m[r][c] = m[r][c] * f;
        }
        return out;
    }

    public Matrixxd mulSelf(double f) {
        for (int r = 0; r < rows(); r++) {
            for (int c = 0; c < columns(); c++) {
                m[r][c] *= f;
            }
        }
        return this;
    }

    public Matrixxd add(double f) {
        Matrixxd out = new Matrixxd(rows(), columns());

        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < columns(); j++)
                out.m[i][j] = m[i][j] + f;
        }
        return out;
    }

    public Matrixxd addSelf(double f) {
        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < columns(); j++)
                m[i][j] += f;
        }
        return this;
    }

    public Matrixxd sub(double f) {
        Matrixxd out = new Matrixxd(rows(), columns());

        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < columns(); j++)
                out.m[i][j] = m[i][j] - f;
        }
        return out;
    }

    public Matrixxd subSelf(double sub) {
        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < columns(); j++)
                m[i][j] -= sub;
        }
        return this;
    }

    /**
     * Will append the specified matrix to the right side of this matrix.
     *
     * This will only work if the number of rows is the same for both
     * matrices.

     * @param other matrix to append
     * @throws IllegalArgumentException is thrown if the number of rows
     *          is not the same for both matrices
     * @return matrix
     */
    public Matrixxd push(Matrixxd other) {
        if (rows() != other.rows())
            throw new IllegalArgumentException("Invalid matrix format");

        Matrixxd out = new Matrixxd(rows(), columns() + other.columns());

        for (int column = 0; column < columns(); column++) {
            for (int row = 0; row < rows(); row++)
                out.m[row][column] = m[row][column];
        }

        for (int column = 0; column < other.columns(); column++) {
            for (int row = 0; row < rows(); row++)
                out.m[row][columns() + column] = other.m[row][column];
        }
        return out;
    }

    public Matrixxd getColumn(int i) {
        if (i >= columns())
            throw new IllegalArgumentException("That column does not exit");

        Matrixxd out = new Matrixxd(rows(), 1);

        for (int row = 0; row < rows(); row++)
            out.m[row][0] = m[row][i];
        return out;
    }

    public Vector3d mul(Vector3d vector3f) {
        if (getWidth() == 3 && getHeight() == 3) {
            return new Vector3d(
                    vector3f.getX() * (m[0][0] + m[1][0] + m[2][0]),
                    vector3f.getY() * (m[0][0] + m[1][0] + m[2][0]),
                    vector3f.getZ() * (m[0][0] + m[1][0] + m[2][0]));
        }
        return null;
    }

    public double diagProduct() {
        if (rows() != columns() || rows() == 0)
            throw new IllegalStateException("Matrix is not symmetrical");

        double prod = m[0][0];
        for (int i = 1; i < rows() && i < columns(); i++)
            prod *= m[i][i];
        return prod;
    }

    public boolean isDefinite() {
        for (int i = 0; i < getWidth() - 1 && i < getHeight(); i++) {
            if (m[i][i] != 1)
                return false;

            for (int j = 0; j < getHeight(); j++) {
                if (j != i && m[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public double distance() {
        return m[2][getWidth() - 1];
    }

    public Vector2d hitCoords() {
        return new Vector2d(m[0][getWidth() - 1], m[1][getWidth() - 1]);
    }

    public boolean hitTriangle(double scale) {
        return Math.abs(m[0][getWidth() - 1]) + Math.abs(m[1][getWidth() - 1]) <= scale;
    }

    public boolean hitQuad(double scale) {
        return Math.abs(m[0][getWidth() - 1]) <= scale && Math.abs(m[1][getWidth() - 1]) <= scale;
    }

    public void set(double[][] m) {
        this.m = m;
    }

    public void set(int x, int y, double value) {
        m[x][y] = value;
    }

    public double get(int x, int y) {
        return m[x][y];
    }

    public int getWidth() {
        return m[0].length;
    }

    public int getHeight() {
        return m.length;
    }

    public int rows() {
        return m.length;
    }

    public int columns() {
        return m[0].length;
    }

    public Matrixxd copy() {
        Matrixxd out = new Matrixxd(rows(), columns());

        for (int i = 0; i < rows(); i++) {
            System.arraycopy(m[i], 0, out.m[i], 0, columns());
        }
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrixxd)) return false;
        Matrixxd matrixxf = (Matrixxd) o;
        return Arrays.equals(m, matrixxf.m);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(m);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (int i = 0; i < getHeight(); i++) {
            stringBuilder.append("\n");
            for (int j = 0; j < getWidth(); j++)
                stringBuilder.append("\t").append(m[i][j]);
        }
        stringBuilder.append("\n]");
        return stringBuilder.toString();
    }
}
