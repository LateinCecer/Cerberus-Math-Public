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
public class Vector3f {

    private float x;
    private float y;
    private float z;

    public Vector3f(float value) {
        this(value, value, value);
    }

    public Vector3f(Vector3f other) {
        this(other.getX(), other.getY(), other.getZ());
    }

    public Vector3f(Vector2f vec, float z) {
        this(vec.getX(), vec.getY(), z);
    }

    public Vector3f(float x, Vector2f vec) {
        this(x, vec.getX(), vec.getY());
    }

    public Vector3f(Vector2d vec, double z) {
        this((float) vec.getX(), (float) vec.getY(), (float) z);
    }

    public Vector3f(double x, Vector2d vec) {
        this((float) x, (float) vec.getX(), (float) vec.getY());
    }

    public Vector3f(Vector2i vec, int z) {
        this(vec.getX(), vec.getY(), z);
    }

    public Vector3f(int x, Vector2i vec) {
        this(x, vec.getX(), vec.getY());
    }

    public Vector3f(Vector2l vec, long z) {
        this(vec.getX(), vec.getY(), z);
    }

    public Vector3f(long x, Vector2l vec) {
        this(x, vec.getX(), vec.getY());
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float dot(Vector3f other) {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    public Vector3f cross(Vector3f other) {
        float x_ = (y * other.z) - (z * other.y);
        float y_ = (z * other.x) - (x * other.z);
        float z_ = (x * other.y) - (y * other.x);

        return new Vector3f(x_, y_, z_);
    }

    public float length() {
        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public Vector3f normalized() {
        float length = length();
        if (length == 0)
            return this;
        return div(length);
    }

    public Vector3f normalizeSelf() {
        float length = length();
        if (length == 0)
            return this;
        return divSelf(length);
    }

    public Vector3f invert() {
        return new Vector3f(-x, -y, -z);
    }

    public Vector3f invertSelf() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Vector3f rotate(Vector3f axis, float angle) {
        float sinAngle = (float)Math.sin(-angle);
        float cosAngle = (float)Math.cos(-angle);

        return this.cross(axis.mul(sinAngle)).add(                      // Rotation on local X
                (this.mul(cosAngle)).add(                               // Rotation on local Z
                        axis.mul(this.dot(axis.mul(1 - cosAngle)))));   // Rotation on local Y
    }

    public Vector3f rotate(Quaternionf rotation) {
        return rotation.mul(this).mul(rotation.conjugate()).re();
    }

    public Quaternionf angel(Vector3f other) {
        return new Quaternionf((float) Math.acos(normalized().dot(other.normalized())), cross(other).normalized());
    }

    public Vector3f pow(float s) {
        return new Vector3f((float) Math.pow(x, s), (float) Math.pow(y, s), (float) Math.pow(z, s));
    }

    public Vector3f pow(float xs, float ys, float zs) {
        return new Vector3f((float) Math.pow(x, xs), (float) Math.pow(y, ys), (float) Math.pow(z, zs));
    }

    public Vector3f pow(Vector3f other) {
        return new Vector3f((float) Math.pow(x, other.getX()), (float) Math.pow(y, other.getY()),
                (float) Math.pow(z, other.getZ()));
    }

    public Vector3f mod(float s) {
        return new Vector3f(x % s, y % s, z % s);
    }

    public Vector3f mod(float xs, float ys, float zs) {
        return new Vector3f(x % xs, y % ys, z % zs);
    }

    public Vector3f mod(Vector3f other) {
        return new Vector3f(x % other.getX(), y % other.getY(), z % other.getZ());
    }

    public Vector3f mul(float s) {
        return new Vector3f(x * s, y * s, z * s);
    }

    public Vector3f mulSelf(float s) {
        x *= s;
        y *= s;
        z *= s;
        return this;
    }

    public Vector3f mul(float xs, float ys, float zs) {
        return new Vector3f(x * xs, y * ys, z * zs);
    }

    public Vector3f mulSelf(float xs, float ys, float zs) {
        x *= xs;
        y *= ys;
        z *= zs;
        return this;
    }

    public Vector3f mul(Vector3f other) {
        return new Vector3f(x * other.x, y * other.y, z * other.z);
    }

    public Vector3f mulSelf(Vector3f other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        return this;
    }

    public Vector3f mul(Matrix3f mat) {
        return new Vector3f(
                x * mat.get(0, 0) + y * mat.get(0, 1) + z * mat.get(0, 2),
                x * mat.get(1, 0) + y * mat.get(1, 1) + z * mat.get(1, 2),
                x * mat.get(2, 0) + y * mat.get(2, 1) + z * mat.get(2, 2));
    }

    public Vector3f add(float s) {
        return new Vector3f(x + s, y + s, z + s);
    }

    public Vector3f addSelf(float s) {
        x += s;
        y += s;
        z += s;
        return this;
    }

    public Vector3f add(float xs, float ys, float zs) {
        return new Vector3f(x + xs, y + ys, z + zs);
    }

    public Vector3f addSelf(float xs, float ys, float zs) {
        x += xs;
        y += ys;
        z += zs;
        return this;
    }

    public Vector3f add(Vector3f other) {
        return new Vector3f(x + other.x, y + other.y, z + other.z);
    }

    public Vector3f addSelf(Vector3f other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    public Vector3f sub(float s) {
        return new Vector3f(x - s, x - s, z - s);
    }

    public Vector3f subSelf(float s) {
        x -= s;
        y -= s;
        z -= s;
        return this;
    }

    public Vector3f sub(float xs, float ys, float zs) {
        return new Vector3f(x - xs, y - ys, z - zs);
    }

    public Vector3f subSelf(float xs, float ys, float zs) {
        x -= xs;
        y -= ys;
        z -= zs;
        return this;
    }

    public Vector3f sub(Vector3f other) {
        return new Vector3f(x - other.x, y - other.y, z - other.z);
    }

    public Vector3f subSelf(Vector3f other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        return this;
    }

    public Vector3f div(float s) {
        return new Vector3f(x / s, y / s, z / s);
    }

    public Vector3f divSelf(float s) {
        x /= s;
        y /= s;
        z /= s;
        return this;
    }

    public Vector3f div(float xs, float ys, float zs) {
        return new Vector3f(x / xs, y / ys, z / zs);
    }

    public Vector3f divSelf(float xs, float ys, float zs) {
        x /= xs;
        y /= ys;
        z /= zs;
        return this;
    }

    public Vector3f div(Vector3f other) {
        return new Vector3f(x / other.x, y / other.y, z / other.z);
    }

    public Vector3f divSelf(Vector3f other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        return this;
    }

    public Vector3f abs() {
        return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public Vector3i round() {
        return new Vector3i(Math.round(x), Math.round(y), Math.round(z));
    }

    public Vector3f set(Vector3f value) {
        x = value.x;
        y = value.y;
        z = value.z;
        return this;
    }

    public Vector3f set(Vector3d value) {
        x = (float) value.getX();
        y = (float) value.getY();
        z = (float) value.getZ();
        return this;
    }

    public Vector3f set(Vector3i value) {
        x = value.getX();
        y = value.getY();
        z = value.getZ();
        return this;
    }

    public Vector3f set(Vector3l value) {
        x = (float) value.getX();
        y = (float) value.getY();
        z = (float) value.getZ();
        return this;
    }

    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3f setX(float x) {
        this.x = x;
        return this;
    }

    public Vector3f setY(float y) {
        this.y = y;
        return this;
    }

    public Vector3f setZ(float z) {
        this.z = z;
        return this;
    }

    public Vector3i toVec3i() {
        return new Vector3i((int) x, (int) y, (int) z);
    }

    public Vector3d toVec3d() {
        return new Vector3d(x, y, z);
    }

    public Vector3l toVec3l() {
        return new Vector3l((long) x, (long) y, (long) z);
    }

    public Vector3i toVector3i() {
        return new Vector3i((int) x, (int) y, (int) z);
    }

    public Vector3d toVector3d() {
        return new Vector3d(x, y, z);
    }

    public Vector3l toVector3l() {
        return new Vector3l((long) x, (long) y, (long) z);
    }

    public Vector3f xyz() {
        return new Vector3f(x, y, z);
    }

    public Vector3f yzx() {
        return new Vector3f(y, z, x);
    }

    public Vector3f zxy() {
        return new Vector3f(z, x, y);
    }

    public Vector3f zyx() {
        return new Vector3f(z, y, x);
    }

    public Vector3f yxz() {
        return new Vector3f(y, x, z);
    }

    public Vector3f xzy() {
        return new Vector3f(x, z, y);
    }

    public Vector2f xy() {
        return new Vector2f(x, y);
    }

    public Vector2f yz() {
        return new Vector2f(y, z);
    }

    public Vector2f zx() {
        return new Vector2f(z, x);
    }

    public Vector2f yx() {
        return new Vector2f(y, x);
    }

    public Vector2f xz() {
        return new Vector2f(x, z);
    }

    public Vector2f zy() {
        return new Vector2f(z, y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public boolean isNull() {
        return x == 0 && y == 0 && z == 0;
    }

    public float compare(Vector3f other) {
        return (x - other.getX()) + (y - other.getY()) + (z - other.getZ());
    }

    public Vector3f copy() {
        return new Vector3f(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3f vector3f = (Vector3f) o;
        return Float.compare(vector3f.x, x) == 0 &&
                Float.compare(vector3f.y, y) == 0 &&
                Float.compare(vector3f.z, z) == 0;
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
