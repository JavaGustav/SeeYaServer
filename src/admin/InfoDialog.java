package admin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class InfoDialog extends Dialog {

	public InfoDialog(Shell parent, int style) {
		super(parent);
	}

	public InfoDialog(Shell parent) {
		this(parent, 0);
	}

	public void open(String headline, String message1, String message2) {
		Shell parent = getParent();
		Shell infoD = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		infoD.setText(headline);
		infoD.setSize(300, 200);

		Label lblMessage1 = new Label(infoD, SWT.NONE);
		lblMessage1.setBounds(35, 29, 223, 17);
		lblMessage1.setText(message1);

		Button btnNewButton = new Button(infoD, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				infoD.dispose();
			}
		});
		btnNewButton.setBounds(103, 133, 93, 29);
		btnNewButton.setText("OK");
		
		Label lblMessage2 = new Label(infoD, SWT.NONE);
		lblMessage2.setBounds(35, 53, 223, 17);
		lblMessage2.setText(message2);
		
		
		
		infoD.open();
		Display display = parent.getDisplay();
		while(!infoD.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}