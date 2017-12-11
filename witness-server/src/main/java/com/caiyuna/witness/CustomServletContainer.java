/**
 * 
 */
package com.caiyuna.witness;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.http.HttpStatus;

/**
 * @author Ldl 
 * @since 1.0.0
 */
// @Component
public class CustomServletContainer implements EmbeddedServletContainerCustomizer {

    /**
     * @Author Ldl
     * @Date 2017年11月28日
     * @since 1.0.0
     * @param container
     * @see org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer#customize(org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer)
     */
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(8443);
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));

    }

}
