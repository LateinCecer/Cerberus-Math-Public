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

/**
 * Created by LateinCecker on 02.03.2016.
 */
@SuppressWarnings("Duplicates")
public class Matrix4f {

    private float[][] m;

    public Matrix4f() {
        m = new float[4][4];
    }

    public Matrix4f initIdentity() {
        m[0][0] = 1;    m[0][1] = 0;    m[0][2] = 0;    m[0][3] = 0;
        m[1][0] = 0;    m[1][1] = 1;    m[1][2] = 0;    m[1][3] = 0;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = 1;    m[2][3] = 0;
        m[3][0] = 0;    m[3][1] = 0;    m[3][2] = 0;    m[3][3] = 1;
        return this;
    }

    public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far) {
        float width = right - left;
        float height = top - bottom;
        float depth = far - near;

        m[0][0] = 2 / width;    m[0][1] = 0;            m[0][2] = 0;            m[0][3] = - (right + left) / width;
        m[1][0] = 0;            m[1][1] = 2 / height;   m[1][2] = 0;            m[1][3] = - (top + bottom) / height;
        m[2][0] = 0;            m[2][1] = 0;            m[2][2] = - 2 / depth;  m[2][3] = - (far + near) / depth;
        m[3][0] = 0;            m[3][1] = 0;            m[3][2] = 0;            m[3][3] = 1;
        return this;
    }

    public Matrix4f initInverseOrthographic(float left, float right, float bottom, float top, float near, float far) {
        float width = right - left;
        float height = top - bottom;
        float depth = far - near;

        m[0][0] = width / 2;    m[0][1] = 0;            m[0][2] = 0;            m[0][3] = (right + left) / 2;
        m[1][0] = 0;            m[1][1] = height / 2;   m[1][2] = 0;            m[1][3] = (top + bottom) / 2;
        m[2][0] = 0;            m[2][1] = 0;            m[2][2] = - depth / 2;  m[2][3] = - (far + near) / 2;
        m[3][0] = 0;            m[3][1] = 0;            m[3][2] = 0;            m[3][3] = 1;
        return this;
    }

    public Matrix4f initTranslation(float x, float y, float z) {
        m[0][0] = 1;    m[0][1] = 0;    m[0][2] = 0;    m[0][3] = x;
        m[1][0] = 0;    m[1][1] = 1;    m[1][2] = 0;    m[1][3] = y;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = 1;    m[2][3] = z;
        m[3][0] = 0;    m[3][1] = 0;    m[3][2] = 0;    m[3][3] = 1;
        return this;
    }

    public Matrix4f initInverseTranslation(float x, float y, float z) {
        m[0][0] = 1;    m[0][1] = 0;    m[0][2] = 0;    m[0][3] = -x;
        m[1][0] = 0;    m[1][1] = 1;    m[1][2] = 0;    m[1][3] = -y;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = 1;    m[2][3] = -z;
        m[3][0] = 0;    m[3][1] = 0;    m[3][2] = 0;    m[3][3] = 1;
        return this;
    }

    public Matrix4f initTranslation(Vector3f translation) {
        return initTranslation(translation.getX(), translation.getY(), translation.getZ());
    }

    public Matrix4f initInverseTranslation(Vector3f translation) {
        return initInverseTranslation(translation.getX(), translation.getY(), translation.getZ());
    }

    public Matrix4f initRotation(float x, float y, float z) {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        rx.m[0][0] = 1;    rx.m[0][1] = 0;    rx.m[0][2] = 0;    rx.m[0][3] = 0;
        rx.m[1][0] = 0;    rx.m[1][1] = (float) Math.cos(x);    rx.m[1][2] = (float) - Math.sin(x);    rx.m[1][3] = 0;
        rx.m[2][0] = 0;    rx.m[2][1] = (float) Math.sin(x);    rx.m[2][2] = (float) Math.cos(x);    rx.m[2][3] = 0;
        rx.m[3][0] = 0;    rx.m[3][1] = 0;    rx.m[3][2] = 0;    rx.m[3][3] = 1;

        ry.m[0][0] = (float) Math.cos(y);    ry.m[0][1] = 0;    ry.m[0][2] = (float) - Math.sin(y);    ry.m[0][3] = 0;
        ry.m[1][0] = 0;    ry.m[1][1] = 1;    ry.m[1][2] = 0;    ry.m[1][3] = 0;
        ry.m[2][0] = (float) Math.sin(y);    ry.m[2][1] = 0;    ry.m[2][2] = (float) Math.cos(y);    ry.m[2][3] = 0;
        ry.m[3][0] = 0;    ry.m[3][1] = 0;    ry.m[3][2] = 0;    ry.m[3][3] = 1;

        rz.m[0][0] = (float) Math.cos(z);    rz.m[0][1] = (float) - Math.sin(z);    rz.m[0][2] = 0;    rz.m[0][3] = 0;
        rz.m[1][0] = (float) Math.sin(z);    rz.m[1][1] = (float) Math.cos(z);    rz.m[1][2] = 0;    rz.m[1][3] = 0;
        rz.m[2][0] = 0;    rz.m[2][1] = 0;    rz.m[2][2] = 1;    rz.m[2][3] = 0;
        rz.m[3][0] = 0;    rz.m[3][1] = 0;    rz.m[3][2] = 0;    rz.m[3][3] = 1;

        m = rz.mul(ry.mul(rx)).getM();
        return this;
    }

    public Matrix4f initRotation(Vector3f rotation) {
        return initRotation(rotation.getX(), rotation.getY(), rotation.getZ());
    }

    public Matrix4f initRotation(Quaternionf rotation) {
        return rotation.toRotationMatrix();
    }

    public Matrix4f initScale(float x, float y, float z) {
        m[0][0] = x;    m[0][1] = 0;    m[0][2] = 0;    m[0][3] = 0;
        m[1][0] = 0;    m[1][1] = y;    m[1][2] = 0;    m[1][3] = 0;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = z;    m[2][3] = 0;
        m[3][0] = 0;    m[3][1] = 0;    m[3][2] = 0;    m[3][3] = 1;
        return this;
    }

    public Matrix4f initInverseScale(float x, float y, float z) {
        m[0][0] = x != 0 ? 1f / x : 1f;    m[0][1] = 0;    m[0][2] = 0;    m[0][3] = 0;
        m[1][0] = 0;    m[1][1] = y != 0 ? 1f / y : 1f;    m[1][2] = 0;    m[1][3] = 0;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = z != 0 ? 1f / z : 1f;    m[2][3] = 0;
        m[3][0] = 0;    m[3][1] = 0;    m[3][2] = 0;    m[3][3] = 1;
        return this;
    }

    public Matrix4f initScale(Vector3f scale) {
        return initScale(scale.getX(), scale.getY(), scale.getZ());
    }

    public Matrix4f initInverseScale(Vector3f scale) {
        return initInverseScale(scale.getX(), scale.getY(), scale.getZ());
    }

    public Matrix4f initProjection(float fov, float aspect, float zNear, float zFar) {
        float tanHalfFov = (float) Math.tan(fov / 2);
        float zRange = zNear - zFar;

        m[0][0] = 1 / (tanHalfFov * aspect);    m[0][1] = 0;    m[0][2] = 0;    m[0][3] = 0;
        m[1][0] = 0;    m[1][1] = 1 / tanHalfFov;    m[1][2] = 0;    m[1][3] = 0;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = (-zNear - zFar) / zRange;    m[2][3] = 2 * zFar * zNear / zRange;
        m[3][0] = 0;    m[3][1] = 0;    m[3][2] = 1;    m[3][3] = 0;
        return this;
    }

    public Matrix4f initInverseProjection(float fov, float aspect, float zNear, float zFar) {
        float tanHalfFov = (float) Math.tan(fov / 2);
        float zRange = zNear - zFar;

        m[0][0] = tanHalfFov * aspect;     m[0][1] = 0;            m[0][2] = 0;                             m[0][3] = 0;
        m[1][0] = 0;                       m[1][1] = tanHalfFov;   m[1][2] = 0;                             m[1][3] = 0;
        m[2][0] = 0;                       m[2][1] = 0;            m[2][2] = 0;                             m[2][3] = 1;
        m[3][0] = 0;                       m[3][1] = 0;            m[3][2] = zRange / (2 * zFar * zNear);   m[3][3] = (zNear + zFar) / (2 * zFar * zNear);
        return this;
    }

    public Matrix4f initRotation(Vector3f forward, Vector3f up) {
        Vector3f f = forward.normalized();
        Vector3f r = up.normalized();
        r = r.cross(f);
        Vector3f u = f.cross(r);

        return initRotation(f, u, r);
    }

    public Matrix4f initRotation(Vector3f forward, Vector3f up, Vector3f right)
    {
        m[0][0] = right.getX();	    m[0][1] = right.getY();	    m[0][2] = right.getZ();	    m[0][3] = 0;
        m[1][0] = up.getX();	    m[1][1] = up.getY();	    m[1][2] = up.getZ();	    m[1][3] = 0;
        m[2][0] = forward.getX();	m[2][1] = forward.getY();	m[2][2] = forward.getZ();	m[2][3] = 0;
        m[3][0] = 0;		        m[3][1] = 0;		        m[3][2] = 0;		        m[3][3] = 1;

        return this;
    }

    public Matrix4f rref() {
        int i = 0;
        int j = 0;

        while (i < 4 && j < 4) {
            if (m[i][j] == 0) {
                float[] buffer = m[i];
                for (int k = i; k < 4; k++) {
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
                for (int l = 0; l < 4; l++)
                    m[i][l] /= pivot;

                for (int y = 0; y < 4; y++) {
                    if (y != i) {
                        float mul = m[y][j];
                        for (int x = 0; x < 4; x++)
                            m[y][x] -= mul * m[i][x];
                    }
                }
                i++;
                j++;
            }
        }
        return this;
    }

    public Matrix4f mul(Matrix4f other) {
        Matrix4f out = new Matrix4f();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                out.set(i, j,
                        m[i][0] * other.get(0, j) +
                        m[i][1] * other.get(1, j) +
                        m[i][2] * other.get(2, j) +
                        m[i][3] * other.get(3, j));
            }
        }

        return out;
    }

    public Matrix4d mul3d(Matrix4f other) {
        Matrix4d out = new Matrix4d();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                out.set(i, j,
                        m[i][0] * other.get(0, j) +
                                m[i][1] * other.get(1, j) +
                                m[i][2] * other.get(2, j) +
                                m[i][3] * other.get(3, j));
            }
        }

        return out;
    }

    public Matrix4d mul(Matrix4d other) {
        Matrix4d out = new Matrix4d();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                out.set(i, j,
                        m[i][0] * other.get(0, j) +
                                m[i][1] * other.get(1, j) +
                                m[i][2] * other.get(2, j) +
                                m[i][3] * other.get(3, j));
            }
        }

        return out;
    }

    public Matrix4f mul3f(Matrix4d other) {
        Matrix4f out = new Matrix4f();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                out.set(i, j,
                        (float) (m[i][0] * other.get(0, j) +
                                m[i][1] * other.get(1, j) +
                                m[i][2] * other.get(2, j) +
                                m[i][3] * other.get(3, j)));
            }
        }
        return out;
    }

    public Vector4f mul(Vector4f vec) {
        return new Vector4f(
                vec.getX() * m[0][0] + vec.getY() * m[0][1] + vec.getZ() * m[0][2] + vec.getW() * m[0][3],
                vec.getX() * m[1][0] + vec.getY() * m[1][1] + vec.getZ() * m[1][2] + vec.getW() * m[1][3],
                vec.getX() * m[2][0] + vec.getY() * m[2][1] + vec.getZ() * m[2][2] + vec.getW() * m[2][3],
                vec.getX() * m[3][0] + vec.getY() * m[3][1] + vec.getZ() * m[3][2] + vec.getW() * m[3][3]
        );
    }

    public Vector3f mul(Vector3f vec) {
        Vector3f out = new Vector3f(
                vec.getX() * m[0][0] + vec.getY() * m[0][1] + vec.getZ() * m[0][2] + m[0][3],
                vec.getX() * m[1][0] + vec.getY() * m[1][1] + vec.getZ() * m[1][2] + m[1][3],
                vec.getX() * m[2][0] + vec.getY() * m[2][1] + vec.getZ() * m[2][2] + m[2][3]
        );
        float w = vec.getX() * m[3][0] + vec.getY() * m[3][1] + vec.getZ() * m[3][2] + m[3][3];
        return out.div(w);
    }

    public Vector4d mul3d(Vector4f vec) {
        return new Vector4d(
                vec.getX() * m[0][0] + vec.getY() * m[0][1] + vec.getZ() * m[0][2] + vec.getW() * m[0][3],
                vec.getX() * m[1][0] + vec.getY() * m[1][1] + vec.getZ() * m[1][2] + vec.getW() * m[1][3],
                vec.getX() * m[2][0] + vec.getY() * m[2][1] + vec.getZ() * m[2][2] + vec.getW() * m[2][3],
                vec.getX() * m[3][0] + vec.getY() * m[3][1] + vec.getZ() * m[3][2] + vec.getW() * m[3][3]
        );
    }

    public Vector4d mul(Vector4d vec) {
        return new Vector4d(
                vec.getX() * m[0][0] + vec.getY() * m[0][1] + vec.getZ() * m[0][2] + vec.getW() * m[0][3],
                vec.getX() * m[1][0] + vec.getY() * m[1][1] + vec.getZ() * m[1][2] + vec.getW() * m[1][3],
                vec.getX() * m[2][0] + vec.getY() * m[2][1] + vec.getZ() * m[2][2] + vec.getW() * m[2][3],
                vec.getX() * m[3][0] + vec.getY() * m[3][1] + vec.getZ() * m[3][2] + vec.getW() * m[3][3]
        );
    }

    public Vector3d mul(Vector3d vec) {
        Vector3d out = new Vector3d(
                vec.getX() * m[0][0] + vec.getY() * m[0][1] + vec.getZ() * m[0][2] + m[0][3],
                vec.getX() * m[1][0] + vec.getY() * m[1][1] + vec.getZ() * m[1][2] + m[1][3],
                vec.getX() * m[2][0] + vec.getY() * m[2][1] + vec.getZ() * m[2][2] + m[2][3]
        );
        double w = vec.getX() * m[3][0] + vec.getY() * m[3][1] + vec.getZ() * m[3][2] + m[3][3];
        return out.div(w);
    }

    public Vector4f mul4f(Vector4d vec) {
        return new Vector4f(
                (float) (vec.getX() * m[0][0] + vec.getY() * m[0][1] + vec.getZ() * m[0][2] + vec.getW() * m[0][3]),
                (float) (vec.getX() * m[1][0] + vec.getY() * m[1][1] + vec.getZ() * m[1][2] + vec.getW() * m[1][3]),
                (float) (vec.getX() * m[2][0] + vec.getY() * m[2][1] + vec.getZ() * m[2][2] + vec.getW() * m[2][3]),
                (float) (vec.getX() * m[3][0] + vec.getY() * m[3][1] + vec.getZ() * m[3][2] + vec.getW() * m[3][3])
        );
    }

    public Matrix4f pow(int exp) {
        if (exp == 0)
            return new Matrix4f().initIdentity();
        else if (exp == 1)
            return this;
        else if (exp % 2 == 0) {
            Matrix4f mat = pow(exp / 2);
            return mat.mul(mat);
        } else {
            return this.mul(pow(exp - 1));
        }
    }

    public Matrix4f setM(float[][] m) {
        this.m = m;
        return this;
    }

    public float[][] getM() {
        return m;
    }

    public float get(int x, int y) {
        return m[x][y];
    }

    public Matrix4f set(int x, int y, float value) {
        m[x][y] = value;
        return this;
    }

    public Vector4f getRow(int index) {
        return new Vector4f(m[index][0], m[index][1], m[index][2], m[index][3]);
    }

    public Vector4f getCoulomb(int index) {
        return new Vector4f(m[0][index], m[1][index], m[2][index], m[3][index]);
    }

    public Matrix4f setRow(int index, Vector4f row) {
        m[index][0] = row.getX();
        m[index][1] = row.getY();
        m[index][2] = row.getZ();
        m[index][3] = row.getW();
        return this;
    }

    public Matrix4f setCoulomb(int index, Vector4f coulomb) {
        m[0][index] = coulomb.getX();
        m[1][index] = coulomb.getY();
        m[2][index] = coulomb.getZ();
        m[3][index] = coulomb.getW();
        return this;
    }

    public Matrix4d toMatrix4d() {
        Matrix4d out = new Matrix4d();
        for (int row = 0; row < 4; row++)
            for (int coulomb = 0; coulomb < 4; coulomb++)
                out.set(row, coulomb, m[row][coulomb]);
        return out;
    }

    public float determinant() {
        return (m[0][0] * m[1][1] - m[0][1] * m[1][0]) * (m[2][2] * m[3][3] - m[2][3] * m[3][2])
                - (m[0][0] * m[1][2] - m[0][2] * m[1][0]) * (m[2][1] * m[3][3] - m[2][3] * m[3][1])
                + (m[0][0] * m[1][3] - m[0][3] * m[1][0]) * (m[2][1] * m[3][2] - m[2][2] * m[3][1])
                + (m[0][1] * m[1][2] - m[0][2] * m[1][1]) * (m[2][0] * m[3][3] - m[2][3] * m[3][0])
                - (m[0][1] * m[1][3] - m[0][3] * m[1][1]) * (m[2][0] * m[3][2] - m[2][2] * m[3][0])
                + (m[0][2] * m[1][3] - m[0][3] * m[1][2]) * (m[2][0] * m[3][1] - m[2][1] * m[3][0]);
    }

    public void invert() {
        float s = determinant();
        if (s == 0.0)
            return;
        s = 1f / s;


        m[0][0] = m[1][1] * (m[2][2] * m[3][3] - m[2][3] * m[3][2])
                + m[1][2] * (m[2][3] * m[3][1] - m[2][1] * m[3][3])
                + m[1][3] * (m[2][1] * m[3][2] - m[2][2] * m[3][1]);

        m[0][1] = m[2][1] * (m[0][2] * m[3][3] - m[0][3] * m[3][2])
                + m[2][2] * (m[0][3] * m[3][1] - m[0][1] * m[3][3])
                + m[2][3] * (m[0][1] * m[3][2] - m[0][2] * m[3][1]);

        m[0][2] = m[3][1] * (m[0][2] * m[1][3] - m[0][3] * m[1][2])
                + m[3][2] * (m[0][3] * m[1][1] - m[0][1] * m[1][3])
                + m[3][3] * (m[0][1] * m[1][2] - m[0][2] * m[1][1]);

        m[0][3] = m[0][1] * (m[1][3] * m[2][2] - m[1][2] * m[2][3])
                + m[0][2] * (m[1][1] * m[2][3] - m[1][3] * m[2][1])
                + m[0][3] * (m[1][2] * m[2][1] - m[1][1] * m[2][2]);


        /*set(m11 * (m22 * m33 - m23 * m32) + m12 * (m23 * m31 - m21 * m33) + m13 * (m21 * m32 - m22 * m31), m21
                        * (m02 * m33 - m03 * m32) + m22 * (m03 * m31 - m01 * m33) + m23 * (m01 * m32 - m02 * m31), m31
                        * (m02 * m13 - m03 * m12) + m32 * (m03 * m11 - m01 * m13) + m33 * (m01 * m12 - m02 * m11), m01
                        * (m13 * m22 - m12 * m23) + m02 * (m11 * m23 - m13 * m21) + m03 * (m12 * m21 - m11 * m22),

                m12 * (m20 * m33 - m23 * m30) + m13 * (m22 * m30 - m20 * m32) + m10 * (m23 * m32 - m22 * m33), m22
                        * (m00 * m33 - m03 * m30) + m23 * (m02 * m30 - m00 * m32) + m20 * (m03 * m32 - m02 * m33), m32
                        * (m00 * m13 - m03 * m10) + m33 * (m02 * m10 - m00 * m12) + m30 * (m03 * m12 - m02 * m13), m02
                        * (m13 * m20 - m10 * m23) + m03 * (m10 * m22 - m12 * m20) + m00 * (m12 * m23 - m13 * m22),

                m13 * (m20 * m31 - m21 * m30) + m10 * (m21 * m33 - m23 * m31) + m11 * (m23 * m30 - m20 * m33), m23
                        * (m00 * m31 - m01 * m30) + m20 * (m01 * m33 - m03 * m31) + m21 * (m03 * m30 - m00 * m33), m33
                        * (m00 * m11 - m01 * m10) + m30 * (m01 * m13 - m03 * m11) + m31 * (m03 * m10 - m00 * m13), m03
                        * (m11 * m20 - m10 * m21) + m00 * (m13 * m21 - m11 * m23) + m01 * (m10 * m23 - m13 * m20),

                m10 * (m22 * m31 - m21 * m32) + m11 * (m20 * m32 - m22 * m30) + m12 * (m21 * m30 - m20 * m31), m20
                        * (m02 * m31 - m01 * m32) + m21 * (m00 * m32 - m02 * m30) + m22 * (m01 * m30 - m00 * m31), m30
                        * (m02 * m11 - m01 * m12) + m31 * (m00 * m12 - m02 * m10) + m32 * (m01 * m10 - m00 * m11), m00
                        * (m11 * m22 - m12 * m21) + m01 * (m12 * m20 - m10 * m22) + m02 * (m10 * m21 - m11 * m20));
        mul(s);*/
    }

    public Matrix3f toReducedMatrix3f() {
        Matrix3f out = new Matrix3f();
        for (int row = 0; row < 3; row++)
            for (int coulomb = 0; coulomb < 3; coulomb++)
                out.set(row, coulomb, m[row][coulomb]);
        return out;
    }

    public Matrix3d toReducedMatrix3d() {
        Matrix3d out = new Matrix3d();
        for (int row = 0; row < 3; row++)
            for (int coulomb = 0; coulomb < 3; coulomb++)
                out.set(row, coulomb, m[row][coulomb]);
        return out;
    }

    public Matrix4f copy() {
        Matrix4f copy = new Matrix4f();
        for (int row = 0; row < 4; row++)
            System.arraycopy(m[row], 0, copy.m[row], 0, 4);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix4f matrix4f = (Matrix4f) o;
        return Arrays.equals(m, matrix4f.m);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(m);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (int i = 0; i < 4; i++) {
            stringBuilder.append("\n");
            for (int j = 0; j < 4; j++)
                stringBuilder.append("\t").append(m[i][j]);
        }
        stringBuilder.append("\n]");
        return stringBuilder.toString();
    }
}
