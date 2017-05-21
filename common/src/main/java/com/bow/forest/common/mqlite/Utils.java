package com.bow.forest.common.mqlite;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Properties;
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

    public static File getCanonicalFile(File f) {
        try {
            return f.getCanonicalFile();
        } catch (IOException e) {
            return f.getAbsoluteFile();
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



    public static String getString(Properties props, String name, String defaultValue) {
        return props.containsKey(name) ? props.getProperty(name) : defaultValue;
    }

    public static String getString(Properties props, String name) {
        if (props.containsKey(name)) {
            return props.getProperty(name);
        }
        throw new IllegalArgumentException("Missing required property '" + name + "'");
    }


    public static int getInt(Properties props, String name) {
        if (props.containsKey(name)) {
            return getInt(props, name, -1);
        }
        throw new IllegalArgumentException("Missing required property '" + name + "'");
    }

    public static int getInt(Properties props, String name, int defaultValue) {
        return getIntInRange(props, name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int getIntInRange(Properties props, String name, int defaultValue, int min, int max) {
        int v = defaultValue;
        if (props.containsKey(name)) {
            v = Integer.valueOf(props.getProperty(name));
        }
        if (v >= min && v <= max) {
            return v;
        }
        throw new IllegalArgumentException(name + " has value " + v + " which is not in the range");
    }
}
