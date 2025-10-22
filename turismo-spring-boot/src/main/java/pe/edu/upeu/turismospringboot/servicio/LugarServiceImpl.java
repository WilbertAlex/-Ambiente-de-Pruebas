package pe.edu.upeu.turismospringboot.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.turismospringboot.model.dto.LugarDto;
import pe.edu.upeu.turismospringboot.model.entity.Familia;
import pe.edu.upeu.turismospringboot.model.entity.Lugar;
import pe.edu.upeu.turismospringboot.repositorio.LugarRepository;
import pe.edu.upeu.turismospringboot.service.LugarService;
import pe.edu.upeu.turismospringboot.util.ArchivoUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LugarServiceImpl implements LugarService {
    @Autowired
    private LugarRepository lugarRepository;

    @Override
    public List<Lugar> getlugares() {
        return lugarRepository.findAll();
    }

    @Override
    public Lugar getLugarById(Long idLugar) {
        return lugarRepository.findById(idLugar).orElseThrow(() -> new RuntimeException("Lugar con id: "+idLugar+" no encontrado"));
    }

    @Override
    public Lugar postLugar(LugarDto lugarDto, MultipartFile file) {
        Lugar lugarCreado = new Lugar();
        lugarCreado.setNombre(lugarDto.getNombre());
        lugarCreado.setDescripcion(lugarDto.getDescripcion());
        lugarCreado.setDireccion(lugarDto.getDireccion());
        lugarCreado.setCiudad(lugarDto.getCiudad());
        lugarCreado.setProvincia(lugarDto.getProvincia());
        lugarCreado.setPais(lugarDto.getPais());
        lugarCreado.setLatitud(lugarDto.getLatitud());
        lugarCreado.setLongitud(lugarDto.getLongitud());
        if (file != null && !file.isEmpty()) {
            String fileName = ArchivoUtil.saveFile(file);
            lugarCreado.setImagenUrl(fileName);
        }
        return lugarRepository.save(lugarCreado);
    }

    @Override
    public Lugar putLugar(Long idLugar, LugarDto lugarDto, MultipartFile file) {
        Lugar lugarEncontrado = lugarRepository.findById(idLugar).orElseThrow(() -> new RuntimeException("Lugar con id: "+idLugar+"no encontrado"));
        lugarEncontrado.setNombre(lugarDto.getNombre());
        lugarEncontrado.setDescripcion(lugarDto.getDescripcion());
        lugarEncontrado.setDireccion(lugarDto.getDireccion());
        lugarEncontrado.setCiudad(lugarDto.getCiudad());
        lugarEncontrado.setProvincia(lugarDto.getProvincia());
        lugarEncontrado.setPais(lugarDto.getPais());
        lugarEncontrado.setLatitud(lugarDto.getLatitud());
        lugarEncontrado.setLongitud(lugarDto.getLongitud());
        if (file != null && !file.isEmpty()) {
            String fileName = ArchivoUtil.saveFile(file);
            lugarEncontrado.setImagenUrl(fileName);
        }
        return lugarRepository.save(lugarEncontrado);
    }

    @Override
    public void deleteLugar(Long idLugar) {
        lugarRepository.deleteById(idLugar);
    }

    @Override
    public List<Lugar> buscarLugarPorNombre(String nombre) {
        return lugarRepository.buscarPorNombre(nombre);
    }

    @Override
    public List<Familia> getFamiliasPorLugar(Long idLugar, String nombre) {
        Lugar lugar = lugarRepository.findById(idLugar)
                .orElseThrow(() -> new RuntimeException("Lugar no encontrado"));

        List<Familia> familias = lugar.getFamilias();

        if (nombre != null && !nombre.isEmpty()) {
            return familias.stream()
                    .filter(f -> f.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return familias;
    }

}
