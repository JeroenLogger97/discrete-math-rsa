package nl.hva.discretemathematics;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        BigInteger n = BigInteger.valueOf(36019);
        BigInteger n = BigInteger.valueOf(997824234353711L);
        String m = "To live a creative life, we must lose our fear of being wrong. ~Anonymous";
    
        BigInteger[] primeFactors = getPrimeFactors(n);
    
        //todo need this exception?
//        if (primeFactors.size() != 2) {
//            throw new IllegalArgumentException("N is not the product of 2 primes! Has " + primeFactors.size() + " prime factors");
//        }
        System.out.println("primeFactors = " + Arrays.toString(primeFactors));
        BigInteger p = primeFactors[0];
        BigInteger q = primeFactors[1];
        
        BigInteger e = chooseE(p, q);
        String encrypted = encrypt(n, e, m);
    
        System.out.println("N         = " + n);
        System.out.println("e         = " + e);
        System.out.println("m         = " + m);
        System.out.println("encrypted = " + encrypted);
        
    }
    
    private static String encrypt(BigInteger n, BigInteger e, String message) {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < message.length(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            
            char c = message.charAt(i);
            sb.append(encrypt(n, e, BigInteger.valueOf((int) c)));
        }
        
        return sb.toString();
    }
    
    private static BigInteger encrypt(BigInteger n, BigInteger e, BigInteger m) {
        return m.modPow(e, n);
    }
    
    public static BigInteger chooseE(BigInteger p, BigInteger q) {
        // Choose e:
        // - random number below n
        // - not containing any of the prime factors of phi
        BigInteger phi = getPhi(p, q);
        
        List<BigInteger> phiFactors = Arrays.asList(getPrimeFactors(phi));
        
        SecureRandom random = new SecureRandom();
        
        // use 65537 as e
        //   if doesn't work: check next prime number?
        int bitLength = p.multiply(q).bitLength() - 1;
        
        BigInteger e;
        while (true){
            // e must be smaller than n, so generate BigInteger where bit length is bitlength of n - 1
            // this constructor always constructs a non-negative BigInteger
            BigInteger possibleE = new BigInteger(bitLength, random);
            
            boolean sharesPrimeFactor = false;
            for (BigInteger factor : getPrimeFactors(possibleE)) {
                if (phiFactors.contains(factor)) {
                    sharesPrimeFactor = true;
//                    System.out.println("possibleE " + possibleE + " shares prime factor " + factor + " with phi!");
                }
            }
    
            if (!sharesPrimeFactor) {
                e = possibleE;
                break;
            } else {
                // possibleE shares a prime factor with phi: continue loop and try new possibleE
            }
        }
    
        System.out.println("found e " + e + " for phi " + phi + " and n " + p.multiply(q));
        System.out.println("\tfactors of phi = " + phiFactors);
        System.out.println("\tfactors of e   = " + Arrays.toString(getPrimeFactors(e)));
        
        return e;
    }
    
    public static BigInteger[] getPrimeFactors(BigInteger n) {
        
        BigInteger two = BigInteger.valueOf(2);
        List<BigInteger> factors = new ArrayList<>();
        
        while (n.mod(two).equals(BigInteger.ZERO)) {
            factors.add(two);
            n = n.divide(two);
        }
    
        if (n.compareTo(BigInteger.ONE) > 0) {
            BigInteger possibleFactor = BigInteger.valueOf(3);
            
            // only check up to possibleFactor^2: any number higher than that will be above n and therefore cannot be a factor of n
            while (possibleFactor.multiply(possibleFactor).compareTo(n) <= 0) {
                if (n.mod(possibleFactor).equals(BigInteger.ZERO)) {
                    factors.add(possibleFactor);
                    n = n.divide(possibleFactor);
                } else {
                    possibleFactor = possibleFactor.add(two);
                }
            }
            factors.add(n);
        }
        
        BigInteger[] primeFactors = new BigInteger[factors.size()];
        for (int j = 0; j < factors.size(); j++) {
            primeFactors[j] = factors.get(j);
        }
        return primeFactors;
    }
    
    private static BigInteger getPhi(BigInteger p, BigInteger q) {
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }
    
    private static BigInteger decrypt(BigInteger n, BigInteger e, BigInteger c) {
        // find p and q
        BigInteger[] primeFactorsOfN = getPrimeFactors(n);
    
        BigInteger p = primeFactorsOfN[0];
        BigInteger q = primeFactorsOfN[1];
        
        // find inverse d for e: (d * e) mod (p - 1) * (q - 1) = 1
        BigInteger d = BigInteger.valueOf(0);
        
        //todo is this true?
        // we know that '((pMinusOneTimesQMinusOne + 1) * d) % pMinusOneTimesQMinusOne' will always return '1', so set that as upper bound
//        for (BigInteger i = BigInteger.valueOf(1); i <= pMinusOneTimesQMinusOne.add(BigInteger.valueOf(1)); i++) {
//            // if (d * e) mod (p - 1) * (q - 1) = 1
//            if ((i * e) % pMinusOneTimesQMinusOne == 1) {
//                d = i;
//                break;
//            }
//        }
        
        // private key is N, d
        // m^d mod N = decrypted
        
        return null;
    }
    
}
