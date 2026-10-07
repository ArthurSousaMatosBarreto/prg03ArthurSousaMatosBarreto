package br.com.ifba.usuario.repositorio;

import br.com.ifba.usuario.entity.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class RepositorioUsuarioEmMemoria { //guarda usuarios cadastrados em memoria pq nao tem banco de dados

    //generics List<Usuario> diz ao compilador o que tem dentro da lista, sem isso cada objeto que sai de la precisaria de cast pra Usuario
    private final List<Usuario> usuarios = new ArrayList<>(); 

    private final Map<String, Usuario> porLogin = new HashMap<>(); //indice por login pra busca rapida ao inves de percorrer

    public void cadastrar(Usuario usuario) {
        usuarios.add(usuario);
        porLogin.put(usuario.getLogin(), usuario);
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios); //copia pra nao expor a lista interna
    }
    public Usuario buscarPorLoginComList(String login) { //busca usando list percorrendo um por um O(n)
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(login)) {
                return usuario;
            }
        }
        return null;
    }
    // busca usando o Map (consulta direta pela chave, O(1))
    public Usuario buscarPorLogin(String login) { //busca usando o map, consulta direta pela chave O(1)
        return porLogin.get(login);
    }
}