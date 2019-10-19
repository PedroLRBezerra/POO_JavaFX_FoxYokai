
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TelaPrincipal extends Application {
	
	Image imgFloresta = new Image(getClass().getResourceAsStream("/images/floresta1.png"));
	 
	 Image imgProtagonista = new Image(getClass().getResourceAsStream("/images/protagonista.png"));
	
	 Image imgHatsune = new Image(getClass().getResourceAsStream("/images/hatsuneinimigo.png"));
	 
	 double florestaWidth =imgFloresta.getWidth();
	 
	
	 double protagonistaYLimiteBaixo=400;
	 double protagonistaYLimiteAlto=200;
	
	 double protagonistaX;
	 double protagonistaY=protagonistaYLimiteBaixo;
	 
	 
	 double protagonistaVelocidade=4.1;
	 double protagonistaBoost =1.0;
	 
	 int isJumping = 1;

	 
	 double hatsuX=metadeImagemX(imgHatsune, florestaWidth);
	 double hatsuY=metadeImagemY(imgHatsune, protagonistaY);
	 
	 double hatsuHidth=imgHatsune.getWidth();
	 
	 double hatsuYLimiteBaixo=400;
	 double hatsuYLimiteAlto=290;
	 
	 
	 double hatsuVelocidade;
	 int hatsuVelocidadeDir=1;;
	 
	 boolean hatsuIsOut=true;
	 
	 long hatsuWaitTime=0;
	 long hatsuWaitedTime=1;
	 
	 
	 
	 Canvas canvas = new Canvas(imgFloresta.getWidth(), imgFloresta.getHeight());
	 
	 GraphicsContext ctx = canvas.getGraphicsContext2D();
	
	public double metadeImagemX(Image img, double x) { 
		return x - img.getWidth() / 2;
	}
	
	public double metadeImagemY(Image img, double y) { 
		return y - img.getHeight() / 2;
	}	
	

	

	@Override
	public void start(Stage stage) throws Exception {
		

		double centerX = imgFloresta.getWidth() / 2;
		double centerY = imgFloresta.getHeight() / 2;

		this.protagonistaX=centerX-(imgProtagonista.getWidth()/2);
		this.protagonistaY=protagonistaYLimiteBaixo;
		
		this.hatsuY=hatsuYLimiteAlto;
	    IntValue points = new IntValue(0);

		
		Group grp = new Group();
		Scene scn = new Scene(grp, imgFloresta.getWidth(), imgFloresta.getHeight());
		
		
		grp.getChildren().add(canvas);
		
		
		EventHandler<KeyEvent> manipulador = new Manipulador();
		EventHandler<KeyEvent> manipuladorRelease = new ManipuladorReleased();
		stage.addEventFilter(KeyEvent.KEY_PRESSED,manipulador);
		stage.addEventFilter(KeyEvent.KEY_RELEASED, manipuladorRelease);
		
		paint();
		Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
		ctx.setFont( theFont );
		ctx.setStroke( javafx.scene.paint.Color.KHAKI );
		ctx.setLineWidth(1);
		
		hatsuReset();
		
		new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				protagonistaMover(protagonistaVelocidade*isJumping*protagonistaBoost);
				
				if(protagonistaBoost>1) {
					protagonistaBoost-=0.02;
				}
				
				
				hatsuMover(hatsuVelocidade);

				paint();
	            String TextPontos = "Pontos: " + points.value;
	            ctx.fillText( TextPontos, 600, 40 );
	            ctx.strokeText( TextPontos, 600, 40 );
			}
		}.start();
		
		stage.setTitle("Fox Yokai");
		stage.setScene(scn);
		stage.show();
	}
	
	
	private void paint() {
		ctx.drawImage(imgFloresta, 0, 0);
		ctx.drawImage(imgProtagonista, protagonistaX, protagonistaY);
		ctx.drawImage(imgHatsune, hatsuX, hatsuY);
	}
	
	class Manipulador implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.UP) {
		    	if(isJumping==0) {
					isJumping=-1;
					protagonistaBoost=2.4;
				}
		    }
		}
	}
		
	
	class ManipuladorReleased implements EventHandler<KeyEvent>{
		@Override
		public void handle(KeyEvent event) {
			protagonistaMover(0);
		}
		
	}
	
	private void protagonistaMover(double y) {
		this.protagonistaY +=y;
		
		
		if(protagonistaY>=protagonistaYLimiteBaixo) {
			this.protagonistaY=this.protagonistaYLimiteBaixo;
			this.isJumping=0;
		}
		
		if(protagonistaY<protagonistaYLimiteAlto) {
			this.protagonistaY=this.protagonistaYLimiteAlto;
			this.isJumping=1;
		}
	}
	
	private void hatsuMover(double x) {
			this.hatsuX += x;
			if (this.hatsuX <= -this.hatsuHidth) {
				hatsuX = -hatsuHidth;
				hatsuReset();
			}
			if (this.hatsuX >= this.florestaWidth) {
				this.hatsuX = this.florestaWidth;
				hatsuReset();
			}
	}
	
	
	private void hatsuReset() {
		if(this.hatsuIsOut) {
			if(hatsuWaitedTime>hatsuWaitTime) {
				hatsuVelocidade=(Math.random()*8)+8;
				hatsuVelocidadeDir*=-1;
				hatsuVelocidade*=hatsuVelocidadeDir;
				hatsuY=hatsuYLimiteBaixo-(Math.random()*110);
				hatsuIsOut=false;
			}
			else {
				hatsuWaitedTime++;
			}
		}
		
		else {
			hatsuIsOut=true;
			hatsuWaitedTime=0;
			hatsuWaitTime=(long) ((Math.random()*15)+10);
		}
	}
	
	
	
	public static void main(String[] args) {
		TelaPrincipal.launch(args);
	}

}
