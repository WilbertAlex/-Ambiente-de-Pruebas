package pe.edu.upeu.turismospringboot.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.turismospringboot.model.dto.EmprendimientoDto;
import pe.edu.upeu.turismospringboot.model.entity.Emprendimiento;
import pe.edu.upeu.turismospringboot.model.entity.FamiliaCategoria;
import pe.edu.upeu.turismospringboot.model.entity.Usuario;
import pe.edu.upeu.turismospringboot.repositorio.EmprendimientoRepository;
import pe.edu.upeu.turismospringboot.repositorio.FamiliaCategoriaRepository;
import pe.edu.upeu.turismospringboot.repositorio.UsuarioRepository;
import pe.edu.upeu.turismospringboot.service.EmprendimientoService;
import pe.edu.upeu.turismospringboot.util.ArchivoUtil;

import java.util.List;

@Service
public class EmprendimientoServiceImpl implements EmprendimientoService {
    @Autowired
    private EmprendimientoRepository emprendimientoRepository;

    @Autowired
    private FamiliaCategoriaRepository familiaCategoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Emprendimiento> getEmprendimientos() {
        return emprendimientoRepository.findAll();
    }

    @Override
    public Emprendimiento getEmprendimientoById(Long idEmprendimiento) {
        return emprendimientoRepository.findById(idEmprendimiento).orElseThrow(() -> new RuntimeException("Emprendimiento con id "+idEmprendimiento+" no encontrado"));
    }

    @Override
    public Emprendimiento postEmprendimiento(EmprendimientoDto emprendimientoDto, MultipartFile file) {
        FamiliaCategoria familiaCategoria = familiaCategoriaRepository.findById(emprendimientoDto.getIdFamiliaCategoria()).orElseThrow(() -> new RuntimeException("Familia Categoria con id "+emprendimientoDto.getIdFamiliaCategoria()+" no encontrada"));
        Emprendimiento emprendimiento = new Emprendimiento();
        emprendimiento.setNombre(emprendimientoDto.getNombre());
        emprendimiento.setDescripcion(emprendimientoDto.getDescripcion());
        emprendimiento.setLatitud(emprendimientoDto.getLatitud());
        emprendimiento.setLongitud(emprendimientoDto.getLongitud());
        if(file != null && !file.isEmpty()) {
            String fileName = ArchivoUtil.saveFile(file);
            emprendimiento.setImagenUrl(fileName);
        }
        emprendimiento.setFamiliaCategoria(familiaCategoria);
        return emprendimientoRepository.save(emprendimiento);
    }

    @Override
    public Emprendimiento putEmprendimiento(Long idEmprendimiento, EmprendimientoDto emprendimientoDto, MultipartFile file) {
        FamiliaCategoria familiaCategoria = familiaCategoriaRepository.findById(emprendimientoDto.getIdFamiliaCategoria()).orElseThrow(() -> new RuntimeException("Familia Categoria con id "+emprendimientoDto.getIdFamiliaCategoria()+" no encontrada"));
        Emprendimiento emprendimientoEncontrado = emprendimientoRepository.findById(idEmprendimiento).orElseThrow(() -> new RuntimeException("Emprendimiento con id "+idEmprendimiento+" no encontrado"));
        emprendimientoEncontrado.setNombre(emprendimientoDto.getNombre());
        emprendimientoEncontrado.setDescripcion(emprendimientoDto.getDescripcion());
        emprendimientoEncontrado.setLatitud(emprendimientoDto.getLatitud());
        emprendimientoEncontrado.setLongitud(emprendimientoDto.getLongitud());
        if(file != null && !file.isEmpty()) {
            String fileName = ArchivoUtil.saveFile(file);
            emprendimientoEncontrado.setImagenUrl(fileName);
        }
        emprendimientoEncontrado.setFamiliaCategoria(familiaCategoria);
        return emprendimientoRepository.save(emprendimientoEncontrado);
    }

    @Override
    public void deleteEmprendimiento(Long idEmprendimiento) {
        emprendimientoRepository.deleteById(idEmprendimiento);
    }

    @Override
    public List<Emprendimiento> buscarPorNombre(String nombre) {
        return emprendimientoRepository.buscarPorNombre(nombre);
    }

    @Override
    public Emprendimiento buscarPorIdUsuario(Long idUsuario){
        Usuario usuarioEncontrado = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new RuntimeException("Usuario con id "+ idUsuario + " no encontrado")
        );

        Emprendimiento emprendimiento = usuarioEncontrado.getEmprendimiento();
        return emprendimiento;
    }
}
