package manejo_bd;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import modelo.Coleccion;
import modelo.Numero;

public class base_datos {

	public static ArrayList<Coleccion> ObtenerColecciones() {

		ArrayList<Coleccion> listaColecciones = new ArrayList<>();

		try {

			String consulta = "select * from colecciones";
			Statement sentencia = vista.Inicio.miConexion.createStatement();
			ResultSet rs = sentencia.executeQuery(consulta);

			while (rs.next()) {
				Coleccion coleccion = new Coleccion();
				coleccion.setId(rs.getInt(1));
				coleccion.setTitulo(rs.getString(2));
				Blob blob = rs.getBlob(3);
				if (blob == null)
					coleccion.setFoto(null);

				else {
					ImageIcon imageIcon = new ImageIcon(blob.getBytes(1L, (int) blob.length()));
					Image image = imageIcon.getImage();
					Image reescalada = image.getScaledInstance(620, 350, java.awt.Image.SCALE_SMOOTH);
					imageIcon = new ImageIcon(reescalada);
					coleccion.setFoto(imageIcon);
				}

				listaColecciones.add(coleccion);
			}
			vista.Inicio.miConexion.commit();
			rs.close();

		} catch (Exception SQLException) {
			SQLException.printStackTrace();
		}

		return listaColecciones;

	}

	public static int Crear_Coleccion(Coleccion coleccion, byte[] bytes) throws IOException {
		int id = coleccion.getId();

		String actualizar = "INSERT INTO colecciones (ID,titulo,foto)" + "values(?,?,?)";

		try (PreparedStatement ps = vista.Inicio.miConexion.prepareStatement(actualizar)) {

			ps.setInt(1, 0);
			System.out.println("Ejecutando Insert" + coleccion.getTitulo());
			ps.setString(2, coleccion.getTitulo());
			ps.setBytes(3, bytes);
			ps.executeUpdate();
			vista.Inicio.miConexion.commit();
			ps.close();

			return 1;

		} catch (Exception SQLException) {
			SQLException.printStackTrace();
			System.out.println("No se pudo modificar la colecci�n");
			return -1;
		}
	}

	public static Coleccion buscar_id(int id) {
		String consulta = "SELECT * FROM colecciones WHERE ID =" + id;

		try {
			Statement sentencia = vista.Inicio.miConexion.createStatement();
			ResultSet rs = sentencia.executeQuery(consulta);

			Coleccion coleccion = new Coleccion();

			while (rs.next()) {
				coleccion.setId(id);
				coleccion.setTitulo(rs.getString(2));
				Blob blob = rs.getBlob(3);
				if (blob != null) {
					ImageIcon imageIcon = new ImageIcon(blob.getBytes(1L, (int) blob.length()));
					Image image = imageIcon.getImage();
					Image reescalada = image.getScaledInstance(620, 350, java.awt.Image.SCALE_SMOOTH);
					imageIcon = new ImageIcon(reescalada);
					coleccion.setFoto(imageIcon);
				} else {
					coleccion.setFoto(null);
				}

			}

			return coleccion;

		} catch (Exception SQLException) {
			SQLException.printStackTrace();
			System.out.println("No se pudo cargar la coleccion");
			return new Coleccion(-1, null, null);

		}
	}

	public static boolean Modificar_coleccion(Coleccion coleccion, byte[] bytes) throws IOException {
		int id = coleccion.getId();

		String actualizar = "UPDATE colecciones SET titulo=? , foto=? WHERE ID = " + id;

		try (PreparedStatement ps = vista.Inicio.miConexion.prepareStatement(actualizar)) {

			ps.setString(1, coleccion.getTitulo());
			ps.setBytes(2, bytes);
			ps.executeUpdate();
			vista.Inicio.miConexion.commit();
			ps.close();

			return true;
		} catch (Exception SQLException) {
			SQLException.printStackTrace();
			System.out.println("No se pudo modificar la colecci�n");
			return false;
		}

	}

	public static int ObtenerID(String titulo_coleccion) {

		ArrayList<Coleccion> listaColecciones = new ArrayList<>();

		try {

			String consulta = "select * from colecciones";
			Statement sentencia = vista.Inicio.miConexion.createStatement();
			ResultSet rs = sentencia.executeQuery(consulta);

			while (rs.next()) {
				Coleccion coleccion = new Coleccion();
				coleccion.setId(rs.getInt(1));
				coleccion.setTitulo(rs.getString(2));
				Blob blob = rs.getBlob(3);

				ImageIcon imageIcon = new ImageIcon(blob.getBytes(1L, (int) blob.length()));
				Image image = imageIcon.getImage();
				Image reescalada = image.getScaledInstance(620, 350, java.awt.Image.SCALE_SMOOTH);
				imageIcon = new ImageIcon(reescalada);
				coleccion.setFoto(imageIcon);

				listaColecciones.add(coleccion);
			}
			vista.Inicio.miConexion.commit();
			rs.close();

		} catch (Exception SQLException) {
			SQLException.printStackTrace();
		}

		int id = 0;
		for (int i = 0; i < listaColecciones.size(); i++) {
			if (listaColecciones.get(i).getTitulo().equals(titulo_coleccion))
				id = listaColecciones.get(i).getId();
		}

		return id;

	}

