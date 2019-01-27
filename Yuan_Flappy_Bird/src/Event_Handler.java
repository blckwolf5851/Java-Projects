
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Event_Handler implements MouseListener{
		Var var;
		GameDisplay game;
		Event_Handler(Var var, GameDisplay game){
			this.var = var;
			this.game = game;
		}
		public Event_Handler(GameDisplay game) {
			this.game = game;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			var.v = -30;
			game.repaint();
			
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	
	
}
