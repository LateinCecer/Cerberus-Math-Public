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

package de.cerberus.buffer;

public interface DataBuffer extends Buffer {

    int readFully() throws InterruptedException;
    int readFully(int halt) throws InterruptedException;

    int readFully(byte[] data) throws InterruptedException;
    int readFully(byte[] data, int off, int len) throws InterruptedException;
    int readFully(int halt, byte[] data) throws InterruptedException;
    int readFully(int halt, byte[] data, int off, int len) throws InterruptedException;

    int skipFully(int amount) throws InterruptedException;
    int skipFully(int halt, int amount) throws InterruptedException;
}
