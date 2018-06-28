/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasifikasikainbatik.Controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import klasifikasikainbatik.Entity.CitraModel;

/**
 *
 * @author shaff
 */
public class UMIManager {
    CitraModel[] citra_latih; 
    CitraModel citra_uji; 
     
    public UMIManager() {
    }
    
    public void inisialisasiCitra(CitraModel[] citra_latih){
        this.citra_latih = citra_latih;
    }
    
    public void inisialisasiCitraUJi(CitraModel citra_uji){
        this.citra_uji = citra_uji;
    }
    
    public double[][] hitung_hu_moment(String proses){
        int n_citra, i, height, width;
        int[][] m_citra;
        double[][] m_hu_moment;
        
        if(proses.equals("Pelatihan")){
            n_citra = citra_latih.length;
            m_hu_moment = new double[n_citra][7];

            for(i=0; i<n_citra; i++){            
                m_citra = imgToMatriks(citra_latih[i].getCitra());

                m_hu_moment[i][0] = normalisasi_center_moment(2, 0, m_citra)+normalisasi_center_moment(0, 2, m_citra);
                m_hu_moment[i][1] = Math.pow(normalisasi_center_moment(2, 0, m_citra)-normalisasi_center_moment(0, 2, m_citra), 2)
                                    +(4*Math.pow(normalisasi_center_moment(1, 1, m_citra), 2));

                m_hu_moment[i][2] = Math.pow(normalisasi_center_moment(3, 0, m_citra)-(3*normalisasi_center_moment(1, 2, m_citra)), 2)
                                    +Math.pow(normalisasi_center_moment(0, 3, m_citra)-(3*normalisasi_center_moment(2, 1, m_citra)), 2);

                m_hu_moment[i][3] = Math.pow(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra), 2)
                                    +Math.pow(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra), 2);

                m_hu_moment[i][4] = ((normalisasi_center_moment(3, 0, m_citra)-(3*normalisasi_center_moment(1, 2, m_citra)))
                                    *(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra))
                                    *(Math.pow(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra), 2)
                                    -(3*(Math.pow(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra), 2)))))
                                    +(((3*normalisasi_center_moment(2, 1, m_citra))-normalisasi_center_moment(0, 3, m_citra))
                                    *(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra))
                                    *((3*Math.pow(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra), 2))
                                    -(Math.pow(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra), 2))));

                m_hu_moment[i][5] = ((normalisasi_center_moment(2, 0, m_citra)-normalisasi_center_moment(0, 2, m_citra))
                                    *(Math.pow(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra), 2)
                                    -Math.pow(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra), 2)))
                                    +((4*(normalisasi_center_moment(1, 1, m_citra)))*(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra))
                                    *(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra)));

                m_hu_moment[i][6] = (((3*normalisasi_center_moment(2, 1, m_citra))-normalisasi_center_moment(0, 3, m_citra))
                                    *(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra))
                                    *(Math.pow(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra), 2)
                                    -(3*Math.pow(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra), 2))))
                                    +(((3*normalisasi_center_moment(1, 2, m_citra))-normalisasi_center_moment(0, 3, m_citra))
                                    *(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra))
                                    *((3*Math.pow(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra), 2))
                                    -(Math.pow(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra), 2))));
            }
        }
        else{
            m_hu_moment = new double[1][7];
            
            m_citra = imgToMatriks(citra_uji.getCitra());

            m_hu_moment[0][0] = normalisasi_center_moment(2, 0, m_citra)+normalisasi_center_moment(0, 2, m_citra);
            m_hu_moment[0][1] = Math.pow(normalisasi_center_moment(2, 0, m_citra)-normalisasi_center_moment(0, 2, m_citra), 2)
                                +(4*Math.pow(normalisasi_center_moment(1, 1, m_citra), 2));

            m_hu_moment[0][2] = Math.pow(normalisasi_center_moment(3, 0, m_citra)-(3*normalisasi_center_moment(1, 2, m_citra)), 2)
                                +Math.pow(normalisasi_center_moment(0, 3, m_citra)-(3*normalisasi_center_moment(2, 1, m_citra)), 2);

            m_hu_moment[0][3] = Math.pow(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra), 2)
                                +Math.pow(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra), 2);

            m_hu_moment[0][4] = ((normalisasi_center_moment(3, 0, m_citra)-(3*normalisasi_center_moment(1, 2, m_citra)))
                                *(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra))
                                *(Math.pow(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra), 2)
                                -(3*(Math.pow(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra), 2)))))
                                +(((3*normalisasi_center_moment(2, 1, m_citra))-normalisasi_center_moment(0, 3, m_citra))
                                *(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra))
                                *((3*Math.pow(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra), 2))
                                -(Math.pow(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra), 2))));

            m_hu_moment[0][5] = ((normalisasi_center_moment(2, 0, m_citra)-normalisasi_center_moment(0, 2, m_citra))
                                *(Math.pow(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra), 2)
                                -Math.pow(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra), 2)))
                                +((4*(normalisasi_center_moment(1, 1, m_citra)))*(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra))
                                *(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra)));

            m_hu_moment[0][6] = (((3*normalisasi_center_moment(2, 1, m_citra))-normalisasi_center_moment(0, 3, m_citra))
                                *(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra))
                                *(Math.pow(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra), 2)
                                -(3*Math.pow(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra), 2))))
                                +(((3*normalisasi_center_moment(1, 2, m_citra))-normalisasi_center_moment(0, 3, m_citra))
                                *(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra))
                                *((3*Math.pow(normalisasi_center_moment(3, 0, m_citra)+normalisasi_center_moment(1, 2, m_citra), 2))
                                -(Math.pow(normalisasi_center_moment(2, 1, m_citra)+normalisasi_center_moment(0, 3, m_citra), 2))));
        }
        
        return m_hu_moment;
    }
    
    public double[][] hitung_united_moment_invariants(double[][] m_hu_moment, String proses){
        int n_citra, i, j, height, width;
        int[][] m_citra;
        double[][] m_united_moment;
        
        if(proses.equals("Pelatihan")){
            n_citra = citra_latih.length;
            m_united_moment = new double[n_citra][8];

            for(i=0; i<n_citra; i++){            
                m_united_moment[i][0] = Math.sqrt(m_hu_moment[i][1])/m_hu_moment[i][0];
                m_united_moment[i][1] = m_hu_moment[i][5]/(m_hu_moment[i][0]*m_hu_moment[i][3]);
                m_united_moment[i][2] = Math.sqrt(m_hu_moment[i][4])/m_hu_moment[i][3];
                m_united_moment[i][3] = m_hu_moment[i][4]/(m_hu_moment[i][2]*m_hu_moment[i][3]);
                m_united_moment[i][4] = (m_hu_moment[i][0]*m_hu_moment[i][5])/(m_hu_moment[i][1]*m_hu_moment[i][2]);
                m_united_moment[i][5] = ((m_hu_moment[i][0]+Math.sqrt(m_hu_moment[i][1]))*m_hu_moment[i][2])/m_hu_moment[i][5];
                m_united_moment[i][6] = (m_hu_moment[i][0]*m_hu_moment[i][4])/(m_hu_moment[i][2]*m_hu_moment[i][5]);
                m_united_moment[i][7] = (m_hu_moment[i][2]+m_hu_moment[i][3])/Math.sqrt(m_hu_moment[i][4]);
            }

            for(i=0; i<m_united_moment.length; i++){
                for(j=0; j<m_united_moment[i].length; j++){
                    if(Double.isNaN(m_united_moment[i][j])){
                        m_united_moment[i][j] = 0;
                    }
                }
            }
        }
        else{
            m_united_moment = new double[1][8];
            
            m_united_moment[0][0] = Math.sqrt(m_hu_moment[0][1])/m_hu_moment[0][0];
            m_united_moment[0][1] = m_hu_moment[0][5]/(m_hu_moment[0][0]*m_hu_moment[0][3]);
            m_united_moment[0][2] = Math.sqrt(m_hu_moment[0][4])/m_hu_moment[0][3];
            m_united_moment[0][3] = m_hu_moment[0][4]/(m_hu_moment[0][2]*m_hu_moment[0][3]);
            m_united_moment[0][4] = (m_hu_moment[0][0]*m_hu_moment[0][5])/(m_hu_moment[0][1]*m_hu_moment[0][2]);
            m_united_moment[0][5] = ((m_hu_moment[0][0]+Math.sqrt(m_hu_moment[0][1]))*m_hu_moment[0][2])/m_hu_moment[0][5];
            m_united_moment[0][6] = (m_hu_moment[0][0]*m_hu_moment[0][4])/(m_hu_moment[0][2]*m_hu_moment[0][5]);
            m_united_moment[0][7] = (m_hu_moment[0][2]+m_hu_moment[0][3])/Math.sqrt(m_hu_moment[0][4]);
            
            for(i=0; i<m_united_moment.length; i++){
                for(j=0; j<m_united_moment[i].length; j++){
                    if(Double.isNaN(m_united_moment[i][j])){
                        m_united_moment[i][j] = 0;
                    }
                }
            }
        }
        
        return m_united_moment;
    }
    
    public double hitung_moment(int p, int q, int[][] citra){
        int i, j, height, width;
        double sum;
        
        height = citra.length;
        width = citra[0].length;
        sum=0;
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                sum += Math.pow(i, p)*Math.pow(j, q)*citra[i][j];
            }
        }
        
        return sum;
    }
    
    public double hitung_center_moment(int p, int q, int[][] citra){
        int i, j, height, width;
        double sum, xbar, ybar, m00, m10, m01;
        
        height = citra.length;
        width = citra[0].length;
        sum=0;
        
        m00 = hitung_moment(0, 0, citra);
        m10 = hitung_moment(1, 0, citra);
        m01 = hitung_moment(0, 1, citra);
        
        xbar = m10/m00;
        ybar = m01/m00;
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                sum += Math.pow(i-xbar, p)*Math.pow(j-ybar, q)*citra[i][j];
            }
        }
        
        return sum;
    }
    
    public double normalisasi_center_moment(int p, int q, int[][] citra){
        double center_moment_pq, center_moment_00, nor_center_moment;
        
        center_moment_pq = hitung_center_moment(p, q, citra);
        center_moment_00 = hitung_center_moment(0, 0, citra);
        
        nor_center_moment = center_moment_pq/Math.pow(center_moment_00, (double)(p+q+2.0)/2.0);
        
        return nor_center_moment;
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
}
