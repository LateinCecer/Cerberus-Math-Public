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

public class Vector4f {

    private float x;
    private float y;
    private float z;
    private float w;

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f(Vector3f vec, float w) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
        this.w = w;
    }

    public Vector4f(Vector3f vec) {
        this(vec, 1);
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public Vector4f normalized() {
        float length = length();
        if (length == 0)
            return this;
        return div(length);
    }

    public Vector4f normalizeSelf() {
        float length = length();
        if (length == 0)
            return this;
        return divSelf(length);
    }

    public Vector4f invert() {
        return new Vector4f(-x, -y, -z, -w);
    }

    public Vector4f invertSelf() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }

    public float dot(Vector4f other) {
        return x * other.x + y * other.y + z * other.z + w * other.w;
    }

    public Vector4f mul(float s) {
        return new Vector4f(x * s, y * s, z * s, w * s);
    }

    public Vector4f mulSelf(float s) {
         x *= s;
         y *= s;
         z *= s;
         w *= s;
         return this;
    }

    public Vector4f mul(float xs, float ys, float zs, float ws) {
        return new Vector4f(x * xs, y * ys, z * zs, w * ws);
    }

    public Vector4f mulSelf(float xs, float ys, float zs, float ws) {
        x *= xs;
        y *= ys;
        z *= zs;
        w *= ws;
        return this;
    }

    public Vector4f mul(Vector4f other) {
        return new Vector4f(x * other.x, y * other.y, z * other.z, w * other.w);
    }

    @SuppressWarnings("Duplicates")
    public Vector4f mulSelf(Vector4f other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        w *= other.w;
        return this;
    }

    public Vector4f div(float s) {
        return new Vector4f(x / s, y / s, z / s, w / s);
    }

    public Vector4f divSelf(float s) {
        x /= s;
        y /= s;
        z /= s;
        w /= s;
        return this;
    }

    public Vector4f div(float xs, float ys, float zs, float ws) {
        return new Vector4f(x / xs, y / ys, z / zs, w / ws);
    }

    public Vector4f divSelf(float xs, float ys, float zs, float ws) {
        x /= xs;
        y /= ys;
        z /= zs;
        w /= ws;
        return this;
    }

    public Vector4f div(Vector4f other) {
        return new Vector4f(x / other.x, y / other.y, z / other.z, w / other.w);
    }

    @SuppressWarnings("Duplicates")
    public Vector4f divSelf(Vector4f other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        w /= other.w;
        return this;
    }

    public Vector4f add(float s) {
        return new Vector4f(x + s, y + s, z + s, w + s);
    }

    public Vector4f addSelf(float s) {
        x += s;
        y += s;
        z += s;
        w += s;
        return this;
    }

    public Vector4f add(float xs, float ys, float zs, float ws) {
        return new Vector4f(x + xs, y + ys, z + zs, w + ws);
    }

    public Vector4f addSelf(float xs, float ys, float zs, float ws) {
        x += xs;
        y += ys;
        z += zs;
        w += ws;
        return this;
    }

    public Vector4f add(Vector4f other) {
        return new Vector4f(x + other.x, y + other.y, z + other.z, w + other.w);
    }

    @SuppressWarnings("Duplicates")
    public Vector4f addSelf(Vector4f other) {
        x += other.x;
        y += other.y;
        z += other.z;
        w += other.w;
        return this;
    }

    public Vector4f sub(float s) {
        return new Vector4f(x - s, y - s, z - s, w - s);
    }

    public Vector4f subSelf(float s) {
        x -= s;
        y -= s;
        z -= s;
        w -= s;
        return this;
    }

    public Vector4f sub(float xs, float ys, float zs, float ws) {
        return new Vector4f(x - xs, y - ys, z - zs, w - ws);
    }

    public Vector4f subSelf(float xs, float ys, float zs, float ws) {
        x -= xs;
        y -= ys;
        z -= zs;
        w -= ws;
        return this;
    }

    public Vector4f sub(Vector4f other) {
        return new Vector4f(x - other.x, y - other.y, z - other.z, w - other.w);
    }

    @SuppressWarnings("Duplicates")
    public Vector4f subSelf(Vector4f other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        w -= other.w;
        return this;
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

    public float getW() {
        return w;
    }

    public Vector4f setX(float value) {
        x = value;
        return this;
    }

    public Vector4f setY(float value) {
        y = value;
        return this;
    }

    public Vector4f setZ(float value) {
        z = value;
        return this;
    }

    public Vector4f setW(float value) {
        w = value;
        return this;
    }

    public Vector4f set(Vector4f value) {
        x = value.x;
        y = value.y;
        z = value.z;
        w = value.w;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4f set(Vector4d value) {
        x = (float) value.getX();
        y = (float) value.getY();
        z = (float) value.getZ();
        w = (float) value.getW();
        return this;
    }

    public Vector4f set(Vector4i value) {
        x = value.getX();
        y = value.getY();
        z = value.getZ();
        w = value.getW();
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4f set(Vector4l value) {
        x = (float) value.getX();
        y = (float) value.getY();
        z = (float) value.getZ();
        w = (float) value.getW();
        return this;
    }

    public Vector4f set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Vector4f set(Vector3f vec, float w) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
        this.w = w;
        return this;
    }

    public Vector3f xyz() {
        return new Vector3f(x, y, z);
    }

    public Vector4d toVector4d() {
        return new Vector4d(x, y, z, w);
    }

    public Vector4i toVector4i() {
        return new Vector4i((int) x, (int) y, (int) z, (int) w);
    }

    public Vector4l toVector4l() {
        return new Vector4l((long) x, (long) y, (long) z, (long) w);
    }

    public Vector4f copy() {
        return new Vector4f(x, y, z, w);
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y + " z: " + z + " w: " + w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector4f vector4f = (Vector4f) o;
        return Float.compare(vector4f.x, x) == 0 &&
                Float.compare(vector4f.y, y) == 0 &&
                Float.compare(vector4f.z, z) == 0 &&
                Float.compare(vector4f.w, w) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }
}

