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

@SuppressWarnings("Duplicates")
public class Matrix3d {

    private double[][] m;

    public Matrix3d() {
        m = new double[3][3];
    }

    public Matrix3d initIdentity() {
        m[0][0] = 1;    m[0][1] = 0;    m[0][2] = 0;
        m[1][0] = 0;    m[1][1] = 1;    m[1][2] = 0;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = 1;
        return this;
    }

    public Matrix3d initRotation(double x, double y, double z) {
        Matrix3d rx = new Matrix3d();
        Matrix3d ry = new Matrix3d();
        Matrix3d rz = new Matrix3d();

        rx.m[0][0] = 1;    rx.m[0][1] = 0;    rx.m[0][2] = 0;
        rx.m[1][0] = 0;    rx.m[1][1] = Math.cos(x);    rx.m[1][2] = - Math.sin(x);
        rx.m[2][0] = 0;    rx.m[2][1] = Math.sin(x);    rx.m[2][2] = Math.cos(x);

        ry.m[0][0] = Math.cos(y);    ry.m[0][1] = 0;    ry.m[0][2] = - Math.sin(y);
        ry.m[1][0] = 0;    ry.m[1][1] = 1;    ry.m[1][2] = 0;
        ry.m[2][0] = Math.sin(y);    ry.m[2][1] = 0;    ry.m[2][2] = Math.cos(y);

        rz.m[0][0] = Math.cos(z);    rz.m[0][1] = - Math.sin(z);    rz.m[0][2] = 0;
        rz.m[1][0] = Math.sin(z);    rz.m[1][1] = Math.cos(z);    rz.m[1][2] = 0;
        rz.m[2][0] = 0;    rz.m[2][1] = 0;    rz.m[2][2] = 1;

        m = rz.mul(ry.mul(rx)).getM();
        return this;
    }

    public Matrix3d initRotation(Vector3d rotation) {
        return initRotation(rotation.getX(), rotation.getY(), rotation.getZ());
    }

    public Matrix3d initRotation(Quaterniond rotation) {
        return rotation.toRotationMatrix().toReducedMatrix3d();
    }

    public Matrix3d initScale(double x, double y, double z) {
        m[0][0] = x;    m[0][1] = 0;    m[0][2] = 0;
        m[1][0] = 0;    m[1][1] = y;    m[1][2] = 0;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = z;
        return this;
    }

    public Matrix3d initScale(Vector3d scale) {
        return initScale(scale.getX(), scale.getY(), scale.getZ());
    }

    public Matrix3d initRotation(Vector3d forward, Vector3d up) {
        Vector3d f = forward.normalized();
        Vector3d r = up.normalized();
        r = r.cross(f);
        Vector3d u = f.cross(r);

        return initRotation(f, u, r);
    }

    public Matrix3d initRotation(Vector3d forward, Vector3d up, Vector3d right) {
        m[0][0] = right.getX();	    m[0][1] = right.getY();	    m[0][2] = right.getZ();
        m[1][0] = up.getX();	    m[1][1] = up.getY();	    m[1][2] = up.getZ();
        m[2][0] = forward.getX();	m[2][1] = forward.getY();	m[2][2] = forward.getZ();
        return this;
    }

    public Matrix3d initZero() {
        m[0][0] = 0;    m[0][1] = 0;    m[0][2] = 0;
        m[1][0] = 0;    m[1][1] = 0;    m[1][2] = 0;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = 0;
        return this;
    }

    public Matrix3d initInertia(Vector3d r, double mass) {
        m[0][0] = (r.getY() * r.getY() + r.getZ() * r.getZ()) * mass;
        m[1][0] = - r.getY() * r.getX() * mass;
        m[2][0] = - r.getZ() * r.getX() * mass;

        m[0][1] = m[1][0];
        m[1][1] = (r.getX() * r.getX() + r.getZ() * r.getZ()) * mass;
        m[2][1] = - r.getZ() * r.getY() * mass;

        m[0][2] = m[2][0];
        m[1][2] = m[2][1];
        m[2][2] = (r.getX() * r.getX() + r.getY() * r.getY()) * mass;
        return this;
    }

