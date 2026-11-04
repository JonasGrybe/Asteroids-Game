package test;

import javax.swing.*;

/**
 * Pagrindinis žaidimo langas.
 * Sukuria ir paruošia žaidimo rėmelį su pagrindiniu meniu.
 */
public class GameFrame extends JFrame {

    /**
     * Inicializuoja žaidimo langą:
     * - nustato pavadinimą,
     * - išjungimo veiksmą,
     * - dydžio keitimo draudimą,
     * - įkelia pagrindinį meniu,
     * - centruoja langą ekrane.
     */
    public GameFrame() {
        setTitle("Asteroids");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(new MainMenuPanel(this));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Paleidžia žaidimą grafinėje aplinkoje (EDT).
     * Sukuria naują GameFrame objektą.
     * @param args nepanaudoti argumentai
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}
