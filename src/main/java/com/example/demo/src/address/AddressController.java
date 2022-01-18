package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.address.model.GetAddressRes;
import com.example.demo.src.address.model.PostAddressReq;
import com.example.demo.src.address.model.PostAddressRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/address")
public class AddressController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AddressProvider addressProvider;
    @Autowired
    private final AddressService addressService;
    @Autowired
    private final JwtService jwtService;;


    public AddressController(AddressProvider addressProvider, AddressService addressService, JwtService jwtService) {
        this.addressProvider = addressProvider;
        this.addressService = addressService;
        this.jwtService = jwtService;
    }
    /**
     *  고객 주소 조회 API
     *  [GET] /address/:userId
     *  @return BaseResponse<List<GetAddressRes>>
     */
    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<List<GetAddressRes>> getCustomerAddress(@PathVariable("userId") int userId){
        try {
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetAddressRes> getAddressRes = addressProvider.getAddress(userId);
            return new BaseResponse<>(getAddressRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 주소 생성 /address/:userId
     * @param postAddressReq
     * @return
     */
    @ResponseBody
    @PostMapping("/{userId}")
    public BaseResponse<PostAddressRes> createAddress(@PathVariable("userId") int userId, @RequestBody PostAddressReq postAddressReq) {
        try {
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostAddressRes postAddressRes = addressService.createAddress(userIdByJwt,postAddressReq);
            return new BaseResponse<>(postAddressRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * * 주소 삭제 /address/:addressId/:userId/delete
     * @param addressId
     * @return BaseResponse<String>
     */

    @ResponseBody
    @PatchMapping("/{addressId}/{userId}/delete")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> deleteAddress(@PathVariable("addressId") int addressId, @PathVariable("userId") int userId) {
        try {
            int userIdByJwt = jwtService.getUserId();
            if (userId != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            addressService.deleteAddress(addressId);
            String result = "주소 삭제 성공";
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }


    }


}
