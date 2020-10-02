import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUI{
	public String msg = null;
	public int flag = 0;
	public JFrame frame;
	public JButton[][] boxes = new JButton[3][3];
	public String jogador, computador; 
	public String ans="";
    public JLabel lable;
    public String turn="";
    public String[][] tabuleiro;
	public Move move;
	
	public class Move 
    { 
        int linha, coluna;
    }
      
   boolean temMovimentosExistentes(String board[][]) {
        for (int i = 0; i<3; i++) 
            for (int j = 0; j<3; j++) 
                if (board[i][j].equals(" ")) 
                    return true; 
        return false; 
    } 
    
    boolean xGanhou(String[][] b) {
    	for (int row = 0; row<3; row++) { 
            if (b[row][0]==b[row][1] && b[row][1]==b[row][2]) { 
                if (b[row][0]=="X") 
                    return true; 
            }
    	}
         
    	  for (int col = 0; col<3; col++) { 
              if (b[0][col]==b[1][col] && b[1][col]==b[2][col]) { 
                  if (b[0][col]=="X") 
                      return true;  
              } 
          }
    	  
    	  if (b[0][0]==b[1][1] && b[1][1]==b[2][2] && b[0][0]=="X" )
              return true;
    	  if (b[0][2]==b[1][1] && b[1][1]==b[2][0] && b[0][2]=="X" )
              return true;
        
    	return false;
    }
    
    boolean oGanhou(String[][] b) {
    	for (int row = 0; row<3; row++) { 
            if (b[row][0]==b[row][1] && b[row][1]==b[row][2]) { 
                if (b[row][0]=="O") 
                    return true; 
            }
    	}
         
    	  for (int col = 0; col<3; col++) { 
              if (b[0][col]==b[1][col] && b[1][col]==b[2][col]) { 
                  if (b[0][col]=="O") 
                      return true;  
              } 
          } 
    	  
    	  if (b[0][0]==b[1][1] && b[1][1]==b[2][2] && b[0][0]=="O" )
              return true;
    	  if (b[0][2]==b[1][1] && b[1][1]==b[2][0] && b[0][2]=="O" )
              return true;
    	return false;
    }
    
    int avaliar(String b[][])
    { 
        // Verifica se a sequencia de linhas 3, X ou 0 para ganhar
        for (int row = 0; row<3; row++) 
        { 
            if (b[row][0]==b[row][1] && 
                b[row][1]==b[row][2]) 
            { 
                if (b[row][0]==jogador) 
                    return 10; 
                else if (b[row][0]==computador) 
                    return -10; 
            } 
        } 
      
        // Verifica a sequencia de colunas para ver se ganhou
        for (int col = 0; col<3; col++) 
        { 
            if (b[0][col]==b[1][col] && 
                b[1][col]==b[2][col]) 
            { 
                if (b[0][col]==jogador) 
                    return 10; 
      
                else if (b[0][col]==computador) 
                    return -10; 
            } 
        } 
      
        // Verifica as Diagonais para ver se ganhou
        if (b[0][0]==b[1][1] && b[1][1]==b[2][2]) 
        { 
            if (b[0][0]==jogador) 
                return 10; 
            else if (b[0][0]==computador) 
                return -10; 
        } 
      
        if (b[0][2]==b[1][1] && b[1][1]==b[2][0]) 
        { 
            if (b[0][2]==jogador) 
                return 10; 
            else if (b[0][2]==computador) 
                return -10; 
        } 
      
        return 0; 
    } 
      
    int scoreMinEMax(String tabuleiro[][], int depth, boolean ehMaximo)
    { 
        int score = avaliar(tabuleiro);
        if (score == 10) 
            return score;

        if (score == -10) 
            return score;

        if (temMovimentosExistentes(tabuleiro)==false)
            return 0; 
      
        if (ehMaximo)
        {
            int melhor = -1000;
            // Colunas transversos
            for (int i = 0; i<3; i++) 
            { 
                for (int j = 0; j<3; j++) 
                { 
                    // Verifica se está vazio
                    if (tabuleiro[i][j].equals(" "))
                    { 
                        // Move a peça
                        tabuleiro[i][j] = jogador;
                        melhor = Math.max( melhor, scoreMinEMax(tabuleiro, 0, !ehMaximo) );
                        tabuleiro[i][j] = " ";
                    } 
                } 
            } 
            return melhor-depth;
        } 
      
        else
        { 
            int melhor = 1000;
      
            // Faz a reversa
            for (int i = 0; i<3; i++) 
            { 
                for (int j = 0; j<3; j++) 
                { 
                    // Verifica se tá vazio
                    if (tabuleiro[i][j].equals(" "))
                    { 
                        // Faz o movimento
                        tabuleiro[i][j] = computador;
                        melhor = Math.min(melhor, scoreMinEMax(tabuleiro,0, !ehMaximo));
                        tabuleiro[i][j] = " ";
                    } 
                } 
            } 
            return melhor+depth;
        } 
    } 
      
    Move enconetraMelhorMovimento(String board[][], boolean ismax) { 
    	    int melhorScore = -1000;
    	    Move melhorMovimento = new Move();
    	    melhorMovimento.linha = -1;
    	    melhorMovimento.coluna = -1;
        for (int i = 0; i<3; i++) 
        { 
            for (int j = 0; j<3; j++) 
            { 
                // Vê se ta vazio
                if (board[i][j].equals(" ")) 
                { 
                    // Faz o movimeto
                    board[i][j] = jogador; 
                    int moveVal = scoreMinEMax(board,0, ismax);
      
                    board[i][j] = " "; 
                    if (moveVal > melhorScore)
                    { 
                        melhorMovimento.linha = i;
                        melhorMovimento.coluna = j;
                        melhorScore = moveVal;
                    } 
                } 
            } 
        }
        return melhorMovimento;
    } 
     
    public void printboard(String[][] b) {
    	for(int i=0;i<3;i++) {
    		for(int j=0;j<3;j++) {
    			System.out.print(b[i][j]+" ");
    		}
    		System.out.println();
    	}
    }
    
    public void criaTabuleiro(String[][] b) {
    	for(int i=0;i<3;i++) {
    		for(int j=0;j<3;j++) {
    			b[i][j] = " ";
    		}
    	}
    }
    
    
    public void finalizaJogo(JButton[][] b) {
    	for(int i=0;i<3;i++) {
    		for(int j=0;j<3;j++) {
    			b[i][j].setEnabled(false);
    		}
    	}
    }
    
    public void iniciaJogo(JButton[][] b) {
    	for(int i=0;i<3;i++) {
    		for(int j=0;j<3;j++) {
    			b[i][j].setEnabled(true);
    		}
    	}
    }

    
    public GUI() {
		frame = new JFrame();
			jogador = "X";
			computador = "O";
			turn = "computador";
            ans = "X";

		if(ans!=null) {
			lable = new JLabel("");
			lable.setBounds(70, -20, 260, 110);

		//adiciona as boxes
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				boxes[i][j] = new JButton(" ");
				boxes[i][j].setBounds((j + 1) * 50, (i + 1) * 50, 50, 50);
				boxes[i][j].setBackground(Color.WHITE);
				boxes[i][j].setForeground(Color.GRAY);
                boxes[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
				final Integer in = new Integer(i);
				final Integer jn = new Integer(j);
				
				boxes[i][j].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if(jogador.equals("O") & turn.equals("jogador")) {
							boxes[in][jn].setText("O");
							tabuleiro[in][jn]="O";
						}
						
						if(jogador.equals("X") & turn.equals("jogador")) {
							boxes[in][jn].setText("X");
							tabuleiro[in][jn]="X";
						}
						
						if(turn.equals("jogador")){
					         turn ="computador";
				        }
					}
				});
				frame.add(boxes[i][j]);
			}
		}
		

        lable.setFont(new Font("Arial", Font.PLAIN, 20));
		frame.add(lable);
		frame.setSize(280, 280);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tabuleiro = new String[3][3];
		criaTabuleiro(tabuleiro);
	   }
	}
}
