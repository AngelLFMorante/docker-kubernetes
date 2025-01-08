package org.afernandez.springcloud.msvc.cursos.clients;

import java.util.List;
import org.afernandez.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

//tienen que ser identico que el micro que queremos, lo tenemos en application.properties
//la url tiene que ser la ruta de donde tenemos el micro levantado en application.properties en port lo tenemos
// ahora hemos cambiado el localhost por msvc-usuarios
@FeignClient(name = "msvc-usuarios", url = "msvc-usuarios:8001")
public interface UsuarioClientRest {

    //tenemos que poner la ruta igual que la que tenemos en el controller del micro Usuarios
    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping
    Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/usuarios-por-curso")
    List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);
}
