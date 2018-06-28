/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasifikasikainbatik.Controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import klasifikasikainbatik.Entity.CitraModel;
import klasifikasikainbatik.Entity.SampelModel;

/**
 *
 * @author shaff
 */
public class HalamanUtamaManager {
    private DatabaseManager manager;
    private PraPengolahanManager praPengolahan;
    private UMIManager umi;
    private NaiveBayesManager naiveBayes;
    private CitraModel[] citra_gray, citra_sobel;
    private CitraModel citra_gray_klasifikasi, citra_sobel_klasifikasi;
    private String[] sampel_name;
    private String nama_sampel;
    private double[][] m_united_moment, m_united_moment_klasifikasi;
    private int index_kelas;
    private ArrayList<SampelModel[]> sampel_list;
    
    public HalamanUtamaManager() {
        manager = new DatabaseManager();
        manager.setKoneksiDatabase();
    }
    
    public void do_prapengolahan(String proses, String path_file){
        CitraModel citra;
        praPengolahan = new PraPengolahanManager();
        
        if(proses.equals("Pelatihan")){
            praPengolahan.inisialisasiDataCitraBatik("Pelatihan", path_file);
            praPengolahan.hitung_grayscale("Pelatihan");
            praPengolahan.hitung_sobel("Pelatihan");
            citra_sobel = praPengolahan.getCitraLatihSobel();
            sampel_name = praPengolahan.getNamaSampel();
        }
        else if(proses.equals("Multi Klasifikasi")){
            praPengolahan.inisialisasiDataCitraBatik("Pelatihan", path_file);
            praPengolahan.hitung_grayscale("Pelatihan");
            praPengolahan.hitung_sobel("Pelatihan");
            citra_sobel = praPengolahan.getCitraLatihSobel();
            sampel_name = praPengolahan.getNamaSampel();
        }
        else{
            praPengolahan.inisialisasiDataCitraBatik("Klasifikasi", path_file);
            praPengolahan.hitung_grayscale("Klasifikasi");
            citra = praPengolahan.getCitraGrayscale();
            citra_gray_klasifikasi = new CitraModel(citra);

            praPengolahan.hitung_sobel("Klasifikasi");
            citra = praPengolahan.getCitraSobel();
            citra_sobel_klasifikasi = new CitraModel(citra);
            nama_sampel = praPengolahan.getNamaSampelUji();
        }
    }
    
    public void do_ekstraksiciri(String proses){
        double[][] m_hu_moment;
        
        umi = new UMIManager();
        
        if(proses.equals("Pelatihan")){
            umi.inisialisasiCitra(citra_sobel);
            m_hu_moment = umi.hitung_hu_moment("Pelatihan");
            m_united_moment = umi.hitung_united_moment_invariants(m_hu_moment, "Pelatihan");
        }
        else if(proses.equals("Multi Klasifikasi")){
            umi.inisialisasiCitra(citra_sobel);
            m_hu_moment = umi.hitung_hu_moment("Pelatihan");
            m_united_moment = umi.hitung_united_moment_invariants(m_hu_moment, "Pelatihan");
        }
        else{
            umi.inisialisasiCitraUJi(citra_sobel_klasifikasi);
            m_hu_moment = umi.hitung_hu_moment("Klasifikasi");
            m_united_moment = umi.hitung_united_moment_invariants(m_hu_moment, "Klasifikasi");
        }
    }
    
    public ArrayList<SampelModel[]> get_sampellist(SampelModel[] sampel){
        naiveBayes = new NaiveBayesManager();
        
        sampel_list = naiveBayes.getInsialisasiSampel(sampel);
        
        return sampel_list;
    }
    
    public void do_klasifikasi(ArrayList<SampelModel[]> sampel_list, double[] m_united_moment, CitraModel citra){
        int i;
        double prob_prior, prob_likehood, prob_posterior, max_prob;
        double[] mean, standar_deviasi;
        
        max_prob = -1;
        index_kelas = 0;
        
        for(i=0; i<sampel_list.size(); i++){
            mean = naiveBayes.hitung_mean(sampel_list.get(i));
            standar_deviasi = naiveBayes.hitung_standar_deviasi(sampel_list.get(i), mean);
            prob_prior = naiveBayes.hitung_prob_prior(citra.getKelasCitra());
            prob_likehood = naiveBayes.hitung_prob_likehood(m_united_moment, mean, standar_deviasi);
            prob_posterior = naiveBayes.hitung_prob_posterior(prob_prior, prob_likehood);
            
            if(i==0){
                max_prob = prob_posterior;
                index_kelas = i;
            }
            else{
                if(max_prob < prob_posterior){
                    max_prob = prob_posterior;
                    index_kelas = i;
                }
            }
        }
    }
    
    public int get_indexkelas(){
        return index_kelas;
    }
    
    public double[][] get_m_unitedmoment(){
        return m_united_moment;
    }
    
    public CitraModel[] get_citrasobel(){
        return citra_sobel;
    }
    
    public String[] get_daftarnamasampel(){
        return sampel_name;
    }
    
    public CitraModel get_citragrayklasifikasi(){
        return citra_gray_klasifikasi;
    }
    
    public CitraModel get_citrasobelklasifikasi(){
        return citra_sobel_klasifikasi;
    }
    
    public String get_namasampelklasifikasi(){
        return nama_sampel;
    }
    
    public int isDataKosong(){
        int isKosong;
        
        isKosong = manager.isDataDatabaseKosong();
        
        if(isKosong == 1){
            return 1;
        }
        else{
            return 0;
        }
    }
    
    public void simpan_modelciri(SampelModel[] sampellist){
        int i;
        
        manager.hapusDataDatabase();
        
        for(i=0; i<sampellist.length; i++){
            manager.simpanDataDatabase(sampellist[i]);
        }
    }
    
    public SampelModel[] get_modelciri(){
        SampelModel[] sampellist;
        
        sampellist = manager.getDataDatabase();
        
        return sampellist;
    }
}
