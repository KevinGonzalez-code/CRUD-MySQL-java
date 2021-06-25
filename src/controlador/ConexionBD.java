package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import javax.swing.table.DefaultTableModel;

public class ConexionBD {

    public void consultasSelect(DefaultTableModel modeloTabla) {

        try {
            //Cargamos el driver
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mariadb://localhost/nombre_tabla", "user", "password");
            Statement sentencia = conexion.createStatement();

            ResultSet resultado = sentencia.executeQuery("SELECT * FROM DEPARTAMENTOS ORDER BY CODIGO");

            while (resultado.next()) {
                Object matriz[] = new Object[4];
                matriz[0] = resultado.getInt(1);
                matriz[1] = resultado.getString(2);
                matriz[2] = resultado.getInt(3);
                matriz[3] = resultado.getInt(4);
                modeloTabla.addRow(matriz);
            }

            resultado.close();
            sentencia.close();
            conexion.close();

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error el driver de la base de datos no se a cargado correctamente", "Error de conexión", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la conexión, revisela por favor", "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insercion(int codigo, String nombre, int id_departamento, int id_manager) {
        try {
            //Cargamos el driver
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mariadb://localhost/nombre_tabla", "user", "password");
            Statement sentencia = conexion.createStatement();
            String sql = "INSERT INTO DEPARTAMENTOS VALUES (" + codigo + "," + "'" + nombre + "'" + "," + id_departamento + "," + id_manager + ")";
            sentencia.executeUpdate(sql);

            sentencia.close();
            conexion.close();

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error el driver de la base de datos no se a cargado correctamente", "Error de conexión", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ya hay registrado un departamento con los datos del formulario", "No se pueden duplicar departamentos", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizacion(String nombre, String id_manager, String id_departamento, String codigoBuscar) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mariadb://localhost/nombre_tabla", "user", "password");
            Statement sentencia = conexion.createStatement();
            int decision = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere modificar el departamento?", "Modificar departamento", JOptionPane.YES_OPTION, JOptionPane.YES_NO_OPTION);
            String sql = "update departamentos set nombre='" + nombre + "' , id_departamento = " + id_departamento + ", id_manager = " + id_manager + " where codigo=" + codigoBuscar + ";";

            if (decision == JOptionPane.YES_OPTION) {
                int actualizacion = sentencia.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Se ha actualizado " + actualizacion + " departamento en la base de datos", "Modificación", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se ha actualizado nada", "Modificación", JOptionPane.PLAIN_MESSAGE);
            }

            sentencia.close();
            conexion.close();

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error el driver de la base de datos no se a cargado correctamente", "Error de conexión", JOptionPane.ERROR);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    public void buscarCodigo(JTextField codigo, JTextField nombre, JTextField id_departamento, JTextField id_manager, JTextField codigoBuscar) {

        try {
            //Cargamos el driver
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mariadb://localhost/nombre_tabla", "user", "password");
            Statement sentencia = conexion.createStatement();
            sentencia = conexion.createStatement();

            ResultSet resultado = sentencia.executeQuery("SELECT * FROM DEPARTAMENTOS WHERE CODIGO = " + codigoBuscar.getText()); //To do sintaxis select

            while (resultado.next()) {
                codigo.setText(String.valueOf(resultado.getInt(1)));
                nombre.setText(resultado.getString(2));
                id_departamento.setText(String.valueOf(resultado.getInt(3)));
                id_manager.setText(String.valueOf(resultado.getInt(4)));
            }

            resultado.close();
            sentencia.close();
            conexion.close();

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error el driver de la base de datos no se a cargado correctamente", "Error de conexión", JOptionPane.ERROR);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void buscarCodigoBorrar(JTextField codigoBuscar, JTextPane panel) {

        try {
            //Cargamos el driver
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mariadb://localhost/nombre_tabla", "user", "password");
            Statement sentencia = conexion.createStatement();
            sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM DEPARTAMENTOS WHERE CODIGO = " + codigoBuscar.getText()); //To do sintaxis select

            while (resultado.next()) {
                panel.setText("Codigo departamento: " + resultado.getInt(1) + "\n"
                        + "Nombre departamento: " + resultado.getString(2) + "\n"
                        + "Id_departamento: " + resultado.getInt(3) + "\n"
                        + "Id_manager: " + resultado.getInt(4) + "\n"
                        + "\n" + "Una vez borrado el departamento no es posible volver a recuperarlo");
            }

            resultado.close();
            sentencia.close();
            conexion.close();

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error el driver de la base de datos no se a cargado correctamente", "Error de conexión", JOptionPane.ERROR);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void borrarDepartamento(String codigoBuscar) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mariadb://localhost/nombre_tabla", "user", "password");
            Statement sentencia = conexion.createStatement();
            int decision = JOptionPane.showConfirmDialog(null, "¿Seguro que quiere borrar el departamento con el codigo " + codigoBuscar + "?", "Borrar departamento", JOptionPane.YES_OPTION, JOptionPane.YES_NO_OPTION);
            String sql = "delete from departamentos where codigo=" + codigoBuscar + ";";

            if (decision == JOptionPane.YES_OPTION) {
                sentencia.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Se ha borrado el departamento con el codigo " + codigoBuscar + " departamento en la base de datos", "Modificación", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se ha borrado nada ", "Borrar departamento", JOptionPane.PLAIN_MESSAGE);
            }

            sentencia.close();
            conexion.close();

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error el driver de la base de datos no se a cargado correctamente", "Error de conexión", JOptionPane.ERROR);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
