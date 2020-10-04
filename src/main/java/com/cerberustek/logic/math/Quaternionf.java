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
@SuppressWarnings("Duplicates")
public class Quaternionf {

    private float x;
    private float y;
    private float z;
    private float w;

    public Quaternionf(Vector4f vec) {
        this(vec.getX(), vec.getY(), vec.getZ(), vec.getW());
    }

    public Quaternionf() {
        this(0, 0, 0, 1);
    }

    public Quaternionf(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Deprecated
    public Quaternionf(Vector3f axis, float angel) {
        this.initRotation(angel, axis);
    }

    public Quaternionf(float angel, Vector3f axis) {
        this.initRotation(angel, axis);
    }

    public Quaternionf(Matrix4f rot)
    {
        float trace = rot.get(0, 0) + rot.get(1, 1) + rot.get(2, 2);

        if(trace > 0)
        {
            float s = 0.5f / (float)Math.sqrt(trace+ 1.0f);
            w = 0.25f / s;
            x = (rot.get(1, 2) - rot.get(2, 1)) * s;
            y = (rot.get(2, 0) - rot.get(0, 2)) * s;
            z = (rot.get(0, 1) - rot.get(1, 0)) * s;
        }
        else
        {
            if(rot.get(0, 0) > rot.get(1, 1) && rot.get(0, 0) > rot.get(2, 2))
            {
                float s = 2.0f * (float)Math.sqrt(1.0f + rot.get(0, 0) - rot.get(1, 1) - rot.get(2, 2));
                w = (rot.get(1, 2) - rot.get(2, 1)) / s;
                x = 0.25f * s;
                y = (rot.get(1, 0) + rot.get(0, 1)) / s;
                z = (rot.get(2, 0) + rot.get(0, 2)) / s;
            }
            else if(rot.get(1, 1) > rot.get(2, 2))
            {
                float s = 2.0f * (float)Math.sqrt(1.0f + rot.get(1, 1) - rot.get(0, 0) - rot.get(2, 2));
                w = (rot.get(2, 0) - rot.get(0, 2)) / s;
                x = (rot.get(1, 0) + rot.get(0, 1)) / s;
                y = 0.25f * s;
                z = (rot.get(2, 1) + rot.get(1, 2)) / s;
            }
            else
            {
                float s = 2.0f * (float)Math.sqrt(1.0f + rot.get(2, 2) - rot.get(0, 0) - rot.get(1, 1));
                w = (rot.get(0, 1) - rot.get(1, 0) ) / s;
                x = (rot.get(2, 0) + rot.get(0, 2) ) / s;
                y = (rot.get(1, 2) + rot.get(2, 1) ) / s;
                z = 0.25f * s;
            }
        }

        float length = (float)Math.sqrt(x * x + y * y + z * z + w * w);
        x /= length;
        y /= length;
        z /= length;
        w /= length;
    }

    public Quaternionf initRotation(float angle, Vector3f axis) {
        float sinHalfAngle = (float) Math.sin(angle / 2f);

        this.x = axis.getX() * sinHalfAngle;
        this.y = axis.getY() * sinHalfAngle;
        this.z = axis.getZ() * sinHalfAngle;
        this.w = (float) Math.cos(angle / 2f);

        return this;
    }

    public float length() {
        return (float) Math.sqrt((x * x) + (y * y) + (z * z) + (w * w));
    }

    public Quaternionf normalized() {
        float length = length();
        if (length == 0)
            return this;
        return div(length);
    }

    public Quaternionf normalizeSelf() {
        float length = length();
        if (length == 0)
            return this;
        return divSelf(length);
    }

    public Quaternionf conjugate() {
        return new Quaternionf(-x, -y, -z, w);
    }

    public Quaternionf conjugateSelf() {
        x *= -1;
        y *= -1;
        z *= -1;
        return this;
    }

    public Quaternionf inverse() {
        float dot = x * x + y * y + z * z + w * w;
        return new Quaternionf(-x / dot, -y / dot, -z / dot, w / dot);
    }

    public Quaternionf invertSelf() {
        float dot = x * x + y * y + z * z + w * w;
        x /= - dot;
        y /= - dot;
        z /= - dot;
        w /= dot;
        return this;
    }

    public float dot(Quaternionf other) {
        return x * other.x + y * other.y + z * other.z + w * other.w;
    }

    @SuppressWarnings("Duplicates")
    public Quaternionf mul(Quaternionf r) {
        float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
        float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
        float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
        float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

        return new Quaternionf(x_, y_, z_, w_);
    }

    @SuppressWarnings("Duplicates")
    public Quaternionf mulSelf(Quaternionf r) {
        float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
        float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
        float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
        float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

        w = w_;
        x = x_;
        y = y_;
        z = z_;
        return this;
    }

    public Quaternionf mul(Vector3f vector) {
        float w_ = - x * vector.getX() - y * vector.getY() - z * vector.getZ();
        float x_ = w * vector.getX() + y * vector.getZ() - z * vector.getY();
        float y_ = w * vector.getY() + z * vector.getX() - x * vector.getZ();
        float z_ = w * vector.getZ() + x * vector.getY() - y * vector.getX();

        return new Quaternionf(x_, y_, z_, w_);
    }

    public Vector3f mul3f(Vector3f vec) {
        float x_ = w * vec.getX() + y * vec.getZ() - z * vec.getY();
        float y_ = w * vec.getY() + z * vec.getX() - x * vec.getZ();
        float z_ = w * vec.getZ() + x * vec.getY() - y * vec.getX();

        return new Vector3f(x_, y_, z_);
    }

    public Quaternionf mul(Vector3d vector) {
        float w_ = (float) (- x * vector.getX() - y * vector.getY() - z * vector.getZ());
        float x_ = (float) (w * vector.getX() + y * vector.getZ() - z * vector.getY());
        float y_ = (float) (w * vector.getY() + z * vector.getX() - x * vector.getZ());
        float z_ = (float) (w * vector.getZ() + x * vector.getY() - y * vector.getX());

        return new Quaternionf(x_, y_, z_, w_);
    }

    public Vector3d mul3d(Vector3d vec) {
        double x_ = w * vec.getX() + y * vec.getZ() - z * vec.getY();
        double y_ = w * vec.getY() + z * vec.getX() - x * vec.getZ();
        double z_ = w * vec.getZ() + x * vec.getY() - y * vec.getX();

        return new Vector3d(x_, y_, z_);
    }

    public Quaternionf mul(float value) {
        return new Quaternionf(
                x * value,
                y * value,
                z * value,
                w * value
        );
    }

    public Quaternionf mulSelf(float s) {
        x *= s;
        y *= s;
        z *= s;
        w *= s;
        return this;
    }

    public Quaternionf div(float s) {
        return new Quaternionf(x / s, y / s, z / s, w / s);
    }

    public Quaternionf divSelf(float s) {
        x /= s;
        y /= s;
        z /= s;
        w /= s;
        return this;
    }

    public Quaternionf add(Quaternionf other) {
        return new Quaternionf(
                x + other.x,
                y + other.y,
                z + other.z,
                w + other.w
        );
    }

    @SuppressWarnings("Duplicates")
    public Quaternionf addSelf(Quaternionf other) {
        x += other.x;
        y += other.y;
        z += other.z;
        w += other.w;
        return this;
    }

    public Quaternionf sub(Quaternionf other) {
        return new Quaternionf(
                x - other.x,
                y - other.y,
                z - other.z,
                w - other.w
        );
    }

    @SuppressWarnings("Duplicates")
    public Quaternionf subSelf(Quaternionf other) {
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

    public Vector3f re() {
        return new Vector3f(x, y, z);
    }

    public float im() {
        return w;
    }

    @SuppressWarnings("Duplicates")
    public Quaternionf set(Quaterniond value) {
        x = (float) value.getX();
        y = (float) value.getY();
        z = (float) value.getZ();
        w = (float) value.getW();
        return this;
    }

    @SuppressWarnings("Duplicates")
    public Quaternionf set(Quaternionf other) {
        this.w = other.w;
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        return this;
    }

    public Quaternionf set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vector3f xyz() {
        return new Vector3f(x, y, z);
    }

    public Quaternionf setX(float x) {
        this.x = x;
        return this;
    }

    public Quaternionf setY(float y) {
        this.y = y;
        return this;
    }

    public Quaternionf setZ(float z) {
        this.z = z;
        return this;
    }

    public Quaternionf setW(float w) {
        this.w = w;
        return this;
    }

    public Matrix4f toRotationMatrix() {
        return new Matrix4f().initRotation(getForward(), getUp(), getRight());
    }

    public Matrix4f toInverseRotationMatrix() {
        return inverse().toRotationMatrix();
    }

    public Vector3f getForward() {
        return new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
    }

    public Vector3f getBack()
    {
        return getForward().mul(-1);
    }

    public Vector3f getUp() {
        return new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
    }

    public Vector3f getDown()
    {
        return getUp().mul(-1);
    }

    public Vector3f getRight() {
        return new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));
    }

    public Vector3f getLeft()
    {
        return getRight().mul(-1);
    }

    public Quaterniond toQuaterniond() {
        return new Quaterniond(x, y, z, w);
    }

    public Quaternionf copy() {
        return new Quaternionf(x, y, z, w);
    }

    @Override
    public Quaternionf clone() throws CloneNotSupportedException {
        super.clone();
        return new Quaternionf(x, y, z, w);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quaternionf that = (Quaternionf) o;
        return Float.compare(that.x, x) == 0 &&
                Float.compare(that.y, y) == 0 &&
                Float.compare(that.z, z) == 0 &&
                Float.compare(that.w, w) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    @Override
    public String toString() {
        return "X: " + x + " Y: " + y + " Z: " + z + " W: " + w;
    }
}
