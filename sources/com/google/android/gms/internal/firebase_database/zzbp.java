package com.google.android.gms.internal.firebase_database;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public final class zzbp extends Reader {
    private boolean closed;
    private List<String> zzgn;
    private int zzgo;
    private int zzgp;
    private int zzgq;
    private int zzgr;
    private boolean zzgs;

    public zzbp() {
        this.zzgn = null;
        this.closed = false;
        this.zzgq = this.zzgo;
        this.zzgr = this.zzgp;
        this.zzgs = false;
        this.zzgn = new ArrayList();
    }

    private final String zzbb() {
        return this.zzgp < this.zzgn.size() ? (String) this.zzgn.get(this.zzgp) : null;
    }

    private final int zzbc() {
        String zzbb = zzbb();
        return zzbb == null ? 0 : zzbb.length() - this.zzgo;
    }

    private final void zzbd() throws IOException {
        if (this.closed) {
            throw new IOException("Stream already closed");
        } else if (!this.zzgs) {
            throw new IOException("Reader needs to be frozen before read operations can be called");
        }
    }

    private final long zzj(long j) {
        long j2 = 0;
        while (this.zzgp < this.zzgn.size() && j2 < j) {
            long j3 = j - j2;
            long zzbc = (long) zzbc();
            if (j3 < zzbc) {
                this.zzgo = (int) (((long) this.zzgo) + j3);
                j2 += j3;
            } else {
                j2 += zzbc;
                this.zzgo = 0;
                this.zzgp++;
            }
        }
        return j2;
    }

    public final void close() throws IOException {
        zzbd();
        this.closed = true;
    }

    public final void mark(int i) throws IOException {
        zzbd();
        this.zzgq = this.zzgo;
        this.zzgr = this.zzgp;
    }

    public final boolean markSupported() {
        return true;
    }

    public final int read() throws IOException {
        zzbd();
        String zzbb = zzbb();
        if (zzbb == null) {
            return -1;
        }
        char charAt = zzbb.charAt(this.zzgo);
        zzj(1);
        return charAt;
    }

    public final int read(CharBuffer charBuffer) throws IOException {
        zzbd();
        int remaining = charBuffer.remaining();
        String zzbb = zzbb();
        int i = 0;
        while (remaining > 0 && zzbb != null) {
            int min = Math.min(zzbb.length() - this.zzgo, remaining);
            charBuffer.put((String) this.zzgn.get(this.zzgp), this.zzgo, this.zzgo + min);
            remaining -= min;
            i += min;
            zzj((long) min);
            zzbb = zzbb();
        }
        if (i <= 0) {
            if (zzbb == null) {
                return -1;
            }
        }
        return i;
    }

    public final int read(char[] cArr, int i, int i2) throws IOException {
        zzbd();
        String zzbb = zzbb();
        int i3 = 0;
        while (zzbb != null && i3 < i2) {
            int min = Math.min(zzbc(), i2 - i3);
            zzbb.getChars(this.zzgo, this.zzgo + min, cArr, i + i3);
            i3 += min;
            zzj((long) min);
            zzbb = zzbb();
        }
        if (i3 <= 0) {
            if (zzbb == null) {
                return -1;
            }
        }
        return i3;
    }

    public final boolean ready() throws IOException {
        zzbd();
        return true;
    }

    public final void reset() throws IOException {
        this.zzgo = this.zzgq;
        this.zzgp = this.zzgr;
    }

    public final long skip(long j) throws IOException {
        zzbd();
        return zzj(j);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String append : this.zzgn) {
            stringBuilder.append(append);
        }
        return stringBuilder.toString();
    }

    public final void zzba() {
        if (this.zzgs) {
            throw new IllegalStateException("Trying to freeze frozen StringListReader");
        }
        this.zzgs = true;
    }

    public final void zzn(String str) {
        if (this.zzgs) {
            throw new IllegalStateException("Trying to add string after reading");
        } else if (str.length() > 0) {
            this.zzgn.add(str);
        }
    }
}
