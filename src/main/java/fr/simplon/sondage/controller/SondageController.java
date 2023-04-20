package fr.simplon.sondage.controller;

import fr.simplon.sondage.SondageApplication;
import fr.simplon.sondage.dao.SondageRepository;
import fr.simplon.sondage.entity.Sondage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SondageController {
    private final SondageRepository repo;

    public SondageController(SondageRepository son){
        this.repo = son;
    }

    @GetMapping("/sondages")
    public List<Sondage> getSondages() {
        return repo.findAll();
    }

    @PostMapping("/sondages")
    public Sondage addSondage(@RequestBody Sondage newSondage){
        return repo.save(newSondage);
    }

   @GetMapping("/sondages/{id}")
    public Sondage getSondage(@PathVariable long id) {
        return repo.findById(id).orElse(null);
    }

    @DeleteMapping("sondages/{id}")
    public void deleteSondage(@PathVariable long id) {
        repo.deleteById(id);
    }

    @PutMapping("/sondages/{id}")
    public Sondage updateSondage(@RequestBody Sondage newSondage, @PathVariable long id) {
        return repo.findById(id)
                .map(sondage -> {
                    sondage.setDescription(newSondage.getDescription());
                    sondage.setQuestion(newSondage.getQuestion());
                    sondage.setEnd_date(newSondage.getEnd_date());
                    sondage.setNom_creator(newSondage.getNom_creator());
                    return repo.save(sondage);
                })
                .orElseGet(() -> {
                    newSondage.setId(id);
                    return repo.save(newSondage);
                });
    }

}
