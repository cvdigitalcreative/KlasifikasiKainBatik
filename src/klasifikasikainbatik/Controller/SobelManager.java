/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasifikasikainbatik.Controller;

import java.awt.Color;
import java.math.BigDecimal;

/**
 *
 * @author shaff
 */
public class SobelManager {

    public SobelManager() {
    }
    
    public int SobelXOperator(int[][] matriks){
        int i,j;
        int x_value;
        
        int Sx[][] = {{-1,0,1},{-2,0,2},{-1,0,1}};
        x_value = 0;
//        System.out.println("adada");
        for(j=0; j<3; j++){
            if(j!=1){
                for(i=0; i<3; i++){
//                    System.out.println("tes"+j);
//                    System.out.println(x_value);
                    x_value += matriks[i][j]*Sx[i][j];
                }
            }
        }
        
        return x_value;
	
    }
    
    public int SobelYOperator(int[][] matriks){
        int i, j;
        int y_value;
	int Sy[][] = {{1,2,1},{0,0,0},{-1,-2,-1}};
        
        y_value = 0;
        
        for(i=0; i<3; i++){
            if(i!=1){
                for(j=0; j<3; j++){
                    y_value += matriks[i][j]*Sy[i][j];
                }
            }
        }
        
        return y_value;
    }
    
    public int[][] hitungDeteksiTepi(int[][] img_gray){
        int height, width, i, j, koordinat, pixel;
        double x_operator, y_operator, magnitude, max, min, Th;
        int[][] subMatriks, m_sobel;
        Color c_gray_img;
        
        height = img_gray.length;
        width = img_gray[0].length;
        
        m_sobel = new int[height][width];
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                m_sobel[i][j] = img_gray[i][j];
            }
        }
        
        max = -1;
        min = 10000;
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                if(i != 0 && i!=height-1 && j != 0 && j!=width-1){
                    subMatriks = new int[3][3];
                    
                    subMatriks[0][0] = img_gray[i-1][j-1];
                    subMatriks[0][1] = img_gray[i-1][j];
                    subMatriks[0][2] = img_gray[i-1][j+1];
                    subMatriks[1][0] = img_gray[i][j-1];
                    subMatriks[1][1] = img_gray[i][j];
                    subMatriks[1][2] = img_gray[i][j+1];
                    subMatriks[2][0] = img_gray[i+1][j-1];
                    subMatriks[2][1] = img_gray[i+1][j];
                    subMatriks[2][2] = img_gray[i+1][j+1];
                     
                    x_operator = SobelXOperator(subMatriks);
                    y_operator = SobelYOperator(subMatriks);
                    
                    magnitude = Math.sqrt(Math.pow(x_operator, 2)+Math.pow(y_operator, 2));
                    
                    if(max < magnitude){
                        max = magnitude;
                    }
                    
                    if(min > magnitude){
                        min = magnitude;
                    }
                    
                    m_sobel[i][j] = (int)magnitude;
                }
            }
        }
        
        Th = (max+min)/2.0;
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                if(m_sobel[i][j] > Th){
                    m_sobel[i][j] = 255;
                }
                else{
                    m_sobel[i][j] = 0;
                }
            }
        }
        
        return m_sobel;
    }
    
//    private BigDecimal truncateDecimal(double x, int numberofDecimals) {
//        if (x > 0) {
//            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
//        } else {
//            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
//        }
//    }
}
