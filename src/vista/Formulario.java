package vista;

import dao.FuncionarioDAO;
import dao.FuncionarioDAOImpl;
import modelo.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class Formulario extends JFrame {

    JTextField txtId, txtDocumento, txtNombres, txtApellidos, txtDireccion, txtTelefono, txtFecha;
    JButton btnGuardar, btnActualizar, btnEliminar, btnNuevo;
    JTable tabla;
    DefaultTableModel modelo;

    FuncionarioDAO dao = new FuncionarioDAOImpl();

    public Formulario() {

        setTitle("Gestión Funcionarios");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 4, 5, 5));

        txtId = new JTextField();
        txtDocumento = new JTextField();
        txtNombres = new JTextField();
        txtApellidos = new JTextField();
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();
        txtFecha = new JTextField();

        txtId.setEnabled(false);

        panel.add(new JLabel("ID"));
        panel.add(txtId);

        panel.add(new JLabel("Documento"));
        panel.add(txtDocumento);

        panel.add(new JLabel("Nombres"));
        panel.add(txtNombres);

        panel.add(new JLabel("Apellidos"));
        panel.add(txtApellidos);

        panel.add(new JLabel("Dirección"));
        panel.add(txtDireccion);

        panel.add(new JLabel("Teléfono"));
        panel.add(txtTelefono);

        panel.add(new JLabel("Fecha Nac (AAAA-MM-DD)"));
        panel.add(txtFecha);

        add(panel, BorderLayout.NORTH);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Documento");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");

        tabla = new JTable(modelo);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel botones = new JPanel();

        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnNuevo = new JButton("Nuevo");

        botones.add(btnGuardar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        botones.add(btnNuevo);

        add(botones, BorderLayout.SOUTH);

        listar();

        btnGuardar.addActionListener(e -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnNuevo.addActionListener(e -> limpiar());

        tabla.getSelectionModel().addListSelectionListener(e -> seleccionarFila());
    }

    public void listar() {

        modelo.setRowCount(0);

        List<Funcionario> lista = dao.listar();

        for (Funcionario f : lista) {
            modelo.addRow(new Object[]{
                    f.getId(),
                    f.getNumeroIdentificacion(),
                    f.getNombres(),
                    f.getApellidos()
            });
        }
    }

    public void guardar() {

        try {

            Funcionario f = new Funcionario();

            f.setNumeroIdentificacion(txtDocumento.getText());
            f.setNombres(txtNombres.getText());
            f.setApellidos(txtApellidos.getText());
            f.setTipoIdentificacionId(1);
            f.setEstadoCivilId(1);
            f.setGeneroId(1);
            f.setDireccion(txtDireccion.getText());
            f.setTelefono(txtTelefono.getText());
            f.setFechaNacimiento(Date.valueOf(txtFecha.getText()));

            dao.guardar(f);

            listar();
            limpiar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar");
        }
    }

    public void actualizar() {

        try {

            Funcionario f = new Funcionario();

            f.setId(Integer.parseInt(txtId.getText()));
            f.setNumeroIdentificacion(txtDocumento.getText());
            f.setNombres(txtNombres.getText());
            f.setApellidos(txtApellidos.getText());
            f.setTipoIdentificacionId(1);
            f.setEstadoCivilId(1);
            f.setGeneroId(1);
            f.setDireccion(txtDireccion.getText());
            f.setTelefono(txtTelefono.getText());
            f.setFechaNacimiento(Date.valueOf(txtFecha.getText()));

            dao.actualizar(f);

            listar();
            limpiar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar");
        }
    }

    public void eliminar() {

        try {
            int id = Integer.parseInt(txtId.getText());

            dao.eliminar(id);

            listar();
            limpiar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Seleccione registro");
        }
    }

    public void seleccionarFila() {

        int fila = tabla.getSelectedRow();

        if (fila >= 0) {

            txtId.setText(tabla.getValueAt(fila, 0).toString());
            txtDocumento.setText(tabla.getValueAt(fila, 1).toString());
            txtNombres.setText(tabla.getValueAt(fila, 2).toString());
            txtApellidos.setText(tabla.getValueAt(fila, 3).toString());
        }
    }

    public void limpiar() {

        txtId.setText("");
        txtDocumento.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtFecha.setText("");
    }
}