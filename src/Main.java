import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Main {

	final static public int N = 4;
	final static public int T = 2;
	// File secretInput = new File("lena_gray_256.png");
	static File coverInput1 = new File("lena_gray.png");
	static File coverInput2 = new File("peppers_gray.png");
	static File coverInput3 = new File("jetplane.png");
	static File coverInput4 = new File("boat.png");
	static File subSecretInput1 = new File("stack24.png");
	static File subSecretInput2 = new File("Sub34.png");
	static File subSecretInput3 = new File("Sub44.png");

	// ImageSource secret = new ImageSource(secretInput, T, 0);
	//static ImageSource cover1 = new ImageSource(coverInput1, T, 0);
	//static ImageSource secret1 = new ImageSource(subSecretInput1, T, 0);
	//static BufferedImage coverImage = new BufferedImage(cover1.getHeight(),
	//		cover1.getWidth(), BufferedImage.TYPE_INT_RGB);

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		
		//System.out.println("S_Height: " + secret1.getHeight() + ", S_Width: "
		//		+ secret1.getWidth());
		//System.out.println("C1_Height: " + cover1.getHeight() + ", C1_Width: "
		//		+ cover1.getWidth());

		// 計算secret的pixel值(要分配到每個CoverImage的)

		GenWaterBit24 waterbit24 = new GenWaterBit24(subSecretInput1, T, 0);
		GenWaterBit34 waterbit34 = new GenWaterBit34(subSecretInput2, T, 0);
		GenWaterBit44 waterbit44 = new GenWaterBit44(subSecretInput3, T, 0);
		//System.out.println("(2,4) waterValu: "+waterbit24.getWatermark24_1().size());
		//System.out.println("(2,4) waterValu: "+waterbit34.getWatermark34_1().size());
		//System.out.println("(2,4) waterValu: "+waterbit44.getWatermark44_1().size());
		
		
		// Temp watermark value
		ArrayList<Integer> waterValue1 = new ArrayList<Integer>();
		ArrayList<Integer> waterValue2 = new ArrayList<Integer>();
		ArrayList<Integer> waterValue3 = new ArrayList<Integer>();
		ArrayList<Integer> waterValue4 = new ArrayList<Integer>();
		// For Cover image 1
		waterValue1 = waterbit24.getWatermark24_1();
		waterValue1.addAll(waterbit34.getWatermark34_1());
		waterValue1.addAll(waterbit44.getWatermark44_1());
		System.out.println("WaterValue1: "+waterValue1.size());
		
		// For Cover image 2
		waterValue2 = waterbit24.getWatermark24_2();
		waterValue2.addAll(waterbit34.getWatermark34_2());
		waterValue2.addAll(waterbit44.getWatermark44_2());
		System.out.println("WaterValue2: "+waterValue2.size());
		
		// For Cover image 3
		waterValue3 = waterbit24.getWatermark24_3();
		waterValue3.addAll(waterbit34.getWatermark34_3());
		waterValue3.addAll(waterbit44.getWatermark44_3());
		System.out.println("WaterValue3: "+waterValue3.size());
		
		// For Cover image 4
		waterValue4 = waterbit24.getWatermark24_4();
		waterValue4.addAll(waterbit34.getWatermark34_4());
		waterValue4.addAll(waterbit44.getWatermark44_4());
		System.out.println("WaterValue4: "+waterValue4.size());

		System.out.println("Watermark Value have been generated OK!! :D");
		// Produce dynamic embedding pixel value
		// For Cover image 1
		CropString member1 = new CropString(coverInput1);
		member1.Embed(waterValue1);
		// For Cover image 2
		CropString member2 = new CropString(coverInput2);
		member2.Embed(waterValue2);
		// For Cover image 3
		CropString member3 = new CropString(coverInput3);
		member3.Embed(waterValue3);
		// For Cover image 4
		CropString member4 = new CropString(coverInput4);
		member4.Embed(waterValue4);
		

	}
	/*
	 * public void Embedded() { int c1_rgb[] = new int[4]; StringProcedure c1[]
	 * = new StringProcedure[4]; int count = 0; ii = 0; flag: { for (int i = 0;
	 * i < cover1.getHeight(); i++) { for (int j = 0; j < cover1.getWidth();
	 * j++) { for (int k = 0; k < 4; k++) { c1_rgb[k] = cover1.getGrayPixel(i,
	 * j); c1[k] = new StringProcedure(sp[ii], k + 1, c1_rgb[k]);
	 * 
	 * System.out.println(c1[k].getResultPixel()); coverImage.setRGB( j, i,
	 * (c1[k].getResultPixel() << 16) | (c1[k].getResultPixel() << 8) |
	 * (c1[k].getResultPixel()));
	 * 
	 * if ((j + 1) % 4 != 0) { j++; } count++; if (count > 3) {
	 * System.out.println("Next block!"); count = 0; }
	 * 
	 * }
	 * 
	 * ii++; if (ii > (secret1.getHeight() * (secret1.getWidth() / T)) - 1) { //
	 * System.out.println("break"); break flag; } } } } try { for (int i =
	 * coverImage.getHeight() / 2; i < coverImage.getHeight(); i++) { for (int j
	 * = 0; j < coverImage.getWidth(); j++) { coverImage.setRGB( j, i,
	 * (cover1.getGrayPixel(i, j) << 16) | (cover1.getGrayPixel(i, j) << 8) |
	 * (cover1.getGrayPixel(i, j))); } } File ls = new File("StegoImage1.png");
	 * ImageIO.write(coverImage, "png", ls); coverImage.flush(); } catch
	 * (IOException e) { e.printStackTrace(); }
	 * 
	 * }
	 */

}
