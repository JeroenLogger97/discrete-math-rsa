package discretemathematics;

import java.math.BigInteger;

import static discretemathematics.RsaDriver.decrypt;
import static discretemathematics.RsaDriver.encrypt;

public class RsaDriverTest {
    
    public static void main(String[] args) {
        whenEncryptingAndDecrypting_shouldReturnOriginalMessage();
        System.out.println("----------------------------------");
        decrypt_smallN();
        System.out.println("----------------------------------");
        decrypt_bigN();
        System.out.println("----------------------------------");
    }
    
    private static void whenEncryptingAndDecrypting_shouldReturnOriginalMessage() {
        BigInteger n = new BigInteger("14279");
        
        BigInteger[] primeFactors = RsaUtil.getPrimeFactors(n);
        BigInteger p = primeFactors[0];
        BigInteger q = primeFactors[1];
        
        String message = "This is a message!.,p[l,!";
        
        BigInteger e = RsaUtil.chooseE(p, q);
        
        String encrypted = encrypt(n, e, message);
        String decrypted = decrypt(n, e, encrypted);
        
        System.out.println("encrypted = " + encrypted);
        System.out.println("decrypted = " + decrypted);
        
        if (!message.equals(decrypted)) {
            System.err.println("DECRYPTED DOES NOT MATCH ORIGINAL MESSAGE");
        }
    }
    
    private static void decrypt_bigN() {
        // Big N
        String input = "298566263964416,942650725883005,482337778916712,553043242369731,329969272985475,718557" +
                "134861008,412857346187050,32613152389277,123702196911366,145251958295474,4823377789167" +
                "12,553043242369731,32613152389277,718557134861008,493716314799522,213379881891167,4823" +
                "37778916712,718557134861008,145251958295474,942650725883005,32613152389277,71855713486" +
                "1008,766748599917557,298178975345559,405388673881409,405388673881409,298178975345559,6" +
                "02477621196073,844420393103912,718557134861008,329969272985475,213379881891167,7185571" +
                "34861008,145251958295474,123702196911366,123702196911366,32613152389277,23097642406119" +
                "9,329969272985475,718557134861008,329969272985475,655326670009073,32613152389277,71855" +
                "7134861008,942650725883005,298178975345559,553043242369731,297559616949743,73482010311" +
                "1010,718557134861008,602477621196073,213379881891167,329969272985475,718557134861008,4" +
                "12857346187050,32613152389277,123702196911366,145251958295474,482337778916712,55304324" +
                "2369731,32613152389277,718557134861008,298178975345559,329969272985475,17253068466305," +
                "553043242369731,718557134861008,553043242369731,145251958295474,654210323572303,326131" +
                "52389277,718557134861008,213379881891167,942650725883005,718557134861008,1237021969113" +
                "66,32613152389277,942650725883005,329969272985475,145251958295474,298178975345559,6024" +
                "77621196073,132719700274691,718557134861008,930263128306837,641325607684028,6024776211" +
                "96073,213379881891167,602477621196073,493716314799522,830430423990737,213379881891167," +
                "482337778916712,553043242369731";
        BigInteger n = new BigInteger("997824234780887");
        BigInteger e = new BigInteger("9011");
        
        String decrypted = decrypt(n, e, input);
        System.out.println("decrypted = " + decrypted);
    }
    
    private static void decrypt_smallN() {
        // Small N
        String input = "9353,1000,9320,9046,11520,1793,9709,9320,14259,9320,3911,5858,9709,14259,6152,11520,1793,970" +
                "9,9320,9046,11520,6274,9709,12219,9320,1539,9709,9320,6431,755,11578,6152,9320,9046,1000,1157" +
                "8,9709,9320,1000,755,5858,9320,6274,9709,14259,5858,9320,1000,6274,9320,10640,9709,11520,1395" +
                "3,9132,9320,1539,5858,1000,13953,9132,2117,9320,2178,6856,13953,1000,13953,2636,6431,1000,755" +
                ",11578";
        BigInteger n = new BigInteger("14279");
        BigInteger e = new BigInteger("7");
    
        String decrypted = decrypt(n, e, input);
        System.out.println("decrypted = " + decrypted);
    }
    
}
