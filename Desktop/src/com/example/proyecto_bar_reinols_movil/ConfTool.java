package com.example.proyecto_bar_reinols_movil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfTool {
//	static File conf = new File("config.xml");

	static private Connection conn;
	static final String USER = "root"; // zuk5X2TjSn
	static final String PASS = ""; // ioXxebaOEY
	static final String URL = "jdbc:mysql://localhost/Bar";

	public static void main(String[] args) {
		System.out.println(getIpServerSQL() + " | " + getNomServerSQL() + " | " + (getNumTaulesSQL()));
	}

	/*
	 * #############################################################################
	 * METODOS
	 * #############################################################################
	 */

	public static int getNumTaulesSQL() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);

			ResultSet rs = conn.prepareStatement("SELECT num_mesa FROM Mesa_Configuracion").executeQuery();

			rs.next();

			return rs.getInt(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error devolviendo numMesas - Error: " + e.getMessage());
			return 0;
		}

	}

	static String getIpServerSQL() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);

			ResultSet rs = conn.prepareStatement("SELECT ip_server FROM Mesa_Configuracion").executeQuery();

			rs.next();

			return rs.getString(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error devolviendo ip server - Error: " + e.getMessage());
			return "Error!";
		}

	}

	static String getNomServerSQL() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);

			ResultSet rs = conn.prepareStatement("SELECT nombre_server FROM Mesa_Configuracion").executeQuery();

			rs.next();

			return rs.getString(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error devolviendo nombre server - Error: " + e.getMessage());
			return "Error!";
		}

	}

	static boolean cambiarNumMesasSQL(int nuevoValor) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASS);

			return conn.prepareStatement("UPDATE Mesa_Configuracion SET num_mesa = " + nuevoValor).executeUpdate() != 0
					? true
					: false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error cambiando numMesas - Error: " + e.getMessage());
			return false;
		}

	}

}
