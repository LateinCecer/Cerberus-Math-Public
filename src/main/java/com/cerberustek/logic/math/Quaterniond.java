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
public class Quaterniond {

    private double x;
    private double y;
    private double z;
    private double w;

    public Quaterniond(Vector4d vec) {
        this(vec.getX(), vec.getY(), vec.getZ(), vec.getW());
    }

    public Quaterniond(Vector3d vec, float w) {
        this(vec.getX(), vec.getY(), vec.getZ(), w);
    }

    public Quaterniond(Vector2d vec1, Vector2d vec2) {
        this(vec1.getX(), vec2.getY(), vec2.getX(), vec2.getY());
    }

    public Quaterniond(Vector2d vec, float z, float w) {
        this(vec.getX(), vec.getY(), z, w);
    }

    public Quaterniond(float x, float y, Vector2d vec) {
        this(x, y, vec.getX(), vec.getY());
    }

    public Quaterniond(Vector4f vec) {
        this(vec.getX(), vec.getY(), vec.getZ(), vec.getW());
    }

    public Quaterniond(Vector3f vec, float w) {
        this(vec.getX(), vec.getY(), vec.getZ(), w);
    }

    public Quaterniond(Vector2f vec1, Vector2f vec2) {
        this(vec1.getX(), vec1.getY(), vec2.getX(), vec2.getY());
    }

    public Quaterniond(Vector2f vec, float z, float w) {
        this(vec.getX(), vec.getY(), z, w);
    }

    public Quaterniond(float x, float y, Vector2f vec) {
        this(x, y, vec.getX(), vec.getY());
    }

    public Quaterniond() {
        this(0, 0, 0, 1);
    }

