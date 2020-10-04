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
public class Vector2i {

    private int x;
    private int y;

    public Vector2i(Vector2i other) {
        this(other.getX(), other.getY());
    }

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i(int value) {
        this.x = value;
        this.y = value;
    }

    public int length() {
        return (int) Math.sqrt((x * x) + (y * y));
    }

    public int dot(Vector2i other) {
        return (x * other.x) + (y * other.y);
    }

    public Vector2i normalize() {
        int length = length();
        return new Vector2i(x / length, y / length);
    }

    public Vector2i rotate(double f) {
        double rad = Math.toRadians(f);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector2i((int) ((x * cos) - (y * sin)), (int) ((x * sin) + (y * cos)));
    }

    public Vector2i mod(int s) {
        return new Vector2i(x % s, y % s);
    }

    public Vector2i modSelf(int s) {
        x %= s;
        y %= s;
        return this;
    }

    public Vector2i mod(int xs, int ys) {
        return new Vector2i(x % xs, y % ys);
    }

    public Vector2i modSelf(int xs, int ys) {
        x %= xs;
        y %= ys;
        return this;
    }

    public Vector2i mod(Vector2i other) {
        return new Vector2i(x % other.x, y % other.y);
    }

    public Vector2i modSelf(Vector2i other) {
        x %= other.x;
        y %= other.y;
        return this;
    }

    public Vector2i mul(int s) {
        return new Vector2i(x * s, y * s);
    }

    public Vector2i mulSelf(int s) {
        x *= s;
        y *= s;
        return this;
    }

    public Vector2i mul(int xs, int ys) {
        return new Vector2i(x * xs, y * ys);
    }

    public Vector2i mulSelf(int xs, int ys) {
        x *= xs;
        y *= ys;
        return this;
    }

    public Vector2i mul(Vector2i other) {
        return new Vector2i(x * other.x, y * other.y);
    }

    public Vector2i mulSelf(Vector2i other) {
        x *= other.x;
        y *= other.y;
        return this;
    }

    public Vector2i add(int s) {
        return new Vector2i(this.x + s, this.y + s);
    }

    public Vector2i addSelf(int s) {
        x += s;
        y += s;
        return this;
    }

    public Vector2i add(int x, int y) {
        return new Vector2i(this.x + x, this.y + y);
    }

    public Vector2i addSelf(int xs, int ys) {
        x += xs;
        y += ys;
        return this;
    }

    public Vector2i add(Vector2i other) {
        return new Vector2i(this.x + other.x, this.y + other.y);
    }

    public Vector2i addSelf(Vector2i other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vector2i sub(int s) {
        return new Vector2i(this.x - s, this.x - s);
    }

    public Vector2i subSelf(int s) {
        x -= s;
        y -= s;
        return this ;
    }

    public Vector2i sub(int xs, int ys) {
        return new Vector2i(this.x - xs, this.y - ys);
    }

    public Vector2i subSelf(int xs, int ys) {
        x -= xs;
        y -= ys;
        return this;
    }

    public Vector2i sub(Vector2i other) {
        return new Vector2i(this.x - other.x, this.y - other.y);
    }

    public Vector2i subSelf(Vector2i other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Vector2i div(int s) {
        return new Vector2i(this.x / s, this.y / s);
    }

    public Vector2i divSelf(int s) {
        x /= s;
        y /= s;
        return this;
    }

    public Vector2i div(int xs, int ys) {
        return new Vector2i(this.x / xs, this.y / ys);
    }

    public Vector2i divSelf(int xs, int ys) {
        x /= xs;
        y /= ys;
        return this;
    }

    public Vector2i div(Vector2i other) {
        return new Vector2i(this.x / other.x, this.y / other.y);
    }

    public Vector2i divSelf(Vector2i other) {
        x /= other.x;
        y /= other.y;
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vector2i set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2i setX(int x) {
        this.x = x;
        return this;
    }

    public Vector2i setY(int y) {
        this.y = y;
        return this;
    }

    public Vector2i abs() {
        return new Vector2i(Math.abs(x), Math.abs(y));
    }

    public Vector2d toVector2d() {
        return new Vector2d(x, y);
    }

    public Vector2f toVector2f() {
        return new Vector2f(x, y);
    }

    public Vector2l toVector2l() {
        return new Vector2l(x, y);
    }

    public Vector2d toVec2d() {
        return new Vector2d(x, y);
    }

    public Vector2f toVec2f() {
        return new Vector2f(x, y);
    }

    public Vector2l toVec2l() {
        return new Vector2l(x, y);
    }

    public Vector2i xy() {
        return new Vector2i(x, y);
    }

    public Vector2i yx() {
        return new Vector2i(y, x);
    }

    public Vector2i copy() {
        return new Vector2i(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2i vector2i = (Vector2i) o;
        return x == vector2i.x &&
                y == vector2i.y;
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
