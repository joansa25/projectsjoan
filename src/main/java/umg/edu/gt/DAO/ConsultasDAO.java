/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package umg.edu.gt.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import umg.edu.gt.DTO.DatosDTO;
import umg.edu.gt.DTO.Cliente;

public class ConsultasDAO {

    ConexionDAO con = new ConexionDAO();
////////////////////////////////////CLIENTES////////////////////////////////////////////////////////////////

    public List<DatosDTO> consultarCliente() throws Exception {
        List<DatosDTO> Lista = new ArrayList<DatosDTO>();

        try {
            String query = "SELECT id, nombre, correo, direccion, telefono FROM clientes";
            Statement s = con.conexionMysql().createStatement();
            ResultSet r = s.executeQuery(query);

            while (r.next()) {
                DatosDTO dato = new DatosDTO();
                dato.setId(r.getLong("id"));
                dato.setNombre(r.getString("nombre"));
                dato.setDireccion(r.getString("direccion"));
                dato.setCorreo(r.getString("correo"));
                dato.setTelefono(r.getString("telefono"));
                Lista.add(dato);
            }
        } catch (Exception e) {
            System.out.println("Error al realizar la consulta");
        } finally {
            if (con != null) {
                try {
                    con.conexionMysql().close();
                    System.out.println("Cierre de conexion exitosa");
                } catch (SQLException ex) {
                    System.out.println("Error al cerrar conexion");
                }
            }
        }

        System.out.println("El tamaño de la lista" + Lista.size());
        return Lista;
    }

    public void insertarCliente(DatosDTO cliente) {

        try {
            String query = "INSERT INTO clientes VALUES (" + cliente.getId() + ",'" + cliente.getNombre() + "','" + cliente.getCorreo() + "','" + cliente.getDireccion() + "','" + cliente.getTelefono() + "')";
            //String query="INSERT INTO clientes VALUES ('"+cliente.getNombre()+"','"+cliente.getCorreo()+"','"+cliente.getDireccion()+"','"+cliente.getTelefono()+"')";
            //String query = "INSERT INTO clientes VALUES (6, 'isai', 'isaimixia18@gmail.com','Santa Luxia', '48407205')";  
            Statement s = con.conexionMysql().createStatement();
            s.executeUpdate(query);

            System.out.println("-------------------Datos Insertados--------------------------------");
            System.out.println("Id: " + cliente.getId() + "Nombre: " + cliente.getNombre() + "Correo: " + cliente.getCorreo() + "Direccion: " + cliente.getDireccion() + "Telefono: " + cliente.getTelefono());
            System.out.println("---------------------------------------------------");

        } catch (Exception e) {
            System.out.println("Error al realizar la insercion");
        }
    }

    public void actualizarCliente(DatosDTO cliente) {

        try {
            //String query="INSERT INTO clientes VALUES ("+pDatos.getId()+",'"+pDatos.getNombre()+"','"+pDatos.getCorreo()+"','"+pDatos.getDireccion()+"','"+pDatos.getTelefono()+"')";
            String query = "UPDATE clientes SET ";

            if (cliente.getNombre() != null && !cliente.getNombre().isEmpty()) {
                query += "nombre='" + cliente.getNombre() + "', ";
            } else {
                query += "nombre=nombre, "; // Keep the existing value
            }

            if (cliente.getCorreo() != null && !cliente.getCorreo().isEmpty()) {
                query += "correo='" + cliente.getCorreo() + "', ";
            } else {
                query += "correo=correo, "; // Keep the existing value
            }

            if (cliente.getDireccion() != null && !cliente.getDireccion().isEmpty()) {
                query += "direccion='" + cliente.getDireccion() + "', ";
            } else {
                query += "direccion=direccion, "; // Keep the existing value
            }

            if (cliente.getTelefono() != null && !cliente.getTelefono().isEmpty()) {
                query += "telefono='" + cliente.getTelefono() + "', ";
            } else {
                query += "telefono=telefono, "; // Keep the existing value
            }

            if (query.endsWith(", ")) {
                query = query.substring(0, query.length() - 2);
            }

            query += " WHERE id='" + cliente.getId() + "'";

            Statement s = con.conexionMysql().createStatement();
            s.executeUpdate(query);

        } catch (Exception e) {
            System.out.println("Error al realizar la actualizacion de cliente");
        }
    }

