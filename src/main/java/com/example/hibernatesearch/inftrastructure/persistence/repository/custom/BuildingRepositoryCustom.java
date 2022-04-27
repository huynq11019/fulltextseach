package com.example.hibernatesearch.inftrastructure.persistence.repository.custom;

import com.example.hibernatesearch.inftrastructure.persistence.entity.Building;
import com.example.hibernatesearch.inftrastructure.persistence.entity.Post;

import java.util.List;

public interface BuildingRepositoryCustom {
    List<Building> search(String keywords);
    List<Building> fuzztSearch(String keywords);

    List<Building> searchCombined(String keywords, int page, int size);
}
