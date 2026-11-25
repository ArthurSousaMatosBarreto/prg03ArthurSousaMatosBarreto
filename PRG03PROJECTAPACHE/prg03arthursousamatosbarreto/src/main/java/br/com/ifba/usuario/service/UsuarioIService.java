package br.com.ifba.usuario.service;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;

public interface UsuarioIService {

    Usuario save(Usuario usuario);

    Usuario update(Usuario usuario);

    void delete(Usuario usuario);

    Usuario findById(Long id);

    List<Usuario> findAll();

    List<Usuario> findByNome(String termo);

    Usuario autenticar(String login, String senha);
}