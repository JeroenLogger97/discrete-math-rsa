package discretemathematics;

import java.math.BigInteger;

public class RsaDriver {
    
    public static void main(String[] args) {
    
//        // -------- ENCRYPTING -------- //
//        BigInteger n = new BigInteger("14279");
//        BigInteger[] primeFactors = RsaUtil.getPrimeFactors(n);
//        BigInteger p = primeFactors[0];
//        BigInteger q = primeFactors[1];
//
//        String message = "This is message 12347808015JOIfnakjnfS!.,p[l,!";
//
//        BigInteger e = RsaUtil.chooseE(p, q);
//
//        String encrypted = encrypt(n, e, message);
//        System.out.println("encrypted = " + encrypted);
//
//        System.out.println("decrypted = " + decrypt(n, e, encrypted));
        
    }
    
    static String encrypt(BigInteger n, BigInteger e, String message) {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < message.length(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            
            char c = message.charAt(i);
            BigInteger m = BigInteger.valueOf((int) c);
            
            sb.append(encrypt(n, e, m));
        }
        
        return sb.toString();
    }
    
    private static BigInteger encrypt(BigInteger n, BigInteger e, BigInteger m) {
        return m.modPow(e, n);
    }
    
    static String decrypt(BigInteger n, BigInteger e, String encrypted) {
        BigInteger[] primeFactors = RsaUtil.getPrimeFactors(n);
        BigInteger p = primeFactors[0];
        BigInteger q = primeFactors[1];
        
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        
        BigInteger d = RsaUtil.findD(e, phi);
        System.out.println("found d " + d + " for e " + e + " and n " + n);
    
        String[] mArray = encrypted.split(",");
    
        StringBuilder sb = new StringBuilder();
    
        for (String part : mArray) {
            BigInteger m = new BigInteger(part);
        
            BigInteger decrypted = decrypt(p.multiply(q), d, m);
        
            // convert from BigInteger to char
            sb.append((char) decrypted.intValueExact());
        }
        
        return sb.toString();
    }
    
    private static BigInteger decrypt(BigInteger n, BigInteger d, BigInteger c) {
        return c.modPow(d, n);
    }
    
}
