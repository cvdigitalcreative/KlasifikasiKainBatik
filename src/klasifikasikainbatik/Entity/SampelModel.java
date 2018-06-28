/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasifikasikainbatik.Entity;

/**
 *
 * @author shaff
 */
public class SampelModel {
    private String nama_sampel;
    private String kelas_sampel;
    private double[] ciri_sampel;
    
    public SampelModel() {
    }
    
    public String getKelasSampel() {
        return kelas_sampel;
    }

    public void setKelasSampel(String kelas_sampel) {
        this.kelas_sampel = kelas_sampel;
    }
    public double[] getCiriSampel() {
        return ciri_sampel;
    }

    public String getNamaSampel() {
        return nama_sampel;
    }
    
    public int getNCiri(){
        return ciri_sampel.length;
    }

    public void setCiriSampel(double[] ciri_sampel) {
        int i;
        
        this.ciri_sampel = new double[ciri_sampel.length];
        
        for(i=0; i<ciri_sampel.length; i++){
            this.ciri_sampel[i] = ciri_sampel[i];
        }
    }

    public void setNamaSampel(String nama_sampel) {
        this.nama_sampel = nama_sampel;
    }
}
