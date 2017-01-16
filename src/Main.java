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
	//According to different cover image to change the input file and change the following method().
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

	static ImageSource stego1 = new ImageSource(coverInput1, T, 0);
	
	static BufferedImage stegoImage1 = new BufferedImage(stego1.getHeight(),
			stego1.getWidth(), BufferedImage.TYPE_INT_RGB);

	public static void main(String[] args) throws NoSuchAlgorithmException {
		

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
		
		// After dynamic embeding pixel value
		ArrayList<Integer> coverValue1 = new ArrayList<Integer>();
		ArrayList<Integer> resultValue1 = new ArrayList<Integer>();
		
		// For Cover image 1

		//將watermarkbit串接起來
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
		
		//將arraylist轉成array，寫入檔案
		stegoPixel1 = arraylist2array(resultValue1);
		
		int ctr =0;
		for (int i = 0; i < 512; i++) {
			for (int j = 0; j < 512; j++) {
				//if(String.valueOf(stegoPixel1[ctr])!=null)
				stegoImage1.setRGB(j, i, (0xff000000 | (stegoPixel1[ctr] << 16) | (stegoPixel1[ctr] <<  8 | stegoPixel1[ctr]) ));
			
				ctr++;
			}

		}
		
		try {
			File ls = new File("StegoImage2.png");
			ImageIO.write(stegoImage1, "png", ls);
			stegoImage1.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Stego image has been produced.");
	
	}

	public static int[] arraylist2array(ArrayList<Integer> sp) {
		Pixel1 = new int[sp.size()];
		for (int i = 0; i < sp.size(); i++) {
			Pixel1[i] = sp.get(i);
		}
		return Pixel1;
	}



}
