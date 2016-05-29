package ipupdater;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Updater implements ActionListener {
	
	private final String URL_STRING = "http://ddwap.mah.se/ad4063/one.php";
	private final long UPDATE_FREQ = 1200000;
	
	private JFrame frame;
	private JButton btnStart;
	private JButton btnStop;
	private JLabel lblLocalIP;
	private JLabel lblIP;
	private JLabel lblStarted;
	private JLabel lblLastUpdate;
	private UpdateWithTimer updaterTimer;
	private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	
	public Updater() {
		frame = new JFrame();
		frame.setTitle("IPUPDATER V1.0 BY GUSTAV");
		frame.setBounds(0, 0 , 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		btnStart = new JButton();
		btnStart.setText("START");
		btnStart.setBounds(20, 50, 120, 60);
		panel.add(btnStart);
		
		btnStop = new JButton();
		btnStop.setText("STOP");
		btnStop.setBounds(250, 50, 120, 60);
		panel.add(btnStop);
		btnStop.setEnabled(false);
		
		lblLocalIP = new JLabel();
		lblLocalIP.setText("NOT STARTED");
		lblLocalIP.setBounds(20, 300, 200, 70);
		panel.add(lblLocalIP);
		
		lblIP = new JLabel();
		lblIP.setText("INFO 2");
		lblIP.setBounds(20, 130, 200, 50);
		panel.add(lblIP);
		
		lblStarted = new JLabel();
		lblStarted.setText("INFO S");
		lblStarted.setBounds(20, 200, 200, 50);
		panel.add(lblStarted);
		
		lblLastUpdate = new JLabel();
		lblLastUpdate.setText("INFO L");
		lblLastUpdate.setBounds(20, 250, 250, 50);
		panel.add(lblLastUpdate);
		
		frame.add(panel);
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		addListeners();
	}
	
	private void addListeners() {
		btnStart.addActionListener(this);
		btnStop.addActionListener(this);
	}
	
	public void start() {
		
	}

	private void sendToPhp() {
		try {
			URL urlObject = new URL(URL_STRING);
			try {
				URLConnection con = urlObject.openConnection();
				con.setDoOutput(true);
				PrintStream printStream = new PrintStream(con.getOutputStream());
				printStream.print("x=fromSY");
				con.getInputStream();
				printStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void setLblText(String txt) {
		lblLocalIP.setText(txt);
	}
	
	public void setTextInfoTwo(String txt) {
		lblIP.setText(txt);
	}
	
	public void setTextStarted(String txt) {
		lblStarted.setText(txt);
	}
	
	public void setTextLatest(String txt) {
		lblLastUpdate.setText(txt);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnStart) {
			btnStart.setEnabled(false);
			btnStop.setEnabled(true);
			updaterTimer = new UpdateWithTimer(UPDATE_FREQ);
		} else if(e.getSource() == btnStop) {
			btnStop.setEnabled(false);
			btnStart.setEnabled(true);
			updaterTimer.stopUpdating();
			updaterTimer = null;
			setTextInfoTwo("INFO");
			setTextStarted("INFO S");
			setTextLatest("INFO L");
		}
	}
	
	public String getTime() {
		Date date = new Date();
		String dateTime = df.format(date);
		return dateTime;
	}
	
	private class UpdateWithTimer extends Thread {
		
		private long time;
		private boolean running = true;
		private int counter = 0;
		
		public UpdateWithTimer(long time) {
			this.time = time;
			this.start();
			setLblText("RUNNING...");
			setTextStarted("STARTED: " + getTime());
		}
		
		public void run() {
			while(running) {
				sendToPhp();
				counter++;
				setTextInfoTwo("Updates since last start: " + counter);
				setTextLatest("LAST UPDATE: " + getTime());
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void stopUpdating() {
			running = false;
			setLblText("STOPPED");
		}
	}

}