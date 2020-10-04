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

package de.cerberus.pipeline.impl;

import de.cerberus.logic.math.*;
import de.cerberus.pipeline.Transformer;

public class SimpleTransformer implements Transformer {

    final private Vector3d translation;
    final private Vector3d scale;
    final private Quaterniond rotation;

    public SimpleTransformer(Vector3d translation, Vector3d scale, Quaterniond rotation) {
        this.translation = translation;
        this.scale = scale;
        this.rotation = rotation;
    }

    public SimpleTransformer() {
        this(new Vector3d(0, 0, 0),
                new Vector3d(1, 1, 1),
                new Quaterniond(0, 0, 0, 1));
    }

    @Override
    public void setTranslation(Vector3d translation) {
        this.translation.set(translation);
    }

    @Override
    public void setTranslation(Vector3f translation) {
        this.translation.set(translation.toVector3d());
    }

    @Override
    public Vector3d getTranslation() {
        return translation;
    }

    @Override
    public void setScale(Vector3d scale) {
        this.scale.set(scale);
    }

    @Override
    public void setScale(Vector3f scale) {
        this.scale.set(scale.toVector3d());
    }

    @Override
    public Vector3d getScale() {
        return scale;
    }

    @Override
    public Quaterniond getRotation() {
        return rotation;
    }

    @Override
    public Quaterniond rotate(double angle, Vector3d axis) {
        return rotate(new Quaterniond(angle, axis));
    }

    @Override
    public Quaterniond rotate(float angle, Vector3f axis) {
        return rotate(new Quaterniond(angle, axis.toVector3d()));
    }

    @Override
    public Quaterniond rotate(Quaterniond rotation) {
        return this.rotation.mulSelf(rotation).normalizeSelf();
    }

    @Deprecated
    @Override
    public Matrix4f toMatrix() {
        return toMatrix4f();
    }

    @Override
    public Matrix4f toMatrix4f() {
        Matrix4f translationMatrix = new Matrix4f().initTranslation(translation.toVector3f());
        Matrix4f scaleMatrix = new Matrix4f().initScale(scale.toVector3f());
        Matrix4f rotationMatrix = rotation.toQuaternionf().toRotationMatrix();
        return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
    }

    @Override
    public Matrix4d toMatrix4d() {
        Matrix4d translationMatrix = new Matrix4d().initTranslation(translation);
        Matrix4d scaleMatrix = new Matrix4d().initScale(scale);
        Matrix4d rotationMatrix = rotation.toRotationMatrix();
        return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
    }

    @Override
    public Matrix4f toInverseMatrix4f() {
        Matrix4f translationMatrix = new Matrix4f().initInverseTranslation(translation.toVector3f());
        Matrix4f scaleMatrix = new Matrix4f().initInverseScale(scale.toVector3f());
        Matrix4f rotationMatrix = rotation.toQuaternionf().toInverseRotationMatrix();
        return scaleMatrix.mul(rotationMatrix.mul(translationMatrix));
    }

    @Override
    public Matrix4d toInverseMatrix4d() {
        Matrix4d translationMatrix = new Matrix4d().initInverseTranslation(translation);
        Matrix4d scaleMatrix = new Matrix4d().initInverseScale(scale);
        Matrix4d rotationMatrix = rotation.toInverseRotationMatrix();
        return scaleMatrix.mul(rotationMatrix.mul(translationMatrix));
    }

    @Override
    public Matrix4f toProjectedMatrix(Matrix4f projectionMatrix) {
        return projectionMatrix.mul(toMatrix4f());
    }

    @Override
    public Matrix4d toProjectedMatrix(Matrix4d projectionMatrix) {
        return projectionMatrix.mul(toMatrix4d());
    }

    @Override
    public Matrix4f toInverseProjectedMatrix(Matrix4f projectionMatrix) {
        return projectionMatrix.mul(toInverseMatrix4f());
    }

    @Override
    public Matrix4d toInverseProjectedMatrix(Matrix4d projectionMatrix) {
        return projectionMatrix.mul(toInverseMatrix4d());
    }

    @Deprecated
    @Override
    public Matrix4f getTranslationMatrix() {
        return getTranslationMatrix4f();
    }

    @Override
    public Matrix4f getTranslationMatrix4f() {
        return new Matrix4f().initTranslation(translation.toVector3f());
    }

    @Override
    public Matrix4d getTranslationMatrix4d() {
        return new Matrix4d().initTranslation(translation);
    }

    @Override
    public Matrix4f getInverseTranslationMatrix4f() {
        return new Matrix4f().initInverseTranslation(translation.toVector3f());
    }

    @Override
    public Matrix4d getInverseTranslationMatrix4d() {
        return new Matrix4d().initInverseTranslation(translation);
    }

    @Deprecated
    @Override
    public Matrix4f getScaleMatrix() {
        return getScaleMatrix4f();
    }

    @Override
    public Matrix4f getScaleMatrix4f() {
        return new Matrix4f().initScale(scale.toVector3f());
    }

    @Override
    public Matrix4d getScaleMatrix4d() {
        return new Matrix4d().initScale(scale);
    }

    @Override
    public Matrix4f getInverseScaleMatrix4f() {
        return new Matrix4f().initInverseScale(scale.toVector3f());
    }

    @Override
    public Matrix4d getInverseScaleMatrix4d() {
        return new Matrix4d().initInverseScale(scale);
    }

    @Deprecated
    @Override
    public Matrix4f getRotationMatrix() {
        return getRotationMatrix4f();
    }

    @Override
    public Matrix4f getRotationMatrix4f() {
        return rotation.toQuaternionf().toRotationMatrix();
    }

    @Override
    public Matrix4d getRotationMatrix4d() {
        return rotation.toRotationMatrix();
    }

    @Override
    public Matrix4f getInverseRotationMatrix4f() {
        return rotation.toQuaternionf().invertSelf().toRotationMatrix();
    }

    @Override
    public Matrix4d getInverseRotationMatrix4d() {
        return rotation.inverse().toRotationMatrix();
    }

    @Override
    public Transformer copy() {
        return new SimpleTransformer(translation.copy(), scale.copy(), rotation.copy());
    }
}
