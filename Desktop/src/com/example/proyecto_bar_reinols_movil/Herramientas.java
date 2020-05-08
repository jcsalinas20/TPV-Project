package com.example.proyecto_bar_reinols_movil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Herramientas {

	static String USER = "root"; // zuk5X2TjSn
	static String PASS = ""; // ioXxebaOEY
	static String URL = "jdbc:mysql://localhost/Bar";
	static Connection conn;
	// jdbc:mysql://remotemysql.com:3306/zuk5X2TjSn?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"

	/*
	 * 
	 * METODOS PARA COGER CATEGORIAS, PRODUCTOS, NUM MESA Y CAMAREROS
	 * 
	 */

	static ResultSet getCatSQL() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);

			return conn.prepareStatement("SELECT nombre, imagen FROM Categorias").executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al conectar a la Base de Datos \rError: " + e.getMessage());
			return null;
		}
	}

	static ResultSet getProdsSQL(String cat) {

		try {
			conn = DriverManager.getConnection(URL, USER, PASS);

			return conn.prepareStatement(
					"SELECT nombre, imagen FROM Productos WHERE id_categoria = (SELECT id_categoria FROM Categorias WHERE nombre = \""
							+ cat + "\")")
					.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al conectar a la Base de Datos \rError: " + e.getMessage());
			return null;
		}

	}

	static String cogerNumeroMesas() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT numMesa FROM Mesa_Configuracion;");

			String numMesas = "";
			while (rs.next()) {
				numMesas += rs.getObject("num_mesa");
			}
			return numMesas;
		} catch (SQLException e) {
			System.out.println("Error al recoger datos \rError: " + e.getMessage());
			return "Error cogerNumeroMesas -Información del error en consola-";
		}
	}

	static String nombreCamareros() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Camareros;");

			String camareros = "";
			while (rs.next()) {
				camareros += rs.getObject("nombre") + "-";
			}

			return camareros;
		} catch (SQLException e) {
			System.out.println("Error al recoger datos \rError: " + e.getMessage());
			return "Error nombreCamareros -Información del error en consola-";
		}
	}

	// METODOS CARGAR COMANDAS

	static void cargarCocinas() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT id_comanda FROM Comanda_Cantidad WHERE servido = 0;");

			String id_comandas = "";
			while (rs.next()) {
				if (!id_comandas.contains(String.valueOf(rs.getInt(1))))
					id_comandas += "-" + rs.getInt(1);
			}

			for (String x : id_comandas.substring(1).split("-")) {
				Cuina c = new Cuina(Integer.valueOf(x));
				VentanaPrincipal.tabbedPane.add(c);
				VentanaPrincipal.tabbedPane.setTitleAt(VentanaPrincipal.tabbedPane.getTabCount() - 1,
						c.lblMesa.getText());
			}

		} catch (SQLException e) {
			System.out.println("Error al recoger datos (cargarComandas) \rError: " + e.getMessage());
		}
	}

	static ResultSet devolverComanda(int id_comanda) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			Statement st = conn.createStatement();
			return st.executeQuery(
					"SELECT nombre, cantidad, preparado FROM Comanda_Cantidad WHERE servido = 0 AND id_comanda = "
							+ id_comanda + " ;");

		} catch (SQLException e) {
			System.out.println("Error al recoger datos (devolverComanda) \rError: " + e.getMessage());
			return null;
		}
	}

	static boolean comprobarFactura(int id_comanda) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT id_comanda FROM Factura WHERE id_comanda = " + id_comanda + " ;");

			if (rs.next())
				return false;
			else
				return true;

		} catch (SQLException e) {
			System.out.println("Error al recoger datos (devolverComandaBarra) \rError: " + e.getMessage());
			return false;
		}
	}

	static ResultSet devolverComandaBarra(int id_comanda) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			Statement st = conn.createStatement();
			return st.executeQuery(
					"SELECT nombre, cantidad, precio FROM Comanda_Cantidad WHERE id_comanda = " + id_comanda + " ;");

		} catch (SQLException e) {
			System.out.println("Error al recoger datos (devolverComandaBarra) \rError: " + e.getMessage());
			return null;
		}
	}

	static int getId_ComandaPorMesa(int numMesa) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT c.id_comanda FROM Comanda c, Comanda_Cantidad cc WHERE c.id_mesa = "
					+ numMesa + " AND cc.servido = 0;");

			rs.next();

			return rs.getInt(1);

		} catch (SQLException e) {
			System.out.println("Error al recoger datos (devolverComanda) \rError: " + e.getMessage());
			return 0;
		}

	}

	static String[] devolverMetaInfo(int id_comanda) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);
			Statement st = conn.createStatement();

			String[] result = new String[3];

			ResultSet rs = st
					.executeQuery("SELECT id_mesa, fecha FROM Comanda WHERE id_comanda = " + id_comanda + " ;");

			rs.next();

			result[0] = String.valueOf(rs.getInt(1)); // id taula

			result[2] = rs.getString(2); // data

			rs = st.executeQuery(
					"SELECT nombre FROM Camareros WHERE id_camarero = (SELECT id_camarero FROM Comanda WHERE id_comanda = "
							+ id_comanda + ")");

			rs.next();

			result[1] = rs.getString(1);

			return result;

		} catch (SQLException e) {
			System.out.println("Error al recoger datos (devolverMetaInfo) \rError: " + e.getMessage());
			return null;
		}
	}

	static void preparadoTrue(int id_comanda, String nomProd) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);

			conn.createStatement().executeUpdate("UPDATE Comanda_Cantidad SET preparado = 1 WHERE nombre = \"" + nomProd
					+ "\" AND id_comanda = " + id_comanda);

		} catch (SQLException e) {
			System.out.println("Error al modificar Servido (preparadoTrue) \rError: " + e.getMessage());
		}
	}

	static void preparadoFalse(int id_comanda, String nomProd) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);

			conn.createStatement().executeUpdate("UPDATE Comanda_Cantidad SET preparado = 0 WHERE nombre = \"" + nomProd
					+ "\" AND id_comanda = " + id_comanda);

		} catch (SQLException e) {
			System.out.println("Error al modificar Servido (preparadoFalse) \rError: " + e.getMessage());
		}
	}

	static void servidoTrue(int id_comanda, String nomProd) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);

			conn.createStatement().executeUpdate("UPDATE Comanda_Cantidad SET servido = 1 WHERE nombre = \"" + nomProd
					+ "\" AND id_comanda = " + id_comanda);

		} catch (SQLException e) {
			System.out.println("Error al modificar Servido (servidoTrue) \rError: " + e.getMessage());
		}
	}

	static void servidoFalse(int id_comanda, String nomProd) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);

			conn.createStatement().executeUpdate("UPDATE Comanda_Cantidad SET servido = 0 WHERE nombre = \"" + nomProd
					+ "\" AND id_comanda = " + id_comanda);

		} catch (SQLException e) {
			System.out.println("Error al modificar Servido (servidoFalse) \rError: " + e.getMessage());
		}
	}

	static void cobrar(int id_comanda, float precio_total) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);

			conn.createStatement().executeUpdate("INSERT INTO Factura (id_factura, id_comanda, precio_total) VALUES ("
					+ generarIdFactura(id_comanda) + ", " + id_comanda + " , " + precio_total + ")");

		} catch (SQLException e) {
			System.out.println("Error al cobrar (cobrar) \rError: " + e.getMessage());
		}
	}

	static float generarIdFactura(int id_comanda) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);

			ResultSet rs = conn.createStatement()
					.executeQuery("SELECT id_mesa, fecha FROM Comanda WHERE id_comanda = " + id_comanda);

			rs.next();

			return Float.parseFloat(
					rs.getString(2).replace("-", "").replace(":", "").replace(" ", "") + rs.getInt(1) + id_comanda);

		} catch (SQLException e) {
			System.out.println("Error al generar id (generarIdFactura) \rError: " + e.getMessage());
			return 1;
		}
	}

	static boolean revisarPreparadoYServido(int id_comanda) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);

			ResultSet rs = conn.createStatement()
					.executeQuery("SELECT preparado, servido FROM Comanda_Cantidad WHERE id_comanda = " + id_comanda);

			while (rs.next()) {
				if (!rs.getBoolean(1))
					return false;
				else if (!rs.getBoolean(2))
					return false;
			}

			return true;
		} catch (SQLException e) {
			System.out.println("Error al revisarChecks (revisarPreparadoYServido) \rError: " + e.getMessage());
			return false;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////// NO
	////////////////////////////////////////////////////////////////////////////////////////////////////////////// SQL

}
