package com.techgeeknext.controller;

import com.techgeeknext.model.Admin;
import com.techgeeknext.model.Subscription;
import com.techgeeknext.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @PostMapping("/updateUsername")

    public ResponseEntity<Object> updateUsername(@RequestParam String username)  {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String u = userDetails.getUsername();

        Admin admin=adminRepository.getAdminUsername(u);
        admin.setUsername(username);
        return ResponseEntity.ok(adminRepository.save(admin).getUsername());
    }

    @PostMapping("/updateEmail")

    public ResponseEntity<Object> updateEmail(@RequestParam String email,@RequestParam String oldPass)  {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String u = userDetails.getUsername();

        Admin admin=adminRepository.getAdminUsername(u);
        if(bcryptEncoder.matches(oldPass,admin.getPassword()))
        {
            admin.setEmail(email);
            return ResponseEntity.ok(adminRepository.save(admin).getEmail());
        }
        else
            return new ResponseEntity<>(HttpStatus.valueOf(201));


    }
    @PostMapping("/updatePassword")

    public ResponseEntity<Object> updatePassword(@RequestParam String oldPass,@RequestParam String password,@RequestParam String password_confirmation)  {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String u = userDetails.getUsername();

        Admin admin=adminRepository.getAdminUsername(u);
        if(bcryptEncoder.matches(oldPass,admin.getPassword()))
        {
            admin.setPassword(bcryptEncoder.encode(password));
            adminRepository.save(admin);
            return ResponseEntity.ok("ok");
        }
        else
            return new ResponseEntity<>(HttpStatus.valueOf(201));


    }


    @GetMapping("/username")

    public ResponseEntity<Object> getUser()  {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String u = userDetails.getUsername();

        Admin admin=adminRepository.getAdminUsername(u);

        Map<String,String> data =new HashMap<>();
        data.put("email",admin.getEmail());
        data.put("username",admin.getUsername());

        return ResponseEntity.ok(data);
    }
}
