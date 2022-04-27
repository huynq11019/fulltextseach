package com.example.hibernatesearch.presentation.web;

import com.example.hibernatesearch.inftrastructure.persistence.entity.Building;
import com.example.hibernatesearch.inftrastructure.persistence.repository.BuildingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/building")
public class BuildingResource {
    private final BuildingRepository buildingRepository;

    public BuildingResource(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @GetMapping("/")
    public String search(@RequestParam(value = "search", required = false) String q, Model model) {
        List<Building> searchResults = null;
        try {
            if (StringUtils.hasLength(q)) {
                searchResults = buildingRepository.search(q);
            }
            log.info("Search results: {}", searchResults);
        } catch (Exception ex) {
            // Nothing
            ex.printStackTrace();
        }
        model.addAttribute("search", searchResults);
        model.addAttribute("searchFuzzy", this.fuzzySeach(q));
        return "building";

    }

    @GetMapping("/fuzzySearch")
    @ResponseBody

    public List<Building> fuzzySeach(@RequestParam(value = "search", required = false) String q) {
        List<Building> searchResults = new ArrayList<>();
        try {
            searchResults = buildingRepository.fuzztSearch(q);
            log.info("Search results: {}", searchResults);
            return searchResults;
        } catch (Exception ex) {
            // Nothing
            ex.printStackTrace();
            return searchResults;
        }
    }

    @PostMapping("insert")
    @ResponseBody
    public void insertBuilding(@RequestBody List<Building> buildings) {
        buildingRepository.saveAll(buildings);
    }

}
