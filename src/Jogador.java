import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class Jogador extends Agent{
	public static void main(String[] args) {
				String[] par = {
						"-gui",
						"-local-host",
						"127.0.0.1"
				};
				jade.Boot.main(par);
			}

	protected void setup() {
		GUI g = new GUI();
		addBehaviour(new  TickerBehaviour(this,500) {

			@Override
			protected void onTick() {
				if(g.turn=="computador") {
				   if(g.computador=="O") {
					   if(g.xGanhou(g.tabuleiro)) {
						   g.lable.setText("Você Ganhou");
						   g.finalizaJogo(g.boxes);
						   g.turn=" ";g.jogador = " ";
					   }

					   if(g.turn=="computador") {
					   g.move = g.enconetraMelhorMovimento(g.tabuleiro,false);
					   g.tabuleiro[g.move.linha][g.move.coluna]="O";
					   g.boxes[g.move.linha][g.move.coluna].setText("O");
					   g.turn = "jogador";
					   }

					   if(g.oGanhou(g.tabuleiro)) {
						   g.lable.setText("Você Perdeu");
						   g.finalizaJogo(g.boxes);
						   g.turn=" ";g.jogador=" ";
					   }
				   }
				}
			}
		});
	}
}
