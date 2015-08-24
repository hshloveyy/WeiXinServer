package com.infolion.xdss3.rate.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.voucher.domain.Voucher;

/**
 */
@Repository
public class RateJdbcDao extends BaseJdbcDao {
    /**
     * 取得最后利率时间
     * 
     * @return
     */
    public String getLastRateDate() {
        return (String)this.getJdbcTemplate().queryForObject("SELECT MAX(enddate) enddate FROM yrate", String.class);
    }

}
