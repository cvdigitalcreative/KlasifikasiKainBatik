/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasifikasikainbatik.Controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import klasifikasikainbatik.Entity.CitraModel;

/**
 *
 * @author shaff
 */
public class PraPengolahanManager {
    private CitraModel[] citra_latih;
    private CitraModel citra_uji;
    private String[] nama_file;
    private String nama_file_uji;
    
    public PraPengolahanManager() {
    }
    
    public void inisialisasiDataCitraBatik(String proses, String path_file){
        int i, j, n_citra;
        boolean ada;
        String tempFileName;
        BufferedImage temp, scaleImage;
        
        if(proses.equals("Pelatihan")){
            File direktori = new File(path_file);
            File[] daftarFile = direktori.listFiles();
            n_citra = daftarFile.length;
            citra_latih = new CitraModel[n_citra];
            nama_file = new String[n_citra];

            for(i=0; i<n_citra; i++){
                ada = daftarFile[i].isFile();
                if(ada == true){
                    try {
                        temp = ImageIO.read(daftarFile[i]);
                        scaleImage = resize(temp, 250, 250);
                        citra_latih[i] = new CitraModel(scaleImage);
                        nama_file[i] = daftarFile[i].getName();
                        tempFileName = getkelas(nama_file[i]);
                        citra_latih[i].setKelasCitra(tempFileName);
                    } catch (IOException ex) {
                        System.out.println("Terjadi kesalahan pada saat membuka berkas" + ex.getMessage());
                    }
                }
            }

            System.out.println("Path Data-data Citra : "+path_file);
            System.out.println("Banyak Data : "+n_citra);
        }
        else{
            File file = new File(path_file);
            System.out.println("namaFile Anda: "+file.toString());
            try{
                temp = ImageIO.read(file);
                scaleImage = resize(temp, 250, 250);
                citra_uji = new CitraModel(scaleImage);
                nama_file_uji = file.getName();
                tempFileName = getkelas(nama_file_uji);
                citra_uji.setKelasCitra(tempFileName);
            }catch(IOException ex){
                System.out.println("Terjadi kesalahan saat membuka berkas! - "+ex.getMessage());
            }
        }
    }
    
    //fungsi untuk mengubah citra asli ke citra grayscale pada citra latih
    public void hitung_grayscale(String proses){
        Color c_oriIm, c_grayIm;
        int height, width, R, G, B, i, j, k, grayLevel, n_citra;
        
        if(proses.equals("Pelatihan")){
            n_citra = citra_latih.length;
        
            for(i=0; i<n_citra; i++){
                height = citra_latih[i].getHeight();
                width = citra_latih[i].getWidth();

                for(j=0; j<height; j++){
                    for(k=0; k<width; k++){
                        int koordinat = citra_latih[i].get_koordinatRGB(j, k);
                        c_oriIm = new Color(koordinat);
                        R = (int)c_oriIm.getRed();
                        G = (int)c_oriIm.getGreen();
                        B = (int)c_oriIm.getBlue();

                        grayLevel = (R+G+B)/3;
                        c_grayIm = new Color(grayLevel, grayLevel, grayLevel);
                        citra_latih[i].set_citraRGB(j, k, c_grayIm);
                    }
                }
            }
        }
        else{
            height = citra_uji.getHeight();
            width = citra_uji.getWidth();

            for(j=0; j<height; j++){
                for(k=0; k<width; k++){
                    int koordinat = citra_uji.get_koordinatRGB(j, k);
                    c_oriIm = new Color(koordinat);
                    R = (int)c_oriIm.getRed();
                    G = (int)c_oriIm.getGreen();
                    B = (int)c_oriIm.getBlue();

                    grayLevel = (R+G+B)/3;
                    c_grayIm = new Color(grayLevel, grayLevel, grayLevel);
                    citra_uji.set_citraRGB(j, k, c_grayIm);
                }
            }
        }
    }
    
    public void hitung_sobel(String proses){
        int n_citra, i;
        int[][] gray_arr, sobel_arr; 
       
        SobelManager sobel = new SobelManager();
        
        if(proses.equals("Pelatihan")){
            n_citra = citra_latih.length;
        
            for(i=0; i<n_citra; i++){
                gray_arr = imgToMatriks(citra_latih[i].getCitra());
                sobel_arr = sobel.hitungDeteksiTepi(gray_arr);
                citra_latih[i].setCitra(matriksToImg(sobel_arr));
            }
        }
        else{
            gray_arr = imgToMatriks(citra_uji.getCitra());
            sobel_arr = sobel.hitungDeteksiTepi(gray_arr);
            citra_uji.setCitra(matriksToImg(sobel_arr));
        }
    }
    
    public BufferedImage matriksToImg(int[][] arr){
        int i, j;
        Color c_sobel;
        BufferedImage img_sobel;
        
        img_sobel = new BufferedImage(arr.length, arr[0].length, BufferedImage.TYPE_INT_ARGB);
        
        for(i=0; i<arr[0].length; i++){
            for(j=0; j<arr.length; j++){
                c_sobel = new Color(arr[i][j], arr[i][j], arr[i][j]);
                img_sobel.setRGB(i, j, c_sobel.getRGB());
            }
        }
        
        return img_sobel;
    }
    
    
    public int[][] imgToMatriks(BufferedImage img){
        int pixel, i, j;
        Color c_img;

        int[][] imgArr = new int[img.getHeight()][img.getWidth()];

        for(i=0; i<img.getHeight(); i++){
            for(j=0; j<img.getWidth(); j++){
                c_img = new Color(img.getRGB(i,j));
                pixel = c_img.getRed();
                
                imgArr[i][j] = pixel;
            }
        }

        return imgArr;
    }
    
    public String getkelas(String file){
        String[] kelas_file;
        
        kelas_file = file.split("_");
        return kelas_file[0];
    }
    
    public String[] getNamaSampel(){
        return nama_file;
    }
    
    public String getNamaSampelUji(){
        return nama_file_uji;
    }
    
    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
    
    public CitraModel[] getCitraLatihGrayscale(){
        int i;
        CitraModel[] citra_latih_gray;
        
        citra_latih_gray = new CitraModel[citra_latih.length];
        
        for(i=0; i<citra_latih_gray.length; i++){
            citra_latih_gray[i] = citra_latih[i];
        }
        
        return citra_latih_gray;
    }
    
    public CitraModel[] getCitraLatihSobel(){
        int i;
        CitraModel[] citra_latih_sobel;
        
        citra_latih_sobel = new CitraModel[citra_latih.length];
        
        for(i=0; i<citra_latih_sobel.length; i++){
            citra_latih_sobel[i] = citra_latih[i];
        }
        
        return citra_latih_sobel;
    }
    
    public CitraModel getCitraGrayscale(){
        return citra_uji;
    }
    
    public CitraModel getCitraSobel(){
        return citra_uji;
    }
}
