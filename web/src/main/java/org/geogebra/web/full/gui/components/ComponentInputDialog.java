package org.geogebra.web.full.gui.components;

import org.geogebra.common.gui.InputHandler;
import org.geogebra.common.main.error.ErrorHandler;
import org.geogebra.common.util.AsyncOperation;
import org.geogebra.web.full.gui.dialog.ProcessInput;
import org.geogebra.web.html5.gui.HasKeyboardPopup;
import org.geogebra.web.html5.gui.inputfield.AutoCompleteTextFieldW;
import org.geogebra.web.html5.gui.util.Dom;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.shared.components.dialog.ComponentDialog;
import org.geogebra.web.shared.components.dialog.DialogData;

/**
 * dialog component for dialogs with one input text field
 * e.g. regular polygon tool dialog
 */
public class ComponentInputDialog extends ComponentDialog
		implements ErrorHandler, HasKeyboardPopup {
	private InputHandler inputHandler;
	private ComponentInputField inputTextField;

	/**
	 * Base dialog constructor: single row
	 * @param app - see {@link AppW}
	 * @param dialogData - contains trans keys for title and buttons
	 * @param autoHide - if the dialog should be closed on click outside
	 * @param hasScrim - background should be greyed out
	 * @param inputHandler - input handler
	 */
	public ComponentInputDialog(AppW app, DialogData dialogData,
			boolean autoHide, boolean hasScrim, InputHandler inputHandler, String labelText,
			String initText) {
		this(app, dialogData, autoHide, hasScrim, inputHandler);
		createGUI(labelText, initText);
	}

	/**
	 *
	 * @param app - see {@link AppW}
	 * @param dialogData - contains trans keys for title and buttons
	 * @param autoHide - if the dialog should be closed on click outside
	 * @param hasScrim - background should be greyed out
	 * @param inputHandler - input handler
	 */
	protected ComponentInputDialog(AppW app, DialogData dialogData,
			boolean autoHide, boolean hasScrim, InputHandler inputHandler) {
		super(app, dialogData, autoHide, hasScrim);
		addStyleName("inputDialogComponent");
		setPreventHide(true);
		setInputHandler(inputHandler);
		setOnPositiveAction(this::processInput);
		if (!app.isWhiteboardActive()) {
			app.registerPopup(this);
		}
		this.addCloseHandler(event -> {
			app.unregisterPopup(this);
			app.hideKeyboard();
		});
	}

	private void createGUI(String labelText, String initText) {
		inputTextField = new ComponentInputField((AppW) app,
				"", labelText, "", initText, -1, 1,
				"");
		addDialogContent(inputTextField);
	}

	protected InputHandler getInputHandler() {
		return inputHandler;
	}

	protected void setInputHandler(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
	}

	protected void processInputHandler(String inputText,
			AsyncOperation<Boolean> callback) {
		getInputHandler().processInput(inputText, this, callback);
	}

	public String getInputText() {
		return inputTextField.getTextField().getText();
	}

	/**
	 * Note: package visibility to make this accessible from anonymous classes
	 *
	 * @return single line text input
	 */
	protected AutoCompleteTextFieldW getTextComponent() {
		return inputTextField == null ? null : inputTextField.getTextField().getTextComponent();
	}

	@Override
	public void show() {
		super.show();
		if (inputTextField != null) {
			inputTextField.focusDeferred();
		}
	}

	@Override
	public void showCommandError(String command, String message) {
		app.getDefaultErrorHandler().showCommandError(command, message);
	}

	@Override
	public String getCurrentCommand() {
		AutoCompleteTextFieldW textComponent = getTextComponent();
		if (textComponent != null) {
			return textComponent.getCommand();
		}
		return null;
	}

	@Override
	public boolean onUndefinedVariables(String string,
			AsyncOperation<String[]> callback) {
		return app.getGuiManager().checkAutoCreateSliders(string, callback);
	}

	@Override
	public void resetError() {
		showError(null);
	}

	@Override
	public void showError(String msg) {
		if (inputTextField != null) {
			inputTextField.setError(msg);
		}
	}

	/**
	 * process input, show error if input wrong,
	 * otherwise hide dialog
	 */
	public void processInput() {
		getInputHandler().processInput(getInputText(), this,
				ok -> {
					if (ok) {
						toolAction();
						hide();
					}
				});
	}

	/**
	 * Callback for tool dialogs
	 */
	protected void toolAction() {
		// overridden in subclasses
	}

	/**
	 * @param inputHandler input event handler
	 */
	public void addInputHandler(ProcessInput inputHandler) {
		Dom.addEventListener(getTextComponent().getTextBox().getElement(),
				"input", event -> inputHandler.onInput());
	}
}