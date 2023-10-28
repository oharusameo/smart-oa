package com.quxue.template.service;

import com.quxue.template.domain.dto.GetSignStatDTO;
import com.quxue.template.domain.vo.SignStatVo;

public interface SignStatusService {
    SignStatVo getSignStat(GetSignStatDTO signStatDTO);
}
