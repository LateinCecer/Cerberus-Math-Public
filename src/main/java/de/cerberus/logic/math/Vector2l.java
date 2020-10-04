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
public class Vector2l {

    private long x;
    private long y;

    public Vector2l(long value) {
        this(value, value);
    }

    public Vector2l(Vector2l other) {
        this(other.getX(), other.getY());
    }

    public Vector2l(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long length() {
        return (long) Math.sqrt((x * x) + (y * y));
    }

    public long dot(Vector2l other) {
        return (x * other.x) + (y * other.y);
    }

    public Vector2l normalize() {
        long length = length();
        return new Vector2l(x / length, y / length);
    }

    public Vector2l rotate(double f) {
        double rad = Math.toRadians(f);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector2l((long) ((x * cos) - (y * sin)), (long) ((x * sin) + (y * cos)));
    }

    public Vector2l mod(long s) {
        return new Vector2l(x % s, y % s);
    }

    public Vector2l modSelf(long s) {
        x %= s;
        y %= s;
        return this;
    }

    public Vector2l mod(long xs, long ys) {
        return new Vector2l(x % xs, y % ys);
    }

    public Vector2l modSelf(long xs, long ys) {
        x %= xs;
        y %= ys;
        return this;
    }

    public Vector2l mod(Vector2l other) {
        return new Vector2l(x % other.x, y % other.x);
    }

    public Vector2l modSelf(Vector2l other) {
        x %= other.x;
        y %= other.y;
        return this;
    }

    public Vector2l mul(long s) {
        return new Vector2l(x * s, y * s);
    }

    public Vector2l mulSelf(long s) {
        x *= s;
        y *= s;
        return this;
    }

    public Vector2l mul(long xs, long ys) {
        return new Vector2l(x * xs, y * ys);
    }

    public Vector2l mulSelf(long xs, long ys) {
        x *= xs;
        y *= ys;
        return this;
    }

    public Vector2l mul(Vector2l other) {
        return new Vector2l(x * other.x, y * other.y);
    }

    public Vector2l mulSelf(Vector2l other) {
        x *= other.x;
        y *= other.y;
        return this;
    }

    public Vector2l add(long s) {
        return new Vector2l(this.x + s, this.y + s);
    }

    public Vector2l addSelf(long s) {
        x += s;
        y += s;
        return this;
    }

    public Vector2l add(long x, long y) {
        return new Vector2l(this.x + x, this.y + y);
    }

    public Vector2l addSelf(long xs, long ys) {
        x += xs;
        y += ys;
        return this;
    }

    public Vector2l add(Vector2l other) {
        return new Vector2l(this.x + other.x, this.y + other.y);
    }

    public Vector2l addSelf(Vector2l other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vector2l sub(long s) {
        return new Vector2l(this.x - s, this.x - s);
    }

    public Vector2l subSelf(long s) {
        x -= s;
        y -= s;
        return this;
    }

    public Vector2l sub(long xs, long ys) {
        return new Vector2l(this.x - xs, this.y - ys);
    }

    public Vector2l subSelf(long xs, long ys) {
        x -= xs;
        y -= ys;
        return this;
    }

    public Vector2l sub(Vector2l other) {
        return new Vector2l(this.x - other.x, this.y - other.y);
    }

    public Vector2l subSelf(Vector2l other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Vector2l div(long s) {
        return new Vector2l(this.x / s, this.y / s);
    }

    public Vector2l divSelf(long s) {
        x /= s;
        y /= s;
        return this;
    }

    public Vector2l div(long xs, long ys) {
        return new Vector2l(this.x / xs, this.y / ys);
    }

    public Vector2l divSelf(long xs, long ys) {
        x /= xs;
        y /= ys;
        return this;
    }

    public Vector2l div(Vector2l other) {
        return new Vector2l(this.x / other.x, this.y / other.y);
    }

    public Vector2l divSelf(Vector2l other) {
        x /= other.x;
        y /= other.y;
        return this;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public Vector2l set(long x, long y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2l setX(long x) {
        this.x = x;
        return this;
    }

    public Vector2l setY(long y) {
        this.y = y;
        return this;
    }

    public Vector2l abs() {
        return new Vector2l(Math.abs(x), Math.abs(y));
    }

    public Vector2d toVector2d() {
        return new Vector2d(x, y);
    }

    public Vector2i toVector2i() {
        return new Vector2i((int) x, (int) y);
    }

    public Vector2f toVector2f() {
        return new Vector2f((float) x, (float) y);
    }

    public Vector2d toVec2d() {
        return new Vector2d(x, y);
    }

    public Vector2i toVec2i() {
        return new Vector2i((int) x, (int) y);
    }

    public Vector2f toVec2f() {
        return new Vector2f((float) x, (float) y);
    }

    public Vector2l xy() {
        return new Vector2l(x, y);
    }

    public Vector2l yx() {
        return new Vector2l(y, x);
    }

    public Vector2l copy() {
        return new Vector2l(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2l vector2l = (Vector2l) o;
        return x == vector2l.x &&
                y == vector2l.y;
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
