package co.edu.uniandes.dse.parcialprueba.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.repositories.ConsultaMedicaRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class PacienteConsultaService {
    
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaMedicaRepository consultaMedicaRepository;

    @Transactional

    public PacienteEntity addConsulta(Long pacienteId, Long consultaMedicaId) throws EntityNotFoundException{
        
        java.util.Optional<PacienteEntity> pacienteOpt = pacienteRepository.findById(pacienteId);
        java.util.Optional<ConsultaMedicaEntity> consultaOpt = consultaMedicaRepository.findById(consultaMedicaId);

        if (pacienteOpt.isEmpty()) { throw new EntityNotFoundException("No existe el paciente");}

        if (consultaOpt.isEmpty()) { throw new EntityNotFoundException("No existe la consulta");}

        PacienteEntity paciente = pacienteOpt.get();
        ConsultaMedicaEntity consulta = consultaOpt.get();

        if ((paciente.getConsultasMedicas() == consulta.getFecha())) { throw new EntityNotFoundException("No existe la entidad");}

        paciente.getConsultasMedicas().add(consulta);
        pacienteRepository.save(paciente);
        return paciente;

    }

    
     @Transactional

    public List<ConsultaMedicaEntity> getConsultasProgramadas(Long pacienteId) throws EntityNotFoundException{

        List<ConsultaMedicaEntity> listaConsultas = new ArrayList<>();
        
        java.util.Optional<PacienteEntity> pacienteOpt = pacienteRepository.findById(pacienteId);
        if (pacienteOpt.isEmpty()) { throw new EntityNotFoundException("No existe el paciente");}

        PacienteEntity paciente = pacienteOpt.get();

        List<ConsultaMedicaEntity> consultasPaciente = paciente.getConsultasMedicas();

        for (ConsultaMedicaEntity consulta: consultasPaciente) {
            if (consulta.getFecha().after(new java.util.Date())) {
                listaConsultas.add(consulta);
            }
        }

        return listaConsultas;
    }

}
