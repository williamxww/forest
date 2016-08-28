package com.bow.forest.frontend.common.serialize;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import net.rubyeye.xmemcached.transcoders.Transcoder;

import com.esotericsoftware.kryo.Kryo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 
 *
 */
public class KryoTranscoder extends SerializingTranscoder implements Transcoder<Object>
{
    private final static int TIMES = 1024;

    private Kryo kryo;

    private int initSize;

    private int maxSize;

    /**
     * 
     */
    public KryoTranscoder(int initSize, int maxSize)
    {
        kryo = new Kryo();
        KryoTranscoderRegisterFactory.getRegister().register(kryo);
        this.initSize = initSize * TIMES;
        this.maxSize = maxSize * TIMES;
    }

    /**
     * Get the bytes representing the given serialized object.
     */
    @Override
    protected byte[] serialize(Object o)
    {
        if (o == null)
        {
            throw new NullPointerException("Can't serialize null!");
        }


        ByteArrayOutputStream out = null;
        Output output = null;
        try {
            out = new ByteArrayOutputStream();
            output = new Output(out, 1024);
            kryo.writeClassAndObject(output, o);
        } catch (Exception e) {
            log.debug("object serialize faild. " + e.toString());
        } finally {
            if (null != out) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                }
            }
            if (null != output) {
                output.close();
                output = null;
            }
        }
        return output.toBytes();
    }

    /**
     * Get the object represented by the given serialized bytes.
     */
    @Override
    protected Object deserialize(byte[] in)
    {
        Input buffer = new Input(in, initSize, maxSize);
        Object rv = kryo.readClassAndObject(buffer);
        return rv;
    }
}
