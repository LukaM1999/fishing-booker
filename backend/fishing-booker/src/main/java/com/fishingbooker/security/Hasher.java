package com.fishingbooker.security;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.util.encoders.Hex;

public class Hasher {

    public static String hashPAN(String panNumber) {
        Digest digest = new SHA256Digest();
        byte[] panBytes = panNumber.getBytes();
        byte[] hashedPAN = new byte[digest.getDigestSize()];

        digest.update(panBytes, 0, panBytes.length);
        digest.doFinal(hashedPAN, 0);

        return new String(Hex.encode(hashedPAN));
    }

    public static void main(String[] args) {
        System.out.println(hashPAN("456"));
    }
}
