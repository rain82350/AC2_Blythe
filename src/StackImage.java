import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class StackImage {

	private BufferedImage image, image2, image3, image4;
	private int height, height2, height3, height4;
	private int width, width2, width3, width4;
	private Raster tmpRas = null;
	ArrayList<Integer> pixelArrList = new ArrayList<Integer>();
	ArrayList<Integer> pixelArrList1 = new ArrayList<Integer>();
	ArrayList<Integer> pixelArrList2 = new ArrayList<Integer>();
	ArrayList<Integer> pixelArrList3 = new ArrayList<Integer>();
	ArrayList<Integer> pixelArrList4 = new ArrayList<Integer>();
	
	//input 2 subimages
	StackImage(File imageFile, File imageFile2) {
		try {
			this.image = ImageIO.read(imageFile);
			tmpRas = this.image.getData();
			this.height = this.image.getHeight();
			this.width = this.image.getWidth();
			//this.arr2ArrList();
			this.image2 = ImageIO.read(imageFile2);
			tmpRas = this.image2.getData();
			this.height2 = this.image2.getHeight();
			this.width2 = this.image2.getWidth();

		} catch (IOException e) {
			System.out.println(" Cannot find the file! ");
			e.printStackTrace();
		}
	}
	//input 3 subimages
	StackImage(File imageFile, File imageFile2, File imageFile3) {
		try {
			this.image = ImageIO.read(imageFile);
			tmpRas = this.image.getData();
			this.height = this.image.getHeight();
			this.width = this.image.getWidth();
			//this.arr2ArrList();
			this.image2 = ImageIO.read(imageFile2);
			tmpRas = this.image2.getData();
			this.height2 = this.image2.getHeight();
			this.width2 = this.image2.getWidth();
			
			this.image3 = ImageIO.read(imageFile3);
			tmpRas = this.image3.getData();
			this.height3 = this.image3.getHeight();
			this.width3 = this.image3.getWidth();

		} catch (IOException e) {
			System.out.println(" Cannot find the file! ");
			e.printStackTrace();
		}
	}
	//input 4 subimages
	StackImage(File imageFile, File imageFile2, File imageFile3, File imageFile4) {
		try {
			this.image = ImageIO.read(imageFile);
			tmpRas = this.image.getData();
			this.height = this.image.getHeight();
			this.width = this.image.getWidth();
			//this.arr2ArrList();
			this.image2 = ImageIO.read(imageFile2);
			tmpRas = this.image2.getData();
			this.height2 = this.image2.getHeight();
			this.width2 = this.image2.getWidth();
			this.image3 = ImageIO.read(imageFile3);
			tmpRas = this.image3.getData();
			this.height3 = this.image3.getHeight();
			this.width3 = this.image3.getWidth();
			this.image4 = ImageIO.read(imageFile4);
			tmpRas = this.image4.getData();
			this.height4 = this.image4.getHeight();
			this.width4 = this.image4.getWidth();

		} catch (IOException e) {
			System.out.println(" Cannot find the file! ");
			e.printStackTrace();
		}
	}

	public int getGrayPixel(int i, int j) {

		tmpRas = this.image.getData();
		return tmpRas.getSample(j, i, 0);
	}
	public int getGrayPixel2(int i, int j) {

		tmpRas = this.image2.getData();
		return tmpRas.getSample(j, i, 0);
	}
	public int getGrayPixel3(int i, int j) {

		tmpRas = this.image3.getData();
		return tmpRas.getSample(j, i, 0);
	}
	public int getGrayPixel4(int i, int j) {

		tmpRas = this.image4.getData();
		return tmpRas.getSample(j, i, 0);
	}

	private ArrayList<Integer> arr2ArrList() {

		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				// System.out.println(this.getGrayPixel(i, j));
				this.pixelArrList.add(this.getGrayPixel(i, j));
			}
		}
		System.out.println("PixelArrList size: " + this.pixelArrList.size());
		return this.pixelArrList;
	}

	public ArrayList<Integer> getArrList() {
		return this.pixelArrList;
	}

	public void stack2Subimage() {
		
		// counter
		int b = 0;
		int b2 = 0;
		int count =0;
		int count2 =0;
		// template array
		int[] a = new int[10];
		int[] a2 = new int[10];
		// template array string pont (decimal -> binary)
		String[] aa = new String[10];
		String[] aa2 = new String[10];
		
		//get pixelvalue from image1 to arraylist
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				// System.out.println(this.getGrayPixel(i, j));
				this.pixelArrList1.add(this.getGrayPixel(i, j));
				this.pixelArrList2.add(this.getGrayPixel2(i, j));
				
				//Do something every ten pixel value 
				if ((this.pixelArrList1.size()%10) == 0 && (this.pixelArrList2.size()%10) == 0){
					//b =0;				
					for (int z=0; z<10; z++ ){
						//get pixel value from subimage1
						a[b] = this.pixelArrList1.get(count++);
						aa[b] = Integer.toBinaryString(a[b]);
						b++;
						//get pixel value from subimage2
						a2[b2] = this.pixelArrList2.get(count2++);
						aa2[b2] = Integer.toBinaryString(a[b2]);
						b2++;
						
					}
					
				}
				else{
					//do nothing
				}
			}
		}

	}

	public void stack3Subimage() {

	}

	public void stack4Subimage() {

	}

}
