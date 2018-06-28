/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasifikasikainbatik.Entity;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author shaff
 */
public class CitraModel {
    private BufferedImage citra;
    private int koordinat_RGB;
    private int n_citra;
    private String kelasCitra;
    
    public CitraModel(int n_citra){
        this.n_citra = n_citra;
    }
    
    public CitraModel(BufferedImage citra){
        this.citra = citra;
    }
    
    public CitraModel(CitraModel citraModel){
        this.citra = citraModel.getCitra();
        this.kelasCitra = citraModel.getKelasCitra();
    }
    
    public int getHeight(){
        return citra.getHeight();
    }
    
    public int getWidth(){
        return citra.getWidth();
    }
    
    public int getType(){
        return citra.getType();
    }
    
    public int get_koordinatRGB(int x, int y){
        return koordinat_RGB = citra.getRGB(x, y);
    }
    
    public void set_citraRGB(int x, int y, Color warna){
        citra.setRGB(x, y, warna.getRGB());
    }
    
    public void setKelasCitra(String kelas){
        kelasCitra = kelas;
    }

    public String getKelasCitra(){
        return kelasCitra;
    }
    
    public void setCitra(BufferedImage citra){
        this.citra = citra;
    }
    
    public BufferedImage getCitra(){
        return citra;
    }
}
