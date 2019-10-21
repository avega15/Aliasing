import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.stream.FileImageInputStream;


public class Aliasing extends Image{
		
		
	public Aliasing(int w, int h){
		super(w, h);
	}
		
	public Aliasing(String fn){
		super(fn);
	}
		
	public void circle(int radius, int thickness){
		white();
		int centerX = getW()/2;
		int centerY = getH()/2;
		
		int pix = 0 << 16 | 0 << 8 | 0;
		int x = 0;
		int y = 0;
		int r = radius;
		int circumference = 0;
		
		while(r <= 256){
			circumference =  (int)(Math.toDegrees(Math.PI) * (r * 2));
			
			for(int j = 0; j <= circumference; j++){
				x = (int)((r * Math.cos(Math.toDegrees(j))) + centerX);
				y = (int)((r * Math.sin(Math.toDegrees(j))) + centerY);
				
				if(r + thickness > 256){
					break;
				}
				
				img.setRGB(x, y, pix);
				
				if(thickness > 0 && r + thickness < 256){
					for(int k = 1; k <= thickness; k++){
						x = (int)(((r + k) * Math.cos(Math.toDegrees(j))) + centerX);
						y = (int)(((r + k) * Math.sin(Math.toDegrees(j))) + centerY);
						img.setRGB(x, y, pix);
					}
				}
			}
			r += radius;
		}			
	}
		
		
	public int[][] upperLeftPixel(int k){
		int[][] pixels = new int[512/k][512/k];
		int kX = 0;
		int kY = 0;
		
		for(int i = 0; i < pixels.length; i++){
			for(int j = 0; j < pixels[i].length; j++){
				pixels[i][j] = img.getRGB(kX, kY);
				kX += k;
				
			}
			kX = 0;
			kY += k;
		}
		
		return pixels;
	}
	
	public int[][] filter(int k, int version){
		int[][] pixels = new int[512/k][512/k];
		int total = 0;
		int mid, top, right, left, bottom, topLeft, topRight, bottomLeft, bottomRight = 0;
		int kX = 0;
		int kY = 0;
		
		for(int i = 0; i < pixels.length; i++){
			for(int j = 0; j < pixels[i].length; j++){
				//pixels[i][j] = img.getRGB(kX, kY);
				
				//First pixel 
				if(i == 0 && j == 0){
					mid = img.getRGB(kX, kY);
					right = img.getRGB(kX + 1, kY);
					bottom = img.getRGB(kX, kY + 1);
					bottomRight = img.getRGB(kX + 1, kY + 1);
					if(version == 1){
						total = (mid + right + bottom + bottomRight) / 4;
					}
					if(version == 2){
						total = (4 * mid + 2 * right + 2* bottom + bottomRight) / 9;
						}
					pixels[i][j] = grayscale(total);;
					kX += k;
					total = 0;
					continue;
					}
					
				//First row exception
				if(i == 0){
					mid = img.getRGB(kX, kY);
					right = img.getRGB(kX + 1, kY);
					left = img.getRGB(kX - 1, kY);
					bottom = img.getRGB(kX, kY + 1);
					bottomRight = img.getRGB(kX + 1, kY + 1);
					bottomLeft = img.getRGB(kX - 1, kY + 1);
					if(version == 1){
						total = (mid + right + bottom + bottomRight) / 6;
					}
					if(version == 2){
						total = (4 * mid + 2 * right + 2 * left + 2 * bottom + bottomRight + bottomLeft) / 12;
					}
					pixels[i][j] = grayscale(total);;
					kX += k;
					total = 0;
					continue;
				}
				
				//First column exception
				if(j == 0){
					mid = img.getRGB(kX, kY);
					right = img.getRGB(kX + 1, kY);
					bottom = img.getRGB(kX, kY + 1);
					bottomRight = img.getRGB(kX + 1, kY + 1);
					top = img.getRGB(kX, kY - 1);
					topRight = img.getRGB(kX + 1, kY - 1);
					if(version == 1){
						total = (mid + right + bottom + bottomRight + top) / 6;
					}
					if(version == 2){
						total = (4 * mid + 2 * right + 2* bottom + bottomRight + 2 * top + topRight) / 12;
						}
					pixels[i][j] = grayscale(total);;
					kX += k;
					total = 0;
					continue;
				}

				//All other cases
				if(j != 0 && i != 0){
					mid = img.getRGB(kX, kY);
					right = img.getRGB(kX + 1, kY);
					left = img.getRGB(kX - 1, kY);
					bottom = img.getRGB(kX, kY + 1);
					top = img.getRGB(kX, kY - 1);

					bottomRight = img.getRGB(kX + 1, kY + 1);
					bottomLeft = img.getRGB(kX - 1, kY + 1);
					
					topRight = img.getRGB(kX + 1, kY - 1);
					topLeft = img.getRGB(kX - 1, kY - 1);
					if(version == 1){
						total = (mid + right + left + bottom + bottomRight + bottomLeft+ top + topRight + topLeft) / 9;
					}
					if(version == 2){
						total = (4 * mid + 2 * right + 2 * left + 2* bottom 
						+ bottomRight + bottomLeft+ 2 * top + topRight + topLeft) / 16;
					}
					pixels[i][j] = grayscale(total);;
					kX += k;
					total = 0;
					continue;
				}
			}
			
			kX = 0;
			kY += k;
		}
		return pixels;
	}
	
		
	public void resize(int k, int[][] pixels){
		int totalBlocks = 512/k * 512/k;
		white();
		
		for(int i = 0; i < 512/k; i++){	
			for(int j = 0; j < 512/k; j++){
				img.setRGB(j, i, pixels[j][i]);
			}
		}
		
	}
	public int grayscale(int pix){
		int temp = pix;
							
		int r = (temp >> 16) & 0xff;
		int g = (temp >> 8) & 0xff;
		int b = temp & 0xff;
				
		int Gray = (int) Math.round(.299 * r + .587 * g + .114 * b);
					
		temp = (Gray << 16) | (Gray << 8) | Gray;
		return temp;
		}
	
	public void white(){
		int height = getH();
		int width = getW(); 
		int pix = 255 << 16 | 255 << 8 | 255;
		
		for(int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				img.setRGB(j, i, pix);
			}
		}
	}
} 
