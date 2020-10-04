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

package com.cerberustek.pipeline;

import com.cerberustek.logic.math.*;

public interface Transformer {

    void setTranslation(Vector3d translation);
    void setTranslation(Vector3f translation);
    Vector3d getTranslation();

    void setScale(Vector3d scale);
    void setScale(Vector3f scale);
    Vector3d getScale();

    Quaterniond getRotation();

    Quaterniond rotate(double angle, Vector3d axis);
    Quaterniond rotate(float angle, Vector3f axis);
    Quaterniond rotate(Quaterniond rotation);

    @Deprecated
    Matrix4f toMatrix();
    Matrix4f toMatrix4f();
    Matrix4d toMatrix4d();

    Matrix4f toInverseMatrix4f();
    Matrix4d toInverseMatrix4d();

    Matrix4f toProjectedMatrix(Matrix4f projectionMatrix);
    Matrix4d toProjectedMatrix(Matrix4d projectionMatrix);

    Matrix4f toInverseProjectedMatrix(Matrix4f projectionMatrix);
    Matrix4d toInverseProjectedMatrix(Matrix4d projectionMatrix);

    @Deprecated
    Matrix4f getTranslationMatrix();
    Matrix4f getTranslationMatrix4f();
    Matrix4d getTranslationMatrix4d();

    Matrix4f getInverseTranslationMatrix4f();
    Matrix4d getInverseTranslationMatrix4d();

    @Deprecated
    Matrix4f getScaleMatrix();
    Matrix4f getScaleMatrix4f();
    Matrix4d getScaleMatrix4d();

    Matrix4f getInverseScaleMatrix4f();
    Matrix4d getInverseScaleMatrix4d();

    @Deprecated
    Matrix4f getRotationMatrix();
    Matrix4f getRotationMatrix4f();
    Matrix4d getRotationMatrix4d();

    Matrix4f getInverseRotationMatrix4f();
    Matrix4d getInverseRotationMatrix4d();

    Transformer copy();
}
