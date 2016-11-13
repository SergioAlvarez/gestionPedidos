/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionpedidos;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Sergio
 */
public class ConexionBD {

    

    public ConexionBD() {
        try {

            //Asignar el driver de la base de datos.
            Class.forName("com.mysql.jdbc.Driver");
            //Establecemos la conexión con la base de datos.
            Connection conexion = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com/sql7143904", "sql7143904", "TzNhGaxJj6");
                //Realizar la consulta y recoger el resultado en un objeto ResulSet.
            // Statement sentencia = conexion.createStatement();
            //String contraseñaString = new String(contraseña.getPassword());

        } catch (ClassNotFoundException ex) {
            System.out.println("Error con el driver de la base de datos ");
        } catch (SQLException ex) {
            System.out.println("Error en la conexión" + ex.getMessage());
        }
    }

    public void consultaLogin(String contraseña) {
        if (!contraseña.equals("")) {

            try {
                //Realizar la consulta y recoger el resultado en un objeto ResulSet.  
                Connection conexion = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com/sql7143904", "sql7143904", "TzNhGaxJj6");
                Statement sentencia = conexion.createStatement();
                ResultSet resul = sentencia.executeQuery("Select count(NOMBRE) from Empleados where clave='" + contraseña + "'");
                //Mostrar el resultado de la consulta.
                while (resul.next()) {

                    if (resul.getInt(1) == 0) {
                        //JOptionPane.showMessageDialog(rootPane, "No existe empleado con esa clave! ");
                        JOptionPane.showMessageDialog(null, "¡No existe empleado con esa clave!");
                        System.out.println(contraseña);
                    } else {
                        resul = sentencia.executeQuery("Select nombre from Empleados where clave ='" + contraseña + "'");
                        while (resul.next()) {

                            JFrameMenu menu = new JFrameMenu(resul.getString(1));
                            Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                            int height = pantalla.height;
                            int width = pantalla.width;
                            menu.setSize(width / 2, height / 2);

                            menu.setLocationRelativeTo(null);
                            menu.setVisible(true);

                            NewJFrame principalClave = new NewJFrame();
                            principalClave.setVisible(false);
                            System.out.println(resul.getInt(1));

                        }

                    }
                }

//            //Liberar los objetos creados
                resul.close();
                conexion.close();

            } catch (SQLException ex) {
                System.out.println("Error en la conexión" + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "¡El campo CLAVE es obligatorio!");

        }
    }
    public void consultaCliente(JTextField telefono, JTextField nombre,JTextField calle, JTextField numero, JTextField portal,
             JTextField escalera, JTextField piso, JTextField puerta, JButton aceptar, JButton aceptarDatosNuevos,
            JButton pedidoNuevo, JButton modifiDatos){
        try {
            //Asignar el driver de la base de datos.
            Class.forName("com.mysql.jdbc.Driver");
            //Establecemos la conexión con la base de datos.
            Connection conexion = DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com/sql7143904", "sql7143904", "TzNhGaxJj6");
            //Realizar la consulta y recoger el resultado en un objeto ResulSet.
            Statement sentencia = conexion.createStatement();
            int telefonoInt = Integer.parseInt(telefono.getText());
            ResultSet resul = sentencia.executeQuery("Select nombre,calle, numero,piso,puerta from clientes where telefono=" + telefonoInt + "");
            //Mostrar el resultado de la consulta.

            while (resul.next()) {
                if (resul.getString(1).equals(0)) {
                    JOptionPane.showMessageDialog(null, "¡No existe empleado con esa clave! ");

                    //System.out.println(telefono);
                } else {
                    nombre.setText(resul.getString(1));
                    calle.setText(resul.getString(2));
                    numero.setText(String.valueOf(resul.getInt(3)));
                    piso.setText(resul.getString(4));
                    puerta.setText(resul.getString(5));
                    //Existe el cliente, por lo que anulamos el poder editar los campos.
                    nombre.setEditable(false);
                    calle.setEditable(false);
                    numero.setEditable(false);
                    portal.setEditable(false);
                    escalera.setEditable(false);
                    piso.setEditable(false);

                    //Existe el cliente, por lo que podemos modificar los datos
                    modifiDatos.setEnabled(true);

                }

            }
            // El telefono no existe en la base de datos:
            telefono.setEnabled(false);
            nombre.setEnabled(true);
            calle.setEnabled(true);
            numero.setEnabled(true);
            piso.setEnabled(true);
            puerta.setEnabled(true);
            portal.setEnabled(true);
            escalera.setEnabled(true);
            //jTextField7Puerta.setEditable(false);
            pedidoNuevo.setVisible(true);
            aceptar.setVisible(false);
            aceptarDatosNuevos.setVisible(true);

            //            //Liberar los objetos creados
            resul.close();
            sentencia.close();
            conexion.close();

        } catch (ClassNotFoundException ex) {
            System.out.println("Error con el driver de la base de datos ");

        } catch (SQLException ex) {
            System.out.println("Error en la conexión" + ex.toString());
        }
        
    }
}
