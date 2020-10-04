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

/**
 * Created by LateinCecker on 02.03.2016.
 */
public class Vector2d {

    private double x;
    private double y;

    public Vector2d(Vector2d other) {
        this(other.getX(), other.getY());
    }

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(double value) {
        this.x = value;
        this.y = value;
    }

    public double length() {
        return Math.sqrt((x * x) + (y * y));
    }

    public double dot(Vector2d other) {
        return (x * other.x) + (y * other.y);
    }

    public Vector2d normalize() {
        double length = length();
        return new Vector2d(x / length, y / length);
    }

    public Vector2d rotate(double f) {
        double rad = Math.toRadians(f);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector2d(((x * cos) - (y * sin)), ((x * sin) + (y * cos)));
    }

    public Vector2d mod(double s) {
        return new Vector2d(x % s, y % s);
    }

    public Vector2d modSelf(double s) {
        x %= s;
        y %= s;
        return this;
    }

    public Vector2d mod(double xs, double ys) {
        return new Vector2d(x % xs, y % ys);
    }

    public Vector2d modSelf(double xs, double ys) {
        x %= xs;
        y %= ys;
        return this;
    }

    public Vector2d mod(Vector2d other) {
        return new Vector2d(x % other.x, y % other.y);
    }

    public Vector2d modSelf(Vector2d other) {
        x %= other.x;
        y %= other.y;
        return this;
    }

    public Vector2d mul(double s) {
        return new Vector2d(x * s, y * s);
    }

    public Vector2d mulSelf(double s) {
        x *= s;
        y *= s;
        return this;
    }

    public Vector2d mul(double xs, double ys) {
        return new Vector2d(x * xs, y * ys);
    }

    public Vector2d mulSelf(double xs, double ys) {
        x *= xs;
        y *= ys;
        return this;
    }

    public Vector2d mul(Vector2d other) {
        return new Vector2d(x * other.x, y * other.y);
    }

    public Vector2d mulSelf(Vector2d other) {
        x *= other.x;
        y *= other.y;
        return this;
    }

    public Vector2d add(double s) {
        return new Vector2d(this.x + s, this.y + s);
    }

    public Vector2d addSelf(double s) {
        x += s;
        y += s;
        return this;
    }

    public Vector2d add(double x, double y) {
        return new Vector2d(this.x + x, this.y + y);
    }

    public Vector2d addSelf(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d addSelf(Vector2d other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2d sub(double s) {
        return new Vector2d(this.x - s, this.x - s);
    }

    public Vector2d subSelf(double s) {
        x -= s;
        y -= s;
        return this;
    }

    public Vector2d sub(double xs, double ys) {
        return new Vector2d(this.x - xs, this.y - ys);
    }

    public Vector2d subSelf(double xs, double ys) {
        x -= xs;
        y -= ys;
        return this;
    }

    public Vector2d sub(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d subSelf(Vector2d other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Vector2d div(double s) {
        return new Vector2d(this.x / s, this.y / s);
    }

    public Vector2d divSelf(double s) {
        x /= s;
        y /= s;
        return this;
    }

    public Vector2d div(double xs, double ys) {
        return new Vector2d(this.x / xs, this.y / ys);
    }

    public Vector2d divSelf(double xs, double ys) {
        x /= xs;
        y /= ys;
        return this;
    }

    public Vector2d div(Vector2d other) {
        return new Vector2d(this.x / other.x, this.y / other.y);
    }

    public Vector2d divSelf(Vector2d other) {
        x /= other.x;
        y /= other.y;
        return this;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double angel(Vector2d other) {
        return Math.acos(normalize().dot(other.normalize()));
    }

    public Vector2l round() {
        return new Vector2l(Math.round(x), Math.round(y));
    }

    public Vector2d set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2d setX(double x) {
        this.x = x;
        return this;
    }

    public Vector2d setY(double y) {
        this.y = y;
        return this;
    }

    public Vector2f toVector2f() {
        return new Vector2f((float) x, (float) y);
    }

    public Vector2l toVector2l() {
        return new Vector2l((long) x, (long) y);
    }

    public Vector2i toVector2i() {
        return new Vector2i((int) x, (int) y);
    }

    public Vector2f toVec2f() {
        return new Vector2f((float) x, (float) y);
    }

    public Vector2l toVec2l() {
        return new Vector2l((long) x, (long) y);
    }

    public Vector2i toVec2i() {
        return new Vector2i((int) x, (int) y);
    }

    public Vector2d xy() {
        return new Vector2d(x, y);
    }

    public Vector2d yx() {
        return new Vector2d(y, x);
    }

    public Vector2d copy() {
        return new Vector2d(x, y);
    }

    public Vector2d abs() {
        return new Vector2d(Math.abs(x), Math.abs(y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return Double.compare(vector2d.x, x) == 0 &&
                Double.compare(vector2d.y, y) == 0;
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
