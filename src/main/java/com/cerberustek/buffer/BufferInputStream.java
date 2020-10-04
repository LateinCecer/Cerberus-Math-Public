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

import java.io.IOException;
import java.io.InputStream;

public class BufferInputStream extends InputStream {

    protected final Buffer buffer;

    public BufferInputStream(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public int read() throws IOException {
        return buffer.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return buffer.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return buffer.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return buffer.skip((int) n);
    }

    @Override
    public int available() throws IOException {
        return buffer.remaining();
    }
}
