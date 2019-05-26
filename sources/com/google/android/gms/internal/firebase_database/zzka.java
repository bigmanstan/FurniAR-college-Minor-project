package com.google.android.gms.internal.firebase_database;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;

final class zzka {
    private zzjw zztb = null;
    private DataInputStream zztx = null;
    private zzjr zzty = null;
    private byte[] zztz = new byte[112];
    private zzjm zzua;
    private volatile boolean zzub = false;

    zzka(zzjr zzjr) {
        this.zzty = zzjr;
    }

    private final int read(byte[] bArr, int i, int i2) throws IOException {
        this.zztx.readFully(bArr, i, i2);
        return i2;
    }

    private final void zzc(zzjx zzjx) {
        this.zzub = true;
        this.zzty.zzb(zzjx);
    }

    final void run() {
        this.zztb = this.zzty.zzgj();
        while (!this.zzub) {
            try {
                int read = read(this.zztz, 0, 1) + 0;
                int i = (this.zztz[0] & 128) != 0 ? 1 : 0;
                if (((this.zztz[0] & 112) != 0 ? 1 : 0) == 0) {
                    byte[] bArr;
                    byte b = (byte) (this.zztz[0] & 15);
                    read += read(this.zztz, read, 1);
                    byte b2 = this.zztz[1];
                    long j = 0;
                    if (b2 < (byte) 126) {
                        j = (long) b2;
                    } else if (b2 == (byte) 126) {
                        read(this.zztz, read, 2);
                        j = (long) (((this.zztz[2] & 255) << 8) | (this.zztz[3] & 255));
                    } else if (b2 == Byte.MAX_VALUE) {
                        read += read(this.zztz, read, 8);
                        bArr = this.zztz;
                        read -= 8;
                        j = (((((((((long) bArr[read]) << 56) + (((long) (bArr[read + 1] & 255)) << 48)) + (((long) (bArr[read + 2] & 255)) << 40)) + (((long) (bArr[read + 3] & 255)) << 32)) + (((long) (bArr[read + 4] & 255)) << 24)) + ((long) ((bArr[read + 5] & 255) << 16))) + ((long) ((bArr[read + 6] & 255) << 8))) + ((long) (bArr[read + 7] & 255));
                    }
                    read = (int) j;
                    bArr = new byte[read];
                    read(bArr, 0, read);
                    if (b == (byte) 8) {
                        this.zzty.zzgk();
                    } else if (b == (byte) 10) {
                        continue;
                    } else {
                        if (!(b == (byte) 1 || b == (byte) 2 || b == (byte) 9)) {
                            if (b != (byte) 0) {
                                StringBuilder stringBuilder = new StringBuilder(24);
                                stringBuilder.append("Unsupported opcode: ");
                                stringBuilder.append(b);
                                throw new zzjx(stringBuilder.toString());
                            }
                        }
                        if (b != (byte) 9) {
                            if (this.zzua != null) {
                                if (b != (byte) 0) {
                                    throw new zzjx("Failed to continue outstanding frame");
                                }
                            }
                            if (this.zzua == null) {
                                if (b == (byte) 0) {
                                    throw new zzjx("Received continuing frame, but there's nothing to continue");
                                }
                            }
                            if (this.zzua == null) {
                                this.zzua = b == (byte) 2 ? new zzjl() : new zzjn();
                            }
                            if (!this.zzua.zzb(bArr)) {
                                throw new zzjx("Failed to decode frame");
                            } else if (i != 0) {
                                zzjz zzgh = this.zzua.zzgh();
                                this.zzua = null;
                                if (zzgh != null) {
                                    this.zztb.zza(zzgh);
                                } else {
                                    throw new zzjx("Failed to decode whole message");
                                }
                            } else {
                                continue;
                            }
                        } else if (i == 0) {
                            throw new zzjx("PING must not fragment across frames");
                        } else if (bArr.length <= 125) {
                            this.zzty.zzd(bArr);
                        } else {
                            throw new zzjx("PING frame too long");
                        }
                    }
                }
                throw new zzjx("Invalid frame received");
            } catch (SocketTimeoutException e) {
            } catch (Throwable e2) {
                zzc(new zzjx("IO Error", e2));
            } catch (zzjx e3) {
                zzc(e3);
            }
        }
    }

    final void zza(DataInputStream dataInputStream) {
        this.zztx = dataInputStream;
    }

    final void zzgp() {
        this.zzub = true;
    }
}
