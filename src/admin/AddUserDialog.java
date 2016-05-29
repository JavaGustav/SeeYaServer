package admin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class AddUserDialog extends Dialog {
	private Text textName;
	private Text textPass;
	private Text textEmail;

	public AddUserDialog(Shell parent, int style) {
		super(parent);
	}
	
	public AddUserDialog(Shell parent) {
		this(parent, 0);
	}
	
	public User open() {
		User user = new User();
		
		Shell parent = getParent();
		Shell shlAddUser = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlAddUser.setText("ADD USER");
		shlAddUser.setSize(500, 300);
		shlAddUser.setLayout(new GridLayout(2, false));
		new Label(shlAddUser, SWT.NONE);
		new Label(shlAddUser, SWT.NONE);
		
		Label lblName = new Label(shlAddUser, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblName.setAlignment(SWT.CENTER);
		lblName.setText("NAME:");
		
		textName = new Text(shlAddUser, SWT.BORDER);
		textName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(shlAddUser, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("PASSWORD:");
		
		textPass = new Text(shlAddUser, SWT.BORDER);
		textPass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_2 = new Label(shlAddUser, SWT.NONE);
		lblNewLabel_2.setAlignment(SWT.CENTER);
		lblNewLabel_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("EMAIL:");
		
		textEmail = new Text(shlAddUser, SWT.BORDER);
		textEmail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shlAddUser, SWT.NONE);
		new Label(shlAddUser, SWT.NONE);
		new Label(shlAddUser, SWT.NONE);
		
		Button btnNewButton = new Button(shlAddUser, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				user.setUserName(textName.getText());
				user.setPassword(textPass.getText());
				user.setEmail(textEmail.getText());
				shlAddUser.dispose();
			}
		});
		btnNewButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnNewButton.setText("CREATE");
		
		
		
		shlAddUser.open();
		Display display = parent.getDisplay();
		while(!shlAddUser.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return user;
	}

}
