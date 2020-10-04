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

public class Vector4l {
    
    private long x;
    private long y;
    private long z;
    private long w;

    public Vector4l(long x, long y, long z, long w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4l(Vector3l vec, long w) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
        this.w = w;
    }

    public Vector4l(Vector3l vec) {
        this(vec, 1);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public Vector4l normalized() {
        double length = length();
        if (length == 0)
            return this;
        return div(length);
    }

    public Vector4l normalizeSelf() {
        double length = length();
        if (length == 0)
            return this;
        return divSelf(length);
    }

    public Vector4l invert() {
        return new Vector4l(-x, -y, -z, -w);
    }

    public Vector4l invertSelf() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    public double dot(Vector4l other) {
        return x * other.x + y * other.y + z * other.z + w * other.w;
    }

    public Vector4l mul(long s) {
        return new Vector4l(x * s, y * s, z * s, w * s);
    }

    public Vector4l mul(double s) {
        return new Vector4l((long) (x * s), (long) (y * s), (long) (z * s), (long) (w * s));
    }

    public Vector4l mulSelf(long s) {
        x *= s;
        y *= s;
        z *= s;
        w *= s;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4l mulSelf(double s) {
        x = (long) (x * s);
        y = (long) (y * s);
        z = (long) (z * s);
        w = (long) (w * s);
        return this;
    }

    public Vector4l mul(long xs, long ys, long zs, long ws) {
        return new Vector4l(x * xs, y * ys, z * zs, w * ws);
    }

    public Vector4l mul(double xs, double ys, double zs, double ws) {
        return new Vector4l((long) (x * xs), (long) (y * ys), (long) (z * zs), (long) (w * ws));
    }

    public Vector4l mulSelf(long xs, long ys, long zs, long ws) {
        x *= xs;
        y *= ys;
        z *= zs;
        w *= ws;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4l mulSelf(double xs, double ys, double zs, double ws) {
        x = (long) (x * xs);
        y = (long) (y * ys);
        z = (long) (z * zs);
        w = (long) (w * ws);
        return this;
    }

    public Vector4l mul(Vector4l other) {
        return new Vector4l(x * other.x, y * other.y, z * other.z, w * other.w);
    }

    public Vector4l mul(Vector4d other) {
        return new Vector4l((long) (x * other.getX()), (long) (y * other.getY()), (long) (z * other.getZ()), (long) (w * other.getW()));
    }

    @SuppressWarnings("Duplicates")
    public Vector4l mulSelf(Vector4l other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        w *= other.w;
        return this;
    }

    public Vector4l mulSelf(Vector4d other) {
        x = (long) (x * other.getX());
        y = (long) (y * other.getY());
        z = (long) (z * other.getZ());
        w = (long) (w * other.getW());
        return this;
    }

    public Vector4l div(long s) {
        return new Vector4l(x / s, y / s, z / s, w / s);
    }

    public Vector4l div(double s) {
        return new Vector4l((long) (x / s), (long) (y / s), (long) (z / s), (long) (w / s));
    }

    public Vector4l divSelf(long s) {
        x /= s;
        y /= s;
        z /= s;
        w /= s;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4l divSelf(double s) {
        x = (long) (x / s);
        y = (long) (y / s);
        z = (long) (z / s);
        w = (long) (w / s);
        return this;
    }

    public Vector4l div(long xs, long ys, long zs, long ws) {
        return new Vector4l(x / xs, y / ys, z / zs, w / ws);
    }

    public Vector4l div(double xs, double ys, double zs, double ws) {
        return new Vector4l((long) (x / xs), (long) (y / ys), (long) (z / zs), (long) (w / ws));
    }

    public Vector4l divSelf(long xs, long ys, long zs, long ws) {
        x /= xs;
        y /= ys;
        z /= zs;
        w /= ws;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4l divSelf(double xs, double ys, double zs, double ws) {
        x = (long) (x / xs);
        y = (long) (y / ys);
        z = (long) (z / zs);
        w = (long) (w / ws);
        return this;
    }

    public Vector4l div(Vector4l other) {
        return new Vector4l(x / other.x, y / other.y, z / other.z, w / other.w);
    }

    public Vector4l div(Vector4d other) {
        return new Vector4l((long) (x / other.getX()), (long) (y / other.getY()), (long) (z / other.getZ()), (long) (w / other.getW()));
    }

    @SuppressWarnings("Duplicates")
    public Vector4l divSelf(Vector4l other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        w /= other.w;
        return this;
    }

    public Vector4l divSelf(Vector4d other) {
        x = (long) (x / other.getX());
        y = (long) (y / other.getY());
        z = (long) (z / other.getZ());
        w = (long) (w / other.getW());
        return this;
    }

    public Vector4l add(long s) {
        return new Vector4l(x + s, y + s, z + s, w + s);
    }

    public Vector4l addSelf(long s) {
        x += s;
        y += s;
        z += s;
        w += s;
        return this;
    }

    public Vector4l add(long xs, long ys, long zs, long ws) {
        return new Vector4l(x + xs, y + ys, z + zs, w + ws);
    }

    public Vector4l addSelf(long xs, long ys, long zs, long ws) {
        x += xs;
        y += ys;
        z += zs;
        w += ws;
        return this;
    }

    public Vector4l add(Vector4l other) {
        return new Vector4l(x + other.x, y + other.y, z + other.z, w + other.w);
    }

    @SuppressWarnings("Duplicates")
    public Vector4l addSelf(Vector4l other) {
        x += other.x;
        y += other.y;
        z += other.z;
        w += other.w;
        return this;
    }

    public Vector4l sub(long s) {
        return new Vector4l(x - s, y - s, z - s, w - s);
    }

    public Vector4l subSelf(long s) {
        x -= s;
        y -= s;
        z -= s;
        w -= s;
        return this;
    }

    public Vector4l sub(long xs, long ys, long zs, long ws) {
        return new Vector4l(x - xs, y - ys, z - zs, w - ws);
    }

    public Vector4l subSelf(long xs, long ys, long zs, long ws) {
        x -= xs;
        y -= ys;
        z -= zs;
        w -= ws;
        return this;
    }

    public Vector4l sub(Vector4l other) {
        return new Vector4l(x - other.x, y - other.y, z - other.z, w - other.w);
    }

    @SuppressWarnings("Duplicates")
    public Vector4l subSelf(Vector4l other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        w -= other.w;
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

    public long getW() {
        return w;
    }

    public Vector4l setX(long value) {
        x = value;
        return this;
    }

    public Vector4l setY(long value) {
        y = value;
        return this;
    }

    public Vector4l setZ(long value) {
        z = value;
        return this;
    }

    public Vector4l setW(long value) {
        w = value;
        return this;
    }

    public Vector4l set(Vector4l value) {
        x = value.x;
        y = value.y;
        z = value.z;
        w = value.w;
        return this;
    }

    public Vector4l set(Vector4f value) {
        x = (long) value.getX();
        y = (long) value.getY();
        z = (long) value.getZ();
        w = (long) value.getW();
        return this;
    }

    public Vector4l set(Vector4i value) {
        x = value.getX();
        y = value.getY();
        z = value.getZ();
        w = value.getW();
        return this;
    }

    public Vector4l set(Vector4d value) {
        x = (long) value.getX();
        y = (long) value.getY();
        z = (long) value.getY();
        w = (long) value.getW();
        return this;
    }

    public Vector4l set(long x, long y, long z, long w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4l set(Vector3i vec, long w) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
        this.w = w;
        return this;
    }

    public Vector3l xyz() {
        return new Vector3l(x, y, z);
    }

    public Vector4d toVector4d() {
        return new Vector4d(x, y, z, w);
    }

    public Vector4f toVector4f() {
        return new Vector4f((float) x, (float) y, (float) z, (float) w);
    }

    public Vector4i toVector4i() {
        return new Vector4i((int) x, (int) y, (int) z, (int) w);
    }

    public Vector4l copy() {
        return new Vector4l(x, y, z, w);
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y + " z: " + z + " w: " + w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector4l vector4l = (Vector4l) o;
        return x == vector4l.x &&
                y == vector4l.y &&
                z == vector4l.z &&
                w == vector4l.w;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }
}
