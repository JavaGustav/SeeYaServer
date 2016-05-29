package admin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Label;

/**
 * SeeYa Admin
 * Gustav Frigren
 */

public class MainWindow {

	private Controller controller;

	private List listUsers;

	Display display = new Display();
	Shell shell = new Shell(display, SWT.TITLE);

	public MainWindow() {
		controller = new Controller(this);
		System.out.println("ADMIN CONSTR MAINWINDOW");
		this.controller = controller;
		shell.setBackgroundImage(SWTResourceManager.getImage
				(MainWindow.class, "/files/SeeYalogodim.png"));
		shell.setSize(810, 500);
		shell.setText("SeeYa Admin");

		initComponents();
	}

	private void initComponents() {

		centreWindow();
		shell.setLayout(null);

		Button btnAddUser = new Button(shell, SWT.NONE);
		btnAddUser.setBounds(10, 45, 109, 29);
		btnAddUser.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		btnAddUser.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
    	btnAddUser.addSelectionListener(new SelectionAdapter() {
    		@Override
    		public void widgetSelected(SelectionEvent e) {
    			AddUserDialog add = new AddUserDialog(shell);
    			User user = add.open();
    			System.out.println(user.getUserName() 
    					+ user.getPassword() + user.getEmail());
    			controller.addUser(user.getUserName(), 
    					user.getPassword(), user.getEmail());
    		}
    	});
    	btnAddUser.setText("ADD USER");

    	Button btnAddActivity = new Button(shell, SWT.NONE);
    	btnAddActivity.setBounds(10, 79, 109, 29);
    	btnAddActivity.setText("ADD ACTIVITY");

    	Button btnAddMainCat = new Button(shell, SWT.NONE);
    	btnAddMainCat.addSelectionListener(new SelectionAdapter() {
    		@Override
    		public void widgetSelected(SelectionEvent e) {
    			AddMainCatDialog d = new AddMainCatDialog(shell);
    			d.open();
    		}
    	});
    	btnAddMainCat.setBounds(10, 114, 109, 29);
    	btnAddMainCat.setText("ADD MAINCAT");

    	Button btnAddSubCat = new Button(shell, SWT.NONE);
    	btnAddSubCat.addSelectionListener(new SelectionAdapter() {
    		@Override
    		public void widgetSelected(SelectionEvent e) {
    			InfoDialog d = new InfoDialog(shell);
    			d.open("TEST", "ETT", "TVÅ");
    		}
    	});
    	btnAddSubCat.setBounds(10, 149, 109, 29);
    	btnAddSubCat.setText("ADD SUBCAT");

    	Button btnAddLandsc = new Button(shell, SWT.NONE);
    	btnAddLandsc.setBounds(10, 184, 109, 29);
    	btnAddLandsc.setText("ADD LANDSC");

    	Button btnAddCity = new Button(shell, SWT.NONE);
    	btnAddCity.setBounds(10, 219, 109, 29);
    	btnAddCity.setText("ADD CITY");

    	Button btnUpdateData = new Button(shell, SWT.NONE);
    	btnUpdateData.addSelectionListener(new SelectionAdapter() {
    		@Override
    		public void widgetSelected(SelectionEvent e) {
    			controller.requestUserList();
    		}
    	});
    	btnUpdateData.setBounds(10, 392, 109, 29);
    	btnUpdateData.setText("UPDATE DATA");

    	listUsers = new List(shell, SWT.BORDER);
    	listUsers.setBounds(175, 45, 170, 203);

    	List listActivities = new List(shell, SWT.BORDER);
    	listActivities.setBounds(382, 45, 170, 203);

    	Button btnSelectUser = new Button(shell, SWT.NONE);
    	btnSelectUser.addSelectionListener(new SelectionAdapter() {
    		@Override
    		public void widgetSelected(SelectionEvent e) {
    			String[] string = listUsers.getSelection();
    			System.out.println(string[0]);
    			controller.requestUserEmail(string[0]);
    		}
    	});
    	btnSelectUser.setBounds(214, 254, 93, 29);
    	btnSelectUser.setText("SELECT");

    	Button btnSelectActivity = new Button(shell, SWT.NONE);
    	btnSelectActivity.setBounds(423, 254, 93, 29);
    	btnSelectActivity.setText("SELECT");

    	Label lblNewLabel = new Label(shell, SWT.NONE);
    	lblNewLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/files/SeeYaBackground.png"));
    	lblNewLabel.setBounds(237, 22, 47, 17);
    	lblNewLabel.setText("USERS:");

    	Label lblNewLabel_1 = new Label(shell, SWT.NONE);
    	lblNewLabel_1.setBounds(430, 22, 83, 17);
    	lblNewLabel_1.setText("ACTIVITIES:");
    	
    	Button btnQuit = new Button(shell, SWT.NONE);
    	btnQuit.setForeground(SWTResourceManager.getColor(SWT.COLOR_MAGENTA));
		btnQuit.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
    	btnQuit.addSelectionListener(new SelectionAdapter() {
    		@Override
    		public void widgetSelected(SelectionEvent e) {
    			controller.stopConnection();
    			System.exit(0);
    		}
    	});
    	btnQuit.setBounds(10, 427, 109, 29);
    	btnQuit.setText("QUIT");

		shell.open();
    	while (!shell.isDisposed()) {
    		if (!display.readAndDispatch()) {
    			display.sleep();
    		}
    	}
    	display.dispose();
	}

	private void centreWindow() {
		Monitor p = display.getPrimaryMonitor();
    	Rectangle b = p.getBounds();
    	Rectangle rectangle = shell.getBounds();

    	int x = b.x + (b.width - rectangle.width) / 2;
    	int y = b.y + (b.height - rectangle.height) / 2;

    	shell.setLocation(x, y);
	}

	public void setUserListData(String[] data) {
		clearUserList();
		for(int i = 0; i < data.length; i++) {
			final int index = i;
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					listUsers.add(data[index]);
				}
			});
		}
	}

	public void clearUserList() {
		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				listUsers.removeAll();
			}
		});
	}

	public void test() {
		System.out.println("HEJ");
	}

	public void showInfoDialog(String headline, String message1, String message2) {
		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				InfoDialog d = new InfoDialog(shell);
				d.open(headline, message1, message2);
			}
		});
	}

	public static void main(String args[]) {
		new MainWindow();
	}
}