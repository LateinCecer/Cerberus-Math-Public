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

public class Complexd {

    /** Real component of the complex number */
    private double re;
    /** Imaginary component of the complex number */
    private double im;

    /**
     * A complex number with only a real component (imaginary
     * component equal to 0.
     *
     * @param re real component
     */
    public Complexd(double re) {
        this(re, 0);
    }

    /**
     * A complex number.
     *
     * @param re real component
     * @param im imaginary component
     */
    public Complexd(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /**
     * Returns the sum of this and some other complex number.
     *
     * @param other complex number to add
     * @return sum
     */
    public Complexd add(Complexd other) {
        return new Complexd(re + other.re, im + other.im);
    }

    /**
     * Returns the sum of this and a real number.
     *
     * @param s real number
     * @return sum
     */
    public Complexd add(double s) {
        return new Complexd(re + s, im);
    }

    public Complexd sub(Complexd other) {
        return new Complexd(re - other.re, im - other.im);
    }

    public Complexd sub(double s) {
        return new Complexd(re - s, im);
    }

    /**
     * Returns the product of this and an other complex number.
     *
     * @param other complex number to multiply with
     * @return product
     */
    public Complexd mul(Complexd other) {
        return new Complexd(re * other.re - im * other.im, re * other.im + other.re * im);
    }

    /**
     * Returns the product of this and a real number
     * @param s real number to multiply with
     * @return product
     */
    public Complexd mul(double s) {
        return new Complexd(re * s, im * s);
    }

    public Complexd div(Complexd other) {
        double s = other.re * other.re + other.im * other.im;
        return new Complexd((re * other.re + im * other.im) / s, (im * other.re - re * other.im) / s);
    }

    public Complexd div(double s) {
        return new Complexd(re / s, im / s);
    }

    public Complexd pow(int exp) {
        if (exp == 0)
            return new Complexd(1, 0);
        else if (exp > 0) {
            if (exp == 1)
                return new Complexd(re, im);
            else if (exp % 2 == 0) {
                Complexd z = pow(exp / 2);
                return z.mul(z);
            } else {
                return mul(pow(exp - 1));
            }
        } else {
            return new Complexd(1, 0).div(pow(-exp));
        }
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
    public Complexd abs() {
        return new Complexd(Math.abs(re), Math.abs(im));
    }

    /**
     * Returns the complex conjugate of this.
     *
     * @return complex conjugated
     */
    public Complexd conjugate() {
        return new Complexd(re, -im);
    }

    /**
     * Returns the length of this complex number in the gaussian number plane.
     *
     * As presented in the description of 'abs()' this method is the
     * mathematically correct absolute of this number.
     *
     * @return gaussian length/absolute value
     */
    public double length() {
        return Math.sqrt(re * re + im * im);
    }

    public Complexf toComplexf() {
        return new Complexf((float) re, (float) im);
    }

    public Complexd set(Complexd value) {
        re = value.re;
        im = value.im;
        return this;
    }

    public Complexd set(Complexf value) {
        re = value.getRe();
        im = value.getIm();
        return this;
    }

    public Complexd set(double re, double im) {
        this.re = re;
        this.im = im;
        return this;
    }

    public void setRe(double re) {
        this.re = re;
    }

    public void setIm(double im) {
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public Complexd swapSelf() {
        double buf = im;
        im = re;
        re = buf;
        return this;
    }

    public Complexd swap() {
        return new Complexd(im, re);
    }

    public Complexd copy() {
        return new Complexd(re, im);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complexd complexd = (Complexd) o;
        return Double.compare(complexd.re, re) == 0 &&
                Double.compare(complexd.im, im) == 0;
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
