package dao;

import data.Conexion;
import modelo.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAOImpl implements FuncionarioDAO {

    @Override
    public List<Funcionario> listar() {

        List<Funcionario> lista = new ArrayList<>();

        String sql = "SELECT * FROM funcionarios";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Funcionario f = new Funcionario();

                f.setId(rs.getInt("id"));
                f.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                f.setNombres(rs.getString("nombres"));
                f.setApellidos(rs.getString("apellidos"));
                f.setTipoIdentificacionId(rs.getInt("tipo_identificacion_id"));
                f.setEstadoCivilId(rs.getInt("estado_civil_id"));
                f.setGeneroId(rs.getInt("genero_id"));
                f.setDireccion(rs.getString("direccion"));
                f.setTelefono(rs.getString("telefono"));
                f.setFechaNacimiento(rs.getDate("fecha_nacimiento"));

                lista.add(f);
            }

        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public boolean guardar(Funcionario f) {

        String sql = "INSERT INTO funcionarios(numero_identificacion,nombres,apellidos,tipo_identificacion_id,estado_civil_id,genero_id,direccion,telefono,fecha_nacimiento) VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, f.getNumeroIdentificacion());
            ps.setString(2, f.getNombres());
            ps.setString(3, f.getApellidos());
            ps.setInt(4, f.getTipoIdentificacionId());
            ps.setInt(5, f.getEstadoCivilId());
            ps.setInt(6, f.getGeneroId());
            ps.setString(7, f.getDireccion());
            ps.setString(8, f.getTelefono());
            ps.setDate(9, f.getFechaNacimiento());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Funcionario f) {

        String sql = "UPDATE funcionarios SET numero_identificacion=?, nombres=?, apellidos=?, tipo_identificacion_id=?, estado_civil_id=?, genero_id=?, direccion=?, telefono=?, fecha_nacimiento=? WHERE id=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, f.getNumeroIdentificacion());
            ps.setString(2, f.getNombres());
            ps.setString(3, f.getApellidos());
            ps.setInt(4, f.getTipoIdentificacionId());
            ps.setInt(5, f.getEstadoCivilId());
            ps.setInt(6, f.getGeneroId());
            ps.setString(7, f.getDireccion());
            ps.setString(8, f.getTelefono());
            ps.setDate(9, f.getFechaNacimiento());
            ps.setInt(10, f.getId());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {

        String sql = "DELETE FROM funcionarios WHERE id=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}