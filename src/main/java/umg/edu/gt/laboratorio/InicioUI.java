/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package umg.edu.gt.laboratorio;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.sql.SQLException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.net.http.HttpClient;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import umg.edu.gt.DAO.ConexionDAO;
import umg.edu.gt.DAO.ConsultasDAO;
import umg.edu.gt.DAO.ConsultasHB;
import umg.edu.gt.DTO.DatosDTO;
import umg.edu.gt.DTO.Cliente;

/**
 *
 * @author soporte
 */
//esto es una notacion, para llamarlo dedede el xhtml
@ManagedBean(name = "bkn_Inicio")
@ViewScoped
public class InicioUI implements Serializable {

    private String mensaje;

    private String dato1;
    private String dato2;
    private String dato3;
    private String dato4;
    private String dato5;

    private String dato6;
    private String dato7;
    private String dato8;

    private String dato9;
    private String dato10;
    private String dato11;
    private String dato12;

    private String dato13;
    private String dato14;
    private String dato15;

    private Cliente datos;

    private boolean band;
    private List<Cliente> clientes = new ArrayList<Cliente>();
    private List<DatosDTO> ordenes = new ArrayList<DatosDTO>();
    private List<DatosDTO> detalle_ordenes = new ArrayList<DatosDTO>();
    private List<DatosDTO> productos = new ArrayList<DatosDTO>();

    @PostConstruct
    public void init() {

        ConexionDAO con = new ConexionDAO();
        ConsultasDAO consulta = new ConsultasDAO();
        ConsultasHB consul = new ConsultasHB();

        try {

            //setClientes(consulta.consultarCliente());
            setClientes(consul.consultarCliente());
            setOrdenes(consulta.consultarOrdenes());
            setDetalle_ordenes(consulta.consultarDetalle());
            setProductos(consulta.consultarProductos());
            System.out.println("La conexion es:" + con.conexionMysql());
            System.out.println("La lista es: " + getClientes().size());
            System.out.println("El nombre es " + getClientes().get(0).getNombre());

        } catch (Exception e) {
            System.out.println("Error de Conexionn");
        }
    }

    /**
     * @return the mensaje
     */
    public void mostraClientes() throws Exception {
        //setMensaje("Hola mundo, mi primer comentario web con jsf2");
        ConexionDAO con = new ConexionDAO();
        ConsultasDAO consulta = new ConsultasDAO();

        try {
            //setClientes(consulta.consultarCliente());
        } catch (Exception ex) {
            System.out.println("Error de la conexion" + ex);
        }

    }
/////////////////////////////////////CLIENTES///////////////////////////////////////////////////////////////

    public void accionesCliente(int opt) throws Exception {

        ConexionDAO con = new ConexionDAO();
        ConsultasDAO consulta = new ConsultasDAO();
        ConsultasHB consultaHB = new ConsultasHB();

        DatosDTO cliente = new DatosDTO();

        try {

            cliente.setNombre(dato2);
            cliente.setCorreo(dato3);
            cliente.setDireccion(dato4);
            cliente.setTelefono(dato5);

            switch (opt) {
                case 1:
                    //consulta.insertarCliente(cliente);
                    //consultaHB.crearCliente(cliente);
                    System.out.println("Respuesta insertar cliente: " + consultaHB.crearCliente(cliente));
                    break;
                case 2:
                    cliente.setId(Long.parseLong(dato1));
                    //consulta.actualizarCliente(cliente);
                    //consultaHB.modificarCliente(cliente);
                    System.out.println("Respuesta actualizacio cliente: " + consultaHB.modificarCliente(cliente));
                    break;
                case 3:
                    cliente.setId(Long.parseLong(dato1));
                    System.out.println("Respuesta actualizacio cliente: " + consultaHB.eliminarCliente(cliente));
                    break;
            }

            //setClientes(consulta.consultarCliente());
            setClientes(consultaHB.consultarCliente());
            System.out.println("Resultado de consulta: " + consultaHB.consultarCliente());

            System.out.println("la conexion es: " + con.conexionMysql());
            System.out.println("La lista de clientes es: " + getClientes().size());
            System.out.println("El nombre es: " + getClientes().get(0).getNombre());

            dato1 = "";
            dato2 = "";
            dato3 = "";
            dato4 = "";
            dato5 = "";
            band = false;
        } catch (Exception ex) {
            System.out.println("Error de la accion de cliente" + ex);
        } finally {
            if (con != null) {
                try {
                    con.conexionMysql().close();
                    System.out.println("Cierre de conexion exitosa");
                } catch (SQLException ex) {
                    System.out.println("Error al cerrar conexion" + ex.getMessage());
                }
            }
        }

    }

