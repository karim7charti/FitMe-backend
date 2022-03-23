package com.techgeeknext.controller;

import com.techgeeknext.model.Client;
import com.techgeeknext.repository.ClientRepository;
import com.techgeeknext.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DashBoardController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @GetMapping("/getDashCardData")
    public ResponseEntity<?> getDashCardData() throws ParseException {
        long countClients=clientRepository.count();
        long inactiveClients=clientRepository.getInactiveClients();

        long monthIncomes=clientRepository.getMonthIncomes();

        Long totalSpend=equipmentRepository.getTotalSpend();
        if(totalSpend==null)
            totalSpend=(long) 0;

        List<List<Double>> incomes=clientRepository.getIncomesPerMonth();
      List<List<Integer>> clients=clientRepository.getNumberClientPerMonth();
        List<List<Integer>> equipmentsPerMonth=equipmentRepository.getEquipmentByMonth();
          int [] arr=new int[12];
        double [] arr1=new double[12];
        int [] arr2=new int[12];
        for(int i=0;i<12;i++)
        {
            arr[i]=0;
            arr1[i]=0;
            arr2[i]=0;
        }

        for(int j=0;j<equipmentsPerMonth.size();j++)
        {

            int last=equipmentsPerMonth.get(j).get(1);
            int first=equipmentsPerMonth.get(j).get(0);
            arr2[last-1]=first;
        }

        for(int j=0;j<clients.size();j++)
        {

            int last=clients.get(j).get(1);
            int first=clients.get(j).get(0);
            arr[last-1]=first;
        }

        for(int j=0;j<incomes.size();j++)
        {

            int last=(int) Math.round(incomes.get(j).get(1));
            double first=incomes.get(j).get(0);
            arr1[last-1]=(first/100);
        }


        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("countClients",countClients);
        objectMap.put("inactiveClients",inactiveClients);
        objectMap.put("monthIncomes",monthIncomes);
        objectMap.put("totalSpend",totalSpend);
       objectMap.put("clients",arr);
        objectMap.put("incomesPermonth",arr1);
        objectMap.put("equipmentPerMonth",arr2);


        return ResponseEntity.ok(objectMap);

    }
    @GetMapping("/getRecentSubscriptions")
    public ResponseEntity<?> getRecentSubscriptions(){
        List<Object> e=equipmentRepository.getRecentSUbcription(PageRequest.of(0,4));

        return ResponseEntity.ok(e);
    }

    @GetMapping("/getSubscriptionsByDates")
    public ResponseEntity<?> getSubscriptionsByDates(){
        List<Object> e=equipmentRepository.getSubscriptionsByDates();

        return ResponseEntity.ok(e);
    }

}
