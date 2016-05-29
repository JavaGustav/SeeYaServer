package admin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddMainCatDialog extends Dialog {
	
	private Text textName;
	
	public AddMainCatDialog(Shell parent, int style) {
		super(parent);
	}
	
	public AddMainCatDialog(Shell parent) {
		this(parent, 0);
	}
	
	public User open() {
		User user = new User();
		
		Shell parent = getParent();
		Shell shlAddUser = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlAddUser.setText("ADD MAINCATEGORY");
		shlAddUser.setSize(500, 200);
		shlAddUser.setLayout(new GridLayout(2, false));
		new Label(shlAddUser, SWT.NONE);
		new Label(shlAddUser, SWT.NONE);

		Label lblName = new Label(shlAddUser, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblName.setAlignment(SWT.CENTER);
		lblName.setText("NAME:");
		
		textName = new Text(shlAddUser, SWT.BORDER);
		textName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		new Label(shlAddUser, SWT.NONE);
		new Label(shlAddUser, SWT.NONE);
		new Label(shlAddUser, SWT.NONE);
		
		Button btnNewButton = new Button(shlAddUser, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				user.setUserName(textName.getText());
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