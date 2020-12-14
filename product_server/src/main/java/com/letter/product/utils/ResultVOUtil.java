package com.letter.product.utils;

import com.letter.product.vo.ResultVO;

public class ResultVOUtil {
    public static ResultVO seccess(Object o){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(o);
        return resultVO;
    }
}
