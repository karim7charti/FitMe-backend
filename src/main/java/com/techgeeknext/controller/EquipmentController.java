package com.techgeeknext.controller;

import com.techgeeknext.model.Equipment;
import com.techgeeknext.repository.EquipmentRepository;
import com.techgeeknext.repository.FileSystemRepository;
import com.techgeeknext.service.FileLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.Validator;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class EquipmentController {
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private FileLocationService fileLocationService;
    private Map<String,Object> vaidate(MultipartFile image)
    {
        Map<String,Object> errors1=new HashMap<>();
        Map<String,String> err=new HashMap<>();
        if(image==null)
        {
            err.put("img","the image is required");
            errors1.put("status",422);
            errors1.put("errors",err);
            return errors1;
        }
        else if(!image.getContentType().toString().toLowerCase().startsWith("image"))
        {
            err.put("img","the file should be an image");
            errors1.put("status",422);
            errors1.put("errors",err);
            return errors1;
        }
        return errors1;

    }
    @PostMapping(value = "/image",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> uploadImage( @RequestParam("img") MultipartFile image,
                                               @RequestParam String name,
                                               @RequestParam String price,
                                               @RequestParam String description,
                                               @RequestParam String quantity) throws Exception {
        Map<String,Object> errors1=vaidate(image);
        if(!errors1.isEmpty())
        {
            return new ResponseEntity<>(errors1,HttpStatus.OK);
        }
        else {
            Equipment e=new Equipment();
            e.setQuantity(Integer.parseInt(quantity));
            e.setDescription(description);
            e.setCreated_at(new Timestamp(System.currentTimeMillis()));
            e.setPrice(Double.parseDouble(price));
            e.setName(name);
            String s=fileLocationService.save(image.getBytes(), image.getOriginalFilename());
            e.setImage(s);

            return ResponseEntity.ok(equipmentRepository.save(e));
        }


    }
    @PostMapping(value = "/editImage/{id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> editImage( @RequestParam("img") MultipartFile image,
                                               @RequestParam String name,
                                               @RequestParam String price,
                                               @RequestParam String description,
                                               @RequestParam String quantity,@PathVariable("id") Long id) throws Exception {
        Map<String,Object> errors1=vaidate(image);
        if(!errors1.isEmpty())
        {
            return new ResponseEntity<>(errors1,HttpStatus.OK);
        }
        else {
            Optional<Equipment> equipment=equipmentRepository.findById(id);
            if(equipment.isPresent())
            {
                Equipment e=equipment.get();
                e.setQuantity(Integer.parseInt(quantity));
                e.setDescription(description);
                e.setCreated_at(new Timestamp(System.currentTimeMillis()));
                e.setPrice(Double.parseDouble(price));
                e.setName(name);
                fileLocationService.deleteFile(e.getImage());
                String s=fileLocationService.save(image.getBytes(), image.getOriginalFilename());
                e.setImage(s);
                return ResponseEntity.ok(equipmentRepository.save(e));
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }


    }
    @GetMapping(value = "/getImage/{location}", produces = MediaType.IMAGE_JPEG_VALUE)
    FileSystemResource downloadImage(@PathVariable("location") String location) throws Exception {
        String RESOURCES_DIR = FileSystemRepository.class.getResource("/")
                .getPath().substring(1);

        String path=RESOURCES_DIR+"/images/"+location;
        return fileLocationService.getFileSystemRepository().findInFileSystem(path);
    }

    @PostMapping("/addEquipment")
    public ResponseEntity<?> addEquipment(@Valid @RequestBody Equipment equipment)
    {
        return ResponseEntity.ok("good");
    }

    @DeleteMapping("/deleteEquipment/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable("id") long id) throws IOException {
        Optional<Equipment> e=equipmentRepository.findById(id);
        if(e.isPresent())
        {
            if(fileLocationService.deleteFile(e.get().getImage()))
            {
                equipmentRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.valueOf(200));
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        }

        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/getEquipments")

    public ResponseEntity<?> getEquipments()
    {
        List<Equipment>e=equipmentRepository.findAll();
        return ResponseEntity.ok(e);

    }

}
