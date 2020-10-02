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
		System.out.println("Aplicativo Iniciado");
		GUI g = new GUI();
		addBehaviour(new  TickerBehaviour(this,500) {

			@Override
			protected void onTick() {
				if(g.turn=="computador") {
				   if(g.computador=="O") {
					   if(g.xGanhou(g.tabuleiro)) {
						   g.lable.setText("Você Ganhou");
						   g.disAbleAll(g.boxes);
						   g.turn=" ";g.jogador = " ";
					   }

					   if(g.turn=="computador") {
					   g.move = g.enconetraMelhorMovimento(g.tabuleiro,false);
					   g.tabuleiro[g.move.row][g.move.col]="O";
					   g.boxes[g.move.row][g.move.col].setText("O");
					   g.turn = "jogador";
					   }

					   if(g.oGanhou(g.tabuleiro)) {
						   g.lable.setText("Você Perdeu");
						   g.disAbleAll(g.boxes);
						   g.turn=" ";g.jogador=" ";
					   }
				   }
				   
				   if(g.computador=="X") {
					   if(g.oGanhou(g.tabuleiro)) {
						   g.lable.setText("Você Ganhou");
						   g.disAbleAll(g.boxes);
						   g.turn=" ";g.jogador=" ";
					   }

					   if(g.turn=="computador") {
					   g.move = g.enconetraMelhorMovimento(g.tabuleiro,true);
					   g.tabuleiro[g.move.row][g.move.col]="X";
					   g.boxes[g.move.row][g.move.col].setText("X");
					   g.turn = "jogador";

					   if(g.xGanhou(g.tabuleiro)) {
						   g.lable.setText("Você Perdeu");
						   g.disAbleAll(g.boxes);
						   g.turn=" ";g.jogador = " ";
					   } 
					 }
				   }
				}
			}
		});	
	}
}
