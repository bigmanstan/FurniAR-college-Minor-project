package com.google.ar.sceneform.rendering;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

class ResourceHelper {
    ResourceHelper() {
    }

    static ByteBuffer readResource(Context context, int resourceId) {
        if (context == null) {
            return null;
        }
        int length = 0;
        ReadableByteChannel source;
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            inputStream.mark(-1);
            while (inputStream.read() != -1) {
                length++;
            }
            inputStream.reset();
            ByteBuffer buffer = ByteBuffer.allocateDirect(length);
            source = Channels.newChannel(inputStream);
            source.read(buffer);
            source.close();
            buffer.rewind();
            return buffer;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        } catch (Throwable th) {
            source.close();
        }
    }
}
