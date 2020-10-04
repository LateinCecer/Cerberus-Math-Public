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

public class MaxHeap<T extends Comparable<T>> implements Heap<T> {

    // children: 2 * i + 1, 2 * i + 2
    // father: (i - 1) / 2 when w != 0
    private final T[] data;
    private int size;

    public MaxHeap(int cap) {
        //noinspection unchecked
        data = (T[]) new Comparable[cap];
        size = 0;
    }

    @Override
    public final void recreate(T[] a, int off, int len) {
        if (a == null || a.length == 0)
            throw new IllegalArgumentException("Invalid input!");
        else {
            size = a.length;
            System.arraycopy(a, 0, data, 0, size);

            for (int i = size / 2 - 1; i >= 0; i--)
                constructHeap(i);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void constructHeap(int index) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int less = index;

        if (left < size && data[left].compareTo(data[less]) > 0)
            less = left;

        if (right < size && data[right].compareTo(data[less]) > 0)
            less = right;

        if (less != index) {
            T buf = data[index];
            data[index] = data[less];
            data[less] = buf;

            constructHeap(less);
        }
    }

    @Override
    public void insert(T value) {
        if (value == null)
            throw new NullPointerException("Cannot insert null value into heap!");

        int current = size;

        if (size == data.length)
            throw new IllegalStateException("Heap is already at maximum capacity!");

        size++;
        int father;
        while (current != 0 && data[father = (current - 1) / 2].compareTo(value) < 0) {
            data[current] = data[father];
            current = father;
        }

        data[current] = value;
    }

    @Override
    public void delete() {
        if (size > 0) {
            data[0] = data[size - 1];
            size--;
            constructHeap(0);
        }
    }
}
