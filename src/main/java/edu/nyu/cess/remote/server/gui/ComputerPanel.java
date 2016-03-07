package edu.nyu.cess.remote.server.gui;

import edu.nyu.cess.remote.common.app.AppExe;
import edu.nyu.cess.remote.server.gui.listeners.StartStopButtonListener;
import edu.nyu.cess.remote.server.gui.observers.StartStopButtonObserver;
import edu.nyu.cess.remote.common.net.ConnectionState;

import javax.swing.*;
import java.awt.*;

public class ComputerPanel extends JPanel
{
    private final String name;

    private final JButton startButton;
    private final JButton stopButton;

	private final JLabel hostNameLabel;
	private final JLabel appExeStateLabel;

    private ConnectionState connectionState;

    public boolean isConnectionState(ConnectionState connectionState)
    {
        return this.connectionState == connectionState;
    }

    public ComputerPanel(String name, String ipAddress, ConnectionState connectionState, StartStopButtonObserver startStopButtonObserver)
    {
		super();
        this.name = name;
		this.connectionState = connectionState;

		/*
		 * Computer Panel Layout & Border
		 */
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder((connectionState == ConnectionState.CONNECTED) ? Color.BLACK : Color.LIGHT_GRAY));
		Color white = new Color(255, 255, 255);
		Color lightRed = new Color(255, 194, 194);
		setBackground((connectionState == ConnectionState.CONNECTED) ? white : lightRed);

		/*
		 * Host Name
		 */
		hostNameLabel = new JLabel("" + name);
        hostNameLabel.setFont(new Font("arial", Font.PLAIN, 18));
		hostNameLabel.setForeground((connectionState == ConnectionState.CONNECTED) ? Color.BLACK : Color.GRAY);

		JPanel clientDescriptionPanel = new JPanel(new FlowLayout());
		clientDescriptionPanel.setOpaque(false);
		clientDescriptionPanel.add(hostNameLabel);
		add(clientDescriptionPanel);

		/*
		 * Computer/Application State
		 */
		JPanel applicationStatePanel = new JPanel(new FlowLayout());
		applicationStatePanel.setOpaque(false);

		appExeStateLabel = new JLabel();
		appExeStateLabel.setText((connectionState == ConnectionState.CONNECTED) ? "Connected" : "Not Connected");
		appExeStateLabel.setFont(new Font("arial", Font.PLAIN, 12));
		appExeStateLabel.setForeground((connectionState == ConnectionState.DISCONNECTED) ? Color.GRAY : Color.BLACK);

		applicationStatePanel.add(appExeStateLabel);
		add(applicationStatePanel);

		/*
		 * Start & Stop Button
		 */
		startButton = new JButton("Start");
		startButton.setFont(new Font("arial", Font.PLAIN, 14));
		startButton.setToolTipText("Starts selected application on " + ipAddress + ".");
		startButton.addActionListener(new StartStopButtonListener(startStopButtonObserver, ipAddress, "Start", "Stop"));
		startButton.setEnabled(connectionState == ConnectionState.CONNECTED);

		stopButton = new JButton("Stop");
		stopButton.setFont(new Font("arial", Font.PLAIN, 14));
		stopButton.setToolTipText("Starts selected application on " + ipAddress + ".");
		stopButton.addActionListener(new StartStopButtonListener(startStopButtonObserver, ipAddress, "Start", "Stop"));
		stopButton.setEnabled(false);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setOpaque(false);
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		add(buttonPanel);
    }

    public void updateAppExe(AppExe appExe)
    {
        switch(appExe.getState()) {
            case STARTED:
                String name = appExe.getAppInfo().getName();
                if (name.length() > 25) {
                    name = name.substring(0, 24) + "...";
                }
                appExeStateLabel.setText(name);
                setBackground(new Color(204, 255, 204));
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                break;
            case STOPPED:
                appExeStateLabel.setText("Connected");
                setBackground(new Color(255, 255, 255));
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                break;
        }
    }


	public void updateState(ConnectionState connectionState)
	{
        switch(connectionState) {
            case CONNECTED:
                if (this.connectionState == ConnectionState.DISCONNECTED) {
                    setBackground(new Color(255, 255, 255));
                    startButton.setEnabled(true);
                    appExeStateLabel.setText("Connected");
                    appExeStateLabel.setForeground(Color.BLACK);
                    hostNameLabel.setForeground(Color.BLACK);
                    setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    this.connectionState = connectionState;
                }
                break;
            case DISCONNECTED:
                if (this.connectionState == ConnectionState.CONNECTED){
                    startButton.setEnabled(false);
                    setBackground(new Color(253, 236, 236));
                    setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                    appExeStateLabel.setText("Not Connected");
                    appExeStateLabel.setForeground(Color.GRAY);
                    hostNameLabel.setForeground(Color.GRAY);
                    this.connectionState = connectionState;
                }
                break;
        }
	}

    public String getName()
    {
        return name;
    }
}
