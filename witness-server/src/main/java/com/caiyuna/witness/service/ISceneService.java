/**
 * 
 */
package com.caiyuna.witness.service;

import com.caiyuna.witness.entity.Scene;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public interface ISceneService {

    void pushSceneDetails(Scene scene, String wsUrl) throws Exception;

    void saveSceneDetails(Scene scene) throws Exception;


}
