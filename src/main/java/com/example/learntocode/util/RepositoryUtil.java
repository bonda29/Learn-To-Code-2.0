package com.example.learntocode.util;

import com.example.learntocode.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class RepositoryUtil {

    public static <T, ID> T findById(JpaRepository<T, ID> repository, ID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found with id: " + id));
    }


}