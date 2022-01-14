package com.example.demo.src.magazine;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.magazine.model.GetMagazineDetailRes;
import com.example.demo.src.magazine.model.GetMagazineRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/magazines")
public class MagazineController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MagazineProvider magazineProvider;

    public MagazineController(MagazineProvider magazineProvider) {
        this.magazineProvider = magazineProvider;
    }

    /**
     *  전체 매거진 조회 API
     *  [GET] /magazines
     *  타입별 매거진 조회 API
     *  [GET] /magazines ? type=
     *  @return BaseResponse<List<GetMagazineRes>>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetMagazineRes>> getAllMagazines(@RequestParam(required = false) String type){
        try {
            if(type == null){
                List<GetMagazineRes> getMagazineRes = magazineProvider.getAllMagazines();
                return new BaseResponse<>(getMagazineRes);
            }
                List<GetMagazineRes> getMagazineRes = magazineProvider.getMagazinesByType(type);
                return new BaseResponse<>(getMagazineRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 매거진 상세화면 API
     * [GET] /magazines/:magazineid
     *
     * @return BaseResponse<GetUserRes>
     */
    @ResponseBody
    @GetMapping("/{magazineId}")
    public BaseResponse<GetMagazineDetailRes> getMagazineDetail(@PathVariable("magazineId") int magazine_id) {
        try {
            GetMagazineDetailRes getMagazineDetailRes = magazineProvider.getMagazineDetail(magazine_id);
            return new BaseResponse<>(getMagazineDetailRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
