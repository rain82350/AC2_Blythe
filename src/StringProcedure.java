public class StringProcedure {

	private String subString;
	private String sp;
	private String rgbBinary;
	private int resultPixel;
	private int strLen = 0;

	StringProcedure(int sp, int choice, int rgb) {
		this.setSubString(sp, choice);
		this.setRGBBinary(rgb);
		this.setResultPixel();
	}

	private void setSubString(int sp, int choice) {
		this.sp = Integer.toBinaryString(sp);
		strLen = this.sp.length(); 
		if (this.sp.length() < 8){
			
			while (this.sp.length() < 8) {
	            StringBuffer sb = new StringBuffer();
	            sb.append("0").append(this.sp);// オ干0
	            // sb.append(str).append("0");//干0
	            this.sp = sb.toString();
	            strLen = this.sp.length();
	        }
			//System.out.println("sp"+		this.sp);
		}
		
		switch (choice) {
		case 1:
			this.sp = this.sp.substring(0, 2);
			break;
		case 2:
			this.sp = this.sp.substring(2, 4);
			break;
		case 3:
			this.sp = this.sp.substring(4, 6);
			break;
		case 4:
			this.sp = this.sp.substring(6, 8);
			break;
		default:
			System.out.println("Error choice");
			break;
		}
	}

	public String getSubString() {
		return this.subString;
	}

	private void setRGBBinary(int rgb) {
		this.rgbBinary = Integer.toBinaryString(rgb);
		strLen = this.rgbBinary.length();
		if (this.rgbBinary.length() < 8){
			//System.out.println("sp"+		this.sp);
			while (this.rgbBinary.length() < 8) {
	            StringBuffer sb = new StringBuffer();
	            sb.append("0").append(this.rgbBinary);// オ干0
	            // sb.append(str).append("0");//干0
	            this.rgbBinary = sb.toString();
	            strLen = this.rgbBinary.length();
	        }
			
		}
		
	}

	public String getRGBBinary() {
		return this.rgbBinary;
	}
	
	private void setResultPixel(){
		this.resultPixel =Integer.valueOf(this.rgbBinary.substring(0,5).concat(this.sp).concat(this.rgbBinary.substring(this.rgbBinary.length()-1)),2);
	}
	
	public int getResultPixel(){
		return this.resultPixel;
	}
}
