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
public class Vector3d {

    private double x;
    private double y;
    private double z;

    public Vector3d(double value) {
        this(value, value, value);
    }

    public Vector3d(Vector3d other) {
        this(other.getX(), other.getX(), other.getZ());
    }

    public Vector3d(Vector2d vec, double z) {
        this(vec.getX(), vec.getY(), z);
    }

    public Vector3d(double x, Vector2d vec) {
        this(x, vec.getX(), vec.getY());
    }

    public Vector3d(Vector2f vec, float z) {
        this(vec.getX(), vec.getY(), z);
    }

    public Vector3d(float z, Vector2f vec) {
        this(z, vec.getX(), vec.getY());
    }

    public Vector3d(Vector2i vec, int z) {
        this(vec.getX(), vec.getY(), z);
    }

    public Vector3d(int z, Vector2i vec) {
        this(z, vec.getX(), vec.getY());
    }

    public Vector3d(Vector2l vec, long z) {
        this(vec.getX(), vec.getY(), z);
    }

    public Vector3d(long z, Vector2l vec) {
        this(z, vec.getX(), vec.getY());
    }

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double dot(Vector3d other) {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    public Vector3d cross(Vector3d other) {
        double x_ = (y * other.z) - (z * other.y);
        double y_ = (z * other.x) - (x * other.z);
        double z_ = (x * other.y) - (y * other.x);

        return new Vector3d(x_, y_, z_);
    }

    public double length() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public Vector3d normalized() {
        double length = length();
        if (length == 0)
            return this;
        return div(length);
    }

    public Vector3d normalizeSelf() {
        double length = length();
        if (length == 0)
            return this;
        return divSelf(length);
    }

    public Vector3d invert() {
        return new Vector3d(-x, -y, -z);
    }

    public Vector3d invertSelf() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Vector3d rotate(Quaternionf rotation) {
        Quaternionf w = rotation.mul(this).mul(rotation.conjugate());
        return new Vector3d(w.getX(), w.getY(), w.getZ());
    }

    public Vector3d rotate(double f, Vector3d axis) {
        double sinHalfAngle = Math.sin(Math.toRadians(f / 2));

        double rx = axis.getX() * sinHalfAngle;
        double ry = axis.getY() * sinHalfAngle;
        double rz = axis.getZ() * sinHalfAngle;

        Quaterniond rotation = new Quaterniond(rx, ry, rz, Math.cos(Math.toRadians(f / 2)));
        Quaterniond conjugate = rotation.conjugate();

        Quaterniond w = rotation.mul(this).mul(conjugate);

        x = w.getX();
        y = w.getY();
        z = w.getZ();

        return this;
    }

    public Vector3d pow(double s) {
        return new Vector3d(Math.pow(x, s), Math.pow(y, s), Math.pow(z, s));
    }

    public Vector3d pow(double xs, double ys, double zs) {
        return new Vector3d(Math.pow(x, xs), Math.pow(y, ys), Math.pow(z, zs));
    }

    public Vector3d pow(Vector3d other) {
        return new Vector3d(Math.pow(x, other.getX()), Math.pow(y, other.getY()), Math.pow(z, other.getZ()));
    }

    public Vector3d mod(double s) {
        return new Vector3d(x % s, y % s, z % s);
    }

    public Vector3d mod(double xs, double ys, double zs) {
        return new Vector3d(x % xs, y % ys, z % zs);
    }

    public Vector3d mod(Vector3d other) {
        return new Vector3d(x % other.getX(), y % other.getY(), z % other.getZ());
    }

    public Vector3d mul(double s) {
        return new Vector3d(x * s, y * s, z * s);
    }

    public Vector3d mulSelf(double s) {
        x *= s;
        y *= s;
        z *= s;
        return this;
    }

    public Vector3d mul(double xs, double ys, double zs) {
        return new Vector3d(x * xs, y * ys, z * zs);
    }

    public Vector3d mulSelf(double xs, double ys, double zs) {
        x *= xs;
        y *= ys;
        z *= zs;
        return this;
    }

    public Vector3d mul(Vector3d other) {
        return new Vector3d(x * other.x, y * other.y, z * other.z);
    }

    public Vector3d mulSelf(Vector3d other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        return this;
    }

    public Vector3d add(double s) {
        return new Vector3d(x + s, y + s, z + s);
    }

    public Vector3d addSelf(double s) {
        x += s;
        y += s;
        z += s;
        return this;
    }

    public Vector3d add(double xs, double ys, double zs) {
        return new Vector3d(x + xs, y + ys, z + zs);
    }

    public Vector3d addSelf(double xs, double ys, double zs) {
        x += xs;
        y += ys;
        z += zs;
        return this;
    }

    public Vector3d add(Vector3d other) {
        return new Vector3d(x + other.x, y + other.y, z + other.z);
    }

    public Vector3d addSelf(Vector3d other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    public Vector3d sub(double s) {
        return new Vector3d(x - s, x - s, z - s);
    }

    public Vector3d subSelf(double s) {
        x -= s;
        y -= s;
        z -= s;
        return this;
    }

    public Vector3d sub(double xs, double ys, double zs) {
        return new Vector3d(x - xs, y - ys, z - zs);
    }

    public Vector3d subSelf(double xs, double ys, double zs) {
        x -= xs;
        y -= ys;
        z -= zs;
        return this;
    }

    public Vector3d sub(Vector3d other) {
        return new Vector3d(x - other.x, y - other.y, z - other.z);
    }

    public Vector3d subSelf(Vector3d other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        return this;
    }

    public Vector3d div(double s) {
        return new Vector3d(x / s, y / s, z / s);
    }

    public Vector3d divSelf(double s) {
        x /= s;
        y /= s;
        z /= s;
        return this;
    }

    public Vector3d div(double xs, double ys, double zs) {
        return new Vector3d(x / xs, y / ys, z / zs);
    }

    public Vector3d divSelf(double xs, double ys, double zs) {
        x /= xs;
        y /= ys;
        z /= zs;
        return this;
    }

    public Vector3d div(Vector3d other) {
        return new Vector3d(x / other.x, y / other.y, z / other.z);
    }

    public Vector3d divSelf(Vector3d other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        return this;
    }

    public Vector3d abs() {
        return new Vector3d(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public Vector3l round() {
        return new Vector3l(Math.round(x), Math.round(y), Math.round(z));
    }

    public Vector3d set(Vector3d value) {
        x = value.x;
        y = value.y;
        z = value.z;
        return this;
    }

    public Vector3d set(Vector3f value) {
        x = value.getX();
        y = value.getY();
        z = value.getZ();
        return this;
    }

    public Vector3d set(Vector3i value) {
        x = value.getX();
        y = value.getY();
        z = value.getZ();
        return this;
    }

    public Vector3d set(Vector3l value) {
        x = value.getX();
        y = value.getY();
        z = value.getZ();
        return this;
    }

    public Vector3d set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3d setX(double x) {
        this.x = x;
        return this;
    }

    public Vector3d setY(double y) {
        this.y = y;
        return this;
    }

    public Vector3d setZ(double z) {
        this.z = z;
        return this;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public boolean isNull() {
        return x == 0 && y == 0 && z == 0;
    }

    public Vector3f toVec3f() {
        return new Vector3f((float) x, (float) y, (float) z);
    }

    public Vector3i toVec3i() {
        return new Vector3i((int) x, (int) y, (int) z);
    }

    public Vector3l toVec3l() {
        return new Vector3l((long) x, (long) y, (long) z);
    }

    public Vector3f toVector3f() {
        return new Vector3f((float) x, (float) y, (float) z);
    }

    public Vector3i toVector3i() {
        return new Vector3i((int) x, (int) y, (int) z);
    }

    public Vector3l toVector3l() {
        return new Vector3l((long) x, (long) y, (long) z);
    }

    public Vector3d xyz() {
        return new Vector3d(x, y, z);
    }

    public Vector3d yzx() {
        return new Vector3d(y, z, x);
    }

    public Vector3d zxy() {
        return new Vector3d(z, x, y);
    }

    public Vector3d zyx() {
        return new Vector3d(z, y, x);
    }

    public Vector3d yxz() {
        return new Vector3d(y, x, z);
    }

    public Vector3d xzy() {
        return new Vector3d(x, z, y);
    }

    public Vector2d xy() {
        return new Vector2d(x, y);
    }

    public Vector2d yz() {
        return new Vector2d(y, z);
    }

    public Vector2d zx() {
        return new Vector2d(z, x);
    }

    public Vector2d yx() {
        return new Vector2d(y, x);
    }

    public Vector2d xz() {
        return new Vector2d(x, z);
    }

    public Vector2d zy() {
        return new Vector2d(z, y);
    }

    public Vector3d copy() {
        return new Vector3d(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3d vector3d = (Vector3d) o;
        return Double.compare(vector3d.x, x) == 0 &&
                Double.compare(vector3d.y, y) == 0 &&
                Double.compare(vector3d.z, z) == 0;
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