    public Quaterniond(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaterniond(double angle, Vector3d axis) {
        this.initRotation(angle, axis);
    }

    public Quaterniond(Matrix4d rot)
    {
        double trace = rot.get(0, 0) + rot.get(1, 1) + rot.get(2, 2);

        if(trace > 0)
        {
            double s = 0.5f / Math.sqrt(trace+ 1.0f);
            w = 0.25f / s;
            x = (rot.get(1, 2) - rot.get(2, 1)) * s;
            y = (rot.get(2, 0) - rot.get(0, 2)) * s;
            z = (rot.get(0, 1) - rot.get(1, 0)) * s;
        }
        else
        {
            if(rot.get(0, 0) > rot.get(1, 1) && rot.get(0, 0) > rot.get(2, 2))
            {
                double s = 2.0f * Math.sqrt(1.0f + rot.get(0, 0) - rot.get(1, 1) - rot.get(2, 2));
                w = (rot.get(1, 2) - rot.get(2, 1)) / s;
                x = 0.25f * s;
                y = (rot.get(1, 0) + rot.get(0, 1)) / s;
                z = (rot.get(2, 0) + rot.get(0, 2)) / s;
            }
            else if(rot.get(1, 1) > rot.get(2, 2))
            {
                double s = 2.0f * Math.sqrt(1.0f + rot.get(1, 1) - rot.get(0, 0) - rot.get(2, 2));
                w = (rot.get(2, 0) - rot.get(0, 2)) / s;
                x = (rot.get(1, 0) + rot.get(0, 1)) / s;
                y = 0.25f * s;
                z = (rot.get(2, 1) + rot.get(1, 2)) / s;
            }
            else
            {
                double s = 2.0f * Math.sqrt(1.0f + rot.get(2, 2) - rot.get(0, 0) - rot.get(1, 1));
                w = (rot.get(0, 1) - rot.get(1, 0) ) / s;
                x = (rot.get(2, 0) + rot.get(0, 2) ) / s;
                y = (rot.get(1, 2) + rot.get(2, 1) ) / s;
                z = 0.25f * s;
            }
        }

        double length = Math.sqrt(x * x + y * y + z * z + w * w);
        x /= length;
        y /= length;
        z /= length;
        w /= length;
    }

    public double length() {
        return Math.sqrt((x * x) + (y * y) + (z * z) + (w * w));
    }

    public Quaterniond normalized() {
        double length = length();
        if (length == 0)
            return this;
        return div(length);
    }

    public Quaterniond normalizeSelf() {
        double length = length();
        if (length == 0)
            return this;
        return divSelf(length);
    }

    public Quaterniond initRotation(double angle, Vector3f axis) {
        return initRotation(angle, axis.toVector3d());
    }

    public Quaterniond initRotation(double angle, Vector3d axis) {
        double sinHalfAngle = Math.sin(angle / 2f);

        this.x = axis.getX() * sinHalfAngle;
        this.y = axis.getY() * sinHalfAngle;
        this.z = axis.getZ() * sinHalfAngle;
        this.w = Math.cos(angle / 2f);

        return this;
    }

    public boolean isActive() {
        return w != 0 && (x != 0 || y != 0 || z != 0);
    }

    public Quaterniond conjugate() {
        return new Quaterniond(-x, -y, -z, w);
    }

    public Quaterniond conjugateSelf() {
        x *= -1;
        y *= -1;
        z *= -1;
        return this;
    }

    public Quaterniond inverse() {
        double dot = x * x + y * y + z * z + w * w;
        return new Quaterniond(-x / dot, -y / dot, -z / dot, w / dot);
    }

    public Quaterniond invertSelf() {
        double dot = x * x + y * y + z * z + w * w;
        x /= - dot;
        y /= - dot;
        z /= - dot;
        w /= dot;
        return this;
    }

    public double dot(Quaterniond other) {
        return x * other.x + y * other.y + z * other.z + w * other.w;
    }

    public Quaterniond mul(Quaterniond other) {
        double w_ = (w * other.w) - (x * other.x) - (y * other.y) - (z * other.z);
        double x_ = (x * other.w) + (w * other.x) + (y * other.z) - (z * other.y);
        double y_ = (y * other.w) + (w * other.y) + (z * other.x) - (x * other.z);
        double z_ = (z * other.w) + (w * other.z) + (x * other.y) - (y * other.x);

        return new Quaterniond(x_, y_, z_, w_);
    }

    public Quaterniond mulSelf(Quaterniond other) {
        double w_ = (w * other.w) - (x * other.x) - (y * other.y) - (z * other.z);
        double x_ = (x * other.w) + (w * other.x) + (y * other.z) - (z * other.y);
        double y_ = (y * other.w) + (w * other.y) + (z * other.x) - (x * other.z);
        double z_ = (z * other.w) + (w * other.z) + (x * other.y) - (y * other.x);

        w = w_;
        x = x_;
        y = y_;
        z = z_;
        return this;
    }

    public Quaterniond mul(Vector3f vector) {
        double w_ = - x * vector.getX() - y * vector.getY() - z * vector.getZ();
        double x_ = w * vector.getX() + y * vector.getZ() - z * vector.getY();
        double y_ = w * vector.getY() + z * vector.getX() - x * vector.getZ();
        double z_ = w * vector.getZ() + x * vector.getY() - y * vector.getX();

        return new Quaterniond(x_, y_, z_, w_);
    }

    public Vector3f mul3f(Vector3f vec) {
        float x_ = (float) (w * vec.getX() + y * vec.getZ() - z * vec.getY());
        float y_ = (float) (w * vec.getY() + z * vec.getX() - x * vec.getZ());
        float z_ = (float) (w * vec.getZ() + x * vec.getY() - y * vec.getX());

        return new Vector3f(x_, y_, z_);
    }

    public Quaterniond mul(Vector3d vector) {
        double w_ = - x * vector.getX() - y * vector.getY() - z * vector.getZ();
        double x_ = w * vector.getX() + y * vector.getZ() - z * vector.getY();
        double y_ = w * vector.getY() + z * vector.getX() - x * vector.getZ();
        double z_ = w * vector.getZ() + x * vector.getY() - y * vector.getX();

        return new Quaterniond(x_, y_, z_, w_);
    }

    public Vector3d mul3d(Vector3d vec) {
        double x_ = w * vec.getX() + y * vec.getZ() - z * vec.getY();
        double y_ = w * vec.getY() + z * vec.getX() - x * vec.getZ();
        double z_ = w * vec.getZ() + x * vec.getY() - y * vec.getX();

        return new Vector3d(x_, y_, z_);
    }

    public Quaterniond mul(double value) {
        return new Quaterniond(
                x * value,
                y * value,
                z * value,
                w * value
        );
    }

    public Quaterniond mulSelf(double s) {
        x *= s;
        y *= s;
        z *= s;
        w *= s;
        return this;
    }

    public Quaterniond div(double s) {
        return new Quaterniond(x / s, y / s, z / s, w / s);
    }

    public Quaterniond divSelf(double s) {
        x /= s;
        y /= s;
        z /= s;
        w /= s;
        return this;
    }

    public Quaterniond add(Quaterniond other) {
        return new Quaterniond(
                x + other.x,
                y + other.y,
                z + other.z,
                w + other.w
        );
    }

    public Quaterniond addSelf(Quaterniond other) {
        x += other.x;
        y += other.y;
        z += other.z;
        w += other.w;
        return this;
    }

    public Quaterniond sub(Quaterniond other) {
        return new Quaterniond(
                x - other.x,
                y - other.y,
                z - other.z,
                w - other.w
        );
    }

    public Quaterniond subSelf(Quaterniond other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        w -= other.w;
        return this;
    }

    public Vector3d re() {
        return new Vector3d(x, y, z);
    }

    public double im() {
        return w;
    }

    public Matrix4d toRotationMatrix() {
        return new Matrix4d().initRotation(getForward(), getUp(), getRight());
    }

    public Matrix4d toInverseRotationMatrix() {
        return inverse().toRotationMatrix();
    }

    public Vector3d getForward() {
        return new Vector3d(2.0d * (x * z - w * y), 2.0d * (y * z + w * x), 1.0d - 2.0d * (x * x + y * y));
    }

    public Vector3d getBack() {
        return getForward().mul(-1);
    }

    public Vector3d getUp() {
        return new Vector3d(2.0d * (x * y + w * z), 1.0d - 2.0d * (x * x + z * z), 2.0d * (y * z - w * x));
    }

    public Vector3d getDown() {
        return getUp().mul(-1);
    }

    public Vector3d getRight() {
        return new Vector3d(1.0d - 2.0d * (y * y + z * z), 2.0d * (x * y - w * z), 2.0d * (x * z + w * y));
    }

    public Vector3d getLeft() {
        return getRight().mul(-1);
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

    public Quaterniond set(Quaternionf other) {
        x = other.getX();
        y = other.getY();
        z = other.getZ();
        w = other.getW();
        return this;
    }

    public Quaterniond set(Quaterniond other) {
        this.w = other.w;
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        return this;
    }

    public Quaterniond set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vector4d toVec4d() {
        return new Vector4d(x, y, z, w);
    }

    public Vector4d toVector4d() {
        return new Vector4d(x, y, z, w);
    }

    public Vector4f toVec4f() {
        return new Vector4f((float) x, (float) y, (float) z, (float) w);
    }

    public Vector4f toVector4f() {
        return new Vector4f((float) x, (float) y, (float) z, (float) w);
    }

    public Vector3d xyz() {
        return new Vector3d(x, y, z);
    }

    public Vector3d yzw() {
        return new Vector3d(y, z, w);
    }

    public Vector3d zwx() {
        return new Vector3d(z, w, x);
    }

    public Vector3d wxy() {
        return new Vector3d(w, x, y);
    }

    public Vector3d zyx() {
        return new Vector3d(z, y, x);
    }

    public Vector3d yxw() {
        return new Vector3d(y, x, w);
    }

    public Vector3d xwz() {
        return new Vector3d(x, w, z);
    }

    public Vector3d wzy() {
        return new Vector3d(w, z, y);
    }

    public Vector2d xy() {
        return new Vector2d(x, y);
    }

    public Vector2d yz() {
        return new Vector2d(y, z);
    }

    public Vector2d zw() {
        return new Vector2d(z, w);
    }

    public Vector2d wx() {
        return new Vector2d(w, x);
    }

    public Vector2d xw() {
        return new Vector2d(x, w);
    }

    public Vector2d wz() {
        return new Vector2d(w, z);
    }

    public Vector2d zy() {
        return new Vector2d(z, y);
    }

    public Vector2d yx() {
        return new Vector2d(y, x);
    }

    public Quaterniond setX(double x) {
        this.x = x;
        return this;
    }

    public Quaterniond setY(double y) {
        this.y = y;
        return this;
    }

    public Quaterniond setZ(double z) {
        this.z = z;
        return this;
    }

    public Quaterniond setW(double w) {
        this.w = w;
        return this;
    }

    public Quaternionf toQuaternionf() {
        return new Quaternionf((float) x, (float) y, (float) z, (float) w);
    }

    public Quaterniond copy() {
        return new Quaterniond(x, y, z, w);
    }

    @Override
    public Quaterniond clone() throws CloneNotSupportedException {
        super.clone();
        return new Quaterniond(x, y, z, w);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quaterniond that = (Quaterniond) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0 &&
                Double.compare(that.z, z) == 0 &&
                Double.compare(that.w, w) == 0;
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
