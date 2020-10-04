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
public class Vector2f {

    private float x;
    private float y;

    public Vector2f(Vector2f other) {
        this(other.getX(), other.getY());
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(float value) {
        this.x = value;
        this.y = value;
    }

    public float length() {
        return (float) Math.sqrt((x * x) + (y * y));
    }

    public float dot(Vector2f other) {
        return (x * other.x) + (y * other.y);
    }

    public Vector2f normalize() {
        float length = length();
        return new Vector2f(x / length, y / length);
    }

    public Vector2f rotate(float f) {
        double rad = Math.toRadians(f);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector2f((float) ((x * cos) - (y * sin)), (float) ((x * sin) + (y * cos)));
    }

    public Vector2f mod(float s) {
        return new Vector2f(x % s, y % s);
    }

    public Vector2f modSelf(float s) {
        x %= s;
        y %= s;
        return this;
    }

    public Vector2f mod(float xs, float ys) {
        return new Vector2f(x % xs, y % ys);
    }

    public Vector2f modSelf(float xs, float ys) {
        x %= xs;
        y %= ys;
        return this;
    }

    public Vector2f mod(Vector2f other) {
        return new Vector2f(x % other.x, y % other.y);
    }

    public Vector2f modSelf(Vector2f other) {
        x %= other.x;
        y %= other.y;
        return this;
    }

    public Vector2f mul(float s) {
        return new Vector2f(x * s, y * s);
    }

    public Vector2f mulSelf(float s) {
        x *= s;
        y *= s;
        return this;
    }

    public Vector2f mul(float xs, float ys) {
        return new Vector2f(x * xs, y * ys);
    }

    public Vector2f mulSelf(float xs, float ys) {
        x *= xs;
        y *= ys;
        return this;
    }

    public Vector2f mul(Vector2f other) {
        return new Vector2f(x * other.x, y * other.y);
    }

    public Vector2f mulSelf(Vector2f other) {
        x *= other.x;
        y *= other.y;
        return this;
    }

    public Vector2f add(float s) {
        return new Vector2f(this.x + s, this.y + s);
    }

    public Vector2f addSelf(float s) {
        x += s;
        y += s;
        return this;
    }

    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

    public Vector2f addSelf(float xs, float ys) {
        x += xs;
        y += ys;
        return this;
    }

    public Vector2f add(Vector2f other) {
        return new Vector2f(this.x + other.x, this.y + other.y);
    }

    public Vector2f addSelf(Vector2f other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vector2f sub(float s) {
        return new Vector2f(this.x - s, this.x - s);
    }

    public Vector2f subSelf(float s) {
        x -= s;
        y -= s;
        return this;
    }

    public Vector2f sub(float xs, float ys) {
        return new Vector2f(this.x - xs, this.y - ys);
    }

    public Vector2f subSelf(float xs, float ys) {
        x -= xs;
        y -= ys;
        return this;
    }

    public Vector2f sub(Vector2f other) {
        return new Vector2f(this.x - other.x, this.y - other.y);
    }

    public Vector2f subSelf(Vector2f other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Vector2f div(float s) {
        return new Vector2f(this.x / s, this.y / s);
    }

    public Vector2f divSelf(float s) {
        x /= s;
        y /= s;
        return this;
    }

    public Vector2f div(float xs, float ys) {
        return new Vector2f(this.x / xs, this.y / ys);
    }

    public Vector2f divSelf(float xs, float ys) {
        x /= xs;
        y /= ys;
        return this;
    }

    public Vector2f div(Vector2f other) {
        return new Vector2f(this.x / other.x, this.y / other.y);
    }

    public Vector2f divSelf(Vector2f other) {
        x /= other.x;
        y /= other.y;
        return this;
    }

    public Vector2i round() {
        return new Vector2i(Math.round(x), Math.round(y));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public double angel(Vector2f other) {
        return Math.acos(normalize().dot(other.normalize()));
    }

    public Vector2f set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2f setX(float x) {
        this.x = x;
        return this;
    }

    public Vector2f setY(float y) {
        this.y = y;
        return this;
    }

    public Vector2f abs() {
        return new Vector2f(Math.abs(x), Math.abs(y));
    }

    public Vector2d toVector2d() {
        return new Vector2d(x, y);
    }

    public Vector2i toVector2i() {
        return new Vector2i((int) x, (int) y);
    }

    public Vector2l toVector2l() {
        return new Vector2l((long) x, (long) y);
    }

    public Vector2d toVec2d() {
        return new Vector2d(x, y);
    }

    public Vector2i toVec2i() {
        return new Vector2i((int) x, (int) y);
    }

    public Vector2l toVec2l() {
        return new Vector2l((long) x, (long) y);
    }

    public Vector2f xy() {
        return new Vector2f(x, y);
    }

    public Vector2f yx() {
        return new Vector2f(y, x);
    }

    public Vector2f copy() {
        return new Vector2f(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2f vector2f = (Vector2f) o;
        return Float.compare(vector2f.x, x) == 0 &&
                Float.compare(vector2f.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }
}
