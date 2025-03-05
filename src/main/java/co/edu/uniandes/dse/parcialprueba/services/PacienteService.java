package co.edu.uniandes.dse.parcialprueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    public PacienteEntity createPaciente(String nombre, Integer edad, Integer celular, String correo) throws IllegalOperationException, EntityNotFoundException {

        if (nombre == null) {
                throw new EntityNotFoundException("El nombre no puede ser nulo");
            }
        if (edad == null) {
                throw new EntityNotFoundException("La edad no puede ser nula");
            }
        if (celular == null) {
               throw new EntityNotFoundException("El celular no puede ser nulo");
            }
        if (correo == null) {
                throw new EntityNotFoundException("El correo no puede ser nulo");
             }    
        
        PacienteEntity paciente = new PacienteEntity();

        paciente.setNombre(nombre);
        paciente.setEdad(edad);
        paciente.setCelular(celular);
        paciente.setCorreo(correo);


        PacienteEntity pacienteCreado = pacienteRepository.save(paciente);

        return pacienteCreado;
    }
    
}
