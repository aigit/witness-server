/**
 * 
 */
package com.caiyuna.witness;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

/**
 * @author Ldl 
 * @since 1.0.0
 */
// @Configuration
public class CustomTomcatEmbeddedServletContainerFactory extends TomcatEmbeddedServletContainerFactory {

    /**
     * @Author Ldl
     * @Date 2017年11月28日
     * @since 1.0.0
     * @param context
     * @see org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory#postProcessContext(org.apache.catalina.Context)
     */
    @Override
    protected void postProcessContext(Context context) {
        SecurityConstraint constraint = new SecurityConstraint();
        constraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        constraint.addCollection(collection);
        context.addConstraint(constraint);
    }

}
