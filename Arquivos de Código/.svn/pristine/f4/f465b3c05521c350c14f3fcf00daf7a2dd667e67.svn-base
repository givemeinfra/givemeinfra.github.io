package collaborative.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import collaborative.controller.MessageController;
import collaborative.valueObject.ActivityVO;
import collaborative.valueObject.MessageVO;
import javax.swing.JComboBox;

public class frmGetAndSendMenssage extends JFrame {

	private JPanel contentPane;
	private JTextArea textAreaGet;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Collection<ActivityVO> t = null;
					frmGetAndSendMenssage frame = new frmGetAndSendMenssage("", t);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	private void loadingMessages(String clickedEntity) {
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
								textAreaGet.setText(textAreaGet.getText() + "  USER MESSAGES:");
								status = true;
							}
							if (message3.getActivity().equals("")) 
							{
								textAreaGet.setText(textAreaGet.getText() + "\n" + "  " + message3.getDate() + ", "	+ " said: " + message3.getText() + "  " + "\n\n\n");
							} 
							else 
							{
								textAreaGet.setText(textAreaGet.getText() + "\n" + "  " +  message3.getDate() + ", Activity: " + message3.getActivity() + ", " 
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
								textAreaGet.setText(textAreaGet.getText() + "  SYSTEM MESSAGES:");
								status = true;
							}							
							if (systemmessage3.getActivity().equals("")) {
								
								textAreaGet.setText(textAreaGet.getText() + "\n" + "  " +  systemmessage3.getDate() + ", "  + systemmessage3.getText() + "  ");
							} 
							else 
							{					
								textAreaGet.setText(textAreaGet.getText() + "\n" + "  " +  systemmessage3.getDate() + ", Activity: "
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
		
	}

	/**
	 * Create the frame.
	 */
	public frmGetAndSendMenssage(final String clickedEntity, final Collection<ActivityVO> listActivity) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 662, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(124, 373, 65, 25);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnCancel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 626, 205);
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 0));
		contentPane.add(scrollPane);
		
		textAreaGet = new JTextArea();
		textAreaGet.setBackground(SystemColor.menu);
		scrollPane.setViewportView(textAreaGet);
		setLocationRelativeTo(null);
		
		textAreaGet.setText("");
		
		final JTextArea textAreaSend = new JTextArea();
		textAreaSend.setBounds(10, 227, 626, 135);
		contentPane.add(textAreaSend);
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(214, 374, 281, 23);
		contentPane.add(comboBox);
		this.setTitle("Send a Message about " + clickedEntity);
		
		// add activities on combobox
		comboBox.addItem("");
		for(ActivityVO activityTemp : listActivity)
			comboBox.addItem(activityTemp.getName());
		
		JButton button = new JButton("Send");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textAreaSend.getText().equals(""))
				{
					MessageController controller = new MessageController();
					if(controller.sendCollaborationMsgByEntity(textAreaSend.getText(), clickedEntity, (String)comboBox.getSelectedItem()))
					{
						textAreaSend.setText("");
						textAreaGet.setText("");
						loadingMessages(clickedEntity);
					}
				}
			}
		});
		button.setBounds(10, 369, 89, 33);
		contentPane.add(button);

		loadingMessages(clickedEntity);
		
		this.setTitle("Messages about " + clickedEntity);	
	}
	
}
