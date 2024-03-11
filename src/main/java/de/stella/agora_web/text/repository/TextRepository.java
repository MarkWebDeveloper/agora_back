package de.stella.agora_web.text.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import de.stella.agora_web.text.model.Text;


@Repository
public interface TextRepository extends JpaRepository<Text, Long> {

  

   
}
