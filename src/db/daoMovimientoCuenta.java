package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import datos.*;
import java.sql.Date;
import java.sql.Timestamp;

public class daoMovimientoCuenta{

    Connection conexion;
    
    public daoMovimientoCuenta(){
       conexionDB.getInstance().liberarConexion();
       conexion =  conexionDB.getInstance().tomarConexion();
    }  
    public void insertar(movimientoCuenta c) throws SQLException {
            String strSQL = "INSERT INTO movimiento_cuenta (K_MOVIMIENTO, V_MOVIMIENTO, I_TIPO, F_MOVIMIENTO, K_NUM_CUENTA) VALUES(?,?,?,?,?)";
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1,c.getK_MOVIMIENTO()); 
            prepStmt.setFloat(2, c.getV_MOVIMIENTO()); 
            prepStmt.setString(3, c.getI_TIPO()); 
            prepStmt.setTimestamp(4, Timestamp.valueOf(c.getF_MOVIMIENTO())); 
            prepStmt.setLong(5, c.getK_NUM_CUENTA()); 
            prepStmt.executeUpdate();
            prepStmt.close();
            //conexionDB.getInstance().commit();
            String strSQL3 = "SELECT V_SALDO FROM cuenta_ahorro WHERE K_NUM_CUENTA = ?";
            PreparedStatement prepStmt3 = conexion.prepareStatement(strSQL3);
            prepStmt3.setLong(1,c.getK_NUM_CUENTA()); 
            ResultSet res = prepStmt3.executeQuery();
            String strSQL2 = "UPDATE cuenta_ahorro SET V_SALDO=? WHERE K_NUM_CUENTA = ?";
            PreparedStatement prepStmt2 = conexion.prepareStatement(strSQL2);
            while(res.next()){
                prepStmt2.setFloat(1,res.getFloat("V_SALDO")+c.getV_MOVIMIENTO()); 
                prepStmt2.setLong(2, c.getK_NUM_CUENTA());
            }
            res.close();            
            prepStmt2.executeUpdate();
            conexionDB.getInstance().commit();
            System.out.println("Se actualizó el saldo");
    }

    public movimientoCuenta[] getMovimiento() throws SQLException{
        int registros = 0;
        String strSQL2 = "SELECT count(1) as cont FROM movimiento_cuenta";
        PreparedStatement prepStmt2 = conexion.prepareStatement(strSQL2);
        ResultSet res2 = prepStmt2.executeQuery();
        res2.next();
        registros = res2.getInt("cont");
        res2.close();
        movimientoCuenta[] data2 = new movimientoCuenta[registros];
        String strSQL = "SELECT K_MOVIMIENTO, V_MOVIMIENTO, I_TIPO, F_MOVIMIENTO, K_NUM_CUENTA FROM movimiento_cuenta";
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        ResultSet res = prepStmt.executeQuery();
        res = prepStmt.executeQuery();
        int i = 0;
        while(res.next()){
            data2[i] = new movimientoCuenta();
            data2[i].setK_MOVIMIENTO(res.getLong("K_MOVIMIENTO"));
            data2[i].setV_MOVIMIENTO(res.getFloat("V_MOVIMIENTO"));
            data2[i].setI_TIPO(res.getString("I_TIPO"));
            data2[i].setF_MOVIMIENTO(res.getTimestamp("F_MOVIMIENTO").toString());
            data2[i].setK_NUM_CUENTA(res.getLong("K_NUM_CUENTA"));
            i++;
        }
        res.close();
        return data2;
    }

    public void actualizar(movimientoCuenta c) throws SQLException {
        String strSQL = "UPDATE movimiento_cuenta SET V_MOVIMIENTO=?, I_TIPO=?, F_MOVIMIENTO=?, K_NUM_CUENTA=? WHERE K_MOVIMIENTO = ?";
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setFloat(1, c.getV_MOVIMIENTO()); 
        prepStmt.setString(2, c.getI_TIPO()); 
        prepStmt.setDate(3, Date.valueOf(c.getF_MOVIMIENTO())); 
        prepStmt.setLong(4, c.getK_NUM_CUENTA()); 
        prepStmt.setLong(5, c.getK_MOVIMIENTO()); 
        prepStmt.executeUpdate();
        prepStmt.close();
        conexionDB.getInstance().commit();
    }
    
}
