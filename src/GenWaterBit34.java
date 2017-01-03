import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GenWaterBit34 {

	final static public int T = 2;
	private BufferedImage image;
	private int height;
	private int width;
	private int red;
	private int green;
	private int blue;
	private int avgPixel;
	private Raster tmpRas = null;
	private int[] sp = null;
	ArrayList<Integer> sp1;
	ArrayList<Integer> sp2;
	ArrayList<Integer> sp3;
	ArrayList<Integer> sp4;

	private BufferedWriter fw = null;

	// Constructor
	GenWaterBit34() {
		this.image = null;
	}

	// Read RGB Image (24bit)
	GenWaterBit34(File imageFile, int t) {

		try {
			this.image = ImageIO.read(imageFile);
			this.height = this.image.getHeight();
			this.width = this.image.getWidth();
			red = 0;
			green = 0;
			blue = 0;
			
			this.genWatermark34_1();
			this.genWatermark34_2();
			this.genWatermark34_3();
			this.genWatermark34_4();

			// System.out.println("Input OK! ");

		} catch (IOException e) {
			System.out.println(" Cannot find the file! ");
			e.printStackTrace();
		}
	}

	// Read Gray Image (8bit)
	GenWaterBit34(File imageFile, int t, int b) {

		try {
			this.image = ImageIO.read(imageFile);
			tmpRas = this.image.getData();
			this.height = this.image.getHeight();
			this.width = this.image.getWidth();
			
			this.genWatermark34_1();
			this.genWatermark34_2();
			this.genWatermark34_3();
			this.genWatermark34_4();

		} catch (IOException e) {
			System.out.println(" Cannot find the file! ");
			e.printStackTrace();
		}
	}

	public void setHeight(int imageHeight) {

		this.height = imageHeight;
	}

	public void setWidth(int imageWidth) {

		this.width = imageWidth;
	}

	public int getHeight() {

		return this.height;
	}

	public int getWidth() {

		return this.width;
	}

	private int getRed(int i, int j) {
		this.red = (this.image.getRGB(j, i) & 0xff0000) >> 16;
		return this.red;
	}

	private int getGreen(int i, int j) {
		this.green = (this.image.getRGB(j, i) & 0x00ff00) >> 8;
		return this.green;
	}

	private int getBlue(int i, int j) {
		this.blue = (this.image.getRGB(j, i) & 0x0000ff);
		return this.blue;
	}

	public int getAvgPixel(int i, int j) {
		this.avgPixel = (this.getRed(i, j) + this.getGreen(i, j) + this
				.getBlue(i, j)) / 3;
		return avgPixel;
	}

	public int getGrayPixel(int i, int j) {

		tmpRas = this.image.getData();
		return tmpRas.getSample(j, i, 0);
	}

	public ArrayList<Integer> getWatermark34_1() {
		return this.sp1;
	}

	public ArrayList<Integer> getWatermark34_2() {
		return this.sp2;
	}

	public ArrayList<Integer> getWatermark34_3() {
		return this.sp3;
	}

	public ArrayList<Integer> getWatermark34_4() {
		return this.sp4;
	}

	public void genWatermark34_1() {

		int ii = 0;
		this.sp1 = new ArrayList<Integer>();
		int tempValue = 0;
		int count = 0;
		int[] a = new int[3];
		int b = 0;
		// this.sp1 = new int[this.image.getHeight()
		// * ((this.image.getWidth() / T) + (this.image.getWidth()))];
		File file = new File("watermarkBit34_1.txt");
		File testfile = new File("test34_1.txt");
		// template value a0, a1, a2

		while (count <= (256 * 256) - 3) {

			b = 0;
			a[b] = this.getGrayPixel(count / 256, count % 256);
			b++;
			a[b] = this.getGrayPixel((count + 1) / 256, (count + 1) % 256);
			b++;
			a[b] = this.getGrayPixel((count + 2) / 256, (count + 2) % 256);

			tempValue = (a[0] + a[1] + a[2]) % 251;
			this.sp1.add(tempValue);

			try {
				String charSp = Integer.toString(sp1.get(ii));
				fw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file, true), "UTF-8"));
				fw.append(charSp);
				fw.append(", ");
				fw.flush();

			} catch (Exception e) {
				e.printStackTrace();
			}

			count = count + 3;
			ii++;
		}

		// special case (255, 255)
		tempValue = this.getGrayPixel(255, 255);
		this.sp1.add(tempValue);

		if (fw != null) {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void genWatermark34_2() {

		int ii = 0;
		this.sp2 = new ArrayList<Integer>();
		int count = 0;
		int tempValue = 0;

		File file = new File("watermarkBit34_2.txt");
		File testfile = new File("test34_2.txt");
		// template value a0, a1, a2
		int[] a = new int[3];
		int b = 0;
		// secret value
		int s1 = 2;

		while (count <= (256 * 256) - 3) {

			b = 0;
			a[b] = this.getGrayPixel(count / 256, count % 256);
			b++;
			a[b] = this.getGrayPixel((count + 1) / 256, (count + 1) % 256);
			b++;
			a[b] = this.getGrayPixel((count + 2) / 256, (count + 2) % 256);

			tempValue = (int) ((a[0] + (a[1] * s1) + (a[2] * (Math.pow(s1, 2)))) % 251);
			this.sp2.add(tempValue);

			try {
				String charSp = Integer.toString(sp2.get(ii));
				fw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file, true), "UTF-8"));
				fw.append(charSp);
				fw.append(", ");
				fw.flush();

			} catch (Exception e) {
				e.printStackTrace();
			}

			count = count + 3;
			ii++;
		}

		// special case (255, 255)
		tempValue = this.getGrayPixel(255, 255);
		this.sp2.add(tempValue);

		if (fw != null) {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void genWatermark34_3() {

		int ii = 0;
		this.sp3 = new ArrayList<Integer>();
		int tempValue = 0;
		int count = 0;

		File file = new File("watermarkBit34_3.txt");
		File testfile = new File("test34_3.txt");
		// template value a0, a1
		int[] a = new int[3];
		int b = 0;
		// secret value
		int s2 = 3;

		while (count <= (256 * 256) - 3) {

			b = 0;
			a[b] = this.getGrayPixel(count / 256, count % 256);
			b++;
			a[b] = this.getGrayPixel((count + 1) / 256, (count + 1) % 256);
			b++;
			a[b] = this.getGrayPixel((count + 2) / 256, (count + 2) % 256);

			tempValue = (int) ((a[0] + (a[1] * s2) + (a[2] * (Math.pow(s2, 2)))) % 251);
			this.sp3.add(tempValue);

			try {
				String charSp = Integer.toString(sp3.get(ii));
				fw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file, true), "UTF-8"));
				fw.append(charSp);
				fw.append(", ");
				fw.flush();

			} catch (Exception e) {
				e.printStackTrace();
			}

			count = count + 3;
			ii++;
		}

		// special case (255, 255)
		tempValue = this.getGrayPixel(255, 255);
		this.sp3.add(tempValue);

		if (fw != null) {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void genWatermark34_4() {

		int ii = 0;
		this.sp4 = new ArrayList<Integer>();
		int tempValue = 0;
		int count = 0;

		File file = new File("watermarkBit34_4.txt");
		File testfile = new File("test34_4.txt");
		// template value a0, a1
		int[] a = new int[3];
		int b = 0;
		// secret value
		int s3 = 4;

		while (count <= (256 * 256) - 3) {

			b = 0;
			a[b] = this.getGrayPixel(count / 256, count % 256);
			b++;
			a[b] = this.getGrayPixel((count + 1) / 256, (count + 1) % 256);
			b++;
			a[b] = this.getGrayPixel((count + 2) / 256, (count + 2) % 256);

			tempValue = (int) ((a[0] + (a[1] * s3) + (a[2] * (Math.pow(s3, 2)))) % 251);
			this.sp4.add(tempValue);

			try {
				String charSp = Integer.toString(sp4.get(ii));
				fw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file, true), "UTF-8"));
				fw.append(charSp);
				fw.append(", ");
				fw.flush();

			} catch (Exception e) {
				e.printStackTrace();
			}

			count = count + 3;
			ii++;
		}

		// special case (255, 255)
		tempValue = this.getGrayPixel(255, 255);
		this.sp4.add(tempValue);

		if (fw != null) {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