    /////////////////////////////////////ORDENES////////////////////////////////////////////
    public void accionesOrdenes(int opt) throws Exception {

        ConexionDAO con = new ConexionDAO();
        ConsultasDAO consulta = new ConsultasDAO();
        DatosDTO orden = new DatosDTO();

        try {

            orden.setCliente_id(Long.parseLong(dato6));
            orden.setFecha(dato8);
            orden.setTotal(Long.parseLong(dato7));

            switch (opt) {
                case 1:
                    consulta.insertarOrden(orden);
                    break;
                case 2:
                    orden.setId(Long.parseLong(dato1));
                    consulta.actualizarOrden(orden);
                    break;
                case 3:
                    orden.setId(Long.parseLong(dato1));
                    consulta.eliminarOrden(orden);
                    break;
            }

            setOrdenes(consulta.consultarOrdenes());

            System.out.println("la conexion es: " + con.conexionMysql());
            System.out.println("La lista de clientes es: " + getClientes().size());
            System.out.println("El nombre es: " + getClientes().get(0).getNombre());

            dato1 = "";
            dato2 = "";
            dato3 = "";
            dato4 = "";
            dato5 = "";
            dato6 = "";
            dato7 = "";
            dato8 = "";

            band = false;
        } catch (Exception ex) {
            System.out.println("Error de la accion de cliente" + ex);
        } finally {
            if (con != null) {
                try {
                    con.conexionMysql().close();
                    System.out.println("Cierre de conexion exitosa");
                } catch (SQLException ex) {
                    System.out.println("Error al cerrar conexion" + ex.getMessage());
                }
            }
        }

    }

    /////////////////////////////////////////DETALLE_ORDENES////////////////////////////////////////////////
    public void accionesDetalle_ordenes(int opt) throws Exception {

        ConexionDAO con = new ConexionDAO();
        ConsultasDAO consulta = new ConsultasDAO();
        DatosDTO detalle = new DatosDTO();

        try {

            detalle.setOrden_id(Long.parseLong(dato9));
            detalle.setProducto_id(Long.parseLong(dato10));
            detalle.setCantidad(Long.parseLong(dato11));
            detalle.setPrecio(Long.parseLong(dato12));

            switch (opt) {
                case 1:
                    consulta.insertarDetalle(detalle);
                    break;
                case 2:
                    detalle.setId(Long.parseLong(dato1));
                    consulta.actualizarDetalle(detalle);
                    break;
                case 3:
                    detalle.setId(Long.parseLong(dato1));
                    consulta.eliminarDetalle(detalle);
                    break;
            }

            setDetalle_ordenes(consulta.consultarDetalle());

            System.out.println("la conexion es: " + con.conexionMysql());
            System.out.println("La lista de clientes es: " + getClientes().size());
            System.out.println("El nombre es: " + getClientes().get(0).getNombre());

            dato1 = "";
            dato2 = "";
            dato3 = "";
            dato4 = "";
            dato5 = "";
            dato6 = "";
            dato7 = "";
            dato8 = "";
            dato9 = "";
            dato10 = "";
            dato11 = "";
            dato12 = "";

            band = false;
        } catch (Exception ex) {
            System.out.println("Error de la accion de detalle_ordenes" + ex);
        } finally {
            if (con != null) {
                try {
                    con.conexionMysql().close();
                    System.out.println("Cierre de conexion exitosa");
                } catch (SQLException ex) {
                    System.out.println("Error al cerrar conexion" + ex.getMessage());
                }
            }
        }

    }