    public Matrix3d addInertia(Vector3d r, double mass) {
        m[0][0] += (r.getY() * r.getY() + r.getZ() * r.getZ()) * mass;
        m[1][0] += - r.getY() * r.getX() * mass;
        m[2][0] += - r.getZ() * r.getX() * mass;

        m[0][1] += m[1][0];
        m[1][1] += (r.getX() * r.getX() + r.getZ() * r.getZ()) * mass;
        m[2][1] += - r.getZ() * r.getY() * mass;

        m[0][2] += m[2][0];
        m[1][2] += m[2][1];
        m[2][2] += (r.getX() * r.getX() + r.getY() * r.getY()) * mass;
        return this;
    }

    public Matrix3d initInertia(Vector3d r) {
        m[0][0] = r.getY() * r.getY() + r.getZ() * r.getZ();
        m[1][0] = - r.getY() * r.getX();
        m[2][0] = - r.getZ() * r.getX();

        m[0][1] = m[1][0];
        m[1][1] = r.getX() * r.getX() + r.getZ() * r.getZ();
        m[2][1] = - r.getZ() * r.getY();

        m[0][2] = m[2][0];
        m[1][2] = m[2][1];
        m[2][2] = r.getX() * r.getX() + r.getY() * r.getY();
        return this;
    }

    public Matrix3d addInertia(Vector3d r) {
        m[0][0] += r.getY() * r.getY() + r.getZ() * r.getZ();
        m[1][0] += - r.getY() * r.getX();
        m[2][0] += - r.getZ() * r.getX();

        m[0][1] += m[1][0];
        m[1][1] += r.getX() * r.getX() + r.getZ() * r.getZ();
        m[2][1] += - r.getZ() * r.getY();

        m[0][2] += m[2][0];
        m[1][2] += m[2][1];
        m[2][2] += r.getX() * r.getX() + r.getY() * r.getY();
        return this;
    }

    public Matrix3d rref() {
        int i = 0;
        int j = 0;

        while (i < 3 && j < 3) {
            if (m[i][j] == 0) {
                double[] buffer = m[i];
                for (int k = i; k < 3; k++) {
                    if (m[k][j] != 0) {
                        m[i] = m[k];
                        m[k] = buffer;
                        break;
                    }
                }
                if (buffer == m[i])
                    j++;
            } else {
                double pivot = m[i][j];
                for (int l = 0; l < 3; l++)
                    m[i][l] /= pivot;

                for (int y = 0; y < 3; y++) {
                    if (y != i) {
                        double mul = m[y][j];
                        for (int x = 0; x < 3; x++)
                            m[y][x] -= mul * m[i][x];
                    }
                }
                i++;
                j++;
            }
        }
        return this;
    }

    public double det() {
        return m[0][0] * m[1][1] * m[2][2] - m[0][2] * m[1][1] * m[2][0]
                + m[1][0] * m[2][1] * m[0][2] - m[1][2] * m[2][1] * m[0][0]
                + m[2][0] * m[0][1] * m[1][2] - m[2][2] * m[0][1] * m[1][0];
    }

    public boolean symmetric() {
        return m[1][0] == m[0][1] && m[2][0] == m[0][2] && m[2][1] == m[1][2];
    }

    public Matrix3d inverse() {
        double det = det();
        if (det == 0)
            return null;

        Matrix3d out = new Matrix3d();
        if (symmetric()) {
            out.m[0][0] = (m[1][1] * m[2][2] - m[1][2] * m[1][2]) / det;
            out.m[1][0] = (m[1][2] * m[2][0] - m[0][1] * m[2][2]) / det;
            out.m[2][0] = (m[0][1] * m[1][2] - m[1][1] * m[2][0]) / det;

            out.m[0][1] = out.m[1][0];
            out.m[1][1] = (m[0][0] * m[2][2] - m[2][0] * m[2][0]) / det;
            out.m[2][1] = (m[0][1] * m[2][0] - m[0][0] * m[1][2]) / det;

            out.m[0][2] = out.m[2][0];
            out.m[1][2] = out.m[2][1];
            out.m[2][2] = (m[0][0] * m[1][1] - m[0][1] * m[0][1]) / det;
        } else {
            out.m[0][0] = (m[1][1] * m[2][2] - m[1][2] * m[2][1]) / det;
            out.m[1][0] = (m[1][2] * m[2][0] - m[1][0] * m[2][2]) / det;
            out.m[2][0] = (m[1][0] * m[2][1] - m[1][1] * m[2][0]) / det;

            out.m[0][1] = (m[0][2] * m[2][1] - m[0][1] * m[2][2]) / det;
            out.m[1][1] = (m[0][0] * m[2][2] - m[0][2] * m[2][0]) / det;
            out.m[2][1] = (m[0][1] * m[2][0] - m[0][0] * m[2][1]) / det;

            out.m[0][2] = (m[0][1] * m[1][2] - m[0][2] * m[1][1]) / det;
            out.m[1][2] = (m[0][2] * m[1][0] - m[0][0] * m[1][2]) / det;
            out.m[2][2] = (m[0][0] * m[1][1] - m[0][1] * m[1][0]) / det;
        }
        return out;
    }

