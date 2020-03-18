package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import datos.*;
import java.sql.Date;

public class daoCuentaAhorro{

    cuentaAhorro cuenta = new cuentaAhorro();    
    public void insertar() throws CaException {
        try {
            String strSQL = "INSERT INTO cuenta_ahorro ( K_NUM_CUENTA, I_ESTADO, V_SALDO, F_APERTURA, K_IDENTIFICACION) VALUES(?,?,?,?,?,)";
            Connection conexion = conexionDB.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1,cuenta.getK_NUM_CUENTA()); 
            prepStmt.setString(2, cuenta.getI_ESTADO()); 
            prepStmt.setFloat(3, cuenta.getV_SALDO()); 
            prepStmt.setString(4, cuenta.getF_APERTURA()); 
            prepStmt.setLong(5, cuenta.getK_IDENTIFICACION()); 
            prepStmt.executeUpdate();
            prepStmt.close();
            conexionDB.getInstance().commit();
          } catch (SQLException e) {
               throw new CaException( "cuenta de ahorro", "No se pudo crear el registro"+ e.getMessage());
          }  finally {
             conexionDB.getInstance().liberarConexion();
          }
        
    }

    public void eliminar() {
        try{
            String strSQL = "DELETE FROM cuenta_ahorro WHERE K_NUM_CUENTA = ?";
            Connection conexion = conexionDB.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1,cuenta.getK_NUM_CUENTA()); 
            prepStmt.executeUpdate();
            prepStmt.close();
            conexionDB.getInstance().commit();
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    public cuentaAhorro[] getCuenta() throws SQLException{
        int registros = 0;
        String strSQL2 = "SELECT count(1) as cont FROM cuenta_ahorro";
        Connection conexion = conexionDB.getInstance().tomarConexion();
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
            data2[i].setK_IDENTIFICACION(res.getLong("K_IDENTIFICACION"));
            data2[i].setI_ESTADO(res.getString("I_ESTADO"));
            data2[i].setV_SALDO(res.getFloat("V_SALDO"));
            data2[i].setF_APERTURA(res.getDate("F_APERTURA").toString());
            data2[i].setK_IDENTIFICACION(res.getLong("K_IDENTIFICACION"));
            System.out.println("Registro creado");
            i++;
        }
        res.close();
        return data2;
    }

    public void actualizar() throws CaException {
        try {
            String strSQL = "UPDATE cuenta_ahorro SET I_ESTADO=?, V_SALDO=?, F_APERTURA=?, K_IDENTIFICACION=? WHERE K_NUM_CUENTA = ?";
            Connection conexion = conexionDB.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setString(1, cuenta.getI_ESTADO()); 
            prepStmt.setFloat(2, cuenta.getV_SALDO()); 
            prepStmt.setString(3, cuenta.getF_APERTURA()); 
            prepStmt.setLong(4, cuenta.getK_IDENTIFICACION()); 
            prepStmt.setLong(5,cuenta.getK_NUM_CUENTA()); 
            prepStmt.executeUpdate();
            prepStmt.close();
            conexionDB.getInstance().commit();
        } catch (SQLException e) {
             throw new CaException( "cuenta", "No se pudo actualizar el registro"+ e.getMessage());
        }  finally {
           conexionDB.getInstance().liberarConexion();
        }
    }
    
}
