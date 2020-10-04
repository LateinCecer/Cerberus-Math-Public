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

public interface Heap<T extends Comparable<T>> {

    /**
     * Will refill the heap with a new set of data.
     *
     * All old data will be lost
     * @param a new data
     */
    void recreate(T[] a, int off, int len);

    /**
     * Inserts an element into the array
     * @param value element to insert
     */
    void insert(T value);

    /**
     * deletes the bottom element.
     *
     * This method will NOT delete the entire heap. It will
     * only delete the very bottom element. For a min heap,
     * for example, this method will delete the minimal
     * element.
     */
    void delete();

    /**
     * Will construct the heap condition for a specific
     * position inside of the heap.
     * @param index index
     */
    void constructHeap(int index);
}
