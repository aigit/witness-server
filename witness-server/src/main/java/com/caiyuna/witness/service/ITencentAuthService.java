/**
 * 
 */
package com.caiyuna.witness.service;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public interface ITencentAuthService {

    String getCosAuthSign(boolean isreusable, boolean isbinddoc);

}
