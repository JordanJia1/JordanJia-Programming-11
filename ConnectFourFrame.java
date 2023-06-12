import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

public class ConnectFourFrame extends javax.swing.JFrame {
    
    private Image ib;  //we do all drawing onto this image, it acts as an image buffer
    private Graphics ibg;  //will be set to our image buffer's graphic object
    private int[][] board = new int[6][7];
    private boolean player1Turn = true;
    public int wins = 0;
    public int wins2 = 0;
    
    public ConnectFourFrame() {
        initComponents();
        button0.setEnabled(false);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        setLocationRelativeTo(null);
        setUpImageBuffer();
        System.out.println(panelDraw.getWidth() + "," + panelDraw.getHeight() );
    }

    //set our image (buffer) to a new image of the correct size
    public void setUpImageBuffer(){
        ib=this.createImage(panelDraw.getWidth(), panelDraw.getHeight() );
        ibg=ib.getGraphics();
        Graphics2D g2 = (Graphics2D) ibg;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    }
    public void draw() {
        // Clear the board
        ibg.setColor(Color.WHITE);
        ibg.fillRect(0, 0, panelDraw.getWidth(), panelDraw.getHeight());

        // Fill the circles
        ibg.setColor(Color.BLUE);
        ibg.fillRect(0, 0, panelDraw.getWidth(), panelDraw.getHeight());
        ibg.setColor(Color.WHITE);
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                int x = col * 58 + 8;
                int y = (5 - row) * 36 + 3;
                ibg.fillOval(x, y, 48, 33);
                if (board[row][col] == 1) {
                    ibg.setColor(Color.RED);
                    ibg.fillOval(x, y, 48, 33);
                } else if (board[row][col] == 2) {
                    ibg.setColor(Color.YELLOW);
                    ibg.fillOval(x, y, 48, 33);
                }
                ibg.setColor(Color.WHITE);
            }
        }
  
    // Draw the border
    ibg.setColor(Color.BLACK);
    ibg.drawRect(0, 0, panelDraw.getWidth(), panelDraw.getHeight());
    
    // Update the display
    Graphics g = panelDraw.getGraphics();
    g.drawImage(ib, 0, 0, this);
}
    
    public void placePiece(int column) {
    // Find the first empty row in the column
    int row = -1;
    for (int i = 0; i < board.length; i++) {
        if (board[i][column] == 0) {
            row = i;
            break;
        }
    }
    if (row == -1) {
        // Column is full, do nothing
        return;
    }
    
    // Place the piece and update the board
    int player = player1Turn ? 1 : 2;
    board[row][column] = player;
    player1Turn = !player1Turn;
    
    // Redraw the board
    draw();
}
    
    public void drawBoard() {
        ibg.setColor(Color.BLUE);
        ibg.fillRect(0, 0, panelDraw.getWidth(), panelDraw.getHeight() );

        ibg.setColor(Color.WHITE);
        for (int i = 0; i < board[0].length; i++) {
            for (int k = 0; k < board.length; k++) {
                ibg.fillOval((58 * i) + 8, ((36 * k) + 3) , 48 ,  33   );
            }
        }   
    }
    
    public void handleWin() {
        button0.setEnabled(false);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        
        if (!player1Turn) {
            wins++;
            labelStats.setText("Stats:  Player 01:  " + wins + " Wins    Player 2:  " + wins2 + " Wins");
        }
        if (player1Turn) {
            wins2++;
            labelStats.setText("Stats:  Player 01:  " + wins + " Wins    Player 2:  " + wins2 + " Wins");
        }
    }
   
    public boolean checkForWin() {
        boolean won;
        // Check horizontal
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col <= board[row].length - 4; col++) {
                int player = board[row][col];
                if (player == 0) continue;
                    won = true;
            for (int i = 1; i < 4; i++) {
                if (board[row][col+i] != player) {
                    won = false;
                    break;
                }
            }
            if (won) {
                return true;
            }
        }
    }

        // Check vertical
        for (int col = 0; col < board[0].length; col++) {
            for (int row = 0; row <= board.length - 4; row++) {
                int player = board[row][col];
                if (player == 0) continue;
                won = true;
                for (int i = 1; i < 4; i++) {
                    if (board[row+i][col] != player) {
                        won = false;
                        break;
                    }
                }
                if (won) { 
                    return true;

                }
            }  
        }

        // Check diagonal (top-left to bottom-right)
        for (int row = 0; row <= board.length - 4; row++) {
            for (int col = 0; col <= board[row].length - 4; col++) {
                int player = board[row][col];
                if (player == 0) continue;
                won = true;
                for (int i = 1; i < 4; i++) {
                    if (board[row+i][col+i] != player) {
                        won = false;
                        break;
                    }
                }
                if (won) {
                   return true;
                }
            }
        }

        // Check diagonal (bottom-left to top-right)
        for (int row = 3; row < board.length; row++) {
            for (int col = 0; col <= board[row].length - 4; col++) {
                int player = board[row][col];
                if (player == 0) continue;
                won = true;
                for (int i = 1; i < 4; i++) {
                    if (board[row-i][col+i] != player) {
                        won = false;
                        break;
                    }
                }
                if (won) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void checkForEnd() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == 0) {
                    return;
                }
            }
    
    }
        button0.setEnabled(false);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
       
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        button0 = new javax.swing.JButton();
        button1 = new javax.swing.JButton();
        button2 = new javax.swing.JButton();
        button3 = new javax.swing.JButton();
        button4 = new javax.swing.JButton();
        button5 = new javax.swing.JButton();
        button6 = new javax.swing.JButton();
        panelDraw = new javax.swing.JPanel();
        textInfo = new javax.swing.JTextField();
        labelStats = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuGame = new javax.swing.JMenu();
        mnuStartNewGame = new javax.swing.JMenuItem();
        mnuResetStats = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        button0.setText("0");
        button0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button0ActionPerformed(evt);
            }
        });

        button1.setText("1");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setText("2");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        button3.setText("3");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        button4.setText("4");
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button4ActionPerformed(evt);
            }
        });

        button5.setText("5");
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button5ActionPerformed(evt);
            }
        });

        button6.setText("6");
        button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button0, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button0)
                    .addComponent(button1)
                    .addComponent(button2)
                    .addComponent(button3)
                    .addComponent(button4)
                    .addComponent(button5)
                    .addComponent(button6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelDraw.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelDrawLayout = new javax.swing.GroupLayout(panelDraw);
        panelDraw.setLayout(panelDrawLayout);
        panelDrawLayout.setHorizontalGroup(
            panelDrawLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelDrawLayout.setVerticalGroup(
            panelDrawLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 219, Short.MAX_VALUE)
        );

        labelStats.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelStats.setText("Stats:  Player 01:  0 Wins    Player 2:  0 Wins");

        mnuGame.setText("Game");

        mnuStartNewGame.setText("Start New Game");
        mnuStartNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStartNewGameActionPerformed(evt);
            }
        });
        mnuGame.add(mnuStartNewGame);

        mnuResetStats.setText("Reset Stats");
        mnuResetStats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuResetStatsActionPerformed(evt);
            }
        });
        mnuGame.add(mnuResetStats);

        jMenuBar1.add(mnuGame);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(textInfo)
            .addComponent(labelStats, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelDraw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDraw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelStats)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuStartNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStartNewGameActionPerformed
       button0.setEnabled(true);
       button1.setEnabled(true);
       button2.setEnabled(true);
       button3.setEnabled(true);
       button4.setEnabled(true);
       button5.setEnabled(true);
       button6.setEnabled(true);
       board = new int[6][7];
       ibg.setColor(Color.WHITE);
       ibg.fillRect(0, 0, panelDraw.getWidth(), panelDraw.getHeight());
       draw();
       drawBoard();
    }//GEN-LAST:event_mnuStartNewGameActionPerformed

    private void button0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button0ActionPerformed
        placePiece(0);
        checkForEnd();
        boolean win = checkForWin();
        if (win) {
           handleWin();
        }
        buttonPressed(0);
    }//GEN-LAST:event_button0ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        placePiece(1);
        checkForEnd();
        boolean win = checkForWin();
        if (win) {
           handleWin();
        }
        buttonPressed(1);
        
    }//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        placePiece(2);
        checkForEnd();
        boolean win = checkForWin();
        if (win) {
           handleWin();
        }
        buttonPressed(2);
    }//GEN-LAST:event_button2ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        placePiece(3);
        checkForEnd();
        boolean win = checkForWin();
        if (win) {
           handleWin();
        } 
        buttonPressed(3);
    }//GEN-LAST:event_button3ActionPerformed

    private void button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button4ActionPerformed
        placePiece(4);
        checkForEnd();
        boolean win = checkForWin();
        if (win) {
           handleWin();
        }
        buttonPressed(4);
    }//GEN-LAST:event_button4ActionPerformed

    private void button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button5ActionPerformed
        placePiece(5);
        checkForEnd();
        boolean win = checkForWin();
        if (win) {
           handleWin();
        }
        buttonPressed(5);
    }//GEN-LAST:event_button5ActionPerformed

    private void button6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button6ActionPerformed
        placePiece(6);
        checkForEnd();
        boolean win = checkForWin();
        if (win) {
           handleWin();
        }
        buttonPressed(6);
    }//GEN-LAST:event_button6ActionPerformed

    private void mnuResetStatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuResetStatsActionPerformed
        wins = 0;
        wins2 = 0;
        labelStats.setText("Stats:  Player 01:  " + wins + " Wins    Player 2:  " + wins2 + " Wins");
    }//GEN-LAST:event_mnuResetStatsActionPerformed

    private void buttonPressed(int col) {
        System.out.println("Pressed: " + col);
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConnectFourFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConnectFourFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConnectFourFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConnectFourFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConnectFourFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button0;
    private javax.swing.JButton button1;
    private javax.swing.JButton button2;
    private javax.swing.JButton button3;
    private javax.swing.JButton button4;
    private javax.swing.JButton button5;
    private javax.swing.JButton button6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelStats;
    private javax.swing.JMenu mnuGame;
    private javax.swing.JMenuItem mnuResetStats;
    private javax.swing.JMenuItem mnuStartNewGame;
    private javax.swing.JPanel panelDraw;
    private javax.swing.JTextField textInfo;
    // End of variables declaration//GEN-END:variables
}
