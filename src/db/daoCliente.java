package db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import datos.*;
import java.sql.Date;

public class daoCliente{
    Connection conexion = conexionDB.getInstance().tomarConexion();
    //cliente cliente = new cliente();

    public void insertar(cliente c) throws SQLException {        
            String strSQL = "INSERT INTO cliente (K_IDENTIFICACION, I_TIPO_IDENTIFICACION, N_NOMBRE, N_APELLIDO, O_DIRECCION, O_TELEFONO, I_SEXO, F_NACIMIENTO, O_OCUPACION, O_CORREO_ELECTRONICO, V_INGRESO_MENSUAL) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setLong(1,c.getK_IDENTIFICACION()); 
            prepStmt.setString(2, c.getI_TIPO_IDENTIFICACION()); 
            prepStmt.setString(3, c.getN_NOMBRE()); 
            prepStmt.setString(4, c.getN_APELLIDO()); 
            prepStmt.setString(5, c.getO_DIRECCION()); 
            prepStmt.setLong(6,c.getO_TELEFONO()); 
            prepStmt.setString(7,String.valueOf(c.getI_SEXO())); 
            prepStmt.setDate(8,Date.valueOf(c.getF_NACIMIENTO())); 
            prepStmt.setString(9,c.getO_OCUPACION());
            prepStmt.setString(10,c.getO_CORREO_ELECTRONICO());
            prepStmt.setFloat(11,c.getV_INGRESO_MENSUAL()); 
            prepStmt.executeUpdate();
            System.out.println("Se creó el registro en la BD");
            prepStmt.close();            
            conexionDB.getInstance().commit();
            conexionDB.getInstance().liberarConexion();
    }

    public void eliminar(cliente c) throws SQLException {
        String strSQL = "DELETE FROM cliente WHERE K_IDENTIFICACION = ?";
        PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
        prepStmt.setLong(1,c.getK_IDENTIFICACION()); 
        prepStmt.executeUpdate();
        prepStmt.close();
        conexionDB.getInstance().commit();
    }


    public cliente[] get() throws SQLException{
        int registros = 0;
        String strSQL2 = "SELECT count(1) as cont FROM cliente";
<<<<<<< HEAD
=======
        Connection conexion = conexionDB.getInstance().tomarConexion();
>>>>>>> master
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
<<<<<<< HEAD
            data[i].setO_TELEFONO(res.getLong("O_TELEFONO"));
=======
            //data[i].setO_TELEFONO(res.getInt("O_TELEFONO"));
>>>>>>> master
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

    public void actualizar(cliente c) throws SQLException {
            String strSQL = "UPDATE cliente SET I_TIPO_IDENTIFICACION=?, N_NOMBRE=?, N_APELLIDO=?, O_DIRECCION=?, O_TELEFONO=?, I_SEXO=?, F_NACIMIENTO=?, O_OCUPACION=?, O_CORREO_ELECTRONICO=?, V_INGRESO_MENSUAL=? WHERE K_IDENTIFICACION = ?";
            PreparedStatement prepStmt = conexion.prepareStatement(strSQL);
            prepStmt.setString(1, c.getI_TIPO_IDENTIFICACION()); 
            prepStmt.setString(2, c.getN_NOMBRE()); 
            prepStmt.setString(3, c.getN_APELLIDO()); 
            prepStmt.setString(4, c.getO_DIRECCION()); 
            prepStmt.setLong(5,c.getO_TELEFONO()); 
            prepStmt.setString(6,String.valueOf(c.getI_SEXO())); 
            prepStmt.setDate(7,Date.valueOf(c.getF_NACIMIENTO())); 
            prepStmt.setString(8,c.getO_OCUPACION());
            prepStmt.setString(9,c.getO_CORREO_ELECTRONICO());
            prepStmt.setFloat(10,c.getV_INGRESO_MENSUAL()); 
            prepStmt.setLong(11,c.getK_IDENTIFICACION()); 
            prepStmt.executeUpdate();
            prepStmt.close();
            conexionDB.getInstance().commit();
            conexionDB.getInstance().liberarConexion();
    }

    
}
