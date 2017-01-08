import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHAHashingExample {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		 
		String password = "123456";
		
		System.out.println(hash(password));
		
		
		/*
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();
        String a = new String();
        int b=0;
        
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        //a=Integer.valueOf("sb.charAt(0)",16).toString() ;
        
        //System.out.println("Hex format : " + sb.charAt(0));
        a = sb.charAt(0)+"";
        b = Integer.valueOf(a);
        System.out.println(b);*/
/*	
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	System.out.println("Hex format : " + hexString.toString());	
		*/
	}
	
	public static String hash (String s) throws NoSuchAlgorithmException{
		

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(s.getBytes());
        byte byteData[] = md.digest();
        String a = new String();
        int b=0;
        String c =null;
        
        //convert the byte to hex format method
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        a = sb.charAt(0)+"";
        b = Integer.valueOf(a);
        c = Integer.toBinaryString(b);
        //System.out.println(b);
		return c.charAt(0)+"";
	}

}
