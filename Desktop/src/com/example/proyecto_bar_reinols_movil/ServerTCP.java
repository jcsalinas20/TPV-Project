package com.example.proyecto_bar_reinols_movil;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.IServerListener;
import net.sf.lipermi.net.Server;

interface TestService {

	public ArrayList<String> getCamareros(String data);

    public ArrayList<byte[]> getImagenCamareros();

	public int getNumMesas(String data);

	public ArrayList<String> getCategorias(String data);

	public void envioDeProductos(String data, String camarero, int mesa, ArrayList<String> productos);

	public ArrayList<String> getComandaMesa(String data, int num_mesa);

	public void guardarImagen(String data, byte[] byteImage, String nombre, String password);

	public ArrayList<byte[]> imagenesCate();

	public ArrayList<byte[]> imagenesProd( );

}

public class ServerTCP extends Thread implements TestService {

	ServerSocket ss;
	Socket socket;
	DataOutputStream dos;
	Connection conn = null;
	DataInputStream dis;

	@Override
	public void run() {
		Server server = new Server();
		CallHandler callHandler = new CallHandler();
		try {
			callHandler.registerGlobal(TestService.class, this);
			server.addServerListener(new IServerListener() {
				public void clientConnected(Socket socket) {
					System.out.print(socket.getInetAddress() + ": ");
				}

				public void clientDisconnected(Socket socket) {
				}
			});
			server.bind(5000, callHandler);
			System.out.println("Server listening");
		} catch (LipeRMIException | IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public ArrayList<String> getCamareros(String data) {
		System.out.println(data);
		ArrayList<String> camarero = new ArrayList<String>();
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Herramientas.URL, Herramientas.USER, Herramientas.PASS);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Camareros;");
			
			while (rs.next()) {
				camarero.add(rs.getObject("nombre").toString());
			}

			return camarero;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
    public ArrayList<byte[]> getImagenCamareros(){
		ArrayList<byte[]> imagenes = new ArrayList<byte[]>();
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Herramientas.URL, Herramientas.USER, Herramientas.PASS);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Camareros;");
			
			while (rs.next()) {
				imagenes.add(rs.getBlob("imagen").getBytes(1, (int) rs.getBlob("imagen").length()));
			}

			return imagenes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }

	@Override
	public int getNumMesas(String data) {
		System.out.println(data);
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Herramientas.URL, Herramientas.USER, Herramientas.PASS);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Mesa_Configuracion;");

			String numMesas = "";
			while (rs.next()) {
				numMesas = rs.getObject("num_mesa").toString();
			}
			return Integer.parseInt(numMesas);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<String> getCategorias(String data) {
		System.out.println(data);
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Herramientas.URL, Herramientas.USER, Herramientas.PASS);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT c.nombre, p.id_producto, p.nombre, p.precio FROM productos p, categorias c WHERE p.id_categoria = c.id_categoria ORDER BY p.id_producto ASC;");

			ArrayList<String> productos = new ArrayList<String>();

			while (rs.next()) {
				productos.add(rs.getObject(1).toString() + "-" + rs.getObject(2).toString() + "-"
						+ rs.getObject(3).toString() + "-" + rs.getObject(4).toString());
			}

			return productos;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void envioDeProductos(String data, String camarero, int mesa, ArrayList<String> productos) {
		System.out.println(data);
		Connection conn = null;
		PreparedStatement pstmnt = null;
		Statement statement = null;
		try {
			conn = DriverManager.getConnection(Herramientas.URL, Herramientas.USER, Herramientas.PASS);
			statement = conn.createStatement();

//			sacar el id_camarero
			ResultSet rs = statement
					.executeQuery("SELECT id_camarero FROM camareros WHERE nombre LIKE '" + camarero + "';");
			String id_camarero = "";
			while (rs.next()) {
				id_camarero = rs.getObject(1).toString();
			}

//			comprobar que la mesa no este ocupada
			boolean mesaOcupada = false;
			rs = statement.executeQuery("SELECT * FROM comanda WHERE id_mesa = " + mesa + ";");
			while (rs.next()) {
				mesaOcupada = true;
			}

			if (!mesaOcupada) {
//				nueva comanda
				pstmnt = conn.prepareStatement(
						"INSERT INTO comanda VALUES (null, " + id_camarero + ", " + mesa + ", now());");
				pstmnt.executeUpdate();
			} else {
				pstmnt = conn.prepareStatement(
						"UPDATE comanda SET id_camarero = " + id_camarero + " WHERE id_mesa = " + mesa + ";");
				pstmnt.executeUpdate();
			}
//			sacar el id_comanda
			rs = statement.executeQuery("SELECT id_comanda FROM comanda WHERE id_mesa = " + mesa + ";");
			String id_comanda = "";
			while (rs.next()) {
				id_comanda = rs.getObject(1).toString();
			}

//			sacar la fecha
			rs = statement.executeQuery("SELECT fecha FROM comanda WHERE id_mesa = " + mesa + ";");
			String fecha = "";
			while (rs.next()) {
				fecha = rs.getObject(1).toString();
			}

			if (productos.isEmpty()) {
				pstmnt = conn.prepareStatement("DELETE FROM comanda_cantidad WHERE id_comanda = " + id_comanda + ";");
				pstmnt.executeUpdate();
			} else {
//			nueva comanda_cantidad
				for (int i = 0; i < productos.size(); i++) {
					boolean productoCambiado = true;
					String[] valores = productos.get(i).split("-");
					String nombre = valores[0];
					float precio = Float.parseFloat(valores[1]);
					int cantidad = Integer.parseInt(valores[2]);

					rs = statement.executeQuery(
							"SELECT nombre, cantidad FROM comanda_cantidad WHERE id_comanda = " + id_comanda + ";");
					while (rs.next()) {
						if (nombre.equalsIgnoreCase(rs.getObject(1).toString()) && cantidad == rs.getInt(2)) {
							productoCambiado = false;
						}
					}

					boolean productoEliminado = false;
					String productoAeliminar = "";
					rs = statement
							.executeQuery("SELECT nombre FROM comanda_cantidad WHERE id_comanda = " + id_comanda + ";");
					while (rs.next()) {
						for (int j = 0; j < productos.size(); j++) {
							String[] nombreProducto = productos.get(j).split("-");
							if (!rs.getObject(1).toString().equals(nombreProducto[0])) {
								productoAeliminar = rs.getObject(1).toString();
								productoEliminado = true;
							} else {
								productoEliminado = false;
								break;
							}
						}

						if (productoEliminado) {
							pstmnt = conn.prepareStatement("DELETE FROM comanda_cantidad WHERE id_comanda = "
									+ id_comanda + " AND nombre LIKE '" + productoAeliminar + "';");
							pstmnt.executeUpdate();
						}
					}

					if (productoCambiado) {
						pstmnt = conn.prepareStatement("DELETE FROM comanda_cantidad WHERE id_comanda = " + id_comanda
								+ " AND nombre LIKE '" + nombre + "';");
						pstmnt.executeUpdate();

						pstmnt = conn.prepareStatement("INSERT INTO comanda_cantidad VALUES (" + id_comanda + ", '"
								+ nombre + "', " + precio + ", " + cantidad + ", 0, 0);");
						pstmnt.executeUpdate();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<String> getComandaMesa(String data, int num_mesa) {
		System.out.println(data);
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Herramientas.URL, Herramientas.USER, Herramientas.PASS);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT cc.nombre, cc.precio, cc.cantidad FROM comanda_cantidad cc, comanda c WHERE cc.id_comanda = c.id_comanda AND c.id_mesa = "
							+ num_mesa + ";");

			ArrayList<String> productos = new ArrayList<String>();

			while (rs.next()) {
				productos.add(rs.getObject(1).toString() + "-" + rs.getObject(2).toString() + "-"
						+ rs.getObject(3).toString());
			}

			return productos;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void guardarImagen(String data, byte[] byteImage, String nombre, String password) {
		System.out.println(data);
		String insert = "call insertarCamarero(?, ?, ?)";
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Herramientas.URL, Herramientas.USER, Herramientas.PASS);
			CallableStatement call = conn.prepareCall(insert);
			call.setString(1, nombre);
			call.setString(2, password);
			call.setBlob(3, new SerialBlob(byteImage));
			call.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public ArrayList<byte[]> imagenesCate() {
		ArrayList<byte[]> imagenes = new ArrayList<byte[]>();
		try {
			conn = DriverManager.getConnection(Herramientas.URL, Herramientas.USER, Herramientas.PASS);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT imagen FROM categorias;");
			
			while (rs.next()) {
				imagenes.add(rs.getBlob("imagen").getBytes(1, (int) rs.getBlob("imagen").length()));
			}

			return imagenes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<byte[]> imagenesProd() {
		ArrayList<byte[]> imagenes = new ArrayList<byte[]>();
		try {
			conn = DriverManager.getConnection(Herramientas.URL, Herramientas.USER, Herramientas.PASS);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT imagen FROM productos ORDER BY id_producto ASC;");
			
			while (rs.next()) {
				imagenes.add(rs.getBlob("imagen").getBytes(1, (int) rs.getBlob("imagen").length()));
			}

			return imagenes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}