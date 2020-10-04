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

import java.util.Objects;
import java.util.Vector;

/**
 * Created by LateinCecker on 02.03.2016.
 */
public class Vector3l {

    private long x;
    private long y;
    private long z;

    public Vector3l(long value) {
        this(value, value, value);
    }

    public Vector3l(Vector3l other) {
        this(other.getX(), other.getY(), other.getZ());
    }

    public Vector3l(Vector2l vec, long z) {
        this(vec.getX(), vec.getY(), z);
    }

    public Vector3l(long x, Vector2l vec) {
        this(x, vec.getX(), vec.getY());
    }

    public Vector3l(Vector2i vec, int z) {
        this(vec.getX(), vec.getY(), z);
    }

    public Vector3l(int x, Vector2i vec) {
        this(x, vec.getX(), vec.getY());
    }

    public Vector3l(Vector2d vec, double z) {
        this((long) vec.getX(), (long) vec.getY(), (long) z);
    }

    public Vector3l(double x, Vector2d vec) {
        this((long) x, (long) vec.getX(), (long) vec.getY());
    }

    public Vector3l(Vector2f vec, float z) {
        this((long) vec.getX(), (long) vec.getY(), (long) z);
    }

    public Vector3l(float x, Vector2f vec) {
        this((long) x, (long) vec.getX(), (long) vec.getY());
    }

