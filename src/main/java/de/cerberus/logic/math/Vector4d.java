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

public class Vector4d {

    private double x;
    private double y;
    private double z;
    private double w;

    public Vector4d(Vector4d other) {
        this(other.getX(), other.getY(), other.getZ(), other.getW());
    }

    public Vector4d(double value) {
        this(value, value, value, value);
    }

    public Vector4d(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4d(Vector3d vec, double w) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
        this.w = w;
    }

    public Vector4d(double x, Vector3d vec) {
        this(x, vec.getX(), vec.getY(), vec.getZ());
    }

    public Vector4d(Vector2d vec1, Vector2d vec2) {
        this(vec1.getX(), vec1.getY(), vec2.getX(), vec2.getY());
    }

    public Vector4d(Vector2d vec, double z, double w) {
        this(vec.getX(), vec.getY(), z, w);
    }

    public Vector4d(double x, Vector2d vec, double w) {
        this(x, vec.getX(), vec.getY(), w);
    }

    public Vector4d(double x, double y, Vector2d vec) {
        this(x, y, vec.getX(), vec.getY());
    }

    public Vector4d(Vector3d vec) {
        this(vec, 1);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public Vector4d normalized() {
        double length = length();
        if (length == 0)
            return this;
        return div(length);
    }

    public Vector4d normalizeSelf() {
        double length = length();
        if (length == 0)
            return this;
        return divSelf(length);
    }

    public Vector4d invert() {
        return new Vector4d(-x, -y, -z, -w);
    }

    public Vector4d invertSelf() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    public double dot(Vector4d other) {
        return x * other.x + y * other.y + z * other.z + w * other.w;
    }

    public Vector4d mul(double s) {
        return new Vector4d(x * s, y * s, z * s, w * s);
    }

    public Vector4d mulSelf(double s) {
        x *= s;
        y *= s;
        z *= s;
        w *= s;
        return this;
    }

    public Vector4d mul(double xs, double ys, double zs, double ws) {
        return new Vector4d(x * xs, y * ys, z * zs, w * ws);
    }

    public Vector4d mulSelf(double xs, double ys, double zs, double ws) {
        x *= xs;
        y *= ys;
        z *= zs;
        w *= ws;
        return this;
    }

    public Vector4d mul(Vector4d other) {
        return new Vector4d(x * other.x, y * other.y, z * other.z, w * other.w);
    }

    @SuppressWarnings("Duplicates")
    public Vector4d mulSelf(Vector4d other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        w *= other.w;
        return this;
    }

    public Vector4d div(double s) {
        return new Vector4d(x / s, y / s, z / s, w / s);
    }

    public Vector4d divSelf(double s) {
        x /= s;
        y /= s;
        z /= s;
        w /= s;
        return this;
    }

    public Vector4d div(double xs, double ys, double zs, double ws) {
        return new Vector4d(x / xs, y / ys, z / zs, w / ws);
    }

    public Vector4d divSelf(double xs, double ys, double zs, double ws) {
        x /= xs;
        y /= ys;
        z /= zs;
        w /= ws;
        return this;
    }

    public Vector4d div(Vector4d other) {
        return new Vector4d(x / other.x, y / other.y, z / other.z, w / other.w);
    }

    @SuppressWarnings("Duplicates")
    public Vector4d divSelf(Vector4d other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        w /= other.w;
        return this;
    }

    public Vector4d add(double s) {
        return new Vector4d(x + s, y + s, z + s, w + s);
    }

    public Vector4d addSelf(double s) {
        x += s;
        y += s;
        z += s;
        w += s;
        return this;
    }

    public Vector4d add(double xs, double ys, double zs, double ws) {
        return new Vector4d(x + xs, y + ys, z + zs, w + ws);
    }

    public Vector4d addSelf(double xs, double ys, double zs, double ws) {
        x += xs;
        y += ys;
        z += zs;
        w += ws;
        return this;
    }

    public Vector4d add(Vector4d other) {
        return new Vector4d(x + other.x, y + other.y, z + other.z, w + other.w);
    }

    @SuppressWarnings("Duplicates")
    public Vector4d addSelf(Vector4d other) {
        x += other.x;
        y += other.y;
        z += other.z;
        w += other.w;
        return this;
    }

    public Vector4d sub(double s) {
        return new Vector4d(x - s, y - s, z - s, w - s);
    }

    public Vector4d subSelf(double s) {
        x -= s;
        y -= s;
        z -= s;
        w -= s;
        return this;
    }

    public Vector4d sub(double xs, double ys, double zs, double ws) {
        return new Vector4d(x - xs, y - ys, z - zs, w - ws);
    }

    public Vector4d subSelf(double xs, double ys, double zs, double ws) {
        x -= xs;
        y -= ys;
        z -= zs;
        w -= ws;
        return this;
    }

    public Vector4d sub(Vector4d other) {
        return new Vector4d(x - other.x, y - other.y, z - other.z, w - other.w);
    }

    @SuppressWarnings("Duplicates")
    public Vector4d subSelf(Vector4d other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        w -= other.w;
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

    public double getW() {
        return w;
    }

    public Vector4d setX(double value) {
        x = value;
        return this;
    }

    public Vector4d setY(double value) {
        y = value;
        return this;
    }

    public Vector4d setZ(double value) {
        z = value;
        return this;
    }

    public Vector4d setW(double value) {
        w = value;
        return this;
    }

    public Vector4d set(Vector4d value) {
        x = value.x;
        y = value.y;
        z = value.z;
        w = value.w;
        return this;
    }

    public Vector4d set(Vector4f value) {
        x = value.getX();
        y = value.getY();
        z = value.getZ();
        w = value.getW();
        return this;
    }

    public Vector4d set(Vector4i value) {
        x = value.getX();
        y = value.getY();
        z = value.getZ();
        w = value.getW();
        return this;
    }

    public Vector4d set(Vector4l value) {
        x = value.getX();
        y = value.getY();
        z = value.getZ();
        w = value.getW();
        return this;
    }

    public Vector4d set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4d set(Vector3i vec, double w) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
        this.w = w;
        return this;
    }

    public Vector3d xyz() {
        return new Vector3d(x, y, z);
    }

    public Vector4f toVector4f() {
        return new Vector4f((float) x, (float) y, (float) z, (float) w);
    }

    public Vector4i toVector4i() {
        return new Vector4i((int) x, (int) y, (int) z, (int) w);
    }

    public Vector4l toVector4l() {
        return new Vector4l((long) x, (long) y, (long) z, (long) w);
    }

    public Vector4d copy() {
        return new Vector4d(x, y, z, w);
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y + " z: " + z + " w: " + w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector4d vector4d = (Vector4d) o;
        return Double.compare(vector4d.x, x) == 0 &&
                Double.compare(vector4d.y, y) == 0 &&
                Double.compare(vector4d.z, z) == 0 &&
                Double.compare(vector4d.w, w) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }
}
