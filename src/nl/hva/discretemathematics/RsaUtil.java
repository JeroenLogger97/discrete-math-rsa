package nl.hva.discretemathematics;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RsaUtil {
    
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
            }
            
            // possibleE shares a prime factor with phi: continue loop and try new possible e
        }
        
        System.out.println("found e " + e + " for phi " + phi + " and n " + p.multiply(q));
        System.out.println("\tfactors of phi = " + phiFactors);
        System.out.println("\tfactors of e   = " + Arrays.toString(getPrimeFactors(e)));
        
        return e;
    }
    
    private static BigInteger getPhi(BigInteger p, BigInteger q) {
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }
    
}
