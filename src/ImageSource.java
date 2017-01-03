import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageSource {

	private BufferedImage image;
	private int height;
	private int width;
	private int red;
	private int green;
	private int blue;
	private int avgPixel;
	private Raster tmpRas = null;

	// Constructor
	ImageSource() {
		this.image = null;
	}

	//Read RGB Image (24bit)
	ImageSource(File imageFile, int t) {

		try {
			this.image = ImageIO.read(imageFile);
			this.image = adjustImage(this.image, t);

			this.height = this.image.getHeight();
			this.width = this.image.getWidth();
			red = 0;
			green = 0;
			blue = 0;

			// System.out.println("Input OK! ");

		} catch (IOException e) {

			System.out.println(" Cannot find the file! ");
			e.printStackTrace();
		}

	}
	
	//Read Gray Image (8bit)
	ImageSource(File imageFile, int t, int b){
		
		try {
			this.image = ImageIO.read(imageFile);
			tmpRas=this.image.getData();
			this.image = adjustImage(this.image, t);

			this.height = this.image.getHeight();
			this.width = this.image.getWidth();
			

		} catch (IOException e) {

			System.out.println(" Cannot find the file! ");
			e.printStackTrace();
		}
		
	}

	private BufferedImage adjustImage(BufferedImage image, int t) {
		if (image.getWidth() % t != 0) {
			int mod = image.getWidth() % t + 1;

			image = new BufferedImage(this.image.getWidth() + mod,
					this.image.getHeight(), BufferedImage.TYPE_INT_RGB);

			Graphics graphics = image.createGraphics();
			graphics.drawImage(this.image, 0, 0, this.image.getWidth() + mod,
					this.image.getHeight(), null);

			// for (int i = 0; i < this.image.getHeight(); i++) {
			// for (int j = 0; j < mod; j++)
			// image.setRGB(this.image.getWidth() + j, i,
			// this.image.getRGB(i, this.image.getWidth() - 1));
			// }

			try {
				ImageIO.write(image, "png", new File("test.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return image;
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
		this.avgPixel = (this.getRed(i, j) + this.getGreen(i, j) + this.getBlue(i, j)) / 3;
		return avgPixel;

	}
	
	public int getGrayPixel(int i, int j){
		
		tmpRas=this.image.getData();
		return tmpRas.getSample(j, i, 0);
	}
}
