/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasifikasikainbatik.Controller;

import java.util.ArrayList;
import klasifikasikainbatik.Entity.SampelModel;

/**
 *
 * @author shaff
 */
public class NaiveBayesManager {
    private SampelModel[] sampel;
    private ArrayList<SampelModel[]> sampel_list;
    
    public NaiveBayesManager() {
    }
    
    public ArrayList<SampelModel[]> getInsialisasiSampel(SampelModel[] sampel){
        int i, index_ceplok, index_lunglungan, index_parang, index_semen;
        SampelModel[] sampel_ceplok, sampel_lunglungan, sampel_parang, sampel_semen;
    
        this.sampel = sampel;
        index_ceplok = index_lunglungan = index_parang = index_semen = 0;
        sampel_list = new ArrayList<>();
        
        for(i=0; i<sampel.length; i++){
            if(sampel[i].getKelasSampel().equals("ceplok")){
                index_ceplok += 1;
            }
            else if(sampel[i].getKelasSampel().equals("lunglungan")){
                index_lunglungan += 1;
            }
            else if(sampel[i].getKelasSampel().equals("parang")){
                index_parang += 1;
            }
            else{
                index_semen += 1;
            }
        }
        
        sampel_ceplok = new SampelModel[index_ceplok];
        sampel_lunglungan = new SampelModel[index_lunglungan];
        sampel_parang = new SampelModel[index_parang];
        sampel_semen = new SampelModel[index_semen];
        
        index_ceplok = index_lunglungan = index_parang = index_semen = 0;
        
        for(i=0; i<sampel.length; i++){
            if(sampel[i].getKelasSampel().equals("ceplok")){
                sampel_ceplok[index_ceplok] = sampel[i];
                index_ceplok += 1; 
            }
            else if(sampel[i].getKelasSampel().equals("lunglungan")){
                sampel_lunglungan[index_lunglungan] = sampel[i];
                index_lunglungan += 1;
            }
            else if(sampel[i].getKelasSampel().equals("parang")){
                sampel_parang[index_parang] = sampel[i];
                index_parang += 1;
            }
            else{
                sampel_semen[index_semen] = sampel[i];
                index_semen += 1;
            }
        }
        
        sampel_list.add(sampel_ceplok);
        sampel_list.add(sampel_lunglungan);
        sampel_list.add(sampel_parang);
        sampel_list.add(sampel_semen);
        
        System.out.println("Sampel berhasil diinisialisasi dan dipecah");
        
        return sampel_list;
    }
    
    public double[] hitung_mean(SampelModel[] sampel_temp){
        int i, j, nCiri;
        double[] mean;
        
        nCiri = sampel_temp[0].getNCiri();
        mean = new double[nCiri];
        
        for(i=0; i<nCiri; i++){
            mean[i] = 0;
        }
        
        for(i=0; i<nCiri; i++){
            for(j=0; j<sampel_temp.length; j++){
                mean[i] += sampel_temp[j].getCiriSampel()[i];
            }
            
            mean[i] /= sampel_temp.length;
        }
        
        return mean;
    }
    
    public double[] hitung_standar_deviasi(SampelModel[] sampel_temp, double[] mean){
        int i, j, nCiri;
        double sum;
        double[] standar_deviasi;
        
        nCiri = sampel_temp[0].getNCiri();
        standar_deviasi = new double[nCiri];
        sum=0;
        
        for(i=0; i<nCiri; i++){
            for(j=0; j<sampel_temp.length; j++){
                sum += Math.pow(sampel_temp[j].getCiriSampel()[i]-mean[i], 2);
            }
            
            sum /= sampel_temp.length-1;
            standar_deviasi[i] = Math.sqrt(sum);
            
        }
        
        return standar_deviasi;
    }
    
    public double hitung_prob_likehood(double[] ciri, double[] mean,  double[] standar_deviasi){
        int i, j, nCiri;
        double prob_likehood;
        double[] prob_likehood_ciri;
        
        nCiri = ciri.length;
        prob_likehood = 1;
        prob_likehood_ciri = new double[nCiri];
        
        for(i=0; i<nCiri; i++){
            prob_likehood_ciri[i] = Math.exp(-1*(Math.pow(ciri[i] - mean[i], 2)/(2*Math.pow(standar_deviasi[i], 2))))/Math.sqrt(2*3.14*Math.pow(standar_deviasi[i], 2));
            prob_likehood *= prob_likehood_ciri[i];
        }
        
        return prob_likehood;
    }
    
    public double hitung_prob_prior(String kelas){
        int i;
        double prob_prior, sum;
        
        sum = 0;
        
        for(i=0; i<sampel_list.size(); i++){
            sum += sampel_list.get(i).length;
        }
        
        switch (kelas) {
            case "ceplok":
                prob_prior = sampel_list.get(0).length/sum;
                break;
            case "lunglungan":
                prob_prior = sampel_list.get(1).length/sum;
                break;
            case "parang":
                prob_prior = sampel_list.get(2).length/sum;
                break;
            default:
                prob_prior = sampel_list.get(3).length/sum;
                break;
        }
        
        return prob_prior;
    }
    
    public double hitung_prob_posterior(double prob_prior, double prob_likehood){
        double prob_posterior;
        
        prob_posterior = prob_prior*prob_likehood;
        
        return prob_posterior;
    }
}