	public static ArrayList<Numero> ObtenerNumeros() {

		ArrayList<Numero> listaNumeros = new ArrayList<>();

		try {

			String consulta = "select * from numeros";
			Statement sentencia = vista.Inicio.miConexion.createStatement();
			ResultSet rs = sentencia.executeQuery(consulta);

			while (rs.next()) {
				Numero numero = new Numero();
				numero.setId(rs.getInt(1));
				numero.setTitulo(rs.getString(2));
				Blob blob = rs.getBlob(3);

				ImageIcon imageIcon = new ImageIcon(blob.getBytes(1L, (int) blob.length()));
				Image image = imageIcon.getImage();
				imageIcon = new ImageIcon(image);
				numero.setPortada(imageIcon);

				numero.setSinopsis(rs.getString(4));
				numero.setPrecio(rs.getDouble(5));
				numero.setEstado(rs.getString(6));
				numero.setFecha_publicacion(rs.getDate(7));
				numero.setId_coleccion(rs.getInt(8));

				listaNumeros.add(numero);
			}
			vista.Inicio.miConexion.commit();
			rs.close();

		} catch (Exception SQLException) {
			SQLException.printStackTrace();
		}

		return listaNumeros;

	}

	public static int CrearNumero(Numero numero, byte[] bytes) throws IOException {

		String actualizar = "INSERT into numeros (id,titulo,portada,sinopsis,precio,estado,fecha_publicacion,id_colecc)"
				+ "values(?,?,?,?,?,?,?,?)";

		try (PreparedStatement ps = vista.Inicio.miConexion.prepareStatement(actualizar)) {

			ps.setInt(1, 0);
			ps.setString(2, numero.getTitulo());
			ps.setBytes(3, bytes);
			ps.setString(4, numero.getSinopsis());
			ps.setDouble(5, numero.getPrecio());
			ps.setString(6, numero.getEstado());
			ps.setDate(7, (java.sql.Date) numero.getFecha_publicacion());
			ps.setInt(8, numero.getId_coleccion());
			ps.executeUpdate();
			ps.close();

			return 1;
		} catch (Exception SQLException) {
			SQLException.printStackTrace();
			System.out.println("No se pudo modificar la colecci�n");
			return -1;
		}

	}

	public static Numero numero_buscar_id(int id) {
		String consulta = "SELECT * FROM numeros WHERE id =" + id;

		try {
			Statement sentencia = vista.Inicio.miConexion.createStatement();
			ResultSet rs = sentencia.executeQuery(consulta);

			Numero numero = new Numero();

			while (rs.next()) {
				numero.setId(id);
				numero.setTitulo(rs.getString(2));
				Blob blob = rs.getBlob(3);
				if (blob != null) {
					ImageIcon imageIcon = new ImageIcon(blob.getBytes(1L, (int) blob.length()));
					Image image = imageIcon.getImage();
					imageIcon = new ImageIcon(image);
					numero.setPortada(imageIcon);
				} else {
					numero.setPortada(null);
				}
				numero.setSinopsis(rs.getString(4));
				numero.setPrecio(rs.getDouble(5));
				numero.setEstado((rs.getString(6)));
				numero.setFecha_publicacion(rs.getDate(7));
				numero.setId_coleccion(rs.getInt(8));

			}

			return numero;

		} catch (Exception SQLException) {
			SQLException.printStackTrace();
			System.out.println("No se pudo cargar el n�mero");
			return new Numero(-1, "aa", null, 1.1, null, null, -1, null);

		}
	}

	public static boolean Modificar_numero(Numero numero, byte[] bytes) throws IOException {
		int id = numero.getId();

		String actualizar = "UPDATE numeros SET titulo=?,  portada=?,  sinopsis=?, precio=?, estado=?, fecha_publicacion=?, id_colecc=?  WHERE id = "
				+ id;

		try (PreparedStatement ps = vista.Inicio.miConexion.prepareStatement(actualizar)) {

			ps.setString(1, numero.getTitulo());
			ps.setBytes(2, bytes);
			ps.setString(3, numero.getSinopsis());
			ps.setDouble(4, numero.getPrecio());
			ps.setString(5, numero.getEstado());
			ps.setDate(6, (java.sql.Date) numero.getFecha_publicacion());
			ps.setInt(7, numero.getId_coleccion());
			ps.executeUpdate();
			ps.close();

			return true;
		} catch (Exception SQLException) {
			SQLException.printStackTrace();
			System.out.println("No se pudo modificar la colecci�n");
			return false;
		}

	}

	public static int eliminar_coleccion(int id) {
		try {
			Statement miStatement = vista.Inicio.miConexion.createStatement();
			miStatement.executeUpdate("DELETE FROM colecciones WHERE ID =" + id);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.print("No se pudo eliminar.");
			e1.printStackTrace();
			return -1;
		}
		try {
			Statement miStatement = vista.Inicio.miConexion.createStatement();
			miStatement.executeUpdate("DELETE FROM numeros WHERE id_colecc =" + id);

			return 1;

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.print("No se pudo eliminar.");
			e1.printStackTrace();
			return -1;
		}

	}

	public static int eliminar_numero(int id) {
		try {
			Statement miStatement = vista.Inicio.miConexion.createStatement();
			miStatement.executeUpdate("DELETE FROM numeros WHERE ID =" + id);

			return 1;

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.print("No se pudo eliminar.");
			e1.printStackTrace();
			return -1;
		}
	}

}
