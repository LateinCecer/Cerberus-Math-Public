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

public class Complexf {

    /** Real component of the complex number */
    private float re;
    /** Imaginary component of the complex number */
    private float im;

    /**
     * A complex number with only a real component (imaginary
     * component equal to 0.
     *
     * @param re real component
     */
    public Complexf(float re) {
        this(re, 0);
    }

    /**
     * A complex number.
     *
     * @param re real component
     * @param im imaginary component
     */
    public Complexf(float re, float im) {
        this.re = re;
        this.im = im;
    }

    /**
     * Returns the sum of this and some other complex number.
     *
     * @param other complex number to add
     * @return sum
     */
    public Complexf add(Complexf other) {
        return new Complexf(re + other.re, im + other.im);
    }

    /**
     * Returns the sum of this and a real number.
     *
     * @param s real number
     * @return sum
     */
    public Complexf add(float s) {
        return new Complexf(re + s, im);
    }

    public Complexf sub(Complexf other) {
        return new Complexf(re - other.re, im - other.im);
    }

    public Complexf sub(float s) {
        return new Complexf(re - s, im);
    }

    /**
     * Returns the product of this and an other complex number.
     *
     * @param other complex number to multiply with
     * @return product
     */
    public Complexf mul(Complexf other) {
        return new Complexf(re * other.re - im * other.im, re * other.im + other.re * im);
    }

    /**
     * Returns the product of this and a real number
     * @param s real number to multiply with
     * @return product
     */
    public Complexf mul(float s) {
        return new Complexf(re * s, im * s);
    }

    public Complexf div(Complexf other) {
        float s = other.re * other.re + other.im * other.im;
        return new Complexf((re * other.re + im * other.im) / s, (im * other.re - re * other.im) / s);
    }

    public Complexf div(float s) {
        return new Complexf(re / s, im / s);
    }

    public Complexf pow(int exp) {
        if (exp == 0)
            return new Complexf(1, 0);
        else if (exp > 0) {
            if (exp == 1)
                return new Complexf(re, im);
            else if (exp % 2 == 0) {
                Complexf z = pow(exp / 2);
                return z.mul(z);
            } else
                return mul(pow(exp - 1));
        } else
            return new Complexf(1, 0).div(pow(-exp));
    }

    /**
     * Returns the absolute value of this number (in a non-mathematical
     * sense)
     *
     * This method will return a new complex number where the real component
     * is the absolute value of the real component of this and the imaginary
     * component is the absolute of the imaginary component of this. If you
     * want to get the mathematical correct absolute of this number, use
     * 'length()'.
     *
     * @return absolute value
     */
    public Complexf abs() {
        return new Complexf(Math.abs(re), Math.abs(im));
    }

    /**
     * Returns the complex conjugate of this.
     *
     * @return complex conjugated
     */
    public Complexf conjugate() {
        return new Complexf(re, -im);
    }

    /**
     * Returns the length of this complex number in the gaussian number plane.
     *
     * As presented in the description of 'abs()' this method is the
     * mathematically correct absolute of this number.
     *
     * @return gaussian length/absolute value
     */
    public float length() {
        return (float) Math.sqrt(re * re + im * im);
    }

    public Complexd toComplexd() {
        return new Complexd(re, im);
    }

    public Complexf set(Complexf value) {
        re = value.re;
        im = value.im;
        return this;
    }

    public Complexf set(Complexd value) {
        re = (float) value.getRe();
        im = (float) value.getIm();
        return this;
    }

    public Complexf set(float re, float im) {
        this.re = re;
        this.im = im;
        return this;
    }

    public void setRe(float re) {
        this.re = re;
    }

    public void setIm(float im) {
        this.im = im;
    }

    public float getRe() {
        return re;
    }

    public float getIm() {
        return im;
    }

    public Complexf swapSelf() {
        float buf = im;
        im = re;
        re = buf;
        return this;
    }

    public Complexf swap() {
        return new Complexf(im, re);
    }

    public Complexf copy() {
        return new Complexf(re, im);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complexf complexf = (Complexf) o;
        return Float.compare(complexf.re, re) == 0 &&
                Float.compare(complexf.im, im) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }

    @Override
    public String toString() {
        return "(" + re + (im < 0 ? " - i" : " + i") + Math.abs(im) + ")";
    }
}
