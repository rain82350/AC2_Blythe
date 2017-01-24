import java.io.File;


public class StackMain {

	static File subImage1 = new File("StegoImage1_test.png");
	static File subImage2 = new File("StegoImage2.png");
	static File subImage3 = new File("StegoImage3.png");
	static File subImage4 = new File("StegoImage4.png");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		StackImage stacktest = new StackImage(subImage1, subImage2);
		
	}

}
