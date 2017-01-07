import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.imageio.ImageIO;

public class Main {

	final static public int N = 4;
	final static public int T = 2;
	// File secretInput = new File("lena_gray_256.png");
	static File coverInput = new File("peppers_gray.png");
	static File subSecretInput1 = new File("stack24.png");

	// ImageSource secret = new ImageSource(secretInput, T, 0);
	static ImageSource cover1 = new ImageSource(coverInput, T, 0);
	static ImageSource secret1 = new ImageSource(subSecretInput1, T, 0);

	static BufferedImage coverImage = new BufferedImage(cover1.getHeight(),
			cover1.getWidth(), BufferedImage.TYPE_INT_RGB);

	static int ii = 0;
	static int[] sp = new int[secret1.getHeight()
			* ((secret1.getWidth() / T) + (secret1.getWidth()))];

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("S_Height: " + secret1.getHeight() + ", S_Width: "
				+ secret1.getWidth());
		System.out.println("C1_Height: " + cover1.getHeight() + ", C1_Width: "
				+ cover1.getWidth());

		// 計算secret的pixel值(要分配到每個CoverImage的)
		// waterbit24.getWatermark24_1();
		GenWaterBit44 waterbit44 = new GenWaterBit44(subSecretInput1, T, 0);
		// System.out.println(waterbit34.getWatermark34_1().get(85));
		// System.out.println(waterbit34.getWatermark34_1().size());

		// System.out.println(waterbit44.getWatermark44_1().size());

		// waterbit44.getWatermark44_1();
		// System.out.println();

		// System.out.println("Secret has been produced!");

	}

	public void Embedded() {
		int c1_rgb[] = new int[4];
		StringProcedure c1[] = new StringProcedure[4];
		int count = 0;
		ii = 0;
		flag: {
			for (int i = 0; i < cover1.getHeight(); i++) {
				for (int j = 0; j < cover1.getWidth(); j++) {
					for (int k = 0; k < 4; k++) {
						c1_rgb[k] = cover1.getGrayPixel(i, j);
						c1[k] = new StringProcedure(sp[ii], k + 1, c1_rgb[k]);

						System.out.println(c1[k].getResultPixel());
						coverImage.setRGB(
								j,
								i,
								(c1[k].getResultPixel() << 16)
										| (c1[k].getResultPixel() << 8)
										| (c1[k].getResultPixel()));

						if ((j + 1) % 4 != 0) {
							j++;
						}
						count++;
						if (count > 3) {
							System.out.println("Next block!");
							count = 0;
						}

					}

					ii++;
					if (ii > (secret1.getHeight() * (secret1.getWidth() / T)) - 1) {
						// System.out.println("break");
						break flag;
					}
				}
			}
		}
		try {
			for (int i = coverImage.getHeight() / 2; i < coverImage.getHeight(); i++) {
				for (int j = 0; j < coverImage.getWidth(); j++) {
					coverImage.setRGB(
							j,
							i,
							(cover1.getGrayPixel(i, j) << 16)
									| (cover1.getGrayPixel(i, j) << 8)
									| (cover1.getGrayPixel(i, j)));
				}
			}
			File ls = new File("StegoImage1.png");
			ImageIO.write(coverImage, "png", ls);
			coverImage.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
