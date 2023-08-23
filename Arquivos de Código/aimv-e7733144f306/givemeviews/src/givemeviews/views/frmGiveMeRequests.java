package givemeviews.views;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import javax.swing.border.LineBorder;
import java.awt.Color;



public class frmGiveMeRequests extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmGiveMeRequests frame = new frmGiveMeRequests();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmGiveMeRequests() {
		setLocationRelativeTo(null);
		setTitle("GiveMe Requests - Registration of Change Requests");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 906, 650);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(0, 0, 900, 116);
		ImageIcon logo = new ImageIcon(frmGiveMeRequests.class.getResource("/givemeviews/givemerequests.png"));
		Image imagem = logo.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_FAST);
		lblLogo.setIcon(new ImageIcon(imagem));
		
		
		contentPane.add(lblLogo);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(0, 116, 900, 506);
		contentPane.add(panel);
		panel.setLayout(null);		
				
	}
}
