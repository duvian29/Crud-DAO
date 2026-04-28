package dao;

import java.util.List;
import modelo.Funcionario;

public interface FuncionarioDAO {

    public List<Funcionario> listar();

    public boolean guardar(Funcionario f);

    public boolean actualizar(Funcionario f);

    public boolean eliminar(int id);
}