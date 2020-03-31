package nl.hva.discretemathematics;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Rsa {
    
    private BigInteger n;
    private BigInteger e;
    private BigInteger d;
    
    // generates random e and finds d
    public Rsa(BigInteger n) {
        this.n = n;
        
        BigInteger[] primeFactors = getPrimeFactors(n);
        BigInteger p = primeFactors[0];
        BigInteger q = primeFactors[1];
        
        this.e = chooseE(p, q);
        
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        this.d = findD(this.e, phi);
    }
    
    public BigInteger getN() {
        return n;
    }
    
    public BigInteger getE() {
        return e;
    }
    
    public BigInteger getD() {
        return d;
    }
    
    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }
    
    public static BigInteger encrypt(BigInteger n, BigInteger message) {
        BigInteger[] primeFactors = getPrimeFactors(n);
        
        BigInteger p = primeFactors[0];
        BigInteger q = primeFactors[1];
        
        BigInteger e = chooseE(p, q);
        
        return message.modPow(e, n);
    }
    
    public BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(d, n);
    }
    
    public static BigInteger decrypt(BigInteger p, BigInteger q, BigInteger e, BigInteger encrypted) {
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        
        BigInteger d = findD(e, phi);
        
        return encrypted.modPow(d, p.multiply(q));
    }
    
    public static BigInteger decrypt(BigInteger n, BigInteger e, BigInteger encrypted) {
        BigInteger[] primeFactors = getPrimeFactors(n);
        
        BigInteger p = primeFactors[0];
        BigInteger q = primeFactors[1];
        
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    
        BigInteger d = findD(e, phi);
        
        return encrypted.modPow(d, n);
    }
    
    private static BigInteger chooseE(BigInteger p, BigInteger q) {
        return Main.chooseE(p, q);
    }
    
    private static BigInteger[] getPrimeFactors(BigInteger n) {
        return Main.getPrimeFactors(n);
    }
    
    private static BigInteger findD(BigInteger e, BigInteger phi) {
        // phi = (p - 1) * (q - 1)
        // todo find inverse d for e: '(d * e) mod phi = 1' instead of using BigInteger method
        return e.modInverse(phi);
    }
    
    @Override
    public String toString() {
        return "Rsa{" +
                "n=" + n +
                ", e=" + e +
                ", d=" + d +
                '}';
    }
}
