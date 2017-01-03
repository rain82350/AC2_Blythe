import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import javax.imageio.ImageIO;

public class GenWaterBit24 {

	final static public int T = 2;
	private BufferedImage image;
	private int height;
	private int width;
	private int red;
	private int green;
	private int blue;
	private int avgPixel;
	private Raster tmpRas = null;

	private BufferedWriter fw = null;
	private int[] sp;

	ArrayList<Integer> sp1;
	ArrayList<Integer> sp2;
	ArrayList<Integer> sp3;
	ArrayList<Integer> sp4;

	/////////// Test for CSV /////////////////
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	//////////////////////////////////////////
	
	
	// Constructor
	GenWaterBit24() {
		this.image = null;
	}

	// Read RGB Image (24bit)
	GenWaterBit24(File imageFile, int t) {

		try {
			this.image = ImageIO.read(imageFile);
			this.height = this.image.getHeight();
			this.width = this.image.getWidth();
			red = 0;
			green = 0;
			blue = 0;

			this.genWatermark24_1();
			this.genWatermark24_2();
			this.genWatermark24_3();
			this.genWatermark24_4();

			// System.out.println("Input OK! ");

		} catch (IOException e) {
			System.out.println(" Cannot find the file! ");
			e.printStackTrace();
		}
	}

	// Read Gray Image (8bit)
	GenWaterBit24(File imageFile, int t, int b) {

		try {
			this.image = ImageIO.read(imageFile);
			tmpRas = this.image.getData();
			this.height = this.image.getHeight();
			this.width = this.image.getWidth();

			///this.genWatermark24_1();
			this.genWatermark24_2();
			//this.genWatermark24_3();
			//this.genWatermark24_4();

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

	public ArrayList<Integer> getWatermark24_1() {
		return this.sp1;
	}

	public ArrayList<Integer> getWatermark24_2() {
		return this.sp2;
	}

	public ArrayList<Integer> getWatermark24_3() {
		return this.sp3;
	}

	public ArrayList<Integer> getWatermark24_4() {
		arraylist2array(this.sp4);
		return this.sp4;
	}

	private int[] arraylist2array(ArrayList<Integer> sp) {
		this.sp = new int[sp.size()];
		for (int i = 0; i < sp.size(); i++) {
			this.sp[i] = sp.get(i);
		}
		return this.sp;
	}

	public void genWatermark24_1() {

		int ii = 0;

		this.sp1 = new ArrayList<Integer>();
		int tempValue = 0;
		File file = new File("watermarkBit24_1.txt");
		File testfile = new File("test24_1.txt");

		for (int i = 0; i < this.image.getHeight(); i++) {
			for (int j = 0; j < this.image.getWidth(); j++) {
				tempValue = 0;
				// 把原本的 j 值暫存起來
				for (int k = 0; k < T; k++) {
					
					tempValue += this.getGrayPixel(i, j);
					if (k + 1 != T) {
						j++;
					}
				}		
				this.sp1.add(tempValue % 251);
				
				try {
					String charSp = Integer.toString(sp1.get(ii));
					fw = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(testfile, true), "UTF-8"));
					fw.append(charSp);
					fw.append(", ");
					fw.flush();

				} catch (Exception e) {
					e.printStackTrace();
				}

				ii++;
			}
		}

		if (fw != null) {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void genWatermark24_2() {

		int ii = 0;

		this.sp2 = new ArrayList<Integer>();
		int tempValue = 0;
		File file = new File("watermarkBit24_2.txt");
		File testfile = new File("test24_2.txt");
		// template value a0, a1
		int a0 = 0;
		int a1 = 0;
		// secret value
		int s1 = 2;
		for (int i = 0; i < this.image.getHeight(); i++) {
			for (int j = 0; j < this.image.getWidth(); j++) {
				// 把原本的 j 值暫存起來
				for (int k = 0; k < T; k++) {
					a0 = this.getGrayPixel(i, j);

					if (k + 1 != T) {
						j++;
						a1 = this.getGrayPixel(i, j);
						tempValue = (a0 + (a1 * s1)) % 251;
						this.sp2.add(tempValue);
						
						/*
						try {
							String charSp = Integer.toString(sp2.get(ii));
							fw = new BufferedWriter(new OutputStreamWriter(
									new FileOutputStream(testfile, true), "UTF-8"));
							fw.append(charSp);
							fw.append(", ");
							fw.flush();

						} catch (Exception e) {
							e.printStackTrace();
						}*/
						
						
						////////////////////////////////////////Test CSV File///////////////////////////////////////
						
						
						String outputFile = "test24_2.csv";
						boolean alreadyExists = new File(outputFile).exists();
						
						try {
							// use FileWriter constructor that specifies open for appending
							CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
							String charSp = Integer.toString(sp2.get(ii));
							
							// if the file didn't already exist then we need to write out the header line
							if (!alreadyExists)
							{
								//csvOutput.write("id");
								//csvOutput.write("name");
								//csvOutput.endRecord();
							}
							// else assume that the file already has the correct header line
							
							// write out a few records
							csvOutput.write(charSp);
							
							csvOutput.endRecord();
							
							
							csvOutput.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						//////////////////////////////////////Test CSV File///////////////////////////////////////

					}
				}
				ii++;
			}
		}
		
		/*
		if (fw != null) {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/

	}

	public void genWatermark24_3() {

		int ii = 0;

		this.sp3 = new ArrayList<Integer>();
		File file = new File("watermarkBit24_3.txt");
		File testfile = new File("test24_3.txt");
		int tempValue = 0;
		// template value a0, a1
		int a0 = 0;
		int a1 = 0;
		// secret value
		int s2 = 3;
		for (int i = 0; i < this.image.getHeight(); i++) {
			for (int j = 0; j < this.image.getWidth(); j++) {
				// 把原本的 j 值暫存起來
				for (int k = 0; k < T; k++) {
					a0 = this.getGrayPixel(i, j);

					if (k + 1 != T) {
						j++;
						a1 = this.getGrayPixel(i, j);
						tempValue = (a0 + (a1 * s2)) % 251;
						this.sp3.add(tempValue);

						try {
							String charSp = Integer.toString(sp3.get(ii));
							fw = new BufferedWriter(new OutputStreamWriter(
									new FileOutputStream(testfile, true), "UTF-8"));
							fw.append(charSp);
							fw.append(", ");
							fw.flush();

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
				ii++;
			}
		}

		if (fw != null) {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void genWatermark24_4() {

		int ii = 0;

		this.sp4 = new ArrayList<Integer>();
		File file = new File("watermarkBit24_4.txt");
		File testfile = new File("test24_4.txt");
		int tempValue = 0;
		// template value a0, a1
		int a0 = 0;
		int a1 = 0;
		// secret value
		int s3 = 4;
		int i = 0, j = 0;

		for (i = 0; i < this.image.getHeight(); i++) {
			for (j = 0; j < this.image.getWidth(); j++) {
				// 把原本的 j 值暫存起來
				for (int k = 0; k < T; k++) {
					a0 = this.getGrayPixel(i, j);

					if (k + 1 != T) {
						j++;
						a1 = this.getGrayPixel(i, j);
						tempValue = (a0 + (a1 * s3)) % 251;
						this.sp4.add(tempValue);

						try {
							String charSp = Integer.toString(sp4.get(ii));
							fw = new BufferedWriter(new OutputStreamWriter(
									new FileOutputStream(testfile, true),
									"UTF-8"));
							fw.append(charSp);
							fw.append(", ");
							fw.flush();

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
				ii++;
			}
		}

		if (fw != null) {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}//end
