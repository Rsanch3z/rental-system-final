package com.rental.service.impl;

import com.rental.model.Tenant;
import com.rental.model.User;
import com.rental.repository.TenantRepository;
import com.rental.service.TenantService; // 介面
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public void createFromUser(User user) {
        Tenant tenant = new Tenant();
        tenant.setEmail(user.getEmail());
        tenant.setName(user.getUsername());
        tenant.setPassword(user.getPassword());
        tenant.setPhone(user.getPhoneNumber());

        tenantRepository.save(tenant);
    }
}
