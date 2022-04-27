package com.example.hibernatesearch.inftrastructure.persistence.repository;

import com.example.hibernatesearch.inftrastructure.persistence.entity.Building;
import com.example.hibernatesearch.inftrastructure.persistence.repository.custom.BuildingRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, String>, BuildingRepositoryCustom {

}
