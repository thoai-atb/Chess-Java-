package UI.game;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class DifficultyUI extends JFrame {
	
	GameFrame frame;
    JRadioButton  but1, but2, but3;
	
	public DifficultyUI(GameFrame frame) {
		super("Adjust Difficulty");
		this.frame = frame;
		this.setSize(300, 130);
		
		init();
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void init() {
	    JPanel content = new JPanel();
		but1 = new JRadioButton("1");
		but2 = new JRadioButton("2");
		but3 = new JRadioButton("3");
		
		int diff = frame.s.controller.getAI().getDifficulty();
		but1.setSelected(diff == 1);
		but2.setSelected(diff == 2);
		but3.setSelected(diff == 3);
		
		ButtonGroup group = new ButtonGroup();
		group.add(but1);
		group.add(but2);
		group.add(but3);
		
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		content.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.weightx = 2;
		c.weighty = 2;
		
		c.gridx = 0;
		c.gridy = 0;
		content.add(but1, c);
		
		c.gridx = 1;
		c.gridy = 0;
		content.add(but2, c);
		
		c.gridx = 2;
		c.gridy = 0;
		content.add(but3, c);
		
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateDifficulty();
			}
		});
		
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		content.add(ok, c);
		
		this.add(content);
	}
	
	private void updateDifficulty() {
		int depth = 0;
		if(but1.isSelected())
			depth = 1;
		else if(but2.isSelected())
			depth = 2;
		else if(but3.isSelected())
			depth = 3;
		frame.s.controller.getAI().setDifficulty(depth);
		this.dispose();
	}
}
