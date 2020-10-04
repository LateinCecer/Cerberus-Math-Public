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

/**
 * Created by LateinCecker on 02.03.2016.
 */
public class Vector3i {

    private int x;
    private int y;
    private int z;

    public Vector3i(int value) {
        this(value, value, value);
    }

    public Vector3i(Vector3i other) {
        this(other.getX(), other.getY(), other.getZ());
    }

    public Vector3i(Vector2i vec, int z) {
        this(vec.getX(), vec.getY(), z);
    }

    public Vector3i(int x, Vector2i vec) {
        this(x, vec.getX(), vec.getY());
    }

    public Vector3i(Vector2l vec, long z) {
        this((int) vec.getX(), (int) vec.getY(), (int) z);
    }

    public Vector3i(long x, Vector2l vec) {
        this((int) x, (int) vec.getX(), (int) vec.getY());
    }

    public Vector3i(Vector2f vec, float z) {
        this((int) vec.getX(), (int) vec.getY(), (int) z);
    }

    public Vector3i(float x, Vector2f vec) {
        this((int) x, (int) vec.getX(), (int) vec.getY());
    }

    public Vector3i(Vector2d vec, double z) {
        this((int) vec.getX(), (int) vec.getY(), (int) z);
    }

    public Vector3i(double x, Vector2d vec) {
        this((int) x, (int) vec.getX(), (int) vec.getY());
    }

    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float dot(Vector3i other) {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    public Vector3i cross(Vector3i other) {
        int x_ = (y * other.z) - (z * other.y);
        int y_ = (z * other.x) - (x * other.z);
        int z_ = (x * other.y) - (y * other.x);

        return new Vector3i(x_, y_, z_);
    }

    public float length() {
        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public Vector3i normalized() {
        float length = length();
        if (length == 0)
            return this;
        return div(length);
    }

    public Vector3i normalizeSelf() {
        float length = length();
        if (length == 0)
            return this;
        return divSelf(length);
    }

    public Vector3i invert() {
        return new Vector3i(-x, -y, -z);
    }

    public Vector3i invertSelf() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Vector3i rotate(float f, Vector3i axis) {
        int sinHalfAngle = (int) Math.sin(Math.toRadians(f / 2));

        int rx = axis.getX() * sinHalfAngle;
        int ry = axis.getY() * sinHalfAngle;
        int rz = axis.getZ() * sinHalfAngle;

        Quaternionf rotation = new Quaternionf(rx, ry, rz, (int) Math.cos(Math.toRadians(f / 2)));
        Quaternionf conjugate = rotation.conjugate();

        Quaternionf w = rotation.mul(toVector3f()).mul(conjugate);

        x = (int) w.getX();
        y = (int) w.getY();
        z = (int) w.getZ();

        return this;
    }

    public Vector3i pow(int s) {
        return new Vector3i((int) Math.pow(x, s), (int) Math.pow(y, s), (int) Math.pow(z, s));
    }

    public Vector3i pow(int xs, int ys, int zs) {
        return new Vector3i((int) Math.pow(x, xs), (int) Math.pow(y, ys), (int) Math.pow(z, zs));
    }

    public Vector3i pow(Vector3i other) {
        return new Vector3i((int) Math.pow(x, other.getX()), (int) Math.pow(y, other.getY()),
                (int) Math.pow(z, other.getZ()));
    }

    public Vector3i mod(int s) {
        return new Vector3i(x % s, y % s, z % s);
    }

    public Vector3i mod(int xs, int ys, int zs) {
        return new Vector3i(x % xs, y % ys, z % zs);
    }

    public Vector3i mod(Vector3i other) {
        return new Vector3i(x % other.getX(), y % other.getY(), z % other.getZ());
    }

    public Vector3i mul(int s) {
        return new Vector3i(x * s, y * s, z * s);
    }

    public Vector3i mul(float s) {
        return new Vector3i((int) (x * s), (int) (y * s), (int) (z * s));
    }

    public Vector3i mulSelf(int s) {
        x *= s;
        y *= s;
        z *= s;
        return this;
    }

    public Vector3i mulSelf(float s) {
        x = (int) (x * s);
        y = (int) (y * s);
        z = (int) (z * s);
        return this;
    }

    public Vector3i mul(int xs, int ys, int zs) {
        return new Vector3i(x * xs, y * ys, z * zs);
    }

    public Vector3i mul(float xs, float ys, float zs) {
        return new Vector3i((int) (x * xs), (int) (y * ys), (int) (z * zs));
    }

    public Vector3i mulSelf(int xs, int ys, int zs) {
        x *= xs;
        y *= ys;
        z *= zs;
        return this;
    }

    public Vector3i mulSelf(float xs, float ys, float zs) {
        x = (int) (x * xs);
        y = (int) (y * ys);
        z = (int) (z * zs);
        return this;
    }

    public Vector3i mul(Vector3i other) {
        return new Vector3i(x * other.x, y * other.y, z * other.z);
    }

    public Vector3i mul(Vector3f other) {
        return new Vector3i((int) (x * other.getX()), (int) (y * other.getY()), (int) (z * other.getZ()));
    }

    public Vector3i mulSelf(Vector3i other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        return this;
    }

    public Vector3i mulSelf(Vector3f other) {
        x = (int) (x * other.getX());
        y = (int) (y * other.getY());
        z = (int) (z * other.getZ());
        return this;
    }

    public Vector3i add(int s) {
        return new Vector3i(x + s, y + s, z + s);
    }

    public Vector3i addSelf(int s) {
        x += s;
        y += s;
        z += s;
        return this;
    }

    public Vector3i add(int xs, int ys, int zs) {
        return new Vector3i(x + xs, y + ys, z + zs);
    }

    public Vector3i addSelf(int xs, int ys, int zs) {
        x += xs;
        y += ys;
        z += zs;
        return this;
    }

    public Vector3i add(Vector3i other) {
        return new Vector3i(x + other.x, y + other.y, z + other.z);
    }

    public Vector3i addSelf(Vector3i other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    public Vector3i sub(int s) {
        return new Vector3i(x - s, x - s, z - s);
    }

    public Vector3i subSelf(int s) {
        x -= s;
        y -= s;
        z -= s;
        return this;
    }

    public Vector3i sub(int xs, int ys, int zs) {
        return new Vector3i(x - xs, y - ys, z - zs);
    }

    public Vector3i subSelf(int xs, int ys, int zs) {
        x -= xs;
        y -= ys;
        z -= zs;
        return this;
    }

    public Vector3i sub(Vector3i other) {
        return new Vector3i(x - other.x, y - other.y, z - other.z);
    }

    public Vector3i subSelf(Vector3i other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        return this;
    }

    public Vector3i div(int s) {
        return new Vector3i(x / s, y / s, z / s);
    }

    public Vector3i div(float s) {
        return new Vector3i((int) (x / s), (int) (y / s), (int) (z / s));
    }

    public Vector3i divSelf(int s) {
        x /= s;
        y /= s;
        z /= s;
        return this;
    }

    public Vector3i divSelf(float s) {
        x = (int) (x / s);
        y = (int) (y / s);
        z = (int) (z / s);
        return this;
    }

    public Vector3i div(int xs, int ys, int zs) {
        return new Vector3i(x / xs, y / ys, z / zs);
    }

    public Vector3i div(float xs, float ys, float zs) {
        return new Vector3i((int) (x / xs), (int) (y / ys), (int) (z / zs));
    }

    public Vector3i divSelf(int xs, int ys, int zs) {
        x /= xs;
        y /= ys;
        z /= zs;
        return this;
    }

    public Vector3i divSelf(float xs, float ys, float zs) {
        x = (int) (x / xs);
        y = (int) (y / ys);
        z = (int) (z / zs);
        return this;
    }

    public Vector3i div(Vector3i other) {
        return new Vector3i(x / other.x, y / other.y, z / other.z);
    }

    public Vector3i div(Vector3f other) {
        return new Vector3i((int) (x / other.getX()), (int) (y / other.getY()), (int) (z / other.getZ()));
    }

    public Vector3i divSelf(Vector3i other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        return this;
    }

    public Vector3i divSelf(Vector3f other) {
        x = (int) (x / other.getX());
        y = (int) (y / other.getY());
        z = (int) (z / other.getZ());
        return this;
    }

    public Vector3i abs() {
        return new Vector3i(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public Vector3i set(Vector3i value) {
        x = value.x;
        y = value.y;
        z = value.z;
        return this;
    }

    public Vector3i set(Vector3f value) {
        x = (int) value.getX();
        y = (int) value.getY();
        z = (int) value.getZ();
        return this;
    }

    public Vector3i set(Vector3d value) {
        x = (int) value.getX();
        y = (int) value.getY();
        z = (int) value.getZ();
        return this;
    }

    public Vector3i set(Vector3l value) {
        x = (int) value.getX();
        y = (int) value.getY();
        z = (int) value.getZ();
        return this;
    }

    public Vector3i set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3i setX(int x) {
        this.x = x;
        return this;
    }

    public Vector3i setY(int y) {
        this.y = y;
        return this;
    }

    public Vector3i setZ(int z) {
        this.z = z;
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

    public boolean isNull() {
        return x == 0 && y == 0 && z == 0;
    }

    public Vector3d toVec3d() {
        return new Vector3d(x, y, z);
    }

    public Vector3f toVec3f() {
        return new Vector3f(x, y, z);
    }

    public Vector3l toVec3l() {
        return new Vector3l(x, y, z);
    }

    public Vector3d toVector3d() {
        return new Vector3d(x, y, z);
    }

    public Vector3f toVector3f() {
        return new Vector3f(x, y, z);
    }

    public Vector3l toVector3l() {
        return new Vector3l(x, y, z);
    }

    public Vector3i copy() {
        return new Vector3i(x, y, z);
    }

    public Vector3i xyz() {
        return new Vector3i(x, y, z);
    }

    public Vector3i yzx() {
        return new Vector3i(y, z, x);
    }

    public Vector3i zxy() {
        return new Vector3i(z, x, y);
    }

    public Vector3i zyx() {
        return new Vector3i(z, y, x);
    }

    public Vector3i yxz() {
        return new Vector3i(y, x, z);
    }

    public Vector3i xzy() {
        return new Vector3i(x, z, y);
    }

    public Vector2i xy() {
        return new Vector2i(x, y);
    }

    public Vector2i yz() {
        return new Vector2i(y, z);
    }

    public Vector2i zx() {
        return new Vector2i(z, x);
    }

    public Vector2i yx() {
        return new Vector2i(x, y);
    }

    public Vector2i xz() {
        return new Vector2i(x, z);
    }

    public Vector2i zy() {
        return new Vector2i(z, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3i vector3i = (Vector3i) o;
        return x == vector3i.x &&
                y == vector3i.y &&
                z == vector3i.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y + " z: " + z;
    }
}
