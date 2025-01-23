package com.github.gabrielvelosoo.libraryapi.services;

import com.github.gabrielvelosoo.libraryapi.models.Client;
import com.github.gabrielvelosoo.libraryapi.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    public void saveClient(Client client) {
        String encryptedSecret = encoder.encode(client.getClientSecret());
        client.setClientSecret(encryptedSecret);
        clientRepository.save(client);
    }

    public Client findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId);
    }
}