    public Vector3l(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double dot(Vector3l other) {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    public Vector3l cross(Vector3l other) {
        long x_ = (y * other.z) - (z * other.y);
        long y_ = (z * other.x) - (x * other.z);
        long z_ = (x * other.y) - (y * other.x);

        return new Vector3l(x_, y_, z_);
    }

    public double length() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public Vector3l normalized() {
        double length = length();
        if (length == 0)
            return this;
        return div(length);
    }

    public Vector3l normalizeSelf() {
        double length = length();
        if (length == 0)
            return this;
        return divSelf(length);
    }

    public Vector3l pow(long s) {
        return new Vector3l((long) Math.pow(x, s), (long) Math.pow(y, s), (long) Math.pow(z, s));
    }

    public Vector3l pow(long xs, long ys, long zs) {
        return new Vector3l((long) Math.pow(x, xs), (long) Math.pow(y, ys), (long) Math.pow(z, zs));
    }

    public Vector3l pow(Vector3l other) {
        return new Vector3l((long) Math.pow(x, other.getX()), (long) Math.pow(y, other.getY()),
                (long) Math.pow(z, other.getZ()));
    }

    public Vector3l mod(long s) {
        return new Vector3l(x % s, y % s, z % s);
    }

    public Vector3l mod(long xs, long ys, long zs) {
        return new Vector3l(x % xs, y % ys, z % zs);
    }

    public Vector3l mod(Vector3l other) {
        return new Vector3l(x % other.getX(), y % other.getY(), z % other.getZ());
    }

    public Vector3l mul(long s) {
        return new Vector3l(x * s, y * s, z * s);
    }

    public Vector3l mul(double s) {
        return new Vector3l((long) (x * s), (long) (y * s), (long) (z * s));
    }

    public Vector3l mulSelf(long s) {
        x *= s;
        y *= s;
        z *= s;
        return this;
    }

    public Vector3l mulSelf(double s) {
        x = (long) (x * s);
        y = (long) (y * s);
        z = (long) (z * s);
        return this;
    }

    public Vector3l mul(long xs, long ys, long zs) {
        return new Vector3l(x * xs, y * ys, z * zs);
    }

    public Vector3l mul(double xs, double ys, double zs) {
        return new Vector3l((long) (x * xs), (long) (y * ys), (long) (z * zs));
    }

    public Vector3l mulSelf(long xs, long ys, long zs) {
        x *= xs;
        y *= ys;
        z *= zs;
        return this;
    }

    public Vector3l mulSelf(double xs, double ys, double zs) {
        x = (long) (x * xs);
        y = (long) (y * ys);
        z = (long) (z * zs);
        return this;
    }

    public Vector3l mul(Vector3l other) {
        return new Vector3l(x * other.x, y * other.y, z * other.z);
    }

    public Vector3l mul(Vector3d other) {
        return new Vector3l((long) (x * other.getX()), (long) (y * other.getY()), (long) (z * other.getZ()));
    }

    public Vector3l mulSelf(Vector3l other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        return this;
    }

    public Vector3l mulSelf(Vector3d other) {
        x = (long) (x * other.getX());
        y = (long) (y * other.getY());
        z = (long) (z * other.getZ());
        return this;
    }

    public Vector3l add(long s) {
        return new Vector3l(x + s, y + s, z + s);
    }

    public Vector3l addSelf(long s) {
        x += s;
        y += s;
        z += s;
        return this;
    }

    public Vector3l add(long xs, long ys, long zs) {
        return new Vector3l(x + xs, y + ys, z + zs);
    }

    public Vector3l addSelf(long xs, long ys, long zs) {
        x += xs;
        y += ys;
        z += zs;
        return this;
    }

    public Vector3l add(Vector3l other) {
        return new Vector3l(x + other.x, y + other.y, z + other.z);
    }

    public Vector3l addSelf(Vector3l other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    public Vector3l sub(long s) {
        return new Vector3l(x - s, x - s, z - s);
    }

    public Vector3l subSelf(long s) {
        x -= s;
        y -= s;
        z -= s;
        return this;
    }

    public Vector3l sub(long xs, long ys, long zs) {
        return new Vector3l(x - xs, y - ys, z - zs);
    }

    public Vector3l subSelf(long xs, long ys, long zs) {
        x -= xs;
        y -= ys;
        z -= zs;
        return this;
    }

    public Vector3l sub(Vector3l other) {
        return new Vector3l(x - other.x, y - other.y, z - other.z);
    }

    public Vector3l subSelf(Vector3l other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        return this;
    }

    public Vector3l div(long s) {
        return new Vector3l(x / s, y / s, z / s);
    }

    public Vector3l div(double s) {
        return new Vector3l((long) (x / s), (long) (y / s), (long) (z / s));
    }

    public Vector3l divSelf(long s) {
        x /= s;
        y /= s;
        z /= s;
        return this;
    }

    public Vector3l divSelf(double s) {
        x = (long) (x / s);
        y = (long) (y / s);
        z = (long) (z / s);
        return this;
    }

    public Vector3l div(long xs, long ys, long zs) {
        return new Vector3l(x / xs, y / ys, z / zs);
    }

    public Vector3l div(double xy, double ys, double zs) {
        return new Vector3l((long) (x / xy), (long) (y / ys), (long) (z / zs));
    }

    public Vector3l divSelf(long xs, long ys, long zs) {
        x /= xs;
        y /= ys;
        z /= zs;
        return this;
    }

    public Vector3l divSelf(double xs, double ys, double zs) {
        x = (long) (x / xs);
        y = (long) (y / ys);
        z = (long) (z / zs);
        return this;
    }

    public Vector3l div(Vector3l other) {
        return new Vector3l(x / other.x, y / other.y, z / other.z);
    }

    public Vector3l div(Vector3d other) {
        return new Vector3l((long) (x / other.getX()), (long) (y / other.getY()), (long) (z / other.getZ()));
    }

    public Vector3l divSelf(Vector3l other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        return this;
    }

    public Vector3l divSelf(Vector3d other) {
        x = (long) (x / other.getX());
        y = (long) (y / other.getY());
        z = (long) (z / other.getZ());
        return this;
    }

    public Vector3l abs() {
        return new Vector3l(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public Vector3l set(Vector3l value) {
        x = value.x;
        y = value.y;
        z = value.z;
        return this;
    }

    public Vector3l set(Vector3f value) {
        x = (long) value.getX();
        y = (long) value.getY();
        z = (long) value.getZ();
        return this;
    }

    public Vector3l set(Vector3d value) {
        x = (long) value.getX();
        y = (long) value.getY();
        z = (long) value.getZ();
        return this;
    }

    public Vector3l set(Vector3i value) {
        x = (long) value.getX();
        y = (long) value.getY();
        z = (long) value.getZ();
        return this;
    }

    public Vector3l set(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3l setX(long x) {
        this.x = x;
        return this;
    }

    public Vector3l setY(long y) {
        this.y = y;
        return this;
    }

    public Vector3l setZ(long z) {
        this.z = z;
        return this;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
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

    public Vector3i toVec3i() {
        return new Vector3i((int) x, (int) y, (int) z);
    }

    public Vector3d toVector3d() {
        return new Vector3d(x, y, z);
    }

    public Vector3f toVector3f() {
        return new Vector3f(x, y, z);
    }

    public Vector3i toVector3i() {
        return new Vector3i((int) x, (int) y, (int) z);
    }

    public Vector3l copy() {
        return new Vector3l(x, y, z);
    }

    public Vector3l xyz() {
        return new Vector3l(x, y, z);
    }

    public Vector3l yzx() {
        return new Vector3l(y, z, x);
    }

    public Vector3l zxy() {
        return new Vector3l(z, x, y);
    }

    public Vector3l zyx() {
        return new Vector3l(z, y, x);
    }

    public Vector3l yxz() {
        return new Vector3l(y, x, z);
    }

    public Vector3l xzy() {
        return new Vector3l(x, z, y);
    }

    public Vector2l xy() {
        return new Vector2l(x, y);
    }

    public Vector2l yz() {
        return new Vector2l(y, z);
    }

    public Vector2l zx() {
        return new Vector2l(z, x);
    }

    public Vector2l yx() {
        return new Vector2l(y, x);
    }

    public Vector2l xz() {
        return new Vector2l(x, z);
    }

    public Vector2l zy() {
        return new Vector2l(z, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3l vector3l = (Vector3l) o;
        return x == vector3l.x &&
                y == vector3l.y &&
                z == vector3l.z;
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
