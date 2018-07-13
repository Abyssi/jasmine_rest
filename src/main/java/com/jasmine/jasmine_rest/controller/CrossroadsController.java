package com.jasmine.jasmine_rest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.jasmine.jasmine_rest.Models.JNCoordinates;
import com.jasmine.jasmine_rest.Models.Mongo.Crossroads;
import com.jasmine.jasmine_rest.Models.Mongo.OutlierCrossroads;
import com.jasmine.jasmine_rest.Models.Mongo.Semaphore;
import com.jasmine.jasmine_rest.exceptions.PageableQueryException;
import com.jasmine.jasmine_rest.response_entity.CommonResponseEntity;
import com.jasmine.jasmine_rest.response_entity.JsonViews;
import com.jasmine.jasmine_rest.response_entity.PageResponseEntityBuilder;
import com.jasmine.jasmine_rest.response_entity.ResponseEntityBuilder;
import com.jasmine.jasmine_rest.service.CrossroadsService;
import com.jasmine.jasmine_rest.service.OutlierCrossroadsService;
import com.jasmine.jasmine_rest.service.SemaphoreService;
import com.jasmine.jasmine_rest.utils.RedisCellMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/crossroads/")
public class CrossroadsController {

    @Autowired
    private CrossroadsService crossroadsService;

    @Autowired
    private OutlierCrossroadsService outlierCrossroadsService;

    @Autowired
    private SemaphoreService semaphoreService;

    @Autowired
    private RedisCellMapper redisCellMapper;

    @JsonView(JsonViews.DetailedCrossroads.class)
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity create(@RequestBody Crossroads crossroads) {
        Crossroads newCrossroads = crossroadsService.save(new Crossroads());
        for (Semaphore semaphore : crossroads.getSemaphores())
            newCrossroads.getSemaphores().add(semaphoreService.save(semaphore));
        crossroadsService.save(newCrossroads);

        redisCellMapper.mapBelongingCell(new JNCoordinates(crossroads.getSemaphores().get(0).getLocation().getY(), crossroads.getSemaphores().get(0).getLocation().getX()));

        return CommonResponseEntity.OkResponseEntity("CREATED");
    }

    @JsonView(JsonViews.DetailedCrossroads.class)
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable String id) {
        Optional<Crossroads> crossroads = crossroadsService.findById(id);

        if (!crossroads.isPresent())
            return CommonResponseEntity.NotFoundResponseEntity("CROSSROADS_NOT_FOUND");

        return new ResponseEntityBuilder<>(crossroads.get()).setStatus(HttpStatus.OK).build();
    }

    @JsonView(JsonViews.DetailedCrossroads.class)
    @RequestMapping(path = "{id}", method = RequestMethod.POST)
    public ResponseEntity update(@PathVariable String id, @RequestBody Crossroads crossroads) {
        crossroads.setId(id);
        crossroadsService.save(crossroads);

        redisCellMapper.mapBelongingCell(new JNCoordinates(crossroads.getSemaphores().get(0).getLocation().getY(), crossroads.getSemaphores().get(0).getLocation().getX()));

        return CommonResponseEntity.OkResponseEntity("UPDATED");
    }

    @JsonView(JsonViews.DetailedCrossroads.class)
    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) {
        Optional<Crossroads> crossroads = crossroadsService.findById(id);
        if (crossroads.isPresent()) {
            JNCoordinates coordinates = new JNCoordinates(crossroads.get().getSemaphores().get(0).getLocation().getY(), crossroads.get().getSemaphores().get(0).getLocation().getX());
            crossroadsService.deleteById(id);
            redisCellMapper.mapBelongingCell(coordinates);

            return CommonResponseEntity.OkResponseEntity("DELETED");
        } else
            return CommonResponseEntity.NotFoundResponseEntity("CROSSROADS_NOT_FOUND");
    }

    @JsonView(JsonViews.DetailedCrossroads.class)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getPaginated(@RequestParam(name = "page") Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        try {
            Page<Crossroads> retrievedPage = crossroadsService.findAll(page, pageSize);
            return new PageResponseEntityBuilder(retrievedPage).setStatus(HttpStatus.OK).build();
        } catch (PageableQueryException e) {
            return CommonResponseEntity.BadRequestResponseEntity(e.getMessage());
        }
    }

    @JsonView(JsonViews.DetailedCrossroads.class)
    @RequestMapping(path = "outliers/{millis}", method = RequestMethod.GET)
    public ResponseEntity getOutliers(@PathVariable Integer millis) {
        try {
            List<OutlierCrossroads> outlierCrossroadsList = outlierCrossroadsService.findAllByTimeWindowMilliseconds(millis);
            return new ResponseEntityBuilder<>(outlierCrossroadsList).setStatus(HttpStatus.OK).build();
        } catch (Exception e) {
            return CommonResponseEntity.BadRequestResponseEntity(e.getMessage());
        }
    }

}
