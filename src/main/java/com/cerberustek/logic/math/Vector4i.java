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

import java.util.Objects;

public class Vector4i {

    private int x;
    private int y;
    private int z;
    private int w;

    public Vector4i(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4i(Vector3i vec, int w) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
        this.w = w;
    }

    public Vector4i(Vector3i vec) {
        this(vec, 1);
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public Vector4i normalized() {
        float length = length();
        if (length == 0)
            return this;
        return div(length);
    }

    public Vector4i normalizeSelf() {
        float length = length();
        if (length == 0)
            return this;
        return divSelf(length);
    }

    public Vector4i invert() {
        return new Vector4i(-x, -y, -z, -w);
    }

    public Vector4i invertSelf() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    public float dot(Vector4i other) {
        return x * other.x + y * other.y + z * other.z + w * other.w;
    }

    public Vector4i mul(int s) {
        return new Vector4i(x * s, y * s, z * s, w * s);
    }

    public Vector4i mul(float s) {
        return new Vector4i((int) (x * s), (int) (y * s), (int) (z * s), (int) (w * s));
    }

    public Vector4i mulSelf(int s) {
        x *= s;
        y *= s;
        z *= s;
        w *= s;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4i mulSelf(float s) {
        x = (int) (x * s);
        y = (int) (y * s);
        z = (int) (z * s);
        w = (int) (w * s);
        return this;
    }

    public Vector4i mul(int xs, int ys, int zs, int ws) {
        return new Vector4i(x * xs, y * ys, z * zs, w * ws);
    }

    public Vector4i mul(float xs, float ys, float zs, float ws) {
        return new Vector4i((int) (x * xs), (int) (y * ys), (int) (z * zs), (int) (w * ws));
    }

    public Vector4i mulSelf(int xs, int ys, int zs, int ws) {
        x *= xs;
        y *= ys;
        z *= zs;
        w *= ws;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4i mulSelf(float xs, float ys, float zs, float ws) {
        x = (int) (x * xs);
        y = (int) (y * ys);
        z = (int) (z * zs);
        w = (int) (w * ws);
        return this;
    }

    public Vector4i mul(Vector4i other) {
        return new Vector4i(x * other.x, y * other.y, z * other.z, w * other.w);
    }

    public Vector4i mul(Vector4f other) {
        return new Vector4i((int) (x * other.getX()), (int) (y * other.getY()), (int) (z * other.getZ()), (int) (w * other.getW()));
    }

    @SuppressWarnings("Duplicates")
    public Vector4i mulSelf(Vector4i other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        w *= other.w;
        return this;
    }

    public Vector4i mulSelf(Vector4f other) {
        x = (int) (x * other.getX());
        y = (int) (y * other.getY());
        z = (int) (z * other.getZ());
        w = (int) (w * other.getW());
        return this;
    }

    public Vector4i div(int s) {
        return new Vector4i(x / s, y / s, z / s, w / s);
    }

    public Vector4i div(float s) {
        return new Vector4i((int) (x / s), (int) (y / s), (int) (z / s), (int) (w / s));
    }

    public Vector4i divSelf(int s) {
        x /= s;
        y /= s;
        z /= s;
        w /= s;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4i divSelf(float s) {
        x = (int) (x / s);
        y = (int) (y / s);
        z = (int) (z / s);
        w = (int) (w / s);
        return this;
    }

    public Vector4i div(int xs, int ys, int zs, int ws) {
        return new Vector4i(x / xs, y / ys, z / zs, w / ws);
    }

    public Vector4i div(float xs, float ys, float zs, float ws) {
        return new Vector4i((int) (x / xs), (int) (y / ys), (int) (z / zs), (int) (w / ws));
    }

    public Vector4i divSelf(int xs, int ys, int zs, int ws) {
        x /= xs;
        y /= ys;
        z /= zs;
        w /= ws;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4i divSelf(float xs, float ys, float zs, float ws) {
        x = (int) (x / xs);
        y = (int) (y / ys);
        z = (int) (z / zs);
        w = (int) (w / ws);
        return this;
    }

    public Vector4i div(Vector4i other) {
        return new Vector4i(x / other.x, y / other.y, z / other.z, w / other.w);
    }

    public Vector4i div(Vector4f other) {
        return new Vector4i((int) (x / other.getX()), (int) (y / other.getY()), (int) (z / other.getZ()), (int) (w / other.getW()));
    }

    @SuppressWarnings("Duplicates")
    public Vector4i divSelf(Vector4i other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        w /= other.w;
        return this;
    }

    public Vector4i divSelf(Vector4f other) {
        x = (int) (x / other.getX());
        y = (int) (y / other.getY());
        z = (int) (z / other.getZ());
        w = (int) (w / other.getW());
        return this;
    }

    public Vector4i add(int s) {
        return new Vector4i(x + s, y + s, z + s, w + s);
    }

    public Vector4i addSelf(int s) {
        x += s;
        y += s;
        z += s;
        w += s;
        return this;
    }

    public Vector4i add(int xs, int ys, int zs, int ws) {
        return new Vector4i(x + xs, y + ys, z + zs, w + ws);
    }

    public Vector4i addSelf(int xs, int ys, int zs, int ws) {
        x += xs;
        y += ys;
        z += zs;
        w += ws;
        return this;
    }

    public Vector4i add(Vector4i other) {
        return new Vector4i(x + other.x, y + other.y, z + other.z, w + other.w);
    }

    @SuppressWarnings("Duplicates")
    public Vector4i addSelf(Vector4i other) {
        x += other.x;
        y += other.y;
        z += other.z;
        w += other.w;
        return this;
    }

    public Vector4i sub(int s) {
        return new Vector4i(x - s, y - s, z - s, w - s);
    }

    public Vector4i subSelf(int s) {
        x -= s;
        y -= s;
        z -= s;
        w -= s;
        return this;
    }

    public Vector4i sub(int xs, int ys, int zs, int ws) {
        return new Vector4i(x - xs, y - ys, z - zs, w - ws);
    }

    public Vector4i subSelf(int xs, int ys, int zs, int ws) {
        x -= xs;
        y -= ys;
        z -= zs;
        w -= ws;
        return this;
    }

    public Vector4i sub(Vector4i other) {
        return new Vector4i(x - other.x, y - other.y, z - other.z, w - other.w);
    }

    @SuppressWarnings("Duplicates")
    public Vector4i subSelf(Vector4i other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        w -= other.w;
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getW() {
        return w;
    }

    public Vector4i setX(int value) {
        x = value;
        return this;
    }

    public Vector4i setY(int value) {
        y = value;
        return this;
    }

    public Vector4i setZ(int value) {
        z = value;
        return this;
    }

    public Vector4i setW(int value) {
        w = value;
        return this;
    }

    public Vector4i set(Vector4i value) {
        x = value.x;
        y = value.y;
        z = value.z;
        w = value.w;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4i set(Vector4f value) {
        x = (int) value.getX();
        y = (int) value.getY();
        z = (int) value.getZ();
        w = (int) value.getW();
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4i set(Vector4d value) {
        x = (int) value.getX();
        y = (int) value.getY();
        z = (int) value.getZ();
        w = (int) value.getW();
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4i set(Vector4l value) {
        x = (int) value.getX();
        y = (int) value.getY();
        z = (int) value.getZ();
        w = (int) value.getW();
        return this;
    }

    public Vector4i set(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4i set(Vector3i vec, int w) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
        this.w = w;
        return this;
    }

    public Vector3i xyz() {
        return new Vector3i(x, y, z);
    }

    public Vector4f toVector4f() {
        return new Vector4f(x, y, z, w);
    }

    public Vector4d toVector4d() {
        return new Vector4d(x, y, z, w);
    }

    public Vector4l toVector4l() {
        return new Vector4l(x, y, z, w);
    }

    public Vector4i copy() {
        return new Vector4i(x, y, z, w);
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y + " z: " + z + " w: " + w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector4i vector4i = (Vector4i) o;
        return vector4i.x == x &&
                vector4i.y == y &&
                vector4i.z == z &&
                vector4i.w == w;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }
}
