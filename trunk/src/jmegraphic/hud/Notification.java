package jmegraphic.hud;

import java.io.IOException;
import java.net.URL;

import com.jme.renderer.ColorRGBA;
import com.jme.util.Timer;
import com.jmex.angelfont.BitmapFont;
import com.jmex.angelfont.BitmapFontLoader;
import com.jmex.angelfont.BitmapText;


/*
 * TESTO DA MOSTRARE A VIDEO
 */
public class Notification extends HudObject {
	BitmapText graphicText;
	String text;
	float expireTime;

	public Notification(String text) {
		super(text);
		this.text = text;
        this.expireTime = -1;
	}
	
	@Override
	public void loadModel() {
		URL fntFile = this.getClass().getClassLoader().getResource("data/font/Dalelands.fnt");
		URL texFile = this.getClass().getClassLoader().getResource("data/font/Dalelands.tga");
		BitmapFont fnt;
		try {
			fnt = BitmapFontLoader.load(fntFile, texFile);
		} catch (IOException e) {
			fnt = BitmapFontLoader.loadDefaultFont();
		}

        graphicText = new BitmapText(fnt, false);
//      Rectangle box = new Rectangle(10, -10, display.getWidth() - 20,
//                        display.getHeight() - 20);
//      txt.setBox(box);
        graphicText.setSize(32);
        graphicText.setDefaultColor(ColorRGBA.white.clone());
        graphicText.setText(text);
        graphicText.update();
        
        this.width = graphicText.getLineWidth()*BORDER_OFFSET;
        this.height = graphicText.getLineHeight()*BORDER_OFFSET;
        graphicText.getLocalTranslation().x = -width/2;
        graphicText.getLocalTranslation().y = height/2;
        
        this.attachChild(graphicText);
	}
	
	public void setExpireTime(float time) {
		expireTime = time + Timer.getTimer().getTimeInSeconds();
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setColour(ColorRGBA col) {
		graphicText.setDefaultColor(col);
	}

	@Override
	public void update() {
		graphicText.setText(text);
		graphicText.update();
		this.width = graphicText.getLineWidth()*BORDER_OFFSET;
		graphicText.getLocalTranslation().x = -width/2;
		if (this.toRemove())
			this.destroy();
	}
	
	
	public boolean toRemove() {
		if (expireTime == -1)
			return false;
		float currentTime = Timer.getTimer().getTimeInSeconds();
		return (currentTime>=expireTime);
	}

}
