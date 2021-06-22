package com.eojin.aoi_region_matcher.service;

import com.eojin.aoi_region_matcher.model.AOI;
import com.eojin.aoi_region_matcher.dto.request.PostAoiRequest;
import com.eojin.aoi_region_matcher.dto.response.AoiResponse;
import com.eojin.aoi_region_matcher.dto.response.GetIntersectedAoiResponse;
import com.eojin.aoi_region_matcher.dto.response.PostAoiResponse;
import com.eojin.aoi_region_matcher.repository.AoiRepository;
import com.eojin.aoi_region_matcher.util.GeometryConverter;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AoiService {
    private final AoiRepository aoiRepository;
    private final GeometryConverter geometryConverter;

    public GetIntersectedAoiResponse getAoiIntersectedWithRegion(Integer id){
        List<AoiResponse> aoiResponses = new ArrayList<>();

        List<AOI> aois = aoiRepository.findOverlappedAoiByRegionId(id);
        System.out.print(aois);

        for(AOI aoi: aois){
            aoiResponses.add(
                    new AoiResponse(
                            aoi.getId(),
                            geometryConverter.convertPolygonToCoordinateList(aoi.getArea()),
                            aoi.getName()
                    )
            );
        }

        return new GetIntersectedAoiResponse(aoiResponses);
    }



    public PostAoiResponse createAoi(PostAoiRequest request){
        Polygon polygon = geometryConverter.convertCoordinatesToPolygon(request.getArea());

        AOI aoi = AOI
                .builder()
                .name(request.getName())
                .area(polygon)
                .build();
        aoi = aoiRepository.save(aoi);

        return new PostAoiResponse(aoi.getId());
    }

}
