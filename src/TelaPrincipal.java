
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
	
	 double protagonistaX=250;
	 double protagonistaY=330;
	 
	 double protagonistaYLimite=330;
	 
	 double velocidade=5;
	 double boost =1.0;
	 
	 int isJumping = 1;

	 
	 Image imgFloresta = new Image(getClass().getResourceAsStream("/images/floresta1.png"));
	 
	 Image imgProtagonista = new Image(getClass().getResourceAsStream("/images/protagonista.png"));
	
	 Image imgHatsune = new Image(getClass().getResourceAsStream("/images/hatsuneinimigo.png"));
		
	 double hatsuX=metadeImagemX(imgHatsune, protagonistaX + 400);
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
				//protagonistaMover(0,velocidade*isJumping*boost);
				if(boost>1) {
					boost-=0.015;
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
		    	if(protagonistaY<=0 ||!(protagonistaX>0 && protagonistaX< 600) ) {
		    		protagonistaMover(0,0);
				} else {
					isJumping=-1;
					boost=2;
				}
		    }
		}
	}
		
	
	class ManipuladorReleased implements EventHandler<KeyEvent>{

		@Override
		public void handle(KeyEvent event) {
			protagonistaMover(0,0);
		}
		
	}
	
	public void protagonistaMover(double x, double y) {
		this.protagonistaX +=x;
		this.protagonistaY +=y;
	}
	
	
	
	
	public static void main(String[] args) {
		TelaPrincipal.launch(args);
	}

}
