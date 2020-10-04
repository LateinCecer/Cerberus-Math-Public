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

package com.cerberustek.buffer;

import java.nio.ByteBuffer;

@SuppressWarnings("Duplicates")
public class OverflowBuffer implements DataBuffer {

    private final ByteBuffer buf;
    private int writePointer = 0;
    private int readPointer = 0;

    private int marked = -1;

    private int remaining = 0;

    public OverflowBuffer(ByteBuffer buf) {
        this.buf = buf;
    }

    public OverflowBuffer(int cap) {
        this(ByteBuffer.allocate(cap));
    }

    public void readMode() {
        buf.position(readPointer);
    }

    public void writeMode() {
        buf.position(writePointer);
    }

    @Override
    public synchronized int read() {
        if (remaining() > 0) {
            readMode();
            int read = buf.get();
            shiftReadHead(1);
            return read;
        }
        return -1;
    }

    @Override
    public synchronized int readFully() throws InterruptedException {
        if (remaining() > 0) {
            readMode();
            int read = buf.get();
            shiftReadHead(1);
            return read;
        }

        synchronized (this) {
            this.wait();
        }
        return readFully();
    }

    @Override
    public synchronized int readFully(int halt) throws InterruptedException {
        if (remaining() > 0) {
            readMode();
            int read = buf.get();
            shiftReadHead(1);
            return read;
        }

        if (halt <= 0)
            return - 1;

        long timeStart = System.currentTimeMillis();
        synchronized (this) {
            this.wait(halt);
        }
        return readFully(halt - (int) (System.currentTimeMillis() - timeStart));
    }

    @Override
    public synchronized int readFully(byte[] data) throws InterruptedException {
        return readFully(data, 0, data.length);
    }

    @Override
    public synchronized int read(byte[] data) {
        return read(data, 0, data.length);
    }

    @Override
    public synchronized int read(byte[] data, int off, int len) {
        if (len <= 0)
            return 0;

        int read = len;
        if ((buf.capacity() - readPointer) < len)
            read = buf.capacity() - readPointer;
        if (read > remaining())
            read = remaining();
        readMode();
        buf.get(data, off, read);
        shiftReadHead(read);

        if (read == len)
            return read;

        if (remaining() > 0)
            return read + read(data, off + read, len - read);

        if (read == 0)
            return -1;
        return read;
    }

    @Override
    public synchronized int readFully(byte[] data, int off, int len) throws InterruptedException {
        // System.out.println("Getting " + len + " bytes!");
        if (len <= 0)
            return 0;

        int read = len;
        if ((buf.capacity() - readPointer) < len)
            read = buf.capacity() - readPointer;
        if (read > remaining())
            read = remaining();
        readMode();
        buf.get(data, off, read);
        shiftReadHead(read);

        if (read == len)
            return len;

        if (remaining() > 0)
            return read + read(data, off + read, len - read);

        // System.out.println("Waiting for data...");
        synchronized (this) {
            this.wait();
        }
        // System.out.println("Got data! " + remaining + "/" + len);
        return readFully(data, off + read, len - read);
    }

    @Override
    public synchronized int readFully(int halt, byte[] data) throws InterruptedException {
        return readFully(halt, data, 0, data.length);
    }

    @Override
    public synchronized int readFully(int halt, byte[] data, int off, int len) throws InterruptedException {
        if (len <= 0)
            return 0;

        int read = len;
        if ((buf.capacity() - readPointer) < len)
            read = buf.capacity() - readPointer;
        if (read > remaining())
            read = remaining();
        readMode();
        buf.get(data, off, read);
        shiftReadHead(read);

        if (read == len)
            return len;

        if (remaining() > 0)
            return read + read(data, off + read, len - read);

        if (halt <= 0)
            return -1;

        long timeStart = System.currentTimeMillis();
        synchronized (this) {
            this.wait(halt);
        }
        return readFully(halt - (int) (System.currentTimeMillis() - timeStart), data, off + read, len - read);
    }

    @Override
    public synchronized int skipFully(int amount) throws InterruptedException {
        int skipped = skip(amount);
        while (amount - skipped > 0) {
            synchronized (this) {
                this.wait();
            }

            skipped += skip(amount - skipped);
        }
        return skipped;
    }

    @Override
    public synchronized int skipFully(int halt, int amount) throws InterruptedException {
        int skipped = skip(amount);
        long timeStart = System.currentTimeMillis();
        while (amount - skipped > 0 && halt > System.currentTimeMillis() - timeStart) {

            long waiting = halt - (System.currentTimeMillis() - timeStart);
            if (waiting <= 0)
                return skipped;

            synchronized (this) {
                this.wait(waiting);
            }

            skipped += skip(amount - skipped);
        }
        return skipped;
    }

    @Override
    public synchronized void write(int data) {
        writeMode();
        buf.put((byte) (data & 0xFF));
        shiftWriteHead(1);

        synchronized (this) {
            this.notifyAll();
        }
    }

    @Override
    public synchronized void write(byte[] data) {
        write(data, 0, data.length);
    }

    @Override
    public synchronized void write(byte[] data, int off, int len) {
        if (len <= 0)
            return;

        int wrote = len;
        if ((buf.capacity() - writePointer) < len)
            wrote = buf.capacity() - writePointer;

        writeMode();
        buf.put(data, off, wrote);
        shiftWriteHead(wrote);

        synchronized (this) {
            this.notifyAll();
        }

        if (wrote != len)
            write(data, off + wrote, len - wrote);
    }

    @Override
    public int remaining() {
        return remaining;
    }

    @Override
    public synchronized int skip(int amount) {
        int left;
        if ((left = remaining()) >= amount) {
            shiftReadHead(amount);
            return amount;
        }
        shiftReadHead(left);
        return left;
    }

    @Override
    public int clear() {
        int remaining = remaining();
        this.remaining = 0;

        readPointer = writePointer = 0;
        return remaining;
    }

    @Override
    public int capacity() {
        return buf.capacity();
    }

    @Override
    public void mark() {
        marked = writePointer;
    }

    @Override
    public void reset() {
        if (marked < 0)
            throw new IllegalStateException("No position marked!");

        int skipping;
        if ((skipping = remaining(marked, writePointer)) > remaining) {
            readPointer = marked;
            remaining = 0;
        }

        remaining -= skipping;
        writePointer = marked;
    }

    @Override
    public boolean hasMark() {
        return marked >= 0;
    }

    /**
     * Will return how many bytes can be read from position
     * a to position b.
     * @param a start
     * @param b end
     * @return amount of bytes that can be read
     */
    private int remaining(int a, int b) {
        if (a < 0 || b < 0)
            return -1;

        if (b == a)
            return 0;

        if (b > a)
            return b - a;

        return buf.capacity() - a + b;
    }

    private synchronized void shiftReadHead(int amount) {
        if (amount == 0)
            return;

        remaining -= amount;
        readPointer = (amount + readPointer) % buf.capacity();
    }

    private synchronized void shiftWriteHead(int amount) {
        if (amount == 0)
            return;

        if (marked > 0 && remaining(marked, writePointer) + amount >= buf.capacity())
            marked = -1;

        remaining += amount;
        writePointer = (amount + writePointer) % buf.capacity();

        if (remaining >= buf.capacity()) {
            remaining = buf.capacity();
            readPointer = writePointer;
        }
    }
}