    public Matrix3d adj() {
        Matrix3d out = new Matrix3d();
        if (symmetric()) {
            out.m[0][0] = m[1][1] * m[2][2] - m[1][2] * m[1][2];
            out.m[1][0] = m[1][2] * m[2][0] - m[0][1] * m[2][2];
            out.m[2][0] = m[0][1] * m[1][2] - m[1][1] * m[2][0];

            out.m[0][1] = out.m[1][0];
            out.m[1][1] = m[0][0] * m[2][2] - m[2][0] * m[2][0];
            out.m[2][1] = m[0][1] * m[2][0] - m[0][0] * m[1][2];

            out.m[0][2] = out.m[2][0];
            out.m[1][2] = out.m[2][1];
            out.m[2][2] = m[0][0] * m[1][1] - m[0][1] * m[0][1];
        } else {
            out.m[0][0] = m[1][1] * m[2][2] - m[1][2] * m[2][1];
            out.m[1][0] = m[1][2] * m[2][0] - m[1][0] * m[2][2];
            out.m[2][0] = m[1][0] * m[2][1] - m[1][1] * m[2][0];

            out.m[0][1] = m[0][2] * m[2][1] - m[0][1] * m[2][2];
            out.m[1][1] = m[0][0] * m[2][2] - m[0][2] * m[2][0];
            out.m[2][1] = m[0][1] * m[2][0] - m[0][0] * m[2][1];

            out.m[0][2] = m[0][1] * m[1][2] - m[0][2] * m[1][1];
            out.m[1][2] = m[0][2] * m[1][0] - m[0][0] * m[1][2];
            out.m[2][2] = m[0][0] * m[1][1] - m[0][1] * m[1][0];
        }
        return out;
    }

    public Matrix3d transpose() {
        Matrix3d out = new Matrix3d();
        out.m[0][0] = m[0][0];
        out.m[1][0] = m[0][1];
        out.m[2][0] = m[0][2];

        out.m[0][1] = m[1][0];
        out.m[1][1] = m[1][1];
        out.m[2][1] = m[1][2];

        out.m[0][2] = m[2][0];
        out.m[1][2] = m[2][1];
        out.m[2][2] = m[2][2];
        return out;
    }

