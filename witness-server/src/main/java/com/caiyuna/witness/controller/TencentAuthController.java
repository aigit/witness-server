/**
 * 
 */
package com.caiyuna.witness.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caiyuna.witness.service.ITencentAuthService;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@RestController
@RequestMapping("tencentauth")
public class TencentAuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TencentAuthController.class);

    @Autowired
    private ITencentAuthService tencentAuthService;

    @RequestMapping("cossign/get/{isreusable}/{isbinddoc}")
    String getCosAuthSign(@PathVariable boolean isreusable, @PathVariable boolean isbinddoc) {
        LOGGER.info("是否多次有效:{},是否绑定文件：{}", isreusable, isbinddoc);
        String authSign = tencentAuthService.getCosAuthSign(isreusable, isbinddoc);
        return authSign;
    }

}
