package com.jasmine.jasmine_rest.service;

import com.jasmine.jasmine_rest.Models.JNLightBulb;
import com.jasmine.jasmine_rest.Models.JNLightBulbColor;
import com.jasmine.jasmine_rest.Models.JNLightBulbStatus;
import com.jasmine.jasmine_rest.Models.Mongo.Semaphore;
import com.jasmine.jasmine_rest.exceptions.PageableQueryException;
import com.jasmine.jasmine_rest.repository.SemaphoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Box;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SemaphoreService {

    @Autowired
    private SemaphoreRepository semaphoreRepository;

    public boolean existsById(String id) {
        return this.semaphoreRepository.existsById(id);
    }

    @Transactional
    public Semaphore save(Semaphore semaphore) {
        if (semaphore.getLightBulbs().size() == 0)
            semaphore.setLightBulbs(getDefaultLightBulbs());
        if (semaphore.getGreenTime() == 0)
            semaphore.setGreenTime(60);
        return semaphoreRepository.save(semaphore);
    }

    @Transactional
    public boolean deleteById(String id) {
        if (semaphoreRepository.existsById(id)) {
            semaphoreRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public List<Semaphore> findAll() {
        return semaphoreRepository.findAll();
    }

    @Transactional
    public Optional<Semaphore> findById(String id) {
        return semaphoreRepository.findById(id);
    }

    @Transactional
    public Page<Semaphore> findAll(@NotNull Integer page, @Nullable Integer pageSize) throws PageableQueryException {
        Page<Semaphore> retrievedPage = this.semaphoreRepository.findAll(PageRequest.of(page, pageSize * (page + 1)));

        if (page != 0 && page > retrievedPage.getTotalPages() - 1)
            throw new PageableQueryException("Page number higher than the maximum");

        return retrievedPage;
    }

    @Transactional
    public List<Semaphore> findAllByLocationWithin(Box cell) {
        return semaphoreRepository.findAllByLocationWithin(cell);
    }

    @Transactional
    public List<Semaphore> findAllByLightBulbs_Status(JNLightBulbStatus status) {
        return semaphoreRepository.findAllByLightBulbs_Status(status);
    }

    @Transactional
    public Page<Semaphore> findAllByLightBulbs_Status(JNLightBulbStatus status, @NotNull Integer page, @Nullable Integer pageSize) throws PageableQueryException {
        Page<Semaphore> retrievedPage = this.semaphoreRepository.findAllByLightBulbs_Status(status, PageRequest.of(page, pageSize * (page + 1)));

        if (page != 0 && page > retrievedPage.getTotalPages() - 1)
            throw new PageableQueryException("Page number higher than the maximum");

        return retrievedPage;
    }

    @Transactional
    public Semaphore findByCrossroads_IdAndSemaphoreId(String crossroadsId, String semaphoreId) {
        return semaphoreRepository.findByCrossroads_IdAndSemaphoreId(crossroadsId, semaphoreId);
    }

    private List<JNLightBulb> getDefaultLightBulbs() {
        List<JNLightBulb> lightBulbs = new ArrayList<>();
        lightBulbs.add(new JNLightBulb(JNLightBulbColor.GREEN, JNLightBulbStatus.OFF));
        lightBulbs.add(new JNLightBulb(JNLightBulbColor.YELLOW, JNLightBulbStatus.OFF));
        lightBulbs.add(new JNLightBulb(JNLightBulbColor.RED, JNLightBulbStatus.OFF));
        return lightBulbs;
    }

}
