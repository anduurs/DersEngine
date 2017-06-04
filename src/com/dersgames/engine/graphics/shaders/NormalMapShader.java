package com.dersgames.engine.graphics.shaders;

import com.dersgames.engine.core.Scene;
import com.dersgames.engine.entities.lights.DirectionalLight;
import com.dersgames.engine.entities.lights.PointLight;
import com.dersgames.engine.entities.lights.SpotLight;
import com.dersgames.engine.maths.Vector2f;
import com.dersgames.engine.maths.Vector3f;

import java.util.List;

/**
 * Created by Anders on 5/28/2017.
 */
public class NormalMapShader extends PhongShader{

    public static final int MAX_POINT_LIGHTS = 4;

    public NormalMapShader() {
        super("entity/normalMapVertexShader", "entity/normalMapFragmentShader");
    }

    @Override
    protected void addUniforms() {
        addUniform("offset");
        addUniform("plane");
        addUniform("modelMatrix");
        addUniform("viewMatrix");
        addUniform("projectionMatrix");

        addUniform("skyColor");
        addUniform("ambientLight");

        addUniform("directionalLightPosition");
        addUniform("directionalLight.light.color");
        addUniform("directionalLight.light.intensity");

        addUniform("renderNormals");
        addUniform("renderTangents");
        addUniform("wireframeMode");

        for(int i = 0; i < MAX_POINT_LIGHTS; i++){
            addUniform("pointLightPositions["+i+"]");
            addUniform("pointLights["+i+"].attenuation");
            addUniform("pointLights["+i+"].range");
            addUniform("pointLights["+i+"].light.color");
            addUniform("pointLights["+i+"].light.intensity");
        }
    }

    @Override
    protected void bindAttributes() {
        super.bindAttributes();
        super.bindAttribute(3, "tangent");
    }

    @Override
    public void loadLightSources(DirectionalLight directionalLight, List<PointLight> pointLights, List<SpotLight> spotLights){
        super.loadVector3f("ambientLight", Scene.getSceneAmbientLight());

        super.loadVector3f("directionalLightPosition", directionalLight.getPosition());
        super.loadVector3f("directionalLight.light.color", directionalLight.getLightColor());
        super.loadFloat("directionalLight.light.intensity", directionalLight.getIntensity());

        for(int i = 0; i < MAX_POINT_LIGHTS; i++){
            if(i < pointLights.size() && pointLights.size() != 0){
                super.loadVector3f("pointLightPositions["+i+"]", pointLights.get(i).getPosition());
                super.loadVector3f("pointLights["+i+"].attenuation", pointLights.get(i).getAttenuation());
                super.loadVector3f("pointLights["+i+"].light.color", pointLights.get(i).getLightColor());
                super.loadFloat("pointLights["+i+"].light.intensity", pointLights.get(i).getIntensity());
                super.loadFloat("pointLights["+i+"].range", pointLights.get(i).getRange());
            }
        }
    }

}
