package de.stella.agora_web.text.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import de.stella.agora_web.text.model.Text;


@Repository
public interface TextRepository extends JpaRepository<Text, Long> {

  
    @SuppressWarnings("null")
    List<Text> findAll();

    @SuppressWarnings("null")
    Optional<Text> findById(Long id);
}