    public void eliminarCliente(DatosDTO cliente) throws Exception {

        try {

            String query = "DELETE FROM clientes WHERE id =" + cliente.getId();
            Statement s = con.conexionMysql().createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            throw new SQLException("Error al eliminar el cliente: " + ex.getMessage());
        }
    }

    //////////////////////////////////////////ORDENES////////////////////////////////////////////////////////
    public List<DatosDTO> consultarOrdenes() throws Exception {
        List<DatosDTO> Lista = new ArrayList<DatosDTO>();

        try {
            String query = "SELECT id, cliente_id, fecha, total FROM ordenes";
            Statement s = con.conexionMysql().createStatement();
            ResultSet r = s.executeQuery(query);

            while (r.next()) {
                DatosDTO dato = new DatosDTO();
                dato.setId(r.getLong("id"));
                dato.setCliente_id(r.getLong("cliente_id"));
                dato.setFecha(r.getString("fecha"));
                dato.setTotal(r.getLong("total"));

                Lista.add(dato);
            }
        } catch (Exception e) {
            System.out.println("Error al consultar Ordenes");
        } finally {
            if (con != null) {
                try {
                    con.conexionMysql().close();
                    System.out.println("Cierre de conexion exitosa");
                } catch (SQLException ex) {
                    System.out.println("Error al cerrar conexion");
                }
            }
        }

        System.out.println("El tamaño de la lista" + Lista.size());
        return Lista;
    }

    public void insertarOrden(DatosDTO orden) {

        try {

            if (orden.getCliente_id() != null && !orden.getFecha().isEmpty() && orden.getTotal() != null) {

                String query = "INSERT INTO ordenes (cliente_id, fecha, total) VALUES (" + orden.getCliente_id() + ", SYSDATE() ," + orden.getTotal() + ")";
                //String query="INSERT INTO clientes VALUES ('"+cliente.getNombre()+"','"+cliente.getCorreo()+"','"+cliente.getDireccion()+"','"+cliente.getTelefono()+"')";
                //String query = "INSERT INTO clientes VALUES (6, 'isai', 'isaimixia18@gmail.com','Santa Luxia', '48407205')";  
                Statement s = con.conexionMysql().createStatement();
                s.executeUpdate(query);

                System.out.println("La consulta es: " + query);

            }

        } catch (Exception e) {
            System.out.println("Error al insertar Orden");
        }

    }

    public void actualizarOrden(DatosDTO orden) {

        //String query="INSERT INTO clientes VALUES ("+pDatos.getId()+",'"+pDatos.getNombre()+"','"+pDatos.getCorreo()+"','"+pDatos.getDireccion()+"','"+pDatos.getTelefono()+"')";
        try {

            String query = "UPDATE ordenes SET ";

            if (orden.getCliente_id() != null) {
                query += "cliente_id=" + orden.getCliente_id() + ", ";
            } else {
                query += "cliente_id=cliente_id, "; // Keep the existing value
            }

            if (orden.getFecha() != null && !orden.getFecha().isEmpty()) {
                query += "fecha='" + orden.getFecha() + "', ";
            } else {
                query += "fecha=fecha, "; // Keep the existing value
            }

            if (orden.getTotal() != null) {
                query += "total=" + orden.getTotal();
            } else {
                query += "total=total,"; // Keep the existing value
            }

            query += " WHERE id=" + orden.getId() + "";

            Statement s = con.conexionMysql().createStatement();
            s.executeUpdate(query);

        } catch (Exception e) {
            System.out.println("Error al actualizar orden");
        }

    }

