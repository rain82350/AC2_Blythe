import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class CropString {

	private BufferedImage image;
	private int height;
	private int width;
	private Raster tmpRas = null;
	ArrayList<Integer> sp24 = new ArrayList<Integer>();
	ArrayList<Integer> sp34 = new ArrayList<Integer>();
	ArrayList<Integer> sp44 = new ArrayList<Integer>();
	ArrayList<Integer> pixelArrList = new ArrayList<Integer>();
	ArrayList<Integer> embedPixel;

	private String subString;
	private String sp;
	private String pixelBinary;
	private int resultPixel;
	private int strLen = 0;

	CropString(File imageFile) {
		try {
			this.image = ImageIO.read(imageFile);
			tmpRas = this.image.getData();
			this.height = this.image.getHeight();
			this.width = this.image.getWidth();
			this.arr2ArrList();

		} catch (IOException e) {
			System.out.println(" Cannot find the file! ");
			e.printStackTrace();
		}
	}

	public int getGrayPixel(int i, int j) {

		tmpRas = this.image.getData();
		return tmpRas.getSample(j, i, 0);
	}

	private ArrayList<Integer> arr2ArrList() {

		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				// System.out.println(this.getGrayPixel(i, j));
				/*
				 * if (this.getGrayPixel(i, j) == 0) { } else {
				 * this.pixelArrList.add(this.getGrayPixel(i, j)); }
				 */
				this.pixelArrList.add(this.getGrayPixel(i, j));
			}
		}
		System.out.println("PixelArrList size: " + this.pixelArrList.size());
		return this.pixelArrList;
	}

	public ArrayList<Integer> Embed(ArrayList<Integer> sp)
			throws NoSuchAlgorithmException {

		if (sp == null)
			System.out.println("fuck");

		// Count coverimage pixel valu
		int count = 0;
		// Count watermark value
		int count2 = 0;
		int b = 0;
		// –7pixelblock7  temp value
		int[] a = new int[7];
		// 7temp value锣ΘbinaryΑ
		String[] aa = new String[7];
		StringBuffer[] buffer = new StringBuffer[7];
		String waterValue = new String();
		String[] w = new String[8];
		int[] strLen = new int[8];
		// Check bit
		String aut = new String();
		// Result pixel value
		this.embedPixel = new ArrayList<Integer>();

		while (count < (512 * 512) && count2 < 35499) {

			// seven pixels a block
			b = 0;
			a[b] = this.pixelArrList.get(count++);
			aa[b] = Integer.toBinaryString(a[b]);
			b++;
			a[b] = this.pixelArrList.get(count++);
			aa[b] = Integer.toBinaryString(a[b]);
			b++;
			a[b] = this.pixelArrList.get(count++);
			aa[b] = Integer.toBinaryString(a[b]);
			b++;
			a[b] = this.pixelArrList.get(count++);
			aa[b] = Integer.toBinaryString(a[b]);
			b++;
			a[b] = this.pixelArrList.get(count++);
			aa[b] = Integer.toBinaryString(a[b]);
			b++;
			a[b] = this.pixelArrList.get(count++);
			aa[b] = Integer.toBinaryString(a[b]);
			b++;
			a[b] = this.pixelArrList.get(count++);
			aa[b] = Integer.toBinaryString(a[b]);

			// Check String length
			strLen[0] = aa[0].length();
			strLen[1] = aa[1].length();
			strLen[2] = aa[2].length();
			strLen[3] = aa[3].length();
			strLen[4] = aa[4].length();
			strLen[5] = aa[5].length();
			strLen[6] = aa[6].length();
			for (int i = 0; i < 7; i++) {
				if (aa[i].length() < 8) {

					while (aa[i].length() < 8) {
						StringBuffer sb = new StringBuffer();
						sb.append("0").append(aa[i]);// オ干0
						// sb.append(str).append("0");//干0
						aa[i] = sb.toString();
						strLen[i] = aa[i].length();
					}
					// System.out.println("sp"+ this.sp);
				}
			}
			// watermark value

			waterValue = Integer.toBinaryString(sp.get(count2));
			if (waterValue.length() < 8) {

				while (waterValue.length() < 8) {
					StringBuffer sb = new StringBuffer();
					sb.append("0").append(waterValue);// オ干0
					// sb.append(str).append("0");//干0
					waterValue = sb.toString();
					strLen[7] = waterValue.length();
				}
				// System.out.println("sp"+ this.sp);
			}

			// System.out.println("aa[], sp OK !");
			if (count2 < 35498) {
				aut.concat(Integer.toBinaryString(sp.get(count2)))
						.concat(Integer.toBinaryString(sp.get(count2 + 1)))
						.concat(aa[0].substring(0, 7))
						.concat(aa[1].substring(0, 7))
						.concat(aa[2].substring(0, 7))
						.concat(aa[3].substring(0, 7))
						.concat(aa[4].substring(0, 7))
						.concat(aa[5].substring(0, 6))
						.concat(aa[6].substring(0, 6))
						.concat(Integer.toBinaryString(count2));

			} else {
				aut.concat(Integer.toBinaryString(sp.get(count2)))
				.concat(Integer.toBinaryString(sp.get(count2 )))
				.concat(aa[0].substring(0, 7))
				.concat(aa[1].substring(0, 7))
				.concat(aa[2].substring(0, 7))
				.concat(aa[3].substring(0, 7))
				.concat(aa[4].substring(0, 7))
				.concat(aa[5].substring(0, 6))
				.concat(aa[6].substring(0, 6))
				.concat(Integer.toBinaryString(count2));
			}

			if (count<35498){
			// Calculate Aut(check bit)
			aut.concat(Integer.toBinaryString(sp.get(count2)))
					.concat(Integer.toBinaryString(sp.get(count2 + 1)))
					.concat(aa[0].substring(0, 7))
					.concat(aa[1].substring(0, 7))
					.concat(aa[2].substring(0, 7))
					.concat(aa[3].substring(0, 7))
					.concat(aa[4].substring(0, 7))
					.concat(aa[5].substring(0, 6))
					.concat(aa[6].substring(0, 6))
					.concat(Integer.toBinaryString(count2));
			}else{
				aut.concat(Integer.toBinaryString(sp.get(count2)))
				.concat(Integer.toBinaryString(sp.get(count2)))
				.concat(aa[0].substring(0, 7))
				.concat(aa[1].substring(0, 7))
				.concat(aa[2].substring(0, 7))
				.concat(aa[3].substring(0, 7))
				.concat(aa[4].substring(0, 7))
				.concat(aa[5].substring(0, 6))
				.concat(aa[6].substring(0, 6))
				.concat(Integer.toBinaryString(count2));
			}
			// aut = this.hash(aut);

			// Produce waterValue substring[ 1-5 pixel are embedded 1 bit][6-7
			// pixel are embedded 2 bits.]
			aa[0] = aa[0].substring(0, 7).concat(waterValue.charAt(0) + "");
			aa[1] = aa[1].substring(0, 7).concat(waterValue.charAt(1) + "");
			aa[2] = aa[2].substring(0, 7).concat(waterValue.charAt(2) + "");
			aa[3] = aa[3].substring(0, 7).concat(waterValue.charAt(3) + "");
			aa[4] = aa[4].substring(0, 7).concat(waterValue.charAt(4) + "");
			aa[5] = aa[5].substring(0, 6).concat(waterValue.charAt(5) + "")
					.concat(waterValue.charAt(7) + "");
			aa[6] = aa[6].substring(0, 6).concat(waterValue.charAt(6) + "")
					.concat(aut);
			// .concat(str)

			this.embedPixel.add(Integer.valueOf(aa[0]));
			this.embedPixel.add(Integer.valueOf(aa[1]));
			this.embedPixel.add(Integer.valueOf(aa[2]));
			this.embedPixel.add(Integer.valueOf(aa[3]));
			this.embedPixel.add(Integer.valueOf(aa[4]));
			this.embedPixel.add(Integer.valueOf(aa[5]));
			this.embedPixel.add(Integer.valueOf(aa[6]));

			count2++;
			// System.out.println("Nest round! ");

		}

		System.out.println("Count2: " + count2);
		System.out.println("Dynamic embedding has been completed !");
		System.out.println(this.embedPixel.size());
		return this.embedPixel;

	}

	public ArrayList<Integer> getEmbedPixel() {
		return this.embedPixel;
	}

	private String hash(String s) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(s.getBytes());
		byte byteData[] = md.digest();
		String a = new String();
		int b = 0;
		String c = new String();

		// convert the byte to hex format method
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		a = sb.charAt(0) + "";
		b = Integer.valueOf(a);
		c = Integer.toBinaryString(b);
		// System.out.println(b);
		return c.charAt(0) + "";
	}

}
