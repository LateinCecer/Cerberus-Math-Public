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

import java.util.Arrays;

@SuppressWarnings("Duplicates")
public class Matrix3f {

    private float[][] m;

    public Matrix3f() {
        m = new float[3][3];
    }

    public Matrix3f initIdentity() {
        m[0][0] = 1;    m[0][1] = 0;    m[0][2] = 0;
        m[1][0] = 0;    m[1][1] = 1;    m[1][2] = 0;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = 1;
        return this;
    }

    public Matrix3f initRotation(float x, float y, float z) {
        Matrix3f rx = new Matrix3f();
        Matrix3f ry = new Matrix3f();
        Matrix3f rz = new Matrix3f();

        rx.m[0][0] = 1;    rx.m[0][1] = 0;    rx.m[0][2] = 0;
        rx.m[1][0] = 0;    rx.m[1][1] = (float) Math.cos(x);    rx.m[1][2] = (float) - Math.sin(x);
        rx.m[2][0] = 0;    rx.m[2][1] = (float) Math.sin(x);    rx.m[2][2] = (float) Math.cos(x);

        ry.m[0][0] = (float) Math.cos(y);    ry.m[0][1] = 0;    ry.m[0][2] = (float) - Math.sin(y);
        ry.m[1][0] = 0;    ry.m[1][1] = 1;    ry.m[1][2] = 0;
        ry.m[2][0] = (float) Math.sin(y);    ry.m[2][1] = 0;    ry.m[2][2] = (float) Math.cos(y);

        rz.m[0][0] = (float) Math.cos(z);    rz.m[0][1] = (float) - Math.sin(z);    rz.m[0][2] = 0;
        rz.m[1][0] = (float) Math.sin(z);    rz.m[1][1] = (float) Math.cos(z);    rz.m[1][2] = 0;
        rz.m[2][0] = 0;    rz.m[2][1] = 0;    rz.m[2][2] = 1;

        m = rz.mul(ry.mul(rx)).getM();
        return this;
    }

    public Matrix3f initRotation(Vector3f rotation) {
        return initRotation(rotation.getX(), rotation.getY(), rotation.getZ());
    }

    public Matrix3f initRotation(Quaternionf rotation) {
        return rotation.toRotationMatrix().toReducedMatrix3f();
    }

    public Matrix3f initScale(float x, float y, float z) {
        m[0][0] = x;    m[0][1] = 0;    m[0][2] = 0;
        m[1][0] = 0;    m[1][1] = y;    m[1][2] = 0;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = z;
        return this;
    }

    public Matrix3f initScale(Vector3f scale) {
        return initScale(scale.getX(), scale.getY(), scale.getZ());
    }

    public Matrix3f initRotation(Vector3f forward, Vector3f up) {
        Vector3f f = forward.normalized();
        Vector3f r = up.normalized();
        r = r.cross(f);
        Vector3f u = f.cross(r);

        return initRotation(f, u, r);
    }

    public Matrix3f initRotation(Vector3f forward, Vector3f up, Vector3f right) {
        m[0][0] = right.getX();	    m[0][1] = right.getY();	    m[0][2] = right.getZ();
        m[1][0] = up.getX();	    m[1][1] = up.getY();	    m[1][2] = up.getZ();
        m[2][0] = forward.getX();	m[2][1] = forward.getY();	m[2][2] = forward.getZ();
        return this;
    }

    public Matrix3f initZero() {
        m[0][0] = 0;    m[0][1] = 0;    m[0][2] = 0;
        m[1][0] = 0;    m[1][1] = 0;    m[1][2] = 0;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = 0;
        return this;
    }

