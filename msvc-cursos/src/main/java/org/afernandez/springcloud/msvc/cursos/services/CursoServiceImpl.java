package org.afernandez.springcloud.msvc.cursos.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.afernandez.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.afernandez.springcloud.msvc.cursos.models.Usuario;
import org.afernandez.springcloud.msvc.cursos.models.entity.Curso;
import org.afernandez.springcloud.msvc.cursos.models.entity.CursoUsuario;
import org.afernandez.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoServiceImpl implements CursoService{

    private final CursoRepository repository;

    private final UsuarioClientRest client;

    public CursoServiceImpl(CursoRepository repository, UsuarioClientRest client) {
        this.repository = repository;
        this.client = client;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        repository.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porIdConUsuarios(Long id) {
        Optional<Curso> optionalCurso = repository.findById(id);
        if (optionalCurso.isPresent()){
            Curso curso = optionalCurso.get();
            if(!curso.getCursoUsuarios().isEmpty()){
                List<Long> ids = curso.getCursoUsuarios()
                        .stream()
                        .map(CursoUsuario::getUsuarioId)
                        .toList();
                List<Usuario> usuarios = client.obtenerAlumnosPorCurso(ids);
                curso.setUsuarios(usuarios);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> optionalCurso = repository.findById(cursoId);
        if(optionalCurso.isPresent()){
            Usuario usuarioMsvc = client.detalle(usuario.getId());

            Curso curso = optionalCurso.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            curso.addCursoUsuario(cursoUsuario);

            repository.save(curso);
            return  Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> optionalCurso = repository.findById(cursoId);
        if(optionalCurso.isPresent()){
            Usuario usuarioMsvc = client.crear(usuario);

            Curso curso = optionalCurso.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            curso.addCursoUsuario(cursoUsuario);

            repository.save(curso);
            return  Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> optionalCurso = repository.findById(cursoId);
        if(optionalCurso.isPresent()){
            Usuario usuarioMsvc = client.detalle(usuario.getId());

            Curso curso = optionalCurso.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            curso.removeCursoUsuario(cursoUsuario);

            repository.save(curso);
            return  Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }
}
