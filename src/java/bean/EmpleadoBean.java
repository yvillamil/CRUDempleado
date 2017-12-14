/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.EmpleadoDAO;
import data.EmpleadoDTO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author Marlon
 */
@ManagedBean
@ViewScoped
public class EmpleadoBean implements Serializable {

    private List<EmpleadoDTO> empleadosDTO;
    private EmpleadoDAO empleadoDAO;

    private EmpleadoDTO empleadoDTO = new EmpleadoDTO();
    private DataTable datatableEmpelado;

    public DataTable getDatatableEmpelado() {
        return datatableEmpelado;
    }

    public void setDatatableEmpelado(DataTable datatableEmpelado) {
        this.datatableEmpelado = datatableEmpelado;
    }

    public List<EmpleadoDTO> getEmpleados() {
        return empleadosDTO;
    }

    public void setEmpleados(List<EmpleadoDTO> empleadosDTO) {
        this.empleadosDTO = empleadosDTO;
    }

    public EmpleadoDTO getEmpleadoDTO() {
        return empleadoDTO;
    }

    public void setEmpleadoDTO(EmpleadoDTO empleadoDTO) {
        this.empleadoDTO = empleadoDTO;
    }

    @PostConstruct
    public void setup() {
        empleadosDTO = new ArrayList<>();
        empleadoDAO = new EmpleadoDAO();
        ArrayList<EmpleadoDTO> empl = new ArrayList<>();

        try {
            empl = empleadoDAO.listarEmpleado();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EmpleadoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        empleadosDTO.addAll(empl);
    }

    public void ingresarEmpleado() throws SQLException {
        empleadoDAO = new EmpleadoDAO();
        empleadoDAO.ingresarEmpleado(empleadoDTO);
        System.out.println("crear.");

        this.empleadosDTO.add(empleadoDTO);
        empleadoDTO = new EmpleadoDTO();
    }

    public void editarEmpleado() throws SQLException {
        empleadoDAO = new EmpleadoDAO();
        empleadoDAO.editarEmpleado(empleadoDTO);
        empleadoDTO = new EmpleadoDTO();
    }

    public void eliminarEmpleado() throws SQLException {
        empleadoDAO = new EmpleadoDAO();
        boolean flag = false;
        empleadoDAO.eliminarEmpleado(empleadoDTO);
        empleadosDTO.remove(empleadoDTO);
        empleadoDTO = new EmpleadoDTO();
    }

}
