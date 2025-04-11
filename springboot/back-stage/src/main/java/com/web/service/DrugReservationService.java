package com.web.service;

import com.web.domain.Req.DrugReservationReq;
import com.web.domain.Resp.DrugReservationResp;
import com.web.utils.PageUtils;
import com.web.domain.DrugReservation;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Date;

/**
* <p>
* 药品预约 服务类
* </p>
*
* @author admin admin@126.com
*
*/
public interface DrugReservationService extends IService<DrugReservation> {

    PageUtils selectPage(DrugReservationReq req);

    DrugReservationResp info(Long id);
}
