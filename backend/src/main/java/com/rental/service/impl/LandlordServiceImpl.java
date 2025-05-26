package com.rental.service.impl;

import com.rental.model.Landlord;
import com.rental.model.User;
import com.rental.repository.LandlordRepository;
import com.rental.service.LandlordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LandlordServiceImpl implements LandlordService {

    @Autowired
    private LandlordRepository landlordRepository;

    @Override
    public void createFromUser(User user) {
        Landlord landlord = new Landlord(); // ✅ 修正這裡

        landlord.setEmail(user.getEmail());
        landlord.setName(user.getUsername());
        landlord.setPassword(user.getPassword());
        landlord.setPhone(user.getPhoneNumber());
        landlord.setVerified(false);

        landlordRepository.save(landlord);
    }
}
