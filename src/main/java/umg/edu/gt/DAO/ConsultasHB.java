/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package umg.edu.gt.DAO;

import java.util.List;
import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import umg.edu.gt.DTO.DatosDTO;
import umg.edu.gt.DTO.Cliente;

public class ConsultasHB {

    public List<Cliente> consultarCliente() {

        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cliente.class).buildSessionFactory();

        Session session = sessionFactory.openSession();

        try {
            Query query = session.createQuery("from Cliente");

            List<Cliente> clientes = query.list();

            session.close();

            return clientes;

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Error HB al consultar clientes " + e.getStackTrace());
            return null;
        }
    }

    public String crearCliente(DatosDTO cliente) {

        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cliente.class).buildSessionFactory();

        Session session = sessionFactory.openSession();

        try {

            Cliente client = new Cliente(cliente.getNombre(), cliente.getCorreo(), cliente.getDireccion(), cliente.getTelefono());

            session.beginTransaction();
            //session.save(client);
            session.persist(client);

            session.getTransaction().commit();

            session.close();

            return "Usuario creado";
        } catch (PersistenceException e) {
            e.printStackTrace();
            return "error al registrar usuario: " + e.getStackTrace();
        }
    }

    public String modificarCliente(DatosDTO cliente) {

        System.out.println("Ingresando al metodo modificar cliente");
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cliente.class).buildSessionFactory();

        Session session = sessionFactory.openSession();

        Transaction transaction = null;

        try {

            transaction = session.beginTransaction();

            Cliente client = session.get(Cliente.class, cliente.getId());

            client.setNombre(cliente.getNombre());
            client.setCorreo(cliente.getCorreo());
            client.setDireccion(cliente.getDireccion());
            client.setTelefono(cliente.getTelefono());

            session.update(client);
            //session.save(client);
            transaction.commit();

            //session.getTransaction().commit();
            //session.close();
            return "Cliente actualizado con exito";
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return "Error HB al actualizar el cliente";
        } finally {
            session.close();
        }
    }

    public String eliminarCliente(DatosDTO cliente) {

        System.out.println("Ingresando al metodo modificar cliente");
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cliente.class).buildSessionFactory();

        Session session = sessionFactory.openSession();

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Recuperar el Cliente de la base de datos
            Cliente clienteEncontrado = session.get(Cliente.class, cliente.getId());

            // Eliminar el Cliente
            session.delete(clienteEncontrado);

            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return "Cliente eliminado exitosamente";
    }

}
