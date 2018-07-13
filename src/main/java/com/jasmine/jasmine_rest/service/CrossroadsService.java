package com.jasmine.jasmine_rest.service;

import com.jasmine.jasmine_rest.Models.Mongo.Crossroads;
import com.jasmine.jasmine_rest.Models.Mongo.Semaphore;
import com.jasmine.jasmine_rest.exceptions.PageableQueryException;
import com.jasmine.jasmine_rest.repository.CrossroadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class CrossroadsService {

    @Autowired
    private CrossroadsRepository crossroadsRepository;

    public boolean existsById(String id) {
        return this.crossroadsRepository.existsById(id);
    }

    @Transactional
    public Crossroads save(Crossroads crossroads) {
        for (int i = 0; i < crossroads.getSemaphores().size(); i++) {
            Semaphore semaphore = crossroads.getSemaphores().get(i);
            semaphore.setCrossroads(crossroads);
            semaphore.setSemaphoreId(String.valueOf(i));
        }
        return crossroadsRepository.save(crossroads);
    }

    @Transactional
    public boolean deleteById(String id) {
        if (crossroadsRepository.existsById(id)) {
            crossroadsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public void deleteAll() {
        crossroadsRepository.deleteAll();
    }

    @Transactional
    public List<Crossroads> findAll() {
        return crossroadsRepository.findAll();
    }

    @Transactional
    public Optional<Crossroads> findById(String id) {
        return crossroadsRepository.findById(id);
    }

    @Transactional
    public Page<Crossroads> findAll(@NotNull Integer page, @Nullable Integer pageSize) throws PageableQueryException {
        Page<Crossroads> retrievedPage = this.crossroadsRepository.findAll(PageRequest.of(page, pageSize * (page + 1)));

        if (page != 0 && page > retrievedPage.getTotalPages() - 1)
            throw new PageableQueryException("Page number higher than the maximum");

        return retrievedPage;
    }

}
