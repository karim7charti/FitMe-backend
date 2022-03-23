package com.techgeeknext.controller;

import com.techgeeknext.model.Client;

import com.techgeeknext.model.Subscription;
import com.techgeeknext.repository.ClientRepository;
import com.techgeeknext.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @PostMapping("/addClient")
    public ResponseEntity<?> addClient(@Valid @RequestBody Client client) throws ParseException {
        client.setCreated_at(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(clientRepository.save(client));

    }

    @PostMapping("/editClient/{id}")
    public ResponseEntity<?> editClient(@Valid @RequestBody Client client, @PathVariable("id") Long id) throws ParseException {
        Optional<Client> e=clientRepository.findById(id);
        if(e.isPresent())
        {
            Client eq=e.get();

            eq.setFirstName(client.getFirstName());
            eq.setLastName(client.getLastName());
            eq.setNationalId(client.getNationalId());
            eq.setEmail(client.getEmail());
            eq.setNum(client.getNum());

            return ResponseEntity.ok(clientRepository.save(eq));
        }

        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @GetMapping("/getClients")

    public ResponseEntity<Object> getClients()  {

        List<Client> pp=clientRepository.findAll();
        return new ResponseEntity<>(pp, HttpStatus.OK);
    }

    @DeleteMapping("/deleteClient/{id}")

    public ResponseEntity<Object> delClient(@PathVariable("id") Long id)  {

        clientRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/getSubscriptions/{id}")

    public ResponseEntity<Object> getCurrentSubscriptions(@PathVariable("id") long id)  {

        List<Object> current=clientRepository.getCurrentSubscreption(id);
        List<Object> expired=clientRepository.getExpiredSubscreption(id);
        Object o=clientRepository.getLastPayment(id);
        Map<String,Object> elements=new HashMap<>();
        elements.put("current",current);
        elements.put("expired",expired);
        elements.put("lastPayment",o);
        return ResponseEntity.ok(elements);
    }

    @PostMapping("/addSubscription")

    public ResponseEntity<Object> getCurrentSubscriptions(@Valid @RequestBody Subscription s)  {

        subscriptionRepository.save(s);
        return ResponseEntity.ok("done");
    }



}
