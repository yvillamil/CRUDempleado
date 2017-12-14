/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connectionPostgres.ConnectionDb;
import data.EmpleadoDTO;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marlon
 */
public class EmpleadoDAO {

    private Connection cn;
    private CallableStatement cs;
    private ResultSet rs;
    private ConnectionDb oc;
    private Statement statement = null;

    ConnectionDb conn = new ConnectionDb();

    public ArrayList<EmpleadoDTO> listarEmpleado() throws SQLException, ClassNotFoundException {
        ArrayList<EmpleadoDTO> empleadosDTO = new ArrayList<>();
        oc = new ConnectionDb();
        cn = ConnectionDb.getConnection();
        rs = null;
        cs = null;
        EmpleadoDTO empleadoDTO;
        String sql = "SELECT * FROM empleado";
        System.out.println("Listar empleados");
        cs = cn.prepareCall(sql);
        statement = cn.createStatement();
        rs = statement.executeQuery(sql);
        while (rs.next()) {
            empleadoDTO = new EmpleadoDTO();
            empleadoDTO.setIdempleado(rs.getInt("idempleado"));
            empleadoDTO.setNombre(rs.getString("nombre"));
            empleadoDTO.setCedula(rs.getInt("cedula"));
            empleadoDTO.setCorreo(rs.getString("correo"));

            empleadosDTO.add(empleadoDTO);
        }
        closeConection(cn);
        return empleadosDTO;
    }

    public void ingresarEmpleado(EmpleadoDTO empleadoDTO) {
        System.out.println("crear....");
        try {
            oc = new ConnectionDb();
            cn = ConnectionDb.getConnection();
            rs = null;
            cs = null;
            cn.setAutoCommit(false);

            String sql = "INSERT INTO empleado (nombre,cedula,correo) values ('"
                    + empleadoDTO.getNombre() + "','"
                    + empleadoDTO.getCedula() + "','"
                    + empleadoDTO.getCorreo()+"');";
            statement = cn.createStatement();
            statement.executeUpdate(sql);
            cn.commit();
            System.out.println("try crear empleados...");
            closeConection(cn);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("cath crear empleados...");
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public boolean editarEmpleado(EmpleadoDTO empleadoDTO) {
        try {
            oc = new ConnectionDb();
            cn = ConnectionDb.getConnection();
            rs = null;
            cs = null;
            cn.setAutoCommit(false);

            java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            String sql = "update empleado set nombre='" + empleadoDTO.getNombre() + "', "
                    + "cedula='" + empleadoDTO.getCedula() + "'" + ", "
                    + "correo='" + empleadoDTO.getCorreo()+";";
            //                +sqlDate+;
            statement = cn.createStatement();
            statement.executeUpdate(sql);
            cn.commit();
            cerrarConexiones(cn);
            return true;
        } catch (Exception e) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean eliminarEmpleado(EmpleadoDTO empleadoDTO) {
        try {
            System.out.println("Cedula : " + empleadoDTO.getIdempleado());
            oc = new ConnectionDb();
            cn = ConnectionDb.getConnection();
            rs = null;
            cs = null;
            cn.setAutoCommit(false);
            String sql = "delete from empleado"
                    + "  where idempleado=" + empleadoDTO.getIdempleado()+";";
            //                +sqlDate+;
            statement = cn.createStatement();
            statement.executeUpdate(sql);
            cn.commit();
            cerrarConexiones(cn);
            return true;
        } catch (Exception e) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    private void cerrarConexiones(Connection c) throws SQLException {
        if (c != null) {
            rs.close();
            cs.close();
            c.close();
        }
    }

    /**
     * *
     * Functions *
     */
    private void closeConection(Connection cn) {
        try {
            if (!cn.isClosed()) {
                rs.close();
                cs.close();
                cn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
