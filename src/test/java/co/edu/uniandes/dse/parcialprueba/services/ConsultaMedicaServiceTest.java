package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.ConsultaMedicaEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ConsultaMedicaService.class)
public class ConsultaMedicaServiceTest {

    @Autowired
    private ConsultaMedicaService consultaMedicaService;
    
    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

	private List<ConsultaMedicaEntity> consultaList = new ArrayList<>();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from ConsultaMedicaEntity").executeUpdate();
		
	}

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ConsultaMedicaEntity consultaEntity = factory.manufacturePojo(ConsultaMedicaEntity.class);
            consultaEntity.setId(null);  // Asegurar que JPA genere un nuevo ID
            entityManager.persist(consultaEntity);
            entityManager.flush(); // Asegurar que se guarde en la BD
            consultaList.add(consultaEntity);
        }
    }

    @Test
    void testCreateconsulta() throws IllegalOperationException {

    @SuppressWarnings("deprecation")
    Date fecha = new Date(0, 0, 0);
    

    ConsultaMedicaEntity result = consultaMedicaService.createConsulta("gripa", fecha );
    ConsultaMedicaEntity entity = entityManager.find(ConsultaMedicaEntity.class, result.getId());

    assertNotNull(entity); // Verificar que el objeto fue guardado
    assertEquals(result.getId(), entity.getId());
    assertEquals(result.getCausa(), entity.getFecha()); // Asegurar que los nombres coinciden
}

    
}
