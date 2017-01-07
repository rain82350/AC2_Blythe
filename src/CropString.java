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
	
	CropString(File imageFile){
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
	
	public void Embed(ArrayList<Integer> sp){
		
		
		
	}

	private void setSubString(int sp, int choice) {
		this.sp = Integer.toBinaryString(sp);
		strLen = this.sp.length(); 
		if (this.sp.length() < 8){
			
			while (strLen < 8) {
	            StringBuffer sb = new StringBuffer();
	            sb.append("0").append(this.sp);// ¥ª¸É0
	            this.sp = sb.toString();
	            strLen = this.sp.length();
	        }
			//System.out.println("sp"+		this.sp);
		}
		
		switch (choice) {
		case 1:
			this.sp = this.sp.charAt(0)+"" ;  
			break;
		case 2:
			this.sp = this.sp.charAt(1)+"";
			break;
		case 3:
			this.sp = this.sp.charAt(2)+"";
			break;
		case 4:
			this.sp = this.sp.charAt(3)+"";
			break;
		case 5:
			this.sp = this.sp.charAt(4)+"";
			break;
		case 6:
			this.sp = this.sp.charAt(5)+"";
			break;
		case 7:
			this.sp = this.sp.charAt(6)+"";
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
		if (this.pixelBinary.length() < 8){
			//System.out.println("sp"+		this.sp);
			while (this.pixelBinary.length() < 8) {
	            StringBuffer sb = new StringBuffer();
	            sb.append("0").append(this.pixelBinary);// ¥ª¸É0
	            
	            this.pixelBinary = sb.toString();
	            strLen = this.pixelBinary.length();
	        }
			
		}
		
	}

	public String getRGBBinary() {
		return this.pixelBinary;
	}
	
	private void setResultPixel(){
		this.resultPixel =Integer.valueOf(this.pixelBinary.substring(0,5).concat(this.sp).concat(this.pixelBinary.substring(this.pixelBinary.length()-1)),2);
	}
	
	public int getResultPixel(){
		return this.resultPixel;
	}

}
