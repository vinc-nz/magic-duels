package utils;

import java.nio.FloatBuffer;

import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.TexCoords;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.BlendState.DestinationFunction;
import com.jme.scene.state.BlendState.SourceFunction;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jme.util.geom.BufferUtils;
 
public class ProgressBar {
 
    private Node hudNode;
 
    private Quad gauge, hudQuad;
 
    private int textureWidth;
 
    private int textureHeight;
 
    private int minimum = 0, maximum = 100;
 
    private int widthScale = 0, heightScale = 0;
 
    private int xpos = 0, ypos = 0;
 
    final private int width = 34, height = 10;
 
    public ProgressBar(DisplaySystem display, String imagePath) {
 
        hudNode = new Node("hudNode");
        hudQuad = new Quad("hud", 34f, 10f);
 
        hudNode.getLocalTranslation().x = xpos;
        hudNode.getLocalTranslation().y = ypos;
 
        gauge = new Quad("gauge", 32f, 8f);
        gauge.setRenderQueueMode(Renderer.QUEUE_ORTHO);
        LightState ls = display.getRenderer().createLightState();
        ls.setEnabled(false);
        hudNode.setRenderState(ls);
        hudNode.updateRenderState();
        hudNode.attachChild(hudQuad);
        hudNode.attachChild(gauge);
        
 
        TextureState ts = display.getRenderer().createTextureState();
        ts.setTexture(TextureManager.loadTexture(getClass().getClassLoader()
                .getResource(imagePath), Texture.MinificationFilter.Trilinear,
                Texture.MagnificationFilter.Bilinear, 0.0f, true));
        textureWidth = ts.getTexture().getImage().getWidth();
        textureHeight = ts.getTexture().getImage().getHeight();
        ts.setEnabled(true);
 
        FloatBuffer texCoords = BufferUtils.createVector2Buffer(4);
        texCoords.put(getUForPixel(0)).put(getVForPixel(0));
        texCoords.put(getUForPixel(0)).put(getVForPixel(10));
        texCoords.put(getUForPixel(34)).put(getVForPixel(10));
        texCoords.put(getUForPixel(34)).put(getVForPixel(0));
        TexCoords c = new TexCoords(texCoords);
        hudQuad.setTextureCoords(c);
        hudNode.setRenderState(ts);
 
        BlendState as = display.getRenderer().createBlendState();
        as.setBlendEnabled(true);
        as.setSourceFunction(SourceFunction.SourceAlpha);
        as.setDestinationFunction(DestinationFunction.OneMinusSourceAlpha);
        as.setTestEnabled(false);
        as.setEnabled(true);
        hudNode.setRenderState(as);
 
    }
 
    public int getWidth() {
        return width * widthScale;
    }
 
    public int getHeight() {
        return height * heightScale;
    }
 
    private float getUForPixel(float xPixel) {
        return (float) xPixel / textureWidth;
    }
 
    private float getVForPixel(float yPixel) {
        return 1f - (float) yPixel / textureHeight;
    }
 
    public Node getNode() {
        return hudNode;
    }
 
    public void setPosition(int xpos, int ypos) {
        hudNode.getLocalTranslation().x = xpos;
        hudNode.getLocalTranslation().y = ypos;
    }
 
    public void setValue(int value) {
 
        float range = maximum - minimum;
        float adjustedRange = value - minimum;
        float percent = adjustedRange / range;
        float newX = (32 * percent);
 
        FloatBuffer texCoords3 = BufferUtils.createVector2Buffer(4);
 
        texCoords3.put(getUForPixel(32 - newX)).put(getVForPixel(56));
        texCoords3.put(getUForPixel(32 - newX)).put(getVForPixel(64));
        texCoords3.put(getUForPixel(64 - newX)).put(getVForPixel(64));
        texCoords3.put(getUForPixel(64 - newX)).put(getVForPixel(56));
        //gauge.setTextureBuffer(0, texCoords3);
        gauge.setTextureCoords(new TexCoords(texCoords3));
 
    }
 
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }
 
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }
 
    public void setScale(int width, int height) {
        widthScale = width;
        heightScale = height;
        hudNode.setLocalScale(new Vector3f(widthScale, heightScale, 1));
    }
 
 
    public int getMaximum() {
        return maximum;
    }
 
    public int getMinimum() {
        return minimum;
    }
}