package com.authorization.service;

import com.authorization.model.Merchant;
import com.authorization.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;

    @Autowired
    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public Merchant createMerchant(Merchant merchant) {
        merchant.setName(removeSpaces(merchant.getName()));
        return merchantRepository.save(merchant);
    }

    public Merchant getById(Long id) {
        return merchantRepository.findById(id).orElse(null);
    }

    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    public Merchant getByName(String name) {
        return merchantRepository.findByName(removeSpaces(name));
    }

    String removeSpaces(String input) {
        return input.replaceAll("\\s+", "");
    }
}