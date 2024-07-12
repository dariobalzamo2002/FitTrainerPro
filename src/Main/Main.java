package Main;

import View.login;
import javax.swing.JFrame;

public class Main 
{
    public static void main(String[] args) 
    {
         // Creare un'istanza del JFrame login
        
        login loginFrame = new login();
        // Configurare il comportamento di chiusura
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Mostrare il JFrame
        loginFrame.setVisible(true);
    }
}