import java.util.Scanner;

public class CS4551_Vega{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		for(int i = 0; i < 1;){
			System.out.println("Main Menu-----------------------------------");
			System.out.println("1. Aliasing");
			System.out.println("2. Dictionary Coding");
			System.out.println("3. Quit");
			System.out.println();
			System.out.println("Please enter the task number [1-3]:");

			int choice = input.nextInt();
			  
			if(choice == 1){
				//Creating circles- Saves into circles.ppm
				Aliasing circle = new Aliasing(512, 512);
				System.out.println("Please enter the radius for the circles.");
				int radius = input.nextInt();
				
				System.out.println("Please enter the thickness of the circles.");
				int thick = input.nextInt();
				
				circle.circle(radius, thick);
				circle.display();
				circle.write2PPM("circles.ppm");
				
				
				System.out.println("By how many times should we reduce the image size by?");
				System.out.println("(Choose 2, 4, 8, or 16.)");
				int blockSize = input.nextInt();
				
				//This is no filter resize- Saves into noFilter.ppm
				Aliasing noFilter = new Aliasing(512/blockSize, 512/blockSize);
				int [][] pixels = circle.upperLeftPixel(blockSize);
			
				noFilter.resize(blockSize, pixels);
				noFilter.display();
				noFilter.write2PPM("noFilter.ppm");
				
				//This is filter 1 resize- Saves into filter1.ppm
				Aliasing filter1 = new Aliasing(512/blockSize, 512/blockSize);
				int [][] pixelsFilter1 = circle.filter(blockSize, 1);
				
				filter1.resize(blockSize, pixelsFilter1);
				filter1.display();
				filter1.write2PPM("filter1.ppm");
				
				//This is filter 2 resize- Saves into filter2.ppm
				Aliasing filter2 = new Aliasing(512/blockSize, 512/blockSize);
				int [][] pixelsFilter2 = circle.filter(blockSize, 2);
				
				filter2.resize(blockSize, pixelsFilter2);
				filter2.display();
				filter2.write2PPM("filter2.ppm");
				
			}
			
			//Dictionary Coding
			else if(choice == 2){
				Dictionary encode = new Dictionary();
				System.out.println("Input the filename of text file to encode: ");
				System.out.println("Example: LZW_test4.txt");
				
				input.nextLine();
				String fileName = input.nextLine();
				
				encode.encoder(fileName);
			}
			
			//Exits Program
			else if(choice == 3){
				i = 1;
			}
		}
		System.out.println("--Good Bye--");
		System.exit(0);
	}

}
