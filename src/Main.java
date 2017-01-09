import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
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
	static File coverInput1 = new File("peppers_gray.png");
//	static File coverInput2 = new File("peppers_gray.png");
//	static File coverInput3 = new File("jetplane.png");
//	static File coverInput4 = new File("boat.png");
	static File subSecretInput1 = new File("stack24.png");
	static File subSecretInput2 = new File("Sub34.png");
	static File subSecretInput3 = new File("Sub44.png");

	static int[] Pixel1;
	static int[] stegoPixel1 = new int[512 * 512];

	// static BufferedImage cover1 = null;

	// Stego image

	// ImageSource secret = new ImageSource(secretInput, T, 0);
	static ImageSource stego1 = new ImageSource(coverInput1, T, 0);
	// static ImageSource secret1 = new ImageSource(subSecretInput1, T, 0);

	static BufferedImage stegoImage1 = new BufferedImage(stego1.getHeight(),
			stego1.getWidth(), BufferedImage.TYPE_INT_RGB);

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub

		// System.out.println("S_Height: " + secret1.getHeight() + ", S_Width: "
		// + secret1.getWidth());
		// System.out.println("C1_Height: " + cover1.getHeight() +
		// ", C1_Width: "
		// + cover1.getWidth());

		// 計算secret的pixel值(要分配到每個CoverImage的)

		GenWaterBit24 waterbit24 = new GenWaterBit24(subSecretInput1, T, 0);
		GenWaterBit34 waterbit34 = new GenWaterBit34(subSecretInput2, T, 0);
		GenWaterBit44 waterbit44 = new GenWaterBit44(subSecretInput3, T, 0);
		// System.out.println("(2,4) waterValu: "+waterbit24.getWatermark24_1().size());
		// System.out.println("(2,4) waterValu: "+waterbit34.getWatermark34_1().size());
		// System.out.println("(2,4) waterValu: "+waterbit44.getWatermark44_1().size());

		// Temp watermark value
		ArrayList<Integer> waterValue1 = new ArrayList<Integer>();
		// ArrayList<Integer> waterValue2 = new ArrayList<Integer>();
		// ArrayList<Integer> waterValue3 = new ArrayList<Integer>();
		// ArrayList<Integer> waterValue4 = new ArrayList<Integer>();
		// After dynamic embeding pixel value
		ArrayList<Integer> coverValue1 = new ArrayList<Integer>();
		ArrayList<Integer> resultValue1 = new ArrayList<Integer>();
		// ArrayList<Integer> resultValue2 = new ArrayList<Integer>();
		// ArrayList<Integer> resultValue3 = new ArrayList<Integer>();
		// ArrayList<Integer> resultValue4 = new ArrayList<Integer>();
		// For Cover image 1

		waterValue1 = waterbit24.getWatermark24_2();
		waterValue1.addAll(waterbit34.getWatermark34_2());
		waterValue1.addAll(waterbit44.getWatermark44_2());

		// BufferedWriter fw = null;
		// File file = new File("waterValue0109.txt");
		// for (int ii = 0; ii < 25942; ii++) {
		// try {
		// String charSp = Integer.toString(waterValue1.get(ii));
		// fw = new BufferedWriter(new OutputStreamWriter(
		// new FileOutputStream(file, true), "UTF-8"));
		// fw.append(charSp);
		// fw.append(", ");
		// fw.flush();
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		//
		// System.out.println("WaterValue1: " + waterValue1.size());
		/*
		 * // For Cover image 2 waterValue2 = waterbit24.getWatermark24_2();
		 * waterValue2.addAll(waterbit34.getWatermark34_2());
		 * waterValue2.addAll(waterbit44.getWatermark44_2());
		 * System.out.println("WaterValue2: "+waterValue2.size());
		 * 
		 * // For Cover image 3 waterValue3 = waterbit24.getWatermark24_3();
		 * waterValue3.addAll(waterbit34.getWatermark34_3());
		 * waterValue3.addAll(waterbit44.getWatermark44_3());
		 * System.out.println("WaterValue3: "+waterValue3.size());
		 * 
		 * // For Cover image 4 waterValue4 = waterbit24.getWatermark24_4();
		 * waterValue4.addAll(waterbit34.getWatermark34_4());
		 * waterValue4.addAll(waterbit44.getWatermark44_4());
		 * System.out.println("WaterValue4: "+waterValue4.size());
		 */
		System.out.println("Watermark Value have been generated OK!! :D");
		// Produce dynamic embedding pixel value

		// For Cover image 1

		CropString member1 = new CropString(coverInput1);
		member1.Embed(waterValue1);
		coverValue1 = member1.getArrList();
		
		System.out.println("coverValue1 size: " + coverValue1.size());
		resultValue1 = member1.getEmbedPixel();
		
		for (int i = 259420; i < 262144; i++) {
			resultValue1.add(coverValue1.get(i));
		}
		
		stegoPixel1 = arraylist2array(resultValue1);
		
		int ctr =0;
		for (int i = 0; i < 512; i++) {
			for (int j = 0; j < 512; j++) {
				//if(String.valueOf(stegoPixel1[ctr])!=null)
				stegoImage1.setRGB(j, i, (0xff000000 | (stegoPixel1[ctr] << 16) | (stegoPixel1[ctr] <<  8 | stegoPixel1[ctr]) ));
			
				ctr++;
			}

		}
		
		
		
		// stegoImage1 = (BufferedImage) getImageFromArray(stegoPixel1, 512,
		// 512);
		try {
			File ls = new File("StegoImage2.png");
			ImageIO.write(stegoImage1, "png", ls);
			stegoImage1.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Stego image has been produced.");
		// System.out.println("Modefied ResultValue1 size: "+
		// resultValue1.size());

		// BufferedWriter fw = null;
		// File file = new File("resultValue1_0109.txt");
		// for (int ii = 0; ii < 25942; ii++) {
		// try {
		// String charSp = Integer.toString(resultValue1.get(ii));
		// fw = new BufferedWriter(new OutputStreamWriter(
		// new FileOutputStream(file, true), "UTF-8"));
		// fw.append(charSp);
		// fw.append(", ");
		// fw.flush();
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// System.out.println("resultValue: " + resultValue1);

		/*
		 * // For Cover image 2 CropString member2 = new
		 * CropString(coverInput2); member2.Embed(waterValue2); // For Cover
		 * image 3 CropString member3 = new CropString(coverInput3);
		 * member3.Embed(waterValue3); // For Cover image 4 CropString member4 =
		 * new CropString(coverInput4); member4.Embed(waterValue4);
		 */

		// int ii = 0;
		// try {
		//
		// for (int i = 0; i < 507; i++) {
		// for (int j = 0; j < 512; j++) {
		//
		// stegoImage1.setRGB(j, i,
		// (resultValue1.get(ii * i + j) << 16)
		// | (resultValue1.get(ii * i + j) << 8)
		// | (resultValue1.get(ii * i + j)));
		// if (resultValue1.get(ii * i + j) < 128) {
		// stegoImage1.setRGB(j, i, Color.BLACK.getRGB());
		// } else {
		// stegoImage1.setRGB(j, i, Color.WHITE.getRGB());
		// }
		//
		// if (i == 507 && j == 348)
		// break;
		// }
		// ii++;
		// }
		// File ls = new File("StegoImage1.png");
		// ImageIO.write(stegoImage1, "png", ls);
		// stegoImage1.flush();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// System.out.println("Stego image has been produced.");
		//
	}

	public static int[] arraylist2array(ArrayList<Integer> sp) {
		Pixel1 = new int[sp.size()];
		for (int i = 0; i < sp.size(); i++) {
			Pixel1[i] = sp.get(i);
		}
		return Pixel1;
	}

	// public static Image getImageFromArray(int[] pixels, int width, int
	// height) {
	// BufferedImage image = new BufferedImage(width, height,
	// BufferedImage.TYPE_INT_RGB);
	// WritableRaster raster = (WritableRaster) image.getData();
	// raster.setPixels(0, 0, width, height, pixels);
	// return image;
	// }

}
