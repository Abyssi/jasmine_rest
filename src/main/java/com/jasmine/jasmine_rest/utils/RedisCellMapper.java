package com.jasmine.jasmine_rest.utils;

import com.jasmine.jasmine_rest.Models.*;
import com.jasmine.jasmine_rest.service.CellService;
import com.jasmine.jasmine_rest.service.SemaphoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RedisCellMapper {

    @Value("${grid.top.left.latitude}")
    private double topLeftLatitude;

    @Value("${grid.top.left.longitude}")
    private double topLeftLongitude;

    @Value("${grid.bottom.right.latitude}")
    private double bottomRightLatitude;

    @Value("${grid.bottom.right.longitude}")
    private double bottomRightLongitude;

    @Value("${grid.latitude.sections}")
    private Integer latitudeSections;

    @Value("${grid.longitude.sections}")
    private Integer longitudeSections;

    @Autowired
    private SemaphoreService semaphoreService;

    @Autowired
    private CellService cellService;

    public void mapGridInRedis() {
        for (JNCell cell : createCells())
            cellService.save(cell);
    }

    public List<JNCell> createCells() {
        JNCellsContainer container = new JNCellsContainer(new JNCoordinates(topLeftLatitude, topLeftLongitude), new JNCoordinates(bottomRightLatitude, bottomRightLongitude), latitudeSections, longitudeSections);

        List<JNCell> grid = new ArrayList<>();

        for (int x = 0; x < container.getLongitudeSize(); x++)
            for (int y = 0; y < container.getLatitudeSize(); y++)
                grid.add(createCell(container, x, y));

        return grid;
    }

    private JNCell createCell(JNCellsContainer container, int x, int y) {
        JNBoxContainer cellContainer = container.getCellBoxContainer(x, y);
        String cellId = this.getBelongingCellId(cellContainer.getCenterCoordinates(), container);

        Box cellBox = new Box(new Point(cellContainer.getTopLeftCoordinates().longitude, cellContainer.getTopLeftCoordinates().latitude), new Point(cellContainer.getBottomRightCoordinates().longitude, cellContainer.getBottomRightCoordinates().latitude));

        List<JNBaseSemaphore> semaphores = this.semaphoreService.findAllByLocationWithin(cellBox).stream().map(
                semaphoreSensor -> new JNBaseSemaphore(
                        semaphoreSensor.getCrossroads().getId(),
                        semaphoreSensor.getSemaphoreId(),
                        new JNCoordinates(
                                semaphoreSensor.getLocation().getY(),
                                semaphoreSensor.getLocation().getX()
                        )
                )).collect(Collectors.toList());

        return new JNCell(cellId, cellContainer, semaphores);
    }

    public String getBelongingCellId(JNCoordinates coordinates, JNCellsContainer container) {
        Tuple2<Integer, Integer> cellIndices = container.getBelongingCellIndices(coordinates);

        //concatenation of x and y: result should be kind of "6-9"
        return cellIndices._1 + "-" + cellIndices._2;

        //if needs an integer: result should be kind of "12"
        //return String.valueOf(cellIndices._2 * container.getXSize() + cellIndices._1);
    }

    public void mapBelongingCell(JNCoordinates coordinates) {
        JNCellsContainer container = new JNCellsContainer(new JNCoordinates(topLeftLatitude, topLeftLongitude), new JNCoordinates(bottomRightLatitude, bottomRightLongitude), latitudeSections, longitudeSections);
        Tuple2<Integer, Integer> cellIndices = container.getBelongingCellIndices(coordinates);
        JNCell cell = createCell(container, cellIndices._1, cellIndices._2);
        cellService.save(cell);
    }
}
