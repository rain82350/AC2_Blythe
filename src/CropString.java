import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
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

	CropString(int sp, int choice, int pixel) {
		this.setSubString(sp, choice);
		this.setPixelBinary(pixel);
		this.setResultPixel();
	}

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
				if (this.getGrayPixel(i, j) == 0) {
				} else {
					this.pixelArrList.add(this.getGrayPixel(i, j));
				}
			}
		}
		System.out.println("PixelArrList size: " + this.pixelArrList.size());
		return this.pixelArrList;
	}

	public void Embed(ArrayList<Integer> sp) {

		// Count coverimage pixel valu
		int count = 0;
		// Count watermark value
		int count2 = 0;
		int b = 0;
		// 每7個pixel為一個block，7 個 temp value
		int[] a = new int[7];
		// 7個temp value轉成binary形式
		String[] aa = new String[7];
		StringBuffer[] buffer = new StringBuffer[7];
		String waterValue = new String();
		String[] w = new String[8];
		//Result pixel value
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

			// 取watermark value
			waterValue = Integer.toBinaryString(sp.get(count2));
			// Produce waterValue substring
			aa[0] = aa[0].substring(0, 8).concat(waterValue.charAt(0)+"");
			aa[1] = aa[1].substring(0, 8).concat(waterValue.charAt(1)+"");
			aa[2] = aa[2].substring(0, 8).concat(waterValue.charAt(2)+"");
			aa[3] = aa[3].substring(0, 8).concat(waterValue.charAt(3)+"");
			aa[4] = aa[4].substring(0, 8).concat(waterValue.charAt(4)+"");
			aa[5] = aa[5].substring(0, 7).concat(waterValue.charAt(5)+"").concat(str);
			aa[6] = aa[6].substring(0, 7).concat(waterValue.charAt(6)+"").concat(str);
			
			this.embedPixel.add(Integer.valueOf(aa[0]));
			this.embedPixel.add(Integer.valueOf(aa[1]));
			this.embedPixel.add(Integer.valueOf(aa[2]));
			this.embedPixel.add(Integer.valueOf(aa[3]));
			this.embedPixel.add(Integer.valueOf(aa[4]));
			this.embedPixel.add(Integer.valueOf(aa[5]));
			this.embedPixel.add(Integer.valueOf(aa[6]));
			
		}

	}

	private void setSubString(int sp, int choice) {
		this.sp = Integer.toBinaryString(sp);
		strLen = this.sp.length();
		if (this.sp.length() < 8) {

			while (strLen < 8) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(this.sp);// 左補0
				this.sp = sb.toString();
				strLen = this.sp.length();
			}
			// System.out.println("sp"+ this.sp);
		}

		switch (choice) {
		case 1:
			this.sp = this.sp.charAt(0) + "";
			break;
		case 2:
			this.sp = this.sp.charAt(1) + "";
			break;
		case 3:
			this.sp = this.sp.charAt(2) + "";
			break;
		case 4:
			this.sp = this.sp.charAt(3) + "";
			break;
		case 5:
			this.sp = this.sp.charAt(4) + "";
			break;
		case 6:
			this.sp = this.sp.charAt(5) + "";
			break;
		case 7:
			this.sp = this.sp.charAt(6) + "";
			break;
		default:
			System.out.println("Error choice");
			break;
		}
	}

	public String getSubString() {
		return this.subString;
	}

	private void setPixelBinary(int pixel) {
		this.pixelBinary = Integer.toBinaryString(pixel);
		strLen = this.pixelBinary.length();
		if (this.pixelBinary.length() < 8) {
			// System.out.println("sp"+ this.sp);
			while (this.pixelBinary.length() < 8) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(this.pixelBinary);// 左補0

				this.pixelBinary = sb.toString();
				strLen = this.pixelBinary.length();
			}

		}

	}

	public String getRGBBinary() {
		return this.pixelBinary;
	}

	private void setResultPixel() {
		this.resultPixel = Integer.valueOf(
				this.pixelBinary
						.substring(0, 5)
						.concat(this.sp)
						.concat(this.pixelBinary.substring(this.pixelBinary
								.length() - 1)), 2);
	}

	public int getResultPixel() {
		return this.resultPixel;
	}

}
