package com.jasmine.jasmine_rest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.jasmine.jasmine_rest.Models.JNLightBulbStatus;
import com.jasmine.jasmine_rest.Models.Mongo.Semaphore;
import com.jasmine.jasmine_rest.exceptions.PageableQueryException;
import com.jasmine.jasmine_rest.response_entity.CommonResponseEntity;
import com.jasmine.jasmine_rest.response_entity.JsonViews;
import com.jasmine.jasmine_rest.response_entity.PageResponseEntityBuilder;
import com.jasmine.jasmine_rest.response_entity.ResponseEntityBuilder;
import com.jasmine.jasmine_rest.service.SemaphoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/semaphores/")
public class SemaphoreController {

    @Autowired
    private SemaphoreService semaphoreService;

    @JsonView(JsonViews.DetailedSemaphore.class)
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity create(@RequestBody Semaphore semaphore) {
        semaphoreService.save(semaphore);

        return CommonResponseEntity.OkResponseEntity("CREATED");
    }

    @JsonView(JsonViews.DetailedSemaphore.class)
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable String id) {
        Optional<Semaphore> semaphore = semaphoreService.findById(id);

        if (!semaphore.isPresent())
            return CommonResponseEntity.NotFoundResponseEntity("SEMAPHORE_NOT_FOUND");

        return new ResponseEntityBuilder<>(semaphore.get()).setStatus(HttpStatus.OK).build();
    }

    @JsonView(JsonViews.DetailedSemaphore.class)
    @RequestMapping(path = "{id}", method = RequestMethod.POST)
    public ResponseEntity update(@PathVariable String id, @RequestBody Semaphore semaphore) {
        semaphore.setId(id);
        semaphoreService.save(semaphore);

        return CommonResponseEntity.OkResponseEntity("UPDATED");
    }

    @JsonView(JsonViews.DetailedSemaphore.class)
    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) {
        if (semaphoreService.deleteById(id))
            return CommonResponseEntity.OkResponseEntity("DELETED");
        else
            return CommonResponseEntity.NotFoundResponseEntity("SEMAPHORE_NOT_FOUND");
    }

    @JsonView(JsonViews.DetailedSemaphore.class)
    @RequestMapping(path = "restore/{id}", method = RequestMethod.GET)
    public ResponseEntity restore(@PathVariable String id) {
        Optional<Semaphore> semaphore = semaphoreService.findById(id);
        semaphore.get().setLightBulbsStatus(JNLightBulbStatus.OFF);

        semaphoreService.save(semaphore.get());

        return CommonResponseEntity.OkResponseEntity("UPDATED");
    }

    @JsonView(JsonViews.DetailedSemaphore.class)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getPaginated(@RequestParam(name = "page") Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        try {
            Page<Semaphore> retrievedPage = semaphoreService.findAll(page, pageSize);
            return new PageResponseEntityBuilder(retrievedPage).setStatus(HttpStatus.OK).build();
        } catch (PageableQueryException e) {
            return CommonResponseEntity.BadRequestResponseEntity(e.getMessage());
        }
    }

    @JsonView(JsonViews.DetailedSemaphore.class)
    @RequestMapping(path = "damaged", method = RequestMethod.GET)
    public ResponseEntity getDamagedPaginated(@RequestParam(name = "page") Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        try {
            Page<Semaphore> retrievedPage = semaphoreService.findAllByLightBulbs_Status(JNLightBulbStatus.DAMAGED, page, pageSize);
            return new PageResponseEntityBuilder(retrievedPage).setStatus(HttpStatus.OK).build();
        } catch (PageableQueryException e) {
            return CommonResponseEntity.BadRequestResponseEntity(e.getMessage());
        }
    }

}