    public Matrix3d mul(Matrix3d other) {
        Matrix3d out = new Matrix3d();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                out.set(i, j,
                        m[i][0] * other.get(0, j) +
                                m[i][1] * other.get(1, j) +
                                m[i][2] * other.get(2, j));
            }
        }

        return out;
    }

    public Matrix3d mul(Matrix3f other) {
        Matrix3d out = new Matrix3d();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                out.set(i, j,
                        m[i][0] * other.get(0, j) +
                                m[i][1] * other.get(1, j) +
                                m[i][2] * other.get(2, j));
            }
        }

        return out;
    }

    public Vector3d mul(Vector3d vec) {
        return new Vector3d(
                vec.getX() * m[0][0] + vec.getY() * m[0][1] + vec.getZ() * m[0][2],
                vec.getX() * m[1][0] + vec.getY() * m[1][1] + vec.getZ() * m[1][2],
                vec.getX() * m[2][0] + vec.getY() * m[2][1] + vec.getZ() * m[2][2]
        );
    }

    public Vector3d mul(Vector3f vec) {
        return new Vector3d(
                vec.getX() * m[0][0] + vec.getY() * m[0][1] + vec.getZ() * m[0][2],
                vec.getX() * m[1][0] + vec.getY() * m[1][1] + vec.getZ() * m[1][2],
                vec.getX() * m[2][0] + vec.getY() * m[2][1] + vec.getZ() * m[2][2]
        );
    }

    public Matrix3f mul3f(Matrix3d other) {
        Matrix3f out = new Matrix3f();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                out.set(i, j,
                        (float) (m[i][0] * other.get(0, j) +
                                m[i][1] * other.get(1, j) +
                                m[i][2] * other.get(2, j)));
            }
        }

        return out;
    }

    public Matrix3f mul3f(Matrix3f other) {
        Matrix3f out = new Matrix3f();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                out.set(i, j,
                        (float) (m[i][0] * other.get(0, j) +
                                m[i][1] * other.get(1, j) +
                                m[i][2] * other.get(2, j)));
            }
        }

        return out;
    }

    public Vector3f mul3f(Vector3d vec) {
        return new Vector3f(
                (float) (vec.getX() * m[0][0] + vec.getY() * m[0][1] + vec.getZ() * m[0][2]),
                (float) (vec.getX() * m[1][0] + vec.getY() * m[1][1] + vec.getZ() * m[1][2]),
                (float) (vec.getX() * m[2][0] + vec.getY() * m[2][1] + vec.getZ() * m[2][2])
        );
    }

    public Vector3f mul3f(Vector3f vec) {
        return new Vector3f(
                (float) (vec.getX() * m[0][0] + vec.getY() * m[0][1] + vec.getZ() * m[0][2]),
                (float) (vec.getX() * m[1][0] + vec.getY() * m[1][1] + vec.getZ() * m[1][2]),
                (float) (vec.getX() * m[2][0] + vec.getY() * m[2][1] + vec.getZ() * m[2][2])
        );
    }

    public Matrix3d pow(int exp) {
        if (exp == 0)
            return new Matrix3d().initIdentity();
        else if (exp == 1)
            return this;
        else if (exp % 2 == 0) {
            Matrix3d mat = pow(exp / 2);
            return mat.mul(mat);
        } else {
            return this.mul(pow(exp - 1));
        }
    }

    public Matrix3d setM(double[][] m) {
        this.m = m;
        return this;
    }

    public double[][] getM() {
        return m;
    }

    public double get(int x, int y) {
        return m[x][y];
    }

    public Matrix3d set(int x, int y, double value) {
        m[x][y] = value;
        return this;
    }

    public Vector3d getRow(int index) {
        return new Vector3d(m[index][0], m[index][1], m[index][2]);
    }

    public Vector3d getCoulomb(int index) {
        return new Vector3d(m[0][index], m[1][index], m[2][index]);
    }

    public Matrix3d setRow(int index, Vector3d row) {
        m[index][0] = row.getX();
        m[index][1] = row.getY();
        m[index][2] = row.getZ();
        return this;
    }

    public Matrix3d setCoulomb(int index, Vector3d coulomb) {
        m[0][index] = coulomb.getX();
        m[1][index] = coulomb.getY();
        m[2][index] = coulomb.getZ();
        return this;
    }

    public Matrix3f toMatrix3f() {
        Matrix3f out = new Matrix3f();
        for (int row = 0; row < 3; row++)
            for (int coulomb = 0; coulomb < 3; coulomb++)
                out.set(row, coulomb, (float) m[row][coulomb]);
        return out;
    }

    public Matrix3d copy() {
        Matrix3d copy = new Matrix3d();
        for (int row = 0; row < 3; row++)
            System.arraycopy(m[row], 0, copy.m[row], 0, 3);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix3d matrix3f = (Matrix3d) o;
        return Arrays.equals(m, matrix3f.m);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(m);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (int i = 0; i < 3; i++) {
            stringBuilder.append("\n");
            for (int j = 0; j < 3; j++)
                stringBuilder.append("\t").append(m[i][j]);
        }
        stringBuilder.append("\n]");
        return stringBuilder.toString();
    }
}