    public Matrix3f initInertia(Vector3f r, float mass) {
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

    public Matrix3f addInertia(Vector3f r, float mass) {
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

    public Matrix3f initInertia(Vector3f r) {
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

    public Matrix3f addInertia(Vector3f r) {
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

    public Matrix3f rref() {
        int i = 0;
        int j = 0;

        while (i < 3 && j < 3) {
            if (m[i][j] == 0) {
                float[] buffer = m[i];
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
                float pivot = m[i][j];
                for (int l = 0; l < 3; l++)
                    m[i][l] /= pivot;

                for (int y = 0; y < 3; y++) {
                    if (y != i) {
                        float mul = m[y][j];
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

    public float det() {
        return m[0][0] * m[1][1] * m[2][2] - m[0][2] * m[1][1] * m[2][0]
                + m[1][0] * m[2][1] * m[0][2] - m[1][2] * m[2][1] * m[0][0]
                + m[2][0] * m[0][1] * m[1][2] - m[2][2] * m[0][1] * m[1][0];
    }

    public boolean symmetric() {
        return m[1][0] == m[0][1] && m[2][0] == m[0][2] && m[2][1] == m[1][2];
    }

    public Matrix3f inverse() {
        float det = det();
        if (det == 0)
            return null;

        Matrix3f out = new Matrix3f();
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

    public Matrix3f adj() {
        Matrix3f out = new Matrix3f();
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

    public Matrix3f transpose() {
        Matrix3f out = new Matrix3f();
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

    public Matrix3f mul(Matrix3f other) {
        Matrix3f out = new Matrix3f();
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

    public Matrix3d mul3d(Matrix3f other) {
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

    public Vector3f mul(Vector3f vec) {
        return new Vector3f(
                vec.getX() * m[0][0] + vec.getY() * m[0][1] + vec.getZ() * m[0][2],
                vec.getX() * m[1][0] + vec.getY() * m[1][1] + vec.getZ() * m[1][2],
                vec.getX() * m[2][0] + vec.getY() * m[2][1] + vec.getZ() * m[2][2]
        );
    }

    public Vector3d mul3d(Vector3f vec) {
        return new Vector3d(
                vec.getX() * m[0][0] + vec.getY() * m[0][1] + vec.getZ() * m[0][2],
                vec.getX() * m[1][0] + vec.getY() * m[1][1] + vec.getZ() * m[1][2],
                vec.getX() * m[2][0] + vec.getY() * m[2][1] + vec.getZ() * m[2][2]
        );
    }

    public Vector3d mul(Vector3d vec) {
        return new Vector3d(
                vec.getX() * m[0][0] + vec.getY() * m[0][1] + vec.getZ() * m[0][2],
                vec.getX() * m[1][0] + vec.getY() * m[1][1] + vec.getZ() * m[1][2],
                vec.getX() * m[2][0] + vec.getY() * m[2][1] + vec.getZ() * m[2][2]
        );
    }

    public Vector3f mul3f(Vector3d vec) {
        return new Vector3f(
                (float) (vec.getX() * m[0][0] + vec.getY() * m[0][1] + vec.getZ() * m[0][2]),
                (float) (vec.getX() * m[1][0] + vec.getY() * m[1][1] + vec.getZ() * m[1][2]),
                (float) (vec.getX() * m[2][0] + vec.getY() * m[2][1] + vec.getZ() * m[2][2])
        );
    }

    public Matrix3f pow(int exp) {
        if (exp == 0)
            return new Matrix3f().initIdentity();
        else if (exp == 1)
            return this;
        else if (exp % 2 == 0) {
            Matrix3f mat = pow(exp / 2);
            return mat.mul(mat);
        } else {
            return this.mul(pow(exp - 1));
        }
    }

    public Matrix3f setM(float[][] m) {
        this.m = m;
        return this;
    }

    public float[][] getM() {
        return m;
    }

    public float get(int x, int y) {
        return m[x][y];
    }

    public Matrix3f set(int x, int y, float value) {
        m[x][y] = value;
        return this;
    }

    public Vector3f getRow(int index) {
        return new Vector3f(m[index][0], m[index][1], m[index][2]);
    }

    public Vector3f getCoulomb(int index) {
        return new Vector3f(m[0][index], m[1][index], m[2][index]);
    }

    public Matrix3f setRow(int index, Vector3f row) {
        m[index][0] = row.getX();
        m[index][1] = row.getY();
        m[index][2] = row.getZ();
        return this;
    }

    public Matrix3f setCoulomb(int index, Vector3f coulomb) {
        m[0][index] = coulomb.getX();
        m[1][index] = coulomb.getY();
        m[2][index] = coulomb.getZ();
        return this;
    }

    public Matrix3d toMatrix3d() {
        Matrix3d out = new Matrix3d();
        for (int row = 0; row < 3; row++)
            for (int coulomb = 0; coulomb < 3; coulomb++)
                out.set(row, coulomb, m[row][coulomb]);
        return out;
    }

    public Matrix3f copy() {
        Matrix3f copy = new Matrix3f();
        for (int row = 0; row < 3; row++)
            System.arraycopy(m[row], 0, copy.m[row], 0, 3);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix3f matrix3f = (Matrix3f) o;
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
