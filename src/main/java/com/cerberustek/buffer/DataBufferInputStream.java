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

import java.io.DataInput;
import java.io.IOException;

public class DataBufferInputStream extends BufferInputStream implements DataInput {

    public DataBufferInputStream(DataBuffer buffer) {
        super(buffer);
    }

    @Override
    public void readFully(byte[] b) throws IOException {
        try {
            ((DataBuffer) buffer).readFully(b);
        } catch (InterruptedException e) {
            throw new IOException();
        }
    }

    @Override
    public void readFully(byte[] b, int off, int len) throws IOException {
        try {
            ((DataBuffer) buffer).readFully(b, off, len);
        } catch (InterruptedException e) {
            throw new IOException();
        }
    }

    @Override
    public int skipBytes(int n) throws IOException {
        try {
            return ((DataBuffer) buffer).skipFully(n);
        } catch (InterruptedException e) {
            throw new IOException();
        }
    }

    @Override
    public boolean readBoolean() throws IOException {
        try {
            return ((DataBuffer) buffer).readFully() != 0;
        } catch (InterruptedException e) {
            throw new IOException();
        }
    }

    @Override
    public byte readByte() throws IOException {
        try {
            return (byte) (((DataBuffer) buffer).readFully() & 0xFF);
        } catch (InterruptedException e) {
            throw new IOException();
        }
    }

    @Override
    public int readUnsignedByte() throws IOException {
        return readByte();
    }

    @Override
    public short readShort() throws IOException {
        return (short) (((readByte() & 0xFF) << 8)
                + (readByte() & 0xFF));
    }

    @Override
    public int readUnsignedShort() throws IOException {
        return ((readByte() & 0xFF) << 8)
                + (readByte() & 0xFF);
    }

    @Override
    public char readChar() throws IOException {
        return (char) readShort();
    }

    @Override
    public int readInt() throws IOException {
        return ((readByte() & 0xFF) << 24)
                + ((readByte() & 0xFF) << 16)
                + ((readByte() & 0xFF) << 8)
                + (readByte() & 0xFF);
    }

    private final byte[] readBuffer = new byte[8];
    @Override
    public long readLong() throws IOException {
        readFully(readBuffer);
        return ((long) (readByte() & 0xFF) << 56)
                + ((long) (readByte() & 0xFF) << 48)
                + ((long) (readByte() & 0xFF) << 40)
                + ((long) (readByte() & 0xFF) << 32)
                + ((readByte() & 0xFF) << 24)
                + ((readByte() & 0xFF) << 16)
                + ((readByte() & 0xFF) << 8)
                + (readByte() & 0xFF);
    }

    @Override
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    @Override
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    @Deprecated
    @Override
    public String readLine() throws IOException {
        return null;
    }

    private final byte[] lineBuffer = new byte[4096];
    @Override
    public String readUTF() throws IOException {
        int length = readUnsignedShort();
        int read;

        StringBuilder stringBuilder = new StringBuilder();
        while (length > 0) {
            if (length < lineBuffer.length)
                read = length;
            else
                read = lineBuffer.length;
            readFully(lineBuffer, 0, length);

            stringBuilder.append(new String(lineBuffer, 0, read));
            length -= lineBuffer.length;
        }
        return stringBuilder.toString();
    }
}
