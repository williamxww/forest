package com.bow.forest.common.mqlite;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.CRC32;

/**
 * @author vv
 * @since 2017/5/20.
 */
public class Utils {

    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * calculate the CRC-32 checksum with the specified array of bytes.
     * 
     * @param bytes the array of bytes to calculate the checksum with
     * @param offset
     * @param size
     * @return checksum
     */
    public static long crc32(byte[] bytes, int offset, int size) {
        CRC32 crc = new CRC32();
        crc.update(bytes, offset, size);
        return crc.getValue();
    }

    public static long crc32(byte[] bytes) {
        return crc32(bytes, 0, bytes.length);
    }

    public static int read(ReadableByteChannel channel, ByteBuffer buffer) throws IOException {
        int count = channel.read(buffer);
        if (count == -1) throw new EOFException("Received -1 when reading from channel, socket has likely been closed.");
        return count;
    }
}
