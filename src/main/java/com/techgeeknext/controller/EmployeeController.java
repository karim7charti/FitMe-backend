package com.techgeeknext.controller;
import com.techgeeknext.model.Employe;
import com.techgeeknext.model.Equipment;
import com.techgeeknext.model.Profession;
import com.techgeeknext.repository.EmployeRepository;
import com.techgeeknext.repository.ProfessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeController {
    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private ProfessionRepository professionRepository;
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String getEmployees() {
        return "Welcome!";
    }


    @PostMapping("/addEmploye")
    public ResponseEntity<?> addEmp(@Valid @RequestBody Employe emp) throws ParseException {

        Optional<Profession> p=professionRepository.findById(emp.getId_profe());

        if(p.isPresent())
        {
            Profession profession=p.get();
            emp.setProf(profession);
            emp.setCreated_at(new Timestamp(System.currentTimeMillis()));
            return ResponseEntity.ok(employeRepository.save(emp));
        }
        else
            return (ResponseEntity<?>) ResponseEntity.status(404);

    }

    @GetMapping("/professions")

    public ResponseEntity<Object> getProf()  {

       List<Profession> pp=professionRepository.findAll();
       return new ResponseEntity<>(pp, HttpStatus.OK);


    }

    @GetMapping("/getEmployees")

    public ResponseEntity<Object> getemp()  {

        List<Employe> pp=employeRepository.findAll();
        return new ResponseEntity<>(pp, HttpStatus.OK);
    }

    @DeleteMapping("/deleteEmp/{id}")

    public ResponseEntity<Object> delemp(@PathVariable("id") Long id)  {

        employeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/editEmploye/{id}")

    public ResponseEntity<Object> editemp(@Valid @RequestBody Employe employe,@PathVariable("id") Long id)  {
        Optional<Employe> e=employeRepository.findById(id);
        if(e.isPresent())
        {
            Employe eq=e.get();

            eq.setFirstName(employe.getFirstName());
            eq.setLastName(employe.getLastName());
            eq.setId_profe(employe.getId_profe());
            eq.setMonthly_Amount(employe.getMonthly_Amount());
            eq.setNationalId(employe.getNationalId());
            eq.setPaymentDay(employe.getPaymentDay());
            return ResponseEntity.ok(employeRepository.save(eq));
        }

        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}