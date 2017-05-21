package com.bow.forest.common.mqlite.log;

import com.bow.forest.common.mqlite.Utils;

import java.nio.ByteBuffer;

import static java.lang.String.format;

/**
 *
 * <pre>
 * --------------------------------------------
 * | magic  | attributes |  CRC32   | payload |
 * --------------------------------------------
 * |2 bytes | 1 byte     | 8 bytes  | n bytes |
 * --------------------------------------------
 * </pre>
 * 
 * @author vv
 * @since 2017/5/20.
 */
public class Message {

    /**
     * to indicate how weight this message is.(SX)
     */
    private static final short MAGIC = 0x7378;

    /**
     * 0: no compression 1: gzip
     */
    private final int codec = 0;

    /**
     * attributes' last 2 bit represent compression
     */
    public static final int COMPRESSION_MASK = 0x03;

    public static final int PAYLOAD_OFFSET = 11;

    public static final int ATTRIBUTE_OFFSET = 2;

    public static final int CHECKSUM_OFFSET = 3;

    private final ByteBuffer buffer;

    private int messageSize;

    public Message(ByteBuffer buffer) {
        this.buffer = buffer;
        this.messageSize = buffer.limit();
    }

    public Message(byte[] bytes) {
        this.messageSize = bytes.length + PAYLOAD_OFFSET;
        this.buffer = ByteBuffer.allocate(messageSize);

        // MAGIC
        buffer.putShort(MAGIC);
        // attributes
        byte attributes = 0;
        attributes = (byte) (attributes | (COMPRESSION_MASK & codec));
        buffer.put(attributes);
        // checksum
        buffer.putLong(Utils.crc32(bytes));
        // bytes
        buffer.put(bytes);
        buffer.rewind();
    }

    public ByteBuffer payload() {
        ByteBuffer payload = buffer.duplicate();
        payload.position(PAYLOAD_OFFSET);
        payload = payload.slice();
        payload.limit(messageSize - PAYLOAD_OFFSET);
        payload.rewind();
        return payload;
    }

    public byte attributes() {
        return buffer.get(ATTRIBUTE_OFFSET);
    }

    public long checksum() {
        return buffer.getLong(CHECKSUM_OFFSET);
    }

    @Override
    public String toString() {
        return format("message(attributes = %d, crc = %d, payload = %s)", //
                attributes(), checksum(), payload());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message) {
            Message m = (Message) obj;
            return checksum() == m.checksum();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return buffer.hashCode();
    }
}
