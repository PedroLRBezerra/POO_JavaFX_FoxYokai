
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class TelaPrincipal extends Application {
	Canvas canvas = new Canvas(800,600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	
	 double x=380;
	 double y=300;
	 
	 double velocidade=5;
	 double boost =1.0;
	 
	 int isJumping = 1;

	@Override
	public void start(Stage stage) throws Exception {
		Group grp = new Group();
		Scene scn = new Scene(grp,800,600);
		grp.getChildren().add(canvas);
		
		EventHandler<KeyEvent> manipulador = new Manipulador();
		EventHandler<KeyEvent> manipuladorRelease = new ManipuladorReleased();
		stage.addEventFilter(KeyEvent.KEY_PRESSED,manipulador);
		stage.addEventFilter(KeyEvent.KEY_RELEASED, manipuladorRelease);
		
		paint(gc);
		
		
		new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				move(0,velocidade*isJumping*boost);
				if(boost>1) {
					boost-=0.015;
				}
			}
		}.start();
		
		stage.setScene(scn);
		stage.setTitle("Fox Yokai");
		stage.show();
	}
	
	
	private void paint(GraphicsContext gc) {
		gc.setFill(Color.PEACHPUFF);
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.DARKRED);
		gc.fillArc(x, y, 40, 40, 0, 360, ArcType.ROUND);
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
        paint(gc);
	}
	
	
	
	
	public static void main(String[] args) {
		TelaPrincipal.launch(args);
	}

}