    //////////////////////////////////////////////PRODUCTOS//////////////////////////////////////////////
    public void accionesProductos(int opt) throws Exception {

        ConexionDAO con = new ConexionDAO();
        ConsultasDAO consulta = new ConsultasDAO();
        DatosDTO producto = new DatosDTO();

        try {

            producto.setNombre(dato2);
            producto.setDescripcion(dato13);
            producto.setPrecio(Long.parseLong(dato12));
            producto.setCantidad(Long.parseLong(dato11));

            switch (opt) {
                case 1:
                    consulta.insertarProducto(producto);
                    break;
                case 2:
                    producto.setId(Long.parseLong(dato1));
                    consulta.actualizarProducto(producto);
                    break;
                case 3:
                    producto.setId(Long.parseLong(dato1));
                    consulta.eliminarProducto(producto);
                    break;
            }

            setProductos(consulta.consultarProductos());

            System.out.println("la conexion es: " + con.conexionMysql());
            System.out.println("La lista de clientes es: " + getClientes().size());
            System.out.println("El nombre es: " + getClientes().get(0).getNombre());

            dato1 = "";
            dato2 = "";
            dato3 = "";
            dato4 = "";
            dato5 = "";
            dato6 = "";
            dato7 = "";
            dato8 = "";
            dato9 = "";
            dato10 = "";
            dato11 = "";
            dato12 = "";
            dato13 = "";
            dato14 = "";
            dato15 = "";

            band = false;
        } catch (Exception ex) {
            System.out.println("Error de la accion de detalle_ordenes" + ex);
        } finally {
            if (con != null) {
                try {
                    con.conexionMysql().close();
                    System.out.println("Cierre de conexion exitosa");
                } catch (SQLException ex) {
                    System.out.println("Error al cerrar conexion" + ex.getMessage());
                }
            }
        }

    }

    /**
     * @return the productos
     */
    public List<DatosDTO> getProductos() {
        return productos;
    }

    /**
     * @param productos the productos to set
     */
    public void setProductos(List<DatosDTO> productos) {
        this.productos = productos;
    }

    /**
     * @return the dato12
     */
    public String getDato12() {
        return dato12;
    }

    /**
     * @param dato12 the dato12 to set
     */
    public void setDato12(String dato12) {
        this.dato12 = dato12;
    }

    /**
     * @return the dato9
     */
    public String getDato9() {
        return dato9;
    }

    /**
     * @param dato9 the dato9 to set
     */
    public void setDato9(String dato9) {
        this.dato9 = dato9;
    }

    /**
     * @return the dato10
     */
    public String getDato10() {
        return dato10;
    }

    /**
     * @param dato10 the dato10 to set
     */
    public void setDato10(String dato10) {
        this.dato10 = dato10;
    }

    /**
     * @return the dato11
     */
    public String getDato11() {
        return dato11;
    }

    /**
     * @param dato11 the dato11 to set
     */
    public void setDato11(String dato11) {
        this.dato11 = dato11;
    }

    /**
     * @return the dato8
     */
    public String getDato8() {
        return dato8;
    }

    /**
     * @param dato8 the dato8 to set
     */
    public void setDato8(String dato8) {
        this.dato8 = dato8;
    }

    /**
     * @return the ordenes
     */
    public List<DatosDTO> getOrdenes() {
        return ordenes;
    }

    /**
     * @param ordenes the ordenes to set
     */
    public void setOrdenes(List<DatosDTO> ordenes) {
        this.ordenes = ordenes;
    }

    /**
     * @return the detalle_ordenes
     */
    public List<DatosDTO> getDetalle_ordenes() {
        return detalle_ordenes;
    }

