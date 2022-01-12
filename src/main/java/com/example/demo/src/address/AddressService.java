package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.address.model.PostAddressReq;
import com.example.demo.src.address.model.PostAddressRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.FAIL_DELETE_ADDRESS;


@Service
public class AddressService {

    private final AddressDao addressDao;



    @Autowired
    public AddressService(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public PostAddressRes createAddress(int userId, PostAddressReq postAddressReq) throws BaseException {
        int newAddress = addressDao.createAddress(userId, postAddressReq);
        return new PostAddressRes(newAddress);
    }

    public void deleteAddress(int addressId) throws BaseException {
        try {
            int result = addressDao.deleteAddress(addressId);
            if (result == 0) {
                throw new BaseException(FAIL_DELETE_ADDRESS);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
