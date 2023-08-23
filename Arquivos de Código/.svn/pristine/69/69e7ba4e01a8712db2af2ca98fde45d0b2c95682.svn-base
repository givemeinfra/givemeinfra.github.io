package treeview.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Collection;
import javax.swing.JComboBox;

import collaborative.controller.MessageController;
import collaborative.valueObject.ActivityVO;

public class frmSendMessage extends JFrame {

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
					Collection<ActivityVO> t = null;
					frmSendMessage frame = new frmSendMessage("", t);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmSendMessage(final String clickedEntity, final Collection<ActivityVO> listActivity) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 639, 243);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 11, 603, 135);
		contentPane.add(textArea);	

		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(235, 171, 281, 23);
		contentPane.add(comboBox);
		this.setTitle("Send a Message about " + clickedEntity);
		
		// add activities on combobox
		comboBox.addItem("");
		for(ActivityVO activityTemp : listActivity)
			comboBox.addItem(activityTemp.getName());
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textArea.getText().equals(""))
				{
					MessageController controller = new MessageController();
					if(controller.sendCollaborationMsgByEntity(textArea.getText(), clickedEntity, (String)comboBox.getSelectedItem()))
						dispose();
				}
			}
		});
		btnSend.setBounds(10, 171, 89, 23);
		contentPane.add(btnSend);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(120, 171, 89, 23);
		contentPane.add(btnCancel);	
		
	}
}
