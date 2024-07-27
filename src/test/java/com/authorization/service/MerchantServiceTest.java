package com.authorization.service;

import com.authorization.model.Merchant;
import com.authorization.repository.MerchantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MerchantServiceTest {

    @Mock
    private MerchantRepository merchantRepository;

    @InjectMocks
    private MerchantService merchantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMerchant() {
        // Arrange
        Merchant merchant = new Merchant();
        merchant.setName("Test Merchant");
        Merchant savedMerchant = new Merchant();
        savedMerchant.setName("TestMerchant");
        when(merchantRepository.save(any(Merchant.class))).thenReturn(savedMerchant);

        // Act
        Merchant createdMerchant = merchantService.createMerchant(merchant);

        // Assert
        assertEquals("TestMerchant", createdMerchant.getName());
        verify(merchantRepository, times(1)).save(any(Merchant.class));
    }

    @Test
    public void testGetById() {
        // Arrange
        Long merchantId = 1L;
        Merchant merchant = new Merchant();
        when(merchantRepository.findById(merchantId)).thenReturn(Optional.of(merchant));

        // Act
        Merchant foundMerchant = merchantService.getById(merchantId);

        // Assert
        assertEquals(merchant, foundMerchant);
        verify(merchantRepository, times(1)).findById(merchantId);
    }

    @Test
    public void testGetAllMerchants() {
        // Arrange
        List<Merchant> merchants = List.of(new Merchant(), new Merchant());
        when(merchantRepository.findAll()).thenReturn(merchants);

        // Act
        List<Merchant> allMerchants = merchantService.getAllMerchants();

        // Assert
        assertEquals(merchants, allMerchants);
        verify(merchantRepository, times(1)).findAll();
    }

    @Test
    public void testGetByName() {
        // Arrange
        String merchantName = "Test Merchant";
        Merchant merchant = new Merchant();
        merchant.setName("TestMerchant");
        when(merchantRepository.findByName("TestMerchant")).thenReturn(merchant);

        // Act
        Merchant foundMerchant = merchantService.getByName(merchantName);

        // Assert
        assertEquals(merchant, foundMerchant);
        verify(merchantRepository, times(1)).findByName("TestMerchant");
    }

    @Test
    public void testRemoveSpaces() {
        String nameWithSpaces = "Test   Merchant";
        String expectedName = "TestMerchant";

        String result = merchantService.removeSpaces(nameWithSpaces);
        assertEquals(expectedName, result);
    }
}
