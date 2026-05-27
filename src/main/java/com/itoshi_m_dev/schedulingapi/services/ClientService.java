package com.itoshi_m_dev.schedulingapi.services;

import com.itoshi_m_dev.schedulingapi.model.Client;
import com.itoshi_m_dev.schedulingapi.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public Client registerClient (Client client){
            var senhaCrip = encoder.encode(client.getClientSecret());
            client.setClientSecret(senhaCrip);
            return repository.save(client);
    }

    public Client getClientById(String clientId){
        return repository.findByClientId(clientId);
    }
}
