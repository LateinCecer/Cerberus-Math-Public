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

public class Line3f {

    /** Anchor/Coordinate of this line. This is the vector from which this
     * line is held in space */
    private Vector3f coord;
    /** Projection/Direction in which this line is facing */
    private Vector3f projection;

    /**
     * Constructs a line facing in the direction of the projection parameter
     * and fixed in 3D space by the coord parameter.
     *
     * @param coord anchor/coordinate
     * @param projection projection/direction vector
     */
    public Line3f(Vector3f coord, Vector3f projection) {
        this.coord = coord;
        this.projection = projection.normalized();
    }

    /**
     * This method will calculate the point of interception between
     * two lines.
     *
     * If this line does, in fact, not intersect the other, specified
     * line, this method will return null.
     * If this line is parallel to the other line, this method will
     * return:
     * 1. NULL, in case the two lines have different coordinates
     * 2. The (common) origin coordinate of this (and the other)
     *    line.
     *
     * @param other an other line
     * @return point of interception
     */
    public Vector3f interception(Line3f other) {
        if (projection.dot(other.projection) != 1) {

            Vector3f relative = other.coord.sub(coord);
            Vector3f normal = other.projection.cross(projection).cross(projection);
            Vector3f point = other.projection.mul(-relative.dot(normal) / other.projection.dot(normal)).add(relative);

            if (elementRelative(point))
                return point;

        } else {
            if (coord.equals(other.coord))
                return coord;
        }
        return null;
    }

    /**
     * This method will check if the specified coordinate (relative to the
     * coordinate origin) is an element of the line.
     *
     * @param element coordinate in space
     * @return element or not
     */
    public boolean element(Vector3f element) {
        return elementRelative(element.sub(this.coord));
    }

    /**
     * This method will check if the specified coordinate (relative to the
     * origin coordinate of this line) is an element of the line.
     *
     * This will return true in case the given vector relative to the anchor
     * of this line lays within this line. Use this method over "element" if
     * you already have the vector of the point in relation to this lines
     * origin.
     *
     * @param element coordinate relative to this
     * @return element or not
     */
    public boolean elementRelative(Vector3f element) {
        float length = element.length();
        return length == 0 || Math.abs(element.dot(projection)) == length;
    }

    /**
     * Returns the origin coordinate of this line
     *
     * @return coordinate of line
     */
    public Vector3f getCoord() {
        return coord;
    }

    /**
     * Sets the coordinate of this line
     *
     * @param coord origin coord
     */
    public void setCoord(Vector3f coord) {
        this.coord = coord;
    }

    /**
     * Returns the vector which represents the direction this line is
     * facing in.
     *
     * This function will always return a normed vector.
     *
     * @return projection vector
     */
    public Vector3f getProjection() {
        return projection;
    }

    /**
     * Sets the vector that represents the direction in which this line is
     * pointing. <STRONG>!!The projection vector has to be normed!!</STRONG>
     *
     * @param projection <STRONG>normed</STRONG> direction vector
     */
    public void setProjection(Vector3f projection) {
        this.projection = projection;
    }
}
