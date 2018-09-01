package com.dersgames.engine.graphics.renderers;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.HashMap;

import com.dersgames.engine.graphics.GLRenderUtils;
import com.dersgames.engine.graphics.materials.Material;
import com.dersgames.engine.graphics.models.Model;
import com.dersgames.engine.graphics.shaders.PhongShader;
import com.dersgames.engine.graphics.shaders.ShaderManager;
import com.dersgames.engine.math.Vector4f;
import com.dersgames.engine.util.Pair;

/**
 * Created by Anders on 5/28/2017.
 */
public class NormalMapRenderer extends EntityRenderer {

    public NormalMapRenderer(){
        m_Shader = (PhongShader) ShaderManager.getInstance().getShader(ShaderManager.DEFAULT_NORMALMAP_SHADER);
        m_Renderables = new HashMap<>();
        m_Shader.enable();
        m_Shader.loadClippingPlane(new Vector4f(0.0f,-1.0f,0.0f,15.0f));
        m_Shader.disable();
    }

    @Override
    protected void bind(Pair<Model, Material> staticMeshData){
    	glBindVertexArray(staticMeshData.first.getVaoID());
        Material material = staticMeshData.second;

        if(material.hasTransparency())
        	GLRenderUtils.disableFaceCulling();

        material.updateUniforms();

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, material.getTextureID());

        if(material.isUsingSpecularMap()){
            glActiveTexture(GL_TEXTURE1);
            glBindTexture(GL_TEXTURE_2D, material.getSpecularMapTextureID());
        }

        glActiveTexture(GL_TEXTURE2);
        glBindTexture(GL_TEXTURE_2D, material.getNormalMapTextureID());
    }
}
