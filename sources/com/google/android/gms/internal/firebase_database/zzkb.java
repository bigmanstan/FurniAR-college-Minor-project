package com.google.android.gms.internal.firebase_database;

import android.support.v4.internal.view.SupportMenu;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

final class zzkb {
    private final Random zzgf = new Random();
    private final Thread zzth = zzjr.getThreadFactory().newThread(new zzkc(this));
    private zzjr zzty;
    private volatile boolean zzub = false;
    private BlockingQueue<ByteBuffer> zzuc;
    private boolean zzud = false;
    private WritableByteChannel zzue;

    zzkb(zzjr zzjr, String str, int i) {
        zzjq zzgi = zzjr.zzgi();
        Thread thread = this.zzth;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 18);
        stringBuilder.append(str);
        stringBuilder.append("Writer-");
        stringBuilder.append(i);
        zzgi.zza(thread, stringBuilder.toString());
        this.zzty = zzjr;
        this.zzuc = new LinkedBlockingQueue();
    }

    private final void zzgq() throws InterruptedException, IOException {
        this.zzue.write((ByteBuffer) this.zzuc.take());
    }

    private final void zzgs() {
        while (!this.zzub && !Thread.interrupted()) {
            try {
                zzgq();
            } catch (Throwable e) {
                this.zzty.zzb(new zzjx("IO Exception", e));
                return;
            } catch (InterruptedException e2) {
                return;
            }
        }
        for (int i = 0; i < this.zzuc.size(); i++) {
            zzgq();
        }
    }

    final synchronized void zza(byte b, boolean z, byte[] bArr) throws IOException {
        int i = 6;
        int length = bArr.length;
        if (length >= 126) {
            i = length <= SupportMenu.USER_MASK ? 8 : 14;
        }
        ByteBuffer allocate = ByteBuffer.allocate(bArr.length + i);
        allocate.put((byte) (b | -128));
        int i2 = 0;
        if (length < 126) {
            allocate.put((byte) (length | 128));
        } else if (length <= SupportMenu.USER_MASK) {
            allocate.put((byte) -2);
            allocate.putShort((short) length);
        } else {
            allocate.put((byte) -1);
            allocate.putInt(0);
            allocate.putInt(length);
        }
        byte[] bArr2 = new byte[4];
        this.zzgf.nextBytes(bArr2);
        allocate.put(bArr2);
        while (i2 < bArr.length) {
            allocate.put((byte) (bArr[i2] ^ bArr2[i2 % 4]));
            i2++;
        }
        allocate.flip();
        if (this.zzub) {
            if (this.zzud || b != (byte) 8) {
                throw new zzjx("Shouldn't be sending");
            }
        }
        if (b == (byte) 8) {
            this.zzud = true;
        }
        this.zzuc.add(allocate);
    }

    final void zza(OutputStream outputStream) {
        this.zzue = Channels.newChannel(outputStream);
    }

    final void zzgr() {
        this.zzub = true;
    }

    final Thread zzgt() {
        return this.zzth;
    }
}
