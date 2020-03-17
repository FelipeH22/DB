package db;
import java.sql.*;
public class conexionDB {
    	/**
	 * Instancia del ServiceLocator
	 */
	private static conexionDB instance = null;

	/**
	 * Conexion compartida a la Base de Datos
	 */
	private Connection conexion = null;

	/**
	 * Bandera que indica el estado de la conexion
	 */
	private boolean conexionLibre = true;

	/**
	 * @return instancia del ServiceLocator para el manejo de la conexion
	 */
	public static conexionDB getInstance() {
		if (instance == null) {
			try {
				instance = new conexionDB();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return instance;
	}

	/**
	 * @throws Exception
	 *             dice si no se pudo crear la conexion
	 */
	conexionDB() throws Exception {
		try {
			// Se registra el Driver y se crea la conexion
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
                        String usuario = "minibanco";
                        String password = "minibanco";  
                        Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
                        conexion = DriverManager.getConnection(url, usuario, password);
                        System.out.println("Conectado");
		  conexion.setAutoCommit(false);
		} catch (Exception e) {
			 throw new CaException("ServiceLocator","ERROR_CONEXION_BD "+ e);
		}
	}

	/**
	 * Toma la conexion para que ningun otro proceso la puedan utilizar
	 * @return da la conexion a la base de datos
	 */
	public synchronized Connection tomarConexion() {
		while (!conexionLibre) {
			try {
			  wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		conexionLibre = false;
		notify();
		return conexion;
	}

	/**
	 * Libera la conexion de la bases de datos para que ningun otro
	 * proceso la pueda utilizar
	 */
	public synchronized void liberarConexion() {
		while (conexionLibre) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		conexionLibre = true;
		notify();
	}

	/**
	 * Cierra la conexion a la base de datos cuando se termina de
	 * ejecutar el programa
	 */
	public void close() {
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Realiza los cambios en la base de datos. Con este metodo
	 * se asegura que no halla inconsitencias en el modelo relacional
	 * de la Base de datos.
	 * 
	 * Se utiliza cuando el procedimiento de insercion es terminado
	 * correctamente y se asegura que los datos en el modelo estan bien
	 * relacionados.
	 */
	public void commit() {
		try {
			conexion.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
        
        public void consulta(){
            
        }

	/**
	 * Deshace los cambios en la base de datos. Con este metodo
	 * se asegura que no halla inconsitencias en el modelo relacional
	 * de la Base de datos.
	 * 
	 * Se utiliza por lo general cuando se devuelve una Exepcion.
	 * Los procedimientos intermedios no deberia quedar almacenados en la
	 * base de datos. 
	 */

	public void rollback() {
		try {
			conexion.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
