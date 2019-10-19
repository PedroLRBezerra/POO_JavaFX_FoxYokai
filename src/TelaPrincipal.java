
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
import javafx.stage.Stage;

public class TelaPrincipal extends Application {
	
	 double protagonistaYLimiteBaixo=400;
	 double protagonistaYLimiteAlto=200;
	
	 double protagonistaX;
	 double protagonistaY=protagonistaYLimiteBaixo;
	 
	 
	 
	 double velocidade=4.1;
	 double boost =1.0;
	 
	 int isJumping = 1;

	 
	 Image imgFloresta = new Image(getClass().getResourceAsStream("/images/floresta1.png"));
	 
	 Image imgProtagonista = new Image(getClass().getResourceAsStream("/images/protagonista.png"));
	
	 Image imgHatsune = new Image(getClass().getResourceAsStream("/images/hatsuneinimigo.png"));
		
	 double hatsuX=metadeImagemX(imgHatsune, protagonistaX);
	 double hatsuY=metadeImagemY(imgHatsune, protagonistaY);
	 
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
		
		
		Group grp = new Group();
		Scene scn = new Scene(grp, imgFloresta.getWidth(), imgFloresta.getHeight());
		
		
		grp.getChildren().add(canvas);
		
		
		EventHandler<KeyEvent> manipulador = new Manipulador();
		EventHandler<KeyEvent> manipuladorRelease = new ManipuladorReleased();
		stage.addEventFilter(KeyEvent.KEY_PRESSED,manipulador);
		stage.addEventFilter(KeyEvent.KEY_RELEASED, manipuladorRelease);
		
		paint();
		
		
		new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				protagonistaMover(velocidade*isJumping*boost);
				if(boost>1) {
					boost-=0.02;
				}
				
				
				paint();
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
					boost=2.4;
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
	
	public void protagonistaMover(double y) {
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
	
	
	
	
	public static void main(String[] args) {
		TelaPrincipal.launch(args);
	}

}