    public void eliminarOrden(DatosDTO orden) throws Exception {

        try {
            String query = "DELETE FROM ordenes WHERE id =" + orden.getId();
            Statement s = con.conexionMysql().createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            throw new SQLException("Error al eliminar la orden: " + ex.getMessage());
        }

    }

///////////////////////////////////////DETALLE_ORDENES////////////////////////////////////////////////////////
    public List<DatosDTO> consultarDetalle() throws Exception {
        List<DatosDTO> detalle = new ArrayList<DatosDTO>();

        try {
            String query = "SELECT id, orden_id, producto_id, cantidad, precio FROM detalles_ordenes";
            Statement s = con.conexionMysql().createStatement();
            ResultSet r = s.executeQuery(query);

            while (r.next()) {
                DatosDTO dato = new DatosDTO();
                dato.setId(r.getLong("id"));
                dato.setOrden_id(r.getLong("orden_id"));
                dato.setProducto_id(r.getLong("producto_id"));
                dato.setPrecio(r.getLong("precio"));
                dato.setCantidad(r.getLong("cantidad"));

                detalle.add(dato);
            }
        } catch (Exception e) {
            System.out.println("Error al consultar detalle_ordenes");
        } finally {
            if (con != null) {
                try {
                    con.conexionMysql().close();
                    System.out.println("Cierre de conexion exitosa");
                } catch (SQLException ex) {
                    System.out.println("Error al cerrar conexion");
                }
            }
        }

        System.out.println("El tamaño de la lista" + detalle.size());
        return detalle;
    }

    public void insertarDetalle(DatosDTO detalle) {

        try {

            if (detalle.getOrden_id() != null && detalle.getProducto_id() != null && detalle.getCantidad() != null && detalle.getPrecio() != null) {

                String query = "INSERT INTO detalles_ordenes (orden_id, producto_id, cantidad, precio) VALUES (" + detalle.getOrden_id() + "," + detalle.getProducto_id() + "," + detalle.getCantidad() + "," + detalle.getPrecio() + ")";
                //String query="INSERT INTO clientes VALUES ('"+cliente.getNombre()+"','"+cliente.getCorreo()+"','"+cliente.getDireccion()+"','"+cliente.getTelefono()+"')";
                //String query = "INSERT INTO clientes VALUES (6, 'isai', 'isaimixia18@gmail.com','Santa Luxia', '48407205')";  
                Statement s = con.conexionMysql().createStatement();
                s.executeUpdate(query);

                System.out.println("La consulta es: " + query);
            }

        } catch (Exception e) {
            System.out.println("Error al insertar Orden");
        }

    }

    public void actualizarDetalle(DatosDTO detalle) {

        //String query="INSERT INTO clientes VALUES ("+pDatos.getId()+",'"+pDatos.getNombre()+"','"+pDatos.getCorreo()+"','"+pDatos.getDireccion()+"','"+pDatos.getTelefono()+"')";
        String query = "UPDATE detalles_ordenes SET ";

        if (detalle.getOrden_id() != null) {
            query += "orden_id=" + detalle.getOrden_id() + ", ";
        } else {
            query += "orden_id=orden_id, "; // Keep the existing value
        }

        if (detalle.getProducto_id() != null) {
            query += "producto_id=" + detalle.getProducto_id() + ",";
        } else {
            query += "producto_id=producto_id, "; // Keep the existing value
        }

        if (detalle.getCantidad() != null) {
            query += "cantidad=" + detalle.getCantidad() + ",";
        } else {
            query += "cantidad=cantidad,"; // Keep the existing value
        }

        if (detalle.getPrecio() != null) {
            query += "precio=" + detalle.getPrecio();
        } else {
            query += "precio=precio,"; // Keep the existing value
        }

        query += " WHERE id=" + detalle.getId();

        try {

            Statement s = con.conexionMysql().createStatement();
            s.executeUpdate(query);

        } catch (Exception e) {
            System.out.println("Error al actualizar detalle_orden");
        }

        System.out.println("Consulta Actualizar para DETALLE: " + query);
    }

    public void eliminarDetalle(DatosDTO detalle) throws Exception {

        String query = "DELETE FROM detalles_ordenes WHERE id =" + detalle.getId();

        try {

            Statement s = con.conexionMysql().createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            throw new SQLException("Error al eliminar la orden: " + ex.getMessage());
        }

        System.out.println("Consulta para eliminar: " + query);
    }
////////////////////////////////////////////PRODUCTOS//////////////////////////////////////////////////////

