package myProyect.Controllers;

import jakarta.validation.Valid;
import myProyect.Model.Subjet_base;
import myProyect.Services.SubjetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/Subjet")
@Validated
public class SubjetController extends GenericController<Subjet_base,Long>{

    private final SubjetService service;

    public SubjetController(SubjetService service) {
        super(service);
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<?> postObjet(@Valid @RequestBody Subjet_base subjet) {
        Optional<Subjet_base> subjetStatus = service.postObject(subjet);
        if(subjetStatus.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(subjetStatus);  //codigo (201)
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR 404: COURSE OR TEACHER NOT FOUND...");
    }
}
