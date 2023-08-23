package treeview.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;


import collaborative.controller.MessageController;
import collaborative.valueObject.MessageVO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;

public class frmGetMessages extends JFrame {

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
					frmGetMessages frame = new frmGetMessages("");
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
	public frmGetMessages(String clickedEntity) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 662, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(10, 368, 85, 28);
		contentPane.add(btnCancel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 626, 351);
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 0));
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(SystemColor.menu);
		scrollPane.setViewportView(textArea);
		setLocationRelativeTo(null);
		
		textArea.setText("");
		MessageController controller = new MessageController();
		try
		{
			ArrayList listMessages = controller.getCollaborationSystemAndUserMsgsByEntity(clickedEntity);
			if(listMessages != null && listMessages.size() > 0)
			{
				if(listMessages.get(0) != null)
				{
					Collection<MessageVO> collection = (Collection<MessageVO>)listMessages.get(0);
					Boolean status = false;
					for(MessageVO message3 : collection)
					{
						if (message3.getEntity()!=null && message3.getEntity().contains(clickedEntity)) 
						{
							if(status == false)
							{
								textArea.setText(textArea.getText() + "  USER MESSAGES:");
								status = true;
							}
							if (message3.getActivity().equals("")) 
							{
								textArea.setText(textArea.getText() + "\n" + "  " + message3.getDate() + ", "	+ " said: " + message3.getText() + "  " + "\n\n\n");
							} 
							else 
							{
								textArea.setText(textArea.getText() + "\n" + "  " +  message3.getDate() + ", Activity: " + message3.getActivity() + ", " 
										+ "You: " + " said: "+ message3.getText() + "  " + "\n");
							}
						}
					}
				}
				if(listMessages.get(1) != null)
				{
					Collection<MessageVO> collection2 = (Collection<MessageVO>) listMessages.get(1);
					Boolean status = false;
					for(MessageVO systemmessage3 : collection2)
					{
						if (systemmessage3.getEntity()!=null && systemmessage3.getEntity().contains(clickedEntity)) 
						{
							if(status == false)
							{
								textArea.setText(textArea.getText() + "  SYSTEM MESSAGES:");
								status = true;
							}							
							if (systemmessage3.getActivity().equals("")) {
								
								textArea.setText(textArea.getText() + "\n" + "  " +  systemmessage3.getDate() + ", "  + systemmessage3.getText() + "  ");
							} 
							else 
							{					
								textArea.setText(textArea.getText() + "\n" + "  " +  systemmessage3.getDate() + ", Activity: "
										+ systemmessage3.getActivity() +  ", " + systemmessage3.getText() + "  ");
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		this.setTitle("Messages about " + clickedEntity);	
		
	}
}
