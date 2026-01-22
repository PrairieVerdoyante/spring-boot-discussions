package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.CategoryEntity;
import ch.hearc.jee2024.aubrysarahprojetpratiquesa.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryServiceImpl service;

    private CategoryEntity category;

    @BeforeEach
    void setUp() {
        category = new CategoryEntity();
        category.setId(1L);
        category.setName("Test");
    }

    @Test
    void save_shouldReturnSavedCategory() {
        when(repository.save(category)).thenReturn(category);

        CategoryEntity result = service.save(category);

        assertNotNull(result);
        assertEquals("Test", result.getName());
        verify(repository, times(1)).save(category);
    }

    @Test
    void findById_shouldReturnCategory() {
        when(repository.findById(1L)).thenReturn(Optional.of(category));

        Optional<CategoryEntity> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test", result.get().getName());
        verify(repository).findById(1L);
    }

    @Test
    void findById_shouldReturnEmpty() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<CategoryEntity> result = service.findById(2L);

        assertTrue(result.isEmpty());
        verify(repository).findById(2L);
    }

    @Test
    void findAll_shouldReturnAllCategories() {
        when(repository.findAll()).thenReturn(List.of(category));

        Iterable<CategoryEntity> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, ((List<?>) result).size());
        verify(repository).findAll();
    }

    @Test
    void existsByName_shouldReturnTrue() {
        when(repository.existsByName("Test")).thenReturn(true);

        boolean result = service.existsByName("Test");

        assertTrue(result);
        verify(repository).existsByName("Test");
    }

    @Test
    void existsByName_shouldReturnFalse() {
        when(repository.existsByName("Unknown")).thenReturn(false);

        boolean result = service.existsByName("Unknown");

        assertFalse(result);
        verify(repository).existsByName("Unknown");
    }
}
