package Menu.src;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Icon;

public class AniMaker extends MouseAdapter implements Icon, ActionListener {
        /** Periodo dell'animazione */
        private final int ANIMATION_PERIOD;
        /** Larghezza di un frame */
        private final int FRAME_WIDTH;
        /** Altezza di un frame */
        private final int FRAME_HEIGHT;
        /** Numero di righe nell'immagine contenente i
        frame */
        private final int ROW_COUNT;
        /** Numero di colonne nell'immagine contenente i
        frame */
        private final int COLUMN_COUNT;
        /** Temporizzatore swing dell'animazione */
        private final javax.swing.Timer ANIMATION_TIMER;
        /** Immagine contenente tutti i frame dell'animazione */
        private final BufferedImage FRAME_SET;
        /** Pulsante bersaglio dell'animazione */
        private final AbstractButton TARGET_BUTTON;
        /** Area di clipping usata durante il disegno */
        private final Rectangle FRAME_CLIP_BUFFER = new Rectangle();
        /** Indice della riga in cui si trova il frame corrente
        dell'animazione */
        private int currentFrameRow;
        /** Indice della colonna in cui si trova il frame corrente
        dell'animazione */
        private int currentFrameColumn;
        /** Inizializza un AniMaker.
        @param frameSet immagine contente i frame dell'animazione
        @param frameWidth larghezza di un frame
        @param frameHeight altezza di un frame
        @param animationPeriod periodo dell'animazione
        @param button pulsante a cui è applicata l'animazione */
        public AniMaker(BufferedImage frameSet, int frameWidth, int frameHeight,
                 int animationPeriod, AbstractButton button)
        {
            FRAME_WIDTH = frameWidth;
            FRAME_HEIGHT = frameHeight;
            ROW_COUNT = frameSet.getHeight() / frameHeight;
		    COLUMN_COUNT = frameSet.getWidth() / frameWidth;
		    FRAME_SET = frameSet;
		    ANIMATION_PERIOD = animationPeriod;
		    ANIMATION_TIMER = new javax.swing.Timer(ANIMATION_PERIOD, this);
		    TARGET_BUTTON = button;
		    TARGET_BUTTON.addMouseListener(this);
		    TARGET_BUTTON.setIcon(this);
        }
		/** Salta al primo frame dell'animazione
		e richiede un aggiornamento del pulsante
		bersaglio */
		private void jumpToFirstFrame() {
		        currentFrameColumn = currentFrameRow = 0;
		        TARGET_BUTTON.repaint();
		}
		/** Calcola gli indici di riga e colonna del
		frame successivo e richiede un aggiornamento
		grafico del pulsante bersaglio */
		private void showNextFrame() {
		        currentFrameColumn++;
		        if(currentFrameColumn == COLUMN_COUNT) {
		                currentFrameColumn = 0;
		                currentFrameRow++;
		                if(currentFrameRow == ROW_COUNT) {
		                        currentFrameRow = 0;
		                }
		        }
		        TARGET_BUTTON.repaint();
		}
		/** Avvia l'animazione */
		public void startAnimation() {
		        ANIMATION_TIMER.start();
		}
		/** Interrompe l'animazione. L'interruzione
		riporta l'animazione al primo frame */
		public void stopAnimation() {
		        jumpToFirstFrame();
		        ANIMATION_TIMER.stop();
		}
		/** @inheritDoc */
		public int getIconWidth() {
		        return FRAME_WIDTH;
		}
		/** @inheritDoc */
		public int getIconHeight() {
		        return FRAME_HEIGHT;
		}
		/** Disegna il frame corrente dell'animazione sul controllo c */
		public void paintIcon(Component c, Graphics g, int x, int y) {
		        int offX = currentFrameColumn * FRAME_WIDTH;
		        int offY = currentFrameRow * FRAME_HEIGHT;
		        Shape previousClip = g.getClip();
		        FRAME_CLIP_BUFFER.setRect(x, y, FRAME_WIDTH, FRAME_HEIGHT);
		        g.setClip(FRAME_CLIP_BUFFER.createIntersection(g.getClipBounds()));
		        g.translate(-offX, -offY);
		        g.drawImage(FRAME_SET, x, y, c);
		        g.translate(offX, offY);
		        g.setClip(previousClip);
		}
		  
		  /** Blocco ciclicamente eseguito dall'AWT Event
		  Dispatcher tramite il javax.swing.Timer che controlla
		  l'animazione */
		  public void actionPerformed(ActionEvent e) {
		          if(e.getSource() == ANIMATION_TIMER) {
		                  showNextFrame();
		          }
		  }
		  /** Semplificazione dell'uso di un AniMaker */
		  public static AniMaker bindAnimation(String frameResourceAddress,
		          int frameWidth, int frameHeight, int animationPeriod,
		          AbstractButton button)
		  {
		          try {
		                  java.net.URL address = AniMaker.class.getResource(
		                          frameResourceAddress);
		                  return new AniMaker(ImageIO.read(address), frameWidth,
		                          frameHeight, animationPeriod, button);
		          } catch(java.io.IOException ex) {
		                  throw new RuntimeException(ex);
		          }
		  }
}
