package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import datos.*;
import java.sql.Date;

public class daoEventoCuenta{

    Connection conexion;
    
    public daoEventoCuenta(){
       conexionDB.getInstance().liberarConexion();
       conexion =  conexionDB.getInstance().tomarConexion();
    }  
    
    public void insertar(eventoCuenta c) throws SQLException {
            String strSQL = "INSERT INTO evento_cuenta ( K_EVENTO, I_TIPO_EVENTO, F_EVENTO, O_DESCRIPCION, K_NUM_CUENTA) VALUES(?,?,?,?,?)";
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1, c.getK_EVENTO()); 
            prepStmt.setString(2, c.getI_TIPO_EVENTO()); 
            prepStmt.setDate(3, Date.valueOf(c.getF_EVENTO())); 
            prepStmt.setString(4, c.getO_DESCRIPCION()); 
            prepStmt.setLong(5, c.getK_NUM_CUENTA()); 
            prepStmt.executeUpdate();
            prepStmt.close();
            //////////////////////////
            String strSQL2 = "UPDATE cuenta_ahorro SET I_ESTADO=? WHERE K_NUM_CUENTA = ?";
            PreparedStatement prepStmt2 = conexion.prepareStatement(strSQL2);
            switch (c.getI_TIPO_EVENTO()) {
            case "ACTIVACION":
                prepStmt2.setString(1, "ACTIVA");
                break;
            case "INACTIVACION":
                prepStmt2.setString(1, "INACTIVA");
                break;
            case "BLOQUEO":
                prepStmt2.setString(1, "BLOQUEADA");
                break;
            default:
                break;
            }
            prepStmt2.setLong(2, c.getK_NUM_CUENTA());        
            prepStmt2.executeUpdate();
            conexionDB.getInstance().commit();
            System.out.println("Se actualizó el estado");
            conexionDB.getInstance().commit();
    }

    public eventoCuenta[] getEvento() throws SQLException{
        int registros = 0;
        String strSQL2 = "SELECT count(1) as cont FROM evento_cuenta";
        PreparedStatement prepStmt2 = conexion.prepareStatement(strSQL2);
        ResultSet res2 = prepStmt2.executeQuery();
        res2.next();
        registros = res2.getInt("cont");
        res2.close();
        eventoCuenta[] data2 = new eventoCuenta[registros];
        String strSQL = "SELECT K_EVENTO, I_TIPO_EVENTO, F_EVENTO, O_DESCRIPCION, K_NUM_CUENTA FROM evento_cuenta";
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        ResultSet res = prepStmt.executeQuery();
        res = prepStmt.executeQuery();
        int i = 0;
        while(res.next()){
            data2[i] = new eventoCuenta();
            data2[i].setK_EVENTO(res.getLong("K_EVENTO"));
            data2[i].setI_TIPO_EVENTO(res.getString("I_TIPO_EVENTO"));            
            data2[i].setF_EVENTO(res.getDate("F_EVENTO").toString());
            data2[i].setO_DESCRIPCION(res.getString("O_DESCRIPCION"));
            data2[i].setK_NUM_CUENTA(res.getLong("K_NUM_CUENTA"));
            i++;
        }
        res.close();
        return data2;
    }
}
