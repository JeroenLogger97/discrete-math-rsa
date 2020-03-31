package nl.hva.discretemathematics;

import java.math.BigInteger;

public class RsaDriver {
    
    public static void main(String[] args) {
//        BigInteger n = BigInteger.valueOf(36019);
////        BigInteger n = BigInteger.valueOf(997824234353711L);
//
//        String message = "To live a creative life, we must lose our fear of being wrong. ~Anonymous";
//
//        Rsa rsa = new Rsa(n);
//        System.out.println("n = " + rsa.getN());
//        System.out.println("e = " + rsa.getE());
//        System.out.println("d = " + rsa.getD());
//
//        String encrypted = encrypt(rsa, message);
//        System.out.println("encrypted = " + encrypted);
//        System.out.println("decrypted = " + decrypt(rsa, encrypted));
        
        // Small N
//        String input = "9353,1000,9320,9046,11520,1793,9709,9320,14259,9320,3911,5858,9709,14259,6152,11520,1793,970" +
//                "9,9320,9046,11520,6274,9709,12219,9320,1539,9709,9320,6431,755,11578,6152,9320,9046,1000,1157" +
//                "8,9709,9320,1000,755,5858,9320,6274,9709,14259,5858,9320,1000,6274,9320,10640,9709,11520,1395" +
//                "3,9132,9320,1539,5858,1000,13953,9132,2117,9320,2178,6856,13953,1000,13953,2636,6431,1000,755" +
//                ",11578";
//        BigInteger n = new BigInteger("14279");
//        BigInteger e = new BigInteger("7");
        
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
    
        BigInteger[] primeFactors = RsaUtil.getPrimeFactors(n);
        BigInteger p = primeFactors[0];
        BigInteger q = primeFactors[1];
        
        for (String s : input.split(",")) {
//            BigInteger decrypt = Rsa.decrypt(n, e, new BigInteger(s));
            BigInteger decrypt = Rsa.decrypt(p, q, e, new BigInteger(s));
            System.out.print((char) decrypt.intValueExact());
        }
        
    }
    
    private static String encrypt(Rsa rsa, String message) {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < message.length(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            
            char c = message.charAt(i);
            sb.append(rsa.encrypt(BigInteger.valueOf((int) c)));
        
        }
        
        return sb.toString();
    }
    
    private static String decrypt(Rsa rsa, String encrypted) {
        String[] split = encrypted.split(",");
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < split.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            
            sb.append(rsa.decrypt(new BigInteger(split[i])));
        }
        
        return sb.toString();
    }
    
}
