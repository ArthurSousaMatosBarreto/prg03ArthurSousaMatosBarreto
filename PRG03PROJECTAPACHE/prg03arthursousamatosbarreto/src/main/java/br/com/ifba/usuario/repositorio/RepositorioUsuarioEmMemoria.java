package br.com.ifba.usuario.repositorio;

import br.com.ifba.usuario.entity.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RepositorioUsuarioEmMemoria { //guarda usuarios cadastrados em memoria por falta de banco de dados

    // generics List<Usuario> diz ao compilador o que tem dentro da lista, sem isso cada objeto que sai de la precisaria de cast pra Usuario
    private final List<Usuario> usuarios = new ArrayList<>(); 
    public void cadastrar(Usuario usuario) {
        usuarios.add(usuario);
    }
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios); // copia pra nao expor a lista interna
    }
}