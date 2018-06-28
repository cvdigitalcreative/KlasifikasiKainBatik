/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasifikasikainbatik.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import klasifikasikainbatik.Entity.SampelModel;

/**
 *
 * @author shaff
 */
public class DatabaseManager {
    private Connection conn = null;
    
    private final String user = "root";
    private final String password = "";
    
    public DatabaseManager() {
    }
    
    public void setKoneksiDatabase(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/klasifikasikainbatik", user, password);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int isDataDatabaseKosong(){
        int nData, i, state;
        String sql_jumlah;
        
        sql_jumlah = "select count(*) from tabel_ciri_sampel";
        nData = 0;
        
        try {            
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql_jumlah);
            
            result.next();
            nData = result.getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(nData == 0){
            state = 1;
        }
        else{
            state = 0;
        }
        
        return nData;
    }
    
    public void hapusDataDatabase(){
        int nData, i;
        String sql, sql_jumlah;
        
        sql_jumlah = "select count(*) from tabel_ciri_sampel";
        nData = 0;
        
        try {            
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql_jumlah);
            
            result.next();
            nData = result.getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sql = "delete from tabel_ciri_sampel where id_sampel = ?";
        
        for(i=1; i<=nData; i++){
            try {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, i);

                statement.execute();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void simpanDataDatabase(SampelModel sampel){
        String sql_id;
        int i, id;
        
        sql_id = "select * from tabel_ciri_sampel";
        
        i=1;
        id = 0;
        try {            
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql_id);
            
            while (result.next()){
                if(i == result.getInt(1)){
                    id = i;
                }
                else{
                    break;
                }
                
                i++;
            }
            
//            System.out.println(id);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sql = "INSERT INTO tabel_ciri_sampel (id_sampel, nama_sampel, ciri_sampel_1, ciri_sampel_2, ciri_sampel_3, ciri_sampel_4, "
                + "ciri_sampel_5, ciri_sampel_6, ciri_sampel_7, ciri_sampel_8) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, id+1);
            statement.setString(2, sampel.getNamaSampel());
            statement.setDouble(3, sampel.getCiriSampel()[0]);
            statement.setDouble(4, sampel.getCiriSampel()[1]);
            statement.setDouble(5, sampel.getCiriSampel()[2]);
            statement.setDouble(6, sampel.getCiriSampel()[3]);
            statement.setDouble(7, sampel.getCiriSampel()[4]);
            statement.setDouble(8, sampel.getCiriSampel()[5]);
            statement.setDouble(9, sampel.getCiriSampel()[6]);
            statement.setDouble(10, sampel.getCiriSampel()[7]);

            int rowsInserted = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public SampelModel[] getDataDatabase(){
        String sql, sql_jumlah;
        SampelModel data;
        SampelModel[] data_list;
        double[] ciri_sampel;
        String[] kelas_citra;
        int nData, index;
        
        sql_jumlah = "select count(*) from tabel_ciri_sampel";
        nData = index = 0;
        
        try {            
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql_jumlah);
            
            result.next();
            nData = result.getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sql = "select * from tabel_ciri_sampel";
        
        data_list = new SampelModel[nData];
        
        try {            
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while (result.next()){
                data = new SampelModel();
                data.setNamaSampel(result.getString(2));
                
                kelas_citra = result.getString(2).split("_");
                data.setKelasSampel(kelas_citra[0]);
                
                ciri_sampel = new double[8];
                ciri_sampel[0] = result.getDouble(3);
                ciri_sampel[1] = result.getDouble(4);
                ciri_sampel[2] = result.getDouble(5);
                ciri_sampel[3] = result.getDouble(6);
                ciri_sampel[4] = result.getDouble(7);
                ciri_sampel[5] = result.getDouble(8);
                ciri_sampel[6] = result.getDouble(9);
                ciri_sampel[7] = result.getDouble(10);
                
                data.setCiriSampel(ciri_sampel);
                
                data_list[index] = data;
                index += 1;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data_list;
    }
}
