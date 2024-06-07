package com.animeweb.controller.admin;

import com.animeweb.dto.serie.SerieDTO;
import com.animeweb.dto.serie.SerieUpdate;
import com.animeweb.entities.Serie;
import com.animeweb.mapper.SerieMapper;
import com.animeweb.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/series")
public class AdminSerieController {
    @Autowired
    SerieService serieService;
    @GetMapping
    public ResponseEntity<List<SerieDTO>> getAllSeries(){
        return ResponseEntity.ok(serieService.getAllSerie());
    }
    @PostMapping("/add")
    public ResponseEntity<SerieDTO> addSerie(@RequestBody SerieUpdate serieUpdate){
        String descriptions = serieUpdate.getDescriptions();
        boolean isExit = serieService.findByDescription(descriptions);
        if (isExit) throw new RuntimeException("Serie đã tồn tại");
        Serie newSerie = new Serie();
        newSerie.setDescriptions(descriptions);
        serieService.save(newSerie);
        return new ResponseEntity<>(SerieMapper.mapToSerieDto(newSerie),HttpStatus.CREATED);
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<SerieDTO> editSerie(@PathVariable Long id, @RequestBody SerieUpdate serieUpdate){
        Serie updateSerie = serieService.findById(id);
        updateSerie.setDescriptions(serieUpdate.getDescriptions());
        updateSerie.setUpdateAt(new Date());
        serieService.save(updateSerie);
        return new ResponseEntity<>(SerieMapper.mapToSerieDto(updateSerie), HttpStatus.CREATED);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSerie(@PathVariable Long id){
        Serie removeSerie = serieService.findById(id);
        removeSerie.setStatus(false);
        serieService.save(removeSerie);
        return new ResponseEntity<>("Xóa Serie thành công",HttpStatus.CREATED);
    }

}
