package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@DataJpaTest
@Transactional
@Import(PacienteConsultaService.class)

public class PacienteConsultaServiceTest {
    

    @Autowired
    private PacienteConsultaService pacienteConsultaService;


    @Autowired
    private TestEntityManager entityManager;
    
    private PodamFactory factory = new PodamFactoryImpl();

	private List<ConsultaMedicaEntity> consultaList = new ArrayList<>();

    private List<PacienteEntity> pacienteList = new ArrayList<>();

    private PacienteEntity paciente = new PacienteEntity();
	private ConsultaMedicaEntity consultaMedica = new ConsultaMedicaEntity();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from PacienteConsultaEntity").executeUpdate();
		
	}

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ConsultaMedicaEntity consultaEntity = factory.manufacturePojo(ConsultaMedicaEntity.class);
            consultaEntity.setId(null);  // Asegurar que JPA genere un nuevo ID
            entityManager.persist(consultaEntity);
            entityManager.flush(); // Asegurar que se guarde en la BD
            consultaList.add(consultaEntity);

            PacienteEntity pacienteEntity = factory.manufacturePojo(PacienteEntity.class);
            pacienteEntity.setId(null);  // Asegurar que JPA genere un nuevo ID
            entityManager.persist(pacienteEntity);
            entityManager.flush(); // Asegurar que se guarde en la BD
            pacienteList.add(pacienteEntity);
        }
    }

    @Test
    void testAddConsulta_Success() throws EntityNotFoundException {
        PacienteEntity result = pacienteConsultaService.addConsulta(paciente.getId(), consultaMedica.getId());

        assertNotNull(result);
        assertTrue(result.getConsultasMedicas().contains(consultaMedica));
    }

    @Test
    void testAddConsulta_Fail() throws EntityNotFoundException {
        Long nonExistentPacienteId = 999L;

        assertThrows(EntityNotFoundException.class, () -> {
            pacienteConsultaService.addConsulta(nonExistentPacienteId, consultaMedica.getId());
        });
    }



}
