package com.google.android.gms.common.net;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.security.PrivateKey;
import javax.net.SocketFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class SSLCertificateSocketFactory extends SSLSocketFactory {
    private static final TrustManager[] zzvf = new TrustManager[]{new zza()};
    @VisibleForTesting
    private final Context mContext;
    @VisibleForTesting
    private SSLSocketFactory zzvg = null;
    @VisibleForTesting
    private SSLSocketFactory zzvh = null;
    @VisibleForTesting
    private TrustManager[] zzvi = null;
    @VisibleForTesting
    private KeyManager[] zzvj = null;
    @VisibleForTesting
    private byte[] zzvk = null;
    @VisibleForTesting
    private byte[] zzvl = null;
    @VisibleForTesting
    private PrivateKey zzvm = null;
    @VisibleForTesting
    private final int zzvn;
    @VisibleForTesting
    private final boolean zzvo;
    @VisibleForTesting
    private final boolean zzvp;
    @VisibleForTesting
    private final String zzvq;

    private SSLCertificateSocketFactory(Context context, int i, boolean z, boolean z2, String str) {
        this.mContext = context.getApplicationContext();
        this.zzvn = i;
        this.zzvo = z;
        this.zzvp = z2;
        this.zzvq = str;
    }

    public static SocketFactory getDefault(Context context, int i) {
        return new SSLCertificateSocketFactory(context, i, false, true, null);
    }

    public static SSLSocketFactory getDefaultWithCacheDir(int i, Context context, String str) {
        return new SSLCertificateSocketFactory(context, i, true, true, str);
    }

    public static SSLSocketFactory getDefaultWithSessionCache(int i, Context context) {
        return new SSLCertificateSocketFactory(context, i, true, true, null);
    }

    public static SSLSocketFactory getInsecure(Context context, int i, boolean z) {
        return new SSLCertificateSocketFactory(context, i, z, false, null);
    }

    public static void verifyHostname(Socket socket, String str) throws IOException {
        if (socket instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket) socket;
            sSLSocket.startHandshake();
            SSLSession session = sSLSocket.getSession();
            if (session == null) {
                throw new SSLException("Cannot verify SSL socket without session");
            } else if (!HttpsURLConnection.getDefaultHostnameVerifier().verify(str, session)) {
                String str2 = "Cannot verify hostname: ";
                str = String.valueOf(str);
                throw new SSLPeerUnverifiedException(str.length() != 0 ? str2.concat(str) : new String(str2));
            } else {
                return;
            }
        }
        throw new IllegalArgumentException("Attempt to verify non-SSL socket");
    }

    @VisibleForTesting
    private static void zza(Socket socket, int i) {
        String valueOf;
        StringBuilder stringBuilder;
        if (socket != null) {
            try {
                socket.getClass().getMethod("setHandshakeTimeout", new Class[]{Integer.TYPE}).invoke(socket, new Object[]{Integer.valueOf(i)});
            } catch (Throwable e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                valueOf = String.valueOf(socket.getClass());
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 46);
                stringBuilder.append("Failed to invoke setSocketHandshakeTimeout on ");
                stringBuilder.append(valueOf);
                throw new RuntimeException(stringBuilder.toString(), e);
            } catch (Throwable e2) {
                valueOf = String.valueOf(socket.getClass());
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 45);
                stringBuilder.append(valueOf);
                stringBuilder.append(" does not implement setSocketHandshakeTimeout");
                throw new IllegalArgumentException(stringBuilder.toString(), e2);
            }
        }
    }

    @VisibleForTesting
    private static void zza(Socket socket, PrivateKey privateKey) {
        String valueOf;
        StringBuilder stringBuilder;
        if (socket != null) {
            try {
                socket.getClass().getMethod("setChannelIdPrivateKey", new Class[]{PrivateKey.class}).invoke(socket, new Object[]{privateKey});
            } catch (Throwable e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                valueOf = String.valueOf(socket.getClass());
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 43);
                stringBuilder.append("Failed to invoke setChannelIdPrivateKey on ");
                stringBuilder.append(valueOf);
                throw new RuntimeException(stringBuilder.toString(), e);
            } catch (Throwable e2) {
                valueOf = String.valueOf(socket.getClass());
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 42);
                stringBuilder.append(valueOf);
                stringBuilder.append(" does not implement setChannelIdPrivateKey");
                throw new IllegalArgumentException(stringBuilder.toString(), e2);
            }
        }
    }

    @VisibleForTesting
    private static void zza(Socket socket, byte[] bArr) {
        String valueOf;
        StringBuilder stringBuilder;
        if (socket != null) {
            try {
                socket.getClass().getMethod("setNpnProtocols", new Class[]{byte[].class}).invoke(socket, new Object[]{bArr});
            } catch (Throwable e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                valueOf = String.valueOf(socket.getClass());
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 36);
                stringBuilder.append("Failed to invoke setNpnProtocols on ");
                stringBuilder.append(valueOf);
                throw new RuntimeException(stringBuilder.toString(), e);
            } catch (Throwable e2) {
                valueOf = String.valueOf(socket.getClass());
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 35);
                stringBuilder.append(valueOf);
                stringBuilder.append(" does not implement setNpnProtocols");
                throw new IllegalArgumentException(stringBuilder.toString(), e2);
            }
        }
    }

    private static byte[] zza(byte[]... bArr) {
        if (bArr.length != 0) {
            int length = bArr.length;
            int i = 0;
            int i2 = i;
            while (i < length) {
                byte[] bArr2 = bArr[i];
                if (bArr2.length == 0 || bArr2.length > 255) {
                    length = bArr2.length;
                    StringBuilder stringBuilder = new StringBuilder(44);
                    stringBuilder.append("s.length == 0 || s.length > 255: ");
                    stringBuilder.append(length);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                i2 += bArr2.length + 1;
                i++;
            }
            byte[] bArr3 = new byte[i2];
            i = bArr.length;
            i2 = 0;
            int i3 = i2;
            while (i2 < i) {
                byte[] bArr4 = bArr[i2];
                int i4 = i3 + 1;
                bArr3[i3] = (byte) bArr4.length;
                i3 = bArr4.length;
                int i5 = i4;
                i4 = 0;
                while (i4 < i3) {
                    int i6 = i5 + 1;
                    bArr3[i5] = bArr4[i4];
                    i4++;
                    i5 = i6;
                }
                i2++;
                i3 = i5;
            }
            return bArr3;
        }
        throw new IllegalArgumentException("items.length == 0");
    }

    @VisibleForTesting
    private static void zzb(Socket socket, byte[] bArr) {
        String valueOf;
        StringBuilder stringBuilder;
        if (socket != null) {
            try {
                socket.getClass().getMethod("setAlpnProtocols", new Class[]{byte[].class}).invoke(socket, new Object[]{bArr});
            } catch (Throwable e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                valueOf = String.valueOf(socket.getClass());
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 37);
                stringBuilder.append("Failed to invoke setAlpnProtocols on ");
                stringBuilder.append(valueOf);
                throw new RuntimeException(stringBuilder.toString(), e);
            } catch (Throwable e2) {
                valueOf = String.valueOf(socket.getClass());
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 36);
                stringBuilder.append(valueOf);
                stringBuilder.append(" does not implement setAlpnProtocols");
                throw new IllegalArgumentException(stringBuilder.toString(), e2);
            }
        }
    }

    @VisibleForTesting
    private final synchronized SSLSocketFactory zzcx() {
        if (this.zzvp) {
            SSLSocketFactory makeSocketFactoryWithCacheDir;
            if (this.zzvq != null) {
                if (this.zzvh == null) {
                    makeSocketFactoryWithCacheDir = SocketFactoryCreator.getInstance().makeSocketFactoryWithCacheDir(this.mContext, this.zzvj, this.zzvi, this.zzvq);
                }
                return this.zzvh;
            }
            if (this.zzvh == null) {
                makeSocketFactoryWithCacheDir = SocketFactoryCreator.getInstance().makeSocketFactory(this.mContext, this.zzvj, this.zzvi, this.zzvo);
            }
            return this.zzvh;
            this.zzvh = makeSocketFactoryWithCacheDir;
            return this.zzvh;
        }
        if (this.zzvg == null) {
            Log.w("SSLCertificateSocketFactory", "Bypassing SSL security checks at caller's request");
            this.zzvg = SocketFactoryCreator.getInstance().makeSocketFactory(this.mContext, this.zzvj, zzvf, this.zzvo);
        }
        return this.zzvg;
    }

    public Socket createSocket() throws IOException {
        Socket createSocket = zzcx().createSocket();
        zza(createSocket, this.zzvk);
        zzb(createSocket, this.zzvl);
        zza(createSocket, this.zzvn);
        zza(createSocket, this.zzvm);
        return createSocket;
    }

    public Socket createSocket(String str, int i) throws IOException {
        Socket createSocket = zzcx().createSocket(str, i);
        zza(createSocket, this.zzvk);
        zzb(createSocket, this.zzvl);
        zza(createSocket, this.zzvn);
        zza(createSocket, this.zzvm);
        if (this.zzvp) {
            verifyHostname(createSocket, str);
        }
        return createSocket;
    }

    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
        Socket createSocket = zzcx().createSocket(str, i, inetAddress, i2);
        zza(createSocket, this.zzvk);
        zzb(createSocket, this.zzvl);
        zza(createSocket, this.zzvn);
        zza(createSocket, this.zzvm);
        if (this.zzvp) {
            verifyHostname(createSocket, str);
        }
        return createSocket;
    }

    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        Socket createSocket = zzcx().createSocket(inetAddress, i);
        zza(createSocket, this.zzvk);
        zzb(createSocket, this.zzvl);
        zza(createSocket, this.zzvn);
        zza(createSocket, this.zzvm);
        return createSocket;
    }

    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        Socket createSocket = zzcx().createSocket(inetAddress, i, inetAddress2, i2);
        zza(createSocket, this.zzvk);
        zzb(createSocket, this.zzvl);
        zza(createSocket, this.zzvn);
        zza(createSocket, this.zzvm);
        return createSocket;
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        socket = zzcx().createSocket(socket, str, i, z);
        zza(socket, this.zzvk);
        zzb(socket, this.zzvl);
        zza(socket, this.zzvn);
        zza(socket, this.zzvm);
        if (this.zzvp) {
            verifyHostname(socket, str);
        }
        return socket;
    }

    public byte[] getAlpnSelectedProtocol(Socket socket) {
        String valueOf;
        StringBuilder stringBuilder;
        try {
            return (byte[]) socket.getClass().getMethod("getAlpnSelectedProtocol", new Class[0]).invoke(socket, new Object[0]);
        } catch (Throwable e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            valueOf = String.valueOf(socket.getClass());
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 44);
            stringBuilder.append("Failed to invoke getAlpnSelectedProtocol on ");
            stringBuilder.append(valueOf);
            throw new RuntimeException(stringBuilder.toString(), e);
        } catch (Throwable e2) {
            valueOf = String.valueOf(socket.getClass());
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 43);
            stringBuilder.append(valueOf);
            stringBuilder.append(" does not implement getAlpnSelectedProtocol");
            throw new IllegalArgumentException(stringBuilder.toString(), e2);
        }
    }

    public String[] getDefaultCipherSuites() {
        return zzcx().getDefaultCipherSuites();
    }

    public byte[] getNpnSelectedProtocol(Socket socket) {
        String valueOf;
        StringBuilder stringBuilder;
        try {
            return (byte[]) socket.getClass().getMethod("getNpnSelectedProtocol", new Class[0]).invoke(socket, new Object[0]);
        } catch (Throwable e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            valueOf = String.valueOf(socket.getClass());
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 43);
            stringBuilder.append("Failed to invoke getNpnSelectedProtocol on ");
            stringBuilder.append(valueOf);
            throw new RuntimeException(stringBuilder.toString(), e);
        } catch (Throwable e2) {
            valueOf = String.valueOf(socket.getClass());
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 42);
            stringBuilder.append(valueOf);
            stringBuilder.append(" does not implement getNpnSelectedProtocol");
            throw new IllegalArgumentException(stringBuilder.toString(), e2);
        }
    }

    public String[] getSupportedCipherSuites() {
        return zzcx().getSupportedCipherSuites();
    }

    public void setAlpnProtocols(byte[][] bArr) {
        this.zzvl = zza(bArr);
    }

    public void setChannelIdPrivateKey(PrivateKey privateKey) {
        this.zzvm = privateKey;
    }

    public void setHostname(Socket socket, String str) {
        String valueOf;
        StringBuilder stringBuilder;
        try {
            socket.getClass().getMethod("setHostname", new Class[]{String.class}).invoke(socket, new Object[]{str});
        } catch (Throwable e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            valueOf = String.valueOf(socket.getClass());
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 32);
            stringBuilder.append("Failed to invoke setHostname on ");
            stringBuilder.append(valueOf);
            throw new RuntimeException(stringBuilder.toString(), e);
        } catch (Throwable e2) {
            valueOf = String.valueOf(socket.getClass());
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 31);
            stringBuilder.append(valueOf);
            stringBuilder.append(" does not implement setHostname");
            throw new IllegalArgumentException(stringBuilder.toString(), e2);
        }
    }

    public void setKeyManagers(KeyManager[] keyManagerArr) {
        this.zzvj = keyManagerArr;
        this.zzvh = null;
        this.zzvg = null;
    }

    public void setNpnProtocols(byte[][] bArr) {
        this.zzvk = zza(bArr);
    }

    public void setSoWriteTimeout(Socket socket, int i) throws SocketException {
        String valueOf;
        StringBuilder stringBuilder;
        try {
            socket.getClass().getMethod("setSoWriteTimeout", new Class[]{Integer.TYPE}).invoke(socket, new Object[]{Integer.valueOf(i)});
        } catch (Throwable e) {
            Throwable cause = e.getCause();
            if (cause instanceof SocketException) {
                throw ((SocketException) cause);
            } else if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else {
                valueOf = String.valueOf(socket.getClass());
                stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 38);
                stringBuilder.append("Failed to invoke setSoWriteTimeout on ");
                stringBuilder.append(valueOf);
                throw new RuntimeException(stringBuilder.toString(), e);
            }
        } catch (Throwable e2) {
            valueOf = String.valueOf(socket.getClass());
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 37);
            stringBuilder.append(valueOf);
            stringBuilder.append(" does not implement setSoWriteTimeout");
            throw new IllegalArgumentException(stringBuilder.toString(), e2);
        }
    }

    public void setTrustManagers(TrustManager[] trustManagerArr) {
        this.zzvi = trustManagerArr;
        this.zzvh = null;
    }

    public void setUseSessionTickets(Socket socket, boolean z) {
        String valueOf;
        StringBuilder stringBuilder;
        try {
            socket.getClass().getMethod("setUseSessionTickets", new Class[]{Boolean.TYPE}).invoke(socket, new Object[]{Boolean.valueOf(z)});
        } catch (Throwable e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            valueOf = String.valueOf(socket.getClass());
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 41);
            stringBuilder.append("Failed to invoke setUseSessionTickets on ");
            stringBuilder.append(valueOf);
            throw new RuntimeException(stringBuilder.toString(), e);
        } catch (Throwable e2) {
            valueOf = String.valueOf(socket.getClass());
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 40);
            stringBuilder.append(valueOf);
            stringBuilder.append(" does not implement setUseSessionTickets");
            throw new IllegalArgumentException(stringBuilder.toString(), e2);
        }
    }
}
