package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import datos.*;
import java.sql.Date;

public class daoCuentaAhorro{
    Connection conexion = conexionDB.getInstance().tomarConexion();
    
    public daoCuentaAhorro(){
        conexionDB.getInstance().liberarConexion();
    }
    
    public void insertar(cuentaAhorro c) throws SQLException {
        String strSQL = "INSERT INTO cuenta_ahorro ( K_NUM_CUENTA, I_ESTADO, V_SALDO, F_APERTURA, K_IDENTIFICACION) VALUES(?,?,?,?,?)";
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setLong(1, c.getK_NUM_CUENTA()); 
        prepStmt.setString(2, c.getI_ESTADO()); 
        prepStmt.setFloat(3, c.getV_SALDO()); 
        prepStmt.setDate(4, Date.valueOf(c.getF_APERTURA())); 
        prepStmt.setLong(5, c.getK_IDENTIFICACION()); 
        prepStmt.executeUpdate();
        prepStmt.close();
        conexionDB.getInstance().commit();
        conexionDB.getInstance().liberarConexion();
        
    }

    public void eliminar(cuentaAhorro c) throws SQLException {
        String strSQL = "DELETE FROM cuenta_ahorro WHERE K_NUM_CUENTA = ?";
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setLong(1,c.getK_NUM_CUENTA()); 
        prepStmt.executeUpdate();
        prepStmt.close();
        conexionDB.getInstance().commit();
    }

    public cuentaAhorro[] getCuenta() throws SQLException{
        int registros = 0;
        String strSQL2 = "SELECT count(1) as cont FROM cuenta_ahorro";
        PreparedStatement prepStmt2 = conexion.prepareStatement(strSQL2);
        ResultSet res2 = prepStmt2.executeQuery();
        res2.next();
        registros = res2.getInt("cont");
        res2.close();
        cuentaAhorro[] data2 = new cuentaAhorro[registros];
        String strSQL = "SELECT K_NUM_CUENTA, I_ESTADO, V_SALDO, F_APERTURA, K_IDENTIFICACION FROM cuenta_ahorro";
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        ResultSet res = prepStmt.executeQuery();
        res = prepStmt.executeQuery();
        int i = 0;
        while(res.next()){
            data2[i] = new cuentaAhorro();
            data2[i].setK_NUM_CUENTA(res.getLong("K_NUM_CUENTA"));
            data2[i].setI_ESTADO(res.getString("I_ESTADO"));
            data2[i].setV_SALDO(res.getFloat("V_SALDO"));
            data2[i].setF_APERTURA(res.getDate("F_APERTURA").toString());
            data2[i].setK_IDENTIFICACION(res.getLong("K_IDENTIFICACION"));
            i++;
        }
        res.close();
        return data2;
    }

    public void actualizar(cuentaAhorro c) throws CaException, SQLException {
        String strSQL = "UPDATE cuenta_ahorro SET I_ESTADO=?, V_SALDO=?, F_APERTURA=?, K_IDENTIFICACION=? WHERE K_NUM_CUENTA = ?";
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setString(1, c.getI_ESTADO()); 
        prepStmt.setFloat(2, c.getV_SALDO()); 
        prepStmt.setString(3, c.getF_APERTURA()); 
        prepStmt.setLong(4, c.getK_IDENTIFICACION()); 
        prepStmt.setLong(5,c.getK_NUM_CUENTA()); 
        prepStmt.executeUpdate();
        prepStmt.close();
        conexionDB.getInstance().commit();
    }
    
}
