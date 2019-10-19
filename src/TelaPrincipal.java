
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
	public double metadeImagemX(Image img, double x) { 
		return x - img.getWidth() / 2;
	}
	
	public double metadeImagemY(Image img, double y) { 
		return y - img.getHeight() / 2;
	}	
	

	
	 double x=380;
	 double y=300;
	 
	 double velocidade=5;
	 double boost =1.0;
	 
	 int isJumping = 1;

	@Override
	public void start(Stage stage) throws Exception {
		Image imgFloresta = new Image(
				getClass().getResourceAsStream("/images/floresta1.png"));
		Image imgProtagonista = new Image(
				getClass().getResourceAsStream("/images/protagonista.png"));
		Image imgHatsune = new Image(
				getClass().getResourceAsStream("/images/hatsuneinimigo.png"));

		double centerX = imgFloresta.getWidth() / 2;
		double centerY = imgFloresta.getHeight() / 2;
		Group grp = new Group();
		Scene scn = new Scene(grp, imgFloresta.getWidth(), imgFloresta.getHeight());
		
		Canvas canvas = new Canvas(imgFloresta.getWidth(), imgFloresta.getHeight());
		grp.getChildren().add(canvas);
		
		GraphicsContext ctx = canvas.getGraphicsContext2D();
		
		EventHandler<KeyEvent> manipulador = new Manipulador();
		EventHandler<KeyEvent> manipuladorRelease = new ManipuladorReleased();
		stage.addEventFilter(KeyEvent.KEY_PRESSED,manipulador);
		stage.addEventFilter(KeyEvent.KEY_RELEASED, manipuladorRelease);
		
		
		new AnimationTimer() {
			
			@Override
			public void handle(long now) {

				double centerflorestaX = metadeImagemX(imgProtagonista, centerX);
				double centerflorestaY = metadeImagemY(imgProtagonista, centerY);
				
				double centerhatX = metadeImagemX(imgHatsune, centerX + 400);
				double centerhatY = metadeImagemY(imgHatsune, centerY);
				
				
				ctx.drawImage(imgFloresta, 0, 0);
				ctx.drawImage(imgProtagonista, centerflorestaX, centerflorestaY);
				ctx.drawImage(imgHatsune, centerhatX, centerhatY);
				move(0,velocidade*isJumping*boost);
				if(boost>1) {
					boost-=0.015;
				}
			}
		}.start();
		
		stage.setTitle("Fox Yokai");
		stage.setScene(scn);
		stage.show();
	}
	
	
	class Manipulador implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.UP) {
		    	if(y<=0 ||!(x>0 && x< 600) ) {
		    		move(0,0);
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
			move(0,0);
		}
		
	}
	
	public void move(double x, double y) {
		this.x +=x;
		this.y +=y;
	}
	
	
	
	
	public static void main(String[] args) {
		TelaPrincipal.launch(args);
	}

}
