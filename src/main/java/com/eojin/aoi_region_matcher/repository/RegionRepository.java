package com.eojin.aoi_region_matcher.repository;


import com.eojin.aoi_region_matcher.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    Region getRegionByName(String name);

    Region getRegionById(Integer id);
}