    public List<DatosDTO> consultarProductos() throws Exception {
        List<DatosDTO> productos = new ArrayList<DatosDTO>();

        try {
            String query = "SELECT id, nombre, descripcion, precio, cantidad FROM productos";
            Statement s = con.conexionMysql().createStatement();
            ResultSet r = s.executeQuery(query);

            while (r.next()) {
                DatosDTO dato = new DatosDTO();
                dato.setId(r.getLong("id"));
                dato.setNombre(r.getString("nombre"));
                dato.setDescripcion(r.getString("descripcion"));
                dato.setPrecio(r.getLong("precio"));
                dato.setCantidad(r.getLong("cantidad"));

                productos.add(dato);
            }
        } catch (Exception e) {
            System.out.println("Error al consultar productos");
        } finally {
            if (con != null) {
                try {
                    con.conexionMysql().close();
                    System.out.println("Cierre de conexion exitosa");
                } catch (SQLException ex) {
                    System.out.println("Error al cerrar conexion");
                }
            }
        }

        System.out.println("El tamaño de la lista" + productos.size());
        return productos;
    }

    public void insertarProducto(DatosDTO producto) {

        try {
            if (!producto.getNombre().isEmpty() && !producto.getDescripcion().isEmpty() && producto.getPrecio() != null && producto.getCantidad() != null) {

                String query = "INSERT INTO productos (nombre, descripcion, precio, cantidad) VALUES ('" + producto.getNombre() + "','" + producto.getDescripcion() + "'," + producto.getPrecio() + "," + producto.getCantidad() + ")";
                //String query="INSERT INTO clientes VALUES ('"+cliente.getNombre()+"','"+cliente.getCorreo()+"','"+cliente.getDireccion()+"','"+cliente.getTelefono()+"')";
                //String query = "INSERT INTO clientes VALUES (6, 'isai', 'isaimixia18@gmail.com','Santa Luxia', '48407205')";  
                Statement s = con.conexionMysql().createStatement();
                s.executeUpdate(query);
                System.out.println("La consulta es: " + query);
            }

        } catch (Exception e) {
            System.out.println("Error al insertar Orden");
        }

    }

    public void actualizarProducto(DatosDTO producto) {

        //String query="INSERT INTO clientes VALUES ("+pDatos.getId()+",'"+pDatos.getNombre()+"','"+pDatos.getCorreo()+"','"+pDatos.getDireccion()+"','"+pDatos.getTelefono()+"')";
        String query = "UPDATE productos SET ";

        if (!producto.getNombre().isEmpty()) {
            query += "nombre='" + producto.getNombre() + "',";
        } else {
            query += "nombre=nombre,"; // Keep the existing value
        }

        if (!producto.getDescripcion().isEmpty()) {
            query += "descripcion='" + producto.getDescripcion() + "',";
        } else {
            query += "descripcion=descripcion,"; // Keep the existing value
        }

        if (producto.getPrecio() != null) {
            query += "precio=" + producto.getPrecio() + ",";
        } else {
            query += "precio=precio,"; // Keep the existing value
        }

        if (producto.getCantidad() != null) {
            query += "cantidad=" + producto.getCantidad();
        } else {
            query += "cantidad=cantidad,"; // Keep the existing value
        }

        query += " WHERE id=" + producto.getId();

        try {

            Statement s = con.conexionMysql().createStatement();
            s.executeUpdate(query);

        } catch (Exception e) {
            System.out.println("Error al actualiz el producto");
        }

        System.out.println("Consulta Actualizar para producto: " + query);
    }

    public void eliminarProducto(DatosDTO producto) throws Exception {

        String query = "DELETE FROM productos WHERE id =" + producto.getId();

        try {

            Statement s = con.conexionMysql().createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            throw new SQLException("Error al eliminar el producto: " + ex.getMessage());
        }

        System.out.println("Consulta para eliminar: " + query);
    }
}
//Tener archiva listo
//SonarQB si es que consigue una version gratis
//Investigar que es ORM
//Hibernet, JPA
//JRebel
//Como persistir en hibernet
