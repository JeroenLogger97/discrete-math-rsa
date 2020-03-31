package discretemathematics;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RsaUtil {
    
    static BigInteger[] getPrimeFactors(BigInteger n) {
        
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
    
    static BigInteger chooseE(BigInteger p, BigInteger q) {
        // Choose e:
        // - random number below n
        // - not containing any of the prime factors of phi = (p - 1) * (q - 1)
        
        BigInteger phi = getPhi(p, q);
        
        List<BigInteger> phiFactors = Arrays.asList(getPrimeFactors(phi));
        
        SecureRandom random = new SecureRandom();
        
        // todo use 65537 as e? if doesn't work: check next prime number?
//        int bitLength = p.multiply(q).bitLength() - 1;
        int bitLength = 16;
        
        BigInteger e;
        while (true) {
            // e must be smaller than n, so generate BigInteger where bit length is bitlength of n - 1
            // this constructor always constructs a non-negative BigInteger
            BigInteger possibleE = new BigInteger(bitLength, random);
            
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
    
    private static BigInteger getPhi(BigInteger p, BigInteger q) {
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }
    
    static BigInteger findD(BigInteger e, BigInteger phi) {
        // find inverse d for e: (d * e) mod (p - 1) * (q - 1) = 1
//        BigInteger d = BigInteger.valueOf(0);
//        //todo is this true?
//        // we know that '((pMinusOneTimesQMinusOne + 1) * d) % pMinusOneTimesQMinusOne' will always return '1', so set that as upper bound
//        for (BigInteger i = BigInteger.valueOf(1); i <= pMinusOneTimesQMinusOne.add(BigInteger.valueOf(1)); i++) {
//            // if (d * e) mod (p - 1) * (q - 1) = 1
//            if ((i * e) % pMinusOneTimesQMinusOne == 1) {
//                d = i;
//                break;
//            }
//        }
        
        // phi = (p - 1) * (q - 1)
        // todo find inverse d for e: '(d * e) mod phi = 1' instead of using BigInteger method
        return e.modInverse(phi);
    }
}
