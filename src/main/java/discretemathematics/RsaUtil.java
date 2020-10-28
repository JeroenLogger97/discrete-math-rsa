package discretemathematics;

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
        // - not containing any of the prime factors of phi = (p - 1) * (q - 1)
        
        BigInteger phi = getPhi(p, q);
        
        List<BigInteger> phiFactors = Arrays.asList(getPrimeFactors(phi));
        
        SecureRandom random = new SecureRandom();
        
        // ensure e is smaller than n and also have max bitLenght on e for performance reasons
        int bitLength = Math.min(p.multiply(q).bitLength() - 1, 16);
        
        BigInteger e;
        while (true) {
            // this constructor always constructs a non-negative BigInteger
            BigInteger possibleE = new BigInteger(bitLength, random);
            
            //todo use a.gcd(b) == 1
            boolean sharesPrimeFactor = false;
            for (BigInteger factor : getPrimeFactors(possibleE)) {
                if (phiFactors.contains(factor)) {
                    sharesPrimeFactor = true;
                }
            }
            
            if (!sharesPrimeFactor) {
                e = possibleE;
                break;
            }
            
            // possibleE shares a prime factor with phi: continue loop and try new possibleE
        }
        
        System.out.println("found e " + e + " for n " + p.multiply(q));
        
        return e;
    }
    
    public static BigInteger getPhi(BigInteger p, BigInteger q) {
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }
    
    public static BigInteger findD(BigInteger e, BigInteger phi) {
        // find inverse d for e: (d * e) mod (p - 1) * (q - 1) = 1
        return e.modInverse(phi);
    }
}
