package classification;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class LearingBaseBuilder extends JFrame {

	ArrayList<Position> positions;
	Random rand;
	TextArea description;
	JButton classify;
	JButton stop;
	ArrayList<JRadioButton> radios;
	Box radiosBox = Box.createVerticalBox();
	Box next = Box.createHorizontalBox();
	int count[];
	ButtonGroup grop;
	ArrayList<ClassifiedPosition> classified;
	int pos;
	JRadioButton skip;
	boolean was[];
	int row = 0;
	JButton right;
	DoubleClassificator classificator;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6693525547694715271L;

	public LearingBaseBuilder() throws FileNotFoundException,
			ClassNotFoundException, IOException {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 700);
		setVisible(true);

		positions = PositionReaderAdvanced.read(50000);
		rand = new Random(System.currentTimeMillis());
		pos = rand.nextInt(positions.size());
		description = new TextArea(positions.get(pos).getName() + "\n"
				+ positions.get(pos).getDescription());
		classify = new JButton("classify");
		right = new JButton("right");
		stop = new JButton("enough");
		radios = new ArrayList<>();
		count = new int[Cluster.values().length];
		classified = new ArrayList<>();
		grop = new ButtonGroup();
		was = new boolean[positions.size()];
		classificator = new DoubleClassificator();
		for (Cluster cluster : Cluster.values()) {
			JRadioButton button = new JRadioButton(cluster.toString());
			radios.add(button);
			radiosBox.add(button);
			button.setActionCommand(cluster.toString());
			grop.add(button);
		}
		skip = new JRadioButton("skip");
		skip.setActionCommand("skip");
		radios.add(skip);
		radiosBox.add(skip);
		grop.add(skip);
		setLayout(new BorderLayout());
		add(radiosBox, BorderLayout.WEST);
		// add(classify, BorderLayout.NORTH);
		// add(right, BorderLayout.NORTH);
		next.add(right);
		next.add(classify);
		add(next, BorderLayout.NORTH);
		add(stop, BorderLayout.SOUTH);
		add(description, BorderLayout.CENTER);
		getRootPane().setDefaultButton(classify);
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					ObjectOutputStream oos = new ObjectOutputStream(
							new FileOutputStream(new File("classified.srzl")));
					ObjectOutputStream oosMap = new ObjectOutputStream(
							new FileOutputStream(new File("classificator.srzl")));
					oos.writeObject(classified);
					oos.close();
					oosMap.writeObject(classificator);
					oosMap.close();
					System.exit(0);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		classify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String act = grop.getSelection().getActionCommand();
				if (act.equals("skip")) {
					pos = rand.nextInt(positions.size());
					try {
						description.setText(positions.get(pos).getName()
								+ "\n"
								+ positions.get(pos).getDescription()
								+ "\n"
								+ classificator.classify(positions.get(pos)
										.getName(), positions.get(pos)
										.getDescription(), 1.0) + "\n ROW: "
								+ row);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				for (Cluster cluster : Cluster.values())
					if (cluster.toString().equals(act)) {
						count[cluster.ordinal()]++;
						radios.get(cluster.ordinal()).setText(
								cluster.toString() + ": "
										+ count[cluster.ordinal()]);
						classified.add(new ClassifiedPosition(positions
								.get(pos), cluster));
						try {
							classificator.add(new ClassifiedPosition(positions
									.get(pos), cluster));
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						was[pos] = true;
						while (was[pos])
							pos = rand.nextInt(positions.size());
						row = 0;
						try {
							description.setText(positions.get(pos).getName()
									+ "\n"
									+ positions.get(pos).getDescription()
									+ "\n"
									+ classificator.classify(positions.get(pos)
											.getName(), positions.get(pos)
											.getDescription(), 1.0)
									+ "\n ROW: " + row);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
			}
		});
		right.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pos = rand.nextInt(positions.size());
				row++;
				try {
					description.setText(positions.get(pos).getName()
							+ "\n"
							+ positions.get(pos).getDescription()
							+ "\n"
							+ classificator.classify(positions.get(pos)
									.getName(), positions.get(pos)
									.getDescription(), 1.0) + "\n ROW: " + row);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new LearingBaseBuilder();
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
