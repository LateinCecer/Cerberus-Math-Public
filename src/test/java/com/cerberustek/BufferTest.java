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

package com.cerberustek;

import com.cerberustek.buffer.DataBuffer;
import com.cerberustek.buffer.OverflowBuffer;

import java.io.IOException;
import java.util.Arrays;

public class BufferTest {

    public static void main(String... args) throws IOException, InterruptedException {
        DataBuffer buffer = new OverflowBuffer(5);
        buffer.write(new byte[] {1, 2});

        System.out.println("Has mark: " + buffer.hasMark() + " remaining: " + buffer.remaining());
        buffer.mark();
        System.out.println("Has mark: " + buffer.hasMark() + " remaining: " + buffer.remaining());

        buffer.write(new byte[] {3, 4});
        System.out.println("Has mark: " + buffer.hasMark() + " remaining: " + buffer.remaining());
        buffer.write(new byte[] {5, 1});
        System.out.println("Has mark: " + buffer.hasMark() + " remaining: " + buffer.remaining());

        buffer.reset();
        System.out.println("remaining: " + buffer.remaining());
        buffer.write(new byte[] {2, 3});
        System.out.println("Has mark: " + buffer.hasMark());

        byte[] buf = new byte[buffer.remaining()];
        buf[0] = (byte) buffer.readFully();
        buf[1] = (byte) buffer.readFully();
        buf[2] = (byte) buffer.readFully();
        System.out.println(Arrays.toString(buf));
        System.out.println("Done!");


        /*CerberusRegistry registry = CerberusRegistry.getInstance();

        String in = "Hello World";
        for (int i = 14; i < 100; i++) {
            registry.debugInLine("Buffersize: " + i + " bytes!");

            DataBuffer buffer = new OverflowBuffer(ByteBuffer.allocateDirect(i));

            DataBufferInputStream inputStream = new DataBufferInputStream(buffer);
            DataOutputStream outputStream = new DataOutputStream(new BufferOutputStream(buffer));

            for (int j = 0; j < 100; j++) {
                outputStream.writeUTF(in);
                Thread.sleep(1);
                String read = inputStream.readUTF();
                // registry.debug("read: " + read);
                if (!read.equals(in))
                    registry.warning("Failed test with buffer size " + i + "!");
            }

            inputStream.close();
            outputStream.close();
        }

        System.out.print('\n');
        registry.debug("End test...!");
        registry.requestStop();*/
    }
}