    /**
     * @param detalle_ordenes the detalle_ordenes to set
     */
    public void setDetalle_ordenes(List<DatosDTO> detalle_ordenes) {
        this.detalle_ordenes = detalle_ordenes;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void onRowSelect(SelectEvent event) {
        datos = (Cliente) event.getObject();

        dato1 = Long.toString(datos.getId());
        dato2 = datos.getNombre();
        dato3 = datos.getCorreo();
        dato4 = datos.getDireccion();
        dato5 = datos.getTelefono();
        /*
        dato8 = datos.getFecha();

        if (datos.getCliente_id() != null && datos.getTotal() != null) {
            dato6 = Long.toString(datos.getCliente_id());
            dato7 = Long.toString(datos.getTotal());
        }

        if (datos.getOrden_id() != null && datos.getProducto_id() != null  && datos.getCantidad() != null && datos.getPrecio() != null) {
            dato9 = Long.toString(datos.getOrden_id());
            dato10 = Long.toString(datos.getProducto_id());
            dato11 = Long.toString(datos.getCantidad());
            dato12 = Long.toString(datos.getPrecio());
        }
        
        if (datos.getCantidad() != null && datos.getPrecio() != null){
            dato11 = Long.toString(datos.getCantidad());
            dato12 = Long.toString(datos.getPrecio());
        }
        
        dato13 = datos.getDescripcion();
         */
        band = true;

        System.out.println("ELEMENTO SELECCIONADO---------------------------");

    }

    /*
    //Cuando se selecciona un dato
    public void onRowSelect(SelectEvent event) {
        datos = (DatosDTO) event.getObject();

        dato1 = Long.toString(datos.getId());
        dato2 = datos.getNombre();
        dato3 = datos.getCorreo();
        dato4 = datos.getDireccion();
        dato5 = datos.getTelefono();

        dato8 = datos.getFecha();

        if (datos.getCliente_id() != null && datos.getTotal() != null) {
            dato6 = Long.toString(datos.getCliente_id());
            dato7 = Long.toString(datos.getTotal());
        }

        if (datos.getOrden_id() != null && datos.getProducto_id() != null  && datos.getCantidad() != null && datos.getPrecio() != null) {
            dato9 = Long.toString(datos.getOrden_id());
            dato10 = Long.toString(datos.getProducto_id());
            dato11 = Long.toString(datos.getCantidad());
            dato12 = Long.toString(datos.getPrecio());
        }
        
        if (datos.getCantidad() != null && datos.getPrecio() != null){
            dato11 = Long.toString(datos.getCantidad());
            dato12 = Long.toString(datos.getPrecio());
        }
        
        dato13 = datos.getDescripcion();
        
        band = true;
        
        
        System.out.println("ELEMENTO SELECCIONADO---------------------------");

    }
     */
    public void onRowUnselect(UnselectEvent event) {
        dato1 = "";
        dato2 = "";
        dato3 = "";
        dato4 = "";
        dato5 = "";

    }

    /**
     * @return the dato13
     */
    public String getDato13() {
        return dato13;
    }

    /**
     * @param dato13 the dato13 to set
     */
    public void setDato13(String dato13) {
        this.dato13 = dato13;
    }

    /**
     * @return the dato14
     */
    public String getDato14() {
        return dato14;
    }

    /**
     * @param dato14 the dato14 to set
     */
    public void setDato14(String dato14) {
        this.dato14 = dato14;
    }

    /**
     * @return the dato15
     */
    public String getDato15() {
        return dato15;
    }

    /**
     * @param dato15 the dato15 to set
     */
    public void setDato15(String dato15) {
        this.dato15 = dato15;
    }

    /**
     * @return the dato6
     */
    public String getDato6() {
        return dato6;
    }

    /**
     * @param dato6 the dato6 to set
     */
    public void setDato6(String dato6) {
        this.dato6 = dato6;
    }

    /**
     * @return the dato7
     */
    public String getDato7() {
        return dato7;
    }

    /**
     * @param dato7 the dato7 to set
     */
    public void setDato7(String dato7) {
        this.dato7 = dato7;
    }

    /**
     * @return the datos
     */
    public Cliente getDatos() {
        return datos;
    }

    /**
     * @param datos the datos to set
     */
    public void setDatos(Cliente datos) {
        this.datos = datos;
    }

    /**
     * @return the band
     */
    public boolean isBand() {
        return band;
    }

    /**
     * @param band the band to set
     */
    public void setBand(boolean band) {
        this.band = band;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the dato1
     */
    public String getDato1() {
        return dato1;
    }

    /**
     * @param dato1 the dato1 to set
     */
    public void setDato1(String dato1) {
        this.dato1 = dato1;
    }

    /**
     * @return the dato2
     */
    public String getDato2() {
        return dato2;
    }

    /**
     * @param dato2 the dato2 to set
     */
    public void setDato2(String dato2) {
        this.dato2 = dato2;
    }

    /**
     * @return the dato3
     */
    public String getDato3() {
        return dato3;
    }

    /**
     * @param dato3 the dato3 to set
     */
    public void setDato3(String dato3) {
        this.dato3 = dato3;
    }

    /**
     * @return the lista
     */
    public List<Cliente> getClientes() {
        return clientes;
    }

    /**
     * @param lista the lista to set
     */
    public void setClientes(List<Cliente> lista) {
        this.clientes = lista;
    }

    /**
     * @return the dato4
     */
    public String getDato4() {
        return dato4;
    }

    /**
     * @param dato4 the dato4 to set
     */
    public void setDato4(String dato4) {
        this.dato4 = dato4;
    }

    /**
     * @return the dato5
     */
    public String getDato5() {
        return dato5;
    }

    /**
     * @param dato5 the dato5 to set
     */
    public void setDato5(String dato5) {
        this.dato5 = dato5;
    }

    public void consumeWS() throws IOException, InterruptedException {
        /*
        try{
         //URL url = new URL("http://localhost:8080/LabWS/webresources/DesarrolloWeb/primerWS");
         URL url = new URL("http://localhost:8080/LabWS/webresources/DesarrolloWeb/primerWS");
         HttpURLConnection conn =(HttpURLConnection) url.openConnection();
         conn.setRequestMethod("GET");
         conn.connect();
         
         int responseCode = conn.getResponseCode();
         if(responseCode !=200){
             throw new RuntimeException("Ocurrio un error: "+  responseCode);
         }else{
             
             StringBuilder informationString = new StringBuilder();
             Scanner scanner = new Scanner(url.openStream());
             
             while(scanner.hasNext()){
                 informationString.append(scanner.nextLine());
             }
             
             scanner.close();
             
             System.out.println(informationString);
         }
        }catch(Exception e){
            e.printStackTrace();
        }
         */
 /*
        try{
           
        } catch(Exception e){
            e.printStackTrace();
        }
         */

        String url = "http://localhost:8090/LabWS/webresources/DesarrolloWeb/primerWS";

        HttpClient client = HttpClient.newHttpClient();

        // Crear una solicitud GET sin parámetros
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        // Enviar la solicitud y obtener la respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // Imprimir el cuerpo de la respuesta
        System.out.println(response.body());

        // Crear una solicitud POST con parámetros
        request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // Enviar la solicitud y obtener la respuesta
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // Imprimir el cuerpo de la respuesta
        System.out.println("respuesta: " + response.body());

        //JsonParser parser = new JsonParser();        
        //JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
        // JsonArray jsonArray = parser.parse(response.body()).getAsJsonArray();
        /*
        // Ahora puedes trabajar con el objeto JSON de tipo array
        for (int i = 0; i < jsonArray.size(); i++) {
            String nombre = jsonArray.get(i).getAsJsonObject().get("name").getAsString();
            String link = jsonArray.get(i).getAsJsonObject().get("url").getAsString();
        
           
            System.out.println("Elemento " + i + ":");
            System.out.println("Nombre: " + nombre);
            System.out.println("Edad: " + link);
        }
         */
    }

}
