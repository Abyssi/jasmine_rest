package com.jasmine.jasmine_rest.controller;

import com.jasmine.jasmine_rest.Models.JNBoxContainer;
import com.jasmine.jasmine_rest.Models.JNCoordinates;
import com.jasmine.jasmine_rest.Models.Mongo.Crossroads;
import com.jasmine.jasmine_rest.Models.Mongo.Semaphore;
import com.jasmine.jasmine_rest.response_entity.CommonResponseEntity;
import com.jasmine.jasmine_rest.service.CrossroadsService;
import com.jasmine.jasmine_rest.service.SemaphoreService;
import com.jasmine.jasmine_rest.utils.RedisCellMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/v1/generate/")
public class GenerationController {

    @Autowired
    private CrossroadsService crossroadsService;

    @Autowired
    private SemaphoreService semaphoreService;

    @Value("${grid.top.left.latitude}")
    private double topLeftLatitude;

    @Value("${grid.top.left.longitude}")
    private double topLeftLongitude;

    @Value("${grid.bottom.right.latitude}")
    private double bottomRightLatitude;

    @Value("${grid.bottom.right.longitude}")
    private double bottomRightLongitude;

    @Autowired
    private RedisCellMapper redisCellMapper;

    @RequestMapping(path = "{size}/{delete}", method = RequestMethod.PUT)
    public ResponseEntity get(@PathVariable @NotNull Integer size, @PathVariable Boolean delete) {
        try {
            if (delete) crossroadsService.deleteAll();
            JNBoxContainer container = new JNBoxContainer(new JNCoordinates(topLeftLatitude, topLeftLongitude), new JNCoordinates(bottomRightLatitude, bottomRightLongitude));
            for (int i = 0; i < size; i++) {
                Crossroads crossroads = crossroadsService.save(new Crossroads());
                JNCoordinates randomCoordinates = container.randomCoordinates();
                GeoJsonPoint point = new GeoJsonPoint(randomCoordinates.longitude, randomCoordinates.latitude);
                GeoJsonPoint[] shifts = {
                        new GeoJsonPoint(-0.0002, 0.0),
                        new GeoJsonPoint(0.0, 0.0002),
                        new GeoJsonPoint(0.0002, 0.0),
                        new GeoJsonPoint(0.0, -0.0002)
                };
                for (int j = 0; j < 4; j++) {
                    Semaphore semaphore = new Semaphore(new GeoJsonPoint(point.getX() + shifts[j].getX(), point.getY() + shifts[j].getY()), crossroads, String.valueOf(j));
                    semaphore = semaphoreService.save(semaphore);
                    crossroads.getSemaphores().add(semaphore);
                }
                crossroadsService.save(crossroads);
            }
        } catch (Exception e) {
            return CommonResponseEntity.UnprocessableEntityResponseEntity("ERROR");
        }

        redisCellMapper.mapGridInRedis();

        return CommonResponseEntity.OkResponseEntity("DONE");
    }

}

