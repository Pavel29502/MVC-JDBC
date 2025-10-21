
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.model.Writer;
import org.example.repository.WriterRepository;
import org.example.service.WriterService;
import org.mockito.Mockito;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {

    private WriterRepository writerRepository;
    private WriterService writerService;


    @BeforeEach
    void setUp() {
        writerRepository = Mockito.mock(WriterRepository.class);
        writerService = new WriterService(writerRepository);
    }

    @Test
    void returnWriterWithCorrectName() {
        Writer writer = new Writer();
        writer.setFirstName("Ivan");
        writer.setLastName("Petrov");
        when(writerRepository.save(any())).thenReturn(writer);

        Writer result = writerService.create("Ivan", "Petrov");

        assertEquals("Ivan", result.getFirstName());
        assertEquals("Petrov", result.getLastName());
        verify(writerRepository, times(1)).save(any());

    }

    @Test
    void getById_ShouldReturnWriter() {
        Writer writer = new Writer();
        writer.setId(1L);
        writer.setFirstName("Victor");

        when(writerRepository.findById(1L)).thenReturn(Optional.of(writer));

        Optional<Writer> result = writerService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("Victor", result.get().getFirstName());
    }

}
