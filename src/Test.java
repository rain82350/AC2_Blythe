import java.io.File;
import java.util.ArrayList;


public class Test {
	
	final static public int T = 2;
	static File subSecretInput1 = new File("stack24.png");
	static File subSecretInput2 = new File("Sub34.png");
	static File subSecretInput3 = new File("Sub44.png");

	public static void main(String[] args) {
		
//		ArrayList<Integer> waterValue1 = new ArrayList<Integer>();
		long startTime = System.currentTimeMillis();
		GenWaterBit24 waterbit24 = new GenWaterBit24(subSecretInput1, T, 0);
		long endTime = System.currentTimeMillis();
		
		System.out.println(endTime-startTime);
//		GenWaterBit34 waterbit34 = new GenWaterBit34(subSecretInput2, T, 0);
//		GenWaterBit44 waterbit44 = new GenWaterBit44(subSecretInput3, T, 0);
//		
//		waterValue1 = waterbit24.getWatermark24_1();
//		waterValue1.addAll(waterbit34.getWatermark34_1());
//		waterValue1.addAll(waterbit44.getWatermark44_1());
//		
//		int count2 = 0;
//		String waterValue = new String();
//		waterValue = Integer.toBinaryString(waterValue1.get(count2));
//		System.out.println(waterValue);

	}

}
