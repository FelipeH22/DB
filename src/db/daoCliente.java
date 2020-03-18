package db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import datos.*;
import java.sql.Date;

public class daoCliente{

    cliente cliente = new cliente();

    public void insertar() throws CaException {
      try {

        String strSQL = "INSERT INTO cliente (K_IDENTIFICACION, I_TIPO_IDENTIFICACION, N_NOMBRE, N_APELLIDO, O_DIRECCION, O_TELEFONO, I_SEXO, F_NACIMIENTO, O_OCUPACION, O_CORREO_ELECTRONICO, V_INGRESO_MENSUAL) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        Connection conexion = conexionDB.getInstance().tomarConexion();
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setLong(1,cliente.getK_IDENTIFICACION()); 
        prepStmt.setString(2, cliente.getI_TIPO_IDENTIFICACION()); 
        prepStmt.setString(3, cliente.getN_NOMBRE()); 
        prepStmt.setString(4, cliente.getN_APELLIDO()); 
        prepStmt.setString(5, cliente.getO_DIRECCION()); 
        prepStmt.setLong(6,cliente.getO_TELEFONO()); 
        prepStmt.setString(7,String.valueOf(cliente.getI_SEXO())); 
        prepStmt.setDate(8,Date.valueOf(cliente.getF_NACIMIENTO())); 
        prepStmt.setString(9,cliente.getO_DIRECCION());
        prepStmt.setString(10,cliente.getO_CORREO_ELECTRONICO());
        prepStmt.setFloat(11,cliente.getV_INGRESO_MENSUAL()); 
        prepStmt.executeUpdate();
        prepStmt.close();
        conexionDB.getInstance().commit();
      } catch (SQLException e) {
           throw new CaException( "Cliente", "No se pudo crear el registro"+ e.getMessage());
      }  finally {
         conexionDB.getInstance().liberarConexion();
      }
    }

    public void eliminar() {
        try{
            String strSQL = "DELETE FROM cliente WHERE K_IDENTIFICACION = ?";
            Connection conexion = conexionDB.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1,cliente.getK_IDENTIFICACION()); 
            prepStmt.executeUpdate();
            prepStmt.close();
            conexionDB.getInstance().commit();
        }catch(SQLException e){
            System.out.println(e);
        }
    }


    public cliente[] get() throws SQLException{
        int registros = 0;
        String strSQL2 = "SELECT count(1) as cont FROM cliente";
        Connection conexion = conexionDB.getInstance().tomarConexion();
        PreparedStatement prepStmt2 = conexion.prepareStatement(strSQL2);
        ResultSet res2 = prepStmt2.executeQuery();
        res2.next();
        registros = res2.getInt("cont");
        res2.close();
        cliente[] data = new cliente[registros];
        String strSQL = "SELECT K_IDENTIFICACION, I_TIPO_IDENTIFICACION, N_NOMBRE, N_APELLIDO, O_DIRECCION, O_TELEFONO, I_SEXO, F_NACIMIENTO, O_OCUPACION, O_CORREO_ELECTRONICO, V_INGRESO_MENSUAL FROM cliente";
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        ResultSet res = prepStmt.executeQuery();
        res = prepStmt.executeQuery();
        int i = 0;
        while(res.next()){
            data[i] = new cliente();
            data[i].setK_IDENTIFICACION(res.getLong("K_IDENTIFICACION"));
            data[i].setI_TIPO_IDENTIFICACION(res.getString("I_TIPO_IDENTIFICACION"));
            data[i].setN_NOMBRE(res.getString("N_NOMBRE"));
            data[i].setN_APELLIDO(res.getString("N_APELLIDO"));
            data[i].setO_DIRECCION(res.getString("O_DIRECCION"));
            data[i].setO_TELEFONO(res.getLong("O_TELEFONO"));
            data[i].setI_SEXO(res.getString("I_SEXO").charAt(0));
            data[i].setF_NACIMIENTO(res.getDate("F_NACIMIENTO").toString());
            data[i].setO_OCUPACION(res.getString("O_OCUPACION"));
            data[i].setO_CORREO_ELECTRONICO(res.getString("O_CORREO_ELECTRONICO"));
            data[i].setV_INGRESO_MENSUAL(res.getFloat("V_INGRESO_MENSUAL"));
            
            i++;
        }
        res.close();
        return data;
    }

    public void actualizar() throws CaException {
        try {
            String strSQL = "UPDATE cliente SET I_TIPO_IDENTIFICACION=?, N_NOMBRE=?, N_APELLIDO=?, O_DIRECCION=?, O_TELEFONO=?, I_SEXO=?, F_NACIMIENTO=?, O_OCUPACION=?, O_CORREO_ELECTRONICO=?, V_INGRESO_MENSUAL=? WHERE K_IDENTIFICACION = ?";
            Connection conexion = conexionDB.getInstance().tomarConexion();
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setString(1, cliente.getI_TIPO_IDENTIFICACION()); 
            prepStmt.setString(2, cliente.getN_NOMBRE()); 
            prepStmt.setString(3, cliente.getN_APELLIDO()); 
            prepStmt.setString(4, cliente.getO_DIRECCION()); 
            prepStmt.setLong(5,cliente.getO_TELEFONO()); 
            prepStmt.setString(6,String.valueOf(cliente.getI_SEXO())); 
            prepStmt.setDate(7,Date.valueOf(cliente.getF_NACIMIENTO())); 
            prepStmt.setString(8,cliente.getO_DIRECCION());
            prepStmt.setString(9,cliente.getO_CORREO_ELECTRONICO());
            prepStmt.setFloat(10,cliente.getV_INGRESO_MENSUAL()); 
            prepStmt.setFloat(11,cliente.getK_IDENTIFICACION()); 
            prepStmt.executeUpdate();
            prepStmt.close();
            conexionDB.getInstance().commit();
        } catch (SQLException e) {
             throw new CaException( "cliente", "No se pudo actualizar el cliente"+ e.getMessage());
        }  finally {
           conexionDB.getInstance().liberarConexion();
        }
        
    }
    
}
