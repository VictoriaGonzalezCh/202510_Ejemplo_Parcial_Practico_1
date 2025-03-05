package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.ConsultaMedicaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class ConsultaMedicaService {
    
    @Autowired
    private ConsultaMedicaRepository consultaMedicaRepository;

    @Transactional
    public ConsultaMedicaEntity createConsulta(String causa, Date fecha) throws IllegalOperationException {

        if (fecha.after(new Date())) {throw new IllegalOperationException(fecha);}

        ConsultaMedicaEntity consulta = new ConsultaMedicaEntity();

        consulta.setCausa(causa);
        consulta.setFecha(fecha);
        

        ConsultaMedicaEntity consultaCreada = consultaMedicaRepository.save(consulta);

        return consultaCreada;
    }
}
