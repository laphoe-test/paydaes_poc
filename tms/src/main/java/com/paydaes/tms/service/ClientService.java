package com.paydaes.tms.service;

import com.paydaes.entities.dto.ClientDto;
import com.paydaes.entities.model.Client;
import com.paydaes.entities.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    public ClientDto createClient(ClientDto clientDto) {
        // Validate if email already exists
        if (clientRepository.existsByEmail(clientDto.getEmail())) {
            throw new IllegalArgumentException("Client with email already exists: " + clientDto.getEmail());
        }
        
        Client client = Client.builder()
                .name(clientDto.getName())
                .email(clientDto.getEmail())
                .phoneNumber(clientDto.getPhoneNumber()).build();
        
        Client savedClient = clientRepository.save(client);
        return convertToDto(savedClient);
    }
    
    public ClientDto getClientById(Long id) {
        return clientRepository.findById(id).map(this::convertToDto).orElseThrow(RuntimeException::new);
    }
    
    public Optional<ClientDto> getClientByEmail(String email) {
        return clientRepository.findByEmail(email).map(this::convertToDto);
    }
    
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<ClientDto> searchClientsByName(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public ClientDto updateClient(Long id, ClientDto clientDto) {
        Optional<Client> existingClient = clientRepository.findById(id);
        if (existingClient.isEmpty()) {
            throw new RuntimeException("Client not found with id: " + id);
        }
        
        Client client = existingClient.get();
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        client.setUpdatedAt(LocalDateTime.now());
        
        Client updatedClient = clientRepository.save(client);
        return convertToDto(updatedClient);
    }
    
    public void deleteClient(Long id) {
        if (!clientRepository.findById(id).isPresent()) {
            throw new RuntimeException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }
    
    public long getTotalClientCount() {
        return clientRepository.countTotalClients();
    }
    
    private ClientDto convertToDto(Client client) {
        return new ClientDto(
            client.getId(),
            client.getName(),
            client.getEmail(),
            client.getPhoneNumber(),
            client.getCreatedAt(),
            client.getUpdatedAt()
        );
    }
}