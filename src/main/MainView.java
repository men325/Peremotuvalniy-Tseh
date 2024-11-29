package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import javazoom.jlme.util.Player;
import main.workers.Worker;
import main.workers.Factory;
import main.workers.Shop;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.net.URL;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private long runPoint;
    JSlider speedSlider;
    private Player music;

    private Thread playThread;
    private Thread prepThread;
    private Thread thread1;
    private Thread thread2;
    private JTextField resultField;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
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
	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 600);
		setBackground(Color.WHITE);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnNewMenu = new JMenu("Game");
		menuBar.add(mnNewMenu);		
		JMenuItem mntmStart = new JMenuItem("Run");		
		mnNewMenu.add(mntmStart);	
		JMenuItem mntmStop = new JMenuItem("Stop");
		mntmStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Stop Game");
				doStopPlay();
		        new Thread() {
		            @Override
		            public void run() {
		                try {
		                    thread1.join();
		                }
		                catch (InterruptedException e) {
		                    e.printStackTrace();
		                }
		            }
		        }.start();
			}
		});
		
		
		mnNewMenu.add(mntmStop);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel worker = new JLabel("");
		worker.setIcon(new ImageIcon(MainView.class.getResource("/resources/second1.png")));
		worker.setBounds(50, 200, 100, 100);
        worker.setName("Worder");
		contentPane.add(worker);
		
		JLabel factory = new JLabel("");
		factory.setIcon(new ImageIcon(MainView.class.getResource("/resources/first1.png")));
		factory.setBounds(350, 200, 100, 100);
        factory.setName("Factory");
		contentPane.add(factory);
				
		JLabel shop = new JLabel("");
		shop.setIcon(new ImageIcon(MainView.class.getResource("/resources/third1.png")));
		shop.setBounds(650, 200, 100, 100);
        shop.setName("Shop");
		contentPane.add(shop);
		
		JSlider workerWorkTime = new JSlider();
		workerWorkTime.setPaintLabels(true);
		workerWorkTime.setPaintTicks(true);
		workerWorkTime.setSnapToTicks(true);
		workerWorkTime.setMajorTickSpacing(250);
		workerWorkTime.setMinorTickSpacing(50);
		workerWorkTime.setValue(250);
		workerWorkTime.setMinimum(250);
		workerWorkTime.setMaximum(1000);
		workerWorkTime.setBounds(10, 10, 200, 65);
		workerWorkTime.setBorder(new TitledBorder(new MatteBorder(0, 0, 0, 0, Color.WHITE), " Worker Work Time ", 2, 2, null, null));
		contentPane.add(workerWorkTime);
		
		JSlider factoryQueue = new JSlider();
		factoryQueue.setMajorTickSpacing(1);
		factoryQueue.setName("Factory Queue");
		factoryQueue.setSnapToTicks(true);
		factoryQueue.setPaintLabels(true);
		factoryQueue.setPaintTicks(true);
		factoryQueue.setValue(0);
		factoryQueue.setMaximum(5);
		factoryQueue.setBounds(300, 325, 200, 65);
		factoryQueue.setBorder(new TitledBorder(new MatteBorder(0, 0, 0, 0, Color.LIGHT_GRAY), " Queue ", 2, 2, null, null));
		contentPane.add(factoryQueue);
		
		JSlider factoryWorkTime = new JSlider();
		factoryWorkTime.setToolTipText("\r\n");
		factoryWorkTime.setSnapToTicks(true);
		factoryWorkTime.setPaintLabels(true);
		factoryWorkTime.setPaintTicks(true);
		factoryWorkTime.setMajorTickSpacing(250);
		factoryWorkTime.setMinorTickSpacing(50);
		factoryWorkTime.setValue(250);
		factoryWorkTime.setMinimum(250);
		factoryWorkTime.setMaximum(1000);
		factoryWorkTime.setBounds(250, 10, 200, 65);
		factoryWorkTime.setBorder(new TitledBorder(new MatteBorder(0, 0, 0, 0, Color.WHITE), " Factory Work Time ", 2, 2, null, null));
		contentPane.add(factoryWorkTime);
		
		JSlider shopQueue = new JSlider();
		shopQueue.setMajorTickSpacing(1);
		shopQueue.setName("Shop Queue");
		shopQueue.setSnapToTicks(true);
		shopQueue.setPaintLabels(true);
		shopQueue.setPaintTicks(true);
		shopQueue.setValue(0);
		shopQueue.setMaximum(5);
		shopQueue.setBorder(new TitledBorder(new MatteBorder(0, 0, 0, 0, Color.WHITE), " Queue ", 2, 2, null, null));
		shopQueue.setBounds(610, 325, 200, 65);
		contentPane.add(shopQueue);
		
		JSlider shopWorkTime = new JSlider();
		shopWorkTime.setSnapToTicks(true);
		shopWorkTime.setPaintTicks(true);
		shopWorkTime.setPaintLabels(true);
		shopWorkTime.setMajorTickSpacing(250);
		shopWorkTime.setMinorTickSpacing(50);
		shopWorkTime.setValue(250);
		shopWorkTime.setMinimum(250);
		shopWorkTime.setMaximum(1000);
		shopWorkTime.setBorder(new TitledBorder(new MatteBorder(0, 0, 0, 0, Color.WHITE), " Shop Work Time ", 2, 2, null, null));
		shopWorkTime.setBounds(490, 10, 200, 65);
		contentPane.add(shopWorkTime);
		
		speedSlider = new JSlider();
		speedSlider.setValue(40);
		speedSlider.setMinorTickSpacing(5);
		speedSlider.setMajorTickSpacing(20);
		speedSlider.setSnapToTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.setPaintTicks(true);
		speedSlider.setMinimum(20);
		speedSlider.setBounds(730, 10, 200, 65);
		speedSlider.setBorder(new TitledBorder(new MatteBorder(0, 0, 0, 0, Color.WHITE), "Product Movement Speed", 2, 2, null, null));
		contentPane.add(speedSlider);
		
		resultField = new JTextField();
		resultField.setName("Sold Items");
		resultField.setBounds(950, 240, 50, 50);
		contentPane.add(resultField);
		resultField.setColumns(10);
		
		JLabel lblStorage = new JLabel("Items Sold");
		lblStorage.setBounds(942, 200, 100, 50);
		contentPane.add(lblStorage);
		
		
		
		mntmStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("start");
				playThread = playMusic();
		        final Result result = new Result(resultField);
		        final WorkingProcessor mainQueue = new WorkingProcessor(MainView.this, factoryQueue, result);
		        final WorkingProcessor secondaryQueue = new WorkingProcessor(MainView.this,shopQueue,result);
		        final Worker workerInstance = new Worker(MainView.this, worker,mainQueue,secondaryQueue,workerWorkTime);
		        final Factory factoryInstance = new Factory(MainView.this,factory,mainQueue,secondaryQueue, factoryWorkTime);
		        final Shop shopInstance = new Shop(MainView.this, shop,mainQueue,  secondaryQueue, shopWorkTime,result);
		        
		        runPoint = System.currentTimeMillis();
		        (prepThread = new Thread(workerInstance)).start();
		        (thread1 = new Thread(factoryInstance)).start();
		        (thread2 = new Thread(shopInstance)).start();
			}
		});
	}

    public Component getPane() {
        return contentPane;
    }
	
    public JSlider getStepTimeSlider() {
        return speedSlider;
    }
    
    public void println(final String s) {
        final long time = System.currentTimeMillis() - this.runPoint;
        System.out.println("Worked: " + time + ":");
        System.out.println(s);
    }
    
    
    private Thread playMusic() {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    final URL u = this.getClass().getResource("/resources/jazz.mp3");
                    music = new Player(new BufferedInputStream(u.openStream(), 2048));
                    music.play();
                    onEndOfPlay();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        return t;
    }
    
    private void doStopPlay() {
        this.music.stop();
    }
    
    private void onEndOfPlay() {
        new Thread() {
            public void run() {
                try {
                    thread1.join();
                    thread2.join();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    
    public boolean isPlaying() {
        return this.playThread.isAlive();
    }
    
    public boolean isCreatorWorking() {
        return this.prepThread.isAlive();
    }
    
}
