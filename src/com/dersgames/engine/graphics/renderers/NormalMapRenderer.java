package com.dersgames.engine.graphics.renderers;

import com.dersgames.engine.components.Camera;
import com.dersgames.engine.components.StaticMesh;
import com.dersgames.engine.core.Scene;
import com.dersgames.engine.graphics.RenderEngine;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.TexturedModel;
import com.dersgames.engine.graphics.shaders.EntityShader;
import com.dersgames.engine.graphics.shaders.NormalMapShader;
import com.dersgames.engine.graphics.shaders.Shader;
import com.dersgames.engine.graphics.textures.lightingmaps.NormalMap;
import com.dersgames.engine.maths.Vector2f;
import com.dersgames.engine.maths.Vector4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by Anders on 5/28/2017.
 */
public class NormalMapRenderer extends EntityRenderer {

    public NormalMapRenderer(){
        m_Shader = new NormalMapShader();
        m_Renderables = new HashMap<>();
        m_Shader.enable();
        m_Shader.loadClippingPlane(new Vector4f(0.0f,-1.0f,0.0f,15.0f));
        m_Shader.disable();
    }

    @Override
    protected void loadTexturedModelData(TexturedModel texturedModel){
        glBindVertexArray(texturedModel.getModel().getVaoID());

        Material material = texturedModel.getMaterial();

        if(material.hasTransparency())
            RenderEngine.disableFaceCulling();

        material.updateUniforms();

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texturedModel.getMaterial().getTextureID());

        if(material.isUsingSpecularMap()){
            glActiveTexture(GL_TEXTURE1);
            glBindTexture(GL_TEXTURE_2D, texturedModel.getMaterial().getSpecularMapTextureID());
        }

        glActiveTexture(GL_TEXTURE2);
        glBindTexture(GL_TEXTURE_2D, texturedModel.getMaterial().getNormalMapTextureID());
    }
}
