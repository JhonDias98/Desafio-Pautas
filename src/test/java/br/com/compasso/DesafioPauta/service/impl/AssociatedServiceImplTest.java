package br.com.compasso.DesafioPauta.service.impl;

import br.com.compasso.DesafioPauta.entity.Associated;
import br.com.compasso.DesafioPauta.exception.EmailInUseException;
import br.com.compasso.DesafioPauta.repository.AssociatedRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AssociatedServiceImplTest {

    @Mock
    private AssociatedRepository associatedRepository;

    @InjectMocks
    private AssociatedServiceImpl associatedService;


    @Test
    void listTest() {

        List<Associated> listMock = Arrays.asList(new Associated());
        when(associatedRepository.findAll()).thenReturn(listMock);
        List<Associated> allAssociatedsList = associatedService.list();
        assertEquals(listMock, allAssociatedsList);
        verify(associatedRepository).findAll();

    }

    @Test
    void findTest() {
        Associated associated = generateAssociated();
        Optional<Associated> associatedOptional = Optional.ofNullable(associated);
        when(associatedRepository.findById(eq("1"))).thenReturn(associatedOptional);
        var associatedMock = associatedService.find("1");
        assertEquals(associated, associatedMock);
        verify(associatedRepository).findById("1");
    }

    @Test
    void findTestMustThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            associatedService.find("1");
        });
    }

    @Test
    void registerTest() {

        var associated = generateAssociated();
        when(associatedRepository.save(associated)).thenReturn(associated);
        var associatedMock = associatedService.register(associated);
        assertEquals(associated, associatedMock);
        verify(associatedRepository).save(associated);

    }

    @Test
    void registerWithEmailInUse() {

        Associated associated = generateAssociated();

        when(associatedService.register(associated)).thenThrow(new EmailInUseException());

        assertThrows(EmailInUseException.class, () -> {
           associatedService.register(associated);
        });
        
    }

    @Test
    void deleteTest() {

        doNothing().when(associatedRepository).deleteById("1");
        associatedService.delete("1");
        verify(associatedRepository).deleteById("1");

    }

    private Associated generateAssociated() {
        return Associated.builder()
                .id("1")
                .name("Matheus")
                .email("matheus@email.com")
                .build();
    }
}